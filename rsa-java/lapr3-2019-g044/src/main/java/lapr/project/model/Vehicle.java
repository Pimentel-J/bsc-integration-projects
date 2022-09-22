package lapr.project.model;

import java.util.Objects;

/**
 * This class allows you to construct a class hierarchy to represent different
 * types of vehicles. It specifies members that are common to all classes in the
 * hierarchy.
 *
 *
 */
public class Vehicle implements Comparable<Vehicle> {

    /**
     * Vehicle's description (e.g. B031)
     */
    private String descr;

    /**
     * Vehicle's weight (kg; no dp)
     */
    private int weight;

    /**
     * Vehicle's park latitude (decimal degrees)
     */
    private double parkLat;

    /**
     * Vehicle's park longitude (decimal degrees)
     */
    private double parkLong;

    /**
     * Vehicle's aerodynamic coefficient (2 dp)
     */
    private float aerodynCoeffic;

    /**
     * Vehicle's frontal area (m^2; 1 dp)
     */
    private float frontalArea;

    /**
     * Vehicle's default description
     */
    private static final String DEFAULT_DESCR = "no descr";

    /**
     * Vehicle's default weight
     */
    private static final int DEFAULT_WEIGHT = 0;

    /**
     * Vehicle's default park latitude
     */
    private static final double DEFAULT_PARKLAT = 0;

    /**
     * Vehicle's default park longitude
     */
    private static final double DEFAULT_PARKLONG = 0;

    /**
     * Vehicle's default aerodynamic coefficient
     */
    private static final float DEFAULT_AERODYNCOEFFIC = 0;

    /**
     * Vehicle's default frontal area
     */
    private static final float DEFAULT_FRONTALAREA = 0;

    /**
     * Initializes vehicle constructor by default
     */
    public Vehicle() {
        descr = DEFAULT_DESCR;
        weight = DEFAULT_WEIGHT;
        parkLat = DEFAULT_PARKLAT;
        parkLong = DEFAULT_PARKLONG;
        aerodynCoeffic = DEFAULT_AERODYNCOEFFIC;
        frontalArea = DEFAULT_FRONTALAREA;
    }

    /**
     * Initializes vehicle constructor with vehicle's description, weight, park
     * latitude, park longitude, aerodynamic coefficient and frontal area
     *
     * @param descr - unique vehicle reference
     * @param weight - vehicle weight in kg (no decimal places)
     * @param parkLat - latitude of the park where the vehicle is parked
     * (decimal degrees)
     * @param parkLong - longitude of the park where the vehicle is parked
     * (decimal degrees)
     * @param aerodynCoeffic - vehicle aerodynamic coefficient (2 decimal
     * places)
     * @param frontalArea - vehicle frontal area in m^2 (1 decimal place)
     */
    public Vehicle(String descr, int weight, double parkLat, double parkLong,
            float aerodynCoeffic, float frontalArea) {
        setDescr(descr);
        setWeight(weight);
        setParkLat(parkLat);
        setParkLong(parkLong);
        setAerodynCoeffic(aerodynCoeffic);
        setFrontalArea(frontalArea);
    }

    /**
     * Returns the vehicle's description
     *
     * @return descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * Returns the vehicle's weight
     *
     * @return weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns the vehicle's park Latitude
     *
     * @return parkLat
     */
    public double getParkLat() {
        return parkLat;
    }

    /**
     * Returns the vehicle's park longitude
     *
     * @return parkLong
     */
    public double getParkLong() {
        return parkLong;
    }

    /**
     * Returns the vehicle's aerodynamic coefficient
     *
     * @return aerodynCoeffic
     */
    public float getAerodynCoeffic() {
        return aerodynCoeffic;
    }

    /**
     * Returns the vehicle's frontal area
     *
     * @return frontalArea
     */
    public float getFrontalArea() {
        return frontalArea;
    }

    /**
     * Sets the vehicle's description
     *
     * @param descr - unique vehicle reference
     */
    public void setDescr(String descr) {
        if (descr == null || descr.isEmpty()) {
            throw new IllegalArgumentException("Invalid description (cannot be null or empty)!");
        }
        this.descr = descr;
    }

    /**
     * Sets the vehicle's weight
     *
     * @param weight - vehicle weight in kg (no decimal places)
     */
    public void setWeight(int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Invalid weight! (" + weight + ")");
        }
        this.weight = weight;
    }

    /**
     * Sets the vehicle's park latitude
     *
     * @param parkLat - latitude of the park where the vehicle is parked
     * (decimal degrees)
     */
    public void setParkLat(double parkLat) {
        if (parkLat < -90 || parkLat > 90) {
            throw new IllegalArgumentException("Invalid latitude! (" + parkLat + ")");
        }
        this.parkLat = parkLat;
    }

    /**
     * Sets the vehicle's park longitude
     *
     * @param parkLong - longitude of the park where the vehicle is parked
     * (decimal degrees)
     */
    public void setParkLong(double parkLong) {
        if (parkLong < -180 || parkLong > 180) {
            throw new IllegalArgumentException("Invalid longitude! (" + parkLong + ")");
        }
        this.parkLong = parkLong;
    }

    /**
     * Sets the vehicle's aerodynamic coefficient
     *
     * @param aerodynCoeffic - vehicle aerodynamic coefficient (2 decimal
     * places)
     */
    public void setAerodynCoeffic(float aerodynCoeffic) {
        if (aerodynCoeffic <= 0.0f) {
            throw new IllegalArgumentException("Invalid aerodynamic coefficient! (" + aerodynCoeffic + ")");
        }
        this.aerodynCoeffic = aerodynCoeffic;
    }

    /**
     * Sets the vehicle's frontal area
     *
     * @param frontalArea - vehicle frontal area in m^2 (1 decimal place)
     */
    public void setFrontalArea(float frontalArea) {
        if (frontalArea <= 0.0f) {
            throw new IllegalArgumentException("Invalid frontal area! (" + frontalArea + ")");
        }
        this.frontalArea = frontalArea;
    }

    @Override
    public int hashCode() {
        return this.descr.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Vehicle other = (Vehicle) obj;
        return Objects.equals(this.descr, other.descr);
    }

    /**
     * Prints vehicle's description, weight, park latitude, park longitude,
     * aerodynamic coefficient and frontal area
     *
     * @return descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea
     */
    @Override
    public String toString() {
        return String.format("%s; %d; %.6f; %.6f; %.2f; %.1f%n",
                descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea);
    }

    /**
     * Alphabetically compares the description of the vehicle and the other
     * received by parameter
     *
     * @param otherVehicle - vehicle to be compared with the original vehicle
     * @return 1, 0 or -1
     */
    @Override
    public int compareTo(Vehicle otherVehicle) {
        String thisDescr = this.getDescr();
        String otherDescr = otherVehicle.getDescr();

        return thisDescr.compareTo(otherDescr);
    }
}
