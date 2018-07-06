package com.eshop.jinxiaocun.main.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.base.Application;
import com.eshop.jinxiaocun.base.BaseActivity;
import com.eshop.jinxiaocun.thread.AsyncTaskThreadImp;
import com.eshop.jinxiaocun.thread.ThreadManagerInterface;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.xiaoshou.bean.TWmSheetMasterBean;
import com.eshop.jinxiaocun.xiaoshou.dao.TWmSheetMasterDao;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import supoin.jinxiaocun.R;

public class MainActivity extends BaseActivity {

    private TabView tabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        tabView= (TabView) findViewById(R.id.tabView);
        List<TabViewChild> tabViewChildList=new ArrayList<>();
        TabViewChild tabViewChild01=new TabViewChild(R.drawable.tab01_sel,R.drawable.tab01_unsel, Application.getInstance().getString(R.string.item_home),  FirstFragment.newInstance());
        TabViewChild tabViewChild02=new TabViewChild(R.drawable.tab02_sel,R.drawable.tab02_unsel, Application.getInstance().getString(R.string.item_single_order),  SingleOrderFrgment.newInstance());
        TabViewChild tabViewChild03=new TabViewChild(R.drawable.tab04_sel,R.drawable.tab04_unsel, Application.getInstance().getString(R.string.item_collocation_order),  CollocationOrderFrgment.newInstance());
        TabViewChild tabViewChild04=new TabViewChild(R.drawable.tab03_sel,R.drawable.tab03_unsel, Application.getInstance().getString(R.string.item_report_form), ReportFormFrgment.newInstance());
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        //end add data
        tabView.setTabViewDefaultPosition(0);
        tabView.setTabViewChild(tabViewChildList,getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int  position, ImageView currentImageIcon, TextView currentTextView) {
//                 Toast.makeText(getApplicationContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });



        /*ThreadManagerInterface mThreadManagerInterface = new AsyncTaskThreadImp();
        mThreadManagerInterface.executeRunnable(this);*/
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
        if(list!=null){
            for (TWmSheetMasterBean cellPhone : list) {
                System.out.println(cellPhone);
            }
        }

    }

}
