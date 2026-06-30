package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.SPI;

public class SparkFunMD extends MotorDriver {
    public SparkFunMD(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!(protocol instanceof SPI)) {
            throw new IllegalArgumentException("SparkFunMD is only compatible with SPI protocol");
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
        return "SparkFunMD";
    }

    @Override
    public void setMotorSpeed(int speed) {
        if (this.state == State.ON) {
            protocol.write(String.valueOf(speed));
        } else {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
        }
    }
}