package HWSystem.Devices;

import HWSystem.Protocols.Protocol;

// Abstract Device class
public abstract class Device {
    public Protocol protocol;
    protected State state;

    public Device(Protocol protocol) {
        this.protocol = protocol;
        this.state = State.OFF;
    }

    public abstract void turnON();
    public abstract void turnOFF();
    public abstract String getName();
    public abstract String getDevType();
    
    public State getState() {
        return state;
    }
}