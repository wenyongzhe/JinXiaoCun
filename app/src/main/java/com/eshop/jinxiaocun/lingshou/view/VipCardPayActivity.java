package com.eshop.jinxiaocun.lingshou.view;

import android.os.Bundle;
import android.text.TextUtils;

import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.List;

public class VipCardPayActivity extends SaveMemberActivity {

    double last_pay_money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        last_pay_money = getIntent().getDoubleExtra("last_pay_money",0);
    }

    @Override
    public void onClickOK() {
        if (TextUtils.isEmpty(mTvCardNumber.getText().toString().trim())) {
            AlertUtil.showToast("请查询会员号");
            return;
        }
        if (data != null && data.size()>0) {
            if(data.get(0).getResidual_amt()<last_pay_money){
                AlertUtil.showToast("余额不足！");
                return;
            }
            Config.mMemberInfo=data.get(0);//记录最近一次会员信息  供销售结算时使用
        }
        finish();
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {

            case Config.MESSAGE_OK:
                AlertUtil.dismissProgressDialog();
                data = (List<MemberCheckResultItem>) o;
                if (data != null && data.size()>0) {
                    //Config.mMemberInfo=data.get(0);//记录最近一次会员信息  供销售结算时使用
                    if(data.get(0).getSaving_flag().equals("1")){
                        refreshUIByData(data.get(0));
                    }
                    else {
                        AlertUtil.showToast("不是会员储值卡！");
                        cleanUI();
                    }
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
}
