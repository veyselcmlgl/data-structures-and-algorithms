package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
// import HWSystem.Protocols.I2C;

/**
 * LCD Display
 */
public class LCD extends Display {
    /**
     * Constructor for LCD
     * @param protocol Protocol to use for communication
     */
    public LCD(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!protocol.getProtocolName().equals("I2C")) {
            throw new IllegalArgumentException("LCD is only compatible with I2C protocol");
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
        return "LCD";
    }

    @Override
    public void printData(String data) {
        if (this.state == State.ON) {
            protocol.write(data);
        } else {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
        }
    }
}