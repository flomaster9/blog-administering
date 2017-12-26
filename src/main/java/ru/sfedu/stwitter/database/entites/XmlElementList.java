package ru.sfedu.stwitter.database.entites;

import org.simpleframework.xml.ElementList;
import java.util.List;
import org.simpleframework.xml.Root;

/**
 *
 * @author daniel
 */
@Root(name="beans")
public class XmlElementList<T> {

    @ElementList(inline=true, name="beans")
    private List<T> beans;

    public XmlElementList(List<T> beans) {
        this.beans = beans;
    }

    public XmlElementList() {}

    public List<T> getBeans() {
        return beans;
    }

    public void setBeans(List<T> beans) {
        this.beans = beans;
    }
}
