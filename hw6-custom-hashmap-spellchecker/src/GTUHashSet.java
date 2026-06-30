/**
 * A hash set implementation using GTUHashMap internally.
 * This implementation does not allow null elements.
 * 
 * @param <E> the type of elements maintained by this set
 */
public class GTUHashSet<E> {
    /** A dummy object used as value in the map */
    private static final Object PRESENT = new Object();
    
    /** Map used to store the elements */
    private GTUHashMap<E, Object> map;
    
    /**
     * Constructs a new, empty set.
     * 
     * Time Complexity: O(1)
     */
    public GTUHashSet() {
        map = new GTUHashMap<>();
    }
    
    /**
     * Adds the specified element to this set if it is not already present.
     * 
     * @param element the element to be added to this set
     * 
     * Time Complexity: Average case O(1), Worst case O(n) when resizing
     */
    public void add(E element) {
        map.put(element, PRESENT);
    }
    
    /**
     * Removes the specified element from this set if it is present.
     * 
     * @param element the element to be removed from this set, if present
     * 
     * Time Complexity: Average case O(1), Worst case O(n)
     */
    public void remove(E element) {
        map.remove(element);
    }
    
    /**
     * Returns true if this set contains the specified element.
     * 
     * @param element the element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     * 
     * Time Complexity: Average case O(1), Worst case O(n)
     */
    public boolean contains(E element) {
        return map.containsKey(element);
    }
    
    /**
     * Returns the number of elements in this set.
     * 
     * @return the number of elements in this set
     * 
     * Time Complexity: O(1)
     */
    public int size() {
        return map.size();
    }
}