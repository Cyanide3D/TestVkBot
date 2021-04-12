package com.cyanide3d.lib.mylittleorm.query;

public interface SQLDialect {

    InsertQueryExtractor getInsertQueryExtractor();
    CreateTableQueryExtractor getCreateTableQueryExtractor();
    SelectQueryExtractor getSelectQueryExtractor();
    UpdateQueryExtractor getUpdateQueryExtractor();

}
