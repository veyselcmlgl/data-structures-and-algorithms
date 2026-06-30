package DSA.Sorting;

import java.util.Comparator;

/**
 * GTUInsertSort class implements insertion sort algorithm.
 * Extends GTUSorter abstract class to provide generic sorting functionality.
 * 
 * Time Complexity: O(n²) in worst case, O(n) in best case
 * Space Complexity: O(1)
 * 
 * @author [Veysel Cemaloglu]
 */
public class GTUInsertSort extends GTUSorter {

    /**
     * Sorts elements in array between start (inclusive) and end (exclusive) using insertion sort.
     * The algorithm builds the final sorted array one item at a time by repeatedly taking 
     * an element from the unsorted portion and inserting it into the correct position 
     * in the sorted portion.
     * 
     * Time Complexity: O(n²) where n = end - start
     * Space Complexity: O(1)
     * 
     * @param <T> Generic type parameter
     * @param arr Array to be sorted
     * @param start Starting index (inclusive)
     * @param end Ending index (exclusive)
     * @param comparator Comparator to determine sort order
     */
    @Override
    protected <T> void sort(T[] arr, int start, int end, Comparator<T> comparator) {
        // Iterate through the array starting from the second element
        for (int i = start + 1; i < end; i++) {
            T key = arr[i];
            int j = i - 1;

            // Move elements that are greater than key one position ahead
            while (j >= start && comparator.compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            
            // Insert the key at its correct position
            arr[j + 1] = key;
        }
    }
}