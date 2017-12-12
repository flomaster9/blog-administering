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
    
    private Result deleteUserRecord(T bean) {
        records = getAllUserRecords();
        records = records.stream().filter(item -> item.getId() != bean.getId())
                                           .collect(Collectors.toList());
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
    
    private Result deletePostRecord(T bean) {
        records = getAllPostRecords();
        records = records.stream().filter(item -> item.getId() != bean.getId())
                                           .collect(Collectors.toList());
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
    
    private Result deleteCommentRecord(T bean) {
        records = getAllCommentRecords();
        records = records.stream().filter(item -> item.getId() != bean.getId())
                                           .collect(Collectors.toList());
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
    public Result deleteRecord(T bean, EntityType type) {
        Result result = getRecordById(bean.getId(), type);
        
        if (result.getStatus() == ResultType.NOT_FOUND.ordinal())
            return result;
        
        switch(type) {
            case USER:
                result = deleteUserRecord(bean);
                break;
            case POST:
                result = deletePostRecord(bean);
                break;
            case COMMENT:
                result = deleteCommentRecord(bean);
                break;
        }
        return result;
    }

    @Override
    public Result getRecordById(int id, EntityType type) {
        Result result = null;
        switch(type) {
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
    public void initDataSource(EntityType type) {}
    
}
