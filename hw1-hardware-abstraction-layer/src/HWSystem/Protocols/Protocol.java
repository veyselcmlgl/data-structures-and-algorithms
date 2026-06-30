package HWSystem.Protocols;

// Protocol Interface
public interface Protocol {
    String getProtocolName();
    String read();
    void write(String data);
}