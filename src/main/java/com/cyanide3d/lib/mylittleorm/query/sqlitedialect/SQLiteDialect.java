package com.cyanide3d.lib.mylittleorm.query.sqlitedialect;

import com.cyanide3d.lib.mylittleorm.query.*;

public class SQLiteDialect implements SQLDialect {
    @Override
    public InsertQueryExtractor getInsertQueryExtractor() {
        return new InsertQueryExtractorImpl();
    }

    @Override
    public CreateTableQueryExtractor getCreateTableQueryExtractor() {
        return new CreateTableQueryExtractorImpl();
    }

    @Override
    public SelectQueryExtractor getSelectQueryExtractor() {
        return new SelectQueryExtractorImpl();
    }

    @Override
    public UpdateQueryExtractor getUpdateQueryExtractor() {
        return new UpdateQueryExtractorImpl();
    }

    @Override
    public DeleteQueryExtractor getDeleteQueryExtractor() {
        return new DeleteQueryExtractorImpl();
    }
}
