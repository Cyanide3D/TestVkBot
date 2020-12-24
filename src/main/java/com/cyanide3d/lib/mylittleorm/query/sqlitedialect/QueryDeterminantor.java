package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.query.QueryType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface QueryDeterminantor {
    public Map<String, List<Object>> execute(Object object, List<Object> params, QueryType type);
}
