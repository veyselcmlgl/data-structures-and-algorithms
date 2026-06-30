package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
// import HWSystem.Protocols.SPI;
// import HWSystem.Protocols.UART;

/**
 * GY951 IMU Sensor
 */
public class GY951 extends IMUSensor {
    /**
     * Constructor for GY951
     * @param protocol Protocol to use for communication
     */
    public GY951(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        String protocolName = protocol.getProtocolName();
        if (!(protocolName.equals("SPI") || protocolName.equals("UART"))) {
            throw new IllegalArgumentException("GY951 is only compatible with SPI or UART protocols");
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
        return "GY951";
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