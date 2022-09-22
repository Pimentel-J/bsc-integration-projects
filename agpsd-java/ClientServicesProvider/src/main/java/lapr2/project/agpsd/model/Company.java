package lapr2.project.agpsd.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import lapr2.project.userandauth.AuthorizationFacade;
import lapr2.project.agpsd.utils.Constants;

/**
 *
 *
 */
public class Company {
/**
 * String related to the designation of the company
 */
    private String m_strDesignacao;
    /**
     * String related to the company's fiscal number
     */
    private String m_strNIF;
    /**
     * Object of the External Service Type related to the external service
     */
    private ExternalService m_oExtServ;
    /**
     * Object of the Pairing Algorithm type related to the pairing algorithm
     */
    private PairingAlgorithm m_oPairingAlg;
    /**
     * Object of the AuthorizationFacade type related to the user authorizations
     */
    private final AuthorizationFacade m_oAutorizacao;
    /**
     * List related to the list of customers
     */
    private final List<Customer> m_lstClientes;
    /**
     * List related to the list of services
     */
    private final List<Service> m_lstServicos;
    /**
     * Object of the CategoriesRegistry type related to the Category registry
     */
    private final CategoriesRegistry m_oCatRegistry;
    /**
     * Related to the Service Type Registry
     */
    private final ServiceTypesRegistry m_oServTypesRegistry;
    /**
     * Object of the ServiceTypesRegistry type related to the Service Registry
     */
    private final ServicesRegistry m_oServRegistry;
    /**
     * Object of theServiceRegsitry type related to the Service Provider Registry
     */
    private final ServiceProviderRegistry m_oServProviderRegistry;
    /**
     * Object of the ServiceRequestRegsitryType related to the Applications' Registry
     */
    private final ApplicationRegistry m_oApplicationRegistry;
    /**
     * Object of the GeographicalAreaRegsitry type related the Geographic Area Registry
     */
    private final GeographicalAreaRegistry m_oGeoAreaRegistry;
    /**
     * Object of the ExecutionOrderRegistry type related to the Execution Order Registry
     */
    private final ExecutionOrderRegistry m_oExecOrderRegistry;
    /**
     * Object of the CustomerRegsitry type related to the Customer Registry
     */
    private final CustomerRegistry m_oCustRegistry;
    /**
     * Object of the ServiceRequestRegistry type related to the Service Request Registry
     */
    private final ServiceRequestRegistry m_oServRequestRegistry;
    /**
     * Object of the Timer type related to the timer for scheduled application tasks
     */
    private Timer timer;
    /**
     * Constructor with the instance variables
     * @param strDesignacao related to the company's designation
     * @param strNIF  related to the company's fiscal number
     */
    public Company(String strDesignacao, String strNIF) {
        if ((strDesignacao == null) || (strNIF == null)
                || (strDesignacao.isEmpty()) || (strNIF.isEmpty())) {
            throw new IllegalArgumentException("None of the arguments can be null or empty.");
        }

        this.m_strDesignacao = strDesignacao;
        this.m_strNIF = strNIF;

        this.m_oAutorizacao = new AuthorizationFacade();

        this.m_lstClientes = new ArrayList<>();
        this.m_lstServicos = new ArrayList<>();

        this.m_oCatRegistry = new CategoriesRegistry();
        this.m_oServTypesRegistry = new ServiceTypesRegistry();
        this.m_oServRegistry = new ServicesRegistry();
        this.m_oServProviderRegistry = new ServiceProviderRegistry();
        this.m_oApplicationRegistry = new ApplicationRegistry();
        this.m_oGeoAreaRegistry = new GeographicalAreaRegistry();
        this.m_oExecOrderRegistry = new ExecutionOrderRegistry();
        this.m_oCustRegistry = new CustomerRegistry();
        this.m_oServRequestRegistry = new ServiceRequestRegistry();
        
        this.timer = new Timer();
    }
//GETTERS
    public AuthorizationFacade getAutorizacaoFacade() {
        return this.m_oAutorizacao;
    }

    public CategoriesRegistry getCategoriesRegistry() {
        return this.m_oCatRegistry;
    }

    public ServiceTypesRegistry getServiceTypesRegistry() {
        return this.m_oServTypesRegistry;
    }

    public ServicesRegistry getServicesRegistry() {
        return this.m_oServRegistry;
    }
    
    public ServiceRequestRegistry getServiceRequestRegistry() {
        return this.m_oServRequestRegistry;
    }

    public ExternalService getExternalService() {
        return this.m_oExtServ;
    }
//SETTERS
    public boolean setExternalService(ExternalService oExtServ) {
        if (oExtServ == null) {
            return false;
        }

        if (this.m_oExtServ == null) {
            this.m_oExtServ = oExtServ;
            return true;
        }
        return false;
    }
    
    public boolean setServiceTypes(List<ServiceType> lstServTypes) {
        return this.m_oServTypesRegistry.setServiceTypes(lstServTypes);
    }
    
