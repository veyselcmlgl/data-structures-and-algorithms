package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
// import HWSystem.Protocols.OneWire;

/**
 * DHT11 Temperature Sensor
 */
public class DHT11 extends TempSensor {
    /**
     * Constructor for DHT11
     * @param protocol Protocol to use for communication
     */
    public DHT11(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!protocol.getProtocolName().equals("OneWire")) {
            throw new IllegalArgumentException("DHT11 is only compatible with OneWire protocol");
        }
    }

    @Override
    public void turnON() {
        System.out.println(getName() + ": Turning ON.");
        protocol.write("turnON");
        this.state = State.ON;
    }

    @Override
    public void turnOFF() {
        System.out.println(getName() + ": Turning OFF.");
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
            // Return constant temperature as per the new requirements
            return 24.00f;
        } else {
            return null;
        }
    }
}