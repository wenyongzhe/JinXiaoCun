package com.eshop.jinxiaocun.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyGoodsPiciDialog extends Activity {

    @BindView(R.id.txtPici)
    EditText txtPici;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_goods_pici);

        ButterKnife.bind(this);

        txtPici.setFocusable(true);
        txtPici.setFocusableInTouchMode(true);
        txtPici.requestFocus();

        Intent intent = getIntent();
        txtPici.setText(intent.getStringExtra("GoodsPici"));
        txtPici.selectAll();

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
    }

    private void closeEditTextKeyboard() {
        MyUtils.closeKeyboard(this, txtPici);
    }

    @OnClick(R.id.btn_ok)
    void OnOk(){
        if (TextUtils.isEmpty(txtPici.getText().toString().trim())) {
            AlertUtil.showToast("请输入批次号！");
            return;
        }

        String pici = txtPici.getText().toString().trim();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(ModifyGoodsPiciDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        Intent intent = new Intent();
        intent.putExtra("GoodsPici",pici);
        setResult(RESULT_OK, intent);
        finish();

    }

    @OnClick(R.id.btn_cancel)
    void OnCancel(){
        InputMethodManager inputMethodManager2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager2.hideSoftInputFromWindow(ModifyGoodsPiciDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        AlertUtil.showToast("不填写批次，该商品不作盘点！");
        finish();
    }
}
