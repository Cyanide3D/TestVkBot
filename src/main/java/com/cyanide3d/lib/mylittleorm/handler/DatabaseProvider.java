package com.cyanide3d.lib.mylittleorm.handler;

import java.util.List;

public interface DatabaseProvider {

    <T> List<T> findByField(String field, Class<?> clazz, Object arg);
    <T> List<T> findAll(Class<?> clazz);
    void createTableIfNotExist(Class<?> clazz);
    void saveOrUpdate(Object entity);

}
