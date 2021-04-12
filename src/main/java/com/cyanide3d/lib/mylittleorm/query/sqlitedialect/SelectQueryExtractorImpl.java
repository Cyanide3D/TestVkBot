package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.query.SelectQueryExtractor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryExtractorImpl implements SelectQueryExtractor {

    @Override
    public String extract(Class<?> clazz, List<Object> params) {
        return "SELECT * FROM " + tableName(clazz) + " WHERE " + columns(params) + ";";
    }

    @Override
    public String extractAll(Class<?> clazz) {
        return "SELECT * FROM " + tableName(clazz) + ";";
    }

    private String tableName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase();
    }

    private String columns(List<Object> columns) {
        return columns.stream()
                .map(param -> param.toString() + "=?")
                .collect(Collectors.joining(" AND "));
    }
}
