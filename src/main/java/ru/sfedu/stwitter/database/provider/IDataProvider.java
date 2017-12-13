/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.sfedu.stwitter.database.provider;
import ru.sfedu.stwitter.database.entites.*;
import java.util.List;

/**
 *
 * @author daniel
 */
public abstract interface IDataProvider<T> { 
    public Result saveRecord(T bean, EntityType type);
    public Result deleteRecord(T bean, EntityType type);
    public Result updateRecord(T bean, EntityType type);
    public Result getRecordById(int id, EntityType type);
    public List<T> getAllRecords(EntityType type);
    public void initDataSource(EntityType type);
}