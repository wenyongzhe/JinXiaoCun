package com.eshop.jinxiaocun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eshop.jinxiaocun.NetWork.Jdbc.OrmLiteManager;
import com.eshop.jinxiaocun.utils.Config;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import supoin.jinxiaocun.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(){
            @Override
            public void run() {
                testSqlServer2008(Config.DB_URL);
            }
        }.start();

    }

    private static void testSqlServer2008(String databaseUrl) {
        ConnectionSource connectionSource;
        try {
            connectionSource = new JdbcConnectionSource(databaseUrl);
            ((JdbcConnectionSource) connectionSource).setUsername("sa");
            ((JdbcConnectionSource) connectionSource).setPassword("123456");
            CellPhoneDao cd = new CellPhoneDao();
            cd.performDBOperations(connectionSource);


            OrmLiteManager ff = new OrmLiteManager();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
