package com.cyanide3d.lib.mylittleorm.handler;

import com.cyanide3d.lib.mylittleorm.database.DatabaseLayer;
import com.cyanide3d.lib.mylittleorm.query.SQLDialect;
import com.cyanide3d.lib.mylittleorm.utils.ObjectUtils;
import com.cyanide3d.lib.mylittleorm.utils.PrimaryKeyUtils;

import java.util.List;

public class DaoRequestInvocationHandler<T> implements DatabaseProvider<T> {

    private final DatabaseLayer dao;
    private final SQLDialect dialect;

    public DaoRequestInvocationHandler(SQLDialect dialect, DatabaseLayer dao) {
        this.dao = dao;
        this.dialect = dialect;
    }

    @Override
    public List<T> findByField(String field, Class<T> clazz, Object arg) {
        String query = dialect.getSelectQueryExtractor().extract(clazz, List.of(field));
        return dao.findByField(query, arg, clazz);
    }

    @Override
    public List<T> findAll(Class<T> clazz) {
        String query = dialect.getSelectQueryExtractor().extractAll(clazz);
        return dao.findAll(query, clazz);
    }

    @Override
    public void createTableIfNotExist(Class<T> clazz) {
        String query = dialect.getCreateTableQueryExtractor().extract(clazz);
        dao.createTable(query);
    }

    @Override
    public void saveOrUpdate(T entity) {
        createTableIfNotExist((Class<T>) entity.getClass());
        if (isEntityNotPresent(entity)) {
            save(entity);
        } else {
            update(entity);
        }
    }

    private boolean isEntityNotPresent(T entity) {
        String query = dialect.getSelectQueryExtractor().extract(entity.getClass(), List.of(PrimaryKeyUtils.getPrimaryKey(entity.getClass())));
        int primaryKey = PrimaryKeyUtils.getValueOfPrimaryKey(entity);
        return !dao.entityHasPresent(query, primaryKey);
    }

    private void save(T entity) {
        String query = dialect.getInsertQueryExtractor().extract(entity.getClass());
        List<Object> values = ObjectUtils.valueExtractWithoutPrimary(entity);
        dao.saveOrUpdate(query, values);
    }

    private void update(T object) {
        String query = dialect.getUpdateQueryExtractor().extract(object.getClass());
        List<Object> values = ObjectUtils.valueExtract(object);
        dao.saveOrUpdate(query, values);
    }
}