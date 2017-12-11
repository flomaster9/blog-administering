/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.database.provider;
import ru.sfedu.stwitter.database.entites.EntityType;
import java.util.List;

/**
 *
 * @author daniel
 */
public abstract interface IDataProvider<T> { 
    public void saveRecord(T bean, EntityType type);
    public void deleteRecord(T bean, EntityType type);
    public T getRecordById(int id, EntityType type);
    public List<T> getAllRecords(EntityType type);
    public void initDataSource(EntityType type);
}