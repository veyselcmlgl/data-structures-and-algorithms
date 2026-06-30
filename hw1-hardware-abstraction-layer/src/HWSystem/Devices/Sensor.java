package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

// Abstract Sensor class
public abstract class Sensor extends Device {
    public Sensor(Protocol protocol) {
        super(protocol);
    }

    public abstract String getSensType();
    public abstract String data2String();

    @Override
    public String getDevType() {
        return getSensType() + " Sensor";
    }
}
