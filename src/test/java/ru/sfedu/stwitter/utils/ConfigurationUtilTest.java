/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.utils;
import ru.sfedu.stwitter.Constants;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniel
 */
public class ConfigurationUtilTest {
    
    public ConfigurationUtilTest() {
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
     * Test of getConfigurationEntry method, of class ConfigurationUtil.
     */
    @Test
    public void testGetConfigurationEntry() throws Exception {
        System.out.println("getConfigurationEntry");
        String key = Constants.CSV_PATH_USERS;
        String expResult = "kek";
        String result = ConfigurationUtil.getConfigurationEntry(key);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("result: " + result);
    }
    
}
