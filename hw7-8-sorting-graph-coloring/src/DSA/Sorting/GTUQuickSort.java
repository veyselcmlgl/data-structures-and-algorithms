package DSA.Sorting;

import java.util.Comparator;
import java.util.Random;

/**
 * GTUQuickSort class implements quicksort algorithm with random pivot selection.
 * Extends GTUSorter abstract class to provide generic sorting functionality.
 * Supports optimization using another sorter for small partitions.
 * 
 * Time Complexity: O(n log n) average case, O(n²) worst case
 * Space Complexity: O(log n) due to recursion
 * 
 * @author [Veysel Cemaloglu]
 */
public class GTUQuickSort extends GTUSorter {
    
    private GTUSorter fallbackSorter;
    private int partitionLimit;
    private Random random;

    /**
     * Default constructor for GTUQuickSort.
     * Uses quicksort for all partition sizes without fallback sorter.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public GTUQuickSort() {
        this.fallbackSorter = null;
        this.partitionLimit = 0;
        this.random = new Random();
    }

    /**
     * Constructor for GTUQuickSort with fallback sorter and partition limit.
     * When partition size becomes smaller than limit, uses fallbackSorter instead.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @param fallbackSorter Sorter to use for small partitions
     * @param partitionLimit Minimum partition size to use quicksort
     */
    public GTUQuickSort(GTUSorter fallbackSorter, int partitionLimit) {
        this.fallbackSorter = fallbackSorter;
        this.partitionLimit = partitionLimit;
        this.random = new Random();
    }

    /**
     * Sorts elements in array between start (inclusive) and end (exclusive) using quicksort.
     * Uses random pivot selection and optional fallback sorter for small partitions.
     * 
     * Time Complexity: O(n log n) average, O(n²) worst case where n = end - start
     * Space Complexity: O(log n) due to recursion
     * 
     * @param <T> Generic type parameter
     * @param arr Array to be sorted
     * @param start Starting index (inclusive)
     * @param end Ending index (exclusive)
     * @param comparator Comparator to determine sort order
     */
    @Override
    protected <T> void sort(T[] arr, int start, int end, Comparator<T> comparator) {
        // Base case: if partition has 1 or 0 elements, it's sorted
        if (end - start <= 1) {
            return;
        }

        // Use fallback sorter if partition is smaller than limit
        if (fallbackSorter != null && (end - start) < partitionLimit) {
            fallbackSorter.sort(arr, start, end, comparator);
            return;
        }

        // Partition the array and get pivot index
        int pivotIndex = partition(arr, start, end, comparator);

        // Recursively sort left and right partitions
        sort(arr, start, pivotIndex, comparator);
        sort(arr, pivotIndex + 1, end, comparator);
    }

    /**
     * Partitions the array around a randomly selected pivot.
     * Elements smaller than pivot go to left, larger elements go to right.
     * 
     * Time Complexity: O(n) where n = end - start
     * Space Complexity: O(1)
     * 
     * @param <T> Generic type parameter
     * @param arr Array to partition
     * @param start Starting index (inclusive)
     * @param end Ending index (exclusive)
     * @param comparator Comparator to determine order
     * @return Final position of pivot element
     */
    private <T> int partition(T[] arr, int start, int end, Comparator<T> comparator) {
        // Select random pivot and move it to end
        int randomIndex = start + random.nextInt(end - start);
        swap(arr, randomIndex, end - 1);
        
        T pivot = arr[end - 1];
        int i = start - 1;

        // Partition elements around pivot
        for (int j = start; j < end - 1; j++) {
            if (comparator.compare(arr[j], pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }

        // Place pivot in correct position
        swap(arr, i + 1, end - 1);
        return i + 1;
    }

    /**
     * Swaps two elements in the array.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @param <T> Generic type parameter
     * @param arr Array containing elements to swap
     * @param i Index of first element
     * @param j Index of second element
     */
    private <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}