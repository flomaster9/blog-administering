package ru.sfedu.stwitter.database.entites;

import org.simpleframework.xml.ElementList;
import java.util.List;
import org.simpleframework.xml.Root;

/**
 *
 * @author daniel
 */
@Root(name="beans")
public class XmlPostList {

    @ElementList(inline=true, name="user")
    private List<Post> beans;

    public XmlPostList(List<Post> beans) {
        this.beans = beans;
    }

    public XmlPostList() {}

    public List<Post> getBeans() {
        return beans;
    }

    public void setBeans(List<Post> beans) {
        this.beans = beans;
    }
}
