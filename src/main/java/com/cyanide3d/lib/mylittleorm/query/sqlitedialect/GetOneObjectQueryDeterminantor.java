package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.handler.ModelPrimaryKeyHandler;
import com.cyanide3d.lib.mylittleorm.query.QueryType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetOneObjectQueryDeterminantor implements QueryDeterminantor {
    @Override
    @SneakyThrows
    public Map<String, List<Object>> execute(Object object, List<Object> params, QueryType type) {
        if (!type.equals(QueryType.ONE_OBJECT)){
            return new HashMap<>();
        }
        String primaryKey = new ModelPrimaryKeyHandler().getPrimaryKey(object);
        List<Object> result = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String query = new StringBuilder()
                .append("SELECT * FROM ")
                .append(clazz.getSimpleName().toLowerCase())
                .append(" WHERE ")
                .append(primaryKey)
                .append("=?;")
                .toString();
        for (Field field : fields) {
            if (field.getName().equals(primaryKey)) {
                field.setAccessible(true);
                result.add(field.get(object));
            }
        }
        return new HashMap<>(Map.of(query, result));
    }
}
