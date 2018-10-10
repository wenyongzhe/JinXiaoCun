package com.eshop.jinxiaocun.netWork.jdbc;

import com.eshop.jinxiaocun.utils.Config;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;

public class OrmLiteManager implements JdbcInterface {

    private ConnectionSource connectionSource;
    private volatile static OrmLiteManager ormLiteManager;

    public OrmLiteManager() {
        try {
            connectionSource = new JdbcConnectionSource(Config.DB_URL);
            ((JdbcConnectionSource) connectionSource).setUsername(Config.DB_USER_NAME);
            ((JdbcConnectionSource) connectionSource).setPassword(Config.DB_PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
       /* try {
            connectionSource = new JdbcConnectionSource(Config.DB_URL);
            ((JdbcConnectionSource) connectionSource).setUsername(Config.DB_USER_NAME);
            ((JdbcConnectionSource) connectionSource).setPassword(Config.DB_PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public Dao creatDao(ConnectionSource connectionSource, Class classStr){
        try {
            return DaoManager.createDao(connectionSource, classStr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void closeDb() {
        try {
            connectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void query() {
    }


}
