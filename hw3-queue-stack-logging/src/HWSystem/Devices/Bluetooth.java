package HWSystem.Devices;

import HWSystem.Protocols.Protocol;
// import HWSystem.Protocols.UART;

/**
 * Bluetooth Wireless I/O
 */
public class Bluetooth extends WirelessIO {
    /**
     * Constructor for Bluetooth
     * @param protocol Protocol to use for communication
     */
    public Bluetooth(Protocol protocol) {
        super(protocol);
        // Check if protocol is compatible
        if (!protocol.getProtocolName().equals("UART")) {
            throw new IllegalArgumentException("Bluetooth is only compatible with UART protocol");
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
        return "Bluetooth";
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