/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.util.Pair;
import lapr.project.model.Path;
import lapr.project.model.Point;
import lapr.project.model.PointNetwork;
import lapr.project.utils.Utils;

/**
 * Has methods for functionalities related to Networks of Geographical Points
 * (mainly using graph algorithms).
 */
public class PointNetworkController {

    public PointNetworkController() {
    }

    /**
     * Finds the shortest route between the two points with the passed
     * coordinates, fills the passed list with the found route and returns the
     * total distance (in meters) of that route. Or -1 if not valid.
     *
     * @param origLat latitude of the origin Point
     * @param origLongit longitude of the origin Point
     * @param destLat latitude of the destination Point
     * @param destLongit longitude of the destination Point
     * @param route list to fill with sequential points from shortest route
     * (ordered according to the route found)
     * @param pathsOfRoute list to fill with sequential paths from shortest
     * route (ordered according to the route found)
     * @param vertices list of vertices/points
     * @param edges list of edges/paths
     *
     * @return total distance (in meters) of the shortest route or -1 if
     * vertices or route don't exist
     */
    public int shortestRoute(double origLat, double origLongit, double destLat,
            double destLongit, List<Point> route, List<Path> pathsOfRoute,
            List<Point> vertices, List<Path> edges) {

        if (route == null || pathsOfRoute == null || vertices == null || vertices.isEmpty() || edges == null) {
            return -1;
        }

        PointNetwork pointNetwork = createGraph(vertices, edges, true);
        return pointNetwork.shortestRoute(origLat, origLongit, destLat, destLongit, route, pathsOfRoute);
    }

    /**
     * Creates and returns an object PointNetwork, which contains the graph of
     * points and paths between them.
     *
     * @param vertices list of vertices/points to insert
     * @param edges list of edges/paths to insert
     * @param distanceAsWeight true if edge weight is distance; false if is
     * energy
     *
     * @return object PointNetwork or null if invalid
     */
    private PointNetwork createGraph(List<Point> vertices, List<Path> edges, boolean distanceAsWeight) {
        PointNetwork pointNetwork = new PointNetwork(distanceAsWeight);
        pointNetwork.addVertices(vertices);
        pointNetwork.addEdges(edges);
        return pointNetwork;
    }

    /**
     * Creates and returns an object PointNetwork, which contains the graph of
     * points and paths between them.
     *
     * @param vertices list of vertices/points to insert
     * @param edges list of edges/paths to insert
     * @param distanceAsWeight true if edge weight is distance; false if is
     * energy
     *
     * @return object PointNetwork or null if invalid
     */
    private PointNetwork createGraph(List<Point> vertices, List<Path> edges,
            boolean distanceAsWeight, double vel) {
        PointNetwork pointNetwork = new PointNetwork(distanceAsWeight);
        pointNetwork.addVertices(vertices);
        pointNetwork.addEdges(edges, vel);
        return pointNetwork;
    }

    /**
     * Finds the shortest route(s) between the two points with the passed
     * coordinates, fills the passed list with the found route(s) and returns
     * the weight of that/those route(s). Or -1 if not valid. If there's more
     * than one shortest route (i.e. two or more routes with shortest distance),
     * this method identifies all of them. List of routes is ordered by number
     * of points of each route, ascending.
     *
     * @param origLat latitude of the origin Point
     * @param origLongit longitude of the origin Point
     * @param destLat latitude of the destination Point
     * @param destLongit longitude of the destination Point
     * @param routesAndItsPaths list that contains, for every route identified,
     * 1. a list with the sequential points from that route and 2. a list with
     * the sequential pths from that route
     * @param vertices list of vertices/points
     * @param edges list of edges/paths
     *
     * @return total distance (in meters) of the shortest route(s) or -1 if
     * vertices or route don't exist
     */
    public int shortestRoutes(double origLat, double origLongit, double destLat, double destLongit,
            List<Pair<List<Point>, List<Path>>> routesAndItsPaths, List<Point> vertices, List<Path> edges) {

        if (routesAndItsPaths == null || vertices == null || vertices.isEmpty() || edges == null) {
            return -1;
        }

        PointNetwork pointNetwork = createGraph(vertices, edges, true);
        int distance = pointNetwork.shortestRoutes(origLat, origLongit, destLat, destLongit, routesAndItsPaths);

        //order list
        Collections.sort(routesAndItsPaths, ascendingNumPoints);

        return distance;
    }

    /**
     * Finds the shortest route(s) between the two points with the passed
     * coordinates, fills the passed list with the found route(s) and returns
     * the weight of that/those route(s). Or -1 if not valid. If there's more
     * than one shortest route (i.e. two or more routes with shortest distance),
     * this method identifies all of them. List of routes is ordered by number
     * of points of each route, ascending.
     *
     * @param origLat latitude of the origin Point
     * @param origLongit longitude of the origin Point
     * @param destLat latitude of the destination Point
     * @param destLongit longitude of the destination Point
     * @param routesAndItsPaths list that contains, for every route identified,
     * 1. a list with the sequential points from that route and 2. a list with
     * the sequential pths from that route
     * @param vertices list of vertices/points
     * @param edges list of edges/paths
     * @param vel velocity
     *
     * @return total distance (in meters) of the shortest route(s) or -1 if
     * vertices or route don't exist
     */
    public int shortestRoutes(double origLat, double origLongit, double destLat, double destLongit,
            List<Pair<List<Point>, List<Path>>> routesAndItsPaths, List<Point> vertices, List<Path> edges,
            double vel) {

        if (routesAndItsPaths == null || vertices == null || vertices.isEmpty()
                || edges == null || vel == 0) {
            return -1;
        }

        PointNetwork pointNetwork = createGraph(vertices, edges, false, vel);
        int energy = pointNetwork.shortestRoutes(origLat, origLongit, destLat, destLongit, routesAndItsPaths);

        //order list
        Collections.sort(routesAndItsPaths, ascendingNumPoints);

        return energy;
    }

    /**
     * Allows ordering of a collection of Pair<List<Point>, List<Path>> (each
     * pair represents a route) by ascending number of points in the route.
     */
    private final Comparator<Pair<List<Point>, List<Path>>> ascendingNumPoints = new Comparator<Pair<List<Point>, List<Path>>>() {

        @Override
        public int compare(Pair<List<Point>, List<Path>> p1, Pair<List<Point>, List<Path>> p2) {
            int size1 = p1.getKey().size();
            int size2 = p2.getKey().size();

            if (size1 < size2) {
                return -1;
            } else if (size1 > size2) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    /**
     * Given a list of paths, return the total distance in metters
     *
     * @param paths paths
     * @return total distance
     */
    public int getTotalDistance(List<Path> paths) {
        double distance = 0;
        
        for (Path path : paths) {
            distance += path.getDistance();
        }
        return (int) distance;
    }
}
