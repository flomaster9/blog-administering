package ru.sfedu.stwitter;

import org.apache.log4j.Logger;
import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import ru.sfedu.stwitter.controllers.*;
import ru.sfedu.stwitter.Routes;

/**
 *
 * @author daniel
 */
public class SimplyTwitterServer {
    private static Logger log = Logger.getLogger(SimplyTwitterServer.class);
    
    public static void main(String[] args) {

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Constants.PORT), 100);
            
            //users resourse
            server.createContext(Routes.USER_PATH, new UserController.Index());
            server.createContext(Routes.USER_CREATE_PATH, new UserController.Create());
            server.createContext(Routes.USER_DESTROY_PATH, new UserController.Drop());
            
            //TODO: Add controllers for web UI
            
            server.start();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
 }
