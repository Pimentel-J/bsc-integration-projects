package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lapr.project.model.Escooter;
import lapr.project.model.Escooter.Type;
import oracle.jdbc.OracleTypes;

/**
 * Class used to do operations with escooters in the database.
 *
 *
 */
public class EscooterDB extends DataHandler {

    /**
     * Invocation of a "stored function".
     * <p>
     * Returns the registry of the specified escooter existing in the
     * "Escooters" table.
     *
     * @param descr identifier of the escooter.
     * @return the registry of the specified escooter or null if it doesn't
     * exist.
     */
    public Escooter getEscooter(String descr) {
        /* Object "callStmt" to call the function "getEscooter" saved in the DB.
         *
         * FUNCTION getEscooter(descr VARCHAR) RETURN pkgEscooters.ref_cursor
         * PACKAGE pkgEscooters AS TYPE ref_cursor IS REF CURSOR; END pkgEscooters
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getEscooter(?) }");

            // Records SQL type of data to interpret the obtained result.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Specifies entry parameter of function "getEscooter".
            callStmt.setString(2, descr);

            // Executes the call to the function "getEscooter".
            callStmt.execute();

            // Saves returned cursor in an object "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String escooterDescr = rSet.getString(1);
                int weight = rSet.getInt(2);
                String type = rSet.getString(3);
                double parkLat = rSet.getDouble(4);
                double parkLong = rSet.getDouble(5);
                float maxBattery = rSet.getFloat(6);
                int actualBattery = rSet.getInt(7);
                float aerodynCoeffic = rSet.getFloat(8);
                float frontalArea = rSet.getFloat(9);
                float motor = rSet.getFloat(10);

                return new Escooter(escooterDescr, weight, Type.valueOf(type), parkLat,
                        parkLong, maxBattery, actualBattery, aerodynCoeffic, frontalArea, motor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Escooter with description: " + descr);
    }

    /**
     * Adds specified escooter to the table "Escooters".
     *
     * @param escooter escooter specified
     */
    public void addEscooter(Escooter escooter) {
        addEscooter(escooter.getDescr(),
                escooter.getWeight(),
                escooter.getType(),
                escooter.getParkLat(),
                escooter.getParkLong(),
                escooter.getMaxBattery(),
                escooter.getActualBattery(),
                escooter.getAerodynCoeffic(),
                escooter.getFrontalArea(),
                escooter.getMotor());
    }

    /**
     * Invocation of a "stored procedure".
     * <p>
     * Adds specified escooter to the table "Escooters".
     *
     * @param descr - unique escooter reference
     * @param weight - escooter weight in kg (no decimal places)
     * @param type - type of escooter (city or off-road)
     * @param parkLat - latitude of the park where the escooter is parked
     * (decimal degrees)
     * @param parkLong - longitude of the park where the escooter is parked
     * (decimal degrees)
     * @param maxBattery - escooter maximum battery capacity in kWh (2 decimal
     * places)
     * @param actualBattery - escooter current battery capacity in percentage
     * (no decimal places)
     * @param aerodynCoeffic - escooter aerodynamic coefficient (2 decimal
     * places)
     * @param frontalArea - escooter frontal area in m^2 (1 decimal place)
     * @param motor - escooter motor power in kWh (2 decimal places)
     */
    private void addEscooter(String descr, int weight, Type type, double parkLat, double parkLong,
            float maxBattery, int actualBattery, float aerodynCoeffic, float frontalArea, float motor) {

        try {
            openConnection();
            try (
                    /*
             *  Object "callStmt" to call the procedure "addEscooter" saved in the 
             * DB.
             *
             *  PROCEDURE addEscooter(descr VARCHAR, weight INTEGER, etype VARCHAR, 
             *  parkLat NUMBER, parkLong NUMBER, maxBattery NUMBER, actualBattery INTEGER, 
             *  aerodynCoeffic NUMBER, frontalArea NUMBER, motor INTEGER)
             *  PACKAGE pkgEscooters AS TYPE ref_cursor IS REF CURSOR; END pkgEscooters
                     */
                    CallableStatement callStmt = getConnection().prepareCall("{ call addEscooter(?,?,?,?,?,?,?,?,?,?) }")) {

                callStmt.setString(1, descr);
                callStmt.setInt(2, weight);
                callStmt.setString(3, type.name());
                callStmt.setDouble(4, parkLat);
                callStmt.setDouble(5, parkLong);
                callStmt.setFloat(6, maxBattery);
                callStmt.setInt(7, actualBattery);
                callStmt.setFloat(8, aerodynCoeffic);
                callStmt.setFloat(9, frontalArea);
                callStmt.setFloat(10, motor);

                callStmt.execute();
            }
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No Escooter with description: " + descr);
        } finally {
            closeAll();
        }
    }

    /**
     * Adds multiple escooters to the table "Escooters". If not able to insert
     * one, does not insert any. It's all or nothing.
     *
     * @param escooters - Escooters to insert
     */
    public void addEscooters(List<Escooter> escooters) {

        try {
            openConnection();
            getConnection().setAutoCommit(false);
            /*
             *  Object "callStmt" to call the procedure "addEscooter" saved in the 
             * DB.
             *
             *  PROCEDURE addEscooter(descr VARCHAR, weight INTEGER, etype VARCHAR, 
             *  parkLat NUMBER, parkLong NUMBER, maxBattery NUMBER, actualBattery INTEGER, 
             *  aerodynCoeffic NUMBER, frontalArea NUMBER, motor INTEGER)
             *  PACKAGE pkgEscooters AS TYPE ref_cursor IS REF CURSOR; END pkgEscooters
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addEscooter(?,?,?,?,?,?,?,?,?,?) }");

            int numBikes = escooters.size();
            for (int i = 0; i < numBikes; i++) {
                Escooter escooter = escooters.get(i);

                callStmt.setString(1, escooter.getDescr());
                callStmt.setInt(2, escooter.getWeight());
                callStmt.setString(3, escooter.getType().name());
                callStmt.setDouble(4, escooter.getParkLat());
                callStmt.setDouble(5, escooter.getParkLong());
                callStmt.setFloat(6, escooter.getMaxBattery());
                callStmt.setInt(7, escooter.getActualBattery());
                callStmt.setFloat(8, escooter.getAerodynCoeffic());
                callStmt.setFloat(9, escooter.getFrontalArea());
                callStmt.setFloat(10, escooter.getMotor());

                callStmt.execute();
            }
            getConnection().commit();
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                getConnection().rollback();
                throw new IllegalArgumentException("Not possible to add Escooters");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            closeAll();
        }
    }

    /**
     * Runs a query that returns all the escooters in the DataBase
     *
     * @return a list with all escooter in DB exist.
     */
    public List<Escooter> getAllEscooters() {

        List<Escooter> escooterList = new ArrayList<>();

        Statement stm;
        try {
            ResultSet rSet = null;
            String query = "Select * FROM escooters";
            stm = getConnection().createStatement();
            rSet = stm.executeQuery(query);

            while (rSet.next()) {
                String descr = rSet.getString(1);
                int weight = rSet.getInt(2);
                String type = rSet.getString(3);
                double parkLat = rSet.getDouble(4);
                double parkLong = rSet.getDouble(5);
                float maxBattery = rSet.getFloat(6);
                int actualBattery = rSet.getInt(7);
                float aerodynCoeffic = rSet.getFloat(8);
                float frontalArea = rSet.getFloat(9);
                float motor = rSet.getFloat(10);

                Escooter escooter = new Escooter(descr, weight, Type.valueOf(type), parkLat, parkLong, maxBattery, actualBattery, aerodynCoeffic, frontalArea, motor);
                escooterList.add(escooter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return escooterList;
    }

    /**
     * Uptade an Escooter
     *
     * @param escooter escooter
     * @return true or false
     */
    public boolean updateEscooter(Escooter escooter) {
        return updateEscooter(escooter.getDescr(), escooter.getWeight(), escooter.getParkLat(),
                escooter.getParkLong(), escooter.getAerodynCoeffic(), escooter.getFrontalArea(),
                escooter.getType(), escooter.getMaxBattery(), escooter.getActualBattery(), escooter.getMotor());
    }

    /**
     * Update a escooter
     *
     * @param descr description
     * @param weight weight
     * @param parkLat park latitude
     * @param parkLong park longitude
     * @param aerodynCoeffic Aerodynamic Coefficient
     * @param frontalArea frontal Area
     * @param type Type
     * @param maxBattery Max battery
     * @param actualBattery Actual battery
     * @param motor motor power
     * @return true (if upade with sucess) or false (it dons't update)
     */
    private boolean updateEscooter(String descr, int weight, double parkLat, double parkLong,
            float aerodynCoeffic, float frontalArea, Type type, float maxBattery, int actualBattery, float motor) {

        int result = 0;

        try {
            openConnection();

            CallableStatement callStmt = getConnection().prepareCall("{? = call UPDATEVEHICLE(?,?,?,?,?,?,?,?,?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, descr);
            callStmt.setInt(3, weight);
            callStmt.setDouble(4, parkLat);
            callStmt.setDouble(5, parkLong);
            callStmt.setFloat(6, aerodynCoeffic);
            callStmt.setFloat(7, frontalArea);
            callStmt.setString(8, type.name());
            callStmt.setFloat(9, maxBattery);
            callStmt.setInt(10, actualBattery);
            callStmt.setNull(11, OracleTypes.INTEGER);
            callStmt.setFloat(12, motor);

            callStmt.execute();
            result = callStmt.getInt(1);

            return result != 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return result != 0;
        } finally {
            closeAll();
        }

    }

    /**
     * Invocation of a "stored function".
     * <p>
     * Deletes a escooter in table "Escooter".
     *
     * @param descr - escooter description
     * @return descrDeletedEscooter - deleted escooter description
     */
    public String deleteEscooter(String descr) {
        String deletedEscooterDescr = null;
        try {
            // 1. Start the connection
            openConnection();
            // 2. Query the database
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call deleteEscooter(?) }");
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setString(2, descr);

            callStmt.execute();
            deletedEscooterDescr = callStmt.getString(1);
            // 3. Close the connection
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return deletedEscooterDescr;
    }

    /**
     * Invocation of a "stored function".
     * <p>
     * Returns the registry of all escooters in a park in the DataBase
     * IMPORTANT -> doesn't return escooters with full battery
     *
     * @param latitude latitude of the Park
     * @param longitude longitude of the Park
     * @return escooterList LinkedList with all escooter in a park in DB.
     */
    public List<Escooter> getAllNotFullEscootersFromPark(double latitude, double longitude) {
        List<Escooter> escooterList = new LinkedList<>();

        /* Object "callStmt" to call the function "getEscootersFromPark" saved in the DB.
         *
         * FUNCTION getEscootersFromPark(latitude NUMBER, longitude NUMBER) 
         * RETURN pkgEscooters.ref_cursor
         * PACKAGE pkgEscooters AS TYPE ref_cursor IS REF CURSOR; END pkgEscooters
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getEscootersFromPark(?,?) }");

            // Records SQL type of data to interpret the obtained result.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Specifies entry parameters of function "getEscootersFromPark".
            callStmt.setDouble(2, latitude);
            callStmt.setDouble(3, longitude);

            // Executes the call to the function "getEscootersFromPark".
            callStmt.execute();

            // Saves returned cursor in an object "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {
                String escooterDescr = rSet.getString(1);
                int weight = rSet.getInt(2);
                String type = rSet.getString(3);
                double parkLat = rSet.getDouble(4);
                double parkLong = rSet.getDouble(5);
                float maxBattery = rSet.getFloat(6);
                int actualBattery = rSet.getInt(7);
                float aerodynCoeffic = rSet.getFloat(8);
                float frontalArea = rSet.getFloat(9);
                float motor = rSet.getFloat(10);

                Escooter escooter = new Escooter(escooterDescr, weight, Type.valueOf(type), parkLat,
                        parkLong, maxBattery, actualBattery, aerodynCoeffic, frontalArea, motor, 0);
                escooterList.add(escooter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return escooterList;
    }
}
