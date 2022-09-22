/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.List;
import java.util.Set;
import javafx.util.Pair;
import lapr.project.utils.Utils;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestSimplePaths;
import org.jgrapht.graph.DirectedWeightedMultigraph;

/**
 * Describes a network of geographical points (parks, POIs) connected by paths.
 * Represents this network as an undirected weighted graph. Weight can be
 * distance or energy of path.
 */
public class PointNetwork {

    /**
     * Undirected weighted graph.
     */
    private final Graph<Point, Path> graph;
    /**
     * Decides variable for edge weight (distance [true] vs energy [false]).
     */
    private final boolean distanceAsWeight;

    /**
     * Constructor with boolean.
     *
     * @param distanceAsWeight new value of distanceAsWeight (true for distance
     * as weight, false for energy)
     */
    public PointNetwork(boolean distanceAsWeight) {

        this.graph = new DirectedWeightedMultigraph<>(null, null);
        this.distanceAsWeight = distanceAsWeight;
    }

    /**
     * Adds a Point as a vertex to the graph.
     *
     * @param point vertex
     */
    public void addVertex(Point point) {
        Point temp = this.getVertex(point.getLatitude(), point.getLongitude());
        if (temp != null) {
            return;
        }
        this.graph.addVertex(point);
    }

    /**
     * Adds multiple Points as vertices to the graph.
     *
     * @param points vertices
     */
    public void addVertices(List<Point> points) {
        if (points == null || points.isEmpty()) {
            return;
        }

        for (Point point : points) {
            this.addVertex(point);
        }
    }

    /**
     * Adds a Path as an edge to the graph.
     *
     * @param path edge
     */
    public void addEdge(Path path) {
        if (distanceAsWeight) {
            Point vertex1 = this.getVertex(path.getLatA(), path.getLongA());
            Point vertex2 = this.getVertex(path.getLatB(), path.getLongB());

            if (vertex1 == null || vertex2 == null) {
                return;
            }
            this.graph.addEdge(vertex1, vertex2, path);
            this.graph.setEdgeWeight(path, path.getDistance());
        }
    }

    /**
     * Adds a Path as an edge to the graph.
     *
     * @param path edge
     * @param avgSpeed average speed
     */
    public void addEdge(Path path, double avgSpeed) {
        if (!distanceAsWeight) {
            Point vertex1 = this.getVertex(path.getLatA(), path.getLongA());
            Point vertex2 = this.getVertex(path.getLatB(), path.getLongB());

            if (vertex1 == null || vertex2 == null) {
                return;
            }

            double distance = Utils.distance(vertex1.getLatitude(), vertex1.getLongitude(),
                    vertex2.getLatitude(), vertex2.getLongitude());
            double slope = Utils.calcSlope(vertex1.getElevation(), vertex2.getElevation(), distance);

            this.graph.addEdge(vertex1, vertex2, path);
            this.graph.setEdgeWeight(path, path.calcEnergy(avgSpeed, slope));
        }
    }

    /**
     * Adds multiple Paths as edges to the graph.
     *
     * @param paths edges
     */
    public void addEdges(List<Path> paths) {
        if (paths == null || paths.isEmpty()) {
            return;
        }

        for (Path path : paths) {
            this.addEdge(path);
        }
    }

    /**
     * Adds multiple Paths as edges to the graph.
     *
     * @param paths edges
     * @param avgSpeed Average speed
     */
    public void addEdges(List<Path> paths, double avgSpeed) {
        if (paths == null || paths.isEmpty() || avgSpeed <= 0) {
            return;
        }

        for (int i = 0; i < paths.size(); i++) {
            this.addEdge(paths.get(i), avgSpeed);
        }
    }

    /**
     * Returns a Set with all the vertices/points of the graph.
     *
     * @return Set with all the vertices/points
     */
    public Set<Point> getAllVertices() {
        return this.graph.vertexSet();
    }

