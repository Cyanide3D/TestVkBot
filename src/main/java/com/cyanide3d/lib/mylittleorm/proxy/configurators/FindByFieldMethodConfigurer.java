package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import com.cyanide3d.exception.EntityNotFoundException;
import com.cyanide3d.lib.mylittleorm.handler.DatabaseProvider;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

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
        Class<?> returnClass = method.getReturnType();
        try {
            List<Object> entity = dao.findByField(findField, clazz, args[0]);
            if (returnClass.equals(List.class)) {
                return entity;
            } else if (returnClass.equals(Optional.class)) {
                return entity.isEmpty() ? Optional.empty() : Optional.ofNullable(entity.get(0));
            } else {
                return entity.get(0);
            }
        } catch (IndexOutOfBoundsException ex) {
            throw new EntityNotFoundException("Can't find entity by [" + findField + " = " + args[0] + "].");
        }
    }
}
