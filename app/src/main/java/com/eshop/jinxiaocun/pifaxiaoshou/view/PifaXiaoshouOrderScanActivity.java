package com.eshop.jinxiaocun.pifaxiaoshou.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.othermodel.bean.CustomerInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.view.SelectCustomerListActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DrawableTextView;

import java.util.List;

import butterknife.BindView;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/17
 * Desc: 批发销售订单扫描
 */

public class PifaXiaoshouOrderScanActivity extends CommonBaseScanActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;
    @BindView(R.id.tv_pf_user)
    DrawableTextView mTvUser;
    @BindView(R.id.tv_pf_store)
    TextView mTvUserStore;

    private CustomerInfoBeanResult mCustomerInfo;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pifa_xiaoshou_order_scan;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("批发订单生成", R.mipmap.ic_left_light, "", R.mipmap.add, "");
        mEtBarcode.setOnKeyListener(onKey);
        mBtnAdd.setText(R.string.btnSave);
        mTvUserStore.setText("[1001]总仓库");

        mTvUser.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent = new Intent(PifaXiaoshouOrderScanActivity.this, SelectCustomerListActivity.class);
                startActivityForResult(intent,2);
            }
        });

        setHeaderTitle(R.id.tv_0,R.string.list_item_ProdName,150);//商品名称
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdCode,150);//商品编码
        setHeaderTitle(R.id.tv_2,R.string.list_item_Spec,100);//规格
        setHeaderTitle(R.id.tv_3,R.string.list_item_Price,100);//价格
        setHeaderTitle(R.id.tv_4,R.string.list_item_XSPrice,100);//销售价格
        setHeaderTitle(R.id.tv_5,R.string.list_item_Unit,80);//单位
        setHeaderTitle(R.id.tv_6,R.string.list_item_StoreNum,100);//库存数量
        setHeaderTitle(R.id.tv_7,R.string.list_item_CountN4,100);//盘点数量


    }

    @Override
    protected void initData() {
        super.initData();
    }

    //手动输入条码事件
    View.OnKeyListener onKey= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_UP){
                scanResultData(mEtBarcode.getText().toString().trim());
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onTopBarRightClick() {
        super.onTopBarRightClick();
        Intent mIntent = new Intent(this, QreShanpingActivity.class);
        startActivityForResult(mIntent,1);
    }

    @Override
    public void handleResule(int flag, Object o) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Config.RESULT_SELECT_GOODS){
            List<GetClassPluResult> selectGoodsList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
        }

        //选择的客户
        if(requestCode == 2 && resultCode == 22){
            mCustomerInfo = (CustomerInfoBeanResult) data.getSerializableExtra("CustomerInfo");
            mTvUser.setText(mCustomerInfo.getName());
        }


    }

    @Override
    protected boolean scanBefore() {
        return true;
    }

    @Override
    protected void scanResultData(String barcode) {

    }

    //保存前
    @Override
    protected boolean addBefore() {
        if(TextUtils.isEmpty(mTvUser.getText().toString().trim())){
            AlertUtil.showToast("请选择客户，再保存!");
            return false;
        }
        return true;
    }

    @Override
    protected void addAfter() {

    }

    @Override
    protected boolean deleteBefore() {
        return false;
    }

    @Override
    protected void deleteAfter() {

    }

    @Override
    protected boolean modifyCountBefore() {
        return false;
    }

    @Override
    protected void modifyCountAfter() {

    }

}
