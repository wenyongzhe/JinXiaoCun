package com.eshop.jinxiaocun.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.db.DBHelper;
import com.eshop.jinxiaocun.login.LoginActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends BaseActivity {

    private TabView tabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        tabView= (TabView) findViewById(R.id.tabView);
        List<TabViewChild> tabViewChildList=new ArrayList<>();
        TabViewChild tabViewChild01=new TabViewChild(R.drawable.sydj,R.drawable.sy, Application.getInstance().getString(R.string.item_home),  HomeFragment.newInstance());
        TabViewChild tabViewChild02=new TabViewChild(R.drawable.kcdj,R.drawable.kc, Application.getInstance().getString(R.string.item_kucun),  KuChunFragment.newInstance());
        TabViewChild tabViewChild03=new TabViewChild(R.drawable.xsdj,R.drawable.xs, Application.getInstance().getString(R.string.item_xiaoshou), XiaoShouFragment.newInstance());
        TabViewChild tabViewChild04=new TabViewChild(R.drawable.tjdj,R.drawable.tj, Application.getInstance().getString(R.string.item_tongji),  TongJiFragment.newInstance());
        TabViewChild tabViewChild05=new TabViewChild(R.drawable.jcdj,R.drawable.jc, Application.getInstance().getString(R.string.item_jichu), jichuFragment.newInstance());
        tabViewChildList.add(tabViewChild01);
        tabViewChildList.add(tabViewChild02);
        tabViewChildList.add(tabViewChild03);
        tabViewChildList.add(tabViewChild04);
        tabViewChildList.add(tabViewChild05);
        //end add data
        tabView.setTabViewDefaultPosition(0);
        tabView.setTabViewChild(tabViewChildList,getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int  position, ImageView currentImageIcon, TextView currentTextView) {
//                 Toast.makeText(getApplicationContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });

//        startLogin();

      /*  ThreadManagerInterface mThreadManagerInterface = new AsyncTaskThreadImp();
        mThreadManagerInterface.executeRunnable(this);*/
    }

    private void startLogin(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }


    @Override
    public Object doInBackground(Object[] objects) {
        return super.doInBackground(objects);
    }

    @Override
    public void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

}
