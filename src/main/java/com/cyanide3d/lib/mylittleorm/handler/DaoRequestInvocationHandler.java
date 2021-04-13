package com.cyanide3d.lib.mylittleorm.handler;

import com.cyanide3d.lib.mylittleorm.database.DatabaseLayer;
import com.cyanide3d.lib.mylittleorm.query.SQLDialect;
import com.cyanide3d.lib.mylittleorm.utils.ObjectUtils;
import com.cyanide3d.lib.mylittleorm.utils.PrimaryKeyUtils;

import java.util.List;

public class DaoRequestInvocationHandler implements DatabaseProvider {

    private final DatabaseLayer dao;
    private final SQLDialect dialect;

    public DaoRequestInvocationHandler(SQLDialect dialect, DatabaseLayer dao) {
        this.dao = dao;
        this.dialect = dialect;
    }

    @Override
    public <T>List<T> findByField(String field, Class<?> clazz, Object arg) {
        String query = dialect.getSelectQueryExtractor().extract(clazz, List.of(field));
        return (List<T>) dao.findByField(query, arg, clazz);
    }

    @Override
    public <T>List<T> findAll(Class<?> clazz) {
        String query = dialect.getSelectQueryExtractor().extractAll(clazz);
        return dao.findAll(query, clazz);
    }

    @Override
    public void createTableIfNotExist(Class<?> clazz) {
        String query = dialect.getCreateTableQueryExtractor().extract(clazz);
        dao.createTable(query);
    }

    @Override
    public void saveOrUpdate(Object entity) {
        createTableIfNotExist(entity.getClass());
        if (isEntityNotPresent(entity)) {
            save(entity);
        } else {
            update(entity);
        }
    }

    private boolean isEntityNotPresent(Object entity) {
        String query = dialect.getSelectQueryExtractor().extract(entity.getClass(), List.of(PrimaryKeyUtils.getPrimaryKey(entity.getClass())));
        int primaryKey = PrimaryKeyUtils.getValueOfPrimaryKey(entity);
        return !dao.entityHasPresent(query, primaryKey);
    }

    private void save(Object arg) {
        String query = dialect.getInsertQueryExtractor().extract(arg.getClass());
        List<Object> values = ObjectUtils.valueExtractWithoutPrimary(arg);
        dao.saveOrUpdate(query, values);
    }

    private void update(Object object) {
        String query = dialect.getUpdateQueryExtractor().extract(object.getClass());
        List<Object> values = ObjectUtils.valueExtract(object);
        dao.saveOrUpdate(query, values);
    }
}