package HWSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import HWSystem.Protocols.*;
import HWSystem.Devices.*;

/**
 * HWSystem class
 * Main interface for the user to do operations with devices
 */
public class HWSystem {
    private ArrayList<Protocol> ports;
    private ArrayList<Device> devices;
    private String logPath;
    
    // Arrays to store devices by their IDs
    private Sensor[] sensors;
    private Display[] displays;
    private WirelessIO[] wirelessAdapters;
    private MotorDriver[] motorDrivers;
    
    private int maxSensors;
    private int maxDisplays;
    private int maxWirelessAdapters;
    private int maxMotorDrivers;

    /**
     * Constructor for HWSystem
     * @param configPath Path to the configuration file
     * @param logPath Path to the directory where log files will be created
     */
    public HWSystem(String configPath, String logPath) {
        ports = new ArrayList<>();
        devices = new ArrayList<>();
        this.logPath = logPath;
        
        loadConfiguration(configPath);
        
        // Initialize arrays based on configuration
        sensors = new Sensor[maxSensors];
        displays = new Display[maxDisplays];
        wirelessAdapters = new WirelessIO[maxWirelessAdapters];
        motorDrivers = new MotorDriver[maxMotorDrivers];
    }

    /**
     * Loads the system configuration from a file
     * @param configPath Path to the configuration file
     */
    private void loadConfiguration(String configPath) {
        try {
            File configFile = new File(configPath);
            Scanner scanner = new Scanner(configFile);

            // Read port configuration
            String portConfig = scanner.nextLine().split(": ")[1];
            String[] portTypes = portConfig.split(",");
            
            for (int i = 0; i < portTypes.length; i++) {
                String portType = portTypes[i].trim();
                switch (portType) {
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
                        System.err.println("Unknown port type: " + portType);
                }
            }

            // Read device limits
            maxSensors = Integer.parseInt(scanner.nextLine().split(":")[1]);
            maxDisplays = Integer.parseInt(scanner.nextLine().split(":")[1]);
            maxWirelessAdapters = Integer.parseInt(scanner.nextLine().split(":")[1]);
            maxMotorDrivers = Integer.parseInt(scanner.nextLine().split(":")[1]);

            scanner.close();
        } catch (Exception e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }

    /**
     * Add a device to the system
     * @param devName Device name
     * @param portID Port ID
     * @param devID Device ID
     */
    public void addDevice(String devName, int portID, int devID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID.");
            return;
        }

        // Check if the port is already occupied
        if (isPortOccupied(portID)) {
            System.err.println("Port is already occupied.");
            return;
        }

        Protocol protocol = ports.get(portID);
        
        try {
            Device device = null;
            
            // Handle different device types
            if (isSensorType(devName)) {
                if (devID < 0 || devID >= maxSensors) {
                    System.err.println("Invalid sensor ID.");
                    return;
                }
                if (sensors[devID] != null) {
                    System.err.println("Sensor ID already in use.");
                    return;
                }
                Sensor sensor = createSensor(devName, protocol);
                if (sensor != null) {
                    sensors[devID] = sensor;
                    device = sensor;
                }
            } 
            else if (isDisplayType(devName)) {
                if (devID < 0 || devID >= maxDisplays) {
                    System.err.println("Invalid display ID.");
                    return;
                }
                if (displays[devID] != null) {
                    System.err.println("Display ID already in use.");
                    return;
                }
                Display display = createDisplay(devName, protocol);
                if (display != null) {
                    displays[devID] = display;
                    device = display;
                }
            } 
            else if (isWirelessType(devName)) {
                if (devID < 0 || devID >= maxWirelessAdapters) {
                    System.err.println("Invalid wireless adapter ID.");
                    return;
                }
                if (wirelessAdapters[devID] != null) {
                    System.err.println("Wireless adapter ID already in use.");
                    return;
                }
                WirelessIO wireless = createWireless(devName, protocol);
                if (wireless != null) {
                    wirelessAdapters[devID] = wireless;
                    device = wireless;
                }
            } 
            else if (isMotorDriverType(devName)) {
                if (devID < 0 || devID >= maxMotorDrivers) {
                    System.err.println("Invalid motor driver ID.");
                    return;
                }
                if (motorDrivers[devID] != null) {
                    System.err.println("Motor driver ID already in use.");
                    return;
                }
                MotorDriver motor = createMotorDriver(devName, protocol);
                if (motor != null) {
                    motorDrivers[devID] = motor;
                    device = motor;
                }
            } 
            else {
                System.err.println("Unknown device: " + devName);
                return;
            }
            
            if (device != null) {
                devices.add(device);
                System.out.println("Device added.");
                System.out.flush();
            }
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding device: " + e.getMessage());
        }
    }
    
