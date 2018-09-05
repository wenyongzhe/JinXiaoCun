package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBean;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.piandian.bean.PandianFanweiBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBeanResultItem;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoCreateBean;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoCreateBeanResult;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.DrawableTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class PandianPihaoCreateActivity extends CommonBaseActivity implements INetWorResult {


    @BindView(R.id.tv_pd_pihao)
    TextView mTvPihao;
    @BindView(R.id.tv_pd_storeNo)
    TextView mTvStoreNo;
    @BindView(R.id.tv_pd_operId)
    TextView mTvOperId;
    @BindView(R.id.tv_pd_date)
    TextView mTvDate;
    @BindView(R.id.tv_pd_bz)
    TextView mTvBz;

    @BindView(R.id.tvPandianFanwei)
    DrawableTextView mTvPandianfanwei;
    @BindView(R.id.tvPandianType)
    DrawableTextView mTvPandianType;
    private IPandian mServerApi;
    private PandianFanweiBeanResult mSelectPandianFanweiBeanEntity = null;
    private PandianLeibieBeanResultItem mSelectPandianLeibieBeanEntity = null;

    private boolean isApplySuccess;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pandain_pihao_create;
    }

    @Override
    protected void loadData() {
//        mServerApi = new PandianImp(this);

        IOtherModel api = new OtherModelImp(this);
        SheetNoBean bean = new SheetNoBean();
        bean.JsonDate.trans_no="PI";
        bean.JsonDate.branch_no="0001";
        api.getSheetNoData(bean);

    }


    //盘点批号生成
    private void getPandianPihaoCreateData(){

        if(isApplySuccess){
            Toast.makeText(this,"盘点批号申请成功，不能重复申请!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(mSelectPandianFanweiBeanEntity == null){
            Toast.makeText(this,"请选择盘点范围!",Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (mSelectPandianFanweiBeanEntity.getType_name().contains("类别") || mSelectPandianFanweiBeanEntity.getType_name().contains("品牌")) {
                if(mSelectPandianLeibieBeanEntity == null){
                    Toast.makeText(this,"请选择类别品牌!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        if(TextUtils.isEmpty(mTvStoreNo.getText().toString())){
            Toast.makeText(this,"门店号不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(mTvPihao.getText().toString())){
            Toast.makeText(this,"盘点批次号不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(mTvOperId.getText().toString())){
            Toast.makeText(this,"操作员ID不能为空!",Toast.LENGTH_SHORT).show();
            return;
        }


        PandianPihaoCreateBean bean = new PandianPihaoCreateBean();
        bean.JsonData.as_sheetno =mTvPihao.getText().toString();//盘点批次号
        bean.JsonData.as_branch_no =mTvStoreNo.getText().toString().trim();//门店号
        bean.JsonData.as_oper_range =mSelectPandianFanweiBeanEntity.getType_id(); //盘点范围
        bean.JsonData.as_check_cls =mSelectPandianLeibieBeanEntity==null?"":mSelectPandianLeibieBeanEntity.getType_no(); //盘点类别
        bean.JsonData.as_oper_id =mTvOperId.getText().toString().trim(); //操作员ID
        bean.JsonData.as_oper_date =mTvDate.getText().toString(); //操作日期
        bean.JsonData.as_memo =TextUtils.isEmpty(mTvBz.getText().toString().trim())?"":mTvBz.getText().toString().trim(); //备注
        mServerApi.getPandianPihaoCreateData(bean);




    }

    @Override
    protected void initView() {
        setTopToolBar("盘点批号申请",R.mipmap.ic_left_light,"",0,"");

        mTvPihao.setText("PD20180001");
        mTvStoreNo.setText("0001");
        mTvOperId.setText("1001");
        mTvDate.setText("2018-9-5");
        mTvBz.setText("测试");


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

    @OnClick({R.id.btn_apply})
    public void onClickApply(View view){
        getPandianPihaoCreateData();
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
                PandianPihaoCreateBeanResult obj = (PandianPihaoCreateBeanResult) o;
                isApplySuccess = false;
                if(obj.status.equals("0")){
                    isApplySuccess = true;
                    Toast.makeText(PandianPihaoCreateActivity.this,obj.msg,Toast.LENGTH_SHORT).show();
                }
                break;
            case Config.MESSAGE_PANDIANPIHAOCREATE_ERROR:
                isApplySuccess = false;
                Toast.makeText(PandianPihaoCreateActivity.this,"申请失败："+o.toString(),Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
