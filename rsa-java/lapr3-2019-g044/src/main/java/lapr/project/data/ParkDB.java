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
import java.util.ArrayList;
import java.util.List;
import lapr.project.model.Park;
import oracle.jdbc.OracleTypes;

/**
 * Class used to do operations with parks in the database.
 */
public class ParkDB extends DataHandler {

    /**
     * Invocation of a "stored function".
     * <p>
     * Returns the registry of the specified park existing in the "Parks" table.
     *
     * @param id identifier of the park.
     * @return the registry of the specified park or null if it doesn't exist.
     */
    public Park getPark(String id) {

        /* Object "callStmt" to call the function "getPark" saved in the DB.
         *
         * FUNCTION getPark(id NUMBER) RETURN pkgParks.ref_cursor
         * PACKAGE pkgParks AS TYPE ref_cursor IS REF CURSOR; END pkgParks;
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getPark(?) }");

            // Records SQL type of data to interpret the obtained result.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Specifies entry parameter of function "getPark".
            callStmt.setString(2, id);

            // Executes the call to the function "getPark".
            callStmt.execute();

            // Saves returned cursor in an object "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String parkID = rSet.getString(1);
                double lat = rSet.getDouble(2);
                double longit = rSet.getDouble(3);
                int elev = rSet.getInt(4);
                String descr = rSet.getString(5);
                int maxBicycles = rSet.getInt(6);
                int maxEscooters = rSet.getInt(7);
                int inputVolt = rSet.getInt(8);
                int inputCurr = rSet.getInt(9);

                return new Park(parkID, lat, longit, elev, descr, maxBicycles,
                        maxEscooters, inputVolt, inputCurr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Park with ID:" + id);
    }

    /**
     * Adds specified park to the table "Parks".
     *
     * @param park park specified
     */
    public void addPark(Park park) {
        addPark(park.getId(),
                park.getLatitude(),
                park.getLongitude(),
                park.getElevation(),
                park.getDescription(),
                park.getMaxNumberBicycles(),
                park.getMaxNumberEscooters(),
                park.getInputVoltage(),
                park.getInputCurrent());
    }

    /**
     * Invocation of a "stored procedure".
     * <p>
     * Adds specified park to the table "Parks".
     *
     * @param id identifier of the park.
     * @param latitude latitude of the park.
     * @param longitude longitude of the park.
     * @param elevation elevation/altitude (in metres) of the park.
     * @param description descriptive name for the park.
     * @param maxNumberBicycles maximum park capability for bicycles.
     * @param maxNumberEscooters maximum park capability for escooters.
     * @param inputVoltage park input voltage (in Volts).
     * @param inputCurrent park input current (in Amperes).
     */
    private void addPark(String id, double latitude, double longitude, int elevation,
            String description, int maxNumberBicycles, int maxNumberEscooters, int inputVoltage, int inputCurrent) {

        try {
            openConnection();
            /*
             *  Object "callStmt" to call the procedure "addPark" saved in the 
             * DB.
             *
             *  PROCEDURE addPark(id INTEGER, latitude NUMBER, longitude NUMBER, 
             * elevation INTEGER, description VARCHAR, maxNumberBicycles INTEGER,
             * maxNumberEscooters INTEGER, inputVoltage INTEGER, inputCurrent INTEGER)
             *  PACKAGE pkgParks AS TYPE ref_cursor IS REF CURSOR; END pkgParks;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addPark(?,?,?,?,?,?,?,?,?) }");

            callStmt.setString(1, id);
            callStmt.setDouble(2, latitude);
            callStmt.setDouble(3, longitude);
            callStmt.setInt(4, elevation);
            callStmt.setString(5, description);
            callStmt.setInt(6, maxNumberBicycles);
            callStmt.setInt(7, maxNumberEscooters);
            callStmt.setInt(8, inputVoltage);
            callStmt.setInt(9, inputCurrent);

            callStmt.execute();

            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No Park with ID:" + id);
        } finally {
            closeAll();
        }
    }

    /**
     * Update a specific park from the database, given it's park, return it's
     * id.
     *
     * @param park
     * @return park id
     */
    public String updatePark(Park park) {
        return updatePark(park.getId(),
                park.getLatitude(),
                park.getLongitude(),
                park.getElevation(),
                park.getDescription(),
                park.getMaxNumberBicycles(),
                park.getMaxNumberEscooters(),
                park.getInputVoltage(),
                park.getInputCurrent());
    }

    /**
     * Invocation of a "stored functio ".
     * <p>
     * Updates a park in table "Parks".
     *
     * @param id identifier of the park.
     * @param latitude latitude of the park.
     * @param longitude longitude of the park.
     * @param elevation elevation/altitude (in metres) of the park.
     * @param description descriptive name for the park.
     * @param maxNumberBicycles maximum park capability for bicycles.
     * @param maxNumberEscooters maximum park capability for escooters.
     * @param inputVoltage park input voltage (in Volts).
     * @param inputCurrent park input current (in Amperes).
     */
    private String updatePark(String id, double latitude, double longitude, int elevation,
            String description, int maxNumberBicycles, int maxNumberEscooters, int inputVoltage, int inputCurrent) {
        String idParkUpdated = null;
        try {
            // 1. Start the connection
            openConnection();
            // 2. Query the database
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call updatePark(?,?,?,?,?,?,?,?,?) }");
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, id);
            callStmt.setDouble(3, latitude);
            callStmt.setDouble(4, longitude);
            callStmt.setInt(5, elevation);
            callStmt.setString(6, description);
            callStmt.setInt(7, maxNumberBicycles);
            callStmt.setInt(8, maxNumberEscooters);
            callStmt.setInt(9, inputVoltage);
            callStmt.setInt(10, inputCurrent);
            callStmt.execute();
            idParkUpdated = callStmt.getString(1);
            // 3. Close the connection
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return idParkUpdated;
    }

    /**
     * Invocation of a "stored functio ".
     * <p>
     * Deletes a park in table "Parks".
     *
     * @param id identifier of the park.
     * @return idParkDeleted park id that was deeleted
     */
    public String deletePark(String id) {
        String idParkDeleted = null;
        try {
            // 1. Start the connection
            openConnection();
            // 2. Query the database
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call deletePark(?) }");
            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, id);

            callStmt.execute();
            idParkDeleted = callStmt.getString(1);
            // 3. Close the connection
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return idParkDeleted;
    }

    /**
     * Invocation of a "stored function".
     * <p>
     * Returns the registry of the specified park existing in the "Parks" table.
     *
     * @param latitude
     * @param longitude
     * @return the registry of the specified park or null if it doesn't exist.
     */
    public Park getParkByCoordinates(double latitude, double longitude) {

        /* Object "callStmt" to call the function "getPark" saved in the DB.
         *
         * FUNCTION getPark(id NUMBER) RETURN pkgParks.ref_cursor
         * PACKAGE pkgParks AS TYPE ref_cursor IS REF CURSOR; END pkgParks;
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getParkByCoordinates(?,?) }");

            // Records SQL type of data to interpret the obtained result.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Specifies entry parameter of function "getPark".
            callStmt.setDouble(2, latitude);
            callStmt.setDouble(3, longitude);
            // Executes the call to the function "getPark".
            callStmt.execute();

            // Saves returned cursor in an object "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String parkID = rSet.getString(1);
                double lat = rSet.getDouble(2);
                double longit = rSet.getDouble(3);
                int elev = rSet.getInt(4);
                String descr = rSet.getString(5);
                int maxBicycles = rSet.getInt(6);
                int maxEscooters = rSet.getInt(7);
                int inputVolt = rSet.getInt(8);
                int inputCurr = rSet.getInt(9);

                return new Park(parkID, lat, longit, elev, descr, maxBicycles,
                        maxEscooters, inputVolt, inputCurr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Park with those coordinates.");
    }

    /**
     * Adds multiple Parks to the table "Parks". If not able to insert one, does
     * not insert any. It's all or nothing.
     *
     * @param parks Parks to insert
     */
    public void addParks(List<Park> parks) {

        try {
            openConnection();
            getConnection().setAutoCommit(false);

            /*
             *  Object "callStmt" to call the procedure "addPark" saved in the 
             * DB.
             *
             *  PROCEDURE addPark(id INTEGER, latitude NUMBER, longitude NUMBER, 
             * elevation INTEGER, description VARCHAR, maxNumberBicycles INTEGER,
             * maxNumberEscooters INTEGER, inputVoltage INTEGER, inputCurrent INTEGER)
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addPark(?,?,?,?,?,?,?,?,?) }");

            int numParks = parks.size();
            for (int i = 0; i < numParks; i++) {
                Park park = parks.get(i);

                callStmt.setString(1, park.getId());
                callStmt.setDouble(2, park.getLatitude());
                callStmt.setDouble(3, park.getLongitude());
                callStmt.setInt(4, park.getElevation());
                callStmt.setString(5, park.getDescription());
                callStmt.setInt(6, park.getMaxNumberBicycles());
                callStmt.setInt(7, park.getMaxNumberEscooters());
                callStmt.setInt(8, park.getInputVoltage());
                callStmt.setInt(9, park.getInputCurrent());

                callStmt.execute();
            }

            getConnection().commit();
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                getConnection().rollback();
                throw new IllegalArgumentException("Not possible to add Parks");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            closeAll();
        }
    }

    /**
     * Runs a query that returns all the bicycles in the DataBase
     *
     * @return a list with all Bicycles in DB exist.
     */
    public List<Park> getAllParks() {

        List<Park> parkList = new ArrayList<>();

        Statement stm;
        try {
            ResultSet rSet = null;
            String query = "Select * FROM parks";
            stm = getConnection().createStatement();
            rSet = stm.executeQuery(query);

            while (rSet.next()) {
                String parkID = rSet.getString(1);
                double lat = rSet.getDouble(2);
                double longit = rSet.getDouble(3);
                int elev = rSet.getInt(4);
                String descr = rSet.getString(5);
                int maxBicycles = rSet.getInt(6);
                int maxEscooters = rSet.getInt(7);
                int inputVolt = rSet.getInt(8);
                int inputCurr = rSet.getInt(9);

                Park park = new Park(parkID, lat, longit, elev, descr, maxBicycles,
                        maxEscooters, inputVolt, inputCurr);
                parkList.add(park);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return parkList;
    }
}