    /**
     * Check if device is a sensor type
     * @param devName Device name
     * @return True if the device is a sensor type
     */
    private boolean isSensorType(String devName) {
        return devName.equals("DHT11") || devName.equals("BME280") || 
               devName.equals("MPU6050") || devName.equals("GY951");
    }
    
    /**
     * Check if device is a display type
     * @param devName Device name
     * @return True if the device is a display type
     */
    private boolean isDisplayType(String devName) {
        return devName.equals("LCD") || devName.equals("OLED");
    }
    
    /**
     * Check if device is a wireless type
     * @param devName Device name
     * @return True if the device is a wireless type
     */
    private boolean isWirelessType(String devName) {
        return devName.equals("Bluetooth") || devName.equals("Wifi");
    }
    
    /**
     * Check if device is a motor driver type
     * @param devName Device name
     * @return True if the device is a motor driver type
     */
    private boolean isMotorDriverType(String devName) {
        return devName.equals("PCA9685") || devName.equals("SparkFunMD");
    }
    
    /**
     * Factory method to create a sensor based on its name
     * @param devName Sensor name
     * @param protocol Protocol to use
     * @return Sensor object
     */
    private Sensor createSensor(String devName, Protocol protocol) {
        switch (devName) {
            case "DHT11":
                return new DHT11(protocol);
            case "BME280":
                return new BME280(protocol);
            case "MPU6050":
                return new MPU6050(protocol);
            case "GY951":
                return new GY951(protocol);
            default:
                return null;
        }
    }
    
    /**
     * Factory method to create a display based on its name
     * @param devName Display name
     * @param protocol Protocol to use
     * @return Display object
     */
    private Display createDisplay(String devName, Protocol protocol) {
        switch (devName) {
            case "LCD":
                return new LCD(protocol);
            case "OLED":
                return new OLED(protocol);
            default:
                return null;
        }
    }
    
    /**
     * Factory method to create a wireless adapter based on its name
     * @param devName Wireless adapter name
     * @param protocol Protocol to use
     * @return WirelessIO object
     */
    private WirelessIO createWireless(String devName, Protocol protocol) {
        switch (devName) {
            case "Bluetooth":
                return new Bluetooth(protocol);
            case "Wifi":
                return new Wifi(protocol);
            default:
                return null;
        }
    }
    
    /**
     * Factory method to create a motor driver based on its name
     * @param devName Motor driver name
     * @param protocol Protocol to use
     * @return MotorDriver object
     */
    private MotorDriver createMotorDriver(String devName, Protocol protocol) {
        switch (devName) {
            case "PCA9685":
                return new PCA9685(protocol);
            case "SparkFunMD":
                return new SparkFunMD(protocol);
            default:
                return null;
        }
    }
    
