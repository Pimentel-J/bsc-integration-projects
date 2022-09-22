package lapr.project.model;

/**
 *
 */
public class Escooter extends Vehicle {

    /**
     * Escooter types.
     */
    public static enum Type {
        CITY, OFFROAD
    }

    /**
     * Escooter's type (city or off-road)
     */
    private Type type;

    /**
     * Escooter's maximum battery capacity (kWh; 2 dp)
     */
    private float maxBattery;

    /**
     * Escooter's actual battery capacity (% kWh)
     */
    private int actualBattery;

    /**
     * Escooter's motor power (kWh)
     */
    private float motor;
    
    /**
     * Escooter's time to finish charge in seconds
     */
    private int totalChargeTime;

    /**
     * Escooter's default type
     */
    private static final Type DEFAULT_TYPE = Type.CITY;

    /**
     * Escooter's default maximum battery capacity
     */
    private static final float DEFAULT_MAXBATTERY = .0f;

    /**
     * Escooter's default actual battery capacity
     */
    private static final int DEFAULT_ACTUALBATTERY = 0;

    /**
     * Escooter's default actual battery capacity
     */
    private static final int DEFAULT_MOTOR = 0;

    /**
     * Initializes escooter constructor by default
     */
    public Escooter() {
        super();
        type = DEFAULT_TYPE;
        maxBattery = DEFAULT_MAXBATTERY;
        actualBattery = DEFAULT_ACTUALBATTERY;
        motor = DEFAULT_MOTOR;
    }

    /**
     * Initializes escooter constructor with escooter's description, weight,
     * type, park latitude, park longitude, maximum battery capacity, actual
     * battery capacity, aerodynamic coefficient and frontal area
     *
     * @param descr - unique escooter reference
     * @param weight - escooter weight in kg (no decimal places)
     * @param type - type of escooter (city or off-road)
     * @param parkLat - latitude of the park where the escooter is parked
     * (decimal degrees)
     * @param parkLong - longitude of the park where the escooter is parked
     * (decimal degrees)
     * @param maxBattery - escooter maximum battery capacity in kWh (2 decimal
     * places)
     * @param actualBattery - escooter current battery capacity in percentage
     * (no decimal places)
     * @param aerodynCoeffic - escooter aerodynamic coefficient (2 decimal
     * places)
     * @param frontalArea - escooter frontal area in m^2 (1 decimal place)
     * @param motor - escooter motor power in kWh (2 decimal places)
     */
    public Escooter(String descr, int weight, Type type, double parkLat, double parkLong,
            float maxBattery, int actualBattery, float aerodynCoeffic, float frontalArea, float motor) {
        super(descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea);
        setType(type);
        setMaxBattery(maxBattery);
        setActualBattery(actualBattery);
        setMotor(motor);
    }
    
    /**
     * Initializes escooter constructor with escooter's description, weight,
     * type, park latitude, park longitude, maximum battery capacity, actual
     * battery capacity, aerodynamic coefficient and frontal area
     *
     * @param descr - unique escooter reference
     * @param weight - escooter weight in kg (no decimal places)
     * @param type - type of escooter (city or off-road)
     * @param parkLat - latitude of the park where the escooter is parked
     * (decimal degrees)
     * @param parkLong - longitude of the park where the escooter is parked
     * (decimal degrees)
     * @param maxBattery - escooter maximum battery capacity in kWh (2 decimal
     * places)
     * @param actualBattery - escooter current battery capacity in percentage
     * (no decimal places)
     * @param aerodynCoeffic - escooter aerodynamic coefficient (2 decimal
     * places)
     * @param frontalArea - escooter frontal area in m^2 (1 decimal place)
     * @param motor - escooter motor power in kWh (2 decimal places)
     * @param totalChargeTime - time to finish charge in seconds
     */
    public Escooter(String descr, int weight, Type type, double parkLat, 
            double parkLong, float maxBattery, int actualBattery, float aerodynCoeffic, 
            float frontalArea, float motor, int totalChargeTime) {
        super(descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea);
        setType(type);
        setMaxBattery(maxBattery);
        setActualBattery(actualBattery);
        setMotor(motor);
        this.totalChargeTime = totalChargeTime;
    }

    /**
     * Returns the escooter's type
     *
     * @return type - type of escooter (city or off-road)
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the escooter's maximum battery capacity
     *
     * @return maxBattery - maximum battery capacity (kWh)
     */
    public float getMaxBattery() {
        return maxBattery;
    }

    /**
     * Returns the escooter's actual battery capacity
     *
     * @return actualBattery - current battery capacity (%)
     */
    public int getActualBattery() {
        return actualBattery;
    }

    /**
     * Returns the escooter's motor power
     *
     * @return motor - motor power (kWh)
     */
    public float getMotor() {
        return motor;
    }
    
    /**
     * Returns the escooter's time to finish charge in seconds
     *
     * @return totalChargeTime - time to finish charge in seconds
     */
    public int getTotalChargeTime() {
        return totalChargeTime;
    }

    /**
     * Sets the escooter's type
     *
     * @param type - type of escooter (city or off-road)
     */
    public void setType(Type type) {
        if (type == null) {
            throw new IllegalArgumentException("Invalid type (cannot be null)!");
        }
        this.type = type;
    }

    /**
     * Sets the escooter's maximum battery capacity
     *
     * @param maxBattery - escooter maximum battery capacity in kWh (2 decimal
     * places)
     */
    public void setMaxBattery(float maxBattery) {
        if (maxBattery <= 0.0f) {
            throw new IllegalArgumentException("Invalid maximum battery! (" + maxBattery + ")");
        }
        this.maxBattery = maxBattery;
    }

    /**
     * Sets the escooter's actual battery capacity
     *
     * @param actualBattery - escooter current battery capacity in percentage
     * (no decimal places)
     */
    public void setActualBattery(int actualBattery) {
        if (actualBattery < 0) {
            throw new IllegalArgumentException("Invalid actual battery! (" + actualBattery + ")");
        }
        this.actualBattery = actualBattery;
    }

    /**
     * Sets the escooter's motor power
     *
     * @param motor power - escooter motor power in kWh (2 decimal places)
     */
    public void setMotor(float motor) {
        if (motor <= 0) {
            throw new IllegalArgumentException("Invalid motor power! (" + motor + ")");
        }
        this.motor = motor;
    }
    
    /**
     * Sets the escooter's time to finish charge in seconds
     *
     * @param totalChargeTime - time to finish charge in seconds
     */
    public void setTotalChargeTime(int totalChargeTime) {
        this.totalChargeTime = totalChargeTime;
    }

    /**
     * Return the actual battery in KwH
     *
     * @return actual battery in KwH
     */
    public float calcInKwHActBattery() {
        return maxBattery * actualBattery / 100;
    }

    /**
     * Prints escooter's description, weight, type, park latitude, park
     * longitude, maximum battery capacity, actual battery capacity, aerodynamic
     * coefficient, frontal area and motor power
     *
     * @return descr, weight, parkLat, parkLong, aerodynCoeffic, frontalArea,
     * motor
     */
    @Override
    public String toString() {
        return String.format("%s; %d; %s; %.6f; %.6f; %.2f; %d; %2f; %.1f, %f%n",
                this.getDescr(), this.getWeight(), type, this.getParkLat(), this.getParkLong(),
                maxBattery, actualBattery, this.getAerodynCoeffic(), this.getFrontalArea(), this.getMotor());
    }
}
