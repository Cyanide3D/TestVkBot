package com.cyanide3d.dao;

import com.cyanide3d.lib.annotations.InjectObject;
import com.cyanide3d.lib.annotations.Singleton;
import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;

import java.util.List;

@Singleton
public class ObjectDao {
    @InjectObject
    DaoRequestInvocationHandler daoRequestInvocationHandler;

    public void save(Object object) {
        daoRequestInvocationHandler.save(object);
    }

    public <T> T findObjectByPrimaryKey(int id, T obj) {
        return daoRequestInvocationHandler.findObjectByPrimaryKey(id, obj);
    }

    public <T> List<T> findObjectByField(Object pattern, String field, String param){
        return daoRequestInvocationHandler.findByField(pattern, field, param);
    }
}
