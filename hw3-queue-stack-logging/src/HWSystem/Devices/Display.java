package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

/**
 * Abstract Display class
 * Base class for all display devices in the system
 */
public abstract class Display extends Device {
    /**
     * Constructor for Display
     * @param protocol Protocol to use for communication
     */
    public Display(Protocol protocol) {
        super(protocol);
    }

    /**
     * Print data to the display
     * @param data Data to print
     */
    public abstract void printData(String data);

    @Override
    public String getDevType() {
        return "Display";
    }
}