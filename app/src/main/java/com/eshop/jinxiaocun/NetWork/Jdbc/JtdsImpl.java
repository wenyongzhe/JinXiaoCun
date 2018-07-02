package com.eshop.jinxiaocun.NetWork.Jdbc;

import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.eshop.jinxiaocun.utils.*;

public class JtdsImpl implements JdbcInterface{

    String driverName = "net.sourceforge.jtds.jdbc.Driver";
    String dbURL ="jdbc:jtds:sqlserver://" + Config.IP + Config.IP_POIN + Config.DB_NAME + ";charset=UTF-8;";
    Connection dbConn = null;
    ResultSet rs = null;
    Statement statement;

    @Override
    public void connectDb(){
        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL,Config.USER_NAME, Config.PASSWORD);
            statement = dbConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeDb(){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
        if(dbConn!=null){
            try {
                dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbConn = null;
        }

       /* try {
            if(rs != null)rs.close();
            if(statement != null) statement.close();
            if(dbConn != null) dbConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void query() {

    }


    private ResultSet executeQuery(String sql){
        connectDb();
        try
        {
            rs = statement.executeQuery(sql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        closeDb();
        return rs;
    }

}
