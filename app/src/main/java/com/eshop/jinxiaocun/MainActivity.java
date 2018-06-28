package com.eshop.jinxiaocun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import supoin.jinxiaocun.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(){
            @Override
            public void run() {
                testSQL();
            }
        }.start();

    }

    private void testSQL(){

        String IP="192.168.10.67";
        String DBName="EWESHOP";
        String UserName="sa";
        String Password="123456";

        String driverName = "net.sourceforge.jtds.jdbc.Driver";
        String dbURL ="jdbc:jtds:sqlserver://"+IP+":1433/"+DBName+";charset=UTF-8;";
        Connection dbConn=null;
        try
        {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL,UserName, Password);
            Statement statement=dbConn.createStatement();
            String strsql="select top 10* from t_da_sell_aim";
            ResultSet rs=statement.executeQuery(strsql);
            ResultSetMetaData metaData=rs.getMetaData();
            int numColumns=metaData.getColumnCount();
            for(int i=1;i<=numColumns;i++)
            {
                Log.e("jdbc",metaData.getColumnName(i));
            }
            while(rs.next())
            {
                Log.e("jdbc",rs.getString(1));
            }
            if(dbConn!=null)
            {
                dbConn.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
