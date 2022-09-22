/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lapr.project.data.BicycleDB;
import lapr.project.data.EscooterDB;
import lapr.project.data.ParkDB;
import lapr.project.data.TripDB;
import lapr.project.data.UserDB;
import lapr.project.model.Bicycle;
import lapr.project.model.Escooter;
import lapr.project.model.Park;
import lapr.project.model.Trip;
import lapr.project.model.User;
import lapr.project.utils.SendEmail;
import lapr.project.utils.Utils;

/**
 *
 *
 */
public class TripController {

    private TripDB tripDB;
    private BicycleDB bicycleDB;
    private EscooterDB escooterDB;
    private UserDB userDB;
    private ParkDB parkDB;

    public TripController() {
        tripDB = new TripDB();
        bicycleDB = new BicycleDB();
        escooterDB = new EscooterDB();
        userDB = new UserDB();
        parkDB = new ParkDB();

    }

    /**
     * Set bicycle instance
     *
     * @param bicycleDB new bicycle instance
     */
    public void setBicycleDB(BicycleDB bicycleDB) {
        this.bicycleDB = bicycleDB;
    }

    /**
     * Set escooter instance
     *
     * @param escooterDB new escooter instance
     */
    public void setEscooterDB(EscooterDB escooterDB) {
        this.escooterDB = escooterDB;
    }

    /**
     * Set user instance
     *
     * @param userDB new user instance
     */
    public void setUserDB(UserDB userDB) {
        this.userDB = userDB;
    }

    /**
     * Set park instance
     *
     * @param parkDB new park instance
     */
    public void setParkDB(ParkDB parkDB) {
        this.parkDB = parkDB;
    }

    /**
     * Set tripDB to instance.
     *
     * @param tripDB new instance of TripDB
     */
    public void setTripDB(TripDB tripDB) {
        this.tripDB = tripDB;
    }

    /**
     * Validates, creates and adds a Trip to the table "Trips" by unlocking a
     * bicycle according to the data passed as parameters
     *
     * @param bikeDescp description of the vehicle in the trip.
     * @param userName user name of the trip.
     * @param parkLat latitude of the park (in metres) of the trip.
     * @param parkLong longitude of the park for the trip.
     * @return trip object trip created
     */
    public Trip unlockBicycle(String bikeDescp, String userName, double parkLat, double parkLong) {
        try {

            Bicycle bike = bicycleDB.getBicycle(bikeDescp);

            User user = userDB.getUserByUserName(userName);
            Park park = parkDB.getParkByCoordinates(parkLat, parkLong);

            if (user == null || park == null || bike == null) {
                return null;
            }

            // conf´irms that user has no ongoing trip
            Trip incompletTrip = new Trip();
            incompletTrip = tripDB.getOnGoingTrips(userName);
            if (incompletTrip != null) {
                return null;
            }

            bike.setParkLat(0);
            bike.setParkLong(0);
            bicycleDB.updateBicycle(bike);

            String starTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
            Trip trip = new Trip();
            trip.setVehicleDescription(bikeDescp);
            trip.setUserName(userName);
            trip.setStartParkId(park.getId());
            trip.setStartTime(starTime);
            tripDB.addTrip(trip);
            return trip;

        } catch (IllegalArgumentException ex1) {
            return null;
        }
    }

    /**
     * Validates, creates and adds a Trip to the table "Trips" by unlocking a
     * Escooter according to the data passed as parameters
     *
     * @param eScooterDescp description of the vehicle in the trip.
     * @param userName user name of the trip.
     * @param parkLat latitude of the park (in metres) of the trip.
     * @param parkLong longitude of the park for the trip.
     * @return trip object trip created
     */
    public Trip unlockEscooter(String eScooterDescp, String userName, double parkLat, double parkLong) {
        try {
            Escooter eScooter = escooterDB.getEscooter(eScooterDescp);

            User user = userDB.getUserByUserName(userName);
            Park park = parkDB.getParkByCoordinates(parkLat, parkLong);

            if (user == null || park == null || eScooter == null) {
                return null;
            }

            // confirms that user has no ongoing trip
            Trip incompletTrip = new Trip();
            incompletTrip = tripDB.getOnGoingTrips(userName);
            if (incompletTrip != null) {
                return null;
            }

            eScooter.setParkLat(0);
            eScooter.setParkLong(0);
            escooterDB.updateEscooter(eScooter);

            String starTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
            Trip trip = new Trip();
            trip.setVehicleDescription(eScooterDescp);
            trip.setUserName(userName);
            trip.setStartParkId(park.getId());
            trip.setStartTime(starTime);
            tripDB.addTrip(trip);
            return trip;

        } catch (IllegalArgumentException ex1) {
            return null;
        }
    }

