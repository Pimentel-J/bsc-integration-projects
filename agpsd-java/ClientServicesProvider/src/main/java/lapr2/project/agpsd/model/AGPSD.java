package lapr2.project.agpsd.model;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lapr2.project.userandauth.AuthorizationFacade;
import lapr2.project.userandauth.UserSession;
import lapr2.project.agpsd.utils.Constants;
import lapr2.project.agpsd.utils.ForEachLists;

/**
 *
 *
 */
public class AGPSD {

    private final Company m_oEmpresa;
    private final AuthorizationFacade m_oAutorizacao;

    private static AGPSD singleton = null;

    private AGPSD() {
        Properties props = getProperties();
        this.m_oEmpresa = new Company(props.getProperty(Constants.PARAMS_COMPANY_DESIGNATION),
                props.getProperty(Constants.PARAMS_COMPANY_NIF));
        this.m_oAutorizacao = this.m_oEmpresa.getAutorizacaoFacade();
        this.m_oEmpresa.setServiceTypes(createSupportedServiceTypes(props));
        this.m_oEmpresa.setExternalService(createExternalService(props));
        this.m_oEmpresa.setPairingAlgorithm(createPairingAlgorithm(props));
        setTimerForPairingAlgorithm(props);
        bootstrap();
    }

    public static AGPSD getInstance() {
        if (singleton == null) {
            synchronized (AGPSD.class) {
                singleton = new AGPSD();
            }
        }
        return singleton;
    }

    public Company getCompany() {
        return this.m_oEmpresa;
    }

    public UserSession getCurrentSession() {
        return this.m_oAutorizacao.getCurrentSession();
    }

    public boolean doLogin(String strId, String strPwd) {
        return this.m_oAutorizacao.doLogin(strId, strPwd) != null;
    }

    public void doLogout() {
        this.m_oAutorizacao.doLogout();
    }

    private Properties getProperties() {
        Properties props = new Properties();

        // Adiciona propriedades e valores por omissão
        props.setProperty(Constants.PARAMS_COMPANY_DESIGNATION, "Default Lda.");
        props.setProperty(Constants.PARAMS_COMPANY_NIF, "Default NIF");
        props.setProperty(Constants.PARAMS_NUMBER_SERV_TYPES, "0");

        // Lê as propriedades e valores definidas 
        try {
            InputStream in = new FileInputStream(Constants.PARAMS_FILE);
            props.load(in);
            in.close();
        } catch (Exception ex) {

        }
        return props;
    }

