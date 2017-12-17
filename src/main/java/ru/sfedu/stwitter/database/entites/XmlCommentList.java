package ru.sfedu.stwitter.database.entites;

import org.simpleframework.xml.ElementList;
import java.util.List;
import org.simpleframework.xml.Root;

/**
 *
 * @author daniel
 */
@Root(name="beans")
public class XmlCommentList {

    @ElementList(inline=true, name="user")
    private List<Comment> beans;

    public XmlCommentList(List<Comment> beans) {
        this.beans = beans;
    }

    public XmlCommentList() {}

    public List<Comment> getBeans() {
        return beans;
    }

    public void setBeans(List<Comment> beans) {
        this.beans = beans;
    }
}
