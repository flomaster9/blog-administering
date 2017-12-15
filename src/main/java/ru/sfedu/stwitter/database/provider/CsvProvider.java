package ru.sfedu.stwitter.database.provider;
import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import ru.sfedu.stwitter.Constants;
import ru.sfedu.stwitter.database.entites.*;
import ru.sfedu.stwitter.utils.ConfigurationUtil;

/**
 *
 * @author daniel
 */
public class CsvProvider<T extends WithId> implements IDataProvider<T> {
    
    private static Logger log = Logger.getLogger(CsvProvider.class);
    
    private FileReader reader;
    private FileWriter writer;
    private StatefulBeanToCsv beanToCsv;
    private List<T> records;

    private List<T> getAllUserRecords() {
        records = null;
        try {
            reader = new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_USERS));
            CsvToBean csvToBean = new CsvToBeanBuilder(reader).withType(User.class).build();
            records = csvToBean.parse();
        } catch (IOException ex) {
            log.info(ex);
        }
        
        return records;
    }
    
    private Result saveUserRecords(List<T> records) {
        Result result = null;
        try {
            writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_USERS));
            beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(records);
            writer.close();
            result = new Result(ResultType.SUCCESS.ordinal(), null);
        } catch (IOException ex) {
            log.info(ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            log.info(ex);
        } catch (CsvDataTypeMismatchException ex) {
            log.info(ex);
        }
        
        return result;
    }
    
    private Result getUserRecordById(int id) {
        records = getAllUserRecords();
        User user = (User) records.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
        return user != null ? new Result(ResultType.SUCCESS.ordinal(), user) : 
                new Result(ResultType.NOT_FOUND.ordinal());
    }
    
    private Result deleteUserRecord(int id) {
        records = getAllUserRecords();
        records = records.stream().filter(item -> item.getId() != id)
                                           .collect(Collectors.toList());
        Result result = saveUserRecords(records);
        return result;
    }
    
    private Result updateUserRecord(T bean) {
        records = getAllUserRecords();
        records.stream().forEach(item -> {
            if (item.getId() == bean.getId()) {
                User t = (User) item;
                t.updateWith((User) bean);
                item = (T) t;
            }
        });
        Result result = saveUserRecords(records);
        result.setBean(bean);
        return result;
    }
    
    private List<T> getAllPostRecords() {
        records = null;
        try {
            reader = new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_POSTS));
            CsvToBean csvToBean = new CsvToBeanBuilder(reader).withType(Post.class).build();
            records = csvToBean.parse();
        } catch (IOException ex) {
            log.info(ex);
        }
        
        return records;
    }
    
    private Result savePostRecords(List<T> records) {
        Result result = null;
        
        try {
            writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_POSTS));
            beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(records);
            writer.close();
            result = new Result(ResultType.SUCCESS.ordinal());
        } catch (IOException ex) {
            log.info(ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            log.info(ex);
        } catch (CsvDataTypeMismatchException ex) {
            log.info(ex);
        }
        return result;
    }
    
    private Result getPostRecordById(int id) {
        records = getAllPostRecords();
        Post post = (Post) records.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
        return post != null ? new Result(ResultType.SUCCESS.ordinal(), post) : 
                new Result(ResultType.NOT_FOUND.ordinal());
    }
    
    private Result deletePostRecord(int id) {
        records = getAllPostRecords();
        records = records.stream().filter(item -> item.getId() != id)
                                           .collect(Collectors.toList());
        Result result = savePostRecords(records);
        return result;
    }
    
    private Result updatePostRecord(T bean) {
        records = getAllPostRecords();
        records.stream().forEach(item -> {
            if (item.getId() == bean.getId()) {
                Post t = (Post) item;
                t.updateWith((Post) bean);
                item = (T) t;
            }
        });
        Result result = savePostRecords(records);
        result.setBean(bean);
        return result;
    }
    
    private List<T> getAllCommentRecords() {
        records = null;
        try {
            reader = new FileReader(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_COMMENTS));
            CsvToBean csvToBean = new CsvToBeanBuilder(reader).withType(Comment.class).build();
            records = csvToBean.parse();
        } catch (IOException ex) {
            log.info(ex);
        }
        
        return records;
    }
    
    private Result saveCommentRecords(List<T> records) {
        Result result = null;
        try {
            writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_COMMENTS));
            beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(records);
            writer.close();
            result = new Result(ResultType.SUCCESS.ordinal(), null);
        } catch (IOException ex) {
            log.info(ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            log.info(ex);
        } catch (CsvDataTypeMismatchException ex) {
            log.info(ex);
        }
        
        return result;
    }
    
    private Result getCommentRecordById(int id) {
        records = getAllCommentRecords();
        Comment comment = (Comment) records.stream().filter(item -> item.getId() == id).findFirst().orElse(null);
        return comment != null ? new Result(ResultType.SUCCESS.ordinal(), comment) : 
                new Result(ResultType.NOT_FOUND.ordinal());
        
    }
    
    private Result deleteCommentRecord(int id) {
        records = getAllCommentRecords();
        records = records.stream().filter(item -> item.getId() != id)
                                           .collect(Collectors.toList());
        Result result = saveCommentRecords(records);
        return result;
    }
    
    private Result updateCommentRecord(T bean) {
        records = getAllCommentRecords();
        records.stream().forEach(item -> {
            if (item.getId() == bean.getId()) {
                Comment t = (Comment) item;
                t.updateWith((Comment) bean);
                item = (T) t;
            }
        });
        Result result = saveCommentRecords(records);
        result.setBean(bean);
        return result;
    }
    
    private int getLastRecordId(List<T> records) {
        return records.size() != 0 ? records.get(records.size() - 1).getId() + 1 : 1;
    }
    
    @Override
    public Result saveRecord(T bean, EntityType type) {
        Result result = null;
        switch(type) {
            case USER:
                records = getAllUserRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                result = saveUserRecords(records);
                break;
            case POST:
                records = getAllPostRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                result = savePostRecords(records);
                break;
            case COMMENT:
                records = getAllCommentRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                result = saveCommentRecords(records);
                break;
        }
        if (result.getStatus() == ResultType.SUCCESS.ordinal()) 
            result.setBean(bean);
        
        return result;
    }

    @Override
    public Result deleteRecord(int id, EntityType type) {
        Result result = getRecordById(id, type);
        
        if (result.getStatus() == ResultType.NOT_FOUND.ordinal())
            return result;
        
        switch(type) {
            case USER:
                return deleteUserRecord(id);
            case POST:
                return deletePostRecord(id);
            case COMMENT:
                return deleteCommentRecord(id);
        }
        result.setStatus(ResultType.FAILURE.ordinal());
        return result;
    }

    @Override
    public Result getRecordById(int id, EntityType type) {
        Result result = null;
        switch(type) {
            case USER:
                return getUserRecordById(id);
            case POST:
                return getPostRecordById(id);
            case COMMENT:
                return getCommentRecordById(id);
        }
        result.setStatus(ResultType.FAILURE.ordinal());
        return result;
    }
    
    @Override
    public Result updateRecord(T bean, EntityType type) {
        Result result = getRecordById(bean.getId(), type);
        
        if (result.getStatus() == ResultType.NOT_FOUND.ordinal())
            return result;
        
        switch(type) {
            case USER:
                return updateUserRecord(bean);
            case POST:
                return updatePostRecord(bean);
            case COMMENT:
                return updateCommentRecord(bean);
        }
        result.setStatus(ResultType.FAILURE.ordinal());
        return result;
    }
    
    @Override
    public List<T> getAllRecords(EntityType type) {
        T record = null;
        switch(type) {
            case USER:
                records = getAllUserRecords();
                break;
            case POST:
                records = getAllPostRecords();
                break;
            case COMMENT:
                records = getAllCommentRecords();
                break;
        }
        return records;
    }

    @Override
    public void initDataSource() {}
    
}
