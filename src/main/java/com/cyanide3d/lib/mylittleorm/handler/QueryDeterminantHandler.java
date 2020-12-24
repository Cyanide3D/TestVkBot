package com.cyanide3d.lib.mylittleorm.handler;

import com.cyanide3d.lib.mylittleorm.query.QueryType;
import com.cyanide3d.lib.mylittleorm.query.sqlitedialect.QueryDeterminantor;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDeterminantHandler {
    ModelPrimaryKeyHandler modelPrimaryKeyHandler = new ModelPrimaryKeyHandler();
    List<QueryDeterminantor> determinants = new ArrayList<>();


    @SneakyThrows
    public QueryDeterminantHandler() {
        Reflections reflections = new Reflections("com");
        for (Class<? extends QueryDeterminantor> aClass : reflections.getSubTypesOf(QueryDeterminantor.class)) {
            determinants.add(aClass.getDeclaredConstructor().newInstance());
        }
    }





    public Map<String, List<Object>> execute(Object object, QueryType type, List<Object> params) {
        Map<String, List<Object>> result = new HashMap<>();
        for (QueryDeterminantor determinant : determinants) {
            result = determinant.execute(object, params, type);
            if (!result.isEmpty()) {
                break;
            }
        }
        return result;
    }





    //TODO Опрделить куда нибудь эту херню
    @SneakyThrows
    public String getCreateTableQuery(Object obj) {
        String primaryKry = modelPrimaryKeyHandler.getPrimaryKey(obj);
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder query = new StringBuilder();
        StringBuilder thirdPart = new StringBuilder();
        String firstPart = new StringBuilder().append("CREATE TABLE ").append(clazz.getSimpleName().toLowerCase()).append(" ( ").toString();
        String secondPart = "";
        for (int i = 0; i < fields.length; i++) {
            Field tempField = fields[i];
            if (tempField.getName().equals(primaryKry)) {
                secondPart = new StringBuilder().append(tempField.getName()).append(" INTEGER PRIMARY KEY AUTOINCREMENT,").toString();
            } else if (i == fields.length - 1) {
                thirdPart.append(tempField.getName()).append(" varchar(255)");
            } else {
                thirdPart.append(tempField.getName()).append(" varchar(255),");
            }
        }
        query.append(firstPart).append(secondPart).append(thirdPart).append(" );");
        return query.toString();
    }
}
