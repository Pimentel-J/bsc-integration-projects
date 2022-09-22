/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import lapr.project.data.PathDB;
import lapr.project.model.Path;

/**
 *
 *
 */
public class PathController {

    /**
     * Instance of PathDB
     */
    private PathDB pathDB;

    public PathController() {
        pathDB = new PathDB();
    }

    /**
     * Set instance of PathDB
     *
     * @param pathDB
     */
    public void setPathDB(PathDB pathDB) {
        this.pathDB = pathDB;
    }

    /**
     * Add a Path
     *
     * @param latA Latitude A
     * @param longA Longitude A
     * @param latB Latitude B
     * @param longB Longitude B
     * @param k_coeffic Kinetic coefficient
     * @param wind_dir wind direction
     * @param wind_speed wind speedd
     * @return sucess or Failure
     */
    public boolean addPath(double latA, double longA, double latB, double longB,
            double k_coeffic, int wind_dir, float wind_speed) {
        try {
            Path path = new Path(latA, longA, latB, longB, k_coeffic, wind_dir, wind_speed);
            return savePath(path);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Add a path into Data Base
     *
     * @param path Path
     * @return sucess or Failure
     */
    private boolean savePath(Path path) {
        try {
            pathDB.getPath(path.getLatA(), path.getLongA(), path.getLatB(), path.getLongB());
            return false;
        } catch (IllegalArgumentException ex) {
            try {
                //If the record does not exist, save it
                pathDB.addPath(path);
                return true;
            } catch (IllegalArgumentException ex2) {
                return false;
            }
        }
    }

    /**
     * Add multiple paths
     *
     * @param latA List of latitudesA
     * @param longA List of longitudesA
     * @param latB List of latitudesB
     * @param longB List of longitudesB
     * @param k_coeffic List of kinetic coefficients
     * @param wind_dir List of wind directions
     * @param wind_speed List of wind speeds
     * @return sucess or Failure
     */
    public boolean addPaths(List<Double> latA, List<Double> longA, List<Double> latB,
            List<Double> longB, List<Double> k_coeffic, List<Integer> wind_dir,
            List<Float> wind_speed) {

        List<Path> paths = new LinkedList<>();

        try {
            for (int i = 0; i < latA.size(); i++) {
                paths.add(new Path(latA.get(i), longA.get(i), latB.get(i), longB.get(i),
                        k_coeffic.get(i), wind_dir.get(i), wind_speed.get(i)));
            }
            return savePaths(paths);
        } catch (IllegalArgumentException ex) { // at least one record with invalid data
            return false;
        }
    }

    /**
     * Save all paths in Date Base
     *
     * @param paths List with all paths
     * @return Sucess or Failure
     */
    private boolean savePaths(List<Path> paths) {

        for (int i = 0; i < paths.size(); i++) {
            Path path = paths.get(i);
            try {
                pathDB.getPath(path.getLatA(), path.getLongA(), path.getLatB(),
                        path.getLongB());
                return false;
            } catch (IllegalArgumentException ex) {
            }
        }

        // if none of the records exist, saves all of them
        try {
            pathDB.addPaths(paths);
            return true;
        } catch (IllegalArgumentException ex2) {
            return false;
        }
    }

    /**
     * Returns a ArrayList with all parks. Returns an empty set if there's no
     * parks.
     *
     * @return paths - ArrayList with all paths (unsorted order)
     */
    public List<Path> getAllPaths() {
        List<Path> paths = new ArrayList<>();
        paths.addAll(pathDB.getAllPaths());
        return paths;
    }

    /**
     * Return the path between two points
     * @param latA latitudeA
     * @param longA longitude A
     * @param latB latitude B
     * @param longB longitude B
     * @return path
     */
    private Path getPath(double latA, double longA, double latB, double longB) {
        try {
            return pathDB.getPath(latA, longA, latB, longB);
        } catch (IllegalArgumentException ex) {

        }
        return null;
    }
}
