package com.eshop.jinxiaocun.huiyuan.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.NetPlayBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.lingshou.view.LingShouCreatAtivity;
import com.eshop.jinxiaocun.lingshou.view.VipCardPayCaptureActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.NfcUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.zxing.android.CaptureActivity;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.landicorp.android.eptapi.device.RFCardReader.LED_GREEN;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc: 会员查询
 */

public class MemberCheckActivity extends CommonBaseActivity implements INetWorResult,IElecCardInterface {


    @BindView(R.id.et_search)
    public EditText mEtSearch;
    @BindView(R.id.tv_cardNumber)
    public TextView mTvCardNumber;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_card_type)
    TextView mTvCardType;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;//余额
    @BindView(R.id.tv_current_integral)
    TextView mTvCurrentIntegral;// 当前积分
    @BindView(R.id.iv_scan)
    ImageView iv_scan;

    private IMemberList mApi;
    public List<MemberCheckResultItem> data;
    private ILingshouScan mApi2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_check;
    }

    @Override
    protected void initView() {
        mApi = new MemberImp(this);
        if(Config.mMemberInfo!=null){
            refreshUIByData(Config.mMemberInfo);
        }

        setTopToolBar("会员查询", R.mipmap.ic_left_light, "", 0, "");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                        AlertUtil.showToast("请输入卡号/手机号/姓名");
                        return false;
                    }
                    AlertUtil.showNoButtonProgressDialog(MemberCheckActivity.this,"正在读取卡信息，请稍后...");
                    mApi.getMemberCheckData(mEtSearch.getText().toString().trim());
                    hideSoftInput();
                    return true;
                }
                return false;
            }
        });
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberCheckActivity.this, VipCardPayCaptureActivity.class);
                startActivityForResult(intent, Config.REQ_QR_CODE);
            }
        });
    }

    @Override
    protected void initData() {
    }

    protected void refreshUIByData(MemberCheckResultItem data) {
        mEtSearch.setText("");
        mTvCardNumber.setText(data.getCardNo_TelNo());
        mTvName.setText(data.getCardName());
        mTvCardType.setText(data.getCardType());
        mTvStatus.setText(data.getCardState());
        mTvBalance.setText(MyUtils.convertToString(data.getResidual_amt(), "0"));
        mTvCurrentIntegral.setText(MyUtils.convertToString(data.getVip_accnum(), "0"));
    }

    protected void cleanUI() {
        mEtSearch.setText("");
        mTvCardNumber.setText("");
        mTvName.setText("");
        mTvCardType.setText("");
        mTvStatus.setText("");
        mTvBalance.setText("");
        mTvCurrentIntegral.setText("");
    }

    @OnClick(R.id.iv_close)
    public void onClickClose(){
        mEtSearch.setText("");
    }

    //搜索
    @OnClick(R.id.iv_search)
    public void onClickSearch() {
        sercheVIPInfo();
    }

    protected void sercheVIPInfo(){
        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
            AlertUtil.showToast("请输入卡号/手机号/姓名");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在读取卡信息，请稍后...");
        mApi.getMemberCheckData(mEtSearch.getText().toString().trim());
        hideSoftInput();
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                scanRFCard();
                return;
            }
            if(msg.obj!=null){
                mEtSearch.setText((CharSequence) msg.obj);
                onClickSearch();
            }
            sendEmptyMessageDelayed(1,2000);
        }
    };

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {

            case Config.MESSAGE_OK:
                AlertUtil.dismissProgressDialog();
                data = (List<MemberCheckResultItem>) o;
                if (data != null && data.size()>0) {
                    //Config.mMemberInfo=data.get(0);//记录最近一次会员信息  供销售结算时使用
                    refreshUIByData(data.get(0));
                } else {
                    AlertUtil.showToast("没有对应此卡号的信息！");
                }
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
        }
    }

    /*隐藏软键盘*/
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mEtSearch.getApplicationWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Config.REQ_QR_CODE && data != null) {
            String codedContent = data.getStringExtra("codedContent");
            if (codedContent != null && !codedContent.equals("")) {
                if(data.getBooleanExtra("isECard",false)){
                    getElecCradNo(codedContent);
                }else {
                    mEtSearch.setText(codedContent);
                    onClickSearch();
                }
            }
            return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //开启前台调度系统
        scanRFCard();

    }

    //非接触卡
    RFCardReader mRFCardReader;
    //卡驱动名称变量
    String driverName;

    @Override
    public void onPause() {
        super.onPause();
        try {
            mRFCardReader.turnOffLed(LED_GREEN);
            mRFCardReader.stopSearch();
        } catch (RequestException e) {
            e.printStackTrace();
        }

    }

    private void scanRFCard(){
        try {
            mRFCardReader = RFCardReader.getInstance();
            mRFCardReader.turnOnLed(LED_GREEN);
            mRFCardReader.searchCard(onSearchListener);
        } catch (RequestException e) {
            e.printStackTrace();
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

    @Override
    public void getElecCradNo(String auth_code) {
        mApi2 = new LingShouScanImp(new INetWorResult() {
            @Override
            public void handleResule(int flag, Object o) {
                switch (flag) {
                    case Config.MESSAGE_ELEC_CARD_RETURN:
                        NetPlayBeanResult mNetPlayBeanResult = (NetPlayBeanResult) o;
                        if (mNetPlayBeanResult != null) {
                            if (mNetPlayBeanResult.getReturn_code().equals("000000")) {
                                mEtSearch.setText(mNetPlayBeanResult.getTrade_no());
                                sercheVIPInfo();
                            } else {
                                AlertUtil.showToast("查询失败 " + mNetPlayBeanResult.getReturn_msg());
                            }
                        }
                        break;
                }

            }
        });
        mApi2.eleccardQry("",auth_code);
    }
}
