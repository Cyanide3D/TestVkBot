package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.query.CreateTableQueryExtractor;
import com.cyanide3d.lib.mylittleorm.utils.PrimaryKeyUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateTableQueryExtractorImpl implements CreateTableQueryExtractor {

    @Override
    public String extract(Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        return "CREATE TABLE IF NOT EXISTS " + tableName + " ( " + columns(clazz) + " );";
    }

    private String columns(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String result = PrimaryKeyUtils.getPrimaryKey(clazz) + " INTEGER PRIMARY KEY AUTOINCREMENT,";

        result += Arrays.stream(fields)
                .filter(field -> !PrimaryKeyUtils.isPrimaryKey(field))
                .map(field -> field.getName() + " varchar(255)")
                .collect(Collectors.joining(","));

        return result;
    }

}
