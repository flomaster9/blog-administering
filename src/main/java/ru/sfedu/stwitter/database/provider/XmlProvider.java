/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.database.provider;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import ru.sfedu.stwitter.database.entites.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.stwitter.utils.ConfigurationUtil;

/**
 *
 * @author daniel
 */
public class XmlProvider<T extends WithId> implements IDataProvider {
    
    protected static Logger log = Logger.getLogger(XmlProvider.class);
    private Serializer serializer;

    private String users_path;
    private String projects_path;
    private String tasks_path;
    private File file;
    
    @Override
    public void initDataSource() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result saveRecord(Object bean, EntityType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result deleteRecord(int id, EntityType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Result updateRecord(Object bean, EntityType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result getRecordById(int id, EntityType type){
        try {
            File source = new File("src/main/resources/xml_beans/users.xml");
            XmlBeanList userList = serializer.read(XmlBeanList.class, source);
            User user = userList.getList().stream().filter(t -> Objects.equals(t.getId(), id)).findFirst().orElse(null);
            log.info(user);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }        
        return new Result(ResultType.FAILURE.ordinal());
    }
    
    @Override
    public List<T> getAllRecords(EntityType type) {
        List<T> records = null;
        return records;
    }
    
    protected void initFile(EntityType type) throws IllegalArgumentException{
            switch (type) {
                case USER:
                    file = new File("users.xml");
                    break;
//                case POST:
//                    file = new File(users_path);
//                    break;
//                case COMMENT:
//                    file = new File(projects_path);
//                    break;
                default:
                    log.info("Wrong entity type");
                    throw new IllegalArgumentException("Wrong entity type");
            }
    }
}
