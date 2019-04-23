package com.eshop.jinxiaocun.huiyuan.view;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
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

import java.io.Serializable;
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
    private float mIntegral;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_exchange_goods;
    }

    @Override
    protected void initView() {

        mAdapter = new IntegralExchangeGoodsAdapter(mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                int selectQry=0;
                for (IntegralExchangeGoodsResultItem data : mDatas) {
                    if(data.isSelect()){
                        selectQry++;
                    }
                }

                for (int i = 0; i < mDatas.size(); i++) {
                    if(i==position){
                        if(mDatas.get(i).isSelect()){//如果选中，再次点击则视为放弃选中
                            mDatas.get(i).setSelect(false);
                        }else{
                            if(selectQry>5 || selectQry==5){
                                AlertUtil.showToast("最多可选5种礼品！");
                                return;
                            }
                            mDatas.get(i).setSelect(true);
                        }
                    }
                }

                mAdapter.setListInfo(mDatas);

            }
        });
    }

    @Override
    protected void initData() {
        mApi = new MemberImp(this);
        String cardNo = getIntent().getStringExtra("CardNo");
        mIntegral = getIntent().getFloatExtra("Integral",0);
        setTopToolBar("可兑换的礼品("+mIntegral+"积分)", R.mipmap.ic_left_light,"",0,"");
        mApi.getIntegralExchangeGoods(cardNo,mIntegral);

    }

    @Override
    protected boolean onTopBarLeftClick() {
        returnActivity();
        return true;
    }

    private void returnActivity(){
        float allSelectIntegral=0;
        List<IntegralExchangeGoodsResultItem> selectDatas = new ArrayList<>();
        for (IntegralExchangeGoodsResultItem info : mDatas) {
            if(info.isSelect()){
                allSelectIntegral = +info.getJiFen();
                selectDatas.add(info);
            }
        }
        if(selectDatas.size()>0){

            if((mIntegral-allSelectIntegral)<0){
                AlertUtil.showToast("当前各种积分不足，请放弃部分礼品!");
                return;
            }

            Intent intent = new Intent();
            intent.putExtra("SelectDatas",(Serializable)selectDatas);
            setResult(RESULT_OK,intent);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode==KeyEvent.KEYCODE_BACK){
            returnActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
                mDatas = (List<IntegralExchangeGoodsResultItem>) o;
                mAdapter.setListInfo(mDatas);
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
