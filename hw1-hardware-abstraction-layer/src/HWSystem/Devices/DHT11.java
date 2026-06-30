package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.OneWire;

public class DHT11 extends TempSensor {
    public DHT11(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!(protocol instanceof OneWire)) {
            throw new IllegalArgumentException("DHT11 is only compatible with OneWire protocol");
        }
    }

    @Override
    public void turnON() {
        System.out.println(getName() + ": Turning ON");
        protocol.write("turnON");
        this.state = State.ON;
    }

    @Override
    public void turnOFF() {
        System.out.println(getName() + ": Turning OFF");
        protocol.write("turnOFF");
        this.state = State.OFF;
    }

    @Override
    public String getName() {
        return "DHT11";
    }

    @Override
    public Float getTemp() {
        if(this.state == State.ON){
            protocol.read();
            return (float) (Math.random() * 50); // Random temperature (0-50°C)
        } else {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return null;
        }
    }
}