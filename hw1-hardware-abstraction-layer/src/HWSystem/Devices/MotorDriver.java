package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

// Abstract MotorDriver class
public abstract class MotorDriver extends Device {
    public MotorDriver(Protocol protocol) {
        super(protocol);
    }

    public abstract void setMotorSpeed(int speed);

    @Override
    public String getDevType() {
        return "MotorDriver";
    }
}