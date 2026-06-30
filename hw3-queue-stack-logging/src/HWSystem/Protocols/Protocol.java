package HWSystem.Protocols;

// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.PrintWriter;
// import java.util.Stack;

/**
 * Protocol Interface
 * Represents a communication protocol in the system
 */
public interface Protocol {
    /**
     * Returns the name of the protocol
     * @return Protocol name as a string
     */
    String getProtocolName();
    
    /**
     * Reads data from the port
     * @return String representing the read operation
     */
    String read();
    
    /**
     * Writes data to the port
     * @param data The data to write
     */
    void write(String data);
    
    /**
     * Adds a read/write operation to the protocol's log
     * @param operation The operation to add to the log
     */
    void addToLog(String operation);
    
    /**
     * Writes the log to a file in descending order (from last to first)
     * @param logPath The directory path where log files should be created
     * @param portID The ID of the port associated with this protocol
     */
    void writeLogToFile(String logPath, int portID);
}