package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

// Abstract IMUSensor class
public abstract class IMUSensor extends Sensor {
    public IMUSensor(Protocol protocol) {
        super(protocol);
    }

    public abstract Float getAccel();
    public abstract Float getRot();

    @Override
    public String getSensType() {
        return "IMUSensor";
    }

    @Override
    public String data2String() {
        return String.format("Accel: %.2f, Rot: %.2f", getAccel(), getRot());
    }
}