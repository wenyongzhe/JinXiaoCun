package com.eshop.jinxiaocun.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eshop.jinxiaocun.base.BaseActivity;
import com.eshop.jinxiaocun.netWork.jdbc.OrmLiteManager;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.xiaoshou.bean.TWmSheetMasterBean;
import com.eshop.jinxiaocun.xiaoshou.dao.TWmSheetMasterDao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import supoin.jinxiaocun.R;

public class MainActivity extends BaseActivity {

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
        TWmSheetMasterDao m  = new TWmSheetMasterDao();
        TWmSheetMasterBean nn = new TWmSheetMasterBean();
        nn.setDb_no("3434234");
        nn.setSheet_no("33333333333333");
        nn.setBranch_no("33333333333333");
        nn.setOper_id("33333333333333");
        nn.setTime_stamp("33333333333333");
        try {
            m.create(nn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<TWmSheetMasterBean> list = null;
        try {
            list = m.getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("*******List of objects saved in DB*******");
        for (TWmSheetMasterBean cellPhone : list) {
            System.out.println(cellPhone);
        }

    }

}
