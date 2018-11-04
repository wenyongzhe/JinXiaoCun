package com.eshop.jinxiaocun.peisong.view;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.j256.ormlite.stmt.query.In;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/11/3
 * Desc:
 */

public class ModifyGoodsInfoActivity extends CommonBaseActivity {


    @BindView(R.id.tv_product_name)
    TextView mTvProductName;
    @BindView(R.id.tv_product_code)
    TextView mTvProductCode;
    @BindView(R.id.tv_product_price)
    EditText mEtProductPrice;
    @BindView(R.id.tv_product_number)
    EditText mEtProductNumber;

    private GetClassPluResult mSelectGoodsEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_goods_info;
    }

    @Override
    protected void initView() {
        super.initView();
        setTopToolBar("修改价格或数量", R.mipmap.ic_left_light, "", 0, "");
        setTopToolBarRightTitleAndStyle("删除",R.drawable.border_bg);
    }

    @Override
    protected void initData() {
        super.initData();

        mSelectGoodsEntity = (GetClassPluResult) getIntent().getSerializableExtra("SelectGoodsEntity");
        if(mSelectGoodsEntity !=null){
            mTvProductName.setText(mSelectGoodsEntity.getItem_name());
            mTvProductCode.setText(mSelectGoodsEntity.getItem_no());
            mEtProductPrice.setText(mSelectGoodsEntity.getSale_price());
            mEtProductNumber.setText(mSelectGoodsEntity.getSale_qnty());
        }

    }

    @Override
    protected void onTopBarRightClick() {

    }

    @OnClick(R.id.btn_sure_modify)
    public void onClickSure(){

        if(mSelectGoodsEntity ==null){
            AlertUtil.showToast("没有商品信息！");
            return;
        }

        if(TextUtils.isEmpty(mEtProductPrice.getText().toString().trim())){
            AlertUtil.showToast("请输入价格！");
            return;
        }

        if(TextUtils.isEmpty(mEtProductNumber.getText().toString().trim())){
            AlertUtil.showToast("请输入数量！");
            return;
        }

        if(MyUtils.convertToFloat(mEtProductPrice.getText().toString().trim(),0)==0f){
            AlertUtil.showToast("价格不能空0！");
            return;
        }

        if(MyUtils.convertToInt(mEtProductNumber.getText().toString().trim(),0)==0){
            AlertUtil.showToast("数量不能空0！");
            return;
        }

        mSelectGoodsEntity.setSale_price(mEtProductPrice.getText().toString().trim());
        mSelectGoodsEntity.setSale_qnty(mEtProductNumber.getText().toString().trim());

        Intent intent = new Intent();
        intent.putExtra("ModifyGoodsInfo",mSelectGoodsEntity);
        setResult(RESULT_OK,intent);
        finish();
    }

}
