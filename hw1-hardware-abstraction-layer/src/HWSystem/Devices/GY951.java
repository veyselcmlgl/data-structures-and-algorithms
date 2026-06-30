package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.SPI;
import HWSystem.Protocols.UART;

public class GY951 extends IMUSensor {
    public GY951(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!(protocol instanceof SPI || protocol instanceof UART)) {
            throw new IllegalArgumentException("GY951 is only compatible with SPI or UART protocols");
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
        return "GY951";
    }

    public Float getAccel() {
        if(this.state == State.ON){
            protocol.read();
            return (float) (Math.random() * 10); // Random acceleration (0-10 m/s²)
        } else {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return null;
        }
    }

    public Float getRot() {
        if(this.state == State.ON){
            protocol.read();
            return (float) (Math.random() * 360); // Random rotation (0-360 degrees)
        } else {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return null;
        }
    }
}
