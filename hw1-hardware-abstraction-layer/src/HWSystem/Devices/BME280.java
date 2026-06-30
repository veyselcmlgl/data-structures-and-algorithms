package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.SPI;
import HWSystem.Protocols.I2C;

public class BME280 extends TempSensor {
    public BME280(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!(protocol instanceof I2C || protocol instanceof SPI)) {
            throw new IllegalArgumentException("BME280 is only compatible with I2C or SPI protocols");
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
        return "BME280";
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
