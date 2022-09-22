package lapr2.project.agpsd.model;

import java.util.Objects;

/**
 * Represents an academic qualifications of a service provider application
 * through its degree, designation and classification.
 *
 *
 */
public class AcademicQualification {

    /**
     * Academic qualification's degree
     */
    private String degree;
    /**
     * Academic qualification's designation
     */
    private String designation;
    /**
     * Academic qualification's classification
     */
    private String classification;

    /**
     * Default degree
     */
    private static final String DEFAULT_DEGREE = "no degree";
    /**
     * Default designation
     */
    private static final String DEFAULT_DESIGNATION = "no designation";
    /**
     * Default classification
     */
    private static final String DEFAULT_CLASSIFICATION = "no classification";

    /**
     * Initializes the default academic qualification's constructor
     */
    public AcademicQualification() {
        degree = DEFAULT_DEGREE;
        designation = DEFAULT_DESIGNATION;
        classification = DEFAULT_CLASSIFICATION;
    }

    /**
     * Initializes the academic qualification's constructor
     *
     * @param degree
     * @param designation
     * @param classification
     */
    public AcademicQualification(String degree, String designation, String classification) {
        if ((designation == null) || (designation.trim().isEmpty())) {
            throw new IllegalArgumentException("The argument cannot be null or empty.");
        }
        this.degree = degree;
        this.designation = designation;
        this.classification = classification;
    }

    /**
     * Initializes the academic qualification's constructor with degree
     *
     * @param degree
     */
    public AcademicQualification(String degree) {
        this.degree = degree;
        this.designation = DEFAULT_DESIGNATION;
        this.classification = DEFAULT_CLASSIFICATION;
    }

    /**
     * Returns the academic qualification's degree
     *
     * @return degree
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Returns the academic qualification's designation
     *
     * @return designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Returns the academic qualification's classification
     *
     * @return classification
     */
    public String getClassification() {
        return classification;
    }

    @Override
    public String toString() {
        return String.format("Degree: %s%nDesignation: %s%nClassification: %s%n",
                this.degree, this.designation, this.classification);
    }
    
    public String toStringOneLine() {
        return String.format("Degree: %s|Designation: %s|Classification: %s%n",
                this.degree, this.designation, this.classification);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hash(this.degree, this.designation, this.classification);
        return hash;
    }

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
        AcademicQualification obj = (AcademicQualification) o;
        return (Objects.equals(degree, obj.degree)
                && Objects.equals(designation, obj.designation)
                && Objects.equals(classification, obj.classification));
    }

}
