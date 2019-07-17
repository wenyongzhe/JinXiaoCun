package com.eshop.jinxiaocun.turnedpurchase.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.turnedpurchase.adapter.ReturnedPurchaseByBillAdapter;
import com.eshop.jinxiaocun.turnedpurchase.bean.ReturnedPurchaseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/7/17
 * Desc:
 */
public class ReturnedPurchaseByBillActivity extends CommonBaseActivity {

    @BindView(R.id.et_billNo)
    EditText mEditBillNo;
    @BindView(R.id.tv_allMoney)
    TextView mTxtAllMoney;//应退金额
    @BindView(R.id.lv_billData)
    ListView mListView;

    private ReturnedPurchaseByBillAdapter mAdapter;
    private List<ReturnedPurchaseBean> mDataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_returned_purchase_by_bill;
    }


    @Override
    protected void initView() {
        setTopToolBar("按单退货", R.mipmap.ic_left_light, "", 0, "");


        mAdapter = new ReturnedPurchaseByBillAdapter(mDataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setItemClickPosition(position);
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void initData() {


        for(int i=0;i<20;i++){
            ReturnedPurchaseBean bean = new ReturnedPurchaseBean();
            bean.setBillNo("RP0000"+i);
            bean.setBillType("该单非销售单，不能退货"+i);
            bean.setPrice(i);
            bean.setReQty(i<<2);
            bean.setQty(i<<2-1);

            mDataList.add(bean);
        }

        mAdapter.add(mDataList);

    }

    //按单号搜索 退货单
    @OnClick(R.id.ib_search)
    public void onClickSearch(){

    }

    //全退
    @OnClick(R.id.btn_allReturned)
    public void onClickAllReturned(){

    }

    //确定退货
    @OnClick(R.id.btn_sureReturned)
    public void onClickSureReturned(){

    }

}
