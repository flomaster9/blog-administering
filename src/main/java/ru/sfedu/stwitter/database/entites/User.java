/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class User implements WithId {
    @CsvBindByPosition(position = 0)
    private int id;
    @CsvBindByPosition(position = 1)
    private String login;
    @CsvBindByPosition(position = 2)
    private String name;

    public User() {}

    public User(String login, String name) {
        this.login = login;
        this.name = name;
    }

    @Override
    @Attribute(name="Id")
    public int getId() {
        return this.id;
    }
    
    @Override
    @Attribute(name="Id")
    public void setId(int id) {
        this.id = id;
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
        return "id: " + id + ", login: " + login + ", name: " + name;
    }
}
       
