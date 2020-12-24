package com.cyanide3d.lib.mylittleorm.handler;

import com.cyanide3d.lib.mylittleorm.database.DatabaseConnectionLayer;
import com.cyanide3d.lib.mylittleorm.query.QueryType;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DaoRequestInvocationHandler {

    private final DatabaseConnectionLayer databaseConnectionLayer;
    private final ModelPrimaryKeyHandler modelPrimaryKeyHandler;
    private final QueryDeterminantHandler queryDeterminantHandler;



    public DaoRequestInvocationHandler() {
        this.databaseConnectionLayer = new DatabaseConnectionLayer();
        this.modelPrimaryKeyHandler = new ModelPrimaryKeyHandler();
        this.queryDeterminantHandler = new QueryDeterminantHandler();
    }





    public void save(Object object) {
        try {
            saveObjectHandler(object);
        } catch (Exception e) {
            saveObjectExceptionHandler(object);
        }
    }

    public <T> List<T> findByField(Object object, String field, String param){
        Map<String, List<Object>> qualifier = queryDeterminantHandler.execute(object, QueryType.LIST_OBJECT_BY, List.of(param + ":" + field));
        return databaseConnectionLayer.findByField(qualifier.keySet().iterator().next(), object);
    }

    @SneakyThrows
    public <T> T findObjectByPrimaryKey(int id, T obj) {
        return (T) getOldDatabaseObject(obj, id);
    }








    private void saveObjectHandler(Object object) {
        Object oldObject = getOldDatabaseObject(object, modelPrimaryKeyHandler.getValueOfPrimaryKey(object));
        int primaryKey = oldObject == null ? 0 : modelPrimaryKeyHandler.getValueOfPrimaryKey(oldObject);
        if (primaryKey == 0) {
            queryDeterminantHandler.execute(object, QueryType.INSERT, new ArrayList<>()).forEach(databaseConnectionLayer::save);
        } else {
            queryDeterminantHandler.execute(object, QueryType.UPDATE, new ArrayList<>()).forEach(databaseConnectionLayer::save);
        }
    }

    private Object getOldDatabaseObject(Object object, int id) {
        Map<String, List<Object>> testObjectQueryMap = queryDeterminantHandler.execute(object, QueryType.ONE_OBJECT, new ArrayList<>());
        String key = testObjectQueryMap.keySet().iterator().next();
        return databaseConnectionLayer.findByPrimaryKey(key, object, id);
    }

    private void saveObjectExceptionHandler(Object object) {
        databaseConnectionLayer.createTable(queryDeterminantHandler.getCreateTableQuery(object));
        save(object);
    }
}