package com.cyanide3d.lib.mylittleorm.proxy;

import com.cyanide3d.lib.mylittleorm.database.DatabaseConnectionLayer;
import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;
import com.cyanide3d.lib.mylittleorm.query.sqlitedialect.SQLiteDialect;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @deprecated use {@link NewMethodParserImpl} instead.
 */
public class MethodParserImpl implements MethodParser {

    private final DaoRequestInvocationHandler daoRequestInvocationHandler;

    public MethodParserImpl() {
        daoRequestInvocationHandler = new DaoRequestInvocationHandler(new SQLiteDialect(), new DatabaseConnectionLayer());
    }

    @Override
    public Object parse(Method method, Object[] args, Class<?> clazz) {
        String name = method.getName();
        if (name.equalsIgnoreCase("save")) {
            daoRequestInvocationHandler.saveOrUpdate(args[0]);
        } else if (StringUtils.startsWith(name, "find") && !name.equalsIgnoreCase("findAll")) {
            String findField = StringUtils.substringAfter(name, "findBy").toLowerCase();
            return isReturnTypeArray(method)
                    ? daoRequestInvocationHandler.findByField(findField, clazz, args[0])
                    : daoRequestInvocationHandler.findByField(findField, clazz, args[0]).get(0);
        } else if (name.equalsIgnoreCase("findAll")) {
            return daoRequestInvocationHandler.findAll(clazz);
        }

        return new Object();
    }

    private boolean isReturnTypeArray(Method method) {
        return method.getReturnType().equals(List.class);
    }

}
