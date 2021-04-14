package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.query.DeleteQueryExtractor;
import com.cyanide3d.lib.mylittleorm.utils.PrimaryKeyUtils;

public class DeleteQueryExtractorImpl implements DeleteQueryExtractor {
    @Override
    public String extract(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        String primary = PrimaryKeyUtils.getPrimaryKey(clazz);
        return "DELETE FROM " + tableName + " WHERE " + primary + "=?;";
    }
}
