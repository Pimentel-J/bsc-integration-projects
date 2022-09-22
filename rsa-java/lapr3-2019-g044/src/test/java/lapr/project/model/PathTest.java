/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 *
 */
public class PathTest {

    private Path example;

    @BeforeEach
    void setUp() {
        example = new Path();
    }

    @Test
    public void testSets() {
        System.out.println("AllSets");
        
        assertThrows(IllegalArgumentException.class, () -> example.setLongA(280));
        assertThrows(IllegalArgumentException.class, () -> example.setLongA(-280));
                
        assertThrows(IllegalArgumentException.class, () -> example.setLatB(280));
        assertThrows(IllegalArgumentException.class, () -> example.setLatB(-280));
        
        assertThrows(IllegalArgumentException.class, () -> example.setLongB(280));
        assertThrows(IllegalArgumentException.class, () -> example.setLongB(-280));
        
        assertThrows(IllegalArgumentException.class, () -> example.setK_coeff(-280));
        
        assertThrows(IllegalArgumentException.class, () -> example.setWind_dir(-280));
        assertThrows(IllegalArgumentException.class, () -> example.setWind_dir(2280));
        
        assertThrows(IllegalArgumentException.class, () -> example.setWind_speed(-280));
        
        
    }
    
    @Test
    public void testGets() {
        System.out.println("AllGets");
        
        assertEquals(example.getK_coeff(),0);
                
        assertEquals(example.getWind_dir(),0);
        
        assertEquals(example.getWind_speed(),0);
        
        example.setLatA(12.3);
        example.setLongA(12.3);
        example.setLatB(12.4);
        example.setLongB(12.4);
        
        assertEquals(example.getDistance(), 15544.45, 0.01);
        
    }
    
    @Test
    public void testToString() {
        System.out.println("ToString");
        
        assertEquals(example.toString(),"Path: [latA=0.000000], [longA=0.000000], "
                + "[latB=0.000000], [longB=0.000000], "
                + "[coef_k=0.000], [wind_dir=0], [wind_speed=0.0]\n");
        
    }
    
    @Test
    public void testHashCode() {
        System.out.println("HashCode");
        
        assertEquals(example.hashCode(),-1309035181);
        
    }
    
    @Test
    public void testEquals() {
        System.out.println("Equals");
        
        assertTrue(example.equals(example));
        
        assertFalse(example.equals(null));
        
        assertFalse(example.equals(new Park()));
        
        Path path = new Path(12.3, 12.3, 12.3, 12.3, 0.01, 3, 21.1f);
        
        assertFalse(example.equals(path));
        path.setLatA(0);
        assertFalse(example.equals(path));
        path.setLongA(0);
        assertFalse(example.equals(path));
        path.setLatB(0);
        assertFalse(example.equals(path));
        path.setLongB(0);
        assertFalse(example.equals(path));
        path.setK_coeff(0);
        assertFalse(example.equals(path));
        path.setWind_dir(0);
        assertFalse(example.equals(path));
        
        
        
        
    }
    
}
