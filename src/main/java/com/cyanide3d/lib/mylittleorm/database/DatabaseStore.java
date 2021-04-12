package com.cyanide3d.lib.mylittleorm.database;

import java.util.List;

public interface DatabaseStore {

    <T> List<T> findByField(String field, Class<?> clazz, Object arg);
    <T> List<T> findAll(Class<?> clazz);
    void createTableIfNotExist(Class<?> clazz);
    void saveOrUpdate(Object arg);


}
