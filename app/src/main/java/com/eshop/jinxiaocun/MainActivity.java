package com.eshop.jinxiaocun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
                String dbURL ="jdbc:jtds:sqlserver://" + Config.IP + Config.IP_POIN + Config.DB_NAME + ";charset=UTF-8;";
                testSqlServer2008(dbURL);
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
