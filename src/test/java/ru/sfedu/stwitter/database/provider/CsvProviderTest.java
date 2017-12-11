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
import org.apache.log4j.Logger;

/**
 *
 * @author daniel
 */
public class CsvProviderTest {
    private static Logger log = Logger.getLogger(CsvProvider.class);
    
    public CsvProviderTest() {
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
     * Test of saveRecord method, of class CsvProvider.
     */
    @Test
    public void testSaveRecord() {
        log.info("-------testSaveRecord------------");
        CsvProvider instance = new CsvProvider();
        
        User user = new User("Guest");
        instance.saveRecord(user, EntityType.USER);
        
        Post post = new Post(user.getId(), "User post", "content content content");
        instance.saveRecord(post, EntityType.POST);
        
        Comment comment = new Comment(post.getId(), user.getId(), "its my new comment");
        instance.saveRecord(comment, EntityType.COMMENT);

        log.info("---------save-end-------------------");
    }
    
    @Test
    public void savePost() {
        Post post = new Post(1, "User post", "content content content");
        log.info(post);
        CsvProvider instance = new CsvProvider();
        instance.saveRecord(post, EntityType.POST);
    }
    
    @Test
    public void saveComment() {
        Comment comment = new Comment(1, 1, "its my new comment");
        CsvProvider instance = new CsvProvider();
        instance.saveRecord(comment, EntityType.COMMENT);
    }

    /**
     * Test of deleteRecord method, of class CsvProvider.
     */
    @Test
    public void testDeleteRecord() {
        log.info("-------testDeleteRecord------------");
        CsvProvider instance = new CsvProvider();
        int id = 1;
        
        User user = (User) instance.getRecordById(id, EntityType.USER);
        Post post = (Post) instance.getRecordById(id, EntityType.POST);
        Comment comment = (Comment) instance.getRecordById(id, EntityType.COMMENT);
        
        instance.deleteRecord(user, EntityType.USER);
        instance.deleteRecord(post, EntityType.POST);
        instance.deleteRecord(comment, EntityType.COMMENT);
        log.info("---------delete-end-------------------");
    }

    /**
     * Test of getRecordById method, of class CsvProvider.
     */
    @Test
    public void testGetRecordById() {
        log.info("-------testGetRecordById------------");
        int id = 1;
        
        CsvProvider instance = new CsvProvider();
        User user = (User) instance.getRecordById(id, EntityType.USER);
        Post post = (Post) instance.getRecordById(id, EntityType.POST);
        Comment comment = (Comment) instance.getRecordById(id, EntityType.COMMENT);
        log.info(user);
        log.info(post);
        log.info(comment);
        log.info("---------get-by-id-end-------------------");
    }
    
}
