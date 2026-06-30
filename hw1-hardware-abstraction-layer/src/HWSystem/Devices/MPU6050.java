package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
import HWSystem.Protocols.I2C;

public class MPU6050 extends IMUSensor {
    public MPU6050(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!(protocol instanceof I2C)) {
            throw new IllegalArgumentException("MPU6050 is only compatible with I2C protocol");
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
        return "MPU6050";
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
