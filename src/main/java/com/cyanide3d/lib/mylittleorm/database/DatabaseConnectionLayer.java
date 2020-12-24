package com.cyanide3d.lib.mylittleorm.database;

import com.cyanide3d.lib.mylittleorm.handler.ModelPrimaryKeyHandler;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO NEED REFACTOR NAHOOY

public class DatabaseConnectionLayer {
    ModelPrimaryKeyHandler modelPrimaryKeyHandler;
    String DB_URL = "jdbc:sqlite:testorm.db";
    String DB_USER = "root";
    String DB_PASSWORD = "root";
    Connection connection;

    @SneakyThrows
    public DatabaseConnectionLayer() {
        Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        modelPrimaryKeyHandler = new ModelPrimaryKeyHandler();
        this.connection = con;
    }

    @SneakyThrows
    public <T> List<T> findByField(String query, Object pattern) {
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(fillObject(pattern, resultSet));
        }
        return result;
    }

    @SneakyThrows
    public <T> T findByPrimaryKey(String query, Object pattern, int id) {
        T t = (T) pattern;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
             t = fillObject(pattern, resultSet);
        }
        resultSet.close();
        statement.close();
        return t;
    }

    @SneakyThrows
    public void save(String query, List<Object> params) {
        PreparedStatement statement = connection.prepareStatement(query);
        int index = 1;
        for (Object param : params) {
            statement.setObject(index++, param);
        }
        statement.executeUpdate();
        statement.close();
    }

    @SneakyThrows
    public void createTable(String query) {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
        statement.close();
    }




    @SneakyThrows
    private <T> T fillObject(Object pattern, ResultSet resultSet) {
        Class<?> clazz = pattern.getClass();
        T obj = (T) clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(obj, resultSet.getObject(field.getName()));
        }
        return obj;
    }
}
