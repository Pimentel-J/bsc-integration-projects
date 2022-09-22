package lapr2.project.agpsd.model;

import lapr2.project.agpsd.utils.Utils;

/**
 * This class allows the service provider's daily availability period to be
 * stored.
 *
 *
 */
public class DailyAvailability {
    /**
     * Availability status' types
     */
    public static enum AvailabilityStatus {
        AVAILABLE, NOTAVAILABLE;
    };

    /**
     * Availability's start date
     */
    private String startDate;
    /**
     * Availability's end date
     */
    private String endDate;
    /**
     * Availability's start time
     */
    private String startTime;
    /**
     * Availability's end time
     */
    private String endTime;
    /**
     * Availability's status
     */
    private AvailabilityStatus availabilityStatus;

    /**
     * Default start date
     */
    private static final String DEFAULT_START_DATE = "no date";
    /**
     * Default end date
     */
    private static final String DEFAULT_END_DATE = "no date";
    /**
     * Default start time
     */
    private static final String DEFAULT_START_TIME = "no time";
    /**
     * Default end time
     */
    private static final String DEFAULT_END_TIME = "no time";

    /**
     * Initializes the default daily availability's constructor
     */
    public DailyAvailability() {
        startDate = DEFAULT_START_DATE;
        startTime = DEFAULT_END_DATE;
        endDate = DEFAULT_START_TIME;
        endTime = DEFAULT_END_TIME;
        availabilityStatus = AvailabilityStatus.NOTAVAILABLE;
    }
    /**
     * Initializes the default daily availability's constructor
     *
     * @param startDate
     * @param startTime
     * @param endDate
     * @param endTime
     */
    public DailyAvailability(String startDate, String startTime, String endDate, String endTime) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        availabilityStatus = AvailabilityStatus.AVAILABLE;
    }

    /**
     * Returns the start date
     *
     * @return startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * Returns the end date
     *
     * @return endDate
     */
    public String getEndDate() {
        return endDate;
    }
    /**
     * Returns the start time
     *
     * @return startTime
     */
    public String getHoraInicio() {
        return startTime;
    }
    /**
     * Returns the end time
     *
     * @return endTime
     */
    public String getHoraFim() {
        return endTime;
    }    
    /**
     * Returns the availability status
     * 
     * @return availabilityStatus
     */
    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }    
    /**
     * Changes the availability status to the value passed as argument
     * 
     * @param availabilityStatus 
     */
    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    @Override
    public String toString() {
        return String.format("Period: from %s, %s hours to %s, %s horas%n",
                this.startDate, this.startDate, this.endTime, this.endTime);
    }
    
    /**
     * Checks if a date & time fit in the middle of this daily availability
     * 
     * @param date
     * @param time
     * @return 
     */
    public boolean checkIfPeriodFitsAvailability(String date, String time) {
        return Utils.checkIfInstanceFitsPeriod(date, time, startDate, startTime, endDate, endTime);
    }
}
