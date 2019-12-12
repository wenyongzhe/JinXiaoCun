package com.eshop.jinxiaocun.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DanPinGaiJiaDialog extends Activity {

//    @BindView(R.id.txtCountN)
//    EditText txtCountN;
    @BindView(R.id.tv_oldprice)
    TextView tv_oldprice;
    @BindView(R.id.et_newprice)
    EditText et_newprice;

    double oldPrice;
    double limit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danpin_gaijia);
        ButterKnife.bind(this);

        oldPrice = getIntent().getDoubleExtra("oldPrice",0.0);
        tv_oldprice.setText("￥"+oldPrice);

        et_newprice.setFocusable(true);
        et_newprice.setFocusableInTouchMode(true);
        et_newprice.requestFocus();

        Intent intent = getIntent();
        et_newprice.setHintTextColor(getResources().getColor(R.color.mid_gray));
        limit = intent.getDoubleExtra("limit",0.000);
        if(limit !=-1){
            et_newprice.setHint("￥"+oldPrice+"—"+"￥"+(oldPrice-limit));
        }

        closeEditTextKeyboard();

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

    public void showSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void closeEditTextKeyboard() {
        MyUtils.closeKeyboard(this, et_newprice);
    }

    @OnClick(R.id.btn_ok)
    void OnOk()
    {
        if (et_newprice.getText().toString().trim().equals("")) {
            MyUtils.showToast("请输入金额！", this);
            return;
        }

        String countN = et_newprice.getText().toString().trim();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(DanPinGaiJiaDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if(!TextUtils.isEmpty(countN)){
            Intent intent = new Intent();
            intent.putExtra("countN",countN);
            setResult(Config.MESSAGE_MONEY, intent);
            finish();
        }else {
            MyUtils.showToast("金额不能为空。",DanPinGaiJiaDialog.this);
        }
    }

    @OnClick(R.id.btn_cancel)
    void OnCancel()
    {
        InputMethodManager inputMethodManager2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager2.hideSoftInputFromWindow(DanPinGaiJiaDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
