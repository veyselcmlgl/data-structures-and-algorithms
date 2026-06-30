package HWSystem.Protocols;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

/**
 * I2C Protocol Implementation
 */
public class I2C implements Protocol {
    // Store log of operations using a Stack (LIFO)
    private Stack<String> operationLog;
    
    /**
     * Constructor for I2C protocol
     */
    public I2C() {
        operationLog = new Stack<>();
        // Add initial log entry
        operationLog.push("Port Opened.");
    }
    
    @Override
    public String getProtocolName() {
        return "I2C";
    }

    @Override
    public String read() {
        // Add to log
        addToLog("Reading.");
        // Return read string
        return getProtocolName() + ": Reading.";
    }

    @Override
    public void write(String data) {
        // Add to log
        addToLog("Writing \"" + data + "\".");
    }
    
    @Override
    public void addToLog(String operation) {
        operationLog.push(operation);
    }
    
    @Override
    public void writeLogToFile(String logPath, int portID) {
        try {
            String fileName = logPath + "/" + getProtocolName() + "_" + portID + ".log";
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            
            // Write logs in descending order (from last to first)
            // This is a natural fit for Stack's LIFO behavior
            Stack<String> tempStack = new Stack<>();
            tempStack.addAll(operationLog);
            
            while (!tempStack.empty()) {
                writer.println(tempStack.pop());
            }
            
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing log file: " + e.getMessage());
        }
    }
}