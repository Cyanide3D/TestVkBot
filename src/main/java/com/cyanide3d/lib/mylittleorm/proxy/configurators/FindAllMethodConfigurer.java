package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.lib.mylittleorm.handler.DatabaseProvider;

import java.lang.reflect.Method;

public class FindAllMethodConfigurer implements MethodConfigurer {

    private final DatabaseProvider dao;

    public FindAllMethodConfigurer(DatabaseProvider dao) {
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
