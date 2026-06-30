package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

/**
 * Abstract MotorDriver class
 * Base class for all motor driver devices in the system
 */
public abstract class MotorDriver extends Device {
    /**
     * Constructor for MotorDriver
     * @param protocol Protocol to use for communication
     */
    public MotorDriver(Protocol protocol) {
        super(protocol);
    }

    /**
     * Set the motor speed
     * @param speed Speed value
     */
    public abstract void setMotorSpeed(int speed);

    @Override
    public String getDevType() {
        return "MotorDriver";
    }
}