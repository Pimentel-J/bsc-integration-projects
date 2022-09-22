package lapr2.project.agpsd.model;

/**
 *
 *
 */
public class ExecutionOrder {

    public  static enum RequestStatus {

        UNFINISHED,
        FINISHED,
        UNEXPECTED;

    };
//SETTERS
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public void setEmissionDate(String emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setSp(ServiceProvider sp) {
        this.m_oSp = sp;
    }
//GETTERS
    public int getNumber() {
        return number;
    }

    public String getEmissionDate() {
        return emissionDate;
    }

    public ServiceProvider getSp() {
        return m_oSp;
    }

    public ServiceRating getServiceRating() {
        return this.m_oServRat;
    }

    public RequestStatus getRequestStatus() {
        return this.status;
    }

/**
 * Constructor with instance variables
 * @param number int related to the execution order's number
 * @param emissionDate String related to the execution order's emission date
 * @param sp object of the type ServiceProvider related to the associated Service Provider
 */
    public ExecutionOrder(int number, String emissionDate, ServiceProvider sp) {
        this.number = number;
        this.emissionDate = emissionDate;
        this.m_oSp = null;
        this.m_oServRat = null;
        this.status = RequestStatus.UNFINISHED;

    }
    /**
     * int related to the number of the execution order
     */
    private int number;
    /**
     * String related to the emission date of the execution order
     */
    private String emissionDate;
    /**
     * object of the ServiceProvider type related to the associated service provider
     */
    private ServiceProvider m_oSp;
    /**
     * Object of the serviceRating type related to the rating of the execution order
     * 
     */
    private ServiceRating m_oServRat;
    /**
     * Object of the RequestStatus type related to the execution order's request status
     */
    private RequestStatus status;
}
