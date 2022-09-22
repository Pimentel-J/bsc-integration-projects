/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import lapr.project.model.POI;
import lapr.project.model.Path;
import lapr.project.model.Point;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for "PointNetworkController".
 */
public class PointNetworkControllerTest {

    private PointNetworkController controller;

    @BeforeEach
    void setUp() {
        controller = new PointNetworkController();
    }

    /**
     * Test of shortestRoute method, of class PointNetworkController.
     */
    @Test
    public void testShortestRoute() {
        System.out.println("testShortestRoute");
        List<Point> route = new LinkedList<>();
        List<Path> pathsOfRoute = new LinkedList<>();
        List<Point> vertices = new LinkedList<>();
        List<Path> edges = new LinkedList<>();

        vertices.add(new POI(6, 6, 0, "A"));
        vertices.add(new POI(2, 2, 0, "B"));
        vertices.add(new POI(3, 3, 0, "C"));
        vertices.add(new POI(4, 4, 0, "D"));
        vertices.add(new POI(4, 5, 0, "E"));
        edges.add(new Path(6, 6, 2, 2, 1, 1, 1)); // A-B (628,2km)
        edges.add(new Path(2, 2, 3, 3, 1, 1, 1)); // B-C (157,2km)
        edges.add(new Path(3, 3, 4, 4, 1, 1, 1)); // C-D (157,2km)
        edges.add(new Path(3, 3, 4, 5, 1, 1, 1)); // C-E (248,3km)
        edges.add(new Path(4, 4, 4, 5, 1, 1, 1)); // D-E (110,9km)

        int expResult = -1;
        int result = controller.shortestRoute(6, 6, 4, 5, null, pathsOfRoute, vertices, edges);
        assertEquals(expResult, result);

        result = controller.shortestRoute(6, 6, 4, 5, route, null, vertices, edges);
        assertEquals(expResult, result);

        result = controller.shortestRoute(6, 6, 4, 5, route, pathsOfRoute, null, edges);
        assertEquals(expResult, result);

        result = controller.shortestRoute(6, 6, 4, 5, route, pathsOfRoute, vertices, null);
        assertEquals(expResult, result);

        result = controller.shortestRoute(6, 6, 4, 5, route, pathsOfRoute, new LinkedList<>(), edges);
        assertEquals(expResult, result);

        expResult = 1033626; //meters
        result = controller.shortestRoute(6, 6, 4, 5, route, pathsOfRoute, vertices, edges);
        assertEquals(expResult, result);
    }

    /**
     * Test of shortestRoutes method, of class PointNetworkController.
     */
    @Test
    public void testShortestRoutes() {
        System.out.println("testShortestRoutes");
        List<Pair<List<Point>, List<Path>>> routesAndItsPaths = new LinkedList<>();
        List<Point> vertices = new LinkedList<>();
        List<Path> edges = new LinkedList<>();

        vertices.add(new POI(0, 0, 0, "A"));
        vertices.add(new POI(0, 25, 0, "B"));
        vertices.add(new POI(0, 50, 0, "C"));
        vertices.add(new POI(0, -50, 0, "D"));
        vertices.add(new POI(50, 0, 0, "E"));
        edges.add(new Path(0, 0, 0, 25, 1, 1, 1)); // A-B (2780km)
        edges.add(new Path(0, 0, 0, -50, 1, 1, 1)); // A-D (5560km)
        edges.add(new Path(0, 25, 0, 50, 1, 1, 1)); // B-C (2780km)
        edges.add(new Path(0, 50, 50, 0, 1, 1, 1)); // C-E (7294km)
        edges.add(new Path(0, -50, 50, 0, 1, 1, 1)); // D-E (7294km)

        int expResult = -1;
        int result = controller.shortestRoutes(0, 0, 50, 0, null, vertices, edges);
        assertEquals(expResult, result);

        result = controller.shortestRoutes(0, 0, 50, 0, routesAndItsPaths, null, edges);
        assertEquals(expResult, result);

        result = controller.shortestRoutes(0, 0, 50, 0, routesAndItsPaths, vertices, null);
        assertEquals(expResult, result);

        result = controller.shortestRoutes(0, 0, 50, 0, routesAndItsPaths, new LinkedList<>(), edges);
        assertEquals(expResult, result);

        expResult = 12853633; //meters
        result = controller.shortestRoutes(0, 0, 50, 0, routesAndItsPaths, vertices, edges);
        assertEquals(expResult, result);
        assertEquals(routesAndItsPaths.size(), 2);
        Iterator<Pair<List<Point>, List<Path>>> it = routesAndItsPaths.iterator();
        assertEquals(it.next().getKey().size(), 3); // A-D-E
        assertEquals(it.next().getKey().size(), 4); // A-B-C-E
    }

    /**
     * Test of shortestRoutes method, of class PointNetworkController.
     */
    @Test
    public void testCreateGraph() {
        System.out.println("CreateGraph");
        List<Pair<List<Point>, List<Path>>> routesAndItsPaths = new LinkedList<>();
        List<Point> vertices = new LinkedList<>();
        List<Path> edges = new LinkedList<>();

        vertices.add(new POI(0, 0, 0, "A"));
        vertices.add(new POI(1, 1, 5, "B"));
        vertices.add(new POI(1, 2, 5, "C"));
        vertices.add(new POI(3, 0, 0, "D"));
        vertices.add(new POI(1, 0, 0, "E"));
        edges.add(new Path(0, 0, 1, 1, 0, 180, 3)); // A-B (10,453)
        edges.add(new Path(1, 0, 1, 1, 1, 180, 0)); // E-B (2,88)
        edges.add(new Path(0, 0, 1, 0, 1, 180, 3)); // A-E (7,579)
        edges.add(new Path(1, 1, 1, 2, 1, 180, 1)); // B-C (0,842187)
        edges.add(new Path(1, 2, 3, 0, 1, 180, 1)); // C-D (0,842187)
        edges.add(new Path(1, 0, 3, 0, 1, 180, 4)); // E-D (3,36875)

        int expResult = -1;
        int result = controller.shortestRoutes(0, 0, 50, 0, null, vertices, edges, 5.5);
        assertEquals(expResult, result);

        result = controller.shortestRoutes(0, 0, 50, 0, routesAndItsPaths, null, edges, 5.5);
        assertEquals(expResult, result);

        result = controller.shortestRoutes(0, 0, 50, 0, routesAndItsPaths, vertices, null, 5.5);
        assertEquals(expResult, result);

        result = controller.shortestRoutes(0, 0, 50, 0, routesAndItsPaths, new LinkedList<>(), edges, 5.5);
        assertEquals(expResult, result);

        expResult = 17; //W
        result = controller.shortestRoutes(0, 0, 3, 0, routesAndItsPaths, vertices, edges, 5.5);
        assertEquals(expResult, result);
        Iterator<Pair<List<Point>, List<Path>>> it = routesAndItsPaths.iterator();
        assertEquals(it.next().getKey().size(), 4); // A-B-C-D
        
        assertEquals(1027593,controller.getTotalDistance(edges));
    }
}
