package ru.sfedu.stwitter.database.entites;

import java.util.ArrayList;
import org.simpleframework.xml.ElementList;
import java.util.List;
import org.simpleframework.xml.Root;

/**
 *
 * @author daniel
 */
@Root(name = "userList")
public class XmlBeanList {
    @ElementList(inline = true)
    private List<User> list = new ArrayList<>();

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }    
}
