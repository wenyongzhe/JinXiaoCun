package com.eshop.jinxiaocun.piandian.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBean;
import com.eshop.jinxiaocun.piandian.bean.PandianStoreJigouBean;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.DrawableTextView;

public class PandianCreateActivity extends BaseActivity implements INetWorResult,ActionBarClickListener {


    private IPandian mServerApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    @Override
    protected void loadData() {
        mServerApi = new PandianImp(this);
//        getPandianFanweiData();
//        getPandianLeibieData();
//        getPandianStoreJigouData();
//        getPandianPihaoCreateData();
//        getPandianPihaoHuoqu();
//        getPandianDetailData();
    }

////    取盘点范围数据
//    private void getPandianFanweiData(){
//        mServerApi.getPandianFanweiData(new PandianFanweiBean());
//    }

//    //取盘点类别数据
//    private void getPandianLeibieData(){
//        PandianLeibieBean bean = new PandianLeibieBean();
//        bean.jsonData.as_branchNo="";//门店号
//        bean.jsonData.as_posId="";//pos id
//        bean.jsonData.as_type="1";//'1'类别 '0' 品牌
//        bean.jsonData.as_clsorbrno="";//指定的类型或者品牌
//        mServerApi.getPandianTypeData(bean);
//    }

    //取盘点门店机构
    private void getPandianStoreJigouData(){
        PandianStoreJigouBean bean = new PandianStoreJigouBean();
        bean.JsonData.as_branchNo ="";//门店号
        bean.JsonData.as_posId =""; //Pos ID
        bean.JsonData.trans_no ="PI";//PI 单据类型
        bean.JsonData.branch_type ="Y";
        mServerApi.getPandianStoreJigouData(bean);
    }

//    //盘点批号生成
//    private void getPandianPihaoCreateData(){
//        PandianPihaoCreateBean bean = new PandianPihaoCreateBean();
//        bean.jsonData.as_sheetno ="PD20180001";//盘点批次号
//        bean.jsonData.as_branch_no ="0001";//门店号
//        bean.jsonData.as_oper_range ="0"; //盘点范围
//        bean.jsonData.as_check_cls =""; //盘点类别
//        bean.jsonData.as_oper_id ="1001"; //操作员ID
//        bean.jsonData.as_oper_date =""; //操作日期
//        bean.jsonData.as_memo =""; //备注
//        mServerApi.getPandianPihaoCreateData(bean);
//    }

//    //盘点批号获取
//    private void getPandianPihaoHuoqu(){
//        PandianPihaoHuoquBean bean = new PandianPihaoHuoquBean();
//        bean.jsonData.sheet_no="PD20180001";//获取所有可填%
//        bean.jsonData.trans_no="PD";//单据标识
//        bean.jsonData.PerNum=10;//每页显示数量
//        bean.jsonData.PageNum=1;//页码
//        bean.jsonData.approveflag="1"; //审核标识
//        bean.jsonData.branch_no=""; //机构号(保留)
//        mServerApi.getPandianPihaoHuoqu(bean);
//
//    }

    //获取盘点明细
    private void getPandianDetailData(){
        PandianDetailBean bean = new PandianDetailBean();
        bean.JsonData.sheet_no="201807055551";//盘点批号
        mServerApi.getPandianDetailData(bean);
    }

    @Override
    protected void initView() {
        mLinearLayout.addView(getView(R.layout.activity_pandian_create));
        mMyActionBar.setData("盘点生成单",R.mipmap.ic_left_light,"",0,"",this);
        DrawableTextView mTvPandianfanwei =mLinearLayout.findViewById(R.id.dtPandianpihao);
        mTvPandianfanwei.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent = new Intent(PandianCreateActivity.this,PandianPihaoListActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }


    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){

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
