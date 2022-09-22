package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lapr.project.model.Bicycle;
import oracle.jdbc.OracleTypes;

/**
 * Class used to do operations with bicycles in the database.
 *
 *
 */
public class BicycleDB extends DataHandler {

    /**
     * Invocation of a "stored function".
     * <p>
     * Returns the registry of the specified bicycle existing in the "Bicycles"
     * table.
     *
     * @param descr identifier of the bicycle.
     * @return the registry of the specified bicycle or null if it doesn't
     * exist.
     */
    public Bicycle getBicycle(String descr) {

        /* Object "callStmt" to call the function "getBicycle" saved in the DB.
         *
         * FUNCTION getBicycle(descr VARCHAR) RETURN pkgBicycles.ref_cursor
         * PACKAGE pkgBicycles AS TYPE ref_cursor IS REF CURSOR; END pkgBicycles
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getBicycle(?) }");

            // Records SQL type of data to interpret the obtained result.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Specifies entry parameter of function "getBicycle".
            callStmt.setString(2, descr);

            // Executes the call to the function "getBicycle".
            callStmt.execute();

            // Saves returned cursor in an object "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String bicycleDescr = rSet.getString(1);
                int weight = rSet.getInt(2);
                double parkLat = rSet.getDouble(3);
                double parkLong = rSet.getDouble(4);
                float aerodynCoeffic = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                int wheelSize = rSet.getInt(7);

                return new Bicycle(bicycleDescr, weight, parkLat, parkLong,
                        aerodynCoeffic, frontalArea, wheelSize);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No Bicycle with description: " + descr);
    }

    /**
     * Adds specified bicycle to the table "Bicycles".
     *
     * @param bicycle bicycle specified
     */
    public void addBicycle(Bicycle bicycle) {
        addBicycle(bicycle.getDescr(),
                bicycle.getWeight(),
                bicycle.getParkLat(),
                bicycle.getParkLong(),
                bicycle.getAerodynCoeffic(),
                bicycle.getFrontalArea(),
                bicycle.getWheelSize());
    }

    /**
     * Invocation of a "stored procedure".
     * <p>
     * Adds specified bicycle to the table "Bicycles".
     *
     * @param descr - unique bicycle reference
     * @param weight - bicycle weight in kg (no decimal places)
     * @param parkLat - latitude of the park where the bicycle is parked
     * (decimal degrees)
     * @param parkLong - longitude of the park where the bicycle is parked
     * (decimal degrees)
     * @param aerodynCoeffic - bicycle aerodynamic coefficient (2 decimal
     * places)
     * @param frontalArea - bicycle frontal area in m^2 (1 decimal place)
     * @param wheelSize - bicycle wheel size in inches
     */
    private void addBicycle(String descr, int weight, double parkLat, double parkLong,
            float aerodynCoeffic, float frontalArea, int wheelSize) {

        try {
            openConnection();
            try (
            /*
             *  Object "callStmt" to call the procedure "addBicycle" saved in the
             * DB.
             *
             *  PROCEDURE addBicycle(descr VARCHAR, weight INTEGER, parkLat NUMBER, 
             * parkLong NUMBER, aerodynCoeffic NUMBER, frontalArea NUMBER, wheelSize INTEGER)
             *  PACKAGE pkgBicycles AS TYPE ref_cursor IS REF CURSOR; END pkgBicycles
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addBicycle(?,?,?,?,?,?,?) }")) {

            callStmt.setString(1, descr);
            callStmt.setInt(2, weight);
            callStmt.setDouble(3, parkLat);
            callStmt.setDouble(4, parkLong);
            callStmt.setFloat(5, aerodynCoeffic);
            callStmt.setFloat(6, frontalArea);
            callStmt.setInt(7, wheelSize);

            callStmt.execute();
            }
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("No Bicycle with description: " + descr);
        } finally {
            closeAll();
        }
    }

    /**
     * Adds multiple bicycles to the table "Bicycles". If not able to insert one, does
     * not insert any. It's all or nothing.
     *
     * @param bicycles - Bicycles to insert
     */
    public void addBicycles(List<Bicycle> bicycles) {

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
            CallableStatement callStmt = getConnection().prepareCall("{ call addBicycle(?,?,?,?,?,?,?) }");

            int numBikes = bicycles.size();
            for (int i = 0; i < numBikes; i++) {
                Bicycle bicycle = bicycles.get(i);

                callStmt.setString(1, bicycle.getDescr());
                callStmt.setInt(2, bicycle.getWeight());
                callStmt.setDouble(3, bicycle.getParkLat());
                callStmt.setDouble(4, bicycle.getParkLong());
                callStmt.setFloat(5, bicycle.getAerodynCoeffic());
                callStmt.setFloat(6, bicycle.getFrontalArea());
                callStmt.setInt(7, bicycle.getWheelSize());

                callStmt.execute();
            }

            getConnection().commit();
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                getConnection().rollback();
                throw new IllegalArgumentException("Not possible to add Bicycles");
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
    public List<Bicycle> getAllBicycles() {

        List<Bicycle> bikeList = new ArrayList<>();

        Statement stm;
        try {
            ResultSet rSet = null;
            String query = "Select * FROM bicycles";
            stm = getConnection().createStatement();
            rSet = stm.executeQuery(query);

            while (rSet.next()) {
                String bicycleDescr = rSet.getString(1);
                int weight = rSet.getInt(2);
                double parkLat = rSet.getDouble(3);
                double parkLong = rSet.getDouble(4);
                float aerodynCoeffic = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                int wheelSize = rSet.getInt(7);
                Bicycle bike = new Bicycle(bicycleDescr, weight, parkLat, parkLong,
                        aerodynCoeffic, frontalArea, wheelSize);
                bikeList.add(bike);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return bikeList;
    }

    /**
     * Update an bicycle
     *
     * @param bike bicycle
     * @return true or false
     */
    public boolean updateBicycle(Bicycle bike) {
        return updateBicycle(bike.getDescr(), bike.getWeight(), bike.getParkLat(),
                bike.getParkLong(), bike.getAerodynCoeffic(),
                bike.getFrontalArea(), bike.getWheelSize());
    }

    /**
     * Update a bicycle
     *
     * @param descr description
     * @param weight weight
     * @param parkLat park latitude
     * @param parkLong park longitude
     * @param aerodynCoeffic Aerodynamic Coefficient
     * @param frontalArea frontal Area
     * @param wheelSize wheel size
     * @return true (if upade with sucess) or false (it dons't update)
     */
    private boolean updateBicycle(String descr, int weight, double parkLat, double parkLong,
            float aerodynCoeffic, float frontalArea, int wheelSize) {

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
            callStmt.setNull(8, OracleTypes.VARCHAR);
            callStmt.setNull(9, OracleTypes.NUMBER);
            callStmt.setNull(10, OracleTypes.NUMBER);
            callStmt.setInt(11, wheelSize);
            callStmt.setNull(12, OracleTypes.INTEGER);

            callStmt.execute();
            result = callStmt.getInt(1);

            closeAll();
            return result != 0;
        } catch (SQLException e) {
            return result != 0;
        }
    }

    /**
     * Invocation of a "stored function".
     * <p>
     * Deletes a bicycle in table "Bicycles".
     *
     * @param descr - bicycle description
     * @return descrDeletedBicycle - deleted bicycle description
     */
    public String deleteBicycle(String descr) {
        String deletedBicycleDescr = null;
        try {
            // 1. Start the connection
            openConnection();
            // 2. Query the database
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call deleteBicycle(?) }");
            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
            callStmt.setString(2, descr);

            callStmt.execute();
            deletedBicycleDescr = callStmt.getString(1);
            // 3. Close the connection
            callStmt.close();
            closeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return deletedBicycleDescr;
    }
}
