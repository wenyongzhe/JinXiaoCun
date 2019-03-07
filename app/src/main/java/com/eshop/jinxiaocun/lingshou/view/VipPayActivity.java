package com.eshop.jinxiaocun.lingshou.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.lingshou.bean.GetOptAuthResult;
import com.eshop.jinxiaocun.lingshou.bean.VipPayBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.List;

public class VipPayActivity extends BaseActivity implements ActionBarClickListener, INetWorResult {

    private EditText vip_name;
    private EditText vip_password;
    private EditText vip_money;
    private ILingshouScan mLingShouScanImp;
    private Button btn_ok;
    private Double money = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.activity_vip_pay, null);
        mLinearLayout.addView(bottomView,-1,params);
        mMyActionBar.setData("会员卡支付",R.mipmap.ic_left_light,"",0,"",this);
        money = getIntent().getDoubleExtra("money",0.00);
        vip_name = (EditText) findViewById(R.id.vip_name);
        vip_password = (EditText) findViewById(R.id.vip_password);
        vip_money = (EditText) findViewById(R.id.vip_money);
        vip_money.setText(money+"");
        btn_ok = (Button) findViewById(R.id.btn_ok);
        mLingShouScanImp = new LingShouScanImp(this);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLingShouScanImp.sellVipPay(vip_name.getText().toString(),vip_password.getText().toString(),money);
            }
        });
    }

    VipPayBeanResult mVipPayBeanResult;
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showAlert(VipPayActivity.this,"提示",(String)o);
                break;
            case Config.MESSAGE_VIP_PAY_RESULT:
                mVipPayBeanResult = (VipPayBeanResult)o;
                setResult(Config.MESSAGE_VIP_PAY_RESULT);
                finish();
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


}
