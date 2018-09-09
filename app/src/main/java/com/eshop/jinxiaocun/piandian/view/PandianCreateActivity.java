package com.eshop.jinxiaocun.piandian.view;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoHuoquBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianStoreJigouBean;
import com.eshop.jinxiaocun.widget.DrawableTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class PandianCreateActivity extends CommonBaseActivity implements INetWorResult {

    @BindView(R.id.tvPandianpihao)
    DrawableTextView mTvPandianpihao;
    @BindView(R.id.tv_pd_storeNo)
    TextView mTvStoreNo;
    @BindView(R.id.tv_pd_fanwei)
    TextView mTvFanwei;
    @BindView(R.id.tv_pd_type)
    TextView mTvType;
    @BindView(R.id.tv_pd_operId)
    TextView mTvOperId;
    @BindView(R.id.tv_pd_date)
    TextView mTvDate;
    @BindView(R.id.et_pd_bz)
    EditText mEtBz;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pandian_create;
    }


    //取盘点门店机构
    private void getPandianStoreJigouData(){
        PandianStoreJigouBean bean = new PandianStoreJigouBean();
        bean.JsonData.as_branchNo ="";//门店号
        bean.JsonData.as_posId =""; //Pos ID
        bean.JsonData.trans_no ="PI";//PI 单据类型
        bean.JsonData.branch_type ="Y";
//        mServerApi.getPandianStoreJigouData(bean);
    }


    @Override
    protected void initView() {
        super.initView();
        setTopToolBar("盘点生成单",R.mipmap.ic_left_light,"",0,"");
        mTvPandianpihao.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent = new Intent(PandianCreateActivity.this,PandianPihaoListActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }


    @OnClick(R.id.btn_pd_next)
    public void onClickNext(View v){
        if(TextUtils.isEmpty(mTvPandianpihao.getText().toString())){
            Toast.makeText(PandianCreateActivity.this,"盘点批号不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(PandianCreateActivity.this,PandianScanActivity.class);
        intent.putExtra("sheet_no",mTvPandianpihao.getText().toString());
        startActivity(intent);

    }


    @Override
    public void handleResule(int flag, Object o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode==11){
            PandianPihaoHuoquBeanResult obj = (PandianPihaoHuoquBeanResult) data.getSerializableExtra("PandianPihao");

            mTvPandianpihao.setText(obj.getSheet_no());
            mTvStoreNo.setText(obj.getBranch_name());
            mTvFanwei.setText(obj.getOper_range_name());
            mTvType.setText(obj.getCheck_cls());
            mTvOperId.setText(obj.getOper_id());
            mTvDate.setText(obj.getOper_date());
            mEtBz.setText(obj.getMemo());

        }

    }
}
