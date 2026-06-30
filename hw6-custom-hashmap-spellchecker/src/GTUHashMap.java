/**
 * A hash map implementation using open addressing with linear probing.
 * This implementation does not allow null keys.
 * 
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class GTUHashMap<K, V> {
    /** Array to store the entries */
    private Entry<K, V>[] table;
    
    /** Number of key-value mappings in this map */
    private int size;
    
    /** Current capacity of the hash table */
    private int capacity;
    
    /** Load factor threshold for resizing */
    private final double LOAD_FACTOR = 0.75;
    
    /**
     * Constructs an empty hash map with default initial capacity (11).
     * 
     * Time Complexity: O(1)
     */
    @SuppressWarnings("unchecked")
    public GTUHashMap() {
        this.capacity = 11;
        this.table = new Entry[capacity];
        this.size = 0;
    }
    
    /**
     * Returns the hash code for the specified key.
     * 
     * @param key the key
     * @return the hash code
     * 
     * Time Complexity: O(1)
     */
    private int getHash(K key) {
        return Math.abs(key.hashCode());
    }
    
    /**
     * Finds the next prime number greater than twice the current capacity.
     * 
     * @return the next prime number
     * 
     * Time Complexity: O(n*sqrt(n)) where n is the new capacity
     */
    private int findNextPrime() {
        int newCapacity = 2 * capacity;
        
        // Find the next prime number
        while (!isPrime(newCapacity)) {
            newCapacity++;
        }
        
        return newCapacity;
    }
    
    /**
     * Checks if a number is prime.
     * 
     * @param num the number to check
     * @return true if the number is prime, false otherwise
     * 
     * Time Complexity: O(sqrt(n)) where n is the number being checked
     */
    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        if (num <= 3) {
            return true;
        }
        if (num % 2 == 0 || num % 3 == 0) {
            return false;
        }
        
        for (int i = 5; i * i <= num; i += 6) {
            if (num % i == 0 || num % (i + 2) == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Resizes the hash table to the next prime capacity to reduce clustering.
     * 
     * Time Complexity: O(n) where n is the number of entries
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        Entry<K, V>[] oldTable = table;
        
        // Find next prime capacity
        capacity = findNextPrime();
        table = new Entry[capacity];
        size = 0;
        
        // Rehash all existing entries
        for (int i = 0; i < oldCapacity; i++) {
            if (oldTable[i] != null && !oldTable[i].isDeleted) {
                put(oldTable[i].key, oldTable[i].value);
            }
        }
    }
    
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * 
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * 
     * Time Complexity: Average case O(1), Worst case O(n) when resizing
     */
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        
        // Check if we need to resize
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }
        
        int hash = getHash(key);
        int index = hash % capacity;
        int initialIndex = index;
        
        // Linear probing to find a slot
        int i = 0;
        while (true) {
            // Found an empty slot or a deleted slot
            if (table[index] == null || table[index].isDeleted) {
                table[index] = new Entry<>(key, value);
                size++;
                return;
            }
            
            // Update value if key already exists
            if (table[index].key.equals(key)) {
                table[index].value = value;
                return;
            }
            
            // Collision: move to next slot (linear probing)
            i++;
            index = (hash + i) % capacity;
            
            // If we've searched the entire table, break
            if (index == initialIndex) {
                break;
            }
        }
    }
    
    /**
     * Returns the value to which the specified key is mapped,
     * or null if this map contains no mapping for the key.
     * 
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if no mapping
     * 
     * Time Complexity: Average case O(1), Worst case O(n)
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }
        
        int hash = getHash(key);
        int index = hash % capacity;
        int initialIndex = index;
        
        // Linear probing to find the key
        int i = 0;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key.equals(key)) {
                return table[index].value;
            }
            
            // Move to next slot (linear probing)
            i++;
            index = (hash + i) % capacity;
            
            // If we've searched the entire table, break
            if (index == initialIndex) {
                break;
            }
        }
        
        return null; // Key not found
    }
    
    /**
     * Removes the mapping for the specified key from this map if present.
     * Uses tombstone marking for deletion.
     * 
     * @param key the key whose mapping is to be removed from the map
     * 
     * Time Complexity: Average case O(1), Worst case O(n)
     */
    public void remove(K key) {
        if (key == null) {
            return;
        }
        
        int hash = getHash(key);
        int index = hash % capacity;
        int initialIndex = index;
        
        // Linear probing to find the key
        int i = 0;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key.equals(key)) {
                // Mark as deleted (tombstone)
                table[index].isDeleted = true;
                size--;
                return;
            }
            
            // Move to next slot (linear probing)
            i++;
            index = (hash + i) % capacity;
            
            // If we've searched the entire table, break
            if (index == initialIndex) {
                break;
            }
        }
    }
    
    /**
     * Returns true if this map contains a mapping for the specified key.
     * 
     * @param key the key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     * 
     * Time Complexity: Average case O(1), Worst case O(n)
     */
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        
        int hash = getHash(key);
        int index = hash % capacity;
        int initialIndex = index;
        
        // Linear probing to find the key
        int i = 0;
        while (table[index] != null) {
            if (!table[index].isDeleted && table[index].key.equals(key)) {
                return true;
            }
            
            // Move to next slot (linear probing)
            i++;
            index = (hash + i) % capacity;
            
            // If we've searched the entire table, break
            if (index == initialIndex) {
                break;
            }
        }
        
        return false; // Key not found
    }
    
    /**
     * Returns the number of key-value mappings in this map.
     * 
     * @return the number of key-value mappings in this map
     * 
     * Time Complexity: O(1)
     */
    public int size() {
        return size;
    }
}