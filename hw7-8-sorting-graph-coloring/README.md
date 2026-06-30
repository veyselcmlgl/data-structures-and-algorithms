# HW7-8 — Sorting Algorithms, Adjacency Matrix, and Graph Coloring

CSE222 / BİL505 Data Structures and Algorithms — Final Assignment (7-8)

The original problem statement is included as [`assignment.pdf`](./assignment.pdf), and a full written report is included as [`report.pdf`](./report.pdf).

## Overview

The final assignment of the course, combining two independent pieces — generic sorting algorithms and a graph implementation — and wiring both into a provided greedy graph-coloring algorithm to demonstrate they work correctly together.

**What I implemented:** `GTUInsertSort`, `GTUQuickSort`, `GTUSelectSort` (sorting), and `AdjacencyVect`, `MatrixGraph` (graph). **What was provided by the course staff:** the `GTUSorter` abstract class, the `GTUGraph` interface, the greedy graph-coloring algorithm (`GreedyGCA`, `GCASolution`), and the `Main` driver — per the assignment's design, students were only responsible for the sorter and graph implementations, not the coloring algorithm itself.

## Design

### Sorting (`DSA.Sorting`)

- **`GTUSorter`** *(provided)* — abstract class exposing a generic `sort(T[] arr, Comparator<T> comparator)`, delegating to an abstract `sort(arr, start, end, comparator)` so recursive algorithms can operate on subarrays.
- **`GTUInsertSort`** — classic insertion sort, `O(n²)` worst case, stable, in-place.
- **`GTUSelectSort`** — classic selection sort, `O(n²)`, included as a bonus implementation.
- **`GTUQuickSort`** — quicksort with a **randomly selected pivot** (`java.util.Random`) to avoid worst-case behavior on already-sorted input. The constructor optionally accepts a **fallback `GTUSorter`** and a **partition-size limit**: once a partition shrinks below the limit, the fallback sorter (e.g. insertion sort) takes over instead of continuing to recurse — a common real-world optimization, since simple sorts beat quicksort's overhead on small partitions. The sorter and limit can only be set via the constructor, by design, to keep the object's behavior fixed after construction.

### Graph (`DSA.Graphs`)

- **`GTUGraph`** *(interface, provided)* — `setEdge`, `getEdge`, `getNeighbors`, `size`, `reset`, plus a static `readGraph` helper that parses the assignment's graph file format.
- **`AdjacencyVect`** — a fixed-size vector of booleans representing one vertex's row in an adjacency matrix, implementing the full `java.util.Collection<Integer>` interface (including `addAll`, `removeAll`, `retainAll`, `toArray` — the bonus objective). Its custom iterator skips over `false` entries so that iterating an `AdjacencyVect` yields exactly that vertex's neighbor IDs, in `O(1)` amortized per step.
- **`MatrixGraph`** — implements `GTUGraph` using one `AdjacencyVect` per vertex (`O(V²)` space). Since the graph is undirected, `setEdge` sets the bit in both vertices' rows; self-loops are rejected.

### Graph Coloring (`DSA.Graphs.GCA`, provided)

`GreedyGCA` sorts vertices by descending degree (using the student-implemented `GTUSorter`) and greedily assigns each vertex the first color not already used by one of its colored neighbors — a textbook degree-ordered greedy coloring heuristic. This is the integration point that exercises both the sorting and graph implementations together.

## Build & Run

```bash
make clean
make collect   # gathers all .java sources into sources.txt
make build     # compiles into ./build
make run ARGS="<input_file> <output_path_ending_with_/>"
make doc       # generates Javadoc into ./doc
```

For each run, the program writes: the sorted sums of each input edge's vertex-ID pairs (one file per sorter variant), the graph's adjacency information as re-queried from `MatrixGraph` (`graph.txt`), and the resulting coloring solution per sorter variant (`<sorterName>_color.txt`).

## Input Format

```
<graph size>
<vertex1> <vertex2>   // edge
<vertex1> <vertex2>
...
```

## Documentation

Javadoc HTML documentation — including time- and space-complexity notes on every method — is included in [`doc/`](./doc) and can be opened locally via `doc/index.html`.
