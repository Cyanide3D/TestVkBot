package com.cyanide3d.lib.mylittleorm.database;

import java.util.List;

public interface DatabaseLayer {

    <T> List<T> findAll(String query, Class<?> clazz);
    <T> List<T> findByField(String query, Object param, Class<?> clazz);
    void delete(String query, Object param);
    void saveOrUpdate(String query, List<Object> params);
    void createTable(String query);
    boolean entityHasPresent(String query, Object id);

}
