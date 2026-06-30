package MyTaskManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import MyPriorityQueue.MyPriorityQueue;
import MyPriorityQueue.MinHeap;

/**
 * Manages tasks in the system by handling users and scheduling tasks.
 */
public class TaskManager {
    private ArrayList<MyUser> users;
    private MyPriorityQueue<MyTask> tasks;
    private int taskIdCounter;
    
    /**
     * Constructs a new TaskManager and initializes the necessary data structures.
     * Time Complexity: O(1)
     */
    public TaskManager() {
        users = new ArrayList<>();
        tasks = new MinHeap<>();
        taskIdCounter = 0;
    }
    
    /**
     * Loads user configuration from the specified file.
     * Each line in the file represents a user's priority.
     * 
     * @param configFilePath The path to the configuration file.
     * @return true if loaded successfully, false otherwise.
     * Time Complexity: O(n) where n is the number of users in the file
     */
    public boolean loadUserConfig(String configFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            int userId = 0;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Skip comments or empty lines
                if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }
                
                // Extract priority from the line
                int priority;
                int commentIndex = line.indexOf("//");
                if (commentIndex != -1) {
                    line = line.substring(0, commentIndex).trim();
                }
                
                try {
                    priority = Integer.parseInt(line);
                    
                    // Validate that priority is non-negative
                    if (priority < 0) {
                        System.err.println("Error: Priority cannot be negative. Found priority: " + priority);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Invalid priority format in line: " + line);
                    continue;
                }
                
                // Create and store user
                MyUser user = new MyUser(userId, priority);
                users.add(user);
                userId++;
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Adds a new task for the specified user.
     * 
     * @param userId The ID of the user issuing the task.
     * @return true if the task was added successfully, false if the user ID is invalid.
     * Time Complexity: O(log n) where n is the number of tasks (for adding to heap)
     */
    public boolean addTask(int userId) {
        // Check if the user ID is valid
        if (userId < 0 || userId >= users.size()) {
            System.err.println("Error: Invalid user ID " + userId);
            return false;
        }
        
        MyUser user = users.get(userId);
        MyTask task = new MyTask(user, taskIdCounter++);
        tasks.add(task);
        return true;
    }
    
    /**
     * Executes all tasks in the priority queue.
     * Tasks are executed based on their priority.
     * 
     * Time Complexity: O(n log n) where n is the number of tasks
     */
    public void executeTasks() {
        while (!tasks.isEmpty()) {
            MyTask task = tasks.poll();
            System.out.println(task.toString());
        }
    }
}