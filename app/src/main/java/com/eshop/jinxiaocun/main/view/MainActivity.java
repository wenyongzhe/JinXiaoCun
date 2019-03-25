package com.eshop.jinxiaocun.main.view;

import android.content.Intent;
import android.os.Bundle;
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
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.login.LoginActivity;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.ycl.tabview.library.TabView;
import com.ycl.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;


public class MainActivity extends BaseActivity implements INetWorResult {

    private TabView tabView;
    private ILingshouScan mLingShouScanImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasBackDialog = true;
        setContentView(R.layout.activity_main_menu);

        getLimit();
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

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:

                break;
            case Config.MESSAGE_GET_OPT_AUTH:

                break;
        }
    }
}
