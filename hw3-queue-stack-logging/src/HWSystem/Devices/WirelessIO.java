package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

/**
 * Abstract WirelessIO class
 * Base class for all wireless I/O devices in the system
 */
public abstract class WirelessIO extends Device {
    /**
     * Constructor for WirelessIO
     * @param protocol Protocol to use for communication
     */
    public WirelessIO(Protocol protocol) {
        super(protocol);
    }

    /**
     * Receive data wirelessly
     * @return Received data as a string
     */
    public abstract String recvData();

    /**
     * Send data wirelessly
     * @param data Data to send
     */
    public abstract void sendData(String data);

    @Override
    public String getDevType() {
        return "WirelessIO";
    }
}