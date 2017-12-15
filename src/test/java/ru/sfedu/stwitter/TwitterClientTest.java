/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.stwitter.database.provider.PsqlProvider;

/**
 *
 * @author daniel
 */
public class TwitterClientTest {
    
    public TwitterClientTest() {
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

    @Test
    public void testLogger() {
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
        SimplyTwitterServer obj = new SimplyTwitterServer();
    }
    
    @Test
    public void testJDBC() {
        // TODO review the generated test code and remove the default call to fail.
        
        
                
    }
    
}
