package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoCreateBean;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.DrawableTextView;

public class PandianPihaoCreateActivity extends BaseActivity implements INetWorResult,ActionBarClickListener {


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
        mLinearLayout.addView(getView(R.layout.activity_pandain_pihao_create));
        mMyActionBar.setData("盘点批申请",R.mipmap.ic_left_light,"",0,"",this);

        DrawableTextView mTvPandianfanwei =mLinearLayout.findViewById(R.id.tvPandianFanwei);
        mTvPandianfanwei.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent = new Intent(PandianPihaoCreateActivity.this,SelectPandianFanweiDialogActivity.class);
                startActivityForResult(intent,1);
            }
        });
        DrawableTextView mTvPandianType =mLinearLayout.findViewById(R.id.tvPandianType);
        mTvPandianType.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent = new Intent(PandianPihaoCreateActivity.this,SelectPandianTypeDialogActivity.class);
                startActivityForResult(intent,2);
            }
        });
    }


    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //盘点批号生成
            case Config.MESSAGE_PANDIANPIHAOCREATE_OK:

                break;
            case Config.MESSAGE_PANDIANPIHAOCREATE_ERROR:

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
