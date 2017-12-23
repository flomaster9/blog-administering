package ru.sfedu.stwitter.database.entites;

/**
 *
 * @author daniel
 */

public class Result <T extends WithId> {
    private int status;
    private T bean;
    
    public Result() {}
    
    public Result(int code) {
        status = code;
    }
    
    public Result(int code, T obj) {
        status = code;
        bean = obj;
    }
    
    public void setStatus(int code) {
        status = code;
    }
    
    public int getStatus() {
        return status;
    }
    
    public T getBean() {
        return bean;
    }
    
    public void setBean(T obj) {
        bean = obj;
    }
}
