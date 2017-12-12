/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.sfedu.stwitter.database.provider;
import java.sql.*;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import ru.sfedu.stwitter.database.entites.*;
import java.util.List;

/**
 *
 * @author daniel
 */
public class PsqlProvider<T extends WithId> implements IDataProvider {
    
    private static PsqlProvider instance;
    private static Logger log = Logger.getLogger(PsqlProvider.class);
    
    private Connection con = null;
    
    private String url = "jdbc:postgresql://localhost:5432/simply_twitter";
    private String login = "daniel";
    private String password = "111";
    
    private List<T> records;
    
    private PsqlProvider() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, login, password);
        } catch (Exception ex) {
            log.info("Error connection");
            log.info("Exception: " + ex);
        }
    }
    
    public void createStatement(String query) {
        if (con != null) {
            try {
                Statement statemant = con.createStatement();
                ResultSet result = statemant.executeQuery(query);
                while (result.next()) {
                    String str = result.getString("name");
                    log.info("String in row: " + str);
                }
            } catch (Exception ex) {
                log.info("Error in create statement: " + ex);
            }
        }
    }
    
    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception ex) {
                log.info("Error in close connection: " + ex);
            }
        }
    }
    
    public static PsqlProvider getInstance() {
        if (instance == null) {
            synchronized (PsqlProvider.class) {
               if (instance == null) {
                   instance = new PsqlProvider();
               } 
            }
        }
        return instance;
    }

    @Override
    public Result saveRecord(Object bean, EntityType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result deleteRecord(Object bean, EntityType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result getRecordById(int id, EntityType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        @Override
    public List<T> getAllRecords(EntityType type) {
        records = null;
        return records;
    }

    @Override
    public void initDataSource(EntityType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
