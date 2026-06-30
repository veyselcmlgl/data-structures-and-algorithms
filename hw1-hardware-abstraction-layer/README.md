# HW1 — Hardware Abstraction Layer (HAL) Simulation

CSE222 Data Structures and Algorithms — Homework 1

## Overview

A console-based simulation of an embedded hardware abstraction layer. The system models a microcontroller-like board with a fixed set of communication ports (I2C, SPI, UART, OneWire), onto which different peripheral devices (sensors, displays, wireless adapters, motor drivers) can be attached, queried, and controlled at runtime through a command interpreter.

The project is built around polymorphism and interface-based design rather than a single custom data structure: each device category is modeled as an abstract base class with concrete implementations, and each communication protocol is modeled as an interface, so that the core `HWSystem` class can manage heterogeneous devices uniformly through their common supertypes.

## Design

- **`Protocol` (interface)** — `I2C`, `SPI`, `UART`, `OneWire`. Each port on the board is a `Protocol` instance.
- **`Device` (abstract class)** — base type for everything pluggable into a port; tracks ON/OFF `State` and exposes `turnON()` / `turnOFF()`.
  - **`Sensor`** → `TempSensor` (`DHT11`, `BME280`), `IMUSensor` (`MPU6050`, `GY951`)
  - **`Display`** → `LCD`, `OLED`
  - **`WirelessIO`** → `Bluetooth`, `Wifi`
  - **`MotorDriver`** → `PCA9685`, `SparkFunMD`
- **`HWSystem`** — owns the list of ports and devices, loads the board layout from a config file, enforces per-category device limits and ID uniqueness, and exposes the operations below.
- **`Main`** — reads a config file path from `args[0]`, then runs an interactive command loop.

## Supported Commands

```
turnON <portID>
turnOFF <portID>
addDev <devName> <portID> <devID>
rmDev <portID>
list ports | Sensor | Display | WirelessIO | MotorDriver
readSensor <devID>
printDisplay <devID> <String>
readWireless <devID>
writeWireless <devID> <String>
setMotorSpeed <devID> <integer>
exit
```

## Configuration File Format

```
Port Configuration: I2C,SPI,OneWire,UART
# of sensors:1
# of displays:2
# of wireless adapters:2
# of motor drivers:2
```

The first line defines the ports available on the board (in order, so port `0` is the first listed protocol, etc.). The remaining lines cap how many devices of each category may be attached.

## Build & Run

```bash
make collect   # gathers all .java sources into sources.txt
make build     # compiles into ./build
make run ARGS=config.txt
make clean     # removes build/ and sources.txt
```

## Example Session

```
Command: addDev DHT11 0 0
Command: turnON 0
Command: readSensor 0
DHT11 TempSensor: ...
Command: list ports
list of ports:
0 I2C occupied DHT11 TempSensor 0 ON
1 SPI empty
...
Command: exit
```
