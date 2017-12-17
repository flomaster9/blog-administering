package ru.sfedu.stwitter.database.entites;

import org.simpleframework.xml.ElementList;
import java.util.List;
import org.simpleframework.xml.Root;

/**
 *
 * @author daniel
 */
@Root(name="beans")
public class XmlUserList {

    @ElementList(inline=true, name="user")
    private List<User> beans;

    public XmlUserList(List<User> beans) {
        this.beans = beans;
    }

    public XmlUserList() {}

    public List<User> getBeans() {
        return beans;
    }

    public void setBeans(List<User> beans) {
        this.beans = beans;
    }
}
