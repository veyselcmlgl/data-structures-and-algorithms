package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

/**
 * Abstract IMUSensor class
 * Represents IMU (Inertial Measurement Unit) sensors in the system
 */
public abstract class IMUSensor extends Sensor {
    /**
     * Constructor for IMUSensor
     * @param protocol Protocol to use for communication
     */
    public IMUSensor(Protocol protocol) {
        super(protocol);
    }

    /**
     * Get acceleration reading
     * @return Acceleration value as a Float
     */
    public abstract Float getAccel();
    
    /**
     * Get rotation reading
     * @return Rotation value as a Float
     */
    public abstract Float getRot();

    @Override
    public String getSensType() {
        return "IMUSensor";
    }

    @Override
    public String data2String() {
        return String.format("Acceleration: %.2f, Rotation: %.2f", getAccel(), getRot());
    }
}