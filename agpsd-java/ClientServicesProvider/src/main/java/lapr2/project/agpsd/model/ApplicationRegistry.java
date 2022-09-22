package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Application's Registry class (Creator/HC+LC).
 *
 *
 */
public class ApplicationRegistry {

    /**
     * List of the Applications
     */
    private List<Application> m_oApplicationList;
//CONSTRUCTORS

    /**
     * Constructor with all instance variables
     *
     * @param fullName related to the full name of the applicant service
     * provider
     * @param NIF related to the fiscal number of the applicant
     * @param email related to the email address of the applicant
     * @param phoneNumber related to the applicant's phone number
     * @param place related to the address of the applicant
     * @param zipCode related to the zip code of the applicant's address
     * @param location related to the location of the applicant's address
     * @return object related to a certain application
     */
    public Application newApplication(String fullName, String NIF, String email,
            String phoneNumber, String place, String zipCode, String location) {
        return new Application(fullName, NIF, email, phoneNumber, new PostalAddress(place, zipCode, location));
    }

    /**
     * Method to start a list of applications
     */
    public ApplicationRegistry() {
        this.m_oApplicationList = new ArrayList<>();
    }
    
    /**
     * Method to set applications into a list, if empty
     * @param apps
     * @return a list with all the applications
     */
    public boolean setApplications(List<Application> apps) {
        if (m_oApplicationList.isEmpty()) {
            m_oApplicationList.addAll(apps);
        }
        return true;
    }

    /**
     * Method to obtain the list of applications
     *
     * @return list of applications
     */
    public List<Application> getApplications() {
        List<Application> lstApplications = new ArrayList<>();
        lstApplications.addAll(m_oApplicationList);
        return lstApplications;
    }

    /**
     * Method to obtain an application related to a certain NIF
     *
     * @param NIF related to the applicant/service provider fiscal number
     * @return application with the respective NIF
     */
    public Application getApplicationByNIF(String NIF) {
        for (Application spApp : this.m_oApplicationList) {
            if (spApp.getNIF().equals(NIF.trim())) {
                return spApp;
            }
        }
        return null;
    }

    /**
     * Method to register a certain application after validation
     *
     * @param oApp object related to a certain application
     * @return boolean confirming or not the registration
     */
    public boolean registerApplication(Application oApp) {
        if (!validateApplication(oApp)) {
            return false;
        }
        return addApplication(oApp);
    }

    /**
     * Method to validate a certain application
     *
     * @param oApp object related to a certain application
     * @return boolean confirming or not the validation of the application
     */
    public boolean validateApplication(Application oApp) {
        return (!oApp.getFullName().equalsIgnoreCase("no name"));
    }

    /**
     * Method to add an application to the application list
     *
     * @param oApp object related to a certain application
     * @return boolean confirming or not the register of the application
     */
    public boolean addApplication(Application oApp) {
        return m_oApplicationList.add(oApp);
    }

}
