import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the space tree (star, planet, or moon).
 */
public class SpaceNode {
    private String name;
    private String type;
    private SensorData sensorData;
    private List<SpaceNode> children;

    /**
     * Constructor for creating a new space node.
     * @param name Name of the celestial body
     * @param type Type of celestial body (Star, Planet, Moon)
     * @param sensorData Sensor data for the celestial body
     */
    public SpaceNode(String name, String type, SensorData sensorData) {
        this.name = name;
        this.type = type;
        this.sensorData = sensorData;
        this.children = new ArrayList<>();
    }

    /**
     * Gets the name of the node.
     * @return Node name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the node.
     * @return Node type (Star, Planet, Moon)
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the sensor data of the node.
     * @return Node's sensor data
     */
    public SensorData getSensorData() {
        return sensorData;
    }

    /**
     * Gets the children of the node.
     * @return List of child nodes
     */
    public List<SpaceNode> getChildren() {
        return children;
    }

    /**
     * Adds a child node to this node.
     * @param child Child node to add
     */
    public void addChild(SpaceNode child) {
        children.add(child);
    }

    /**
     * Returns a string representation of the node.
     * @return String containing node details
     */
    @Override
    public String toString() {
        return String.format("%s (%s): %.1f Kelvin, %.1f Pascals, %.1f%% Humidity, %.4f Sieverts", 
            name, type, sensorData.getTemperature(), sensorData.getPressure(), 
            sensorData.getHumidity(), sensorData.getRadiation());
    }
}