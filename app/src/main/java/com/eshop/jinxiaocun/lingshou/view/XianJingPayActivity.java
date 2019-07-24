package com.eshop.jinxiaocun.lingshou.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XianJingPayActivity extends BaseActivity {

    @BindView(R.id.et_price)
    TextView et_price;
    @BindView(R.id.et_pay_money)
    EditText et_pay_money;
    @BindView(R.id.tv_pay_return)
    TextView tv_pay_return;
    @BindView(R.id.btn_jiesuan)
    Button btn_jiesuan;

    private double money = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.activity_xianjing_pay, null);
        mLinearLayout.addView(bottomView,-1,params);
        ButterKnife.bind(this);
        money = getIntent().getDoubleExtra("money",0.0);
        et_price.setText("￥"+money);
        et_pay_money.setText(MyUtils.formatDouble2(money));
        tv_pay_return.setText("0");
        btn_jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_jiesuan();
            }
        });
        et_pay_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    tv_pay_return.setText("");
                }else {
                    float tv_money = Float.valueOf(charSequence.toString());
                    //tv_money = tv_money + 0.0000000000001;
                    if(tv_money>money){
                        tv_pay_return.setText(MyUtils.formatDouble2(tv_money-money));
                    }else{
                        tv_pay_return.setText("0");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void btn_jiesuan() {
        if(et_pay_money.getText().toString().equals("") || Double.parseDouble(et_pay_money.getText().toString())<0){
            ToastUtils.showShort("填写付款金额有误。");
            return;
        }
        Intent mIntent = new Intent();
        mIntent.putExtra("money",Double.parseDouble(et_pay_money.getText().toString()));
        mIntent.putExtra("money_return",Double.parseDouble(tv_pay_return.getText().toString()));
        setResult(Config.XIANJING_RETURN,mIntent);
        finish();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }
}
