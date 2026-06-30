package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.I2C;

public class PCA9685 extends MotorDriver {
    public PCA9685(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!(protocol instanceof I2C)) {
            throw new IllegalArgumentException("PCA9685 is only compatible with I2C protocol");
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
        return "PCA9685";
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