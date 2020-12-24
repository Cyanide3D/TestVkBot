package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.handler.ModelPrimaryKeyHandler;
import com.cyanide3d.lib.mylittleorm.query.QueryType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateQueryDeterminantor implements QueryDeterminantor {
    @Override
    @SneakyThrows
    public Map<String, List<Object>> execute(Object object, List<Object> params, QueryType type) {
        if (!type.equals(QueryType.UPDATE)){
            return new HashMap<>();
        }
        String primaryKey = new ModelPrimaryKeyHandler().getPrimaryKey(object);
        List<Object> result = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Object temp = new Object();
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(clazz.getSimpleName().toLowerCase()).append(" SET ");
        for (int i = 0; i < fields.length; i++) {
            Field tempField = fields[i];
            tempField.setAccessible(true);
            if (!tempField.getName().equals(primaryKey)) {
                if (i == fields.length - 1) {
                    query.append(tempField.getName()).append("=").append("? ");
                } else {
                    query.append(tempField.getName()).append("=").append("?,");
                }
                result.add(tempField.get(object));
            } else {
                temp = tempField.get(object);
            }
        }
        result.add(temp);
        query.append("WHERE ").append(primaryKey).append("=?");
        return new HashMap<>(Map.of(query.toString(), result));
    }
}
