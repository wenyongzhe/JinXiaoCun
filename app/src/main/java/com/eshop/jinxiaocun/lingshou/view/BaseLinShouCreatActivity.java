package com.eshop.jinxiaocun.lingshou.view;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseScanActivity;
import com.eshop.jinxiaocun.utils.NfcUtils;

import java.io.UnsupportedEncodingException;

public class BaseLinShouCreatActivity extends BaseScanActivity {

    @Override
    public void addViewConten() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.lingshou_creat_bottom, null);
        mLinearLayout.addView(bottomView,-1,params);
        mListview = bottomView.findViewById(R.id.listview_data);
        mMyActionBar.setData("零售",R.mipmap.ic_left_light,"",0,"更多商品",this);
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            String str = NfcUtils.readNFCFromTag(intent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void initData() {
        //nfc初始化设置
        NfcUtils nfcUtils = new NfcUtils(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //开启前台调度系统
        NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
    }

    @Override
    public void onPause() {
        super.onPause();
        //关闭前台调度系统
        NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
    }


    @Override
    protected void scanData(String barcode) {
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void onRightClick() {
    }
}