    /**
     * Check if a port is occupied by a device
     * @param portID Port ID
     * @return True if the port is occupied
     */
    private boolean isPortOccupied(int portID) {
        Protocol port = ports.get(portID);
        Iterator<Device> iterator = devices.iterator();
        while (iterator.hasNext()) {
            Device device = iterator.next();
            if (device.protocol == port) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Remove a device from the system
     * @param portID Port ID of the device to remove
     */
    public void removeDevice(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Protocol port = ports.get(portID);
        Device deviceToRemove = null;
        
        Iterator<Device> iterator = devices.iterator();
        while (iterator.hasNext()) {
            Device device = iterator.next();
            if (device.protocol == port) {
                deviceToRemove = device;
                break;
            }
        }
        
        // If no device is connected to this port
        if (deviceToRemove == null) {
            System.err.println("Port is empty.");
            return;
        }
        
        // Check if the device is ON
        if (deviceToRemove.getState() == State.ON) {
            System.err.println("Device is active.");
            System.err.println("Command failed.");
            return;
        }
        
        // Remove from appropriate array
        removeFromTypeArray(deviceToRemove);
        
        // Remove from main devices list
        devices.remove(deviceToRemove);
        
        System.out.println("Device removed.");
        System.out.flush();
    }
    
    /**
     * Helper method to remove device from its type-specific array
     * @param device Device to remove
     */
    private void removeFromTypeArray(Device device) {
        String devType = device.getDevType();
        if (devType.contains("Sensor")) {
            for (int i = 0; i < sensors.length; i++) {
                if (sensors[i] == device) {
                    sensors[i] = null;
                    break;
                }
            }
        } else if (devType.equals("Display")) {
            for (int i = 0; i < displays.length; i++) {
                if (displays[i] == device) {
                    displays[i] = null;
                    break;
                }
            }
        } else if (devType.equals("WirelessIO")) {
            for (int i = 0; i < wirelessAdapters.length; i++) {
                if (wirelessAdapters[i] == device) {
                    wirelessAdapters[i] = null;
                    break;
                }
            }
        } else if (devType.equals("MotorDriver")) {
            for (int i = 0; i < motorDrivers.length; i++) {
                if (motorDrivers[i] == device) {
                    motorDrivers[i] = null;
                    break;
                }
            }
        }
    }
    
    /**
     * Turn a device ON
     * @param portID Port ID of the device to turn on
     */
    public void turnDeviceON(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Protocol port = ports.get(portID);
        Device deviceToTurnOn = null;
        
        Iterator<Device> iterator = devices.iterator();
        while (iterator.hasNext()) {
            Device device = iterator.next();
            if (device.protocol == port) {
                deviceToTurnOn = device;
                break;
            }
        }
        
        // If no device is connected to this port
        if (deviceToTurnOn == null) {
            System.err.println("Port is empty.");
            return;
        }
        
        // Turn the device ON
        deviceToTurnOn.turnON();
    }
    
    /**
     * Turn a device OFF
     * @param portID Port ID of the device to turn off
     */
    public void turnDeviceOFF(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID.");
            return;
        }
        
        // Find the device connected to this port
        Protocol port = ports.get(portID);
        Device deviceToTurnOff = null;
        
        Iterator<Device> iterator = devices.iterator();
        while (iterator.hasNext()) {
            Device device = iterator.next();
            if (device.protocol == port) {
                deviceToTurnOff = device;
                break;
            }
        }
        
        // If no device is connected to this port
        if (deviceToTurnOff == null) {
            System.err.println("Port is empty.");
            return;
        }
        
        // Turn the device OFF
        deviceToTurnOff.turnOFF();
    }
    
    /**
     * List all ports and their status
     */
    public void listPorts() {
        System.out.println("list of ports:");
        System.out.flush();
        for (int i = 0; i < ports.size(); i++) {
            Protocol port = ports.get(i);
            String protocolType = port.getProtocolName();
            
            // Find if there's a device connected to this port
            Device connectedDevice = null;
            Iterator<Device> iterator = devices.iterator();
            while (iterator.hasNext()) {
                Device device = iterator.next();
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
            System.out.flush();
        }
    }
    
    /**
     * Find the ID of a device in its specific array
     * @param device Device to find
     * @return Device ID or -1 if not found
     */
    private int getDeviceID(Device device) {
        String devType = device.getDevType();
        if (devType.contains("Sensor")) {
            for (int i = 0; i < sensors.length; i++) {
                if (sensors[i] == device) {
                    return i;
                }
            }
        } else if (devType.equals("Display")) {
            for (int i = 0; i < displays.length; i++) {
                if (displays[i] == device) {
                    return i;
                }
            }
        } else if (devType.equals("WirelessIO")) {
            for (int i = 0; i < wirelessAdapters.length; i++) {
                if (wirelessAdapters[i] == device) {
                    return i;
                }
            }
        } else if (devType.equals("MotorDriver")) {
            for (int i = 0; i < motorDrivers.length; i++) {
                if (motorDrivers[i] == device) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    /**
     * Find the port ID for a device
     * @param device Device to find
     * @return Port ID or -1 if not found
     */
    private int getPortID(Device device) {
        for (int i = 0; i < ports.size(); i++) {
            if (device.protocol == ports.get(i)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * List devices of a specific type
     * @param devType Device type to list
     */
    public void listDeviceType(String devType) {
        System.out.println("list of " + devType + "s:");
        System.out.flush();
        Device[] deviceArray;
        switch (devType) {
            case "Sensor":
                deviceArray = sensors;
                break;
            case "Display":
                deviceArray = displays;
                break;
            case "WirelessIO":
                deviceArray = wirelessAdapters;
                break;
            case "MotorDriver":
                deviceArray = motorDrivers;
                break;
            default:
                System.err.println("Unknown device type: " + devType);
                return;
        }
        
        for (int i = 0; i < deviceArray.length; i++) {
            Device device = deviceArray[i];
            if (device != null) {
                int portID = getPortID(device);
                System.out.println(device.getName() + " " + i + " " + portID + " " + device.protocol.getProtocolName());
                System.out.flush();
            }
        }
    }
    
    /**
     * Read sensor data
     * @param devID Sensor ID
     */
    public void readSensor(int devID) {
        // Check if sensor ID is valid
        if (devID < 0 || devID >= sensors.length || sensors[devID] == null) {
            System.err.println("Invalid sensor ID.");
            return;
        }
        
        Sensor sensor = sensors[devID];
        
        // Check if the sensor is ON
        if (sensor.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }
        
        // Read and display sensor data using the polymorphic data2String method
        System.out.println(sensor.getName() + " " + sensor.getDevType() + ": " + sensor.data2String() + ".");
        System.out.flush();

    }
    
    /**
     * Print data to display
     * @param devID Display ID
     * @param data Data to print
     */
    public void printDisplay(int devID, String data) {
        // Check if display ID is valid
        if (devID < 0 || devID >= displays.length || displays[devID] == null) {
            System.err.println("Invalid display ID.");
            return;
        }
        
        Display display = displays[devID];
        
        // Check if the display is ON
        if (display.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }
        
        // Print data to the display
        System.out.println(display.getName() + ": Printing \"" + data + "\".");
        System.out.flush();
        display.printData(data);
    }
    
    /**
     * Read from wireless adapter
     * @param devID Wireless adapter ID
     */
    public void readWireless(int devID) {
        // Check if wireless adapter ID is valid
        if (devID < 0 || devID >= wirelessAdapters.length || wirelessAdapters[devID] == null) {
            System.err.println("Invalid wireless adapter ID.");
            return;
        }
        
        WirelessIO wireless = wirelessAdapters[devID];
        
        // Check if the wireless adapter is ON
        if (wireless.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }
        
        // Read data from the wireless adapter
        System.out.println(wireless.getName() + ": Received \"Some Data\".");
        System.out.flush();
        wireless.recvData();
    }
    
    /**
     * Write to wireless adapter
     * @param devID Wireless adapter ID
     * @param data Data to send
     */
    public void writeWireless(int devID, String data) {
        // Check if wireless adapter ID is valid
        if (devID < 0 || devID >= wirelessAdapters.length || wirelessAdapters[devID] == null) {
            System.err.println("Invalid wireless adapter ID.");
            return;
        }
        
        WirelessIO wireless = wirelessAdapters[devID];
        
        // Check if the wireless adapter is ON
        if (wireless.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }
        
        // Send data to the wireless adapter
        System.out.println(wireless.getName() + ": Sending \"" + data + "\".");
        System.out.flush();
        wireless.sendData(data);
    }
    
    /**
     * Set motor speed
     * @param devID Motor driver ID
     * @param speed Speed to set
     */
    public void setMotorSpeed(int devID, int speed) {
        // Check if motor driver ID is valid
        if (devID < 0 || devID >= motorDrivers.length || motorDrivers[devID] == null) {
            System.err.println("Invalid motor driver ID.");
            return;
        }
        
        MotorDriver motor = motorDrivers[devID];
        
        // Check if the motor driver is ON
        if (motor.getState() == State.OFF) {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return;
        }
        
        // Set the motor speed
        System.out.println(motor.getName() + ": Setting speed to " + speed + ".");
        System.out.flush();
        motor.setMotorSpeed(speed);
    }
    
    /**
     * Write logs from all ports to files
     */
    public void writeLogs() {
        for (int i = 0; i < ports.size(); i++) {
            Protocol port = ports.get(i);
            port.writeLogToFile(logPath, i);
        }
    }
}