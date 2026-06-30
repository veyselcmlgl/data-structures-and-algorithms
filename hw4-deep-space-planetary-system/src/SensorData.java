/**
 * Represents sensor data for celestial bodies.
 */
public class SensorData {
    private double temperature; // In Kelvin
    private double pressure;    // In Pascals
    private double humidity;    // Percentage (0-100)
    private double radiation;   // In Sieverts

    /**
     * Constructor for creating sensor data.
     * @param temperature Temperature in Kelvin
     * @param pressure Pressure in Pascals
     * @param humidity Humidity percentage (0-100)
     * @param radiation Radiation in Sieverts
     */
    public SensorData(double temperature, double pressure, double humidity, double radiation) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.radiation = radiation;
    }

    /**
     * Gets the temperature.
     * @return Temperature in Kelvin
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Gets the pressure.
     * @return Pressure in Pascals
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * Gets the humidity.
     * @return Humidity percentage
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Gets the radiation.
     * @return Radiation in Sieverts
     */
    public double getRadiation() {
        return radiation;
    }
}