package com.eshop.jinxiaocun.piandian.view;


import android.os.Bundle;
import android.view.View;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.piandian.bean.PandianFanweiBean;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBean;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoCreateBean;
import com.eshop.jinxiaocun.piandian.bean.PandianStoreJigouBean;
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
//        getPandianFanweiData();
//        getPandianLeibieData();
//        getPandianStoreJigouData();
        getPandianPihaoCreateData();
    }

    //取盘点范围数据
    private void getPandianFanweiData(){
        mServerApi.getPandianFanweiData(new PandianFanweiBean());
    }

    //取盘点类别数据
    private void getPandianLeibieData(){
        PandianLeibieBean bean = new PandianLeibieBean();
        bean.JsonData.as_branchNo="";//门店号
        bean.JsonData.as_posId="";//pos id
        bean.JsonData.as_type="1";//'1'类别 '0' 品牌
        bean.JsonData.as_clsorbrno="";//指定的类型或者品牌
        mServerApi.getPandianTypeData(bean);
    }

    //取盘点门店机构
    private void getPandianStoreJigouData(){
        PandianStoreJigouBean bean = new PandianStoreJigouBean();
        bean.JsonData.as_branchNo ="";//门店号
        bean.JsonData.as_posId =""; //Pos ID
        bean.JsonData.trans_no ="PI";//PI 单据类型
        bean.JsonData.branch_type ="Y";
        mServerApi.getPandianStoreJigouData(bean);
    }

    //盘点批号生成
    private void getPandianPihaoCreateData(){
        PandianPihaoCreateBean bean = new PandianPihaoCreateBean();
        bean.JsonData.as_sheetno ="PD20180001";//盘点批次号
        bean.JsonData.as_branch_no ="0001";//门店号
        bean.JsonData.as_oper_range ="0"; //盘点范围
        bean.JsonData.as_check_cls =""; //盘点类别
        bean.JsonData.as_oper_id ="1001"; //操作员ID
        bean.JsonData.as_oper_date =""; //操作日期
        bean.JsonData.as_memo =""; //备注
        mServerApi.getPandianPihaoCreateData(bean);
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
            case Config.MESSAGE_PANDIANLEIBIE_OK:
                break;
            case Config.MESSAGE_PANDIANLEIBIE_ERROR:
                break;
            //取盘点门店机构
            case Config.MESSAGE_PANDIANSTOREJIGOU_OK:
                break;
            case Config.MESSAGE_PANDIANSTOREJIGOU_ERROR:
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