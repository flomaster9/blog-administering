package ru.sfedu.stwitter.database.provider;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import ru.sfedu.stwitter.database.entites.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.stwitter.Constants;
import ru.sfedu.stwitter.utils.ConfigurationUtil;

/**
 *
 * @author daniel
 */
public class XmlProvider<T extends WithId> implements IDataProvider<T> {
    
    protected static Logger log = Logger.getLogger(XmlProvider.class);
    private Serializer serializer = new Persister();
    
    @Override
    public void initDataSource() {}

    private int getLastRecordId(List<T> records) {
        return records != null && records.size() != 0 ? records.get(records.size() - 1).getId() + 1 : 1;
    }
    
    private Result saveUserRecords(List<User> list) {
        XmlElementList userList = new XmlElementList();
        userList.setBeans(list);
        try {
            File source = new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_USERS));
            serializer.write(userList, source);
            return new Result(ResultType.SUCCESS.ordinal());
        } catch (Exception ex) {
            log.info(ex);
        }
        
        return new Result(ResultType.FAILURE.ordinal());
    }
    
    private Result savePostRecords(List<Post> list) {
        XmlElementList postList = new XmlElementList();
        postList.setBeans(list);
        try {
            File source = new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_POSTS));
            serializer.write(postList, source);
            return new Result(ResultType.SUCCESS.ordinal());
        } catch (Exception ex) {
            log.info(ex);
        }
        
        return new Result(ResultType.FAILURE.ordinal());
    }
        
    private Result saveCommentRecords(List<Comment> list) {
        XmlElementList commentList = new XmlElementList();
        commentList.setBeans(list);
        try {
            File source = new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_COMMENTS));
            serializer.write(commentList, source);
            return new Result(ResultType.SUCCESS.ordinal());
        } catch (Exception ex) {
            log.info(ex);
        }
        
        return new Result(ResultType.FAILURE.ordinal());
    }
    
    private boolean isRecordExist(int id, EntityType type) {
        Result result = null;
        switch (type){
            case USER:
                result = getUserRecordById(id);
                break;
            case POST:
                result = getPostRecordById(id);
                break;
            case COMMENT:
                result = getCommentRecordById(id);
                break;
        }
        return result.getStatus() == ResultType.SUCCESS.ordinal() ? true : false;
    }
    
    @Override
    public Result saveRecord(T bean) {
        Result result = null;
        List<T> records = null;
        int userId = 0;
        int postId = 0;
        switch(bean.getType()) {
            case USER:
                records = (List<T>) getAllUserRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                result = saveUserRecords((List<User>) records);
                break;
            case POST:
                userId = ((Post) bean).getUserId();
                if (!isRecordExist(userId, EntityType.USER))
                    return new Result(ResultType.USER_NOT_EXIST.ordinal());
                
                records = (List<T>) getAllPostRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                result = savePostRecords((List<Post>) records);
                break;
            case COMMENT:
                userId = ((Comment) bean).getUserId();
                if (!isRecordExist(userId, EntityType.USER))
                    return new Result(ResultType.USER_NOT_EXIST.ordinal());
                
                postId = ((Comment) bean).getPostId();
                if (!isRecordExist(postId, EntityType.POST))
                    return new Result(ResultType.POST_NOT_EXIST.ordinal());
                
                records = (List<T>) getAllCommentRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                result = saveCommentRecords((List<Comment>) records);
                break;
        }
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) 
            result.setBean(bean);
        
        return result;
    }

    private Result dependencyDestroy(int id, EntityType type) {
        List<T> postRecords = null;
        List<T> commentRecords = null;
        Result result;
        switch (type){
            case USER:
                postRecords = (List<T>) getAllPostRecords().stream().filter(t -> {
                    if (t.getUserId() == id)
                        dependencyDestroy(t.getId(), EntityType.POST);
                    return t.getUserId() != id;
                }).collect(Collectors.toList());
                result = savePostRecords((List<Post>) postRecords);
                
                commentRecords = (List<T>) getAllCommentRecords().stream().filter(t -> t.getUserId() != id).collect(Collectors.toList());
                result = saveCommentRecords((List<Comment>) commentRecords);
                
                return result;
            case POST:
                commentRecords = (List<T>) getAllCommentRecords().stream().filter(t -> t.getPostId() != id).collect(Collectors.toList());
                result = saveCommentRecords((List<Comment>) commentRecords);
                return result;
            case COMMENT:
                return new Result(ResultType.SUCCESS.ordinal());
        }
        
        return new Result(ResultType.FAILURE.ordinal());
    }
    
    @Override
    public Result deleteRecord(int id, EntityType type) {
        List<T> records = null;
        Result result = getRecordById(id, type);
        
        if (result.getStatus() != ResultType.SUCCESS.ordinal())
            return result;
        
        records = (List<T>) getAllRecords(type).stream().filter(r -> id != r.getId()).collect(Collectors.toList());

        switch (type){
            case USER:
                dependencyDestroy(id, EntityType.USER);
                return saveUserRecords((List<User>) records);
            case POST:
                dependencyDestroy(id, EntityType.POST);
                return savePostRecords((List<Post>) records);
            case COMMENT:
                return saveCommentRecords((List<Comment>) records);
        }
        
        return null;
    }
    
    private Result updateUserRecord(T bean) {
        List<T> records = null;
        records = (List<T>) getAllUserRecords();
        records.stream().forEach(item -> {
            if (item.getId() == bean.getId()) {
                User t = (User) item;
                t.updateWith((User) bean);
                item = (T) t;
            }
        });
        Result result = saveUserRecords((List<User>) records);
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result.setBean(bean);

        return result;
    }
    
    private Result updatePostRecord(T bean) {
        List<T> records = null;
        records = (List<T>) getAllPostRecords();
        records.stream().forEach(item -> {
            if (item.getId() == bean.getId()) {
                Post t = (Post) item;
                t.updateWith((Post) bean);
                item = (T) t;
            }
        });
        Result result = savePostRecords((List<Post>) records);
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result.setBean(bean);

        return result;
    }
        
    private Result updateCommentRecord(T bean) {
        List<T> records = null;
        records = (List<T>) getAllCommentRecords();
        records.stream().forEach(item -> {
            if (item.getId() == bean.getId()) {
                Comment t = (Comment) item;
                t.updateWith((Comment) bean);
                item = (T) t;
            }
        });
        Result result = saveCommentRecords((List<Comment>) records);
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            result.setBean(bean);

        return result;
    }
    
    @Override
    public Result updateRecord(T bean) {
        switch (bean.getType()){
            case USER:
                return updateUserRecord(bean);
            case POST:
                return updatePostRecord(bean);
            case COMMENT:
                return updateCommentRecord(bean);
        }
        
        return new Result(ResultType.FAILURE.ordinal());
    }
    
    private Result getUserRecordById(int id) {
        User user = null;
        user = getAllUserRecords().stream().filter(t -> Objects.equals(t.getId(), id)).findFirst().orElse(null);
        return user != null ? new Result(ResultType.SUCCESS.ordinal(), user) : new Result(ResultType.NOT_FOUND.ordinal());
    }
    
    private Result getPostRecordById(int id) {
        Post post = null;
        post = getAllPostRecords().stream().filter(t -> Objects.equals(t.getId(), id)).findFirst().orElse(null);
        return post != null ? new Result(ResultType.SUCCESS.ordinal(), post) : new Result(ResultType.NOT_FOUND.ordinal());
    }
        
    private Result getCommentRecordById(int id) {
        Comment comment = null;
        comment = getAllCommentRecords().stream().filter(t -> Objects.equals(t.getId(), id)).findFirst().orElse(null);
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
    
    private List<User> getAllUserRecords() {
        XmlElementList userList = new XmlElementList();
        List<User> records = new LinkedList<User>();
        try {
            File source = new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_USERS));
            userList = serializer.read(XmlElementList.class, source);
            if (userList.getBeans() != null)
                records = userList.getBeans();
        } catch (Exception ex) {
            log.error(ex);
        }
        return records;
    }
 
    private List<Post> getAllPostRecords() {
        XmlElementList postList = new XmlElementList();
        List<Post> records = new LinkedList<Post>();
        try {
            File source = new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_POSTS));
            postList = serializer.read(XmlElementList.class, source);
            if (postList.getBeans() != null)
                records = postList.getBeans();
        } catch (Exception ex) {
            log.error(ex);
        }
        return records;
    }
    
    private List<Comment> getAllCommentRecords() {
        XmlElementList commentList = new XmlElementList();
        List<Comment> records = new LinkedList<Comment>();
        try {
            File source = new File(ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH_COMMENTS));
            commentList = serializer.read(XmlElementList.class, source);
            if (commentList.getBeans() != null)
                records = commentList.getBeans();
        } catch (Exception ex) {
            log.error(ex);
        }
        return records;
    }
    
    @Override
    public List<T> getAllRecords(EntityType type) {
        switch (type){
            case USER:
                return (List<T>) getAllUserRecords();
            case POST:
                return (List<T>) getAllPostRecords();
            case COMMENT:
                return (List<T>) getAllCommentRecords();
        }
        return null;
    }
}
