package MyPriorityQueue;

import java.util.ArrayList;

/**
 * A generic min heap implementation for the MyPriorityQueue interface.
 * @param <T> The type of elements to be stored in the heap. Must implement Comparable.
 */
public class MinHeap<T extends Comparable<T>> implements MyPriorityQueue<T> {
    private ArrayList<T> heap;
    
    /**
     * Constructs an empty min heap.
     * Time Complexity: O(1)
     */
    public MinHeap() {
        heap = new ArrayList<>();
    }
    
    /**
     * Adds an element to the min heap.
     * @param t The element to be added.
     * Time Complexity: O(log n) - where n is the number of elements in the heap
     */
    @Override
    public void add(T t) {
        // Add the element to the end of the heap
        heap.add(t);
        
        // Maintain the min heap property by sifting up
        siftUp(heap.size() - 1);
    }
    
    /**
     * Removes and returns the minimum element from the heap.
     * @return The minimum element, or null if the heap is empty.
     * Time Complexity: O(log n) - where n is the number of elements in the heap
     */
    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        
        // Get the minimum element (root of the heap)
        T min = heap.get(0);
        
        // Replace the root with the last element
        T lastElement = heap.remove(heap.size() - 1);
        
        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            // Maintain the min heap property by sifting down
            siftDown(0);
        }
        
        return min;
    }
    
    /**
     * Checks if the heap is empty.
     * @return true if the heap is empty, false otherwise.
     * Time Complexity: O(1)
     */
    @Override
    public Boolean isEmpty() {
        return heap.isEmpty();
    }
    
    /**
     * Restores the min heap property by moving an element up the heap.
     * @param index The index of the element to be moved.
     * Time Complexity: O(log n) - where n is the number of elements in the heap
     */
    private void siftUp(int index) {
        int parentIndex = (index - 1) / 2;
        
        // If the parent is greater than the current element, swap them
        if (index > 0 && heap.get(parentIndex).compareTo(heap.get(index)) > 0) {
            T temp = heap.get(index);
            heap.set(index, heap.get(parentIndex));
            heap.set(parentIndex, temp);
            
            // Continue sifting up
            siftUp(parentIndex);
        }
    }
    
    /**
     * Restores the min heap property by moving an element down the heap.
     * @param index The index of the element to be moved.
     * Time Complexity: O(log n) - where n is the number of elements in the heap
     */
    private void siftDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int smallest = index;
        
        // Find the smallest element among the parent and its children
        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).compareTo(heap.get(smallest)) < 0) {
            smallest = leftChildIndex;
        }
        
        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).compareTo(heap.get(smallest)) < 0) {
            smallest = rightChildIndex;
        }
        
        // If the smallest element is not the parent, swap them
        if (smallest != index) {
            T temp = heap.get(index);
            heap.set(index, heap.get(smallest));
            heap.set(smallest, temp);
            
            // Continue sifting down
            siftDown(smallest);
        }
    }
}