package com.eshop.jinxiaocun.base.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.thread.TaskInterface;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.MyActionBar;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author Lu An
 * 创建时间  2018/9/5 0005
 * 描述
 */

public abstract class CommonBaseActivity extends AppCompatActivity implements TaskInterface {


    @BindView(R.id.actionbar)
    MyActionBar mMyActionBar;

    protected FrameLayout mView;
    protected LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = new FrameLayout(this);
        View rootView = View.inflate(this, R.layout.activity_base, null);
        mLinearLayout = rootView.findViewById(R.id.content);
        View contentView = LayoutInflater.from(this).inflate(getLayoutId(),null);
        mLinearLayout.addView(contentView,0);
        setContentView(rootView);
        ButterKnife.bind(this);

        Application.getInstance().addActivity(this);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initView();
        loadData();
    }

    protected abstract @LayoutRes int getLayoutId();

    protected void loadData(){}
    protected void initView(){}
    //如果返回ture则子类自己处理自己的逻辑，否则父类处理
    protected boolean onTopBarLeftClick(){return false;}
    protected void onTopBarRightClick(){}

    public void setTopToolBar(String strTitle, @DrawableRes int resIdLeft, String strLeft, @DrawableRes int resIdRight, String strRight){
        mMyActionBar.setData(strTitle, resIdLeft, strLeft,resIdRight, strRight, new ActionBarClickListener() {
            @Override
            public void onLeftClick() {
                if(!onTopBarLeftClick())finish();
            }

            @Override
            public void onRightClick() {
                onTopBarRightClick();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Application.getInstance().finishActivity(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            //点击HOME ICON finish当前Activity
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public Object doInBackground(Object[] objects){
        return null;
    }

    @Override
    public void onPostExecute(Object o) {

    }


}