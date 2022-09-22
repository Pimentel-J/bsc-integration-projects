/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.Locale;
import lapr.project.utils.Utils;

/**
 *
 *
 */
public class Path {

    /**
     * Latitude A
     */
    private double latA;
    /**
     * Longitude A
     */
    private double longA;
    /**
     * Latitude B
     */
    private double latB;
    /**
     * Longitude B
     */
    private double longB;
    /**
     * Kinetic coefficient
     */
    private double k_coeff;
    /**
     * wind Direction
     */
    private int wind_dir;
    /**
     * Wind speed (m/s)
     */
    private float wind_speed;
    /**
     * Default Kinetic coefficient
     */
    private final double K_COEFF_DEFAULT = 0;
    /**
     * Default Wind direction
     */
    private final int WIND_DIR_DEFAULT = 0;
    /**
     * Default Wind speed
     */
    private final float WIND_SPEED_DEFAULT = 0;

    /**
     * Empty constuctor
     */
    public Path() {
        this.wind_dir = WIND_DIR_DEFAULT;
        this.wind_speed = WIND_SPEED_DEFAULT;
        this.k_coeff = K_COEFF_DEFAULT;
    }

    /**
     * Constructor with all parameters
     *
     * @param latA latitude point A
     * @param longA longitude point A
     * @param latB latitude point B
     * @param longB longitude point B
     * @param k_coeff Kinetic coefficient
     * @param wind_dir wind direction
     * @param wind_speed wind speed
     */
    public Path(double latA, double longA, double latB, double longB, double k_coeff, int wind_dir, float wind_speed) {
        setLatA(latA);
        setLongA(longA);
        setLatB(latB);
        setLongB(longB);
        setK_coeff(k_coeff);
        setWind_dir(wind_dir);
        setWind_speed(wind_speed);
    }

    /**
     * Get Latitude point A
     *
     * @return latitudeA
     */
    public double getLatA() {
        return latA;
    }

    /**
     * Set Latitude point A
     *
     * @param latA latitudeA
     */
    public final void setLatA(double latA) {
        if (validLat(latA)) {
            this.latA = latA;
            return;
        }
        throw new IllegalArgumentException("Invalid latitudeA (" + latA + ")");
    }

    /**
     * Get Longitude point A
     *
     * @return longitudeA
     */
    public double getLongA() {
        return longA;
    }

    /**
     * Set Longitude point A
     *
     * @param longA longitudeA
     */
    public final void setLongA(double longA) {
        if (validLong(longA)) {
            this.longA = longA;
            return;
        }
        throw new IllegalArgumentException("Invalid longitudeA (" + longA + ")");
    }

    /**
     * Get Latitude point B
     *
     * @return latitudeB
     */
    public double getLatB() {
        return latB;
    }

    /**
     * Set Latitude point B
     *
     * @param latB latitudeB
     */
    public final void setLatB(double latB) {
        if (validLat(latB)) {
            this.latB = latB;
            return;
        }
        throw new IllegalArgumentException("Invalid latitudeB (" + latB + ")");
    }

    /**
     * Get Longitude point B
     *
     * @return longitudeB
     */
    public double getLongB() {
        return longB;
    }

    /**
     * Set Longitude point B
     *
     * @param longB longitudeB
     */
    public final void setLongB(double longB) {
        if (validLong(longB)) {
            this.longB = longB;
            return;
        }
        throw new IllegalArgumentException("Invalid longitudeB (" + longB + ")");
    }

    /**
     * Get Kinetic coefficient
     *
     * @return Kinetic coefficient
     */
    public double getK_coeff() {
        return k_coeff;
    }

    /**
     * Set Kinetic coefficient
     *
     * @param k_coeff Kinetic coefficient
     */
    public final void setK_coeff(double k_coeff) {
        if (k_coeff >= 0) {
            this.k_coeff = k_coeff;
            return;
        }
        throw new IllegalArgumentException("Invalid Kinetic coefficient! (" + k_coeff + ")");
    }

