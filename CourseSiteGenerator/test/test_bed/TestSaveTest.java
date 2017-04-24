/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import csg.csg_App;
import csg.data.csg_TAData;
import djf.components.AppDataComponent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Richu
 */
public class TestSaveTest {
    static csg_App app; 
    
    public TestSaveTest() {
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
     * Test of main method, of class TestSave.
     */
    @Test
//    public void testMain() {
//        System.out.println("main");
//        String[] args = null;
//        TestSave.main(args);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of loadData method, of class TestSave.
     */
    //@Test
    public void testLoadData() throws Exception {
        try{
        csg_App app = new csg_App();
        System.out.println("loadData");
        AppDataComponent data = null;
        csg_TAData dataManager = (csg_TAData)data;
        
        //csg_TAData data = new csg_TAData(app);
        String filePath = "C:\\Users\\Richu\\219NetBeans\\Final_Proj\\CourseSiteGenerator\\work\\SiteSaveTest.json";
        TestSave instance = new TestSave();
        instance.loadData(dataManager, filePath);
        assertEquals(dataManager, data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Test of saveData method, of class TestSave.
     */
//    @Test
//    public void testSaveData() throws Exception {
//        System.out.println("saveData");
//        AppDataComponent data = null;
//        csg_TAData dataManager = (csg_TAData)data;
//        String filePath = "C:\\Users\\Richu\\219NetBeans\\Final_Proj\\CourseSiteGenerator\\work\\SiteSaveTest.json";
//        TestSave instance = new TestSave();
//        instance.saveData(dataManager, filePath);
//        //assertEquals(data, data);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
