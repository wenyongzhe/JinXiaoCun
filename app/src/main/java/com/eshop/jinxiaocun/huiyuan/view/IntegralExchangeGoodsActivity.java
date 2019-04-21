package com.eshop.jinxiaocun.huiyuan.view;

import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.adapter.IntegralExchangeGoodsAdapter;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralExchangeGoodsResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/21
 * Desc: 查看兑换商品
 */

public class IntegralExchangeGoodsActivity extends CommonBaseActivity implements INetWorResult{


    @BindView(R.id.lv_exchange_goods)
    ListView mListView;

    private IMemberList mApi;
    private IntegralExchangeGoodsAdapter mAdapter;

    private List<IntegralExchangeGoodsResultItem> mDatas = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_exchange_goods;
    }

    @Override
    protected void initView() {
        setTopToolBar("可兑换的礼品", R.mipmap.ic_left_light,"",0,"");
        mAdapter = new IntegralExchangeGoodsAdapter(mDatas);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        mApi = new MemberImp(this);
        String cardNo = getIntent().getStringExtra("CardNo");
        float integral = getIntent().getFloatExtra("Integral",0);
        mApi.getIntegralExchangeGoods(cardNo,integral);

    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_OK:

                break;

            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(o.toString());
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatas!=null){
            mDatas.clear();
            mDatas=null;
        }
    }
}
