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
import ru.sfedu.stwitter.database.entites.*;

/**
 *
 * @author daniel
 */
public class XmlProviderTest {
    
    XmlProvider instance = new XmlProvider();
    Result result;
    
    public XmlProviderTest() {
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
    public void getUserById() {
        result = instance.getRecordById(1, EntityType.USER);
        System.out.println((User) result.getBean());
    }
    
}
