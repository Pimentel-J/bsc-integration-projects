/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lapr.project.model.Trip;
import oracle.jdbc.OracleTypes;

/**
 *
 *
 */
public class TripDB extends DataHandler {

    /**
     * Adds specified trip to the table "Trip".
     *
     * @param trip Trip specified
     */
    public void addTrip(Trip trip) {
        addTrip(trip.getVehicleDescription(), trip.getUserName(), trip.getStartParkId(), trip.getEndParkId(), trip.getStartTime(), trip.getEndTime(), trip.getCost(), trip.getPoints(), trip.getInvoiceId());
    }

    /**
     * Invocation of a "stored procedure".
     *
     * Adds specified trip to the table "Trips".
     *
     * @param vehicleDescr vehicle description of the Trip.
     * @param userName user name of the trip.
     * @param startParkId start park id of the trip.
     * @param endParkId end park id for the trip.
     * @param startTime start time for the trip.
     * @param endTime end time for the trip.
     * @param cost cost for the trip.
     * @param invoiceId id of invoice for the trip.
     *
     */
    private void addTrip(String vehicleDescr, String userName, String startParkId, String endParkId, String startTime, String endTime, Double cost, int points, Integer invoiceId) {
        CallableStatement callStmt = null;
        try {
            // 1. Start the connection
            openConnection();
            // 2. Query the database
            callStmt = getConnection().prepareCall("{ call ADDTRIP(?,?,?,?,?,?,?,?,?) }");
            callStmt.setString(1, vehicleDescr);
            callStmt.setString(2, userName);
            callStmt.setString(3, startParkId);
            callStmt.setString(4, endParkId);
            callStmt.setTimestamp(5, Timestamp.valueOf(startTime));
            if (endTime == null) {
                callStmt.setString(6, null);
            } else {
                callStmt.setTimestamp(6, Timestamp.valueOf(endTime));
            }
            callStmt.setDouble(7, cost);
            callStmt.setDouble(8, points);
            if (invoiceId == null) {
                callStmt.setObject(9, null);
            } else {
                callStmt.setInt(9, invoiceId);
            }
            callStmt.execute();
            // 3. Close all connections
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Not possible to add Trip");
        } finally {
            closeAll();
        }
    }

