package DSA.Sorting;

import java.util.Comparator;

/**
 * GTUSelectSort class implements selection sort algorithm.
 * Extends GTUSorter abstract class to provide generic sorting functionality.
 * 
 * Time Complexity: O(n²) in all cases
 * Space Complexity: O(1)
 * 
 * @author [Veysel Cemaloglu]
 */
public class GTUSelectSort extends GTUSorter {

    /**
     * Sorts elements in array between start (inclusive) and end (exclusive) using selection sort.
     * The algorithm divides the array into sorted and unsorted regions, repeatedly selecting
     * the minimum element from the unsorted region and moving it to the sorted region.
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
        // Iterate through the array
        for (int i = start; i < end - 1; i++) {
            // Find the minimum element in remaining unsorted array
            int minIndex = i;
            for (int j = i + 1; j < end; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element
            if (minIndex != i) {
                T temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }
}