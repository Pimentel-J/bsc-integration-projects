/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

/**
 *
 *
 */
public class Trip {

    private int tripId;
    private String vehicleDescription;
    private String userName;
    private String startParkId;
    private String endParkId;
    private String startTime;
    private String endTime;
    private Double cost = 0.0;
    private Integer invoiceId;
    private int points;

    public Trip() {
        // empty constructor
    }

    /**
     * Full constructor.
     *
     * @param tripId if of the Trip.
     * @param vehicleDescription description of the vehicle in the Trip.
     * @param userName user name of the trip.
     * @param startParkId start park id for the Trip.
     * @param endParkId end park id for the Trip.
     * @param startTime start time for the Trip.
     * @param endTime end park time for the Trip.
     * @param cost cost of the Trip.
     * @param points points of the trip.
     * @param invoiceId invoice id of the Trip.
     */
    public Trip(int tripId, String vehicleDescription, String userName, String startParkId, String endParkId, String startTime, String endTime, double cost,int points, Integer invoiceId) {

        setTripId(tripId);
        setVehicleDescription(vehicleDescription);
        setUserName(userName);
        setStartParkId(startParkId);
        setEndParkId(endParkId);
        setStartTime(startTime);
        setEndTime(endTime);
        setCost(cost);
        setInvoiceId(invoiceId);
       setPoints(points);
    }

    /**
     * Method that returns a trip id
     *
     * @return tripId, id of trip
     */
    public int getTripId() {
        return this.tripId;
    }
    /**
     * Method that returns  points
     *
     * @return points, points of trip
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Method that returns the vehicle description
     *
     * @return vehicleDescription description of vehicle
     */
    public String getVehicleDescription() {
        return this.vehicleDescription;
    }

    /**
     * Method that returns user name
     *
     * @return userName username of user
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Method that returns start park id
     *
     * @return startParkId id of startpark
     */
    public String getStartParkId() {
        return this.startParkId;
    }

    /**
     * Method that returns end park id
     *
     * @return endParkId id of end park
     */
    public String getEndParkId() {
        return this.endParkId;
    }

    /**
     * Method that returns the start time
     *
     * @return startTime string with the start time of trip
     */
    public String getStartTime() {
        return this.startTime;
    }

    /**
     * Method that returns the finish toime
     *
     * @return endTime string with end time of trip
     */
    public String getEndTime() {
        return this.endTime;
    }

    /**
     * Method that returns the cost of trip
     *
     * @return cost double with the cost of trip
     */
    public double getCost() {
        return cost;
    }

    /**
     * Method that returns the id of invoice
     *
     * @return invoiceId id of invoice
     */
    public Integer getInvoiceId() {
        return invoiceId;
    }

    /**
     * Method that specifies ride id
     *
     * @param tripId id of trip
     */
    public final void setTripId(int tripId) {
        this.tripId = tripId;
    }
     /**
     * Method that specifies points 
     *
     * @param points  of trip
     */
    public final void setPoints(int points) {
        this.points = points;
    }

    /**
     * Method that specifies vehicle description
     *
     * @param vehicleDescription description of vehicle
     */
    public final void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }

    /**
     * Method that specifies user name
     *
     * @param userName username of user
     */
    public final void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Method that specifies start park id
     *
     * @param startParkId id of startpark
     */
    public final void setStartParkId(String startParkId) {
        this.startParkId = startParkId;
    }

    /**
     * Method that specifies end park id
     *
     * @param endParkId id of end park
     */
    public final void setEndParkId(String endParkId) {
        this.endParkId = endParkId;
    }

    /**
     * Method that specifies the start time
     *
     * @param startTime string with the start time of trip
     */
    public final void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * Method that specifies the finish time
     *
     * @param endTime string with end time of trip
     */
    public final void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * Method that specifies the cost of trip
     *
     * @param cost double with the cost of trip
     */
    public final void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Method that specifies the id of invoice
     *
     * @param invoiceId int with the invoice id
     */
    public final void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Method that returns hash code value
     *
     * @return hash, String, hash value
     */
    @Override
    public int hashCode() {
        return this.startTime.hashCode();
    }

    /**
     * Method that verifies equality between ride and another object
     *
     * @param obj, Object, another object to verify equality
     * @return true if the two are equal or false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Trip other = (Trip) obj;

        return this.tripId == other.tripId;
    }

    /**
     * Method that returns a string with the state of the object Trip
     *
     * @return stringRide.toString(), String with the state of the Trip instance
     */
    @Override
    public String toString() {
        return "Trip: \n" + "TripID=" + tripId + "\n VehicleDescription=" + vehicleDescription + "\n UserName=" + userName + "\n StartParkID=" + startParkId + "\n EndParkID=" + endParkId + "\n StartTime=" + startTime + "\n EndTime=" + endTime + "\n cost=" + cost + "\n points=" + points + "\n InvoiceID=" + invoiceId + '.';
    }

}
