package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

// Abstract Display class
public abstract class Display extends Device {
    public Display(Protocol protocol) {
        super(protocol);
    }

    public abstract void printData(String data);

    @Override
    public String getDevType() {
        return "Display";
    }
}
