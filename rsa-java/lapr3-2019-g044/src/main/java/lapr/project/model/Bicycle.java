package lapr.project.model;

/**
 *
 */
public class Bicycle extends Vehicle {

    /**
     * Bicycle's wheel size in inches
     */
    private int wheelSize;

    /**
     * Bicycle's default wheel size
     */
    private static final int DEFAULT_WHEELSIZE = 0;

    /**
     * Initializes bicycle constructor by default
     */
    public Bicycle() {
        super();
        wheelSize = DEFAULT_WHEELSIZE;
    }

    /**
     * Initializes bicycle constructor with bicycle's description, weight, park
     * latitude, park longitude, aerodynamic coefficient and frontal area
     *
     * @param descr - unique bicycle reference
     * @param weight - bicycle weight in kg (no decimal places)
     * @param parkLat - latitude of the park where the bicycle is parked
     * (decimal degrees)
     * @param parkLong - longitude of the park where the bicycle is parked
     * (decimal degrees)
     * @param aerodynCoeffic - bicycle aerodynamic coefficient (2 decimal
     * places)
     * @param frontalArea - bicycle frontal area in m^2 (1 decimal place)
     * @param wheelSize - bicycle wheel size in inches
     */
    public Bicycle(String descr, int weight, double parkLat, double parkLong,
            float aerodynCoeffic, float frontalArea, int wheelSize) {
        super(descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea);
        setWheelSize(wheelSize);
    }

    /**
     * Returns the bicycle's wheel size
     *
     * @return wheel size
     */
    public int getWheelSize() {
        return wheelSize;
    }

    /**
     * Sets the bicycle's maximum battery capacity
     *
     * @param wheelSize - bicycle wheel size in inches
     */
    public void setWheelSize(int wheelSize) {
        if (wheelSize <= 0) {
            throw new IllegalArgumentException("Invalid wheel size! (" + wheelSize + ")");
        }
        this.wheelSize = wheelSize;
    }
}
