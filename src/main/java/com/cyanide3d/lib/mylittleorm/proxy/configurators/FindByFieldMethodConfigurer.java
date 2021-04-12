package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.exception.EntityNotFoundException;
import com.cyanide3d.lib.mylittleorm.handler.DatabaseProvider;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

public class FindByFieldMethodConfigurer implements MethodConfigurer {

    private final DatabaseProvider dao;

    public FindByFieldMethodConfigurer(DatabaseProvider dao) {
        this.dao = dao;
    }

    @Override
    public Object configure(Method method, Object[] args, Class<?> clazz) {
        String name = method.getName();
        if (StringUtils.startsWith(name, "find") && !name.equalsIgnoreCase("findAll")) {
            return getObject(method, args, clazz, name);
        }
        return null;
    }

    private Object getObject(Method method, Object[] args, Class<?> clazz, String name) {
        String findField = StringUtils.substringAfter(name, "findBy").toLowerCase();
        try {
            return isReturnTypeArray(method)
                    ? dao.findByField(findField, clazz, args[0])
                    : dao.findByField(findField, clazz, args[0]).get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw new EntityNotFoundException("Can't find entity by [" + findField + " = " + args[0] + "].");
        }
    }

    private boolean isReturnTypeArray(Method method) {
        return method.getReturnType().equals(List.class);
    }

}
