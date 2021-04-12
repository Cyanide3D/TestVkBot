package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.lib.mylittleorm.database.DatabaseStore;

import java.lang.reflect.Method;

public class FindAllMethodConfigurer implements MethodConfigurer {

    private final DatabaseStore dao;

    public FindAllMethodConfigurer(DatabaseStore dao) {
        this.dao = dao;
    }

    @Override
    public Object configure(Method method, Object[] args, Class<?> clazz) {
        if (method.getName().equalsIgnoreCase("findAll")) {
            return dao.findAll(clazz);
        }
        return null;
    }
}
