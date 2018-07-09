package com.eshop.jinxiaocun.base.view;

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

public class BaseBean implements Dao {
    @Override
    public Object queryForId(Object o) throws SQLException {
        return null;
    }

    @Override
    public Object queryForFirst(PreparedQuery preparedQuery) throws SQLException {
        return null;
    }

    @Override
    public List queryForAll() throws SQLException {
        return null;
    }

    @Override
    public List queryForEq(String fieldName, Object value) throws SQLException {
        return null;
    }

    @Override
    public List queryForMatching(Object matchObj) throws SQLException {
        return null;
    }

    @Override
    public List queryForMatchingArgs(Object matchObj) throws SQLException {
        return null;
    }

    @Override
    public Object queryForSameId(Object data) throws SQLException {
        return null;
    }

    @Override
    public QueryBuilder queryBuilder() {
        return null;
    }

    @Override
    public UpdateBuilder updateBuilder() {
        return null;
    }

    @Override
    public DeleteBuilder deleteBuilder() {
        return null;
    }

    @Override
    public List query(PreparedQuery preparedQuery) throws SQLException {
        return null;
    }

    @Override
    public int create(Object data) throws SQLException {
        return 0;
    }

    @Override
    public int create(Collection datas) throws SQLException {
        return 0;
    }

    @Override
    public Object createIfNotExists(Object data) throws SQLException {
        return null;
    }

    @Override
    public CreateOrUpdateStatus createOrUpdate(Object data) throws SQLException {
        return null;
    }

    @Override
    public int update(Object data) throws SQLException {
        return 0;
    }

    @Override
    public int updateId(Object data, Object newId) throws SQLException {
        return 0;
    }

    @Override
    public int update(PreparedUpdate preparedUpdate) throws SQLException {
        return 0;
    }

    @Override
    public int refresh(Object data) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Object data) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(Object o) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Collection datas) throws SQLException {
        return 0;
    }

    @Override
    public int deleteIds(Collection collection) throws SQLException {
        return 0;
    }

    @Override
    public int delete(PreparedDelete preparedDelete) throws SQLException {
        return 0;
    }

    @Override
    public CloseableIterator iterator() {
        return null;
    }

    @Override
    public CloseableIterator iterator(int resultFlags) {
        return null;
    }

    @Override
    public CloseableIterator iterator(PreparedQuery preparedQuery) throws SQLException {
        return null;
    }

    @Override
    public CloseableIterator iterator(PreparedQuery preparedQuery, int resultFlags) throws SQLException {
        return null;
    }

    @Override
    public CloseableWrappedIterable getWrappedIterable() {
        return null;
    }

    @Override
    public CloseableWrappedIterable getWrappedIterable(PreparedQuery preparedQuery) {
        return null;
    }

    @Override
    public void closeLastIterator() throws IOException {

    }

    @Override
    public GenericRawResults<String[]> queryRaw(String query, String... arguments) throws SQLException {
        return null;
    }

    @Override
    public GenericRawResults<Object[]> queryRaw(String query, DataType[] columnTypes, String... arguments) throws SQLException {
        return null;
    }

    @Override
    public long queryRawValue(String query, String... arguments) throws SQLException {
        return 0;
    }

    @Override
    public int executeRaw(String statement, String... arguments) throws SQLException {
        return 0;
    }

    @Override
    public int executeRawNoArgs(String statement) throws SQLException {
        return 0;
    }

    @Override
    public int updateRaw(String statement, String... arguments) throws SQLException {
        return 0;
    }

    @Override
    public String objectToString(Object data) {
        return null;
    }

    @Override
    public boolean objectsEqual(Object data1, Object data2) throws SQLException {
        return false;
    }

    @Override
    public Object extractId(Object data) throws SQLException {
        return null;
    }

    @Override
    public Class getDataClass() {
        return null;
    }

    @Override
    public boolean isUpdatable() {
        return false;
    }

    @Override
    public boolean isTableExists() throws SQLException {
        return false;
    }

    @Override
    public long countOf() throws SQLException {
        return 0;
    }

    @Override
    public long countOf(PreparedQuery preparedQuery) throws SQLException {
        return 0;
    }

    @Override
    public void assignEmptyForeignCollection(Object parent, String fieldName) throws SQLException {

    }

    @Override
    public void setObjectCache(boolean enabled) throws SQLException {

    }

    @Override
    public void setObjectCache(ObjectCache objectCache) throws SQLException {

    }

    @Override
    public ObjectCache getObjectCache() {
        return null;
    }

    @Override
    public void clearObjectCache() {

    }

    @Override
    public Object mapSelectStarRow(DatabaseResults results) throws SQLException {
        return null;
    }

    @Override
    public GenericRowMapper getSelectStarRowMapper() throws SQLException {
        return null;
    }

    @Override
    public RawRowMapper getRawRowMapper() {
        return null;
    }

    @Override
    public boolean idExists(Object o) throws SQLException {
        return false;
    }

    @Override
    public DatabaseConnection startThreadConnection() throws SQLException {
        return null;
    }

    @Override
    public void endThreadConnection(DatabaseConnection connection) throws SQLException {

    }

    @Override
    public void setAutoCommit(DatabaseConnection connection, boolean autoCommit) throws SQLException {

    }

    @Override
    public boolean isAutoCommit(DatabaseConnection connection) throws SQLException {
        return false;
    }

    @Override
    public void commit(DatabaseConnection connection) throws SQLException {

    }

    @Override
    public void rollBack(DatabaseConnection connection) throws SQLException {

    }

    @Override
    public ConnectionSource getConnectionSource() {
        return null;
    }

    @Override
    public void setObjectFactory(ObjectFactory objectFactory) {

    }

    @Override
    public void registerObserver(DaoObserver observer) {

    }

    @Override
    public void unregisterObserver(DaoObserver observer) {

    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public void notifyChanges() {

    }

    @Override
    public ForeignCollection getEmptyForeignCollection(String fieldName) throws SQLException {
        return null;
    }

    @Override
    public FieldType findForeignFieldType(Class clazz) {
        return null;
    }

    @Override
    public Object callBatchTasks(Callable callable) throws Exception {
        return null;
    }

    @Override
    public GenericRawResults queryRaw(String query, DatabaseResultsMapper mapper, String... arguments) throws SQLException {
        return null;
    }

    @Override
    public GenericRawResults queryRaw(String query, DataType[] columnTypes, RawRowObjectMapper mapper, String... arguments) throws SQLException {
        return null;
    }

    @Override
    public GenericRawResults queryRaw(String query, RawRowMapper mapper, String... arguments) throws SQLException {
        return null;
    }

    @Override
    public List queryForFieldValuesArgs(Map fieldValues) throws SQLException {
        return null;
    }

    @Override
    public List queryForFieldValues(Map fieldValues) throws SQLException {
        return null;
    }

    @Override
    public CloseableIterator closeableIterator() {
        return null;
    }
}
