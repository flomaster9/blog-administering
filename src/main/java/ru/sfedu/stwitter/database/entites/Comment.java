/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.database.entites;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author daniel
 */
@Root(name="comment")
public class Comment implements WithId {
    @CsvBindByPosition(position = 0)
    private int id;
    @CsvBindByPosition(position = 1)
    private int postId;
    @CsvBindByPosition(position = 2)
    private int userId;
    @Element(name = "content")
    @CsvBindByPosition(position = 3)
    private String content;
    
    public Comment(int postId, int userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }
    
    public Comment(){}
    
    @Attribute(name="Id")
    public int getId() {
        return this.id;
    }
    
    @Attribute(name="Id")
    public void setId(int id) {
        this.id = id;
    }
    
    @Attribute(name="PostId")
    public int getPostId() {
        return this.postId;
    }
    
    @Attribute(name="PostId")
    public void setPostId(int postId) {
        this.postId = postId;
    }
    
    @Attribute(name="UserId")
    public int getUserId() {
        return this.userId;
    }
    
    @Attribute(name="UserId")
    public void setUserId(int userId) {
        this.userId = userId;
    }
        
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public void updateWith(Comment object) {
        this.setContent(object.getContent());
    }
    
    @Override
    public String toString() {
        return "'" + postId + "', '" + userId  + "', '" + content + "'";
    }
}
