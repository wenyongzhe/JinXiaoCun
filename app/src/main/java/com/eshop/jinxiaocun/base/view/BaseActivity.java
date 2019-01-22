package com.eshop.jinxiaocun.base.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.thread.TaskInterface;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.MyActionBar;

public abstract class BaseActivity extends AppCompatActivity implements TaskInterface{

    protected LinearLayout mLinearLayout;
    public MyActionBar mMyActionBar;
    public boolean hasBackDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mMyActionBar = findViewById(R.id.actionbar);
        mLinearLayout = findViewById(R.id.content);
        Application.getInstance().addActivity(this);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    protected abstract void loadData();

    protected abstract void initView();

    public View getView(int layout){
        View returnView = this.getLayoutInflater().inflate(layout, null);
        return returnView;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Application.getInstance().finishActivity(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if(hasBackDialog){
                AlertUtil.showAlert(BaseActivity.this,
                        R.string.dialog_title,
                        R.string.mess_back,
                        R.string.confirm,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertUtil.dismissDialog();
                                finish();
                            } },
                        R.string.cancel,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertUtil.dismissDialog();
                            } }
                );
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

   /* public void setTileText(String title) {
        TextView toolbar = (TextView) findViewById(R.id.toolbar);
        toolbar.setText(title);

        TextView head = (TextView) findViewById(R.id.keyboard_base);
        if (head != null) {
            head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSoftKeyboard();
                }
            });
        }
    }*/

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

    /*protected void setKeyboard(boolean flag) {
        TextView tv_keyboard = (TextView) findViewById(R.id.keyboard_base);
        if (tv_keyboard != null) {
            if (flag) {
                tv_keyboard.setVisibility(View.VISIBLE);
            } else {
                tv_keyboard.setVisibility(View.INVISIBLE);
            }
        }
    }*/
}
