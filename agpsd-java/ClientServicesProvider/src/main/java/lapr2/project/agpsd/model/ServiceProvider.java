package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;
import lapr2.project.agpsd.model.DailyAvailability.AvailabilityStatus;

/**
 *
 *
 */
public class ServiceProvider {

    private String registryNumber;
    private String fullName;
    private String shortName;
    private String instEmail;
    private ServiceProviderCategoryList m_oSpCatList;
    private ServiceProviderGeographicalAreaList m_oSpGeoAreaList;
    private ServiceProviderEvaluation m_oSpEval;
    private PostalAddress m_oPostAddress;
    private List<DailyAvailability> m_oListDailyAvailability;
    
    /**
     *
     * @param registryNumber
     * @param fullName
     * @param shortName
     * @param instEmail
     */
    public ServiceProvider(String registryNumber, String fullName, String shortName, String instEmail) {
        
        this.registryNumber = registryNumber;
        this.fullName = fullName;
        this.shortName = shortName;
        this.instEmail = instEmail;
        this.m_oSpCatList = new ServiceProviderCategoryList();
        this.m_oSpGeoAreaList = new ServiceProviderGeographicalAreaList();
        this.m_oSpEval = new ServiceProviderEvaluation();
        this.m_oPostAddress = null;
        this.m_oListDailyAvailability = new ArrayList<>();
    }
    
    /**
     *
     * @param registryNumber
     * @param fullName
     * @param shortName
     * @param instEmail
     * @param address
     * @param location
     * @param zipCode
     */
    public ServiceProvider(String registryNumber, String fullName, String shortName, 
            String instEmail, String address, String location, String zipCode) {
        
        this.registryNumber = registryNumber;
        this.fullName = fullName;
        this.shortName = shortName;
        this.instEmail = instEmail;
        this.m_oSpCatList = new ServiceProviderCategoryList();
        this.m_oSpGeoAreaList = new ServiceProviderGeographicalAreaList();
        this.m_oSpEval = new ServiceProviderEvaluation();
        this.m_oPostAddress = new PostalAddress(address, zipCode, location);
        this.m_oListDailyAvailability = new ArrayList<>();
    }
    
    /**
     *
     * @param m_oSpCatList
     */
    public void setSpCatList(ServiceProviderCategoryList m_oSpCatList) {
        this.m_oSpCatList = m_oSpCatList;
    }

    /**
     *
     * @param m_oSpGeoAreaList
     */
    public void setSpGeoAreaList(ServiceProviderGeographicalAreaList m_oSpGeoAreaList) {
        this.m_oSpGeoAreaList = m_oSpGeoAreaList;
    }

    /**
     *
     * @return m_oSpCatList
     */
    public ServiceProviderCategoryList getSpCatList() {
        return m_oSpCatList;
    }

    /**
     *
     * @return m_oSpGeoAreaList
     */
    public ServiceProviderGeographicalAreaList getSpGeoAreaList() {
        return m_oSpGeoAreaList;
    }

    /**
     *
     * @return registryNumber
     */
    public String getRegistryNumber(){
        return this.registryNumber;
    }
    
    /**
     *
     * @return m_oSpEval label
     */
    public ServiceProviderLabel getLabel(){
        return m_oSpEval.getLabel();
    }
    
    /**
     *
     * @return m_oSpEval
     */
    public ServiceProviderEvaluation getServiceProviderEvaluation(){
        return this.m_oSpEval;
    }
    
    /**
     *
     * @param label
     */
    public void setLabel(ServiceProviderLabel label){
        this.m_oSpEval.setLabel(label);
    }
    
    /**
     *
     * @return fullName
     */
    public String getFullName(){
        return this.fullName;
    }
    
    /**
     *
     * @return instEmail
     */
    public String getEmail(){
        return this.instEmail;
    }
    
    /**
     *
     * @param catList
     */
    public void addCategoryList(List<Category> catList) {
        m_oSpCatList.addCategoryList(catList);
    }
    
    /**
     *
     * @param cat
     */
    public void addCategory(Category cat){
        boolean validated = validate(cat);
        if (validated) {
            this.m_oSpCatList.addCategory(cat);
        }
    }
    
    /**
     *
     * @param ga
     */
    public void addGeographicalArea(GeographicalArea ga) {
        boolean validated = validate(ga);
        if (validated) {
            this.m_oSpGeoAreaList.addGeographicalArea(ga);
        }
    }
    
    /**
     *
     * @return m_oPostAddress
     */
    public PostalAddress getPostalAddress() {
        return this.m_oPostAddress;
    }
    
    /**
     *
     * @param place
     * @param zipCode
     * @param location
     */
    public void addPostalAddress(String place, String zipCode, String location) {
        PostalAddress postAddress = new PostalAddress(place, zipCode, location);
        boolean validated = validate(postAddress);
        if (validated) {
            add(postAddress);
        }
    }
    
    /**
     *
     * @param postAddress
     */
    public void add(PostalAddress postAddress) {
        this.m_oPostAddress = postAddress;
    }
    
    /**
     *
     * @param postAddress
     * @return
     */
    public boolean validate(PostalAddress postAddress) {
        //When you have the time, properly validate
        return true;
    }
    
    /**
     *
     * @param cat
     * @return true or false
     */
    public boolean validate(Category cat){
        return (cat != null);
    }
    
    /**
     *
     * @param ga
     * @return true or false
     */
    public boolean validate(GeographicalArea ga) {
        return (ga != null);
    }
    
    /**
     *
     * @param da
     */
    public void addDailyAvailability(DailyAvailability da) {
        m_oListDailyAvailability.add(da);
    }
    
    /**
     *
     * @param date
     * @param time
     * @return true or false
     */
    public boolean changeAvailabilityStatus(String date, String time) {
        for (DailyAvailability oAvail : m_oListDailyAvailability) {
            if (oAvail.checkIfPeriodFitsAvailability(date, time)) {
                oAvail.setAvailabilityStatus(AvailabilityStatus.NOTAVAILABLE);
                return true;
            }
        }
        return false;
    }
    
    /**
     *
     * @return m_oListDailyAvailability list
     */
    public List<DailyAvailability> getDailyAvailabilityList() {
        return new ArrayList<>(m_oListDailyAvailability);
    }
    
    /**
     *
     * @param da
     * @return true or false
     */
    public boolean registerDailyAvailability(DailyAvailability da) {
        if (!validateDailyAvailability(da)) {
            return false;
        }
        return true;
    }

    private boolean validateDailyAvailability(DailyAvailability da) {
        return (!da.getStartDate().equalsIgnoreCase("no date"));
    }
    
    /**
     *
     * @return Registry number and short name, formatted as String
     */
    @Override
    public String toString() {
        return String.format("Number: %s - Name: %s", registryNumber, shortName);
    }

}
