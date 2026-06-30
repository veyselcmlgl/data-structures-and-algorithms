package DSA.Graphs.MatrixGraph;

import java.util.Collection;
import java.util.Iterator;

/**
 * AdjacencyVect represents a vector in an adjacency matrix for graph representation.
 * Implements Collection interface to store boolean values for vertex adjacency.
 * Only iterates over vertices that are adjacent (true values).
 * 
 * Time Complexity: Most operations O(1), iteration O(n)
 * Space Complexity: O(n) where n is the number of vertices
 * 
 * @author [Veysel Cemaloglu]
 */
public class AdjacencyVect implements Collection<Integer> {
    
    private boolean[] adjacencies;
    private int size;
    private int count; // Number of true values

    /**
     * Constructor creates adjacency vector with specified size.
     * All adjacencies are initially false.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * 
     * @param size Number of vertices in the graph
     */
    public AdjacencyVect(int size) {
        this.size = size;
        this.adjacencies = new boolean[size];
        this.count = 0;
    }

    /**
     * Returns the number of adjacent vertices (true values).
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @return Number of adjacent vertices
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Checks if the adjacency vector is empty (no adjacent vertices).
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @return true if no adjacent vertices, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Checks if the specified vertex is adjacent.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @param o Object to check (should be Integer vertex ID)
     * @return true if vertex is adjacent, false otherwise
     */
    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }
        int vertex = (Integer) o;
        if (vertex < 0 || vertex >= size) {
            return false;
        }
        return adjacencies[vertex];
    }

    /**
     * Returns iterator that only iterates over adjacent vertices (true values).
     * 
     * Time Complexity: O(1) for creation, O(n) for full iteration
     * Space Complexity: O(1)
     * 
     * @return Iterator over adjacent vertices
     */
    @Override
    public Iterator<Integer> iterator() {
        return new AdjacencyIterator();
    }

    /**
     * Custom iterator class that only returns adjacent vertices.
     */
    private class AdjacencyIterator implements Iterator<Integer> {
        private int currentIndex;

        /**
         * Constructor initializes iterator to first adjacent vertex.
         * 
         * Time Complexity: O(n) worst case
         * Space Complexity: O(1)
         */
        public AdjacencyIterator() {
            currentIndex = -1;
            findNext();
        }

        /**
         * Checks if there are more adjacent vertices to iterate over.
         * 
         * Time Complexity: O(1)
         * Space Complexity: O(1)
         * 
         * @return true if more elements exist, false otherwise
         */
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        /**
         * Returns the next adjacent vertex.
         * 
         * Time Complexity: O(n) worst case
         * Space Complexity: O(1)
         * 
         * @return Next adjacent vertex ID
         */
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            int result = currentIndex;
            findNext();
            return result;
        }

        /**
         * Finds the next adjacent vertex (with true value).
         * 
         * Time Complexity: O(n) worst case
         * Space Complexity: O(1)
         */
        private void findNext() {
            currentIndex++;
            while (currentIndex < size && !adjacencies[currentIndex]) {
                currentIndex++;
            }
        }
    }

    /**
     * Converts collection to array of adjacent vertex IDs.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k) where k is number of adjacent vertices
     * 
     * @return Array containing adjacent vertex IDs
     */
    @Override
    public Object[] toArray() {
        Object[] result = new Object[count];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (adjacencies[i]) {
                result[index++] = i;
            }
        }
        return result;
    }

    /**
     * Converts collection to typed array of adjacent vertex IDs.
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(k) where k is number of adjacent vertices
     * 
     * @param <T> Array element type
     * @param a Array to store elements
     * @return Array containing adjacent vertex IDs
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < count) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), count);
        }
        
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (adjacencies[i]) {
                a[index++] = (T) Integer.valueOf(i);
            }
        }
        
        if (a.length > count) {
            a[count] = null;
        }
        
        return a;
    }

    /**
     * Adds vertex to adjacency vector (sets adjacency to true).
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @param e Vertex ID to add
     * @return true if vertex was added, false if already present
     */
    @Override
    public boolean add(Integer e) {
        if (e == null || e < 0 || e >= size) {
            return false;
        }
        if (!adjacencies[e]) {
            adjacencies[e] = true;
            count++;
            return true;
        }
        return false;
    }

    /**
     * Removes vertex from adjacency vector (sets adjacency to false).
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @param o Vertex ID to remove
     * @return true if vertex was removed, false if not present
     */
    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Integer)) {
            return false;
        }
        int vertex = (Integer) o;
        if (vertex < 0 || vertex >= size) {
            return false;
        }
        if (adjacencies[vertex]) {
            adjacencies[vertex] = false;
            count--;
            return true;
        }
        return false;
    }

    /**
     * Checks if all vertices in the collection are adjacent.
     * 
     * Time Complexity: O(k) where k is size of collection c
     * Space Complexity: O(1)
     * 
     * @param c Collection of vertices to check
     * @return true if all vertices are adjacent, false otherwise
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds all vertices in the collection to adjacency vector.
     * 
     * Time Complexity: O(k) where k is size of collection c
     * Space Complexity: O(1)
     * 
     * @param c Collection of vertices to add
     * @return true if any vertex was added, false otherwise
     */
    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        boolean modified = false;
        for (Integer vertex : c) {
            if (add(vertex)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Removes all vertices in the collection from adjacency vector.
     * 
     * Time Complexity: O(k) where k is size of collection c
     * Space Complexity: O(1)
     * 
     * @param c Collection of vertices to remove
     * @return true if any vertex was removed, false otherwise
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Retains only vertices that are in the specified collection.
     * 
     * Time Complexity: O(n + k) where n is graph size, k is collection size
     * Space Complexity: O(1)
     * 
     * @param c Collection of vertices to retain
     * @return true if adjacency vector was modified, false otherwise
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (adjacencies[i] && !c.contains(i)) {
                adjacencies[i] = false;
                count--;
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Removes all adjacencies (sets all to false).
     * 
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            adjacencies[i] = false;
        }
        count = 0;
    }
}