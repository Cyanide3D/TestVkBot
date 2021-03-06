package com.cyanide3d.lib.mylittleorm.database;

import com.cyanide3d.lib.annotations.Property;
import com.cyanide3d.lib.mylittleorm.utils.PropertyUtils;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnectionLayer implements DatabaseLayer{
    private final Connection connection;

    @SneakyThrows
    public DatabaseConnectionLayer() {
        Class.forName(PropertyUtils.getDatabaseDriverPath());

        this.connection = DriverManager.getConnection(
                PropertyUtils.getDatabaseUrl(),
                PropertyUtils.getDatabaseUsername(),
                PropertyUtils.getDatabasePassword()
        );
    }

    @SneakyThrows
    @Override
    public <T> List<T> findAll(String query, Class<?> clazz) {
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        return makeEntityList(clazz, resultSet);
    }

    @SneakyThrows
    @Override
    public <T> List<T> findByField(String query, Object param, Class<?> clazz) {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, param);
        ResultSet resultSet = statement.executeQuery();
        return makeEntityList(clazz, resultSet);
    }

    @Override
    @SneakyThrows
    public void delete(String query, Object param) {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, param);
        statement.execute();
    }

    private <T> List<T> makeEntityList(Class<?> clazz, ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        Field[] fields = clazz.getDeclaredFields();
        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            Object o = clazz.getDeclaredConstructor().newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(o, resultSet.getObject(field.getName().toLowerCase()));
            }
            result.add((T) o);
        }
        return result;
    }

    @SneakyThrows
    @Override
    public void saveOrUpdate(String query, List<Object> params) {
        PreparedStatement statement = connection.prepareStatement(query);
        int index = 1;
        for (Object param : params) {
            statement.setObject(index++, param);
        }
        statement.executeUpdate();
        statement.close();
    }

    @SneakyThrows
    @Override
    public void createTable(String query) {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    @SneakyThrows
    public boolean entityHasPresent(String query, Object id) {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setObject(1, id);
        return preparedStatement.executeQuery().next();
    }
}
