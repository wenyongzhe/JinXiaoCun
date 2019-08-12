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
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.othermodel.bean.PayRecordResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.reportforms.adapter.TodayGatheringAdapter;
import com.eshop.jinxiaocun.reportforms.bean.TodayGatheringInfo;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/12
 * Desc: 今日收款
 */
public class TodayGatherActivity extends CommonBaseActivity implements INetWorResult {

    @BindView(R.id.et_billNo)
    EditText mEtBillNo;

    @BindView(R.id.lv_cashierData)
    ListView mListView;

    private IOtherModel mServerApi;
    private TodayGatheringAdapter mAdapter;
    private List<TodayGatheringInfo> mDataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_today_gathering;
    }


    //初始化控件
    @Override
    protected void initView() {
        setTopToolBar("今日收款", R.mipmap.ic_left_light, "", 0, "");


        mEtBillNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftInput();
                    onClickSearch();
                    return true;
                }
                return false;
            }
        });

        mAdapter = new TodayGatheringAdapter(this,mDataList);
        mListView.setAdapter(mAdapter);

    }

    //初始化数据和网络接口
    @Override
    protected void initData() {
        mServerApi = new OtherModelImp(this);
        loadData("");
    }

    //搜索
    @OnClick(R.id.ib_search)
    public void onClickSearch(){
        if(TextUtils.isEmpty(mEtBillNo.getText().toString().trim())){
            AlertUtil.showToast("请输入单据！");
            return;
        }

        hideSoftInput();
        loadData(mEtBillNo.getText().toString().trim());

    }


    //加载数据
    private void loadData(String billNo){
        if(!MyUtils.isOpenNetwork()){
            AlertUtil.showToast("网络不可用，请检查网络!");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在获取收款数据，请稍后...");
        mServerApi.getPayRecordDatas(billNo);
    }

    //根据返回的销售记录，单据相同算一组，并刷新UI
    private void refreshData(List<PayRecordResult> dataList){
        mDataList.clear();

        if(dataList==null){
            mAdapter.add(mDataList);
            return;
        }

        //缓存不相同的单据号
        Set<String> billNoList = new HashSet<>();
        for (PayRecordResult item : dataList) {
            billNoList.add(item.getFlow_no());
        }

        //合并相同的单据商品
        for (String billNo : billNoList) {
            List<PayRecordResult> sameGoodsList = new ArrayList<>();
            for (PayRecordResult item : dataList) {
                if(billNo.equals(item.getFlow_no())){
                    sameGoodsList.add(item);
                }
            }
            if(sameGoodsList.size()>0){
                TodayGatheringInfo info = new TodayGatheringInfo();
                info.setBillNo(sameGoodsList.get(0).getFlow_no());
                info.setBillDate(sameGoodsList.get(0).getOper_date());
                info.setPayRecordInfos(sameGoodsList);
                mDataList.add(info);
            }
        }

        //刷新UI
        mAdapter.add(mDataList);

    }

    /*隐藏软键盘*/
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mEtBillNo.getApplicationWindowToken(), 0);
        }
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.PAY_RECORD_SUCCESS://获取单据的付款记录
                List<PayRecordResult> resultList = (List<PayRecordResult>) o;
                refreshData(resultList);
                AlertUtil.dismissProgressDialog();
                break;
            case Config.RESULT_FAIL:
                AlertUtil.showToast((String) o);
                AlertUtil.dismissProgressDialog();
                break;

        }
    }
}
