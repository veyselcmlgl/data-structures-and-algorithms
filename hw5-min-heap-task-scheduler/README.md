# HW5 — Task Scheduling Using a Min Heap

CSE222 / BİL505 Data Structures and Algorithms — Assignment 5

The original problem statement is included as [`assignment.pdf`](./assignment.pdf).

## Overview

A from-scratch binary min-heap, used as the backing store for a priority queue that schedules tasks issued by users of different importance. Users are configured at startup with integer priority values (lower value = higher importance); tasks are issued from the terminal as user IDs and, on the `execute` command, are drained from the heap and printed in priority order — higher-importance users first, and earlier-issued tasks first among equally important users.

## Design

- **`MyPriorityQueue<T extends Comparable<T>>`** — a minimal generic interface (`add`, `poll`, `isEmpty`) that any priority-queue-like structure must satisfy.
- **`MinHeap<T extends Comparable<T>>`** — implements `MyPriorityQueue<T>` as a binary min heap backed by an `ArrayList<T>` (array representation: children of index `i` at `2i+1`/`2i+2`). `add` appends and sifts up; `poll` swaps the root with the last element, removes it, and sifts down. Both run in `O(log n)`; `isEmpty` is `O(1)`.
- **`MyUser`** — immutable id/priority pair. Users are loaded once from a config file and stored in an `ArrayList<MyUser>`, then referenced (not recreated) by every task that user issues.
- **`MyTask implements Comparable<MyTask>`** — holds a reference to its issuing `MyUser` and an auto-incremented task ID. `compareTo` orders first by the user's priority value, and breaks ties by task ID (earlier-issued task wins), which is exactly the ordering the heap needs to schedule tasks correctly.
- **`TaskManager`** — owns the user list and a `MyPriorityQueue<MyTask>` (a `MinHeap`), loads the config file, validates and registers tasks by user ID, and executes (polls and prints) all pending tasks in priority order.
- **`Main`** — reads the config file path from `args[0]`, then reads one user ID per line from stdin until the `execute` command is received.

## Configuration File Format

```
<priority of user 0>
<priority of user 1>
<priority of user 2>
...
```

Each line defines one user, in order, by their priority value (lower = more important).

## Input Format

```
<userID>      // issues a new task for that user
<userID>
...
execute       // drains and executes all queued tasks, then terminates
```

## Build & Run

```bash
make collect   # gathers all .java sources into sources.txt
make build     # compiles into ./build
make run ARGS="config.txt"   # then provide task input via stdin
make test      # runs against config.txt and test_input.txt automatically
make docs      # generates Javadoc into ./docs
make clean     # removes build/, sources.txt, and docs/
```

## Documentation

Javadoc HTML documentation — including time-complexity notes on every method — is included in [`docs/`](./docs) and can be opened locally via `docs/index.html`.
