package com.cyanide3d.lib.mylittleorm.utils;

import com.cyanide3d.Main;
import com.cyanide3d.lib.mylittleorm.database.DatabaseLayer;
import com.cyanide3d.lib.mylittleorm.query.SQLDialect;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    @SneakyThrows
    public static SQLDialect getSQLDialect() {
        return (SQLDialect) Class.forName(properties.getProperty("database_dialect")).getDeclaredConstructor().newInstance();
    }

    public static String getDatabaseUrl() {
        return properties.getProperty("database_url");
    }

    public static String getDatabaseUsername() {
        return properties.getProperty("database_username");
    }

    public static String getDatabasePassword() {
        return properties.getProperty("database_password");
    }

    @SneakyThrows
    public static DatabaseLayer getDatabaseLayer() {
        return (DatabaseLayer) Class.forName(properties.getProperty("database_layer")).getDeclaredConstructor().newInstance();
    }

    public static String getDatabaseDriverPath() {
        return properties.getProperty("database_driver");
    }

}
