package com.cyanide3d.lib.mylittleorm.proxy;

import com.cyanide3d.Main;
import com.cyanide3d.exception.InvalidInterfaceMethodSignatureException;
import com.cyanide3d.lib.mylittleorm.database.DatabaseLayer;
import com.cyanide3d.lib.mylittleorm.handler.DaoRequestInvocationHandler;
import com.cyanide3d.lib.mylittleorm.handler.DatabaseProvider;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.FindAllMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.FindByFieldMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.MethodConfigurer;
import com.cyanide3d.lib.mylittleorm.proxy.configurators.SaveMethodConfigurer;
import com.cyanide3d.lib.mylittleorm.query.SQLDialect;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

public class NewMethodParserImpl implements MethodParser {
    private final DatabaseProvider dao;

    public NewMethodParserImpl() {
        dao = getDao();
    }

    @SneakyThrows
    private DatabaseProvider getDao() {
        Properties properties = new Properties();
        properties.load(Main.class.getResourceAsStream("/database.properties"));
        SQLDialect dialect = (SQLDialect) Class.forName(properties.getProperty("database_dialect")).getDeclaredConstructor().newInstance();
        DatabaseLayer dao = (DatabaseLayer) Class.forName(properties.getProperty("database_layer")).getDeclaredConstructor().newInstance();
        return new DaoRequestInvocationHandler(dialect, dao);
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
