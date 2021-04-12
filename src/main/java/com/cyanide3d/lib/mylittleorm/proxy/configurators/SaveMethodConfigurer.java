package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;

import java.lang.reflect.Method;

public class SaveMethodConfigurer implements MethodConfigurer{

    private final DaoRequestInvocationHandler daoRequestInvocationHandler;

    public SaveMethodConfigurer(DaoRequestInvocationHandler daoRequestInvocationHandler) {
        this.daoRequestInvocationHandler = daoRequestInvocationHandler;
    }

    @Override
    public Object configure(Method method, Object[] args, Class<?> clazz) {
        if (method.getName().equalsIgnoreCase("save")) {
            daoRequestInvocationHandler.saveOrUpdate(args[0]);
        }
        return null;
    }
}
