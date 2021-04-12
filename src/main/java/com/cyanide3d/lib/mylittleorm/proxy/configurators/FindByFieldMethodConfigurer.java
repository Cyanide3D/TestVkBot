package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

public class FindByFieldMethodConfigurer implements MethodConfigurer {

    private final DaoRequestInvocationHandler daoRequestInvocationHandler;

    public FindByFieldMethodConfigurer(DaoRequestInvocationHandler daoRequestInvocationHandler) {
        this.daoRequestInvocationHandler = daoRequestInvocationHandler;
    }

    @Override
    public Object configure(Method method, Object[] args, Class<?> clazz) {
        String name = method.getName();
        if (StringUtils.startsWith(name, "find") && !name.equalsIgnoreCase("findAll")) {
            String findField = StringUtils.substringAfter(name, "findBy").toLowerCase();
            return isReturnTypeArray(method)
                    ? daoRequestInvocationHandler.findByField(findField, clazz, args[0])
                    : daoRequestInvocationHandler.findByField(findField, clazz, args[0]).get(0);
        }
        return null;
    }

    private boolean isReturnTypeArray(Method method) {
        return method.getReturnType().equals(List.class);
    }

}
