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
 * @author daniel
 */
public class CsvProviderTest {
    private static Logger log = Logger.getLogger(CsvProvider.class);
    private static CsvProvider instance = new CsvProvider();
    private static int userId = 0;
    private static int postId = 0;
    private static int commentId = 0;
    
    @BeforeClass
    public static void setUpClass() {
        User user = new User("GuestLogin", "GuestName");
        instance.saveRecord(user, EntityType.USER);
        userId = user.getId();
        log.info(userId);
        
        Post post = new Post(userId, "PostTitle", "post content");
        instance.saveRecord(post, EntityType.POST);
        postId = post.getId();
        
        Comment comment = new Comment(userId, postId, "new comment");
        instance.saveRecord(comment, EntityType.COMMENT);
        userId = user.getId();
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
     * Test of saveRecord methods, of class CsvProvider.
     */

    @Test
    public void testSaveUserRecord() {
        User user = new User("Guest", "Name");
        Result result = instance.saveRecord(user, EntityType.USER);
        
        if(result.getStatus() == ResultType.SUCCESS.ordinal()) {
            userId = user.getId();
            log.info("User with id " + user.getId() + " was saved");
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }
    
    @Test
    public void testSavePostRecord() {
        Post post = new Post(userId, "post title", "post content");
        Result result = instance.saveRecord(post, EntityType.POST);
        
        if(result.getStatus() == ResultType.SUCCESS.ordinal()) {
            postId = post.getId();
            log.info("Post with id " + post.getId() + " was saved");
        } else {
            fail("Test failure with " + result.getStatus());
        }  
    }
    
    @Test
    public void testSaveCommentRecord() {
        Comment comment = new Comment(postId, userId, "comment content");
        Result result = instance.saveRecord(comment, EntityType.COMMENT);
        
        if(result.getStatus() == ResultType.SUCCESS.ordinal()) {
            commentId = comment.getId();
            log.info("Comment with id " + comment.getId() + " was saved");
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }

     /**
     * Test of getRecordById methods, of class CsvProvider.
     */
    
    @Test 
    public void TestGetUserRecordById() {
        Result result = instance.getRecordById(userId, EntityType.USER);
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Find user with id " + result.getBean().getId());
        } else if(result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Could not find user with id " + userId);
        }
    }
    
    @Test 
    public void TestGetPostRecordById() {
        Result result = instance.getRecordById(postId, EntityType.POST);
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Find post with id " + result.getBean().getId());
        } else if(result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Could not find post with id " + postId);
        }
    }
    
    @Test 
    public void TestGetCommentRecordById() {
        Result result = instance.getRecordById(commentId, EntityType.COMMENT);
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Find comment with id " + result.getBean().getId());
        } else if(result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Could not find comment with id " + commentId);
        }
    } 

     /**
     * Test of deleteRecord methods, of class CsvProvider.
     */
    
    @Test 
    public void testDeleteUserById() {
        Result result = instance.getRecordById(userId, EntityType.USER);
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result = instance.deleteRecord((User) result.getBean(), EntityType.USER);
        else {
            log.info("Not found user with id " + userId);
            return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("User with id " + result.getBean().getId() + " was deleted");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found user with id " + userId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }
    
    @Test 
    public void testDeletePostById() {
        Result result = instance.getRecordById(postId, EntityType.POST);
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result = instance.deleteRecord((Post) result.getBean(), EntityType.POST);
        else {
            log.info("Not found post with id " + postId);
            return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Post with id " + result.getBean().getId() + " was deleted");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found post with id " + postId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }
    
    @Test 
    public void testDeleteCommentById() {
        Result result = instance.getRecordById(commentId, EntityType.COMMENT);
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result = instance.deleteRecord((Comment) result.getBean(), EntityType.COMMENT);
        else {
            log.info("Not found comment with id " + commentId);
            return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Comment with id " + result.getBean().getId() + " was deleted");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found comment with id " + commentId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }  
         
}
