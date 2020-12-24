package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.handler.ModelPrimaryKeyHandler;
import com.cyanide3d.lib.mylittleorm.query.QueryType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertQueryDeterminantor implements QueryDeterminantor {
    @SneakyThrows
    @Override
    public Map<String, List<Object>> execute(Object object, List<Object> params, QueryType type) {
        if (!type.equals(QueryType.INSERT)){
            return new HashMap<>();
        }
        String primaryKey = new ModelPrimaryKeyHandler().getPrimaryKey(object);
        List<Object> result = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(clazz.getSimpleName().toLowerCase()).append(" (");
        for (int i = 0; i < fields.length; i++) {
            Field tempField = fields[i];
            if (!tempField.getName().equals(primaryKey)) {
                if (i == fields.length - 1) {
                    query.append(tempField.getName());
                } else {
                    query.append(tempField.getName()).append(",");
                }
                tempField.setAccessible(true);
                result.add(tempField.get(object));
            }
        }
        query.append(") VALUES (");
        for (int i = 1; i < fields.length; i++) {
            if (i == fields.length - 1) {
                query.append("?");
            } else {
                query.append("?,");
            }
        }
        query.append(");");
        return new HashMap<>(Map.of(query.toString(), result));
    }
}
