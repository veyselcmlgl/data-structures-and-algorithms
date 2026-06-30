package Main;

import HWSystem.HWSystem;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Main class - entry point of the application
 */
public class Main {
    /**
     * Main method
     * @param args Command line arguments - config file path and log path
     */
    public static void main(String[] args) {
        // Check if configuration file path and log path are provided
        if (args.length < 2) {
            System.err.println("Please provide a configuration file path and log path.");
            System.err.println("Usage: java Main.Main <configPath> <logPath>");
            return;
        }
        
        String configPath = args[0];
        String logPath = args[1];
        HWSystem system = new HWSystem(configPath, logPath);
        
        // Create a Queue to store commands
        Queue<String> commandQueue = new LinkedList<>();
        
        Scanner scanner = new Scanner(System.in);
        String command;
        boolean running = true;
        
        while (running) {
            // Read one command at a time
            command = scanner.nextLine().trim();
            
            // Parse the command
            String[] parts = command.split(" ");
            String operation = parts[0];
            
            // Check if it's the exit command
            if (operation.equals("exit")) {
                running = false;
                commandQueue.add(command); // Add exit command to the queue
            } else {
                // Add command to the queue
                commandQueue.add(command);
            }
        }
        
        // Close scanner
        scanner.close();
        
        // Execute all commands in the queue
        executeCommands(commandQueue, system);
        
        // Write all port logs
        system.writeLogs();
    }
    
    /**
     * Execute a list of commands in order
     * @param commandQueue Queue of commands to execute
     * @param system HWSystem instance
     */
    private static void executeCommands(Queue<String> commandQueue, HWSystem system) {
        // Process commands from the Queue
        while (!commandQueue.isEmpty()) {
            String command = commandQueue.poll();
            
            // Parse the command
            String[] parts = command.split(" ");
            String operation = parts[0];
            
            try {
                switch (operation) {
                    case "turnON":
                        if (parts.length < 2) {
                            System.err.println("Usage: turnON <portID>");
                            break;
                        }
                        int portID = Integer.parseInt(parts[1]);
                        system.turnDeviceON(portID);
                        break;
                        
                    case "turnOFF":
                        if (parts.length < 2) {
                            System.err.println("Usage: turnOFF <portID>");
                            break;
                        }
                        portID = Integer.parseInt(parts[1]);
                        system.turnDeviceOFF(portID);
                        break;
                        
                    case "addDev":
                        if (parts.length < 4) {
                            System.err.println("Usage: addDev <devName> <portID> <devID>");
                            break;
                        }
                        String devName = parts[1];
                        portID = Integer.parseInt(parts[2]);
                        int devID = Integer.parseInt(parts[3]);
                        system.addDevice(devName, portID, devID);
                        break;
                        
                    case "rmDev":
                        if (parts.length < 2) {
                            System.err.println("Usage: rmDev <portID>");
                            break;
                        }
                        portID = Integer.parseInt(parts[1]);
                        system.removeDevice(portID);
                        break;
                        
                    case "list":
                        if (parts.length < 2) {
                            System.err.println("Usage: list ports|Sensor|Display|WirelessIO|MotorDriver");
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
                            System.err.println("Usage: readSensor <devID>");
                            break;
                        }
                        devID = Integer.parseInt(parts[1]);
                        system.readSensor(devID);
                        break;
                        
                    case "printDisplay":
                        if (parts.length < 3) {
                            System.err.println("Usage: printDisplay <devID> <String>");
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
                            System.err.println("Usage: readWireless <devID>");
                            break;
                        }
                        devID = Integer.parseInt(parts[1]);
                        system.readWireless(devID);
                        break;
                        
                    case "writeWireless":
                        if (parts.length < 3) {
                            System.err.println("Usage: writeWireless <devID> <String>");
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
                            System.err.println("Usage: setMotorSpeed <devID> <integer>");
                            break;
                        }
                        devID = Integer.parseInt(parts[1]);
                        int speed = Integer.parseInt(parts[2]);
                        system.setMotorSpeed(devID, speed);
                        break;
                        
                    case "exit":
                        System.out.println("Exiting ...");
                        break;
                        
                    default:
                        System.err.println("Unknown command: " + operation);
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please check your input.");
            } catch (Exception e) {
                System.err.println("Error executing command: " + e.getMessage());
            }
        }
    }
}