/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.database;


import java.util.List;
import org.apache.log4j.Logger;
import ru.sfedu.stwitter.database.entites.*;
import ru.sfedu.stwitter.database.provider.*;

/**
 *
 * @author daniel
 */
public class Clean {
    protected static Logger log = Logger.getLogger(Clean.class);
    private static IDataProvider dataProvider = null;
    private static Result result = null;

    public static void main(String args[]) {
        log.info("_____CLEAN_____");
        
        log.info("_____SQL provider start_____");
        dataProvider = new PsqlProvider();
        clean(dataProvider);
        log.info("_____SQL provider end_____");
        
        log.info("_____CSV provider start_____");
        dataProvider = new CsvProvider();
        clean(dataProvider);
        log.info("_____CSV provider end_____");
        
        log.info("_____XML provider start_____");
        dataProvider = new XmlProvider();
        clean(dataProvider);
        log.info("_____XML provider end_____");
        
        log.info("-----CLEAN------");
    }
    
    public static void clean(IDataProvider dataProvider) {
        List<User> users = dataProvider.getAllRecords(EntityType.USER);
        users.stream().forEach(user -> {
            result = dataProvider.deleteRecord(user.getId(), EntityType.USER);

            if (result.getStatus() == ResultType.SUCCESS.ordinal())
                log.info("User with id " + user.getId() + " was deleted");
            else if (result.getStatus() == ResultType.NOT_FOUND.ordinal())
                log.info("User with id " + user.getId() + " could not exist");
            else 
                log.info("Failure with status " + result.getStatus());
        });
    }
}
