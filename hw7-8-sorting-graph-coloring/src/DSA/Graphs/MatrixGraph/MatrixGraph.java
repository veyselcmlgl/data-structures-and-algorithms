package DSA.Graphs.MatrixGraph;

import java.util.Collection;

import DSA.Graphs.GTUGraph;

/**
 * MatrixGraph implements an undirected graph using adjacency matrix representation.
 * Each vertex has an AdjacencyVect that stores its connections to other vertices.
 * Implements GTUGraph interface for basic graph operations.
 * 
 * Time Complexity: O(V) space where V is number of vertices
 * Space Complexity: O(V²) for adjacency matrix
 * 
 * @author [Veysel Cemaloglu]
 */
public class MatrixGraph implements GTUGraph {
    
    private AdjacencyVect[] adjacencyMatrix;
    private int vertexCount;

    /**
     * Default constructor creates empty graph.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     */
    public MatrixGraph() {
        this.vertexCount = 0;
        this.adjacencyMatrix = null;
    }

    /**
     * Constructor creates graph with specified number of vertices.
     * 
     * Time Complexity: O(V²) where V is number of vertices
     * Space Complexity: O(V²)
     * 
     * @param size Number of vertices in the graph
     */
    public MatrixGraph(int size) {
        reset(size);
    }

    /**
     * Adds an edge between vertices v1 and v2.
     * Since graph is undirected, adds edge in both directions.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @param v1 First vertex ID
     * @param v2 Second vertex ID
     * @return true if edge was added successfully, false otherwise
     */
    @Override
    public Boolean setEdge(int v1, int v2) {
        // Validate vertex indices
        if (v1 < 0 || v1 >= vertexCount || v2 < 0 || v2 >= vertexCount) {
            return false;
        }

        // Avoid self-loops
        if (v1 == v2) {
            return false;
        }

        // Add edge in both directions (undirected graph)
        boolean added1 = adjacencyMatrix[v1].add(v2);
        boolean added2 = adjacencyMatrix[v2].add(v1);

        // Return true if at least one direction was added
        return added1 || added2;
    }

    /**
     * Checks if an edge exists between vertices v1 and v2.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @param v1 First vertex ID
     * @param v2 Second vertex ID
     * @return true if edge exists, false otherwise
     */
    @Override
    public Boolean getEdge(int v1, int v2) {
        // Validate vertex indices
        if (v1 < 0 || v1 >= vertexCount || v2 < 0 || v2 >= vertexCount) {
            return false;
        }

        // Check if v2 is in v1's adjacency list
        return adjacencyMatrix[v1].contains(v2);
    }

    /**
     * Gets all neighbors of the specified vertex.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @param v Vertex ID
     * @return Collection of neighbor vertex IDs
     */
    @Override
    public Collection<Integer> getNeighbors(int v) {
        // Validate vertex index
        if (v < 0 || v >= vertexCount) {
            return new AdjacencyVect(0); // Return empty collection
        }

        return adjacencyMatrix[v];
    }

    /**
     * Returns the number of vertices in the graph.
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1)
     * 
     * @return Number of vertices
     */
    @Override
    public int size() {
        return vertexCount;
    }

    /**
     * Resets the graph and changes its size.
     * Creates new adjacency matrix with specified number of vertices.
     * 
     * Time Complexity: O(V²) where V is the new size
     * Space Complexity: O(V²)
     * 
     * @param size New number of vertices
     */
    @Override
    public void reset(int size) {
        if (size < 0) {
            size = 0;
        }

        this.vertexCount = size;
        this.adjacencyMatrix = new AdjacencyVect[size];

        // Initialize adjacency vectors for each vertex
        for (int i = 0; i < size; i++) {
            adjacencyMatrix[i] = new AdjacencyVect(size);
        }
    }

    /**
     * Returns string representation of the graph showing adjacency information.
     * 
     * Time Complexity: O(V²)
     * Space Complexity: O(V²) for string construction
     * 
     * @return String representation of the graph
     */
    @Override
    public String toString() {
        if (vertexCount == 0) {
            return "Empty graph";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Graph with ").append(vertexCount).append(" vertices:\n");
        
        for (int i = 0; i < vertexCount; i++) {
            sb.append("Vertex ").append(i).append(" -> ");
            boolean first = true;
            for (Integer neighbor : adjacencyMatrix[i]) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append(neighbor);
                first = false;
            }
            if (first) {
                sb.append("(no neighbors)");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}