    /**
     * Invocation of a "stored function".
     * <p>
     * Returns the registry of the specified Trio existing in the "Trips" table.
     *
     * @param username username of the Trip.
     * @return the registry of the specified Trip or null if it doesn't exist.
     */
    public Trip getTrip(String username) {

        /* Object "callStmt" to call the function "getPoi" saved in the DB.
         *
         * FUNCTION getPoi(latitude NUMBER, longitude NUMBER) RETURN pkgPOIs.ref_cursor
         * PACKAGE pkgPOIs AS TYPE ref_cursor IS REF CURSOR; END pkgPOIs;
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getTrip(?) }");

            // Records SQL type of data to interpret the obtained result.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Specifies entry parameters of function "getTrip".
            callStmt.setString(2, username);

            // Executes the call to the function "getTrip".
            callStmt.execute();

            // Saves returned cursor in an object "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int tripId = rSet.getInt(1);
                String vehicleDescription = rSet.getString(2);
                String userName = rSet.getString(3);
                String startParkId = rSet.getString(4);
                String endParkId = rSet.getString(5);
                String startTime = rSet.getString(6);
                String endTime = rSet.getString(7);
                double cost = rSet.getDouble(8);
                int points = rSet.getInt(9);
                Integer invoiceId = rSet.getInt(10);
                if (invoiceId == 0) {
                    invoiceId = null;
                }
                return new Trip(tripId, vehicleDescription, userName, startParkId, endParkId, startTime, endTime, cost, points, invoiceId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Trip with user name=" + username);
    }

    /**
     * Method that querys the Trips table to see if there is a trip for a with
     * matching username that is not finished yet (if the timestamp_finish
     * column is null , trip is not finished.
     *
     * @param userName username of the Trip.
     * @return the object of the specified Trip or null if it doesn't exist.
     */
    public Trip getOnGoingTrips(String userName) {
        Statement stm;
        List<Trip> trips = new ArrayList<>();
        String query = "SELECT * FROM Trips WHERE Trips.timestamp_finish is null AND Trips.username = '" + userName + "'";

        try {
            ResultSet rSet = null;
            stm = getConnection().createStatement();
            // 2. Query the database
            rSet = stm.executeQuery(query);
            // 3. Get the data
            while (rSet.next()) {
                int tripId = rSet.getInt(1);
                String vehicleDescription = rSet.getString(2);
                String username = rSet.getString(3);
                String startParkId = rSet.getString(4);
                String endParkId = rSet.getString(5);
                String startTime = rSet.getString(6);
                String endTime = rSet.getString(7);
                double cost = rSet.getDouble(8);
                int points = rSet.getInt(9);
                Integer invoiceId = rSet.getInt(10);
                if (invoiceId == 0) {
                    invoiceId = null;
                }
                Trip trip = new Trip(tripId, vehicleDescription, username, startParkId,
                        endParkId, startTime, endTime, cost, points, invoiceId);
                trips.add(trip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return trips.isEmpty() ? null : trips.get(0);
    }

    /**
     * Updates specified trip to the table "Trip".
     *
     * @param trip Trip specified
     */
    public void updateTrip(Trip trip) {
        updateTrip(trip.getTripId(), trip.getVehicleDescription(), trip.getUserName(), trip.getStartParkId(), trip.getEndParkId(), trip.getStartTime(), trip.getEndTime(), trip.getCost(), trip.getPoints(), trip.getInvoiceId());
    }

    /**
     * Invocation of a "stored procedure".
     *
     * Updates specified trip to the table "Trips".
     *
     * @param vehicleDescr vehicle description of the Trip.
     * @param userName user name of the trip.
     * @param startParkId start park id of the trip.
     * @param endParkId end park id for the trip.
     * @param startTime start time for the trip.
     * @param endTime end time for the trip.
     * @param cost cost for the trip.
     * @param invoiceId id of invoice for the trip.
     *
     */
    private void updateTrip(int tripId, String vehicleDescr, String userName, String startParkId, String endParkId, String startTime, String endTime, Double cost, int points, Integer invoiceId) {
        CallableStatement callStmt = null;
        try {
            // 1. Start the connection
            openConnection();
            // 2. Query the database
            callStmt = getConnection().prepareCall("{ call UPDATETRIP(?,?,?,?,?,?,?,?,?,?) }");
            callStmt.setInt(1, tripId);
            callStmt.setString(2, vehicleDescr);
            callStmt.setString(3, userName);
            callStmt.setString(4, startParkId);
            callStmt.setString(5, endParkId);
            callStmt.setTimestamp(6, Timestamp.valueOf(startTime));
            if (endTime == null) {
                callStmt.setString(7, null);
            } else {
                callStmt.setTimestamp(7, Timestamp.valueOf(endTime));
            }
            callStmt.setDouble(8, cost);
            callStmt.setInt(9, points);
            if (invoiceId == null) {
                callStmt.setObject(10, null);
            } else {
                callStmt.setInt(10, invoiceId);
            }
            callStmt.execute();
            // 3. Close all connections
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Not possible to update Trip");
        } finally {
            closeAll();
        }
    }

    /**
     * Returns all ongoing trips.
     *
     * @return list of objects Trip; or null if an exception occurs.
     */
    public List<Trip> getOnGoingTrips() {
        Statement stm;
        List<Trip> trips = new ArrayList<>();
        String query = "SELECT * FROM Trips WHERE Trips.timestamp_finish IS NULL";

        try {
            ResultSet rSet = null;
            stm = getConnection().createStatement();
            // 2. Query the database
            rSet = stm.executeQuery(query);
            // 3. Get the data
            while (rSet.next()) {
                int tripId = rSet.getInt(1);
                String vehicleDescription = rSet.getString(2);
                String username = rSet.getString(3);
                String startParkId = rSet.getString(4);
                String endParkId = rSet.getString(5);
                String startTime = rSet.getString(6);
                String endTime = rSet.getString(7);
                double cost = rSet.getDouble(8);
                int points = rSet.getInt(9);
                Integer invoiceId = rSet.getInt(10);
                if (invoiceId == 0) {
                    invoiceId = null;
                }
                Trip trip = new Trip(tripId, vehicleDescription, username, startParkId,
                        endParkId, startTime, endTime, cost, points, invoiceId);
                trips.add(trip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            trips = null;
        } finally {
            closeAll();
        }
        return trips;
    }

    /**
     * Returns all trips.
     *
     * @param userName username to search for
     * @param controller determinates if its with invoice or not. (0) without
     * invoice
     * @return list of objects Trip; or null if an exception occurs.
     */
    public List<Trip> getTrips(String userName, int controller) {
        Statement stm;
        List<Trip> trips = new ArrayList<>();
        String query = null;
        if (controller == 0) {
            query = "SELECT * FROM Trips WHERE Trips.invoice_id IS NULL AND Trips.timestamp_finish IS NOT NULL AND Trips.username = '" + userName + "'";
        } else {
            query = "SELECT * FROM Trips WHERE Trips.timestamp_finish IS NOT NULL AND Trips.username = '" + userName + "'";

        }
        try {
            ResultSet rSet = null;
            stm = getConnection().createStatement();
            // 2. Query the database
            rSet = stm.executeQuery(query);
            // 3. Get the data
            while (rSet.next()) {
                int tripId = rSet.getInt(1);
                String vehicleDescription = rSet.getString(2);
                String username = rSet.getString(3);
                String startParkId = rSet.getString(4);
                String endParkId = rSet.getString(5);
                String startTime = rSet.getString(6);
                String endTime = rSet.getString(7);
                double cost = rSet.getDouble(8);
                int points = rSet.getInt(9);
                Integer invoiceId = rSet.getInt(10);
                if (invoiceId == 0) {
                    invoiceId = null;
                }
                Trip trip = new Trip(tripId, vehicleDescription, username, startParkId,
                        endParkId, startTime, endTime, cost, points, invoiceId);
                trips.add(trip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            trips = null;
        } finally {
            closeAll();
        }
        return trips;
    }

}
