package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
// import HWSystem.Protocols.SPI;
// import HWSystem.Protocols.UART;

/**
 * Wifi Wireless I/O
 */
public class Wifi extends WirelessIO {
    /**
     * Constructor for Wifi
     * @param protocol Protocol to use for communication
     */
    public Wifi(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        String protocolName = protocol.getProtocolName();
        if (!(protocolName.equals("SPI") || protocolName.equals("UART"))) {
            throw new IllegalArgumentException("Wifi is only compatible with SPI or UART protocols");
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
        return "Wifi";
    }

    @Override
    public String recvData() {
        if (this.state == State.ON) {
            return protocol.read();
        } else {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return null;
        }
    }

    @Override
    public void sendData(String data) {
        if (this.state == State.ON) {
            protocol.write(data);
        } else {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
        }
    }
}