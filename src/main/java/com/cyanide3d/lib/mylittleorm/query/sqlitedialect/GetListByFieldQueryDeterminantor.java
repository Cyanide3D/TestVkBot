package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.query.QueryType;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetListByFieldQueryDeterminantor implements QueryDeterminantor {
    @Override
    public HashMap<String, List<Object>> execute(Object object, List<Object> params, QueryType type) {
        if (!type.equals(QueryType.LIST_OBJECT_BY)) {
            return new HashMap<>();
        }
        String param = params.get(0).toString();
        String query = new StringBuilder()
                .append("SELECT * FROM ")
                .append(object.getClass().getSimpleName().toLowerCase())
                .append(" WHERE LOWER(")
                .append(StringUtils.substringAfter(param, ":"))
                .append(")='")
                .append(StringUtils.substringBefore(param, ":").toLowerCase())
                .append("';")
                .toString();
        System.out.println(query);
        return new HashMap<>(Map.of(query, new ArrayList<>()));
    }
}
