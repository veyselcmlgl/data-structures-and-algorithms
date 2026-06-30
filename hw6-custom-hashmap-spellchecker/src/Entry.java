/**
 * Represents an entry in a hash map with a key, value, and deletion status.
 * 
 * @param <K> the type of keys maintained by this entry
 * @param <V> the type of mapped values
 */
public class Entry<K, V> {
    /** The key of this entry */
    public K key;
    
    /** The value of this entry */
    public V value;
    
    /** Flag indicating if this entry has been deleted (tombstone) */
    public boolean isDeleted;
    
    /**
     * Constructs a new entry with the specified key and value.
     * 
     * @param key the key of this entry
     * @param value the value of this entry
     * 
     * Time Complexity: O(1)
     */
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.isDeleted = false;
    }
}