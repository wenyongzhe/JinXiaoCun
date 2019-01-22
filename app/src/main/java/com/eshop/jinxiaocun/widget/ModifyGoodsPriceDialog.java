package com.eshop.jinxiaocun.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyGoodsPriceDialog extends Activity {

    @BindView(R.id.tv_old_price)
    TextView mTvOldPrice;//现价
    @BindView(R.id.tv_discounts_price)
    TextView mTvDiscountsPrice;//最高折让金额
    @BindView(R.id.et_price)
    EditText mEtPrice;//改价

    private float mOldPrice = 0f;
    private float mDiscountsPrice = 0f;//最高折让金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_goods_price);
        ButterKnife.bind(this);

        mEtPrice.setFocusable(true);
        mEtPrice.setFocusableInTouchMode(true);
        mEtPrice.requestFocus();

        mOldPrice = MyUtils.convertToFloat(getIntent().getStringExtra("OldPrice"),0);
        mDiscountsPrice = (float) getIntent().getDoubleExtra("DiscountsPrice",0);
        mTvOldPrice.setText(MyUtils.convertToString(mOldPrice,"0"));
        mTvDiscountsPrice.setText(MyUtils.convertToString(mDiscountsPrice,"0"));
        mEtPrice.setText(MyUtils.convertToString(mOldPrice,"0"));
        mEtPrice.selectAll();

        int screen_width = DensityUtil.getInstance().getScreenWidth(this);
        int screen_height = DensityUtil.getInstance().getScreenHeight(this);
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.copyFrom(getWindow().getAttributes());
        if(screen_height>1080)
            localLayoutParams.width = screen_width-160;
        else
            localLayoutParams.width = screen_width-100;
        localLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(localLayoutParams);
        mH.sendEmptyMessageDelayed(2,300);
    }

    Handler mH = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showSoftKeyboard();
        }
    };

    @OnClick(R.id.btn_ok)
    void OnOk(){
        if (TextUtils.isEmpty(mEtPrice.getText().toString().trim())) {
            AlertUtil.showToast("请输入修改的价格！", this);
            return;
        }
        float modifyPrice = MyUtils.convertToFloat(mEtPrice.getText().toString().trim(),0f);
        if (modifyPrice==0f) {
            AlertUtil.showToast("输入价格要大于0！", this);
            return;
        }

        float limitPrice = mOldPrice - modifyPrice;
        if(limitPrice<0f || limitPrice>mDiscountsPrice){
            //合理价格范围
            float reasonablePrice = mOldPrice-mDiscountsPrice;
            AlertUtil.showToast("修改的价格("+modifyPrice+")不在优惠金额范围内("+reasonablePrice+"~"+mOldPrice+")", this);
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(ModifyGoodsPriceDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        Intent intent = new Intent();
        intent.putExtra("ModifyPrice",mEtPrice.getText().toString().trim());
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.btn_cancel)
    void OnCancel() {
        InputMethodManager inputMethodManager2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager2.hideSoftInputFromWindow(ModifyGoodsPriceDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void showSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
