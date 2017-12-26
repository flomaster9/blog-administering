package ru.sfedu.stwitter.database.entites;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Attribute;

/**
 *
 * @author daniel
 */
public class WithId {
    @CsvBindByName
    private int id = 0;
    private EntityType type;

    public WithId(EntityType type) {
        this.type = type;
    }
    
    @Attribute(name="Id")
    public int getId() {
        return id;
    }
    
    @Attribute(name="Id")
    public void setId(int id) {
        this.id = id;
    }
    
    public EntityType getType() {
        return type;
    }
    
    public String toInsert(){
        return null;
    }
 }
