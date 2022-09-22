/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class CustomerRegistry {
 /**
  * Declaration of the customer list
  */   
    private List<Customer> m_lstCustomers;
/**
 * Constructor instantiating the customer list
 */
    public CustomerRegistry() {
        this.m_lstCustomers= new ArrayList<>();
        
        
        //Object List ( faltam adicionar zipcodes)
        PostalAddress JoaoAddress = new PostalAddress("Av. Joao Augusto","Lisboa");
        this.m_lstCustomers.add(new Customer("Joao","111222333", "913758372","joao@fdjh.com",JoaoAddress));
        PostalAddress FredAddress = new PostalAddress("Av. Fred Augusto","Porto");
        this.m_lstCustomers.add(new Customer("Fred","114522333", "913678372","fred@fdjh.com",FredAddress));
        PostalAddress FranciscoAddress = new PostalAddress("Av. Francisco Augusto","Braga");
        this.m_lstCustomers.add(new Customer("Francisco","114562333", "913737872","francisco@fdjh.com",FranciscoAddress));
        PostalAddress NunoAddress = new PostalAddress("Av. Nuno Augusto","Guimaraes");
        this.m_lstCustomers.add(new Customer("Nuno","117890333", "948908372","nuno@fdjh.com",NunoAddress));
        PostalAddress DiogoAddress = new PostalAddress("Av. Diogo Augusto","Faro");
        this.m_lstCustomers.add(new Customer("Diogo","112938333", "914567372","diogo@fdjh.com",DiogoAddress));
    }
    
    
    /**
     * Method to return the list of customers
     * @return list of objects of the type Customer
     */
    public List<Customer> getCustomers() 
    {
        List<Customer> lstCustomers = new ArrayList<>();
        lstCustomers.addAll(m_lstCustomers);
        return lstCustomers;
    } 
    /**
     * Method to obtain a certain customer through the email
     * @param email String related to the customer's email
     * @return object of the Customer type associated to the email
     */
    public Customer getCustomerByEmail(String email) 
    {
        for (Customer oCustomer : m_lstCustomers) 
        {
            if (oCustomer.hasEmail(email)) 
            {
                return oCustomer;
            }
        }
        return null;
    }
    /**
     * Method to obtain a certain customer through the fiscal number
     * @param nif String related to the customer's fiscal number
     * @return object of the Customer type related associated to the fiscal number
     */
    public Customer getCustomerByNif(String nif) 
    {
        for (Customer oCustomer : m_lstCustomers) 
        {
            if (oCustomer.hasNif(nif)) 
            {
                return oCustomer;
            }
        }
        return null;
    }
    /**
     * Method to add an object of the Customer type to the customer list
     * @param oCustomer object of the Customer type to be added to the customer list
     * @return boolean 
     */
    public boolean addCustomer(Customer oCustomer) 
    {
        return this.m_lstCustomers.add(oCustomer);
    }
    
}
