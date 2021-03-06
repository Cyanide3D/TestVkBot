package com.cyanide3d.lib.mylittleorm.proxy;

import com.cyanide3d.exception.InvalidInterfaceMethodSignatureException;
import com.cyanide3d.lib.mylittleorm.database.DatabaseLayer;
import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;
import com.cyanide3d.lib.mylittleorm.handler.DatabaseProvider;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.*;
import com.cyanide3d.lib.mylittleorm.query.SQLDialect;
import com.cyanide3d.lib.mylittleorm.utils.PropertyUtils;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.List;

public class NewMethodParserImpl implements MethodParser {
    private final DatabaseProvider dao;

    public NewMethodParserImpl() {
        dao = getDao();
    }

    @SneakyThrows
    private DatabaseProvider getDao() {
        SQLDialect dialect = PropertyUtils.getSQLDialect();
        DatabaseLayer dao = PropertyUtils.getDatabaseLayer();
        return new DaoRequestInvocationHandler(dialect, dao);
    }

    @Override
    public Object parse(Method method, Object[] args, Class<?> clazz) {
        List<MethodConfigurer> methodConfigurers = List.of(
                new FindByFieldMethodConfigurer(dao),
                new FindAllMethodConfigurer(dao),
                new SaveMethodConfigurer(dao),
                new DeleteMethodConfigurer(dao)
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
