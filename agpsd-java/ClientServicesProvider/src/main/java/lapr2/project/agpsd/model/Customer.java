 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 *
 */
public class Customer
{
    /**
     * String related to the customer's name
     */
    private String name;
    /**
     * String related to the customer's fiscal number
     */
    private String NIF;
    /**
     * String related to the customer's phone number
     */
    private String phoneNumber;
    /**
     * String related to the customer's email address
     */
    private String email;
    /**
     * List related to the PostalAdress objects related to the customer's several postal addresses
     */
    private List<PostalAddress> lstPostalAddress = new ArrayList<PostalAddress>();
            
    /**
     * Constructor with the instance variables
     * @param name String related to the customer's name
     * @param NIF String related to the customer's fiscal number
     * @param phoneNumber String related to the customer's phone number
     * @param email String related to the customer's email address
     * @param pAddress String related to the customer's postal address
     */
    public Customer(String name, String NIF, String phoneNumber, String email, PostalAddress pAddress)
    {
        if ( (name == null) || (NIF == null) || (phoneNumber == null) ||
                (email == null) || (pAddress == null) ||
                (name.isEmpty())|| (NIF.isEmpty()) || (phoneNumber.isEmpty()) || 
                (email.isEmpty()))
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        
        this.name = name;
        this.email = email;
        this.NIF = NIF;
        this.phoneNumber = phoneNumber;
        lstPostalAddress.add(pAddress);
    }
//GETTERS
    public String getName()
    {
        return this.name;
    }
    
    public String getEmail()
    {
        return this.email;
    }
 /**
  * Method to verify if a certain object of the Customer type has an email address
  * @param email related to the customer's email address
  * @return boolean confirming or not if the customer has the specified email
  */   
    public boolean hasEmail(String email)
    {
        return this.email.equalsIgnoreCase(email);
    }
/**
 * Method to verify if a certain object of the Customer type has a fiscal number
 * @param nif related to the customer's fiscal number
 * @return boolean confirming or not if the customer has the specified fiscal number
 */
    public boolean hasNif(String nif)
    {
        return this.NIF.equalsIgnoreCase(nif);
    }
 /**
  * Method to add an object of the PostalAddress type to the list of the customer's postal addresses
  * @param pAddress object of the PostalAddress type related to the customer's address to be added
  * @return boolean confirming or not if the postal address has been added
  */  
    public boolean addPostalAddress(PostalAddress pAddress)
    {
        return this.lstPostalAddress.add(pAddress);
    }
    /**
     * Method to remove an object of the PostalAddress type from the customer's registered addresses
     * @param pAddress object of the PostalAddress type related to the address to be removed
     * @return boolean confirming or not the removal of the postal address
     */
    public boolean removePostalAddress(PostalAddress pAddress)
    {
        return this.lstPostalAddress.remove(pAddress);
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.email);
        return hash;
    }
    
    @Override
    public boolean equals(Object o) {
        // Inspirado em https://www.sitepoint.com/implement-javas-equals-method-correctly/
        
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        // field comparison
        Customer obj = (Customer) o;
        return (Objects.equals(email, obj.email) || Objects.equals(NIF, obj.NIF));
    }
    /**
     * Overwrite of the toString method
     * @return String with the content of an object of the Customer type
     */
    @Override
    public String toString()
    {
        String str = String.format("%s - %s - %s - %s", this.name, this.NIF, this.phoneNumber, this.email);
        for(PostalAddress pAddress:this.lstPostalAddress)
            str += "\nMorada:\n" + pAddress.toString();
        return str;
    }
    /**
     * Method to instantiate a new object of the PostalAddress type
     * @param strLocal String related to the customer's address
     * @param strCodPostal String related to customer's address zip code
     * @param strLocalidade String related to the customer's address location
     * @return 
     */
    public static PostalAddress newPostalAddress(String strLocal, String strCodPostal, String strLocalidade)
    {
        return new PostalAddress(strLocal,strCodPostal,strLocalidade);
    }

    
}
