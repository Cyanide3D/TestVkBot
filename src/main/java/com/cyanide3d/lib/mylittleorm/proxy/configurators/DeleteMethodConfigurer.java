package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.lib.mylittleorm.handler.DatabaseProvider;

import java.lang.reflect.Method;

public class DeleteMethodConfigurer implements MethodConfigurer{

    private final DatabaseProvider dao;

    public DeleteMethodConfigurer(DatabaseProvider dao) {
        this.dao = dao;
    }

    @Override
    public Object configure(Method method, Object[] args, Class<?> clazz) {
        if (method.getName().equalsIgnoreCase("delete")) {
            dao.delete(args[0]);
            return true;
        }

        return null;
    }
}
