# HW6 — High-Performance Spell Checker with a Custom HashMap and HashSet

CSE222 Data Structures and Algorithms — Homework 6

The original problem statement is included as [`assignment.pdf`](./assignment.pdf), and a full written report is included as [`report.pdf`](./report.pdf).

## Overview

A spell checker backed entirely by hand-written hash-based data structures — no `java.util.HashMap`, `HashSet`, `List`, `ArrayList`, or any other Java collection type is used anywhere in the implementation. The only `java.util`/`java.io` usage is `Scanner`, `BufferedReader`, `FileReader`, and `IOException` for I/O. Given a 50,000+ word dictionary, the program tells the user whether an input word is correctly spelled and, if not, suggests corrections within edit distance ≤ 2 — without ever scanning the full dictionary.

## Design

- **`Entry<K, V>`** — a simple key/value slot used by the hash table, with an `isDeleted` flag used for tombstone-based deletion.
- **`GTUHashMap<K, V>`** — a hash map built from scratch using **open addressing with linear probing**:
  - `put` / `get` / `remove` / `containsKey` / `size` as required by the assignment.
  - Deletions use **tombstones** (`Entry.isDeleted`) rather than nulling the slot, so probing sequences for other keys stay intact after a removal.
  - Once the load factor exceeds 0.75, the table **rehashes** into the next prime capacity greater than twice the current size (the bonus requirement), which keeps probe sequences short and reduces clustering as the table grows.
- **`GTUHashSet<E>`** — implemented on top of `GTUHashMap<E, Object>` exactly as the assignment skeleton specifies: `add` calls `map.put(element, PRESENT)`, `contains` calls `map.containsKey(element)`, and so on, reusing the map's hashing and probing logic rather than duplicating it.
- **`SpellChecker`** — loads the dictionary into a `GTUHashSet<String>`, then for each query word:
  1. Checks `dictionary.contains(word)` for an exact match — average-case `O(1)` thanks to the hash set.
  2. If misspelled, generates all **edit-distance-1 and edit-distance-2 variants** of the word (single-character insertions, deletions, and substitutions, composed twice for distance 2) and tests each variant against the dictionary with `contains()`, rather than scanning all 50,000+ dictionary entries. This bounds suggestion generation to roughly `O(L²)` candidate strings for a word of length `L`, keeping lookups well under the assignment's 100ms requirement.

## Build & Run

```bash
make collect   # gathers all .java sources into sources.txt
make build     # compiles into ./build
make run       # interactive: prompts for words, type .exit to quit
make docs      # generates Javadoc into ./docs
make clean     # removes build/, sources.txt, and docs/
```

The program expects `dictionary.txt` to be present in the working directory it's run from (it's loaded as a relative path).

## Example Session

```
Enter a word [.exit to quit] : aple
Incorrect.
Suggestions: [apple, ample, ...]
Lookup and suggestion took 0.8x ms
Enter a word [.exit to quit] : aardvark
Correct.
Lookup and suggestion took 0.0x ms
```

## Dictionary

[`dictionary.txt`](./dictionary.txt) contains 80,000+ English words, one per line, as provided with the assignment.

## Report

[`report.pdf`](./report.pdf) documents the methodology, test results, and performance discussion (open addressing vs. chaining trade-offs, load factor and rehashing behavior, edit-distance generation strategy, and measured lookup times) as required by the assignment.
