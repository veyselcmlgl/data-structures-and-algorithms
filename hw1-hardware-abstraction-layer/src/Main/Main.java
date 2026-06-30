package Main;

import HWSystem.HWSystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Check if configuration file path is provided
        if (args.length < 1) {
            System.out.println("Please provide a configuration file path.");
            return;
        }
        
        String configPath = args[0];
        HWSystem system = new HWSystem(configPath);
        
        Scanner scanner = new Scanner(System.in);
        String command;
        boolean running = true;
        
        while (running) {
            System.out.print("Command: ");
            command = scanner.nextLine().trim();
            
            // Parse the command
            String[] parts = command.split(" ");
            String operation = parts[0];
            
            try {
                switch (operation) {
                    case "turnON":
                        if (parts.length < 2) {
                            System.out.println("Usage: turnON <portID>");
                            break;
                        }
                        int portID = Integer.parseInt(parts[1]);
                        system.turnDeviceON(portID);
                        break;
                        
                    case "turnOFF":
                        if (parts.length < 2) {
                            System.out.println("Usage: turnOFF <portID>");
                            break;
                        }
                        portID = Integer.parseInt(parts[1]);
                        system.turnDeviceOFF(portID);
                        break;
                        
                    case "addDev":
                        if (parts.length < 4) {
                            System.out.println("Usage: addDev <devName> <portID> <devID>");
                            break;
                        }
                        String devName = parts[1];
                        portID = Integer.parseInt(parts[2]);
                        int devID = Integer.parseInt(parts[3]);
                        system.addDevice(devName, portID, devID);
                        break;
                        
                    case "rmDev":
                        if (parts.length < 2) {
                            System.out.println("Usage: rmDev <portID>");
                            break;
                        }
                        portID = Integer.parseInt(parts[1]);
                        system.removeDevice(portID);
                        break;
                        
                    case "list":
                        if (parts.length < 2) {
                            System.out.println("Usage: list ports|Sensor|Display|WirelessIO|MotorDriver");
                            break;
                        }
                        
                        String listType = parts[1];
                        if (listType.equals("ports")) {
                            system.listPorts();
                        } else {
                            system.listDeviceType(listType);
                        }
                        break;
                        
                    case "readSensor":
                        if (parts.length < 2) {
                            System.out.println("Usage: readSensor <devID>");
                            break;
                        }
                        devID = Integer.parseInt(parts[1]);
                        system.readSensor(devID);
                        break;
                        
                    case "printDisplay":
                        if (parts.length < 3) {
                            System.out.println("Usage: printDisplay <devID> <String>");
                            break;
                        }
                        devID = Integer.parseInt(parts[1]);
                        
                        // Combine all remaining parts as the string to print
                        StringBuilder dataBuilder = new StringBuilder();
                        for (int i = 2; i < parts.length; i++) {
                            dataBuilder.append(parts[i]);
                            if (i < parts.length - 1) {
                                dataBuilder.append(" ");
                            }
                        }
                        system.printDisplay(devID, dataBuilder.toString());
                        break;
                        
                    case "readWireless":
                        if (parts.length < 2) {
                            System.out.println("Usage: readWireless <devID>");
                            break;
                        }
                        devID = Integer.parseInt(parts[1]);
                        system.readWireless(devID);
                        break;
                        
                    case "writeWireless":
                        if (parts.length < 3) {
                            System.out.println("Usage: writeWireless <devID> <String>");
                            break;
                        }
                        devID = Integer.parseInt(parts[1]);
                        
                        // Combine all remaining parts as the string to send
                        dataBuilder = new StringBuilder();
                        for (int i = 2; i < parts.length; i++) {
                            dataBuilder.append(parts[i]);
                            if (i < parts.length - 1) {
                                dataBuilder.append(" ");
                            }
                        }
                        system.writeWireless(devID, dataBuilder.toString());
                        break;
                        
                    case "setMotorSpeed":
                        if (parts.length < 3) {
                            System.out.println("Usage: setMotorSpeed <devID> <integer>");
                            break;
                        }
                        devID = Integer.parseInt(parts[1]);
                        int speed = Integer.parseInt(parts[2]);
                        system.setMotorSpeed(devID, speed);
                        break;
                        
                    case "exit":
                        running = false;
                        break;
                        
                    default:
                        System.out.println("Unknown command: " + operation);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please check your input.");
            } catch (Exception e) {
                System.out.println("Error executing command: " + e.getMessage());
            }
        }
        
        scanner.close();
        System.out.println("Exiting program.");
    }
}