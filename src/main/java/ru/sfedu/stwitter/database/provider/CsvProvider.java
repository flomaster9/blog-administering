/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    private void saveUserRecords(List<T> records) {
        try {
            writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_USERS));
            beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(records);
            writer.close();
        } catch (IOException ex) {
            log.info(ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            log.info(ex);
        } catch (CsvDataTypeMismatchException ex) {
            log.info(ex);
        }
    }
    
    private T getUserRecordById(int id) {
        records = getAllUserRecords();
        return records.stream().filter(item -> item.getId() == id).findFirst().get();
    }
    
    private void deleteUserRecord(T bean) {
        records = getAllUserRecords();
        records = records.stream().filter(item -> item.getId() != bean.getId())
                                           .collect(Collectors.toList());
        saveUserRecords(records);
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
    
    private void savePostRecords(List<T> records) {
        try {
            writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_POSTS));
            beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(records);
            writer.close();
        } catch (IOException ex) {
            log.info(ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            log.info(ex);
        } catch (CsvDataTypeMismatchException ex) {
            log.info(ex);
        }
    }
    
    private T getPostRecordById(int id) {
        records = getAllPostRecords();
        return records.stream().filter(item -> item.getId() == id).findFirst().get();
    }
    
    private void deletePostRecord(T bean) {
        records = getAllPostRecords();
        records = records.stream().filter(item -> item.getId() != bean.getId())
                                           .collect(Collectors.toList());
        savePostRecords(records);
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
    
    private void saveCommentRecords(List<T> records) {
        try {
            writer = new FileWriter(ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH_COMMENTS));
            beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            beanToCsv.write(records);
            writer.close();
        } catch (IOException ex) {
            log.info(ex);
        } catch (CsvRequiredFieldEmptyException ex) {
            log.info(ex);
        } catch (CsvDataTypeMismatchException ex) {
            log.info(ex);
        }
    }
    
    private T getCommentRecordById(int id) {
        records = getAllCommentRecords();
        return records.stream().filter(item -> item.getId() == id).findFirst().get();
    }
    
    private void deleteCommentRecord(T bean) {
        records = getAllCommentRecords();
        records = records.stream().filter(item -> item.getId() != bean.getId())
                                           .collect(Collectors.toList());
        saveCommentRecords(records);
    }
    
    private int getLastRecordId(List<T> records) {
        return records.size() != 0 ? records.get(records.size() - 1).getId() + 1 : 1;
    }
    
    @Override
    public void saveRecord(T bean, EntityType type) {
        switch(type) {
            case USER:
                records = getAllUserRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                saveUserRecords(records);
                break;
            case POST:
                records = getAllPostRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                savePostRecords(records);
                break;
            case COMMENT:
                records = getAllCommentRecords();
                bean.setId(getLastRecordId(records));
                records.add(bean);
                saveCommentRecords(records);
                break;
        }
    }

    @Override
    public void deleteRecord(T bean, EntityType type) {
        switch(type) {
            case USER:
                deleteUserRecord(bean);
                break;
            case POST:
                deletePostRecord(bean);
                break;
            case COMMENT:
                deleteCommentRecord(bean);
                break;
        }
    }

    @Override
    public T getRecordById(int id, EntityType type) {
        T record = null;
        switch(type) {
            case USER:
                record = getUserRecordById(id);
                break;
            case POST:
                record = getPostRecordById(id);
                break;
            case COMMENT:
                record = getCommentRecordById(id);
                break;
        }
        return record;
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
