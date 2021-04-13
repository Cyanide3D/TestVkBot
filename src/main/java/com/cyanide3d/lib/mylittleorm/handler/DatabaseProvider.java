package com.cyanide3d.lib.mylittleorm.handler;

import java.util.List;

public interface DatabaseProvider<T> {

    List<T> findByField(String field, Class<T> clazz, Object arg);
    List<T> findAll(Class<T> clazz);
    void createTableIfNotExist(Class<T> clazz);
    void saveOrUpdate(T entity);

}