    /**
     * Returns a Set with all the edges/paths of the graph.
     *
     * @return Set with all the edges/paths
     */
    public Set<Path> getAllEdges() {
        return this.graph.edgeSet();
    }

    /**
     * Finds the shortest route between the two points with the passed
     * coordinates, fills the passed list with the found route and returns the
     * weight of that route. Or -1 if not valid.
     *
     * @param origLat latitude of the origin Point
     * @param origLongit longitude of the origin Point
     * @param destLat latitude of the destination Point
     * @param destLongit longitude of the destination Point
     * @param route list to fill with sequential points from shortest route
     * (ordered according to the route found)
     * @param pathsOfRoute list to fill with sequential paths from shortest
     * route (ordered according to the route found)
     *
     * @return weight of the shortest route or -1 if vertices or route don't
     * exist
     */
    public int shortestRoute(double origLat, double origLongit, double destLat,
            double destLongit, List<Point> route, List<Path> pathsOfRoute) {

        Point origVert = this.getVertex(origLat, origLongit);
        Point destVert = this.getVertex(destLat, destLongit);

        if (origVert == null || destVert == null) {
            return -1;
        }

        DijkstraShortestPath<Point, Path> dijkstra = new DijkstraShortestPath<>(this.graph);
        GraphPath<Point, Path> graphRoute = dijkstra.getPath(origVert, destVert);
        if (graphRoute == null) {
            return -1;
        }

        route.addAll(graphRoute.getVertexList());
        pathsOfRoute.addAll(graphRoute.getEdgeList());
        return (int) graphRoute.getWeight();
    }

    /**
     * Finds the shortest route(s) between the two points with the passed
     * coordinates, fills the passed list with the found route(s) and returns
     * the weight of that/those route(s). Or -1 if not valid. If there's more
     * than one shortest route (i.e. two or more routes with shortest distance),
     * this method identifies all of them.
     *
     * @param origLat latitude of the origin Point
     * @param origLongit longitude of the origin Point
     * @param destLat latitude of the destination Point
     * @param destLongit longitude of the destination Point
     * @param routesAndItsPaths list that contains, for every route identified,
     * 1. a list with the sequential points from that route and 2. a list with
     * the sequential pths from that route
     *
     * @return weight of the shortest route(s) or -1 if vertices or route don't
     * exist
     */
    public int shortestRoutes(double origLat, double origLongit, double destLat,
            double destLongit, List<Pair<List<Point>, List<Path>>> routesAndItsPaths) {

        Point origVert = this.getVertex(origLat, origLongit);
        Point destVert = this.getVertex(destLat, destLongit);

        if (origVert == null || destVert == null) {
            return -1;
        }

        try {
            KShortestSimplePaths<Point, Path> kShortestPaths = new KShortestSimplePaths<>(this.graph);
            List<GraphPath<Point, Path>> graphRoutes = kShortestPaths.getPaths(origVert, destVert, 5);

            int weightShortestRoute = (int) graphRoutes.get(0).getWeight();

            for (GraphPath<Point, Path> graphRoute : graphRoutes) {
                if (((int) graphRoute.getWeight()) == weightShortestRoute) {
                    List<Point> points = graphRoute.getVertexList();
                    List<Path> paths = graphRoute.getEdgeList();
                    routesAndItsPaths.add(new Pair<>(points, paths));
                } else {
                    break;
                }
            }
            return weightShortestRoute;
        } catch (IllegalArgumentException | IndexOutOfBoundsException ex) {
            return -1;
        }
    }

    /**
     * Finds and returns the vertex/Point that has same latitude and longitude
     * as the ones passed as parameter. Or returns null if no vertex like that
     * exists in the graph.
     *
     * @param latitude latitude from the Point to find
     * @param longitude longitude from the Point to find
     *
     * @return Point found or null
     */
    private Point getVertex(double latitude, double longitude) {
        Set<Point> points = this.graph.vertexSet();
        for (Point point : points) {
            if (point.getLatitude() == latitude && point.getLongitude() == longitude) {
                return point;
            }
        }
        return null;
    }

}
