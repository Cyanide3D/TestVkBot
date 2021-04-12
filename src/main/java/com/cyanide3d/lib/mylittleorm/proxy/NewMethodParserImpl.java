package com.cyanide3d.lib.mylittleorm.proxy;

import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.FindAllMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.FindByFieldMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.MethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.SaveMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.query.sqlitedialect.SQLiteDialect;

import java.lang.reflect.Method;
import java.util.List;

public class NewMethodParserImpl implements MethodParser {
    private final DaoRequestInvocationHandler daoRequestInvocationHandler;

    public NewMethodParserImpl() {
        daoRequestInvocationHandler = new DaoRequestInvocationHandler(new SQLiteDialect());
    }

    @Override
    public Object parse(Method method, Object[] args, Class<?> clazz) {
        Object object = null;
        List<MethodConfigurer> methodConfigurers = List.of(
                new FindByFieldMethodConfigurer(daoRequestInvocationHandler),
                new FindAllMethodConfigurer(daoRequestInvocationHandler),
                new SaveMethodConfigurer(daoRequestInvocationHandler)
        );

        for (MethodConfigurer methodConfigurer : methodConfigurers) {
            Object configure = methodConfigurer.configure(method, args, clazz);
            if (configure != null) {
                object = configure;
            }
        }

        return object;
    }
}
