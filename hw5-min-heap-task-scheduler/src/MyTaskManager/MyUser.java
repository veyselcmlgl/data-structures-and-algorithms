package MyTaskManager;

/**
 * Represents a user in the task management system.
 * Each user has an ID and a priority level.
 */
public class MyUser {
    private Integer id;
    private Integer priority;
    
    /**
     * Constructs a new user with the specified ID and priority.
     * @param id The ID of the user.
     * @param priority The priority of the user (lower value means higher importance).
     * Time Complexity: O(1)
     */
    public MyUser(Integer id, Integer priority) {
        this.id = id;
        this.priority = priority;
    }
    
    /**
     * Gets the ID of the user.
     * @return The user's ID.
     * Time Complexity: O(1)
     */
    public Integer getID() {
        return this.id;
    }
    
    /**
     * Gets the priority of the user.
     * @return The user's priority (lower value means higher importance).
     * Time Complexity: O(1)
     */
    public Integer getPriority() {
        return this.priority;
    }
}