    /**
     * Locks a bicycle by finishing a trip. calculates the duration , points and
     * cost.updates bicycle and user, adds line to invoice and sends email to
     * user
     *
     * @param bike bicycle to lock
     * @param park destiantion park
     * @param user user that has bicycle unlocked
     * @return onGoingTrip the trip object that was terminated.
     */
    public Trip lockBicycle(Bicycle bike, Park park, User user) {
        try {
            if (bike.getParkLat() != 0 || bike.getParkLong() != 0) {
                return null;
            }
            Trip onGoingTrip = tripDB.getOnGoingTrips(user.getUserName());
            if (onGoingTrip == null) {
                return null;
            }

            //Calculate trip duration
            String strEndTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
            Date startTime = Utils.stringToDate(onGoingTrip.getStartTime());
            Date endTime = Utils.stringToDate(strEndTime);

            double tripDuration = Utils.getTripDuration(startTime, endTime);
            //calcualte trip cost
            Double cost = Utils.calculateTripCost(tripDuration);
            tripDuration = Utils.getTripDurationInSeconds(onGoingTrip.getStartTime(), strEndTime); // converte para segundos
            //update vehcile
            bike.setParkLat(park.getLatitude());
            bike.setParkLong(park.getLongitude());
            bicycleDB.updateBicycle(bike);
            //calculate points
            Park starPark = parkDB.getPark(onGoingTrip.getStartParkId());
            int points = Utils.calculatePointsFromAltitude(starPark.getElevation(), park.getElevation());

            //update user
            user.addPoints(points);
            userDB.updateUser(user);

            //update trip   
            onGoingTrip.setEndTime(strEndTime);
            onGoingTrip.setCost(cost);
            onGoingTrip.setEndParkId(park.getId());
            onGoingTrip.setPoints(points);
            //FALTA GERAR INVOICE
            tripDB.updateTrip(onGoingTrip);

            SendEmail.validateSendEmail(user.getEmail(), "New Finished Trip", "Your trip had the durantion of " + (int) tripDuration + " seconds and is now correctly locked.");
            //calculate calorias
            return onGoingTrip;
        } catch (IllegalArgumentException ex1) {
            return null;
        }
    }

    /**
     * Locks a Escooter by finishing a trip. calculates the duration , points
     * and cost. updates Escooter and user, adds line to invoice and sends email
     * to user
     *
     * @param scooter Escooter to lock
     * @param park destiantion park
     * @param user user that has bicycle unlocked
     * @return onGoingTrip the trip object that was terminated.
     */
    public Trip lockEscooter(Escooter scooter, Park park, User user) {
        try {
            if (scooter.getParkLat() != 0 || scooter.getParkLong() != 0) {
                return null;
            }
            Trip onGoingTrip = tripDB.getOnGoingTrips(user.getUserName());
            if (onGoingTrip == null) {
                return null;
            }
            //Calculate trip duration
            String strEndTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
            Date startTime = Utils.stringToDate(onGoingTrip.getStartTime());
            Date endTime = Utils.stringToDate(strEndTime);

            double tripDuration = Utils.getTripDuration(startTime, endTime);
            //calcualte trip cost
            Double cost = Utils.calculateTripCost(tripDuration);
            tripDuration = Utils.getTripDurationInSeconds(onGoingTrip.getStartTime(), strEndTime); // converte para segundos
            //update vehcile
            scooter.setParkLat(park.getLatitude());
            scooter.setParkLong(park.getLongitude());
            escooterDB.updateEscooter(scooter);
            //calculate points
            Park starPark = parkDB.getPark(onGoingTrip.getStartParkId());
            int points = Utils.calculatePointsFromAltitude(starPark.getElevation(), park.getElevation());
            //update user
            user.addPoints(points);
            userDB.updateUser(user);

            //update trip   
            onGoingTrip.setEndTime(strEndTime);
            onGoingTrip.setCost(cost);
            onGoingTrip.setEndParkId(park.getId());
            onGoingTrip.setPoints(points);
            //FALTA GERAR INVOICE
            tripDB.updateTrip(onGoingTrip);

            SendEmail.validateSendEmail(user.getEmail(), "New Finished Trip", "Your trip had the durantion of " + (int) tripDuration + " seconds and is now correctly locked.");

            return onGoingTrip;
        } catch (IllegalArgumentException ex1) {
            return null;
        }
    }

