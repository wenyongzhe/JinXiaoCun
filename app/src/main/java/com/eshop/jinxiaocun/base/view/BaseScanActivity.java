package com.eshop.jinxiaocun.base.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eastaeon.decoderapi.DecoderHelper;
import com.eastaeon.decoderapi.DecoderHelperListener;
import com.eastaeon.decoderapi.DecoderHelperResult;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.UpDetailBean;
import com.eshop.jinxiaocun.base.bean.UpMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import android.hardware.BarcodeScan;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScanActivity extends BaseActivity implements ActionBarClickListener ,DecoderHelperListener {

    protected UpMainBean mUpMainBean;
    protected List<UpDetailBean> mUpDetailBeanList;
//    protected BarcodeScan mBarcodeScan;//扫描控制
    protected boolean newSheet = true;
    protected String sheet_no = "";
    protected DanJuMainBeanResultItem mDanJuMainBeanResultItem;
    protected ListView mListview;
    protected MyBaseAdapter mScanAdapter;
    protected int itemClickPosition;

    public DecoderHelper mDecoderHelper=null;
    private static final String ACTION_DATA_CODE_RECEIVED = "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED";
    private static final String ACTION_BAR_SCAN = "ACTION_BAR_SCAN";
    private static final String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasBackDialog = true;
        if(getIntent().getStringExtra(Config.SHEET_NO)!=null){
            newSheet = getIntent().getStringExtra(Config.SHEET_NO).equals("")?true:false;
            if( !newSheet ){
                sheet_no = getIntent().getStringExtra(Config.SHEET_NO);
                mDanJuMainBeanResultItem = (DanJuMainBeanResultItem) getIntent().getExtras().get("DanJuMain");
            }
        }
        /////////////////条码
        IntentFilter scanDataIntentFilter = new IntentFilter();
        scanDataIntentFilter.addAction(ACTION_DATA_CODE_RECEIVED);
        registerReceiver(mScanDataReceiver, scanDataIntentFilter);
        try {
//            mBarcodeScan = new BarcodeScan(this);
//            mBarcodeScan.open();

            mDecoderHelper = DecoderHelper.getInstance(this);
            mDecoderHelper.setDecoderHelperListeners(this);
        }catch (Exception e){
            Log.e("",e.getMessage());
        }

        //mBarcodeScan.scanning();
        /////////////////
        addViewConten();

        mUpMainBean = new UpMainBean();
        mUpDetailBeanList = new ArrayList<>();
        loadData();
        initView();
    }

    public void addViewConten(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.common_scan_bottom, null);
        mLinearLayout.addView(bottomView,-1,params);
        mListview = bottomView.findViewById(R.id.listview_data);
        mMyActionBar.setData("零售",R.mipmap.ic_left_light,"",0,"查询商品",this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mDecoderHelper!=null){
            mDecoderHelper.connect();//开始启动连接操作
        }
    }

    @Override
    public void onPause() {
        super.onPause();;
        if(mDecoderHelper!=null){
            mDecoderHelper.disconnect();//断开连接
        }
    }

    @Override
    protected void initView() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemClickPosition = i;
                mScanAdapter.setItemClickPosition(i);
                mScanAdapter.notifyDataSetInvalidated();

            }
        });
    }

    /*
        扫描返回数据
         */
    private BroadcastReceiver mScanDataReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(ACTION_DATA_CODE_RECEIVED)) {
                String str = intent.getStringExtra(DATA);
                scanData(str);
            }
        }
    };

    protected abstract void scanData(String barcode);

    @Override
    protected void loadData() {

    }

    public void setHeaderTitle(int tv_id, int title, int width) {
        TextView textView = (TextView) this.findViewById(tv_id);
        textView.setWidth(CommonUtility.dip2px(this, width));
        textView.setText(getResources().getString(title));
        textView.setVisibility(View.VISIBLE);

        switch (tv_id) {

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
                View view_6 = this.findViewById(R.id.view_6);
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
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mBarcodeScan.stop();
//        if(mBarcodeScan!=null){
//            mBarcodeScan.close();
//        }
        unregisterReceiver(mScanDataReceiver);
    }

    /*
	扫码引擎连接完毕时执行
	*/
    @Override
    public void onDecoderConnected() {
    }
    /*
    开始连接引擎时执行
    */
    @Override
    public void onStartDecoderConnect() {
    }
    /*
    开始断开扫码引擎时执行
    */
    @Override
    public void onStartDecoderDisconnect() {

    }
    /*
    扫码引擎断开时执行
    */
    @Override
    public void onDecoderDisconnected() {

    }
    /*
    扫码结果返回回调，多个条码同时识别是调用，暂时未实现
    */
    @Override
    public void onDecodeMultiResultCallback() {

    }
    /*
    扫码结果返回回调
    */
    @Override
    public void onDecodeTwoResultCallback(final DecoderHelperResult mDecoderHelperResult) {
        Log.d("", "mDecoderHelperResult.barcodeString="+mDecoderHelperResult.barcodeString);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //m_DecodeResultsView.setText(getString(R.string.result)+mDecoderHelperResult.barcodeString);
            }
        });
    }
    /*
    规定时间内DecoderHelper.g_nDecodeTimeout扫码失败回调
    */
    @Override
    public void onDecoderFailed(int failType,String failDetail) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        Log. d("", "onKeyDown" + "keyCode=" + keyCode +")");

//        if(mDecoderHelper!=null){
//
//            if(!mDecoderHelper.isScaning()){
//
//                mDecoderHelper.startScan();//开始连续扫码
//            }else{
//                mDecoderHelper.stopScan();//停止连续扫码
//            }
//        }

        return super.onKeyDown(keyCode, event);

    }

    public void onClickScan(View view) {
		/*if(mDecoderHelper.isScaning()){
			mDecoderHelper.stopScan();//停止连续扫码
		}else{
			mDecoderHelper.startScan();//开始连续扫码
		}*/
        mDecoderHelper.startScanOneTimes();//单次扫码
    }

}
