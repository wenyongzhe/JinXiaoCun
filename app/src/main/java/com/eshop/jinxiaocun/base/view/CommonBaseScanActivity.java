package com.eshop.jinxiaocun.base.view;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.BarcodeScan;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.rscja.deviceapi.RFIDWithUHF;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/9
 * Desc:
 */

public abstract class CommonBaseScanActivity extends CommonBaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.listview)
    protected ListView mListView;
    @BindView(R.id.ll_scan_bottom)
    protected LinearLayout mLayoutScanBottom;
    @BindView(R.id.btn_add)
    protected Button mBtnAdd;
    @BindView(R.id.btn_delete)
    protected Button mBtnDelete;
    @BindView(R.id.btn_modify_count)
    protected Button mBtnModifyCount;
    @BindView(R.id.btn_modify_price)
    protected Button mBtnModifyPrice;
    @BindView(R.id.ll_scan_bottom_zsl_zje)
    protected LinearLayout mLayoutScanBottomZslZje;
    @BindView(R.id.ll_allRowNumber)
    protected LinearLayout mLayoutAllRowNumber;
    @BindView(R.id.tv_allRowNumber)
    protected TextView mTvAllRowNumber;//总行数
    @BindView(R.id.tv_ZSL)
    protected TextView mTvZsl;//总数量
    @BindView(R.id.tv_ZJE)
    protected TextView mTvZje;//总金额


//    protected BarcodeScan mBarcodeScan;//扫描控制
    protected abstract @LayoutRes int getLayoutContentId();
    protected abstract boolean scanBefore();//扫描前
    protected abstract void scanResultData(String barcode);//扫描返回的数据
    protected abstract boolean addBefore();//添加前
    protected abstract void addAfter();
    protected abstract boolean deleteBefore();//删除前
    protected abstract void deleteAfter();
    protected abstract boolean modifyCountBefore();//修改数前
    protected abstract void modifyCountAfter();
    protected boolean modifyPriceBefore(){return false;}//修价格前
    protected void modifyPriceAfter(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSound();
        initUHF();
    }

    @Override
    protected int getLayoutId() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.common_scan_bottom_one, null);
        mLinearLayout.addView(bottomView,-1,params);
        return getLayoutContentId();
    }

    @Override
    protected void initView() {
        super.initView();
        try {
//            mBarcodeScan = new BarcodeScan(this);
//            mBarcodeScan.open();
        }catch (Exception e){
            Log.e("",e.getMessage());
        }
    }

    @Override
    protected void initData() {
        super.initData();
//        hasBackDialog = true;
        /////////////////条码
        IntentFilter scanDataIntentFilter = new IntentFilter();
        scanDataIntentFilter.addAction("ACTION_BAR_SCAN");
        registerReceiver(mScanDataReceiver, scanDataIntentFilter);
        try {
//            mBarcodeScan = new BarcodeScan(this);
//            mBarcodeScan.open();
        }catch (Exception e){
            Log.e("",e.getMessage());
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
                if(!scanBefore()){
                    return;
                }
                scanResultData(str);
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @OnClick(R.id.btn_add)
    public void onClickAdd(){
        if(!addBefore()){
            return;
        }

        addAfter();
    }

    @OnClick(R.id.btn_delete)
    public void onClickDelete(){
        if(!deleteBefore()){
            return;
        }

        AlertUtil.showAlert(this, R.string.dialog_title, "您确定要删除吗？", R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deleteAfter();
                }catch (Exception ex){
                    AlertUtil.showToast("删除失败！原因："+ex.getMessage(),CommonBaseScanActivity.this);
                }
                AlertUtil.dismissDialog();
            }
        }, R.string.cancel, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertUtil.dismissDialog();
            }
        });


    }

    @OnClick(R.id.btn_modify_count)
    public void onClickModifyCount(){
        if(!modifyCountBefore()){
            return;
        }

        modifyCountAfter();
    }

    @OnClick(R.id.btn_modify_price)
    public void onClickModifyPrice(){
        if(!modifyPriceBefore()){
            return;
        }

        modifyPriceAfter();
    }

    public void setHeaderTitle(int tv_id, int title, int width){
        TextView textView= (TextView) this.findViewById(tv_id);
        textView.setWidth(CommonUtility.dip2px(this,width));
        textView.setText(getResources().getString(title));
        textView.setGravity(Gravity.CENTER);
        textView.setVisibility(View.VISIBLE);

//        switch (tv_id){
//
//            case R.id.tv_0:
//                View view_0 = this.findViewById(R.id.view_0);
//                view_0.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_1:
//                View view_1 = this.findViewById(R.id.view_1);
//                view_1.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_2:
//                View view_2 = this.findViewById(R.id.view_2);
//                view_2.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_3:
//                View view_3 = this.findViewById(R.id.view_3);
//                view_3.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_4:
//                View view_4 = this.findViewById(R.id.view_4);
//                view_4.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_5:
//                View view_5 = this.findViewById(R.id.view_5);
//                view_5.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_6:
//                View view_6= this.findViewById(R.id.view_6);
//                view_6.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_7:
//                View view_7 = this.findViewById(R.id.view_7);
//                view_7.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_8:
//                View view_8 = this.findViewById(R.id.view_8);
//                view_8.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.tv_9:
//                View view_9 = this.findViewById(R.id.view_9);
//                view_9.setVisibility(View.VISIBLE);
//                break;
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(mBarcodeScan!=null){
//            mBarcodeScan.close();
//        }
        if (mReader != null) {
            mReader.free();
        }
        unregisterReceiver(mScanDataReceiver);
    }

    private void setScanBroadCast(){
        Intent intent = new Intent("com.android.scanner.service_settings");
        intent.putExtra("action_barcode_broadcast","com.android.server.scannerservice.broadcast");
        intent.putExtra("key_barcode_broadcast", "scannerdata");
        sendBroadcast(intent);
        IntentFilter intentFilter = new IntentFilter("com.android.server.scannerservice.broadcast");
        registerReceiver(scanReceiver,intentFilter);
    }

    private BroadcastReceiver scanReceiver  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String barcode = intent.getStringExtra("scannerdata");
            Log.e("","--"+barcode);
//            et_barcode.setText(barcode);
        }
    };


    public RFIDWithUHF mReader;
    public void initUHF() {
        try {
            mReader = RFIDWithUHF.getInstance();
        } catch (Exception ex) {
            return;
        }
        if (mReader != null) {
            new InitTask().execute();
        }
    }

    /**
     *
     * 初始化RFID
     * @author
     */
    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(CommonBaseScanActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            mypDialog.cancel();
            if (!result) {
                Toast.makeText(CommonBaseScanActivity.this, "init fail",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
    private SoundPool soundPool;
    private float volumnRatio;
    private AudioManager am;
    private void initSound(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(this, R.raw.barcodebeep, 1));
        soundMap.put(2, soundPool.load(this, R.raw.serror, 1));
        am = (AudioManager) this.getSystemService(AUDIO_SERVICE);// 实例化AudioManager对象
    }
    /**
     * 播放提示音
     *
     * @param id 成功1，失败2
     */
    public void playSound(int id) {

        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 返回当前AudioManager对象的最大音量值
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);// 返回当前AudioManager对象的音量值
        volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        try {
            soundPool.play(soundMap.get(id), volumnRatio, // 左声道音量
                    volumnRatio, // 右声道音量
                    1, // 优先级，0为最低
                    0, // 循环次数，0无不循环，-1无永远循环
                    1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
            );
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

