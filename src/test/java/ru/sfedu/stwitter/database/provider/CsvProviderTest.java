package ru.sfedu.stwitter.database.provider;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.sfedu.stwitter.database.entites.*;
import org.apache.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * @author daniel
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CsvProviderTest {
    private static Logger log = Logger.getLogger(CsvProvider.class);
    private static CsvProvider instance = new CsvProvider();
    private static int userId = 0;
    private static int postId = 0;
    private static int commentId = 0;
    
    @BeforeClass
    public static void setUpClass() {
        Result result;
        User user = new User("GuestLogin", "GuestName");
        result = instance.saveRecord(user, EntityType.USER);
        userId = result.getBean().getId();
        
        Post post = new Post(userId, "New post title", "New post content");
        result = instance.saveRecord(post, EntityType.POST);
        postId = result.getBean().getId();
        
        Comment comment = new Comment(postId, userId, "New user's comment");
        result = instance.saveRecord(comment, EntityType.COMMENT);
        commentId = result.getBean().getId();
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
    public void testASaveUserRecord() {
        User user = new User("GuestLogin", "GuestName");
        Result result = instance.saveRecord(user, EntityType.USER);
        
        if(result.getStatus() == ResultType.SUCCESS.ordinal()) {
            userId = user.getId();
            log.info("User with id " + user.getId() + " was saved");
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }
    
    @Test
    public void testBSavePostRecord() {
        Post post = new Post(userId, "New post title", "New post content");
        Result result = instance.saveRecord(post, EntityType.POST);
        
        if(result.getStatus() == ResultType.SUCCESS.ordinal()) {
            postId = post.getId();
            log.info("Post with id " + post.getId() + " was saved");
        } else if(result.getStatus() == ResultType.USER_NOT_EXIST.ordinal()) {
            log.info("User with id " + userId + " is missing");
        } else {
            fail("Test failure with " + result.getStatus());
        }  
    }
    
    @Test
    public void testCSaveCommentRecord() {
        Comment comment = new Comment(postId, userId, "New user's comment");
        Result result = instance.saveRecord(comment, EntityType.COMMENT);
        
        if(result.getStatus() == ResultType.SUCCESS.ordinal()) {
            commentId = comment.getId();
            log.info("Comment with id " + comment.getId() + " was saved");
        } else if(result.getStatus() == ResultType.USER_NOT_EXIST.ordinal()) {
            log.info("User with id " + userId + " is missing");
        } else if(result.getStatus() == ResultType.POST_NOT_EXIST.ordinal()) {
            log.info("Post with id " + postId + " is missing");
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }

     /**
     * Test of getRecordById methods, of class CsvProvider.
     */
    
    @Test 
    public void TestDGetUserRecordById() {
        Result result = instance.getRecordById(userId, EntityType.USER);
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Find user with id " + result.getBean().getId());
        } else if(result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Could not find user with id " + userId);
        }
    }
    
    @Test 
    public void TestEGetPostRecordById() {
        Result result = instance.getRecordById(postId, EntityType.POST);
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Find post with id " + result.getBean().getId());
        } else if(result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Could not find post with id " + postId);
        }
    }
    
    @Test 
    public void TestFGetCommentRecordById() {
        Result result = instance.getRecordById(commentId, EntityType.COMMENT);
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Find comment with id " + result.getBean().getId());
        } else if(result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Could not find comment with id " + commentId);
        }
    } 
    
    /**
     * Test of updateRecord methods, of class CsvProvider.
     */
    @Test
    public void testGUpdateUserRecord() {
        Result result = instance.getRecordById(userId, EntityType.USER);
        
        
        if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found user with id " + postId);
            return;
        } else if (result.getStatus() != ResultType.SUCCESS.ordinal()) {
            fail("Test failure with " + result.getStatus());
            return;
        }

        User user = (User) result.getBean();
        user.setLogin("UpdatedGuestLogin");
        user.setName("UpdatedGuestName");
        result = instance.updateRecord(user, EntityType.USER);
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("User with id " + result.getBean().getId() + " was updated");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found user with id " + userId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }
    
    @Test
    public void testHUpdatePostRecord() {
        Result result = instance.getRecordById(postId, EntityType.POST);
        
        if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found post with id " + postId);
            return;
        } else if (result.getStatus() != ResultType.SUCCESS.ordinal()) {
            fail("Test failure with " + result.getStatus());
            return;
        }

        Post post = (Post) result.getBean();
        post.setTitle("Updated post title");
        post.setContent("Updated post content");
        result = instance.updateRecord(post, EntityType.POST);

        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result = instance.updateRecord(post, EntityType.POST);

        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Post with id " + result.getBean().getId() + " was updated");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found post with id " + postId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }
    
    @Test
    public void testIUpdateCommentRecord() {
        Result result = instance.getRecordById(commentId, EntityType.COMMENT);

        if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found comment with id " + commentId);
            return;
        } else if (result.getStatus() != ResultType.SUCCESS.ordinal()) {
            fail("Test failure with " + result.getStatus());
            return;
        }

        Comment comment = (Comment) result.getBean();
        comment.setContent("Updated user's comment");
        result = instance.updateRecord(comment, EntityType.COMMENT);

        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Comment with id " + result.getBean().getId() + " was updated");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found comment with id " + commentId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }

     /**
     * Test of deleteRecord methods, of class CsvProvider.
     */
    
    @Test 
    public void testLDeleteUserById() {
        Result result = instance.getRecordById(userId, EntityType.USER);
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result = instance.deleteRecord(userId, EntityType.USER);
        else {
            log.info("Not found user with id " + userId);
            return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("User with id " + userId + " was deleted");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found user with id " + userId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }
    
    @Test 
    public void testKDeletePostById() {
        Result result = instance.getRecordById(postId, EntityType.POST);
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result = instance.deleteRecord(postId, EntityType.POST);
        else {
            log.info("Not found post with id " + postId);
            return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Post with id " + postId + " was deleted");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found post with id " + postId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }
    
    @Test 
    public void testJDeleteCommentById() {
        Result result = instance.getRecordById(commentId, EntityType.COMMENT);
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result = instance.deleteRecord(commentId, EntityType.COMMENT);
        else {
            log.info("Not found comment with id " + commentId);
            return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) {
            log.info("Comment with id " + commentId + " was deleted");
        } else if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
            log.info("Not found comment with id " + commentId);
        } else {
            fail("Test failure with " + result.getStatus());
        }
    }  
         
}
