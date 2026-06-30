package MyPriorityQueue;

/**
 * Interface for a priority queue implementation.
 * @param <T> The type of elements to be stored in the queue. Must implement Comparable.
 */
public interface MyPriorityQueue<T extends Comparable<T>> {
    /**
     * Adds an element to the priority queue.
     * @param t The element to be added.
     * Time Complexity: O(log n) - where n is the number of elements in the queue
     */
    void add(T t);
    
    /**
     * Removes and returns the highest priority element from the queue.
     * @return The highest priority element, or null if the queue is empty.
     * Time Complexity: O(log n) - where n is the number of elements in the queue
     */
    T poll();
    
    /**
     * Checks if the priority queue is empty.
     * @return true if the queue is empty, false otherwise.
     * Time Complexity: O(1)
     */
    Boolean isEmpty();
}