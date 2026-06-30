package Main;

import java.util.Scanner;
import MyTaskManager.TaskManager;

/**
 * Main class for the Task Scheduling application.
 * This class handles the command-line interface and program flow.
 */
public class Main {
    /**
     * The entry point of the application.
     * 
     * @param args Command line arguments. The first argument should be the path to the user configuration file.
     * Time Complexity: O(n + m log m) where n is the number of users and m is the number of tasks
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Error: Please provide the path to the configuration file as a command line argument.");
            System.exit(1);
        }
        
        String configFilePath = args[0];
        TaskManager taskManager = new TaskManager();
        
        // Load user configuration
        if (!taskManager.loadUserConfig(configFilePath)) {
            System.err.println("Failed to load user configuration. Exiting...");
            System.exit(1);
        }
        
        // Process input tasks
        Scanner scanner = new Scanner(System.in);
        String input;
        
        while (scanner.hasNextLine()) {
            input = scanner.nextLine().trim();
            
            // Check if it's the execute command
            if (input.equalsIgnoreCase("execute")) {
                taskManager.executeTasks();
                break; // Terminate the program after execution
            }
            
            // Process task input (user ID)
            try {
                int userId = Integer.parseInt(input);
                taskManager.addTask(userId);
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid input format. Expected user ID or 'execute' command.");
            }
        }
        
        scanner.close();
    }
}