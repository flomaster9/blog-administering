package ru.sfedu.stwitter.database;

import java.util.ArrayList;
import java.util.List;
import static ru.sfedu.stwitter.SeedEntities.planets;
import ru.sfedu.stwitter.database.provider.*;
import org.apache.log4j.Logger;
import ru.sfedu.stwitter.database.entites.*;

/**
 *
 * @author daniel
 */
public class Seed {
    private static IDataProvider dataProvider = null;
    private static User user = null;
    private static Post post = null;
    private static Comment comment = null;
    protected static Logger log = Logger.getLogger(Seed.class);
    private static Result result;
    private static int postCounter = 2;
    private static int commentCounter = 1;
    

    public static void main(String args[]) {
        log.info("_____SEEDS_____");
        
        log.info("_____SQL provider start_____");
        dataProvider = new PsqlProvider();
        seed(dataProvider);
        log.info("_____SQL provider end_____");
        
        log.info("_____CSV provider start_____");
        dataProvider = new CsvProvider();
        seed(dataProvider);
        log.info("_____CSV provider end_____");
        
        log.info("_____XML provider start_____");
        dataProvider = new XmlProvider();
        seed(dataProvider);
        log.info("_____XML provider end_____");
        
        log.info("-----SEEDS------");
    }
    
    private static void seed(IDataProvider dataProvider) {   
        List<User> createdUsers = new ArrayList<User>();
        List<Post> createdPosts = new ArrayList<Post>();
        List<Comment> createdComments = new ArrayList<Comment>();
        
        planets.stream().forEach(planet -> {
            createdUsers.add(seedUser(planet));
        });
        
        createdUsers.stream().forEach(user -> {
            if (user == null) return;
            for (int i = 1; i < postCounter + 1 ; i++)
                createdPosts.add(seedUserPosts(user, i));
        });
        
        createdPosts.stream().forEach(post -> {
            if (post == null) return;

            createdUsers.stream().forEach(user -> {
                if (post == null) return;
                for (int i = 1; i < commentCounter + 1 ; i++)
                    createdComments.add(seedUserPostComments(post, user, i));
            });
        });
    }
    
    private static User seedUser(String planet) {
        user = new User(planet + "Login", planet + "Name");
        result = dataProvider.saveRecord(user, EntityType.USER);
            
        if (result.getStatus() != ResultType.SUCCESS.ordinal()) {
            log.info("Exit with status " + result.getStatus());
            return null;
        }
        
        user = (User) result.getBean();
        log.info("User with ID " + user.getId() + " was created");
        return user;
    }
    
    private static Post seedUserPosts(User user, int currentPost) {
        String postTitle = user.getLogin() + " " + currentPost + " post title";
        String postContent = "hello, my name is " +  user.getName() + " and here is my " + currentPost + " post!";
        
        post = new Post(user.getId(), postTitle, postContent);
        result = dataProvider.saveRecord(post, EntityType.POST);
            
        if (result.getStatus() != ResultType.SUCCESS.ordinal()) {
            log.info("Exit with status " + result.getStatus());
            return null;
        }
        
        post = (Post) result.getBean();
        log.info("Post with ID " + post.getId() + " was created");
        return post;
    }
    
    private static Comment seedUserPostComments(Post post, User user, int currentComment){
        String commentContent = "Hello, my name is " +  user.getName() + " and here is my " + currentComment + " comment for post with title: " + post.getTitle() + " !";
        
        comment = new Comment(post.getId(), user.getId(), commentContent);
        result = dataProvider.saveRecord(comment, EntityType.COMMENT);
            
        if (result.getStatus() != ResultType.SUCCESS.ordinal()) {
            log.info("Exit with status " + result.getStatus());
            return null;
        }
        
        comment = (Comment) result.getBean();
        log.info("Comment with ID " + comment.getId() + " was created");
        return comment;
    }
}
