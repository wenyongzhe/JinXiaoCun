package com.eshop.jinxiaocun.lingshou.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.huiyuan.view.MemberCheckActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.NfcUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFCpuCardDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.StringUtil;
import com.zxing.android.CaptureActivity;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.landicorp.android.eptapi.device.RFCardReader.LED_GREEN;

public class SaveMemberActivity extends MemberCheckActivity {

    @BindView(R.id.bt_ok)
    Button bt_ok;//
    @BindView(R.id.iv_scan)
    ImageView iv_scan;

    //非接触卡
    RFCardReader mRFCardReader;
    //卡驱动名称变量
    String driverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @OnClick(R.id.bt_ok)
    public void onClickOK(){
        if (TextUtils.isEmpty(mTvCardNumber.getText().toString().trim())) {
            AlertUtil.showToast("请查询会员号");
            return;
        }
       /* Intent mIntent = new Intent();
        mIntent.putExtra("memberId",mTvCardNumber.getText().toString().trim());
        mIntent.putExtra("data", (Serializable) data);
        setResult(Config.SAVE_MEMBER_ID,mIntent);*/
        if (data != null && data.size()>0) {
            Config.mMemberInfo=data.get(0);//记录最近一次会员信息  供销售结算时使用
        }
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_get;
    }

    @Override
    protected void initView() {
        super.initView();
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaveMemberActivity.this, CaptureActivity.class);
                startActivityForResult(intent, Config.REQ_QR_CODE);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            String str = NfcUtils.resolveIntent(intent);
            mEtSearch.setText(str);
            onClickSearch();
        } catch (Exception e) {
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
        if(NfcUtils.mNfcAdapter!=null){
            NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
        }

        try {
            mRFCardReader = RFCardReader.getInstance();
            mRFCardReader.turnOnLed(LED_GREEN);
            mRFCardReader.searchCard(onSearchListener);
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //关闭前台调度系统
        if(NfcUtils.mNfcAdapter!=null){
            NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
        }

        try {
            mRFCardReader.turnOffLed(LED_GREEN);
            mRFCardReader.stopSearch();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQ_QR_CODE && data != null) {
            String codedContent = data.getStringExtra("codedContent");
            if(codedContent != null && !codedContent.equals("")){
                mEtSearch.setText(codedContent);
                onClickSearch();
            }
        }
    }


    //定义寻卡监听器
    RFCardReader.OnSearchListener onSearchListener = new
    RFCardReader.OnSearchListener() {
        @Override
        public void onCardPass(int cardType) {
            //寻卡成功,返回卡类型
            switch (cardType) {
                case S50_CARD:// S50 卡,仅支持 S50 驱动
                    driverName = "S50";
                    break;
                case S70_CARD:// S70 卡,仅支持 S70 驱动
                    driverName = "S70";
                    break;
                case CPU_CARD:// CPU 卡,仅支持 CPU 驱动
                case PRO_CARD:// PRO 卡,仅支持 PRO 驱动
                case S50_PRO_CARD://支持 S50 驱动和 PRO 驱动的 PRO 卡
                case S70_PRO_CARD://支持 S70 驱动和 PRO 驱动的 PRO 卡
                    driverName = "PRO";
                    break;
                default:
                    break;
            }
            //System.out.println("RFCard 已检测,用" + driverName + "驱动去读取");
            try {
                // 激活卡片
                RFCardReader.getInstance().activate(driverName,onActiveListener);
            } catch (RequestException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFail(int code) {
            //扫描失败处理
            switch (code) {
                case ERROR_CARDNOACT:// Pro 卡或者 TypeB 卡未激活
                    break;
                case ERROR_CARDTIMEOUT:// 超时无响应
                    break;
                case ERROR_MULTIERR:// 感应区内多卡存在
                    break;
                case ERROR_PROTERR:// 卡片返回数据不符合规范要求
                    break;
                case ERROR_TRANSERR:// 通信错误
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onCrash() {
            //设备服务崩溃处理
        }
    };

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    // 卡激活监听器
    private RFCardReader.OnActiveListener onActiveListener = new
    RFCardReader.OnActiveListener() {
        @Override
        public void onCardActivate(RFDriver driver) {
            // 成功激活时触发
            // 用返回的 driver 进行读卡等操作
            MifareDriver mMifareDriver = (MifareDriver) driver;
            NfcUtils.readMifareCard(mMifareDriver,mHandler);
        }
        @Override
        public void onActivateError(int code) {
        // 激活失败时触发,一般是一些严重的错误,如坏卡
            switch (code) {
                case ERROR_TRANSERR:// code=162 通信错误
                    break;
                case ERROR_PROTERR:// 163 卡片返回数据不符合规范要求
                    break;
                case ERROR_CARDTIMEOUT:// 167 超时无响应
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onUnsupport(String driverName) {
        // 指定的驱动不支持该卡时触发
        }
        @Override
        public void onCrash() {
        //设备服务崩溃处理
        }

    };
}
