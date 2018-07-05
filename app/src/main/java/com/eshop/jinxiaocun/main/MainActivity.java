package com.eshop.jinxiaocun.main;

import android.os.Bundle;

import com.eshop.jinxiaocun.base.BaseActivity;
import com.eshop.jinxiaocun.thread.BlankjThreadImp;
import com.eshop.jinxiaocun.thread.ThreadManagerInterface;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.xiaoshou.bean.TWmSheetMasterBean;
import com.eshop.jinxiaocun.xiaoshou.dao.TWmSheetMasterDao;

import java.sql.SQLException;
import java.util.List;

import supoin.jinxiaocun.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ThreadManagerInterface mThreadManagerInterface = new BlankjThreadImp();
        mThreadManagerInterface.executeRunnable(this);




    }


    @Override
    public Object doInBackground(Object[] objects) {
        testSqlServer2008(Config.DB_URL);
        return super.doInBackground(objects);
    }

    @Override
    public void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    private static void testSqlServer2008(String databaseUrl) {
        TWmSheetMasterBean nn  = new TWmSheetMasterBean();
        TWmSheetMasterDao m = new TWmSheetMasterDao();
        nn.setOper_id("1");
        nn.setBranch_no("2");
        nn.setSheet_no(""+ MyUtils.getRandom(300));
        nn.setDb_no("4");
        try {
            m.getDao().create(nn);
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
