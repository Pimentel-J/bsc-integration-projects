/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;


/**
 * Describes a park.
 */
public class Park implements Comparable<Park>, Point {

    /**
     * Identifier of the park.
     */
    private String id;
    /**
     * Latitude of the park.
     */
    private double latitude;
    /**
     * Longitude of the park.
     */
    private double longitude;
    /**
     * Elevation/altitude (in metres) of the park.
     */
    private int elevation;
    /**
     * Descriptive name for the park.
     */
    private String description;
    /**
     * Maximum park capability for bicycles.
     */
    private int maxNumberBicycles;
    /**
     * Maximum park capability for escooters.
     */
    private int maxNumberEscooters;
    /**
     * Park input voltage (in Volts).
     */
    private int inputVoltage;
    /**
     * Park input current (in Amperes).
     */
    private int inputCurrent;

    /**
     * Empty constructor.
     */
    public Park() {
        
    }

    /**
     * Full constructor.
     *
     * @param id identifier of the park.
     * @param latitude latitude of the park.
     * @param longitude longitude of the park.
     * @param elevation elevation/altitude (in metres) of the park.
     * @param description descriptive name for the park.
     * @param maxNumberBicycles maximum park capability for bicycles.
     * @param maxNumberEscooters maximum park capability for escooters.
     * @param inputVoltage park input voltage (in Volts).
     * @param inputCurrent park input current (in Amperes).
     */
    public Park(String id, double latitude, double longitude, int elevation,
            String description, int maxNumberBicycles, int maxNumberEscooters, int inputVoltage, int inputCurrent) {

        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
        setElevation(elevation);
        setDescription(description);
        setMaxNumberBicycles(maxNumberBicycles);
        setMaxNumberEscooters(maxNumberEscooters);
        setInputVoltage(inputVoltage);
        setInputCurrent(inputCurrent);
    }

    /**
     * Returns the id of the park.
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the latitude of the park.
     *
     * @return
     */
    @Override
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the longitude of the park.
     *
     * @return
     */
    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns the elevation of the park.
     *
     * @return
     */
    @Override
    public int getElevation() {
        return elevation;
    }

    /**
     * Returns the description of the park.
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the park capability for bicycles.
     *
     * @return
     */
    public int getMaxNumberBicycles() {
        return maxNumberBicycles;
    }

    /**
     * Returns the park capability for escooters.
     *
     * @return
     */
    public int getMaxNumberEscooters() {
        return maxNumberEscooters;
    }

    /**
     * Returns the park input voltage.
     *
     * @return
     */
    public int getInputVoltage() {
        return inputVoltage;
    }

    /**
     * Returns the park input current.
     *
     * @return
     */
    public int getInputCurrent() {
        return inputCurrent;
    }

    /**
     * Sets park id to the value passed as parameter, if valid.
     *
     * @param id new identifier of the park.
     */
    public final void setId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid Park ID!");
        }
        this.id = id;
    }

    /**
     * Sets park latitude to the value passed as parameter, if valid.
     *
     * @param latitude new latitude of the park.
     */
    public final void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid Park latitude!");
        }
        this.latitude = latitude;
    }

    /**
     * Sets park longitude to the value passed as parameter, if valid.
     *
     * @param longitude new longitude of the park.
     */
    public final void setLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid Park longitude!");
        }
        this.longitude = longitude;
    }

    /**
     * Sets park elevation to the value passed as parameter, if valid.
     *
     * @param elevation new elevation/altitude (in metres) of the park.
     */
    public final void setElevation(int elevation) {
        this.elevation = elevation;
    }

    /**
     * Sets park description to the value passed as parameter, if valid.
     *
     * @param description new descriptive name for the park.
     */
    public final void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Invalid Park description!");
        }
        this.description = description;
    }

    /**
     * Sets park capability for bicycles to the value passed as parameter, if
     * valid.
     *
     * @param maxNumberBicycles new maximum park capability for bicycles.
     */
    public final void setMaxNumberBicycles(int maxNumberBicycles) {
        if (maxNumberBicycles <= 0) {
            throw new IllegalArgumentException("Invalid Park capability for bicycles!");
        }
        this.maxNumberBicycles = maxNumberBicycles;
    }

    /**
     * Sets park capability for escooters to the value passed as parameter, if
     * valid.
     *
     * @param maxNumberEscooters new maximum park capability for escooters.
     */
    public final void setMaxNumberEscooters(int maxNumberEscooters) {
        if (maxNumberEscooters <= 0) {
            throw new IllegalArgumentException("Invalid Park capability for escooters!");
        }
        this.maxNumberEscooters = maxNumberEscooters;
    }

    /**
     * Sets park input voltage to the value passed as parameter, if valid.
     *
     * @param inputVoltage new park input voltage (in Volts).
     */
    public final void setInputVoltage(int inputVoltage) {
        if (inputVoltage <= 0) {
            throw new IllegalArgumentException("Invalid Park input voltage!");
        }
        this.inputVoltage = inputVoltage;
    }

    /**
     * Sets park input current to the value passed as parameter, if valid.
     *
     * @param inputCurrent new park input current (in Amperes).
     */
    public final void setInputCurrent(int inputCurrent) {
        if (inputCurrent <= 0) {
            throw new IllegalArgumentException("Invalid Park input current!");
        }
        this.inputCurrent = inputCurrent;
    }

    /**
     * Method to print Parks
     *
     * @return string
     */
    @Override
    public String toString() {
        return "Park: \n" + "id=" + id + "\n laititude=" + latitude + "\n longitude=" + longitude + "\n elevation=" + elevation + "\n description=" + description + "\n Bicycles=" + maxNumberBicycles + "\n Escooters=" + maxNumberEscooters + "\n volatge=" + inputVoltage + "\n current=" + inputCurrent + '.';
    }

    /**
     * Compares the id (in ascending order) of the park and the other
     * received by parameter
     *
     * @param otherPark - park to be compared with the original park
     * @return 1, 0 or -1
     */
    @Override
    public int compareTo(Park otherPark) {
        String thisId = this.getId();
        String otherId = otherPark.getId();

        return thisId.compareTo(otherId);
    }
}