    /**
     * Calculate the energy required to travel from A to B
     *
     * @param latA latitude point A
     * @param longA longitude point A
     * @param latB latitude point B
     * @param longB longitude point B
     * @param userName user Name
     * @return true if the scooter can do the trip, or false order whise
     */
    public double energyRequiredToTravelAtoB(double latA, double longA, double latB, double longB, String userName) {

        try {
            Trip trip = tripDB.getOnGoingTrips(userName);
            if (trip != null) {
                Escooter escooter = escooterDB.getEscooter(trip.getVehicleDescription());

                double dist = Utils.distance(latA, longA, latB, longB);
                float motor = escooter.getMotor();

                double seconds = Utils.calcDurationInSeconds(dist);

                return motor * seconds;
            }
        } catch (IllegalArgumentException ex) {
        }
        return -1;

    }

    /**
     * Returns the description of the vehicle currently loaned by the username
     * passed as parameter. Returns null if no vehicle is currently loaned.
     *
     * @param username identification name for the user
     * @return description of currently loaned vehicle; or null
     */
    public String getLoanedVehicle(String username) {
        Trip trip = getActiveTrip(username);
        if (trip != null) {
            return trip.getVehicleDescription();
        }
        return null;
    }

    /**
     * Gets active trip from user with username passed. Or null if user has no
     * active trip.
     *
     * @param username identification name for the user
     * @return Trip currently active for that username; or null
     */
    public Trip getActiveTrip(String username) {
        Trip trip;
        try {
            trip = tripDB.getOnGoingTrips(username);
        } catch (IllegalArgumentException ex) {
            trip = null;
        }
        return trip;
    }

    /**
     * Returns all ongoing trips in the current moment.
     *
     * @return list of objects Trip
     */
    public List<Trip> getOngoingTrips() {
        return tripDB.getOnGoingTrips();
    }

    /**
     * Returns all ongoing trips with no invoice in the current moment.
     *
     * @param username username of the trips
     * @return list of trips
     */
    public List<Trip> getTripsWithoutInvoice(String username) {
        return tripDB.getTrips(username , 0);
    }

    /**
     * Updates a trip instance
     *
     * @param trip trip object to update *
     */
    public void updateTrip(Trip trip) {
        tripDB.updateTrip(trip);
    }

    /**
     * Returns and shows all trips done by User.
     *
     * @param username username of the trips
     */
    public void getLockingHistory(String username) {
        List<Trip> tripList = tripDB.getTrips(username , 1);
       if(!tripList.isEmpty()){
        for (Trip tmp : tripList) {
            System.out.println("Vehicle Description: " + tmp.getVehicleDescription() + "; Start Time: " + tmp.getStartTime() + "; End Time: " + tmp.getEndTime() + "; Star Park: " + tmp.getStartParkId() + "; End Park: " + tmp.getEndParkId());
        }
       }
    }
    
     /**
     * Returns all finished trips from USer
     *
     * @param username username of the trips
     * @return list of trips
     */
    public List<Trip> getAllTrips(String username) {
        return tripDB.getTrips(username , 1);
    }
}
