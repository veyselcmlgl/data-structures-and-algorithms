package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
// import HWSystem.Protocols.I2C;

/**
 * MPU6050 IMU Sensor
 */
public class MPU6050 extends IMUSensor {
    /**
     * Constructor for MPU6050
     * @param protocol Protocol to use for communication
     */
    public MPU6050(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!protocol.getProtocolName().equals("I2C")) {
            throw new IllegalArgumentException("MPU6050 is only compatible with I2C protocol");
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
        return "MPU6050";
    }

    @Override
    public Float getAccel() {
        if(this.state == State.ON){
            protocol.read();
            // Return constant acceleration as per the new requirements
            return 1.00f;
        } else {
            return null;
        }
    }

    @Override
    public Float getRot() {
        if(this.state == State.ON){
            protocol.read();
            // Return constant rotation as per the new requirements
            return 0.50f;
        } else {
            return null;
        }
    }
}