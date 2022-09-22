/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr2.project.agpsd.model;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
//import org.junit.Test;
//import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 *
 */
public class ApplicationTest {

    public ApplicationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getFullName method, of class Application.
     */
    @Ignore
    public void testGetFullName() {
        System.out.println("getFullName");
        Application instance = new Application("testName", "123", "test", "123", new PostalAddress("test", "test", "test"));
        String expResult = "testName";
        String result = instance.getFullName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEmail method, of class Application.
     */
    @Ignore
    public void testGetEmail() {
        System.out.println("getEmail");
        Application instance = new Application();
        String expResult = "";
        String result = instance.getEmail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPhoneNumber method, of class Application.
     */
    @Ignore
    public void testGetPhoneNumber() {
        System.out.println("getPhoneNumber");
        Application instance = new Application();
        String expResult = "";
        String result = instance.getPhoneNumber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNIF method, of class Application.
     */
    @Ignore
    public void testGetNIF() {
        System.out.println("getNIF");
        Application instance = new Application();
        String expResult = "";
        String result = instance.getNIF();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPostalAddress method, of class Application.
     */
    @Ignore
    public void testGetPostalAddress() {
        System.out.println("getPostalAddress");
        Application instance = new Application();
        PostalAddress expResult = null;
        PostalAddress result = instance.getPostalAddress();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCategoryList method, of class Application.
     */
    @Ignore
    public void testGetCategoryList() {
        System.out.println("getCategoryList");
        Application instance = new Application();
        List<Category> expResult = null;
        List<Category> result = instance.getCategoryList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAcademicQualificationList method, of class Application.
     */
    @Ignore
    public void testGetAcademicQualificationList() {
        System.out.println("getAcademicQualificationList");
        Application instance = new Application();
        List<AcademicQualification> expResult = null;
        List<AcademicQualification> result = instance.getAcademicQualificationList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getProfessionalQualificationList method, of class Application.
     */
    @Ignore
    public void testGetProfessionalQualificationList() {
        System.out.println("getProfessionalQualificationList");
        Application instance = new Application();
        List<ProfessionalQualification> expResult = null;
        List<ProfessionalQualification> result = instance.getProfessionalQualificationList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getDocumentList method, of class Application.
     */
    @Ignore
    public void testGetDocumentList() {
        System.out.println("getDocumentList");
        Application instance = new Application();
        List<Document> expResult = null;
        List<Document> result = instance.getDocumentList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addCategory method, of class Application.
     */
    @Ignore
    public void testAddCategory() {
        System.out.println("addCategory");
        Category newCategory = null;
        Application instance = new Application();
        boolean expResult = false;
        boolean result = instance.addCategory(newCategory);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addAcademicQualification method, of class Application.
     */
    @Ignore
    public void testAddAcademicQualification() {
        System.out.println("addAcademicQualification");
        AcademicQualification newAcademicQualification = null;
        Application instance = new Application();
        boolean expResult = false;
        boolean result = instance.addAcademicQualification(newAcademicQualification);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addProfessionalQualification method, of class Application.
     */
    @Test
    public void testAddProfessionalQualification() {
        System.out.println("addProfessionalQualification");
        ProfessionalQualification newprofessionalQualification = new ProfessionalQualification("pqTest");
        Application instance = new Application("testName", "123", "test", "123", new PostalAddress("test", "test", "test"));
        instance.addProfessionalQualification(newprofessionalQualification);
        String expResult = "pqTest";
        String result = instance.getProfessionalQualificationList().get(0).getDescription();
        assertEquals(expResult, result, "pqTest");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addDocument method, of class Application.
     */
    @Ignore
    public void testAddDocument() {
        System.out.println("addDocument");
        Document newDocument = null;
        Application instance = new Application();
        boolean expResult = false;
        boolean result = instance.addDocument(newDocument);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addPostalAddress method, of class Application.
     */
    @Ignore
    public void testAddPostalAddress() {
        System.out.println("addPostalAddress");
        PostalAddress postalAddress = null;
        Application instance = new Application();
        instance.addPostalAddress(postalAddress);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Application.
     */
    @Ignore
    public void testToString() {
        System.out.println("toString");
        Application instance = new Application();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Application.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Application instance = new Application("testName", "123", "test", "123", new PostalAddress("test", "test", "test"));
        int expResult = instance.hashCode();
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Application.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object o = new Application("testName", "123", "test", "123", new PostalAddress("test", "test", "test"));
        Application instance = new Application("testName", "123", "test", "123", new PostalAddress("test", "test", "test"));
        boolean expResult = true;
        boolean result = instance.equals(o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
