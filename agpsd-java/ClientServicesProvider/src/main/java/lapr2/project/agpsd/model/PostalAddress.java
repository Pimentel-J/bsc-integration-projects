package lapr2.project.agpsd.model;

import java.util.Objects;

/**
 *
 *
 */
public class PostalAddress {

    private String place;
    private ZipCode zipCode;
    private String location;
    
    private static final String DEFAULT_PLACE = "no place";
    private static final String DEFAULT_LOCATION = "no location";

    public PostalAddress() {
        this.place = DEFAULT_PLACE;
        this.location = DEFAULT_LOCATION;
    }

    /**
     *
     * @param place
     * @param location
     */
    public PostalAddress(String place, String location) {
        if ((place == null) || (location == null)
                || (place.isEmpty()) || (location.isEmpty())) {
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        }
        this.place = place;
        this.location = location;
        this.zipCode = new ZipCode();
    }

    /**
     *
     * @param place
     * @param zipCode
     * @param location
     */
    public PostalAddress(String place, String zipCode, String location) {
        if ((place == null) || (location == null)
                || (place.isEmpty()) || (location.isEmpty())) {
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        }
        this.place = place;
        this.location = location;
        this.zipCode = new ZipCode(zipCode);
    }
    
    /**
     *
     * @return zipCode
     */
    public ZipCode getZipCode() {
        return this.zipCode;
    }

    /**
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hash(this.place, this.location);
        return hash;
    }

    /**
     *
     * @param o
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        // Inspirado em https://www.sitepoint.com/implement-javas-equals-method-correctly/

        // self check
        if (this == o) {
            return true;
        }
        // null check
        if (o == null) {
            return false;
        }
        // type check and cast
        if (getClass() != o.getClass()) {
            return false;
        }
        // field comparison
        PostalAddress obj = (PostalAddress) o;
        return (Objects.equals(place, obj.place)
                && Objects.equals(location, obj.location));
    }

    /**
     *
     * @return Postal Address: place, zip code, location
     */
    @Override
    public String toString() {
        return String.format("Postal Address: %s, %s, %s", this.place, this.zipCode, this.location);
    }

}
