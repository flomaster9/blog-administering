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

@Root(name="user")
public class User extends WithId {
//    @CsvBindByPosition(position = 0)
//    private int id;
    @CsvBindByName
    private String login;
    @CsvBindByName
    private String name;

    public User() {
        super(EntityType.USER);
    }

    public User(String login, String name) {
        super(EntityType.USER);

        this.login = login;
        this.name = name;
    }
    
    @Attribute(name="Login")
    public String getLogin() {
        return this.login;
    }
    
    @Attribute(name="Login")
    public void setLogin(String login) {
        this.login = login;
    }
    
    @Attribute(name="Name")
    public String getName() {
        return this.name;
    }

    @Attribute(name="Name")
    public void setName(String name) {
        this.name = name;
    }
    
    public void updateWith(User object) {
        this.setLogin(object.getLogin());
        this.setName(object.getName());
    }
    
    @Override
    public String toInsert() {
        return "'" + login + "', '" + name + "'";
    }
    
    @Override
    public String toString() {
        return "id: " + getId() + ", login: " + login + ", name: " + name;
    }
}
       
