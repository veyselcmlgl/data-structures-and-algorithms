# HW3 — Queue-Based Command Processing & Stack-Based Operation Logging

CSE222 Data Structures and Algorithms — Homework 3

The original problem statement is included as [`assignment.pdf`](./assignment.pdf).

## Overview

This assignment extends the [HW1 Hardware Abstraction Layer simulation](../hw1-hardware-abstraction-layer) with two data-structure-driven features built on top of the same board/device/protocol model:

1. **Command buffering with a Queue.** Instead of executing each command as it is typed, all commands are first read from standard input and enqueued into a `Queue<String>` (backed by `LinkedList`). Once the `exit` command is entered, the entire queue is drained and the commands are executed **in the order they were issued** (FIFO).
2. **Per-port operation logging with a Stack.** Every `Protocol` instance (I2C, SPI, UART, OneWire) maintains its own `Stack<String>` of operations (port opened, reads, writes). At program termination, each port's log is flushed to its own file, written in **reverse-chronological order** (most recent operation first) — a natural fit for a stack's LIFO behavior.

The device/protocol class hierarchy itself (abstract `Device`, `Protocol` interface, `Sensor`/`Display`/`WirelessIO`/`MotorDriver` subtypes) is unchanged from HW1; this assignment focuses on wrapping that system with queue-driven execution and stack-driven logging.

## What's New vs. HW1

| Feature | Data Structure | Where |
|---|---|---|
| Commands are collected, then executed in submission order | `Queue<String>` (`LinkedList`) | `Main.java` |
| Each port logs its own operation history, written newest-first | `Stack<String>` | `I2C.java`, `SPI.java`, `UART.java`, `OneWire.java` (via the `Protocol` interface) |
| Full Javadoc comments on public API | — | all source files |

## Build & Run

```bash
make collect
make build
make run ARGS="config.txt ./logs"
make clean
```

The program now requires **two** arguments: the configuration file path and the directory where per-port log files should be written.

## Logging Behavior

When the program exits, one log file is created per port: `<ProtocolName>_<portID>.log` (e.g. `I2C_0.log`). Each file lists that port's operations from most recent to least recent. Sample logs from a run are included in [`logs/`](./logs).

```
# logs/I2C_0.log
Port Opened.
```

## Documentation

Javadoc HTML documentation for the full source tree is included in [`docs/`](./docs) and can be opened locally via `docs/index.html`. It was generated with:

```bash
javadoc -d docs -sourcepath src -subpackages HWSystem:Main
```

## Build & Run Reference

```bash
make collect   # gathers all .java sources into sources.txt
make build     # compiles into ./build
make run ARGS="config.txt ./logs"
make clean     # removes build/ and sources.txt
```
