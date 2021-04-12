package com.cyanide3d.lib.mylittleorm.proxy;

import com.cyanide3d.exception.InvalidInterfaceMethodSignatureException;
import com.cyanide3d.lib.mylittleorm.database.DatabaseConnectionLayer;
import com.cyanide3d.lib.mylittleorm.handler.DatabaseProvider;
import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.FindAllMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.FindByFieldMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.MethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.SaveMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.query.sqlitedialect.SQLiteDialect;

import java.lang.reflect.Method;
import java.util.List;

public class NewMethodParserImpl implements MethodParser {
    private final DatabaseProvider dao;

    public NewMethodParserImpl() {
        dao = new DaoRequestInvocationHandler(new SQLiteDialect(), new DatabaseConnectionLayer());
    }

    @Override
    public Object parse(Method method, Object[] args, Class<?> clazz) {
        List<MethodConfigurer> methodConfigurers = List.of(
                new FindByFieldMethodConfigurer(dao),
                new FindAllMethodConfigurer(dao),
                new SaveMethodConfigurer(dao)
        );

        for (MethodConfigurer methodConfigurer : methodConfigurers) {
            Object configure = methodConfigurer.configure(method, args, clazz);
            if (configure != null) {
                return configure;
            }
        }

        throw new InvalidInterfaceMethodSignatureException("Unsupported method signature.");
    }
}