    public boolean setPairingAlgorithm(PairingAlgorithm oPairAlg) {
        if (oPairAlg == null) {
            return false;
        }

        if (this.m_oPairingAlg == null) {
            this.m_oPairingAlg = oPairAlg;
            return true;
        }
        return false;
    }
/**
 * Method to call the timer schedule for the application's timed tasks
 * @param delay related to the timer's delay to start
 * @param period related to the period of the task
 */    
    public void invokeTimerSchedule(long delay, long period) {
        timer.schedule(
                new PairServiceProvidersTask(m_oServRequestRegistry, m_oServProviderRegistry, m_oPairingAlg),
                delay,
                period );
    }

    // Customer
    // <editor-fold defaultstate="collapsed">
    /**
     * Method to obtain a customer by email
     * @param strEMail related to the customer's email
     * @return associated customer
     */
    public Customer getCustomerByEmail(String strEMail) {
        for (Customer cliente : this.m_lstClientes) {
            if (cliente.hasEmail(strEMail)) {
                return cliente;
            }
        }

        return null;
    }
/**
 * Method to instantiate a new customer
 * @param strNome related to the customer's name
 * @param strNIF related to the customer's fiscal number
 * @param strTelefone related to the customer's phone number
 * @param strEmail related to the customer's email
 * @param morada related to the customer's address
 * @return object of the type Customer
 */
    public Customer newCustomer(String strNome, String strNIF, String strTelefone, String strEmail, PostalAddress morada) {
        return new Customer(strNome, strNIF, strTelefone, strEmail, morada);
    }
/**
 * Method to confirm a customer's registration
 * @param oCliente Object of the customer type related to the customer
 * @param strPwd String related to the customer's password
 * @return 
 */
    public boolean registerCustomer(Customer oCliente, String strPwd) {
        if (this.validateCustomer(oCliente, strPwd)) {
            if (this.m_oAutorizacao.registerUserWithRole(oCliente.getName(), oCliente.getEmail(), strPwd, Constants.ROLE_CLIENT)) {
                return addCustomer(oCliente);
            }
        }
        return false;
    }
/**
 * Method to add a customer the company's customer list
 * @param oCliente object of the Customer type related to the customer to be added
 * @return boolean confirming or not the registered customer
 */
    private boolean addCustomer(Customer oCliente) {
        return m_lstClientes.add(oCliente);
    }
/**
 * Method to validate a certain customer
 * @param oCliente object of the Customer type related to the customer to be validated
 * @param strPwd related to the concerned customer's password
 * @return boolean confirming or not that the customer has been validated
 */
    public boolean validateCustomer(Customer oCliente, String strPwd) {
        boolean bRet = true;

        // Escrever aqui o código de validação
        if (this.m_oAutorizacao.userExistsBoolean(oCliente.getEmail())) {
            bRet = false;
        }
        if (strPwd == null) {
            bRet = false;
        }
        if (strPwd.isEmpty()) {
            bRet = false;
        }
        //

        return bRet;
    }
/**
 * Method to obtain the Customer Registry
 * @return object of the CustomerRegistry type related to the Customer Registry
 */
    public CustomerRegistry getCustomerRegistry() {
        return this.m_oCustRegistry;
    }

    // </editor-fold>
   
    // Categories 
    // <editor-fold defaultstate="collapsed">
    /**
     * Method to instantiate a new object of the Category type
     * @param strCodigo related to the category's code
     * @param strDescricao String related to the category's description
     * @return object of the Category type
     */
    public Category newCategory(String strCodigo, String strDescricao) {
        return new Category(strCodigo, strDescricao);
    }
/**
 * Method to validate an object of the Category type
 * @param oCategoria object of the Category type related to the concerned category
 * @return boolean confirming or not if the category has been validated
 */
    public boolean validateCategory(Category oCategoria) {
        boolean bRet = true;

        // Escrever aqui o código de validação
        //
        return bRet;
    }

    // </editor-fold>
    
    // ServiceProvider
    /**
     * Method to obtain the Service Provider Registry
     * @return object of the ServiceProviderRegistry related to the Service Provider Registry
     */
    public ServiceProviderRegistry getServiceProviderRegistry() {
        return this.m_oServProviderRegistry;
    }
    
    // Application
     /**
     * Method to obtain the Application Registry
     * @return object of the ApplicationRegistry type related to the Application Registry
     */
    public ApplicationRegistry getApplicationRegistry() {
        return this.m_oApplicationRegistry;
    }
     /**
     * Method to obtain the Geographical Area Registry
     * @return object of the GeographicalAreaRegistry related to the Geographical Area Registry
     */
    // GeographicalArea
    public GeographicalAreaRegistry getGeographicalAreaRegistry() {
        return this.m_oGeoAreaRegistry;
    }
     /**
     * Method to obtain the Execution Order Registry
     * @return object of the ExecutionOrderRegistry related to the Execution Order Registry
     */
    // ExecutionOrder
    public ExecutionOrderRegistry getExecutionOrderRegistry() {
        return this.m_oExecOrderRegistry;
    }

}
