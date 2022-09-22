package lapr2.project.agpsd.controller;

import java.util.List;
import lapr2.project.agpsd.model.AGPSD;
import lapr2.project.agpsd.model.AcademicQualification;
import lapr2.project.agpsd.model.Application;
import lapr2.project.agpsd.model.ApplicationRegistry;
import lapr2.project.agpsd.model.Category;
import lapr2.project.agpsd.model.Company;
import lapr2.project.agpsd.model.Document;
import lapr2.project.agpsd.model.PostalAddress;
import lapr2.project.agpsd.model.ProfessionalQualification;

/**
 * UC2 - Submit Service Provider Application Controller's class
 *
 *
 */
public class UC2SubmitApplicationController {

    private final Company company;
    private final AGPSD agpsd;
    private Application spApp;
    private ApplicationRegistry appReg;

    public UC2SubmitApplicationController() {
        this.agpsd = AGPSD.getInstance();
        this.company = agpsd.getCompany();
    }

    public Application getApplication() {
        return spApp;
    }

    public List<Application> getApplications() {
        this.appReg = company.getApplicationRegistry();
        return appReg.getApplications();
    }

    public void setApplication(Application spApp) {
        this.spApp = spApp;
    }

    public boolean newApplication(String fullName, String NIF, String email,
            String phoneNumber, String place, String zipCode, String location) {
        this.spApp = new Application(fullName, NIF, email, phoneNumber, new PostalAddress(place, zipCode, location));
        return this.company.getApplicationRegistry().validateApplication(spApp);
    }

    public boolean registerApplication(Application application) {
        if (company.getApplicationRegistry().validateApplication(this.spApp)) {
            return company.getApplicationRegistry().addApplication(this.spApp);
        }
        return false;
    }

    public List<Category> getCategories() {
        return company.getCategoriesRegistry().getCategories();
    }
    
    public String getApplicationString() {
        return this.spApp.toString();
    }
}
