/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.database.provider;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.stwitter.database.entites.EntityType;

/**
 *
 * @author daniel
 */
public class PsqlProviderTest {
    
    public PsqlProviderTest() {
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
     * Test of createStatement method, of class PsqlProvider.
     */
    @Test
    public void testCreateStatement() {
        System.out.println("createStatement");
        String query = "";
        PsqlProvider instance = null;
        instance.createStatement(query);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeConnection method, of class PsqlProvider.
     */
    @Test
    public void testCloseConnection() {
        System.out.println("closeConnection");
        PsqlProvider instance = null;
        instance.closeConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstance method, of class PsqlProvider.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        PsqlProvider expResult = null;
        PsqlProvider result = PsqlProvider.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveRecord method, of class PsqlProvider.
     */
    @Test
    public void testSaveRecord() {
        System.out.println("saveRecord");
        Object bean = null;
        EntityType type = null;
        PsqlProvider instance = null;
        instance.saveRecord(bean, type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteRecord method, of class PsqlProvider.
     */
    @Test
    public void testDeleteRecord() {
        System.out.println("deleteRecord");
        Object bean = null;
        EntityType type = null;
        PsqlProvider instance = null;
        instance.deleteRecord(bean, type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecordById method, of class PsqlProvider.
     */
    @Test
    public void testGetRecordById() {
        System.out.println("getRecordById");
        int id = 0;
        EntityType type = null;
        PsqlProvider instance = null;
        Object expResult = null;
        Object result = instance.getRecordById(id, type);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initDataSource method, of class PsqlProvider.
     */
    @Test
    public void testInitDataSource() {
        System.out.println("initDataSource");
        EntityType type = null;
        PsqlProvider instance = null;
        instance.initDataSource(type);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
