package com.eshop.jinxiaocun.lingshou.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.lingshou.bean.GetSystemBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.VipPayBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DanPinGaiJiaDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends BaseActivity implements ActionBarClickListener, INetWorResult {

    @BindView(R.id.btn_moling)
    Button btn_moling;//抹零
    @BindView(R.id.btn_zhengdanzhekou)
    Button btn_zhengdanzhekou;//整单折扣
    @BindView(R.id.btn_zhengdanyijia)
    Button btn_zhengdanyijia;//整单议价
    @BindView(R.id.btn_zhengdanquxiao)
    Button btn_zhengdanquxiao;//整单取消
    @BindView(R.id.btn_yingyeyuan)
    Button btn_yingyeyuan;//营业员
    @BindView(R.id.et_price)
    TextView et_price;


    private ILingshouScan mLingShouScanImp;
    private Button btn_ok;
    private Spinner sp_payway;
    private Double money = 0.00;
    GetSystemBeanResult.SystemJson mSystemJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.activity_pay, null);
        mLinearLayout.addView(bottomView,-1,params);
        ButterKnife.bind(this);
        mMyActionBar.setData("支付订单",R.mipmap.ic_left_light,"",0,"",this);

        money = getIntent().getDoubleExtra("money",0.00);
        String totalStr = money+"";
        if((totalStr.length()-totalStr.indexOf("."))>3){
            totalStr = totalStr.substring(0,totalStr.indexOf(".")+4);
        }
        et_price.setText("￥"+totalStr);

        btn_ok = findViewById(R.id.btn_ok);
        sp_payway = findViewById(R.id.sp_payway);
        mLingShouScanImp = new LingShouScanImp(this);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mLingShouScanImp.sellVipPay(vip_name.getText().toString(),vip_password.getText().toString(),money);
            }
        });

        //数据源
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add("现金");
        spinners.add("支付宝");
        spinners.add("微信");
        spinners.add("会员卡");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinners);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_payway.setAdapter(adapter);
        mLingShouScanImp.getSystemInfo();
    }

    VipPayBeanResult mVipPayBeanResult;
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showAlert(PayActivity.this,"提示",(String)o);
                break;
            case Config.MESSAGE_VIP_PAY_RESULT:
                mVipPayBeanResult = (VipPayBeanResult)o;
                setResult(Config.MESSAGE_VIP_PAY_RESULT);
                finish();
                break;
            case Config.MESSAGE_GET_SYSTEM_INFO_RETURN:
                mSystemJson = (GetSystemBeanResult.SystemJson)o;
                break;
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

    @OnClick(R.id.btn_moling)
    void btn_moling() {
        try {
            if(mSystemJson==null){
                return;
            }

            //0-实收，1- 抹去分 3- 抹去角分，3-元以后的四舍五入，4角以下四舍五入
            switch (mSystemJson.getValue()){
                case "0":
                    break;
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
            }
        }catch (Exception e){
        }
    }

    @OnClick(R.id.btn_zhengdanzhekou)
    void btn_zhengdanzhekou() {
        try {
        }catch (Exception e){
        }
    }


    @OnClick(R.id.btn_zhengdanyijia)
    void btn_zhengdanyijia() {
        try {
        }catch (Exception e){
        }
    }


    @OnClick(R.id.btn_zhengdanquxiao)
    void btn_zhengdanquxiao() {
        try {
        }catch (Exception e){
        }
    }

    @OnClick(R.id.btn_yingyeyuan)
    void btn_yingyeyuan() {
        try {
        }catch (Exception e){
        }
    }

}
