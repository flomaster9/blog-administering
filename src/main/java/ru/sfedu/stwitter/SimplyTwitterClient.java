package ru.sfedu.stwitter;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import ru.sfedu.stwitter.database.entites.*;
import ru.sfedu.stwitter.database.provider.*;
import ru.sfedu.stwitter.utils.ConfigurationUtil;

/**
 *
 * @author daniel
 */
public class SimplyTwitterClient {
    static Scanner scanner = new Scanner(System.in);
    static String choose;
    static IDataProvider dataProvider = null;
    static Result result = null;
    static String[] params;
    static String[] subparams;
    static String action;

    public static void main(String args[]) {
        String path = System.getProperty("PATH"); 
        if(path != null){ 
            ConfigurationUtil.setDefaultPath(System.getProperty("PATH")); 
        }
        while (true) {
            choose = scanner.nextLine();
            
            if (!setUpParams(choose)) continue;
            
            action = params[0];
            
            switch(action) {
                case "help":
                    help();
                    break;
                case "use":
                    choseDataSourceByParam(params);
                    if (dataProvider != null) System.out.println(dataProvider.toString());
                    break;
                case "get":
                    if (subparams == null)
                        printRecordsByParam(params);
                    else
                        printRecordById(params, subparams);
                    break;
                case "create":
                    createRecordByParams(params, subparams);
                    break;
                case "update":
                    updateRecordByParams(params, subparams);
                    break;
                case "delete":
                    deleteRecordById(params, subparams);
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }
    
    private static void help() {
        System.out.println("___HELP___");
        System.out.println("@ To set dataprovider use:  'use xml|csv|sql'");
        System.out.println("@ To get all entities use:  'get entities'");
        System.out.println("@ To get entity use:        'get entity: id'");
        System.out.println("@ To create entity use:     'create entity: params[,]'");
        System.out.println("@ To update entity use:     'update entity: params[,]'");
        System.out.println("@ To delete entity use:     'delete entity: id'");
        System.out.println("@ To exit use:              'q'");
    }
    
    private static boolean setUpParams(String choose) {
        
        if (choose.split(":").length == 2) {
            params = choose.split(":")[0].split(" ");
            subparams = choose.split(":")[1].split(",");
        } else if (choose.split(" ").length <= 2) {
            params = choose.split(" ");
            subparams = null;
        } else {
            System.out.println("Invalid params, try again");
            return false;
        }
            
        if (params != null)
            params = Arrays.stream(params).map(String::trim).toArray(String[]::new);
        if (subparams != null)
            subparams = Arrays.stream(subparams).map(String::trim).toArray(String[]::new);

        return true;
    }

    private static boolean isDataProviderEmpty() {
        if (dataProvider == null)
            System.out.println("ERROR: Uninitialize data provider");
        return dataProvider == null;
    }

    private static boolean isIncorrectArgumentsLength(String[] params, int length) {
        if (params.length != length)
            System.out.println("ERROR: Not enough arguments, try again");
        return params.length != length;
    }
    
    private static void choseDataSourceByParam(String[] params) {
        if (isIncorrectArgumentsLength(params, 2)) return;
        switch(params[1]) {
            case "csv":
                dataProvider = new CsvProvider();
                break;
            case "xml":
                dataProvider = new XmlProvider();
                break;
            case "sql":
                dataProvider = new PsqlProvider();
                break;
            default: 
                System.out.println("Invalid command");
                return;
        }
    }
    
    private static void printRecordsByParam(String params[]) {
        if (isDataProviderEmpty() || isIncorrectArgumentsLength(params, 2)) return;
       
        List list = null;
        switch(params[1]) {
            case "users":
                list = dataProvider.getAllRecords(EntityType.USER);
                break;
            case "posts":
                list = dataProvider.getAllRecords(EntityType.POST);
                break;
            case "comments":
                list = dataProvider.getAllRecords(EntityType.COMMENT);
                break;
            default:
                System.out.println("Invalid entity");
                return;
        }
        
        list.stream().forEach(item -> System.out.println(item));
    }
    
    private static void printRecordById(String params[], String subparams[]) {
        if (isDataProviderEmpty() || isIncorrectArgumentsLength(subparams, 1)) return;

        String type = params[1];
        int id = Integer.parseInt(subparams[0]);
        Result result;
        
        switch(type) {
            case "user":
                result = dataProvider.getRecordById(id, EntityType.USER);
                break;
            case "post":
                result = dataProvider.getRecordById(id, EntityType.POST);
                break;
            case "comment":
                result = dataProvider.getRecordById(id, EntityType.COMMENT);
                break;
            default:
                System.out.println("Invalid entity");
                return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            System.out.println(result.getBean());
        else if (result.getStatus() == ResultType.NOT_FOUND.ordinal())
            System.out.println("Not Found " + type + " with id " + id);
        else
            System.out.println("Failure with status: " + result.getStatus());
    }

    private static void createRecordByParams(String[] params, String[] subparams) {
        if (isDataProviderEmpty()) return;
        String entity = params[1];
        int userId = 0;
        int postId = 0;
        switch(entity) {
            case "user": {
                if (isIncorrectArgumentsLength(subparams, 2)) return;
                String login = subparams[0];
                String name = subparams[1];
                
                User user = new User(login, name);
                result = dataProvider.saveRecord(user, EntityType.USER);
                break;
            }
            case "post": {
                if (isIncorrectArgumentsLength(subparams, 3)) return;
                userId = Integer.parseInt(subparams[0]);
                String title = subparams[1];
                String content = subparams[2];
                
                Post post = new Post(userId, title, content);
                result = dataProvider.saveRecord(post, EntityType.POST);
                break;
            }
            case "comment": {
                if (isIncorrectArgumentsLength(subparams, 3)) return;
                postId = Integer.parseInt(subparams[0]);
                userId = Integer.parseInt(subparams[1]);
                String content = subparams[2];
                
                Comment comment = new Comment(postId, userId, content);
                result = dataProvider.saveRecord(comment, EntityType.COMMENT);
                break;
            }
            default:
                System.out.println("Invalid entity");
                return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            System.out.println(entity + " with id: " + result.getBean().getId() + " was successfully created");
        else if (result.getStatus() == ResultType.USER_NOT_EXIST.ordinal())
            System.out.println("Not Found user with id " + userId);
        else if (result.getStatus() == ResultType.POST_NOT_EXIST.ordinal())
            System.out.println("Not Found post with id " + postId);
        else
            System.out.println("Failure with status: " + result.getStatus());
    }
    
    private static void updateRecordByParams(String[] params, String[] subparams) {
        if (isDataProviderEmpty()) return;
        String entity = params[1];
        Result result;
        int userId = 0;
        int postId = 0;
        int commentId = 0;
        switch(entity) {
            case "user": {
                if (isIncorrectArgumentsLength(subparams, 3)) return;
                
                userId = Integer.parseInt(subparams[0]);
                String login = subparams[1];
                String name = subparams[2];
                
                result = dataProvider.getRecordById(userId, EntityType.USER);
                if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
                    System.out.print("Not found user with id " + userId);
                    return;
                }
                
                User user = (User) result.getBean();
                user.setName(name);
                user.setLogin(login);

                dataProvider.updateRecord(user, EntityType.USER);
                break;
            }
            case "post": {
                if (isIncorrectArgumentsLength(subparams, 3)) return;
                
                postId = Integer.parseInt(subparams[0]);
                String title = subparams[1];
                String content = subparams[2];
                
                result = dataProvider.getRecordById(postId, EntityType.POST);
                if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
                    System.out.print("Not found post with id " + postId);
                    return;
                }
                
                Post post = (Post) result.getBean();
                post.setTitle(title);
                post.setContent(content);

                dataProvider.updateRecord(post, EntityType.POST);
                break;
            }
            case "comment":
                if (isIncorrectArgumentsLength(subparams, 2)) return;
                
                commentId = Integer.parseInt(subparams[0]);
                String content = subparams[1];
                
                result = dataProvider.getRecordById(commentId, EntityType.COMMENT);
                if (result.getStatus() == ResultType.NOT_FOUND.ordinal()) {
                    System.out.print("Not found comment with id " + commentId);
                    return;
                }
                
                Comment comment = (Comment) result.getBean();
                comment.setContent(content);

                dataProvider.updateRecord(comment, EntityType.COMMENT);
                break;
            default:
                System.out.println("Invalid entity");
                return;
        }
        
        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            System.out.println(entity + " with id: " + result.getBean().getId() + " was successfully updated");
        else
            System.out.println("Failure with status: " + result.getStatus());
    }

    private static void deleteRecordById(String params[], String subparams[]) {
        if (isDataProviderEmpty() || isIncorrectArgumentsLength(subparams, 1)) return;

        String type = params[1];
        int id = Integer.parseInt(subparams[0]);
        Result result;

        switch(type) {
            case "user":
                result = dataProvider.deleteRecord(id, EntityType.USER);
                break;
            case "post":
                result = dataProvider.deleteRecord(id, EntityType.POST);
                break;
            case "comment":
                result = dataProvider.deleteRecord(id, EntityType.COMMENT);
                break;
            default:
                System.out.println("Invalid entity");
                return;
        }

        if (result.getStatus() == ResultType.SUCCESS.ordinal())
            System.out.println(type + " with id " + id + " was successfully deleted");
        else if (result.getStatus() == ResultType.NOT_FOUND.ordinal())
            System.out.println("Not Found " + type + " with id " + id);
        else
            System.out.println("Failure with status: " + result.getStatus());
    }
}
