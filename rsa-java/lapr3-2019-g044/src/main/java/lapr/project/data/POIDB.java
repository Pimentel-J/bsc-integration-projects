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
import java.util.LinkedList;
import java.util.List;
import lapr.project.model.POI;
import oracle.jdbc.OracleTypes;

/**
 * Class used to do operations with points of interest (POI) in the database.
 */
public class POIDB extends DataHandler {
    
    /**
     * Invocation of a "stored function".
     * <p>
     * Returns the registry of the specified POI existing in the "POIs" table.
     *
     * @param latitude latitude of the POI.
     * @param longitude longitude of the POI.
     * @return the registry of the specified POI or null if it doesn't exist.
     */
    public POI getPoi(double latitude, double longitude) {

        /* Object "callStmt" to call the function "getPoi" saved in the DB.
         *
         * FUNCTION getPoi(latitude NUMBER, longitude NUMBER) RETURN pkgPOIs.ref_cursor
         * PACKAGE pkgPOIs AS TYPE ref_cursor IS REF CURSOR; END pkgPOIs;
         */
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? = call getPOI(?,?) }");

            // Records SQL type of data to interpret the obtained result.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Specifies entry parameters of function "getPOI".
            callStmt.setDouble(2, latitude);
            callStmt.setDouble(3, longitude);

            // Executes the call to the function "getPark".
            callStmt.execute();

            // Saves returned cursor in an object "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                double lat = rSet.getDouble(2);
                double longit = rSet.getDouble(3);
                int elev = rSet.getInt(4);
                String descr = rSet.getString(5);

                return new POI(lat, longit, elev, descr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        throw new IllegalArgumentException("No POI with latitude=" + latitude + " and longitude:" + longitude);
    }

    /**
     * Adds specified POI to the table "POIs".
     *
     * @param poi POI specified
     */
    public void addPoi(POI poi) {
        addPoi(poi.getLatitude(),
                poi.getLongitude(),
                poi.getElevation(),
                poi.getDescription());
    }

    /**
     * Invocation of a "stored procedure".
     * <p>
     * Adds specified POI to the table "POIs".
     *
     * @param latitude latitude of the POI.
     * @param longitude longitude of the POI.
     * @param elevation elevation/altitude (in metres) of the POI.
     * @param description descriptive name for the POI.
     */
    private void addPoi(double latitude, double longitude, int elevation, String description) {

        try {
            openConnection();
            /*
             *  Object "callStmt" to call the procedure "addPoi" saved in the 
             * DB.
             *
             *  PROCEDURE addPoi(latitude NUMBER, longitude NUMBER, elevation INTEGER, description VARCHAR)
             *  PACKAGE pkgPois AS TYPE ref_cursor IS REF CURSOR; END pkgPois;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addPOI(?,?,?,?) }");

            callStmt.setDouble(1, latitude);
            callStmt.setDouble(2, longitude);
            callStmt.setInt(3, elevation);
            callStmt.setString(4, description);

            callStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Not possible to add POI latitude=" + latitude + " and longitude:" + longitude);
        } finally {
            closeAll();
        }
    }
    
    /**
     * Adds multiple POIs to the table "POIs". If not able to insert one, does 
     * not insert any. It's all or nothing.
     *
     * @param pois POIs to insert
     */
    public void addPois(List<POI> pois) {
        
        try {
            openConnection();
            getConnection().setAutoCommit(false);
            
            /*
             *  Object "callStmt" to call the procedure "addPoi" saved in the 
             * DB.
             *
             *  PROCEDURE addPoi(latitude NUMBER, longitude NUMBER, elevation INTEGER, description VARCHAR)
             *  PACKAGE pkgPois AS TYPE ref_cursor IS REF CURSOR; END pkgPois;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call addPOI(?,?,?,?) }");
            
            int numPois = pois.size();
            for (int i = 0; i < numPois; i++) {
                POI poi = pois.get(i);
                
                callStmt.setDouble(1, poi.getLatitude());
                callStmt.setDouble(2, poi.getLongitude());
                callStmt.setInt(3, poi.getElevation());
                callStmt.setString(4, poi.getDescription());

                callStmt.execute();
            }

            getConnection().commit();
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                getConnection().rollback();
                throw new IllegalArgumentException("Not possible to add POIs");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            closeAll();
        }
    }
    
    /**
     * Runs a query that returns all the POIs in the database.
     *
     * @return a list with all the POIs from the database.
     */
    public List<POI> getAllPois() {
        
        List<POI> pois = new LinkedList<>();

        Statement stm;
        try {
            ResultSet rSet = null;
            String query = "SELECT * FROM pois";
            stm = getConnection().createStatement();
            rSet = stm.executeQuery(query);

            while (rSet.next()) {
                double lat = rSet.getDouble(2);
                double longit = rSet.getDouble(3);
                int elev = rSet.getInt(4);
                String descr = rSet.getString(5);

                POI poi = new POI(lat, longit, elev, descr);
                pois.add(poi);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            pois.clear();
        } finally {
            closeAll();
        }
        return pois;
    }
    
}
