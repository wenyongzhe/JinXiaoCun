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

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DanPinGaiJiaDialog extends Activity {

    @BindView(R.id.txtCountN)
    EditText txtCountN;
    Double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_count);
        total = getIntent().getDoubleExtra("total",0.0);

        ButterKnife.bind(this);

        txtCountN.setFocusable(true);
        txtCountN.setFocusableInTouchMode(true);
        txtCountN.requestFocus();

        Intent intent = getIntent();
        txtCountN.setText(intent.getStringExtra("countN"));
        txtCountN.selectAll();
        txtCountN.setHintTextColor(getResources().getColor(R.color.mid_gray));
        double limit = intent.getDoubleExtra("limit",0.000);
        if(limit !=0.000){
            txtCountN.setHint("最高折让金额："+limit);
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
        MyUtils.closeKeyboard(this, txtCountN);
    }

    @OnClick(R.id.btn_ok)
    void OnOk()
    {
        if (txtCountN.getText().toString().trim().equals("")) {
            MyUtils.showToast("请输入数量！", this);
            return;
        }

        if (txtCountN.getText().toString().trim().equals("0")) {
            MyUtils.showToast("请输入大于0的数量！", this);
            return;
        }

        String countN = txtCountN.getText().toString().trim();
        if(total>0.0 && Double.parseDouble(countN)<total){
            MyUtils.showToast("请输入大于等于"+total+"的数！", this);
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(DanPinGaiJiaDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if(!TextUtils.isEmpty(countN)){
            Intent intent = new Intent();
            intent.putExtra("countN",countN);
            setResult(Config.MESSAGE_MONEY, intent);
            finish();
        }else {
            MyUtils.showToast("数量不能为空。",DanPinGaiJiaDialog.this);
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
