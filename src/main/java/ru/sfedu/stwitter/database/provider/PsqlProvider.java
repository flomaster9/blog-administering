/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sfedu.stwitter.database.provider;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import ru.sfedu.stwitter.database.entites.*;
import java.util.List;

/**
 *
 * @author daniel
 */
public class PsqlProvider<T extends WithId> implements IDataProvider<T> {

    private static Logger log = Logger.getLogger(PsqlProvider.class);
    
    private Connection conn;
    Statement statement;
    
    private String url;
    private String login;
    private String password;
    
    private List<T> records;
    
    public PsqlProvider() {
        initDataSource();
    }
    
    @Override
    public void initDataSource() {
        url = "jdbc:postgresql://localhost:5432/simply_twitter";
        login = "daniel";
        password = "111";
       
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, login, password);
            statement = conn.createStatement();
        } catch (Exception ex) {
            log.error("Error connection");
            log.error("Exception: " + ex);
        }
    }
    
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                statement.close();
            } catch (Exception ex) {
                log.error("Error in close connection: " + ex);
            }
        }
    }

    @Override
    public Result saveRecord(T bean, EntityType type){
        String query = "";
        switch (type){
            case USER:
                query = "INSERT INTO users(login, name)" +
                        " VALUES (" + bean.toString() + ");";
                break;
            case POST:
                query = "INSERT INTO posts(user_id, title, content)" +
                        " VALUES (" + bean.toString() + ");";
                break;
            case COMMENT:
                query = "INSERT INTO comments(post_id, user_id, content)" +
                        " VALUES (" + bean.toString() + ");";
                break;
        }
        try {
            statement.executeUpdate(query);
        } catch (SQLException e){
            log.error(e);
            return new Result(ResultType.SQL_EXCEPTION.ordinal());
        }
        return new Result(ResultType.SUCCESS.ordinal(), bean);
    }

    @Override
    public Result deleteRecord(int id, EntityType type){
        String query = "";
        Result result = getRecordById(id, type);
        
        if (result.getStatus() != ResultType.SUCCESS.ordinal())
            return result;
        
        switch (type){
            case USER:
                query = "DELETE FROM users WHERE id = " + id;
                break;
            case POST:
                query = "DELETE FROM posts WHERE id = " + id;
                break;
            case COMMENT:
                query = "DELETE FROM comments WHERE id = " + id;
                break;
        }
        try {
            statement.executeUpdate(query);
        } catch (SQLException e){
            log.error(e);
            return new Result(ResultType.SQL_EXCEPTION.ordinal());
        }
        return new Result(ResultType.SUCCESS.ordinal(), result.getBean());
    }
    
    @Override
    public Result updateRecord(T bean, EntityType type) {
        Result result = getRecordById(bean.getId(), type);
        
        if (result.getStatus() != ResultType.SUCCESS.ordinal())
            return result;
        
        String query = "";
        
        switch (type){
            case USER:
                query = "UPDATE users set (login, name) = (" + bean.toString() + ") where id = " + bean.getId() + ";";
                break;
            case POST:
                query = "UPDATE posts set (user_id, title, content) = (" + bean.toString() + ") where id = " + bean.getId() + ";";
                break;
            case COMMENT:
                query = "UPDATE comments set (post_id, user_id, content) = (" + bean.toString() + ") where id = " + bean.getId() + ";";
                break;
        }
        
        try {
            statement.executeUpdate(query);
            result.setBean(bean);
            return result;
        } catch (SQLException e){
            log.error(e);
            return new Result(ResultType.SQL_EXCEPTION.ordinal());
        }
    }
    
    private Result getUserRecordById(int id){
        String query = "SELECT * FROM users WHERE id = " + id;
        ResultSet resultSet;
        User user = new User();
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
            } else
                user = null;
        } catch (SQLException e){
            log.error(e);
            return new Result(ResultType.SQL_EXCEPTION.ordinal());
        }
        return user != null ? new Result(ResultType.SUCCESS.ordinal(), user) : new Result(ResultType.NOT_FOUND.ordinal());
    }
    
    private Result getPostRecordById(int id){
        String query = "SELECT * FROM posts WHERE id = " + id;
        ResultSet resultSet;
        Post post = new Post();
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                post.setId(resultSet.getInt("id"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
            } else
                post = null;
        } catch (SQLException e){
            log.error(e);
            return new Result(ResultType.SQL_EXCEPTION.ordinal());
        }
        return post != null ? new Result(ResultType.SUCCESS.ordinal(), post) : new Result(ResultType.NOT_FOUND.ordinal());
    }
    
    private Result getCommentRecordById(int id){
        String query = "SELECT * FROM comments WHERE id = " + id;
        ResultSet resultSet;
        Comment comment = new Comment();
        try {
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                comment.setId(resultSet.getInt("id"));
                comment.setPostId(resultSet.getInt("post_id"));
                comment.setUserId(resultSet.getInt("user_id"));
                comment.setContent(resultSet.getString("content"));
            } else 
                comment = null;
        } catch (SQLException e){
            log.error(e);
            return new Result(ResultType.SQL_EXCEPTION.ordinal());
        }
        return comment != null ? new Result(ResultType.SUCCESS.ordinal(), comment) : new Result(ResultType.NOT_FOUND.ordinal());
    }

    @Override
    public Result getRecordById(int id, EntityType type){
        Result result = null;
        switch (type){
            case USER:
                return getUserRecordById(id);
            case POST:
                return getPostRecordById(id);
            case COMMENT:
                return getCommentRecordById(id);
        }
        return new Result(ResultType.FAILURE.ordinal());
    }
    
    
    private List<T> getAllUserRecords() {
        records = new LinkedList<T>();
        String query = "Select * from users";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                if (user == null) break;
                records.add((T) user);
            }
        } catch (SQLException e){
            log.error(e);
        }
        return records;
    }
    
    private List<T> getAllPostRecords() {
        records = new LinkedList<T>();
        String query = "Select * from posts";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setUserId(resultSet.getInt("user_id"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                if (post == null) break;
                records.add((T) post);
            }
        } catch (SQLException e){
            log.error(e);
        }
        return records;
    }
        
    private List<T> getAllCommentRecords() {
        records = new LinkedList<T>();
        String query = "Select * from comments";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setPostId(resultSet.getInt("post_id"));
                comment.setUserId(resultSet.getInt("user_id"));
                comment.setContent(resultSet.getString("content"));
                if (comment == null) break;
                records.add((T) comment);
            }
        } catch (SQLException e){
            log.error(e);
        }
        return records;
    }
    
    @Override
    public List<T> getAllRecords(EntityType type) {
        records = null;

        switch (type){
            case USER:
                return getAllUserRecords();
            case POST:
                return getAllPostRecords();
            case COMMENT:
                return getAllCommentRecords();
        }
        return records;
    }
}
