package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.exception.UnsupportedReturnTypeException;
import com.cyanide3d.lib.mylittleorm.handler.DatabaseProvider;

import java.lang.reflect.Method;
import java.util.List;

public class FindAllMethodConfigurer implements MethodConfigurer {

    private final DatabaseProvider dao;

    public FindAllMethodConfigurer(DatabaseProvider dao) {
        this.dao = dao;
    }

    @Override
    public Object configure(Method method, Object[] args, Class<?> clazz) {
        if (!List.class.isAssignableFrom(method.getReturnType())) {
            throw new UnsupportedReturnTypeException("Method with name " + method.getName() + " work only with List.class return type.");
        }
        if (method.getName().equalsIgnoreCase("findAll")) {
            return dao.findAll(clazz);
        }
        return null;
    }
}
