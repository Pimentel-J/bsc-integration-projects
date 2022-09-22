/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for "PointNetwork".
 */
public class PointNetworkTest {
    
    private PointNetwork pointNetworkByDistance;
    private PointNetwork pointNetworkByEnergy;

    @BeforeEach
    void setUp() {
        pointNetworkByDistance = new PointNetwork(true);
        pointNetworkByEnergy = new PointNetwork(false);
    }
    
    /**
     * Test of addVertex method, of class PointNetwork.
     */
    @Test
    public void testAddVertex() {
        System.out.println("testAddVertex");
        int expResult = 1;
        Park instance = new Park("1", 1, 1, 1, "A", 1, 1, 1, 1);
        pointNetworkByDistance.addVertex(instance);
        int result = pointNetworkByDistance.getAllVertices().size();
        assertEquals(expResult, result);
        
        Park instance2 = new Park("1", 1, 1, 1, "A", 1, 1, 1, 1);
        pointNetworkByDistance.addVertex(instance);
        result = pointNetworkByDistance.getAllVertices().size();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of addVertices method, of class PointNetwork.
     */
    @Test
    public void testAddVertices() {
        System.out.println("testAddVertices");
        int expResult = 0;
        List<Point> points = null;
        pointNetworkByDistance.addVertices(points);
        int result = pointNetworkByDistance.getAllVertices().size();
        assertEquals(expResult, result);
        
        points = new LinkedList<>();
        pointNetworkByDistance.addVertices(points);
        result = pointNetworkByDistance.getAllVertices().size();
        assertEquals(expResult, result);
        
        expResult = 2;
        points.add(new Park("1", 1, 1, 1, "A", 1, 1, 1, 1));
        points.add(new POI(1,0,0,"A"));
        pointNetworkByDistance.addVertices(points);
        result = pointNetworkByDistance.getAllVertices().size();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of addEdge method, of class PointNetwork.
     */
    @Test
    public void testAddEdge() {
        System.out.println("testAddEdge");
        int expResult = 0;
        pointNetworkByDistance.addVertex(new POI(1,0,0,"A"));
        pointNetworkByDistance.addVertex(new POI(2,2,0,"B"));
        Path path = new Path(1,1,2,2,1,1,1);
        pointNetworkByDistance.addEdge(path);
        int result = pointNetworkByDistance.getAllEdges().size();
        assertEquals(expResult, result);
        
        path = new Path(1,0,2,1,1,1,1);
        pointNetworkByDistance.addEdge(path);
        result = pointNetworkByDistance.getAllEdges().size();
        assertEquals(expResult, result);
        
        expResult = 1;
        path = new Path(1,0,2,2,1,1,1);
        pointNetworkByDistance.addEdge(path);
        result = pointNetworkByDistance.getAllEdges().size();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of addEdges method, of class PointNetwork.
     */
    @Test
    public void testAddEdges() {
        System.out.println("testAddEdges");
        int expResult = 0;
        List<Path> paths = null;
        pointNetworkByDistance.addEdges(paths);
        int result = pointNetworkByDistance.getAllEdges().size();
        assertEquals(expResult, result);
        
        paths = new LinkedList<>();
        pointNetworkByDistance.addEdges(paths);
        result = pointNetworkByDistance.getAllEdges().size();
        assertEquals(expResult, result);
        
        expResult = 2;
        pointNetworkByDistance.addVertex(new POI(1,0,0,"A"));
        pointNetworkByDistance.addVertex(new POI(2,2,0,"B"));
        pointNetworkByDistance.addVertex(new POI(3,3,0,"C"));
        paths.add(new Path(1,0,2,2,1,1,1));
        paths.add(new Path(1,0,3,3,1,1,1));
        pointNetworkByDistance.addEdges(paths);
        result = pointNetworkByDistance.getAllEdges().size();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of shortestRoute method, of class PointNetwork.
     */
    @Test
    public void testShortestRoute() {
        System.out.println("testShortestRoute");
        pointNetworkByDistance.addVertex(new POI(6,6,0,"A"));
        pointNetworkByDistance.addVertex(new POI(2,2,0,"B"));
        pointNetworkByDistance.addVertex(new POI(3,3,0,"C"));
        pointNetworkByDistance.addVertex(new POI(4,4,0,"D"));
        pointNetworkByDistance.addVertex(new POI(4,5,0,"E"));
        pointNetworkByDistance.addEdge(new Path(6,6,2,2,1,1,1)); // A-B (628,2km)
        pointNetworkByDistance.addEdge(new Path(2,2,3,3,1,1,1)); // B-C (157,2km)
        pointNetworkByDistance.addEdge(new Path(3,3,4,4,1,1,1)); // C-D (157,2km)
        pointNetworkByDistance.addEdge(new Path(3,3,4,5,1,1,1)); // C-E (248,3km)
        pointNetworkByDistance.addEdge(new Path(4,4,4,5,1,1,1)); // D-E (110,9km)
        
        List<Point> route = new LinkedList<>();
        List<Path> pathsOfRoute = new LinkedList<>();
        
        int expResult = -1;
        int result = pointNetworkByDistance.shortestRoute(1, 1, 4, 5, route, pathsOfRoute);
        assertEquals(expResult, result);
        
        result = pointNetworkByDistance.shortestRoute(6, 6, 1, 1, route, pathsOfRoute);
        assertEquals(expResult, result);
        
        expResult = 1033626; //meters
        route.clear();
        pathsOfRoute.clear();
        result = pointNetworkByDistance.shortestRoute(6, 6, 4, 5, route, pathsOfRoute);
        assertEquals(expResult, result);
        Iterator<Point> it = route.iterator(); // A-B-C-E
        assertEquals(it.next().getLatitude(), 6);
        assertEquals(it.next().getLatitude(), 2);
        assertEquals(it.next().getLatitude(), 3);
        assertEquals(it.next().getLongitude(), 5);
        
        expResult = 424824; //meters
        pointNetworkByDistance.addEdge(new Path(6,6,4,4,1,1,1)); // A-D (313,9km)
        route.clear();
        pathsOfRoute.clear();
        result = pointNetworkByDistance.shortestRoute(6, 6, 4, 5, route, pathsOfRoute);
        assertEquals(expResult, result);
        Iterator<Point> it2 = route.iterator(); // A-D-E
        assertEquals(it2.next().getLatitude(), 6);
        assertEquals(it2.next().getLongitude(), 4);
        assertEquals(it2.next().getLongitude(), 5);
        
        expResult = -1;
        pointNetworkByDistance.addVertex(new POI(7,7,0,"F"));
        result = pointNetworkByDistance.shortestRoute(6, 6, 7, 7, route, pathsOfRoute);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of shortestRoutes method, of class PointNetwork.
     */
    @Test
    public void testShortestRoutes() {
        System.out.println("testShortestRoutes");
        pointNetworkByDistance.addVertex(new POI(0,0,0,"A"));
        pointNetworkByDistance.addVertex(new POI(0,50,0,"B"));
        pointNetworkByDistance.addVertex(new POI(0,-50,0,"C"));
        pointNetworkByDistance.addVertex(new POI(50,0,0,"D"));
        pointNetworkByDistance.addEdge(new Path(0,0,0,50,1,1,1)); // A-B (5560km)
        pointNetworkByDistance.addEdge(new Path(0,0,0,-50,1,1,1)); // A-C (5560km)
        pointNetworkByDistance.addEdge(new Path(0,50,50,0,1,1,1)); // B-D (7294km)
        pointNetworkByDistance.addEdge(new Path(0,-50,50,0,1,1,1)); // C-D (7294km)
        
        List<Pair<List<Point>, List<Path>>> routesAndItsPaths = new LinkedList<>();
        
        int expResult = -1;
        int result = pointNetworkByDistance.shortestRoutes(1, 1, 4, 5, routesAndItsPaths);
        assertEquals(expResult, result);
        
        result = pointNetworkByDistance.shortestRoutes(6, 6, 1, 1, routesAndItsPaths);
        assertEquals(expResult, result);
        
        expResult = 12853633; //meters
        routesAndItsPaths.clear();
        result = pointNetworkByDistance.shortestRoutes(0, 0, 50, 0, routesAndItsPaths); // A-D
        assertEquals(expResult, result);
        assertEquals(routesAndItsPaths.size(), 2);
        
        expResult = -1;
        result = pointNetworkByDistance.shortestRoutes(0, 0, 0, 0, routesAndItsPaths);
        assertEquals(expResult, result);
    }
    
}
