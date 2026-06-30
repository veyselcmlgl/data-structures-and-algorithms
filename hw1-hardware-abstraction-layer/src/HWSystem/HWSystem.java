package HWSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import HWSystem.Protocols.*;
import HWSystem.Devices.*;
import HWSystem.Devices.State;

public class HWSystem {
    private ArrayList<Protocol> ports;
    private ArrayList<Device> devices;
    private ArrayList<Sensor> sensors;
    private ArrayList<Display> displays;
    private ArrayList<WirelessIO> wirelessAdapters;
    private ArrayList<MotorDriver> motorDrivers;
    
    private int maxSensors;
    private int maxDisplays;
    private int maxWirelessAdapters;
    private int maxMotorDrivers;

    public HWSystem(String configPath) {
        ports = new ArrayList<>();
        devices = new ArrayList<>();
        sensors = new ArrayList<>();
        displays = new ArrayList<>();
        wirelessAdapters = new ArrayList<>();
        motorDrivers = new ArrayList<>();
        
        loadConfiguration(configPath);
    }

    private void loadConfiguration(String configPath) {
        try {
            File configFile = new File(configPath);
            Scanner scanner = new Scanner(configFile);

            // Read port configuration
            String portConfig = scanner.nextLine().split(": ")[1];
            String[] portTypes = portConfig.split(",");
            
            for (String portType : portTypes) {
                switch (portType.trim()) {
                    case "I2C":
                        ports.add(new I2C());
                        break;
                    case "SPI":
                        ports.add(new SPI());
                        break;
                    case "UART":
                        ports.add(new UART());
                        break;
                    case "OneWire":
                        ports.add(new OneWire());
                        break;
                    default:
                        System.out.println("Unknown port type: " + portType);
                }
            }

            // Read device limits
            maxSensors = Integer.parseInt(scanner.nextLine().split(":")[1]);
            maxDisplays = Integer.parseInt(scanner.nextLine().split(":")[1]);
            maxWirelessAdapters = Integer.parseInt(scanner.nextLine().split(":")[1]);
            maxMotorDrivers = Integer.parseInt(scanner.nextLine().split(":")[1]);

            scanner.close();
        } catch (Exception e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add a device to the system
    public void addDevice(String devName, int portID, int devID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }

        // Check if the port is already occupied
        if (isPortOccupied(portID)) {
            System.out.println("Port is already occupied.");
            return;
        }

        Protocol protocol = ports.get(portID);
        
        try {
            Device device = null;
            
            // Create the appropriate device based on the device name
            switch (devName) {
                case "DHT11":
                    if (sensors.size() >= maxSensors) {
                        System.out.println("Maximum number of sensors reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.out.println("Invalid sensor ID.");
                        return;
                    }
                    if (isDeviceIDUsed(sensors, devID)) {
                        System.out.println("Sensor ID already in use.");
                        return;
                    }
                    device = new DHT11(protocol);
                    sensors.add((Sensor) device);
                    break;
                case "BME280":
                    if (sensors.size() >= maxSensors) {
                        System.out.println("Maximum number of sensors reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.out.println("Invalid sensor ID.");
                        return;
                    }
                    if (isDeviceIDUsed(sensors, devID)) {
                        System.out.println("Sensor ID already in use.");
                        return;
                    }
                    device = new BME280(protocol);
                    sensors.add((Sensor) device);
                    break;
                case "MPU6050":
                    if (sensors.size() >= maxSensors) {
                        System.out.println("Maximum number of sensors reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.out.println("Invalid sensor ID.");
                        return;
                    }
                    if (isDeviceIDUsed(sensors, devID)) {
                        System.out.println("Sensor ID already in use.");
                        return;
                    }
                    device = new MPU6050(protocol);
                    sensors.add((Sensor) device);
                    break;
                case "GY951":
                    if (sensors.size() >= maxSensors) {
                        System.out.println("Maximum number of sensors reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.out.println("Invalid sensor ID.");
                        return;
                    }
                    if (isDeviceIDUsed(sensors, devID)) {
                        System.out.println("Sensor ID already in use.");
                        return;
                    }
                    device = new GY951(protocol);
                    sensors.add((Sensor) device);
                    break;
                case "LCD":
                    if (displays.size() >= maxDisplays) {
                        System.out.println("Maximum number of displays reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxDisplays) {
                        System.out.println("Invalid display ID.");
                        return;
                    }
                    if (isDeviceIDUsed(displays, devID)) {
                        System.out.println("Display ID already in use.");
                        return;
                    }
                    device = new LCD(protocol);
                    displays.add((Display) device);
                    break;
                case "OLED":
                    if (displays.size() >= maxDisplays) {
                        System.out.println("Maximum number of displays reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxDisplays) {
                        System.out.println("Invalid display ID.");
                        return;
                    }
                    if (isDeviceIDUsed(displays, devID)) {
                        System.out.println("Display ID already in use.");
                        return;
                    }
                    device = new OLED(protocol);
                    displays.add((Display) device);
                    break;
                case "Bluetooth":
                    if (wirelessAdapters.size() >= maxWirelessAdapters) {
                        System.out.println("Maximum number of wireless adapters reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxWirelessAdapters) {
                        System.out.println("Invalid wireless adapter ID.");
                        return;
                    }
                    if (isDeviceIDUsed(wirelessAdapters, devID)) {
                        System.out.println("Wireless adapter ID already in use.");
                        return;
                    }
                    device = new Bluetooth(protocol);
                    wirelessAdapters.add((WirelessIO) device);
                    break;
                case "Wifi":
                    if (wirelessAdapters.size() >= maxWirelessAdapters) {
                        System.out.println("Maximum number of wireless adapters reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxWirelessAdapters) {
                        System.out.println("Invalid wireless adapter ID.");
                        return;
                    }
                    if (isDeviceIDUsed(wirelessAdapters, devID)) {
                        System.out.println("Wireless adapter ID already in use.");
                        return;
                    }
                    device = new Wifi(protocol);
                    wirelessAdapters.add((WirelessIO) device);
                    break;
                case "PCA9685":
                    if (motorDrivers.size() >= maxMotorDrivers) {
                        System.out.println("Maximum number of motor drivers reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxMotorDrivers) {
                        System.out.println("Invalid motor driver ID.");
                        return;
                    }
                    if (isDeviceIDUsed(motorDrivers, devID)) {
                        System.out.println("Motor driver ID already in use.");
                        return;
                    }
                    device = new PCA9685(protocol);
                    motorDrivers.add((MotorDriver) device);
                    break;
                case "SparkFunMD":
                    if (motorDrivers.size() >= maxMotorDrivers) {
                        System.out.println("Maximum number of motor drivers reached.");
                        return;
                    }
                    if (devID < 0 || devID >= maxMotorDrivers) {
                        System.out.println("Invalid motor driver ID.");
                        return;
                    }
                    if (isDeviceIDUsed(motorDrivers, devID)) {
                        System.out.println("Motor driver ID already in use.");
                        return;
                    }
                    device = new SparkFunMD(protocol);
                    motorDrivers.add((MotorDriver) device);
                    break;
                default:
                    System.out.println("Unknown device: " + devName);
                    return;
            }
            
            devices.add(device);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding device: " + e.getMessage());
        }
    }
    
    // Check if a port is occupied by a device
    private boolean isPortOccupied(int portID) {
        Protocol port = ports.get(portID);
        for (Device device : devices) {
            if (device.protocol == port) {
                return true;
            }
        }
        return false;
    }
    
    // Check if a device ID is already used in a specific device list
    private <T extends Device> boolean isDeviceIDUsed(ArrayList<T> deviceList, int devID) {
        return deviceList.size() > devID && deviceList.get(devID) != null;
    }
    
    // Remove a device from the system
    public void removeDevice(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Protocol port = ports.get(portID);
        Device deviceToRemove = null;
        
        for (Device device : devices) {
            if (device.protocol == port) {
                deviceToRemove = device;
                break;
            }
        }
        
        // If no device is connected to this port
        if (deviceToRemove == null) {
            System.out.println("Port is empty.");
            return;
        }
        
        // Check if the device is ON
        if (deviceToRemove.getState() == State.ON) {
            System.out.println("Device is active.");
            System.out.println("Command failed.");
            return;
        }
        
        // Remove the device from the appropriate list
        if (deviceToRemove instanceof Sensor) {
            sensors.remove(deviceToRemove);
        } else if (deviceToRemove instanceof Display) {
            displays.remove(deviceToRemove);
        } else if (deviceToRemove instanceof WirelessIO) {
            wirelessAdapters.remove(deviceToRemove);
        } else if (deviceToRemove instanceof MotorDriver) {
            motorDrivers.remove(deviceToRemove);
        }
        
        // Remove from the main devices list
        devices.remove(deviceToRemove);
    }
    
    // Turn a device ON
    public void turnDeviceON(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Protocol port = ports.get(portID);
        Device deviceToTurnOn = null;
        
        for (Device device : devices) {
            if (device.protocol == port) {
                deviceToTurnOn = device;
                break;
            }
        }
        
        // If no device is connected to this port
        if (deviceToTurnOn == null) {
            System.out.println("Port is empty.");
            return;
        }
        
        // Turn the device ON
        deviceToTurnOn.turnON();
    }
    
    // Turn a device OFF
    public void turnDeviceOFF(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Protocol port = ports.get(portID);
        Device deviceToTurnOff = null;
        
        for (Device device : devices) {
            if (device.protocol == port) {
                deviceToTurnOff = device;
                break;
            }
        }
        
        // If no device is connected to this port
        if (deviceToTurnOff == null) {
            System.out.println("Port is empty.");
            return;
        }
        
        // Turn the device OFF
        deviceToTurnOff.turnOFF();
    }
    
    // List all ports and their status
    public void listPorts() {
        System.out.println("list of ports:");
        for (int i = 0; i < ports.size(); i++) {
            Protocol port = ports.get(i);
            String protocolType = port.getProtocolName();
            
            // Find if there's a device connected to this port
            Device connectedDevice = null;
            for (Device device : devices) {
                if (device.protocol == port) {
                    connectedDevice = device;
                    break;
                }
            }
            
            if (connectedDevice == null) {
                System.out.println(i + " " + protocolType + " empty");
            } else {
                System.out.println(i + " " + protocolType + " occupied " + 
                                  connectedDevice.getName() + " " + 
                                  connectedDevice.getDevType() + " " + 
                                  getDeviceID(connectedDevice) + " " + 
                                  connectedDevice.getState());
            }
        }
    }
    
    // Find the ID of a device in its specific list
    private int getDeviceID(Device device) {
        if (device instanceof Sensor) {
            return sensors.indexOf(device);
        } else if (device instanceof Display) {
            return displays.indexOf(device);
        } else if (device instanceof WirelessIO) {
            return wirelessAdapters.indexOf(device);
        } else if (device instanceof MotorDriver) {
            return motorDrivers.indexOf(device);
        }
        return -1;
    }
    
    // Find the port ID for a device
    private int getPortID(Device device) {
        for (int i = 0; i < ports.size(); i++) {
            if (device.protocol == ports.get(i)) {
                return i;
            }
        }
        return -1;
    }
    
    // List devices of a specific type
    public void listDeviceType(String devType) {
        ArrayList<? extends Device> deviceList;
        
        // Determine which list to use based on devType
        switch (devType) {
            case "Sensor":
                deviceList = sensors;
                break;
            case "Display":
                deviceList = displays;
                break;
            case "WirelessIO":
                deviceList = wirelessAdapters;
                break;
            case "MotorDriver":
                deviceList = motorDrivers;
                break;
            default:
                System.out.println("Unknown device type: " + devType);
                return;
        }
        
        System.out.println("list of " + devType + "s:");
        for (Device device : deviceList) {
            int devID = getDeviceID(device);
            int portID = getPortID(device);
            System.out.println(device.getName() + " " + devID + " " + portID + " " + device.protocol.getProtocolName());
        }
    }
    
    // Read sensor data
    public void readSensor(int devID) {
        // Check if sensor ID is valid
        if (devID < 0 || devID >= sensors.size()) {
            System.out.println("Invalid sensor ID.");
            return;
        }
        
        Sensor sensor = sensors.get(devID);
        
        // Check if the sensor is ON
        if (sensor.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        // Read and display sensor data
        System.out.println(sensor.getName() + " " + sensor.getDevType() + ": " + sensor.data2String());
    }
    
    // Print data to display
    public void printDisplay(int devID, String data) {
        // Check if display ID is valid
        if (devID < 0 || devID >= displays.size()) {
            System.out.println("Invalid display ID.");
            return;
        }
        
        Display display = displays.get(devID);
        
        // Print data to the display
        display.printData(data);
    }
    
    // Read from wireless adapter
    public void readWireless(int devID) {
        // Check if wireless adapter ID is valid
        if (devID < 0 || devID >= wirelessAdapters.size()) {
            System.out.println("Invalid wireless adapter ID.");
            return;
        }
        
        WirelessIO wireless = wirelessAdapters.get(devID);
        
        // Read data from the wireless adapter
        wireless.recvData();
    }
    
    // Write to wireless adapter
    public void writeWireless(int devID, String data) {
        // Check if wireless adapter ID is valid
        if (devID < 0 || devID >= wirelessAdapters.size()) {
            System.out.println("Invalid wireless adapter ID.");
            return;
        }
        
        WirelessIO wireless = wirelessAdapters.get(devID);
        
        // Send data to the wireless adapter
        wireless.sendData(data);
    }
    
    // Set motor speed
    public void setMotorSpeed(int devID, int speed) {
        // Check if motor driver ID is valid
        if (devID < 0 || devID >= motorDrivers.size()) {
            System.out.println("Invalid motor driver ID.");
            return;
        }
        
        MotorDriver motor = motorDrivers.get(devID);
        
        // Set the motor speed
        motor.setMotorSpeed(speed);
    }
}