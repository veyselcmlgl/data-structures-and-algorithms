package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

/**
 * Abstract TempSensor class
 * Represents temperature sensors in the system
 */
public abstract class TempSensor extends Sensor {
    /**
     * Constructor for TempSensor
     * @param protocol Protocol to use for communication
     */
    public TempSensor(Protocol protocol) {
        super(protocol);
    }

    /**
     * Get temperature reading
     * @return Temperature value as a Float
     */
    public abstract Float getTemp();

    @Override
    public String getSensType() {
        return "TempSensor";
    }

    @Override
    public String data2String() {
        return String.format("Temperature: %.2fC", getTemp());
    }
}