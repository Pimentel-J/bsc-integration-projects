package lapr2.project.agpsd.model;

import java.util.Objects;

/**
 * Represents a professional qualifications of a service provider application
 * through its description.
 *
 *
 */
public class ProfessionalQualification {

    /**
     * Professional qualification's description
     */
    private String description;

    /**
     * Default description
     */
    private static final String DEFAULT_DESCRIPTION = "no description";

    /**
     * Initializes the default professional qualification's constructor
     */
    public ProfessionalQualification() {
        description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the professional qualification's constructor
     *
     * @param description
     */
    public ProfessionalQualification(String description) {
                if ((description == null) || (description.trim().isEmpty())) {
            throw new IllegalArgumentException("The argument cannot be null or empty.");
        }
        this.description = description;
    }

    /**
     * Returns the professional qualification's description
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return formatted String
     */
    @Override
    public String toString() {
        return String.format("%s%n", this.description);
    }

    /**
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hash(this.description);
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
        ProfessionalQualification obj = (ProfessionalQualification) o;
        return (Objects.equals(description, obj.description));
    }

}
