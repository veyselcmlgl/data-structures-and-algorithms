package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

// Abstract TempSensor class
public abstract class TempSensor extends Sensor {
    public TempSensor(Protocol protocol) {
        super(protocol);
    }

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