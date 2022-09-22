package lapr2.project.agpsd.model;

/**
 *
 *
 */
public class ZipCode {
    //SETTERS

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
//GETTERS
    public String getZipCode() {
        return zipCode;
    }
/**
 * Constructor with instance variables
 * @param zipCode String related to the zip code
 */
    public ZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
 /**
  * Empty Constructor
  */   
    public ZipCode() {
        this.zipCode = null;
    }

    private String zipCode;
/**
 * Rewrite of the toString method
 * @return String with the content of the ZipCode type object
 */
    @Override
    public String toString() {
        return String.format("%s", this.zipCode);
    }
}
