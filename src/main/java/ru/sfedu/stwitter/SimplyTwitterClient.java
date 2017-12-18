package ru.sfedu.stwitter;

import java.util.List;
import java.util.Scanner;
import ru.sfedu.stwitter.database.entites.*;
import ru.sfedu.stwitter.database.provider.*;

/**
 *
 * @author daniel
 */
public class SimplyTwitterClient {
    static Scanner scanner = new Scanner(System.in);
    static String choose;
    static IDataProvider dataProvider = null;
    static Result result = null;

    public static void main(String args[]) {
        while (true) {
            choose = scanner.nextLine();
            String[] command = choose.split(" ");

            switch(command[0]) {
                case "use":
                    choseDataSourceByParam(command[1]);
                    if (dataProvider != null) System.out.println(dataProvider.toString());
                    break;
                case "get_all":
                    printRecordsByParam(command[1]);
                    break;
                case "get_one":
                    printRecordById(command[1], command[2]);
                    break;
                case "create":
                    break;
                case "update":
                    break;
                case "delete":
                    break;
                case "q":
                    return;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private static boolean isDataProviderEmpty() {
        if (dataProvider == null)
            System.out.println("ERROR: Uninitialize data provider");
        return dataProvider == null;
    }
    
    private static void choseDataSourceByParam(String param) {      
        switch(param) {
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
        }
    }
    
    private static void printRecordsByParam(String type) {
        if (isDataProviderEmpty()) return;
       
        List list = null;
        switch(type) {
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
        }
        list.stream().forEach(item -> System.out.println(item));
    }
    
    private static void printRecordById(String type, String stringId) {
        if (isDataProviderEmpty()) return;
        int id = Integer.parseInt(stringId);
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
}

