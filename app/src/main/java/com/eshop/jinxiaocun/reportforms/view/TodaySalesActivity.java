package com.eshop.jinxiaocun.reportforms.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.view.MemberCheckActivity;
import com.eshop.jinxiaocun.reportforms.adapter.TodaySalesAdapter;
import com.eshop.jinxiaocun.reportforms.bean.TodaySalesInfo;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/6
 * Desc:
 */
public class TodaySalesActivity extends CommonBaseActivity {

    @BindView(R.id.et_billNo)
    EditText mEtBillNo;

    @BindView(R.id.lv_salesData)
    ListView mListView;

    private TodaySalesAdapter mAdapter;
    private List<TodaySalesInfo> mDataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_today_sales;
    }


    //初始化控件
    @Override
    protected void initView() {
        setTopToolBar("今天销售", R.mipmap.ic_left_light, "", 0, "");


        mEtBillNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mEtBillNo.getText().toString().trim())) {
                        AlertUtil.showToast("请输入单据！");
                        return false;
                    }
                    hideSoftInput();
                    return true;
                }
                return false;
            }
        });

        mAdapter = new TodaySalesAdapter(this,mDataList);
        mListView.setAdapter(mAdapter);

    }

    //初始化数据和网络接口
    @Override
    protected void initData() {

    }

    //搜索
    @OnClick(R.id.ib_search)
    public void onClickSearch(){
        if(TextUtils.isEmpty(mEtBillNo.getText().toString().trim())){
            AlertUtil.showToast("请输入单据！");
            return;
        }

        hideSoftInput();


    }


    /*隐藏软键盘*/
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mEtBillNo.getApplicationWindowToken(), 0);
        }
    }

}
