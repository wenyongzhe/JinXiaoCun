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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DanPinZheKouCreatDialog extends Activity {

    @BindView(R.id.txtCountN)
    EditText et_value;
    @BindView(R.id.et_oldprice)
    EditText et_oldprice;
    @BindView(R.id.tv_newprice)
    TextView tv_newprice;
    @BindView(R.id.tv_count)
    EditText tv_count;
    @BindView(R.id.im_delet)
    ImageView im_delet;

    double limit = -1;
    double yijialimit = -1;

    double oldPrice;
    private String mSavediscount = "1";
    private String mLimitdiscount = "1";
    private String mCount = "1";
    private GetClassPluResult mGetClassPluResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danping_zhekou_creat);
        ButterKnife.bind(this);
        try {
            mSavediscount = getIntent().getStringExtra("Savediscount");
            mLimitdiscount = getIntent().getStringExtra("Limitdiscount");
            mCount = getIntent().getStringExtra("count");
            mGetClassPluResult = (GetClassPluResult) getIntent().getSerializableExtra("GetClassPluResult");

            tv_count.setText(mCount);
            et_value.setHintTextColor(getResources().getColor(R.color.mid_gray));
            et_value.setHint(Double.parseDouble(mLimitdiscount)*100+"-"+Double.parseDouble(mSavediscount)*100);
        }catch (Exception e){
        }

//        txtCountN.setFocusable(true);
//        txtCountN.setFocusableInTouchMode(true);
//        txtCountN.requestFocus();

        Intent intent = getIntent();
        limit = intent.getDoubleExtra("limit",0.00)*100;
        yijialimit = intent.getDoubleExtra("yijialimit",0.00);
        if(yijialimit == -1){
            et_oldprice.setEnabled(false);
        }
        if(limit == -1){
            et_value.setEnabled(false);
        }
        if(limit !=-1){
            et_value.setHint("最低折扣："+limit);
        }
        et_value.setText(intent.getStringExtra("countN"));

        et_oldprice.setText(""+MyUtils.formatDouble2(Double.parseDouble(mGetClassPluResult.getSale_price_beforModify())));
        oldPrice = Double.parseDouble(MyUtils.formatDouble2(Double.parseDouble(mGetClassPluResult.getSale_price_beforModify())));

        et_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               if(b){
                   et_value.selectAll();
               }
           }
        });
        et_oldprice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    et_oldprice.selectAll();
                }
            }
        });
        tv_count.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    tv_count.selectAll();
                }
            }
        });
        et_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    double price = Double.parseDouble(charSequence.toString().trim());
                    if(price>0){
                        double oldprice2 = Double.parseDouble(et_oldprice.getText().toString().trim());
                        tv_newprice.setText("￥"+MyUtils.formatDouble2(oldprice2*price/100));
                    }
                    if(price==0){
                        tv_newprice.setText("￥0");
                    }
                }catch (Exception e){
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        et_oldprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    double price = Double.parseDouble(charSequence.toString().trim());
                    double oldprice2 = Double.parseDouble(et_value.getText().toString().trim());
                    if(price>0){
                        tv_newprice.setText("￥"+MyUtils.formatDouble2(oldprice2*price/100));
                    }
                    if(price==0){
                        tv_newprice.setText("￥0");
                    }
                }catch (Exception e){
                    Log.e("","");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        int zhekou = mGetClassPluResult.getZhekou();
        if(zhekou<0){
            et_value.setText("100");
        }else{
            et_value.setText(zhekou+"");
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
        et_value.selectAll();
    }

    private void closeEditTextKeyboard() {
        MyUtils.closeKeyboard(this, et_value);
    }

    @OnClick(R.id.im_delet)
    void onDElet()
    {
        Intent intent = new Intent();
        intent.putExtra("countN",tv_newprice.getText().toString().trim().replace("￥",""));
        intent.putExtra("count","0");
        setResult(Config.MESSAGE_INTENT_ZHEKOU, intent);
        finish();
    }

    @OnClick(R.id.btn_ok)
    void OnOk()
    {
        if (et_value.getText().toString().trim().equals("")) {
            MyUtils.showToast("请输入折扣！", this);
            return;
        }
        if (et_value.getText().toString().contains(".")) {
            MyUtils.showToast("折扣请不要输入小数！", this);
            return;
        }

        if (!tv_count.getText().toString().trim().equals("") && Float.parseFloat(tv_count.getText().toString().trim())<0) {
            MyUtils.showToast("请输入大于0的数量！", this);
            return;
        }

        if (et_value.getText().toString().trim().equals("0")) {
            MyUtils.showToast("请输入大于0的折扣！", this);
            return;
        }

        if (Double.valueOf(et_value.getText().toString().trim())<limit) {
            MyUtils.showToast("后台设置折扣必须大于等于"+limit, this);
            return;
        }

//        if (Double.parseDouble(et_oldprice.getText().toString().trim())>oldPrice || (oldPrice-Double.parseDouble(et_oldprice.getText().toString().trim()))<yijialimit) {
//            MyUtils.showToast("请输入系统设置的金额！", this);
//            return;
//        }

        if(!et_oldprice.getText().toString().trim().equals(MyUtils.formatDouble2(Double.parseDouble(mGetClassPluResult.getSale_price_beforModify())))){
            if(Integer.decode(et_value.getText().toString())<100){
                MyUtils.showToast("折扣与议价不能同时优惠！", this);
                return;
            }
        }
        String countN = et_value.getText().toString().trim();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(DanPinZheKouCreatDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if(!TextUtils.isEmpty(countN)){
            Intent intent = new Intent();
            intent.putExtra("countN",tv_newprice.getText().toString().trim().replace("￥",""));
            intent.putExtra("count",tv_count.getText().toString().trim());
            intent.putExtra("price",et_oldprice.getText().toString().trim());
            mGetClassPluResult.setZhekou(Integer.decode(et_value.getText().toString()));
            intent.putExtra("zhekou",Integer.decode(et_value.getText().toString()));
            setResult(Config.MESSAGE_INTENT_ZHEKOU, intent);
            finish();
        }else {
            MyUtils.showToast("折扣不能为空。", DanPinZheKouCreatDialog.this);
        }
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

    @OnClick(R.id.btn_cancel)
    void OnCancel()
    {
        InputMethodManager inputMethodManager2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager2.hideSoftInputFromWindow(DanPinZheKouCreatDialog.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
