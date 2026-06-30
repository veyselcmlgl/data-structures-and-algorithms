package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

// Abstract WirelessIO class
public abstract class WirelessIO extends Device {
    public WirelessIO(Protocol protocol) {
        super(protocol);
    }

    public abstract String recvData();

    public abstract void sendData(String data);

    @Override
    public String getDevType() {
        return "WirelessIO";
    }
}
