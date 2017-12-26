package ru.sfedu.stwitter.database.entites;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author daniel
 */
@Root(name="post")
public class Post extends WithId {
//    @CsvBindByName
//    private int id;
    @CsvBindByName
    private int userId;
    @Element(name = "title")
    @CsvBindByName
    private String title;
    @Element(name = "content")
    @CsvBindByName
    private String content;
    
    public Post(int userId, String title, String content) {
        super(EntityType.POST);
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post() {
        super(EntityType.POST);
    }
    
    
//    @Override
//    @Attribute(name="Id")
//    public int getId() {
//        return this.id;
//    }
    
//    @Override
//    @Attribute(name="Id")
//    public void setId(int id) {
//        this.id = id;
//    }
    
    @Attribute(name="UserId")
    public int getUserId() {
        return this.userId;
    }
    
    @Attribute(name="UserId")
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public void updateWith(Post object) {
        this.setTitle(object.getTitle());
        this.setContent(object.getContent());
    }
    
    @Override
    public String toInsert() {
        return "'" + userId + "', '" + title + "', '" + content + "'";
    }
    
    @Override
    public String toString() {
        return "id: " + getId() + ", userId: " + userId + ", title: " + title + ", content: " + content;
    }
}
