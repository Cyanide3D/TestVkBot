package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;


import com.cyanide3d.lib.mylittleorm.utils.PrimaryKeyUtils;
import com.cyanide3d.lib.mylittleorm.query.UpdateQueryExtractor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UpdateQueryExtractorImpl implements UpdateQueryExtractor {

    private Field[] fields;

    @Override
    public String extract(Class<?> clazz) {
        fields = clazz.getDeclaredFields();
        String tableName = clazz.getSimpleName().toLowerCase();

        return "UPDATE " + tableName + " SET " + columns(clazz) + " WHERE " + PrimaryKeyUtils.getPrimaryKey(clazz) + "=?";
    }

    private String columns(Class<?> clazz) {
        return Arrays.stream(fields)
                .filter(field -> isPrimaryKeyField(clazz, field))
                .map(field -> field.getName() + "=?")
                .collect(Collectors.joining(","));
    }

    private boolean isPrimaryKeyField(Class<?> clazz, Field field) {
        return !field.getName().equals(PrimaryKeyUtils.getPrimaryKey(clazz));
    }

}
