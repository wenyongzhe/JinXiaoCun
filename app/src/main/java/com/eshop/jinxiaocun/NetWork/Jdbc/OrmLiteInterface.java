package com.eshop.jinxiaocun.NetWork.Jdbc;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DatabaseResultsMapper;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.GenericRowMapper;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;
import com.j256.ormlite.table.ObjectFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public interface OrmLiteInterface extends Dao{
    @Override
    Object queryForId(Object o) throws SQLException;

    @Override
    Object queryForFirst(PreparedQuery preparedQuery) throws SQLException;

    @Override
    List queryForAll() throws SQLException;

    @Override
    List queryForEq(String fieldName, Object value) throws SQLException;

    @Override
    List queryForMatching(Object matchObj) throws SQLException;

    @Override
    List queryForMatchingArgs(Object matchObj) throws SQLException;

    @Override
    List queryForFieldValues(Map fieldValues) throws SQLException;

    @Override
    List queryForFieldValuesArgs(Map fieldValues) throws SQLException;

    @Override
    Object queryForSameId(Object data) throws SQLException;

    @Override
    QueryBuilder queryBuilder();

    @Override
    UpdateBuilder updateBuilder();

    @Override
    DeleteBuilder deleteBuilder();

    @Override
    List query(PreparedQuery preparedQuery) throws SQLException;

    @Override
    int create(Object data) throws SQLException;

    @Override
    int create(Collection datas) throws SQLException;

    @Override
    Object createIfNotExists(Object data) throws SQLException;

    @Override
    CreateOrUpdateStatus createOrUpdate(Object data) throws SQLException;

    @Override
    int update(Object data) throws SQLException;

    @Override
    int updateId(Object data, Object newId) throws SQLException;

    @Override
    int update(PreparedUpdate preparedUpdate) throws SQLException;

    @Override
    int refresh(Object data) throws SQLException;

    @Override
    int delete(Object data) throws SQLException;

    @Override
    int deleteById(Object o) throws SQLException;

    @Override
    int delete(Collection datas) throws SQLException;

    @Override
    int deleteIds(Collection collection) throws SQLException;

    @Override
    int delete(PreparedDelete preparedDelete) throws SQLException;

    @Override
    CloseableIterator iterator();

    @Override
    CloseableIterator iterator(int resultFlags);

    @Override
    CloseableIterator iterator(PreparedQuery preparedQuery) throws SQLException;

    @Override
    CloseableIterator iterator(PreparedQuery preparedQuery, int resultFlags) throws SQLException;

    @Override
    CloseableWrappedIterable getWrappedIterable();

    @Override
    CloseableWrappedIterable getWrappedIterable(PreparedQuery preparedQuery);

    @Override
    void closeLastIterator() throws IOException;

    @Override
    GenericRawResults<String[]> queryRaw(String query, String... arguments) throws SQLException;

    @Override
    GenericRawResults queryRaw(String query, RawRowMapper mapper, String... arguments) throws SQLException;

    @Override
    GenericRawResults queryRaw(String query, DataType[] columnTypes, RawRowObjectMapper mapper, String... arguments) throws SQLException;

    @Override
    GenericRawResults<Object[]> queryRaw(String query, DataType[] columnTypes, String... arguments) throws SQLException;

    @Override
    GenericRawResults queryRaw(String query, DatabaseResultsMapper mapper, String... arguments) throws SQLException;

    @Override
    long queryRawValue(String query, String... arguments) throws SQLException;

    @Override
    int executeRaw(String statement, String... arguments) throws SQLException;

    @Override
    int executeRawNoArgs(String statement) throws SQLException;

    @Override
    int updateRaw(String statement, String... arguments) throws SQLException;

    @Override
    Object callBatchTasks(Callable callable) throws Exception;

    @Override
    String objectToString(Object data);

    @Override
    boolean objectsEqual(Object data1, Object data2) throws SQLException;

    @Override
    Object extractId(Object data) throws SQLException;

    @Override
    Class getDataClass();

    @Override
    FieldType findForeignFieldType(Class clazz);

    @Override
    boolean isUpdatable();

    @Override
    boolean isTableExists() throws SQLException;

    @Override
    long countOf() throws SQLException;

    @Override
    long countOf(PreparedQuery preparedQuery) throws SQLException;

    @Override
    void assignEmptyForeignCollection(Object parent, String fieldName) throws SQLException;

    @Override
    ForeignCollection getEmptyForeignCollection(String fieldName) throws SQLException;

    @Override
    void setObjectCache(boolean enabled) throws SQLException;

    @Override
    void setObjectCache(ObjectCache objectCache) throws SQLException;

    @Override
    ObjectCache getObjectCache();

    @Override
    void clearObjectCache();

    @Override
    Object mapSelectStarRow(DatabaseResults results) throws SQLException;

    @Override
    GenericRowMapper getSelectStarRowMapper() throws SQLException;

    @Override
    RawRowMapper getRawRowMapper();

    @Override
    boolean idExists(Object o) throws SQLException;

    @Override
    DatabaseConnection startThreadConnection() throws SQLException;

    @Override
    void endThreadConnection(DatabaseConnection connection) throws SQLException;

    @Override
    void setAutoCommit(DatabaseConnection connection, boolean autoCommit) throws SQLException;

    @Override
    boolean isAutoCommit(DatabaseConnection connection) throws SQLException;

    @Override
    void commit(DatabaseConnection connection) throws SQLException;

    @Override
    void rollBack(DatabaseConnection connection) throws SQLException;

    @Override
    ConnectionSource getConnectionSource();

    @Override
    void setObjectFactory(ObjectFactory objectFactory);

    @Override
    void registerObserver(DaoObserver observer);

    @Override
    void unregisterObserver(DaoObserver observer);

    @Override
    String getTableName();

    @Override
    void notifyChanges();
}
