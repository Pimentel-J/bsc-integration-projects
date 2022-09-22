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
import lapr.project.model.Path;
import oracle.jdbc.OracleTypes;

/**
 *
 *
 */
public class PathDB extends DataHandler {

    /**
     * Add a path
     *
     * @param path path
     */
    public void addPath(Path path) {
        addPath(path.getLatA(), path.getLongA(), path.getLatB(), path.getLongB(),
                path.getK_coeff(), path.getWind_dir(), path.getWind_speed());
    }

    /**
     * Add path
     *
     * @param latA latitude A
     * @param longA longitude A
     * @param latB latitude B
     * @param longB longitude B
     * @param k_coeff Kinetic coefficient
     * @param wind_dir wind direction
     * @param wind_speed wind speed
     */
    private void addPath(double latA, double longA, double latB, double longB,
            double k_coeff, int wind_dir, float wind_speed) {
        int result;
        try {
            openConnection();
            /*
             *  Object "callStmt" to call the procedure "addPath" saved in the 
             * DB.
             *
             *  PROCEDURE addPoi(latitudeA NUMBER, longitudeA NUMBER, latitudeB NUMBER, 
            longitudeB NUMBER, k_coeff NUMBER, wind_dir INTEGER, wind_speed NUMBER)
             *  PACKAGE pkgPath AS TYPE ref_cursor IS REF CURSOR; END pkgPath;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call ADDPATH(?,?,?,?,?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmtSet(callStmt, latA, longA, latB, longB);
            callStmt.setDouble(6, k_coeff);
            callStmt.setInt(7, wind_dir);
            callStmt.setFloat(8, wind_speed);

            callStmt.execute();

            result = callStmt.getInt(1);

        } catch (SQLException e) {
            throw new IllegalArgumentException("Not possible to add Path latitudeA=" + latA
                    + " and longitudeA:" + longA + "Not possible to add Path latitudeB=" + latB
                    + " and longitudeB:" + longB);
        } finally {
            closeAll();
        }
    }

    /**
     * Return the path!
     *
     * @param latA latitudeA
     * @param longA longitudeA
     * @param latB latitude B
     * @param longB longitude B
     * @return path
     */
    public Path getPath(double latA, double longA, double latB, double longB) {

        try {
            openConnection();
            /*
             *  Object "callStmt" to call the procedure "getPath" saved in the 
             * DB.
             *
             *  PROCEDURE getPath(latitudeA NUMBER, longitudeA NUMBER, latitudeB NUMBER, longitudeB NUMBER,)
             *  PACKAGE pkgPath AS TYPE ref_cursor IS REF CURSOR; END pkgPath;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ ? = call GETPATH(?,?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmtSet(callStmt, latA, longA, latB, longB);

            callStmt.execute();
            ResultSet rSet = null;

            rSet = callStmt.getResultSet();

            double k_coeff = rSet.getDouble(5);
            int wind_dir = rSet.getInt(6);
            float wind_speed = rSet.getInt(7);

            return new Path(latA, longA, latB, longB, k_coeff, wind_dir, wind_speed);

        } catch (SQLException e) {
            throw new IllegalArgumentException("Not possible to get Path latitudeA=" + latA
                    + " and longitudeA:" + longA + "Not possible to get Path latitudeB=" + latB
                    + " and longitudeB:" + longB);
        } finally {
            closeAll();
        }
    }

    /**
     * Add a list of paths
     *
     * @param paths list of paths
     */
    public void addPaths(List<Path> paths) {
        try {
            openConnection();
            getConnection().setAutoCommit(false);
            
            /*
             *  Object "callStmt" to call the procedure "addPath2" saved in the 
             * DB.
             *
             *  PROCEDURE addPoi(latitudeA NUMBER, longitudeA NUMBER, latitudeB NUMBER, 
            longitudeB NUMBER, k_coeff NUMBER, wind_dir INTEGER, wind_speed NUMBER)
             *  PACKAGE pkgPath AS TYPE ref_cursor IS REF CURSOR; END pkgPath;
             */
            CallableStatement callStmt = getConnection().prepareCall("{ call ADDPATH2(?,?,?,?,?,?,?) }");

            for (int i = 0; i < paths.size(); i++) {
                Path path = paths.get(i);

                callStmt.setDouble(1, path.getLatA());
                callStmt.setDouble(2, path.getLongA());
                callStmt.setDouble(3, path.getLatB());
                callStmt.setDouble(4, path.getLongB());
                callStmt.setDouble(5, path.getK_coeff());
                callStmt.setInt(6, path.getWind_dir());
                callStmt.setFloat(7, path.getWind_dir());
                callStmt.execute();
            }

            getConnection().commit();

        } catch (SQLException e) {
            try {
                e.printStackTrace();
                getConnection().rollback();
                throw new IllegalArgumentException("Not possible to add Path!");
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            closeAll();
        }
    }

    /**
     * Set callabelStatement
     *
     * @param callStmt callabelStatement
     * @param latA latitudeA
     * @param longA longitudeA
     * @param latB latitudeB
     * @param longB longitudeB
     */
    private void callStmtSet(CallableStatement callStmt, double latA, double longA,
            double latB, double longB) {
        try {
            callStmt.setDouble(2, latA);
            callStmt.setDouble(3, longA);
            callStmt.setDouble(4, latB);
            callStmt.setDouble(5, longB);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException("Not possible to set Path latitudeA=" + latA
                    + " and longitudeA:" + longA + "Not possible to set Path latitudeB=" + latB
                    + " and longitudeB:" + longB);
        }
    }

    /**
     * Runs a query that returns all the paths in the DataBase
     *
     * @return a list with all Paths in DB exist.
     */
    public List<Path> getAllPaths() {

        List<Path> pathList = new ArrayList<>();

        Statement stm;
        try {
            ResultSet rSet = null;
            String query = "Select * FROM paths";
            stm = getConnection().createStatement();
            rSet = stm.executeQuery(query);

            while (rSet.next()) {
                double latA = rSet.getDouble(1);
                double longA = rSet.getDouble(2);
                double latB = rSet.getDouble(3);
                double longB = rSet.getDouble(4);
                double kCoeff = rSet.getDouble(5);
                int windDir = rSet.getInt(6);
                float windSpeed = rSet.getFloat(7);
                Path path = new Path(latA, longA, latB, longB, kCoeff, windDir, windSpeed);
                pathList.add(path);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return pathList;
    }
}