    private void bootstrap() {
        this.m_oAutorizacao.registerUserRole(Constants.ROLE_ADMINISTRATOR);
        this.m_oAutorizacao.registerUserRole(Constants.ROLE_CLIENT);
        this.m_oAutorizacao.registerUserRole(Constants.ROLE_HRO);
        this.m_oAutorizacao.registerUserRole(Constants.ROLE_SERVICE_PROVIDER);

        this.m_oAutorizacao.registerUserWithRole("Administrativo 1", "adm1@esoft.pt", "123456", Constants.ROLE_ADMINISTRATOR);
        this.m_oAutorizacao.registerUserWithRole("Administrativo 2", "adm2@esoft.pt", "123456", Constants.ROLE_ADMINISTRATOR);

        this.m_oAutorizacao.registerUserWithRole("FRH 1", "frh1@esoft.pt", "123456", Constants.ROLE_HRO);
        this.m_oAutorizacao.registerUserWithRole("FRH 2", "frh2@esoft.pt", "123456", Constants.ROLE_HRO);
        
        this.m_oAutorizacao.registerUserWithRole("Maria Santos", "msantos@gmail.com", "prosdbsts190", Constants.ROLE_CLIENT);
        this.m_oAutorizacao.registerUserWithRole("António Lage", "aLage@gmail.com", "aLage1234", Constants.ROLE_CLIENT);
        this.m_oAutorizacao.registerUserWithRole("Ana Santos", "aSantos23@isep.ipp.pt", "aSantini456", Constants.ROLE_CLIENT);
        this.m_oAutorizacao.registerUserWithRole("Joana Santos", "jSantos@isep.ipp.pt", "jjSantos23", Constants.ROLE_CLIENT);
        
        this.m_oAutorizacao.registerUserWithRole("António Padrão", "aPadrao@gmail.com", "123456", Constants.ROLE_SERVICE_PROVIDER);
        this.m_oAutorizacao.registerUserWithRole("Maria Silva", "mSilva@hotmail.com", "123456", Constants.ROLE_SERVICE_PROVIDER);
        this.m_oAutorizacao.registerUserWithRole("Joaquina Dos Santos", "jaquina@hotmail.com", "123456", Constants.ROLE_SERVICE_PROVIDER);
        this.m_oAutorizacao.registerUserWithRole("Serafim Santos", "sSantos@gmail.com", "123456", Constants.ROLE_SERVICE_PROVIDER);

        this.m_oAutorizacao.registerUserWithRoles("Martim", "martim@esoft.pt", "123456", new String[]{Constants.ROLE_HRO, Constants.ROLE_ADMINISTRATOR});

        // # Adding objects available in moodle #
        // Category
        addCategory("1", "Plumber");
        addCategory("2", "Locksmith");
        addCategory("3", "Automotive Mechanic");
        addCategory("4", "Cook");
        addCategory("5", "Painter");

        //Service
        addFixedService("1", "Light plumbing", "Install water tap", 100, "1", 60);
        addLimitedService("2", "Heavy plumbing", "Pipeline repair", 40, "1");
        addLimitedService("3", "Gate painting", "Gate painting", 60, "5");
        addExpandableService("4", "Prepare dinner", "Prepare dinner and clean kitchen", 80, "4");
        addLimitedService("5", "Repair vehicle", "Repair vehicle engine", 80, "3");
        addFixedService("6", "Gate painting", "Gate painting", 90, "5", 60);

        //GeographicalArea
        addGeographicalArea("Gondomar-1", "4420-002", 10, 50);
        addGeographicalArea("Gondomar-2", "4420-570", 10, 50);
        addGeographicalArea("Gondomar-3", "4435-685", 10, 50);
        addGeographicalArea("Porto-1", "4250-108", 10, 50);
        addGeographicalArea("Maia-1", "4470-526", 10, 50);
        
        //ServiceProvider
        addServiceProvider("10001", "500324896", "António Padrão", "António Dos Santos Padrão", "aPadrao@gmail.com", "4415-995");
        addServiceProvider("10002", "510324896", "Maria Silva", "Maria Das Neves Silva", "mSilva@hotmail.com", "4420-002");
        addServiceProvider("10003", "510324877", "Joaquina Dos Santos", "Joaquina Dos Santos", "jaquina@hotmail.com", "4470-526");
        addServiceProvider("10004", "230324822", "Serafim Santos", "Serafim Santos", "sSantos@gmail.com", "4430-601");
        addCategoryToServiceProvider("10001", "3");
        addCategoryToServiceProvider("10001", "1");
        addCategoryToServiceProvider("10002", "2");
        addCategoryToServiceProvider("10002", "4");
        addCategoryToServiceProvider("10002", "5");
        addCategoryToServiceProvider("10003", "1");
        addCategoryToServiceProvider("10003", "2");
        addCategoryToServiceProvider("10003", "3");
        addCategoryToServiceProvider("10004", "5");
        addCategoryToServiceProvider("10004", "1");
        addGeoAreaToServiceProvider("10001", "Gondomar-1");
        addGeoAreaToServiceProvider("10001", "Gondomar-2");
        addGeoAreaToServiceProvider("10002", "Porto-1");
        addGeoAreaToServiceProvider("10003", "Maia-1");
        addGeoAreaToServiceProvider("10004", "Maia-1");
        addGeoAreaToServiceProvider("10004", "Gondomar-2");
        this.m_oEmpresa.getServiceProviderRegistry().getServiceProviderByID("10001").getServiceProviderEvaluation().setAverage(2);
        this.m_oEmpresa.getServiceProviderRegistry().getServiceProviderByID("10001").getServiceProviderEvaluation().setLabel(ServiceProviderLabel.WORST);
        
        //DailyAvailability
        addDailyAvailability("10001", new DailyAvailability("03/06/2019", "09:00", "05/06/2019", "23:00"));
        addDailyAvailability("10001", new DailyAvailability("24/06/2019", "09:00", "25/06/2019", "13:00"));
        addDailyAvailability("10002", new DailyAvailability("06/06/2019", "09:00", "07/06/2019", "23:00"));
        addDailyAvailability("10002", new DailyAvailability("24/06/2019", "09:00", "25/06/2019", "22:00"));
        addDailyAvailability("10002", new DailyAvailability("28/06/2019", "20:00", "29/06/2019", "18:00"));        
        addDailyAvailability("10003", new DailyAvailability("07/06/2019", "09:00", "10/06/2019", "23:00"));
        addDailyAvailability("10003", new DailyAvailability("28/06/2019", "20:00", "29/06/2019", "18:00"));        
        addDailyAvailability("10004", new DailyAvailability("28/06/2019", "20:00", "29/06/2019", "18:00"));

        //Customer
        addCustomer("100542369", "Maria Santos", "936565651", "msantos@gmail.com", "prosdbsts190", "Rua D. João de França, nº 1", "Gondomar", "4420-001");
        addCustomer("200542669", "António Lage", "916535661", "aLage@gmail.com", "aLage1234", "R. Gonçalves de Castro, nº 8", "Pedroso", "4415-999");
        addCustomer("110542349", "Ana Santos", "966535661", "aSantos23@isep.ipp.pt", "aSantini456", "R. do Carvalhido, nº 9", "Porto", "4250-100");
        addCustomer("210975020", "Joana Santos", "966545644", "jSantos@isep.ipp.pt", "jjSantos23", "R. Cegonheira, nº 3", "Maia", "4470-528");

        //ExecutionOrder
        addExecutionOrder();

        //Applications
        addApplication("António Patrão", "500324896", "aPatrao@gmail.com", "968795236", new PostalAddress("R. Central", "Crestuma", "4415-995"));
        addApplication("Maria Silva", "510324896", "mSilva@hotmail.com", "928735537", new PostalAddress(".", ".", "4420-002"));
        addApplication("Joaquina Dos Santos", "510324877", "jaquina@hotmail", "934735567", new PostalAddress("Rua Altino Silva Gomes", "Maia", "4470-526"));
        addApplication("Serafim Santos", "230324822", "sSantos@gmail.com", "223654987", new PostalAddress("R. Alberto Alves Tavares", "Vila Nova De Gaia", "4430-601"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("500324896").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("1"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("500324896").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("3"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324896").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("5"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324896").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("2"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324896").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("4"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324877").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("1"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324877").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("2"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324877").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("3"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("230324822").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("5"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("230324822").addCategory(m_oEmpresa.getCategoriesRegistry().getCategoryById("1"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("500324896").addAcademicQualification(new AcademicQualification("Bachelor"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324896").addAcademicQualification(new AcademicQualification("Bachelor", "Master", "PhD"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324877").addAcademicQualification(new AcademicQualification("Bachelor"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("230324822").addAcademicQualification(new AcademicQualification("High School"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("500324896").addProfessionalQualification(new ProfessionalQualification("Professional Training Course of Automotive Mechanic of the Center of Professional Training of Automotive Repair"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("500324896").addProfessionalQualification(new ProfessionalQualification("Professional license for light and heavy vehicles"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("500324896").addProfessionalQualification(new ProfessionalQualification("Advanced Course in Automotive Mechanics"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324896").addProfessionalQualification(new ProfessionalQualification("Advanced Course of plumbing and locksmithing"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324896").addProfessionalQualification(new ProfessionalQualification("Professional license for light and heavy vehicles"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324896").addProfessionalQualification(new ProfessionalQualification("Cooking Course"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324877").addProfessionalQualification(new ProfessionalQualification("Advanced Course of plumbing and locksmithing"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("510324877").addProfessionalQualification(new ProfessionalQualification("Advanced Course in Automotive Mechanics"));
        m_oEmpresa.getApplicationRegistry().getApplicationByNIF("230324822").addProfessionalQualification(new ProfessionalQualification("Painter Course"));

        //Service Requests
        addServiceRequest("1", "01/06/2019", "100542369", "Rua D. João de França, nº 4", "Gondomar", "4420-001", 150);
        addServiceRequest("2", "02/06/2019", "100542369", "Rua D. João de França, nº 4", "Gondomar", "4420-001", 90);
        addServiceRequest("3", "03/06/2019", "110542349", "R. do Carvalhido, nº 1", "Porto", "4250-100", 90);
        addServiceRequest("4", "04/06/2019", "110542349", "R. do Carvalhido, nº 1", "Porto", "4250-100", 190);
        addServiceRequest("5", "05/06/2019", "210975020", "R. Cegonheira, nº 3", "Maia", "4470-528", 140);
        addServiceRequest("6", "06/06/2019", "210975020", "R. Cegonheira, nº 3", "Maia", "4470-528", 120);
        addServiceRequest("7", "07/06/2019", "210975020", "R. Cegonheira, nº 3", "Maia", "4470-528", 130);
        addServiceDescriptionToRequest("1", "1", "Close water tap", 0);
        addServiceDescriptionToRequest("2", "2", "Pipeline Repair", 60);
        addServiceDescriptionToRequest("3", "3", "Iron gate painting", 120);
        addServiceDescriptionToRequest("4", "4", "Prepare dinner and clean kitchen", 120);
        addServiceDescriptionToRequest("5", "1", "Water tap repair", 60);
        addServiceDescriptionToRequest("6", "5", "Repair vehicle engine and change oil", 60);
        addServiceDescriptionToRequest("7", "6", "Gate paiting", 60);
        addScheduleToRequest("1", 1, "03/06/2019", "09:00");
        addScheduleToRequest("1", 2, "05/06/2019", "22:00");
        addScheduleToRequest("2", 1, "24/06/2019", "09:00");
        addScheduleToRequest("2", 2, "25/06/2019", "22:00");
        addScheduleToRequest("3", 1, "24/06/2019", "10:00");
        addScheduleToRequest("3", 2, "25/06/2019", "14:30");
        addScheduleToRequest("4", 1, "06/06/2019", "19:00");
        addScheduleToRequest("5", 1, "07/06/2019", "19:00");
        addScheduleToRequest("6", 1, "08/06/2019", "09:00");
        addScheduleToRequest("7", 1, "29/06/2019", "20:00");
        
        // # END - Adding objects available in moodle #
    }

    private ExternalService createExternalService(Properties props) {
        try {
            String className = props.getProperty(Constants.PARAMS_COMPANY_EXTERNAL_SERVICE);
            Class<?> oClass = Class.forName(className);
            ExternalService oExtServ = (ExternalService) oClass.newInstance();
            return oExtServ;

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    private List<ServiceType> createSupportedServiceTypes(Properties props) {
        List<ServiceType> listTypes = new ArrayList<>();

        // Know how many ServiceType are supported
        String numberOfTypes = props.getProperty(Constants.PARAMS_NUMBER_SERV_TYPES);
        int num = Integer.parseInt(numberOfTypes);

        // For each type supported, create the respecting instance
        for (int i = 1; i <= num; i++) {
            // Know the information (designation and class) from the instance that will be created
            String strDesignation = props.getProperty(Constants.PARAMS_SERVICE_TYPE + "." + i + ".Designation");
            String strServClass = props.getProperty(Constants.PARAMS_SERVICE_TYPE + "." + i + ".Class");

            // Create the instance
            ServiceType servType = new ServiceType(strDesignation, strServClass);

            // Add it to the list that will be returned
            listTypes.add(servType);
        }

        // Return supported service types
        return listTypes;
    }
    
    private PairingAlgorithm createPairingAlgorithm(Properties props) {
        try {
            String className = props.getProperty(Constants.PARAMS_COMPANY_PAIRING_ALGORITHM);
            Class<?> oClass = Class.forName(className);
            PairingAlgorithm oPairAlg = (PairingAlgorithm) oClass.newInstance();
            return oPairAlg;

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
    
    private void setTimerForPairingAlgorithm(Properties props) {
        String delay = props.getProperty(Constants.PARAMS_COMPANY_PAIRING_ALGORITHM_DELAY);
        String period = props.getProperty(Constants.PARAMS_COMPANY_PAIRING_ALGORITHM_PERIOD);
        long longDelay = Long.parseLong(delay);
        long longPeriod = Long.parseLong(period);
        this.m_oEmpresa.invokeTimerSchedule(longDelay, longPeriod);
    }

    private void addCategory(String code, String description) {
        this.m_oEmpresa.getCategoriesRegistry().registerCategory(new Category(code, description));
    }
    
    private void addFixedService(String id, String shortDesc, String longDesc, double cost, String catId, double dur) {
        this.m_oEmpresa.getServicesRegistry().registerService(
                new FixedService(id, shortDesc, longDesc, cost,
                        m_oEmpresa.getCategoriesRegistry().getCategoryById(catId),
                        m_oEmpresa.getServiceTypesRegistry().getServiceTypeByDesignation("Fixed"),
                        dur));
    }

    private void addLimitedService(String id, String shortDesc, String longDesc, double cost, String catId) {
        this.m_oEmpresa.getServicesRegistry().registerService(
                new LimitedService(id, shortDesc, longDesc, cost,
                        m_oEmpresa.getCategoriesRegistry().getCategoryById(catId),
                        m_oEmpresa.getServiceTypesRegistry().getServiceTypeByDesignation("Limited")));
    }

    private void addExpandableService(String id, String shortDesc, String longDesc, double cost, String catId) {
        this.m_oEmpresa.getServicesRegistry().registerService(
                new ExpandableService(id, shortDesc, longDesc, cost,
                        m_oEmpresa.getCategoriesRegistry().getCategoryById(catId),
                        m_oEmpresa.getServiceTypesRegistry().getServiceTypeByDesignation("Expandable")));
    }

    private void addGeographicalArea(String desig, String zipCode, double radius, double cost) {
        this.m_oEmpresa.getGeographicalAreaRegistry().registerGeographicalArea(
                new GeographicalArea(desig, cost, zipCode, radius, new ExternalService1Adapter()));
    }

    private void addExecutionOrder() {

        this.m_oEmpresa.getExecutionOrderRegistry().addExecutionOrder(
                new ExecutionOrder(1, "05/06/2019", this.m_oEmpresa.getServiceProviderRegistry().getServiceProviderByID("PLACEHOLDER")));

    }

    private void addCustomer(String nif, String name, String phone, String mail, 
            String pwd, String address, String location, String zipCode) {

        this.m_oEmpresa.getCustomerRegistry().getCustomers().add(
                new Customer(name, nif, phone, mail, new PostalAddress(address, zipCode, location)));
    }

    private void addServiceProvider(String num, String nif, String shortName, 
            String fullName, String email, String zipCode) {

        this.m_oEmpresa.getServiceProviderRegistry().addServiceProvider(
                new ServiceProvider(num, fullName, shortName, email, ".", ".", zipCode));
    }
    
    private void addCategoryToServiceProvider(String numProvider, String codeCat) {
        this.m_oEmpresa.getServiceProviderRegistry().getServiceProviderByID(numProvider).addCategory(
                m_oEmpresa.getCategoriesRegistry().getCategoryById(codeCat) );
    }
    
    private void addGeoAreaToServiceProvider(String numProvider, String designation) {
        this.m_oEmpresa.getServiceProviderRegistry().getServiceProviderByID(numProvider).addGeographicalArea(
                m_oEmpresa.getGeographicalAreaRegistry().getGeographicalAreaByID(designation) );
    }

    private void addApplication(String fullName, String NIF, String email,
            String phoneNumber, PostalAddress postalAddress) {
        this.m_oEmpresa.getApplicationRegistry().registerApplication(
                new Application(fullName, NIF, email, phoneNumber, postalAddress));
    }

    private void addDailyAvailability(String registryNumber, DailyAvailability da) {
        this.m_oEmpresa.getServiceProviderRegistry().getServiceProviderByID(registryNumber).addDailyAvailability(da);
    }
    
    private void addServiceRequest(String num, String date, String customerNif, 
            String address, String location, String zipCode, double total) {
        
        this.m_oEmpresa.getServiceRequestRegistry().addServiceRequest(
                new ServiceRequest(num, date, total, 
                        m_oEmpresa.getCustomerRegistry().getCustomerByNif(customerNif), 
                        new PostalAddress(address, zipCode, location)));
    }
    
    private void addServiceDescriptionToRequest(String requestNum, String serviceId, 
            String description, double duration) {
        
        this.m_oEmpresa.getServiceRequestRegistry().getServiceRequestByNum(requestNum).addRequestedServiceDescription(
                new RequestedServiceDescription(
                        duration, 
                        description,
                        m_oEmpresa.getServicesRegistry().getServiceById(serviceId) ));
    }
    
    private void addScheduleToRequest(String requestNum, int order, String date, String time) {
        this.m_oEmpresa.getServiceRequestRegistry().getServiceRequestByNum(requestNum).addSchedulePreference(
                new SchedulePreference(order, date, time));
    }
}
