import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * A spell checker implementation using GTUHashSet for dictionary storage and lookup.
 * Provides suggestions for misspelled words based on edit distance.
 */
public class SpellChecker {
    
    private GTUHashSet<String> dictionary;
    private String word; // The word being processed for suggestions
    
    /**
     * Constructs a new spell checker and loads the dictionary.
     * 
     * @param dictionaryPath path to the dictionary file
     * @throws IOException if an I/O error occurs
     * 
     * Time Complexity: O(n) where n is the number of words in the dictionary
     */
    public SpellChecker(String dictionaryPath) throws IOException {
        dictionary = new GTUHashSet<>();
        loadDictionary(dictionaryPath);
    }
    
    /**
     * Loads the dictionary from a file.
     * 
     * @param dictionaryPath path to the dictionary file
     * @throws IOException if an I/O error occurs
     * 
     * Time Complexity: O(n) where n is the number of words in the dictionary
     */
    private void loadDictionary(String dictionaryPath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath));
        String word;
        while ((word = reader.readLine()) != null) {
            dictionary.add(word.trim().toLowerCase());
        }
        reader.close();
    }
    
    /**
     * Checks if a word is correctly spelled.
     * 
     * @param word the word to check
     * @return true if the word is in the dictionary, false otherwise
     * 
     * Time Complexity: Average case O(1), Worst case O(n)
     */
    public boolean isCorrectlySpelled(String word) {
        return dictionary.contains(word.toLowerCase());
    }
    
    /**
     * Finds suggestions for a misspelled word based on edit distance.
     * 
     * @param word the misspelled word
     * @return an array of suggested corrections
     * 
     * Time Complexity: O(L²) where L is the length of the word
     * Space Complexity: O(L²) where L is the length of the word
     */
    public String[] getSuggestions(String word) {
        this.word = word.toLowerCase();
        
        // We'll use an array-based approach to store suggestions
        // Since we can't directly iterate through our set
        String[] possibleSuggestions = new String[1000]; // Large enough to hold all possible suggestions
        int suggestionCount = 0;
        
        // Add words with edit distance 1
        suggestionCount = findEditDistance2(this.word, possibleSuggestions, suggestionCount);
        
        // If few suggestions found, try edit distance 2
        // if (suggestionCount < 5) { // Only try edit distance 2 if we don't have many suggestions
        //     suggestionCount = findEditDistance2(this.word, possibleSuggestions, suggestionCount);
        // }
        
        // Create properly sized result array
        String[] result = new String[suggestionCount];
        for (int i = 0; i < suggestionCount; i++) {
            result[i] = possibleSuggestions[i];
        }
        
        return result;
    }
    
    /**
     * Finds all words with edit distance 1 from the input word and adds them to the suggestions array.
     * 
     * @param word the input word
     * @param suggestions the array to store suggestions
     * @param startIndex the starting index in the array
     * @return the new index after adding suggestions
     * 
     * Time Complexity: O(L) where L is the length of the word
     */
    private int findEditDistance1(String word, String[] suggestions, int startIndex) {
        int index = startIndex;
        
        // Check if the word itself is in the dictionary (in case we're called for edit distance 2)
        if (dictionary.contains(word) && !containsWord(suggestions, word, index)) {
            suggestions[index++] = word;
        }
        
        // Deletions
        for (int i = 0; i < word.length(); i++) {
            String deletion = word.substring(0, i) + word.substring(i + 1);
            if (dictionary.contains(deletion) && !containsWord(suggestions, deletion, index)) {
                suggestions[index++] = deletion;
            }
        }
        
        // Insertions
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String insertion = word.substring(0, i) + c + word.substring(i);
                if (dictionary.contains(insertion) && !containsWord(suggestions, insertion, index)) {
                    suggestions[index++] = insertion;
                }
            }
        }
        
        // Substitutions
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != word.charAt(i)) {
                    String substitution = word.substring(0, i) + c + word.substring(i + 1);
                    if (dictionary.contains(substitution) && !containsWord(suggestions, substitution, index)) {
                        suggestions[index++] = substitution;
                    }
                }
            }
        }
        
        // Transpositions
        for (int i = 0; i < word.length() - 1; i++) {
            String transposition = word.substring(0, i) + 
                                  word.charAt(i + 1) + 
                                  word.charAt(i) + 
                                  word.substring(i + 2);
            if (dictionary.contains(transposition) && !containsWord(suggestions, transposition, index)) {
                suggestions[index++] = transposition;
            }
        }
        
        return index;
    }
    
    /**
     * Checks if a word is already in the suggestions array.
     * 
     * @param suggestions the suggestions array
     * @param word the word to check
     * @param count the number of elements in the array
     * @return true if the word is already in the array
     */
    private boolean containsWord(String[] suggestions, String word, int count) {
        for (int i = 0; i < count; i++) {
            if (word.equals(suggestions[i])) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Finds all words with edit distance 2 from the input word and adds them to the suggestions array.
     * 
     * @param word the input word
     * @param suggestions the array to store suggestions
     * @param startIndex the starting index in the array
     * @return the new index after adding suggestions
     * 
     * Time Complexity: O(L²) where L is the length of the word
     */
    private int findEditDistance2(String word, String[] suggestions, int startIndex) {
        int index = startIndex;
        
        // For edit distance 2, we'll generate all possible edit distance 1 words first
        String[] edits1 = new String[2000]; // Large enough for all edit distance 1 words
        int edits1Count = 0;
        
        // Deletions at edit distance 1
        for (int i = 0; i < word.length(); i++) {
            String deletion = word.substring(0, i) + word.substring(i + 1);
            if (!containsWord(edits1, deletion, edits1Count)) {
                edits1[edits1Count++] = deletion;
            }
        }
        
        // Transpositions at edit distance 1
        for (int i = 0; i < word.length() - 1; i++) {
            String transposition = word.substring(0, i) + 
                                  word.charAt(i + 1) + 
                                  word.charAt(i) + 
                                  word.substring(i + 2);
            if (!containsWord(edits1, transposition, edits1Count)) {
                edits1[edits1Count++] = transposition;
            }
        }
        
        // Substitutions at edit distance 1
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != word.charAt(i)) {
                    String substitution = word.substring(0, i) + c + word.substring(i + 1);
                    if (!containsWord(edits1, substitution, edits1Count)) {
                        edits1[edits1Count++] = substitution;
                    }
                }
            }
        }
        
        // Insertions at edit distance 1 (last for performance)
        for (int i = 0; i <= word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String insertion = word.substring(0, i) + c + word.substring(i);
                if (!containsWord(edits1, insertion, edits1Count)) {
                    edits1[edits1Count++] = insertion;
                }
            }
        }
        
        // Now for each edit distance 1 word, find all edit distance 1 words again
        // This will give us edit distance 2 from the original word
        for (int i = 0; i < edits1Count; i++) {
            String edit1 = edits1[i];
            index = findEditDistance1(edit1, suggestions, index);
        }
        
        // Also try more common prefixes and suffixes
        String[] commonPrefixes = {"a", "be", "re", "in", "un", "dis", "mis", "pre", "over", "under"};
        for (String prefix : commonPrefixes) {
            String withPrefix = prefix + word;
            if (dictionary.contains(withPrefix) && !containsWord(suggestions, withPrefix, index)) {
                suggestions[index++] = withPrefix;
            }
        }
        
        String[] commonSuffixes = {"s", "ed", "ing", "er", "est", "ly", "ment", "ness", "ful", "less"};
        for (String suffix : commonSuffixes) {
            String withSuffix = word + suffix;
            if (dictionary.contains(withSuffix) && !containsWord(suggestions, withSuffix, index)) {
                suggestions[index++] = withSuffix;
            }
        }
        
        return index;
    }
    
    /**
     * Main method to run the spell checker.
     * 
     * @param args command-line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        SpellChecker spellChecker = new SpellChecker("dictionary.txt");
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Enter a word [.exit to quit] : ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase(".exit")) {
                break;
            }
            
            long startTime = System.nanoTime();
            
            if (spellChecker.isCorrectlySpelled(input)) {
                System.out.println("Correct.");
            } else {
                System.out.println("Incorrect.");
                System.out.print("Suggestions: ");
                
                String[] suggestions = spellChecker.getSuggestions(input);
                
                // Print suggestions
                System.out.print("[");
                for (int i = 0; i < suggestions.length; i++) {
                    System.out.print(suggestions[i]);
                    if (i < suggestions.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("]");
            }
            
            long endTime = System.nanoTime();
            System.out.printf("Lookup and suggestion took %.2f ms\n", (endTime - startTime) / 1e6);
        }
        
        scanner.close();
    }
}