package com.eshop.jinxiaocun.huiyuan.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.adapter.ExpenseCheckAdapter;
import com.eshop.jinxiaocun.huiyuan.bean.ExpenseCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc: 消费查询
 */

public class ExpenseCheckActivity extends CommonBaseActivity implements INetWorResult{

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_start_date)
    TextView mTvStartDate;
    @BindView(R.id.tv_end_date)
    TextView mTvEndDate;
    @BindView(R.id.lv_expense_info)
    ListView mListView;

    private IMemberList mApi;
    private ExpenseCheckAdapter mAdapter;
    private List<ExpenseCheckResultItem> mDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_expense_check;
    }

    @Override
    protected void initView() {
        setTopToolBar("消费查询", R.mipmap.ic_left_light,"",0,"");
        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                        AlertUtil.showToast("请输入会员ID");
                        return false;
                    }
                    AlertUtil.showNoButtonProgressDialog(ExpenseCheckActivity.this,"正在查询消费信息，请稍后...");
                    mApi.getExpenseCheckData(mEtSearch.getText().toString().trim(),
                            mTvStartDate.getText().toString(),
                            mTvEndDate.getText().toString());
                    hideSoftInput();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void initData() {
        mApi = new MemberImp(this);

        mAdapter = new ExpenseCheckAdapter(mDatas);
        mListView.setAdapter(mAdapter);

    }

    @OnClick(R.id.iv_close)
    public void onClickClose(){
        mEtSearch.setText("");
    }

    @OnClick(R.id.iv_search)
    public void onClickSearch(){
        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
            AlertUtil.showToast("请输入会员ID");
            return ;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在查询消费信息，请稍后...");
        mApi.getExpenseCheckData(mEtSearch.getText().toString().trim(),
                mTvStartDate.getText().toString(),
                mTvEndDate.getText().toString());
        hideSoftInput();
    }

    @OnClick(R.id.tv_start_date)
    void OnStartDate(){
        new SlideDateTimePicker.Builder(this.getSupportFragmentManager())
                .setListener(listenerStart)
                .setInitialDate(new Date())
                .build()
                .show();
    }

    private SlideDateTimeListener listenerStart = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = format.format(calendar.getTime());
            mTvStartDate.setText(strDate);
            if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                return ;
            }
            AlertUtil.showNoButtonProgressDialog(ExpenseCheckActivity.this,"正在查询消费信息，请稍后...");
            mApi.getExpenseCheckData(mEtSearch.getText().toString().trim(),
                    mTvStartDate.getText().toString(),
                    mTvEndDate.getText().toString());
        }
        @Override
        public void onDateTimeCancel() {}
    };

    @OnClick(R.id.tv_end_date)
    void OnEndDate(){
        new SlideDateTimePicker.Builder(this.getSupportFragmentManager())
                .setListener(listenerEnd)
                .setInitialDate(new Date())
                .build()
                .show();
    }

    private SlideDateTimeListener listenerEnd = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = format.format(calendar.getTime());
            mTvEndDate.setText(strDate);
            if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                return ;
            }
            AlertUtil.showNoButtonProgressDialog(ExpenseCheckActivity.this,"正在查询消费信息，请稍后...");
            mApi.getExpenseCheckData(mEtSearch.getText().toString().trim(),
                    mTvStartDate.getText().toString(),
                    mTvEndDate.getText().toString());
        }
        @Override
        public void onDateTimeCancel() {}
    };


    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                AlertUtil.dismissProgressDialog();
                mDatas = (List<ExpenseCheckResultItem>) o;
                if(mDatas!=null && mDatas.size()>0){
                    mAdapter.setListInfo(mDatas);
                }else{
                    AlertUtil.showToast("没有对应此卡号的消费信息！");
                }
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
        }
    }

    /*隐藏软键盘*/
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mEtSearch.getApplicationWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        if(mDatas!=null){
            mDatas.clear();
            mDatas = null;
        }
        super.onDestroy();
    }
}
