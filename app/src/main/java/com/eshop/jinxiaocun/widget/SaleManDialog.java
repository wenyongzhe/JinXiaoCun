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
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaleManDialog extends Activity implements INetWorResult {

    @BindView(R.id.txtCountN)
    EditText txtPayMan;
    Double total;
    private ILingshouScan mLingShouScanImp;
    String countN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_man);

        ButterKnife.bind(this);

        txtPayMan.setFocusable(true);
        txtPayMan.setFocusableInTouchMode(true);
        txtPayMan.requestFocus();
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
        mLingShouScanImp = new LingShouScanImp(this);
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
        MyUtils.closeKeyboard(this, txtPayMan);
    }

    @OnClick(R.id.btn_ok)
    void OnOk()
    {
        countN = txtPayMan.getText().toString().trim();
        if (countN.equals("")) {
            MyUtils.showToast("请输入营业员！", this);
            return;
        }

        mLingShouScanImp.checkSaleman(countN);
    }

    private void checkOK(){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(SaleManDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        Intent intent = new Intent();
        intent.putExtra("PayMan",countN);
        setResult(Config.MESSAGE_PAY_MAN, intent);
        finish();
    }

    @OnClick(R.id.btn_cancel)
    void OnCancel()
    {
        InputMethodManager inputMethodManager2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager2.hideSoftInputFromWindow(SaleManDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void handleResule(int flag, Object o) {
        Intent intent;
        switch (flag) {
            case Config.YING_YE_YUAN:
                AlertUtil.showToast("已保存营业员！");
                checkOK();
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(countN+"营业员不存在,请检查！");
                break;
        }
    }
}
