package com.eshop.jinxiaocun.base.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.BarcodeScan;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.CommonUtility;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/9
 * Desc:
 */

public abstract class CommonBaseScanActivity extends CommonBaseActivity implements AdapterView.OnItemClickListener{


    protected BarcodeScan mBarcodeScan;//扫描控制

    protected abstract @LayoutRes int getLayoutContentId();
    protected abstract void scanResultData(String barcode);//扫描返回的数据

    @Override
    protected int getLayoutId() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.common_scan_bottom, null);
        mLinearLayout.addView(bottomView,-1,params);
        return getLayoutContentId();
    }


    @Override
    protected void loadData() {
        super.loadData();
        /////////////////条码
        IntentFilter scanDataIntentFilter = new IntentFilter();
        scanDataIntentFilter.addAction("ACTION_BAR_SCAN");
        registerReceiver(mScanDataReceiver, scanDataIntentFilter);
        try {
            mBarcodeScan = new BarcodeScan(this);
            mBarcodeScan.open();
        }catch (Exception e){

        }
    }

    /*
     *扫描返回数据
     */
    private BroadcastReceiver mScanDataReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals("ACTION_BAR_SCAN")) {
                String str = intent.getStringExtra("EXTRA_SCAN_DATA");
                scanResultData(str);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void setHeaderTitle(int tv_id, int title, int width){
        TextView textView=this.findViewById(tv_id);
        textView.setWidth(CommonUtility.dip2px(this,width));
        textView.setText(getResources().getString(title));
        textView.setGravity(Gravity.CENTER);
        textView.setVisibility(View.VISIBLE);

        switch (tv_id){

            case R.id.tv_0:
                View view_0 = this.findViewById(R.id.view_0);
                view_0.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_1:
                View view_1 = this.findViewById(R.id.view_1);
                view_1.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_2:
                View view_2 = this.findViewById(R.id.view_2);
                view_2.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_3:
                View view_3 = this.findViewById(R.id.view_3);
                view_3.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_4:
                View view_4 = this.findViewById(R.id.view_4);
                view_4.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_5:
                View view_5 = this.findViewById(R.id.view_5);
                view_5.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_6:
                View view_6= this.findViewById(R.id.view_6);
                view_6.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_7:
                View view_7 = this.findViewById(R.id.view_7);
                view_7.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_8:
                View view_8 = this.findViewById(R.id.view_8);
                view_8.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_9:
                View view_9 = this.findViewById(R.id.view_9);
                view_9.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBarcodeScan!=null){
            mBarcodeScan.close();
        }
        unregisterReceiver(mScanDataReceiver);
    }
}
