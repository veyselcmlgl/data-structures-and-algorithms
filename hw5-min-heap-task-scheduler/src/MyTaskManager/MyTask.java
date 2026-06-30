package MyTaskManager;

/**
 * Represents a task in the task management system.
 * Each task has a user who issued it and an ID.
 */
public class MyTask implements Comparable<MyTask> {
    private MyUser user;
    private Integer id;
    
    /**
     * Constructs a new task with the specified user and ID.
     * @param user The user who issued the task.
     * @param id The ID of the task.
     * Time Complexity: O(1)
     */
    public MyTask(MyUser user, Integer id) {
        this.user = user;
        this.id = id;
    }
    
    /**
     * Returns a string representation of the task.
     * @return A string in the format "Task <id> User <userID>".
     * Time Complexity: O(1)
     */
    @Override
    public String toString() {
        return "Task " + id + " User " + user.getID();
    }
    
    /**
     * Compares this task with another task for ordering.
     * A task has priority over another task if:
     * 1. The user who issued it has a higher importance (lower priority value)
     * 2. If users have equal importance, the task that was issued first has higher priority (lower ID)
     * 
     * @param other The task to be compared.
     * @return A negative integer, zero, or a positive integer as this task
     *         has higher, equal to, or lower priority than the other task.
     * Time Complexity: O(1)
     */
    @Override
    public int compareTo(MyTask other) {
        // Compare user priorities first (lower priority value means higher importance)
        int priorityComparison = this.user.getPriority().compareTo(other.user.getPriority());
        
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        
        // If users have equal priority, compare task IDs (lower ID means issued earlier)
        return this.id.compareTo(other.id);
    }
}