package com.eshop.jinxiaocun.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.lingshou.bean.GetOptAuthResult;
import com.eshop.jinxiaocun.lingshou.bean.GetSystemBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.lingshou.view.CombiPayActivity;
import com.eshop.jinxiaocun.login.LoginActivity;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.othermodel.bean.CheckVerResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.utils.CheckVerUtils;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


public class MainActivity extends BaseActivity implements INetWorResult {

    private TabView tabView;
    private ILingshouScan mLingShouScanImp;
    private IOtherModel mIOtherModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasBackDialog = true;
        setContentView(R.layout.activity_main_menu);
        tabView= (TabView) findViewById(R.id.tabView);
        List<TabViewChild> tabViewChildList=new ArrayList<>();
        TabViewChild tabViewChild01=new TabViewChild(R.drawable.sydj,R.drawable.sy, Application.getInstance().getString(R.string.item_home),  HomeFragment.newInstance());
//        TabViewChild tabViewChild02=new TabViewChild(R.drawable.kcdj,R.drawable.kc, Application.getInstance().getString(R.string.item_kucun),  KuChunFragment.newInstance());
//        TabViewChild tabViewChild03=new TabViewChild(R.drawable.xsdj,R.drawable.xs, Application.getInstance().getString(R.string.item_xiaoshou), XiaoShouFragment.newInstance());
//        TabViewChild tabViewChild04=new TabViewChild(R.drawable.tjdj,R.drawable.tj, Application.getInstance().getString(R.string.item_tongji),  TongJiFragment.newInstance());
        TabViewChild tabViewChild05=new TabViewChild(R.drawable.jcdj,R.drawable.jc, Application.getInstance().getString(R.string.item_jichu), JichuFragment.newInstance());
        tabViewChildList.add(tabViewChild01);
//        tabViewChildList.add(tabViewChild02);
//        tabViewChildList.add(tabViewChild03);
//        tabViewChildList.add(tabViewChild04);
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

        getLimit();

        checkVer();
    }

    private void checkVer(){
        mIOtherModel = new OtherModelImp(this);
        mIOtherModel.CheckVer();
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

    IJsonFormat mJsonFormatImp = new JsonFormatImp();
    private void getLimit(){
        mLingShouScanImp = new LingShouScanImp(this);
        //-1：取整笔议价最高折让金额 和 单笔议价最高折让金额
        mLingShouScanImp.getOptAuth(Config.GRANT_ITEM_JINE,new IResponseListener(){
            @Override
            public void handleError(Object event) {
            }
            @Override
            public void handleResult(Response event, String result) {
            }
            @Override
            public void handleResultJson(String status, String msg, String jsonData) {
                try{
                    if(status.equals(Config.MESSAGE_OK+"")){
                        GetOptAuthResult mGetOptAuthResult =  mJsonFormatImp.JsonToBean(jsonData,GetOptAuthResult.class);
                        Config.zhendanYiJialimit = mGetOptAuthResult.getLimitdiscount();
                        Config.danbiYiJialimit = mGetOptAuthResult.getSavediscount();
                        Config.mYiJiaPermission = mGetOptAuthResult.getIsgrant();
                    }else{
                        AlertUtil.showAlert(MainActivity.this, "提示", "请求失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AlertUtil.showAlert(MainActivity.this, "提示", "请求失败");
                }
            }
        });
        //单笔折扣
        mLingShouScanImp.getOptAuth(Config.GRANT_ITEM_DISCOUNT,new IResponseListener(){
            @Override
            public void handleError(Object event) {
            }
            @Override
            public void handleResult(Response event, String result) {
            }
            @Override
            public void handleResultJson(String status, String msg, String jsonData) {
                try{
                    if(status.equals(Config.MESSAGE_OK+"")){
                        GetOptAuthResult mGetOptAuthResult =  mJsonFormatImp.JsonToBean(jsonData,GetOptAuthResult.class);
                        Config.zhendanZheKoulimit = mGetOptAuthResult.getLimitdiscount();
                        Config.danbiZheKoulimit = mGetOptAuthResult.getSavediscount();
                    }else{
                        AlertUtil.showAlert(MainActivity.this, "提示", "请求失败");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    AlertUtil.showAlert(MainActivity.this, "提示", "请求失败");
                }
            }
        });
    }

    CheckVerResult.CheckVerJson mCheckVerJson;
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:

                break;
            case Config.MESSAGE_GET_OPT_AUTH:
                break;
            case Config.MESSAGE_CHECKVER:
                mCheckVerJson = (CheckVerResult.CheckVerJson) o;
                if(mCheckVerJson!=null && mCheckVerJson.bUpdate>0){
                    AlertUtil.showAlert(MainActivity.this, "提示", "是否更新\n"+
                            "当前版本："+MyUtils.getAppVersionName(MainActivity.this)+"\n"+
                            "服务器版本："+mCheckVerJson.ServerVer,
                            "确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertUtil.dismissDialog();
                                    AlertUtil.showNoButtonProgressDialog(MainActivity.this,"下载中");
                                    new Thread(){
                                        @Override
                                        public void run() {
                                            CheckVerUtils mCheckVerUtils = new CheckVerUtils();
                                            mCheckVerUtils.downLoadFile(MainActivity.this,mCheckVerJson.AppUrl);
                                            han.sendEmptyMessage(1);
                                        }
                                    }.start();

                                }
                            },R.string.cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertUtil.dismissDialog();
                                }
                            });
                }
                break;
        }
    }
    Handler han = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            AlertUtil.dismissProgressDialog();
        }
    };
}
