package ru.sfedu.stwitter.database.entites;

/**
 *
 * @author daniel
 */
public interface WithId {
    public int getId();
    public void setId(int id);
    public String toInsert();
 }
