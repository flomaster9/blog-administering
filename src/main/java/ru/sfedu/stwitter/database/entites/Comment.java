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
public class Comment extends WithId {
//    @CsvBindByPosition(position = 0)
//    private int id;
    
    @CsvBindByName
    private int postId;
    @CsvBindByName
    private int userId;
    @Element(name = "content")
    @CsvBindByName
    private String content;
    
    public Comment(int postId, int userId, String content) {
        super(EntityType.COMMENT);
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }
    
    public Comment(){
        super(EntityType.COMMENT);
    }
    
//    @Attribute(name="Id")
//    public int getId() {
//        return this.id;
//    }
//    
//    @Attribute(name="Id")
//    public void setId(int id) {
//        this.id = id;
//    }
    
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
    public String toInsert() {
        return "'" + postId + "', '" + userId  + "', '" + content + "'";
    }
    
    @Override
    public String toString() {
        return "id: " + getId() + ", postId: " + postId + ", userId: " + userId + ", content: " + content;
    }
}
