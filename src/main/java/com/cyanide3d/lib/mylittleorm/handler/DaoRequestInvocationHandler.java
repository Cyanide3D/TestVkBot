package com.cyanide3d.lib.mylittleorm.handler;

import com.cyanide3d.lib.mylittleorm.database.DatabaseStore;
import com.cyanide3d.lib.mylittleorm.database.DatabaseConnectionLayer;
import com.cyanide3d.lib.mylittleorm.query.SQLDialect;
import com.cyanide3d.lib.mylittleorm.utils.ObjectUtils;
import com.cyanide3d.lib.mylittleorm.utils.PrimaryKeyUtils;

import java.util.List;

public class DaoRequestInvocationHandler implements DatabaseStore {

    private final DatabaseConnectionLayer dao = new DatabaseConnectionLayer();
    private final SQLDialect dialect;

    public DaoRequestInvocationHandler(SQLDialect dialect) {
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
    public void saveOrUpdate(Object arg) {
        createTableIfNotExist(arg.getClass());
        if (isEntityNotPresent(arg)) {
            save(arg);
        } else {
            update(arg);
        }
    }

    private boolean isEntityNotPresent(Object arg) {
        return false; //TODO
    }

    private void save(Object arg) {
        String query = dialect.getInsertQueryExtractor().extract(arg.getClass());
        List<Object> values = ObjectUtils.valueExtractWithoutPrimary(arg);
        dao.saveOrUpdate(query, values);
    }

    private void update(Object object) {
        String query = dialect.getUpdateQueryExtractor().extract(object.getClass());
        List<Object> val = ObjectUtils.valueExtractWithoutPrimary(object);
        val.add(PrimaryKeyUtils.getValueOfPrimaryKey(object));
        dao.saveOrUpdate(query, val);
    }
}