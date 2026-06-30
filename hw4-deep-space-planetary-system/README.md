# HW4 — Deep Space Planetary System Analysis

CSE222 / BİL505 Data Structures and Algorithms — Homework 4

The original problem statement is included as [`assignment.pdf`](./assignment.pdf), and a full written report is included as [`report.pdf`](./report.pdf).

## Overview

A terminal application that models a discovered planetary system as a general (N-ary) tree: the star sits at the root, planets are its children, and each planet's moons are its children in turn. Every node — star, planet, or moon — carries a name, a type, and a `SensorData` reading (temperature, pressure, humidity, radiation). The program builds this tree interactively from user commands and supports recursive analysis over it.

## Design

- **`SpaceNode`** — a tree node holding a name, type, `SensorData`, and a `List<SpaceNode>` of children. Any node can have an arbitrary number of children, which is what makes the structure a general tree rather than a binary tree.
- **`SensorData`** — a simple data holder for temperature (K), pressure (Pa), humidity (%), and radiation (Sv), with input validation (e.g. humidity is rejected outside 0–100, stars cannot have humidity).
- **`DeepSpacePlanetarySystem`** — owns the root of the tree, parses terminal commands, and implements all tree operations recursively.

## Commands

```
create planetSystem "name" "temperature" "pressure" "humidity" "radiation"
addPlanet "planetName" "parentName" "temperature" "pressure" "humidity" "radiation"
addSatellite "satelliteName" "parentName" "temperature" "pressure" "humidity" "radiation"
findRadiationAnomalies "threshold"
getPathTo "nodeName"
printMissionReport
printMissionReport "nodeName"
exit
```

## Recursive Tree Operations

- **`findRadiationAnomalies`** — recursively walks every node in the tree and collects all nodes whose radiation reading exceeds the given threshold, regardless of depth.
- **`getPathTo`** — recursively searches the tree for a target node and builds the root-to-node path using a `Stack<String>`: each recursive call pushes its own node name only once the target has been found further down the tree, so popping the stack naturally yields the path from the target back up to the root.
- **`printMissionReport`** — recursively renders the whole tree (or a single node) as an indented, branch-prefixed report (`└──`), showing each node's sensor readings in their proper units.

## Build & Run

```bash
make collect   # gathers all .java sources into sources.txt
make build     # compiles into ./build
make run       # starts the interactive terminal UI
make test      # runs the program against test_commands.txt (batch scenario)
make docs      # generates Javadoc into ./docs
make clean     # removes build/, sources.txt, and docs/
```

## Test Scenario

[`test_commands.txt`](./test_commands.txt) contains a scripted scenario that builds out a multi-level solar system (star → planets → moons, several levels deep), then exercises edge cases: duplicate names, invalid sensor values (negative temperature/pressure, out-of-range humidity), references to non-existent parents, and lookups for nodes that don't exist — before running `findRadiationAnomalies`, `getPathTo`, and `printMissionReport` against the resulting tree.

## Documentation

Javadoc HTML documentation for the full source tree is included in [`docs/`](./docs) and can be opened locally via `docs/index.html`.
