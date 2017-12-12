/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.database.entites;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

/**
 *
 * @author daniel
 */
public class Comment implements WithId {
    @CsvBindByPosition(position = 0)
    private int id;
    @CsvBindByPosition(position = 1)
    private int postId;
    @CsvBindByPosition(position = 2)
    private int userId;
    @CsvBindByPosition(position = 3)
    private String content;
    
    public Comment(int postId, int userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }
    
    public Comment(){}
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPostId() {
        return this.postId;
    }
    
    public void setPostId(int userId) {
        this.postId = userId;
    }
    
    public int getUserId() {
        return this.postId;
    }
    
    public void setUserId(int userId) {
        this.postId = userId;
    }
        
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return this.id + " " + this.content + " ";
    }
}