    /**
     * Get Wind direction
     *
     * @return wind direction
     */
    public int getWind_dir() {
        return wind_dir;
    }

    /**
     * Set wind direction
     *
     * @param wind_dir wind direction
     */
    public final void setWind_dir(int wind_dir) {
        if (wind_dir >= 0 && wind_dir <= 360) {
            this.wind_dir = wind_dir;
            return;
        }
        throw new IllegalArgumentException("Invalid wind direction ! (" + wind_dir + ")");
    }

    /**
     * Get wind speed
     *
     * @return wind speed
     */
    public float getWind_speed() {
        return wind_speed;
    }

    /**
     * Set wind speed
     *
     * @param wind_speed wind speed
     */
    public final void setWind_speed(float wind_speed) {
        if (wind_speed >= 0) {
            this.wind_speed = wind_speed;
            return;
        }
        throw new IllegalArgumentException("Invalid wind speed ! (" + wind_speed + ")");
    }

    /**
     * Valid if latitude are valid
     *
     * @param lat latitude
     * @return true or false
     */
    private boolean validLat(double lat) {
        return (lat > -90 && lat < 90);
    }

    /**
     * Valid if longitude are valid
     *
     * @param longn longitude
     * @return true or false
     */
    private boolean validLong(double longn) {
        return (longn > -180 && longn < 180);
    }

    /**
     * Return the distance of the path
     *
     * @return distance in M
     */
    public double getDistance() {
        return Utils.distance(latA, longA, latB, longB);
    }

    /**
     * Calc the energy for this path
     *
     * @param avgSpeed average speed user, bicycle or escooter
     * @param slope slope
     * @return energy in watts
     */
    public double calcEnergy(double avgSpeed, double slope) {
        double result = Utils.calcPower(Utils.DEFAULT_MASS, slope, wind_speed, avgSpeed, wind_dir);
        return result;
    }

    /**
     * Hascode for this path
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.latA) ^ (Double.doubleToLongBits(this.latA) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.longA) ^ (Double.doubleToLongBits(this.longA) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.latB) ^ (Double.doubleToLongBits(this.latB) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.longB) ^ (Double.doubleToLongBits(this.longB) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.k_coeff) ^ (Double.doubleToLongBits(this.k_coeff) >>> 32));
        hash = 71 * hash + this.wind_dir;
        hash = 71 * hash + Float.floatToIntBits(this.wind_speed);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.K_COEFF_DEFAULT) ^ (Double.doubleToLongBits(this.K_COEFF_DEFAULT) >>> 32));
        hash = 71 * hash + this.WIND_DIR_DEFAULT;
        hash = 71 * hash + Float.floatToIntBits(this.WIND_SPEED_DEFAULT);
        return hash;
    }

    /**
     * Compare if one object is equal to this object Path
     *
     * @param obj object
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Path other = (Path) obj;
        if (Double.doubleToLongBits(this.latA) != Double.doubleToLongBits(other.latA)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longA) != Double.doubleToLongBits(other.longA)) {
            return false;
        }
        if (Double.doubleToLongBits(this.latB) != Double.doubleToLongBits(other.latB)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longB) != Double.doubleToLongBits(other.longB)) {
            return false;
        }
        if (Double.doubleToLongBits(this.k_coeff) != Double.doubleToLongBits(other.k_coeff)) {
            return false;
        }
        if (this.wind_dir != other.wind_dir) {
            return false;
        }
        if (Float.floatToIntBits(this.wind_speed) != Float.floatToIntBits(other.wind_speed)) {
            return false;
        }
        return true;
    }

    /**
     * Return one string with all information about the object
     *
     * @return All information about the object
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Path: [latA=%.6f], [longA=%.6f], [latB=%.6f], [longB=%.6f],"
                + " [coef_k=%.3f], [wind_dir=%d], [wind_speed=%.1f]\n",
                latA, longA, latB, longB, k_coeff, wind_dir, wind_speed);
    }

}
