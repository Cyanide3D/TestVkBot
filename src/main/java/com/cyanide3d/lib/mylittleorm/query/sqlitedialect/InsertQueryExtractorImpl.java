package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.utils.PrimaryKeyUtils;
import com.cyanide3d.lib.mylittleorm.query.InsertQueryExtractor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertQueryExtractorImpl implements InsertQueryExtractor {

    private Field[] fields;

    @Override
    @SneakyThrows
    public String extract(Class<?> clazz) {
        fields = clazz.getDeclaredFields();
        String tableName = clazz.getSimpleName().toLowerCase();

        return "INSERT INTO " + tableName + " (" + columns(clazz) + ") VALUES (" + params() + ");";
    }

    private String params() {
        String params = StringUtils.repeat("?,", fields.length - 1);
        return params.substring(0, params.length() - 1);
    }

    private String columns(Class<?> clazz) {
        return Arrays.stream(fields)
                .filter(field -> isPrimaryKeyField(clazz, field))
                .map(field -> field.getName().toLowerCase())
                .collect(Collectors.joining(","));
    }

    private boolean isPrimaryKeyField(Class<?> clazz, Field field) {
        return !field.getName().equals(PrimaryKeyUtils.getPrimaryKey(clazz));
    }

}
