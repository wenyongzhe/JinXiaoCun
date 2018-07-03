package com.eshop.jinxiaocun.NetWork.Jdbc;

import com.eshop.jinxiaocun.Base.BaseBean;
import com.eshop.jinxiaocun.CellPhoneDao;
import com.eshop.jinxiaocun.utils.Config;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.DatabaseResultsMapper;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.dao.RawRowObjectMapper;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
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
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class OrmLiteManager implements JdbcInterface {

    private ConnectionSource connectionSource;
    private volatile static OrmLiteManager ormLiteManager;

    public OrmLiteManager() {
    }

    public static OrmLiteManager getOrmLiteManager(){
        if (ormLiteManager == null) {
            synchronized (OrmLiteManager.class) {
                if (ormLiteManager == null) {
                    ormLiteManager = new OrmLiteManager();
                }
            }
        }
        return ormLiteManager;
    }

    @Override
    public void connectDb() {
        try {
            connectionSource = new JdbcConnectionSource(Config.DB_URL);
            ((JdbcConnectionSource) connectionSource).setUsername(Config.USER_NAME);
            ((JdbcConnectionSource) connectionSource).setPassword(Config.PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    @Override
    public void closeDb() {
    }

    @Override
    public void query() {
    }


}
