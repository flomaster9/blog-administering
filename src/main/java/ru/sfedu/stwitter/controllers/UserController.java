/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.controllers;
import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import ru.sfedu.stwitter.database.entites.*;
import ru.sfedu.stwitter.database.provider.*;
import org.apache.log4j.Logger;
import com.google.gson.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import ru.sfedu.stwitter.utils.JSON;

/**
 *
 * @author daniel
 */
public class UserController {
    
    static CsvProvider <User> instanse = new CsvProvider();
    static List users;
    static User currentUser = new User();
    private static Logger log = Logger.getLogger(CsvProvider.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    public UserController() {};
    
    public static class Index implements HttpHandler {
        public void handle(HttpExchange ex) throws IOException {
            
            users = instanse.getAllRecords(EntityType.USER);
            String usersJson = JSON.stringify(users);
            
            ex.sendResponseHeaders(200, usersJson.getBytes().length);
            final OutputStream output = ex.getResponseBody();
            output.write(usersJson.getBytes());
            ex.close();
        }
    }

    public static class Create implements HttpHandler {
        public void handle(HttpExchange ex) throws IOException {
            if (!ex.getRequestMethod().equalsIgnoreCase("POST")) return;
            String payload = "ok";
           
            InputStream stream = ex.getRequestBody();
            User newUser = JSON.parseUserStream(stream);

            Result status = instanse.saveRecord(newUser, EntityType.USER);
            String responseJson = JSON.stringify(status);
            
            ex.sendResponseHeaders(200, responseJson.getBytes().length);
            final OutputStream output = ex.getResponseBody();
            output.write(responseJson.getBytes());
            ex.close();
        }
    }
    
    public static class Drop implements HttpHandler {
        public void handle(HttpExchange ex) throws IOException {
            if (!ex.getRequestMethod().equalsIgnoreCase("PUT")) return;
            String payload = "ok";
           
            InputStream stream = ex.getRequestBody();
            int id = JSON.parseIdStream(stream);
            Result result = instanse.getRecordById(id, EntityType.USER);

            if (result.getBean() == null) {
                result = new Result(ResultType.NOT_FOUND.ordinal());
            } else {
                result = instanse.deleteRecord(id, EntityType.USER);
            }
            
            String responseJson = JSON.stringify(result);
            
            ex.sendResponseHeaders(200, responseJson.getBytes().length);
            final OutputStream output = ex.getResponseBody();
            output.write(responseJson.getBytes());
            ex.close();
        }
    }
}
