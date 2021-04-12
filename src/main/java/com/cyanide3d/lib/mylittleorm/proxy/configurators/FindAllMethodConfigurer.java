package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;

import java.lang.reflect.Method;

public class FindAllMethodConfigurer implements MethodConfigurer {

    private final DaoRequestInvocationHandler daoRequestInvocationHandler;

    public FindAllMethodConfigurer(DaoRequestInvocationHandler daoRequestInvocationHandler) {
        this.daoRequestInvocationHandler = daoRequestInvocationHandler;
    }

    @Override
    public Object configure(Method method, Object[] args, Class<?> clazz) {
        if (method.getName().equalsIgnoreCase("findAll")) {
            return daoRequestInvocationHandler.findAll(clazz);
        }
        return null;
    }
}
