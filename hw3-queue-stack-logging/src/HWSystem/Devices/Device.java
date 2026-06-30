package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

/**
 * Abstract Device class
 * Base class for all device types in the system
 */
public abstract class Device {
    public Protocol protocol;
    protected State state;

    /**
     * Constructor for Device
     * @param protocol Protocol to use for communication
     */
    public Device(Protocol protocol) {
        this.protocol = protocol;
        this.state = State.OFF;
    }

    /**
     * Turn the device ON
     */
    public abstract void turnON();
    
    /**
     * Turn the device OFF
     */
    public abstract void turnOFF();
    
    /**
     * Get the device name
     * @return Device name
     */
    public abstract String getName();
    
    /**
     * Get the device type
     * @return Device type
     */
    public abstract String getDevType();
    
    /**
     * Get the device state
     * @return Device state (ON/OFF)
     */
    public State getState() {
        return state;
    }
}