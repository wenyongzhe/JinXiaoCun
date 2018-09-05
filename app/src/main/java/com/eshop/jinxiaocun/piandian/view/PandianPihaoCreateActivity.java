package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.view.View;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.piandian.bean.PandianFanweiBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBeanResultItem;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoCreateBean;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.DrawableTextView;

import butterknife.BindView;

public class PandianPihaoCreateActivity extends CommonBaseActivity implements INetWorResult {

    @BindView(R.id.tvPandianFanwei)
    DrawableTextView mTvPandianfanwei;
    @BindView(R.id.tvPandianType)
    DrawableTextView mTvPandianType;
    private IPandian mServerApi;
    private PandianFanweiBeanResult mSelectPandianFanweiBeanEntity = null;
    private PandianLeibieBeanResultItem mSelectPandianLeibieBeanEntity = null;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pandain_pihao_create;
    }

    @Override
    protected void loadData() {
        mServerApi = new PandianImp(this);
    }


    //盘点批号生成
    private void getPandianPihaoCreateData(){
        PandianPihaoCreateBean bean = new PandianPihaoCreateBean();
        bean.JsonData.as_sheetno ="";//盘点批次号PD20180001
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
        setTopToolBar("盘点批申请",R.mipmap.ic_left_light,"",0,"");
        mTvPandianfanwei.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent = new Intent(PandianPihaoCreateActivity.this,SelectPandianFanweiDialogActivity.class);
                intent.putExtra("PandianFanwei",mSelectPandianFanweiBeanEntity);
                startActivityForResult(intent,1);
            }
        });
        mTvPandianType.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                if(mSelectPandianFanweiBeanEntity !=null ){
                    if(mSelectPandianFanweiBeanEntity.getType_name().contains("类别")||mSelectPandianFanweiBeanEntity.getType_name().contains("品牌")){
                        Intent intent = new Intent(PandianPihaoCreateActivity.this,SelectPandianTypeDialogActivity.class);
                        //'1'类别 '0' 品牌
                        intent.putExtra("as_type",mSelectPandianFanweiBeanEntity.getType_name().contains("类别")?"1":"0");
                        intent.putExtra("PandianLeibie",mSelectPandianLeibieBeanEntity);
                        startActivityForResult(intent,2);
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == 11){
            mSelectPandianFanweiBeanEntity = (PandianFanweiBeanResult) data.getSerializableExtra("PandianFanwei");
            if(mSelectPandianFanweiBeanEntity !=null){
                mTvPandianfanwei.setText(mSelectPandianFanweiBeanEntity.getType_name());
                if(!mSelectPandianFanweiBeanEntity.getType_name().contains("类别")||!mSelectPandianFanweiBeanEntity.getType_name().contains("品牌")){
                    mTvPandianType.setText("");
                }
            }


        }

        if(requestCode == 2 && resultCode == 22){
            mSelectPandianLeibieBeanEntity = (PandianLeibieBeanResultItem) data.getSerializableExtra("PandianLeibie");
            if(mSelectPandianLeibieBeanEntity !=null){
                mTvPandianType.setText(mSelectPandianLeibieBeanEntity.getType_name());
            }
        }


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

}
