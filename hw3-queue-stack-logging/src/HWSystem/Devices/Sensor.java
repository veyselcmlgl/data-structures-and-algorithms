package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

/**
 * Abstract Sensor class
 * Base class for all sensor types in the system
 */
public abstract class Sensor extends Device {
    /**
     * Constructor for Sensor
     * @param protocol Protocol to use for communication
     */
    public Sensor(Protocol protocol) {
        super(protocol);
    }

    /**
     * Get the sensor's specific type
     * @return Sensor type as a string
     */
    public abstract String getSensType();
    
    /**
     * Convert sensor data to a formatted string
     * @return Formatted sensor data
     */
    public abstract String data2String();

    @Override
    public String getDevType() {
        return getSensType() + " Sensor";
    }
}