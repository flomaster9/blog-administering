/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.utils;

import java.io.IOException;
import ru.sfedu.stwitter.database.entites.EntityType;
import ru.sfedu.stwitter.database.entites.*;
import com.google.gson.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import ru.sfedu.stwitter.controllers.UserController;

/**
 *
 * @author daniel
 */
public class JSON {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Logger log = Logger.getLogger(JSON.class);

    public static String stringifyStream(InputStream in) throws IOException {
        return IOUtils.toString(in, StandardCharsets.UTF_8);
    }
    
    public static <T> String stringify(T bean) throws IOException {
        return GSON.toJson(bean);
    }
    
    public static User parseUserStream(InputStream in) throws IOException {
        User user = null;
        String stringEntity = stringifyStream(in);
        user = GSON.fromJson(stringEntity, User.class);
        return user;
    }
    
    public static int parseIdStream(InputStream in) throws IOException {
        int id = -1;
        String stringEntity = stringifyStream(in);
        id = GSON.fromJson(stringEntity, Integer.class);
        return id;
    }
    
    public static Post parsePostStream(InputStream in) throws IOException {
        Post post = null;
        String stringEntity = stringifyStream(in);
        post = GSON.fromJson(stringEntity, Post.class);
        return post;
    }   
    
    public static Comment parseCommentStream(InputStream in) throws IOException {
        Comment comment = null;
        String stringEntity = stringifyStream(in);
        comment = GSON.fromJson(stringEntity, Comment.class);
        return comment;
    }
}
