package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
// import HWSystem.Protocols.SPI;
// import HWSystem.Protocols.I2C;

/**
 * BME280 Temperature Sensor
 */
public class BME280 extends TempSensor {
    /**
     * Constructor for BME280
     * @param protocol Protocol to use for communication
     */
    public BME280(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        String protocolName = protocol.getProtocolName();
        if (!("I2C".equals(protocolName) || "SPI".equals(protocolName))) {
            throw new IllegalArgumentException("BME280 is only compatible with I2C or SPI protocols");
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
        return "BME280";
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