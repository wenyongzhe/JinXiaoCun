package com.eshop.jinxiaocun.piandian.view;


import android.os.Bundle;
import android.view.View;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.piandian.bean.PandianFanweiBean;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBean;
import com.eshop.jinxiaocun.piandian.presenter.IPandianCreat;
import com.eshop.jinxiaocun.piandian.presenter.PandianCreatImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.DrawableTextView;

public class PandianCreateActivity extends BaseActivity implements INetWorResult,ActionBarClickListener {


    private IPandianCreat mServerApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    @Override
    protected void loadData() {
        mServerApi = new PandianCreatImp(this);
//        mServerApi.getPandianFanweiData(new PandianFanweiBean());

        PandianLeibieBean pandianLeibieBean = new PandianLeibieBean();
        pandianLeibieBean.JsonData.as_branchNo="";//门店号
        pandianLeibieBean.JsonData.as_posId="";//pos id
        pandianLeibieBean.JsonData.as_type="1";//'1'类别 '0' 品牌
        pandianLeibieBean.JsonData.as_clsorbrno="";//指定的类型或者品牌
        mServerApi.getPandianTypeData(pandianLeibieBean);
    }

    @Override
    protected void initView() {
        mLinearLayout.addView(getView(R.layout.activity_pandian_create));
        mMyActionBar.setData("盘点生成单",R.mipmap.ic_left_light,"",R.mipmap.add,"",this);
        DrawableTextView mTvPandianfanwei =mLinearLayout.findViewById(R.id.dtPandianfanwei);
        mTvPandianfanwei.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {

            }
        });
    }


    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //取盘点范围
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                break;
            //取盘点类别
            case Config.MESSAGE_PandianLeibie_OK:
                break;
            case Config.MESSAGE_PandianLeibie_ERROR:
                break;
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
