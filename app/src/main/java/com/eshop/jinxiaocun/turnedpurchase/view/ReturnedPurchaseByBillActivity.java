package com.eshop.jinxiaocun.turnedpurchase.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.turnedpurchase.adapter.ReturnedPurchaseByBillAdapter;
import com.eshop.jinxiaocun.othermodel.bean.ReturnedPurchaseResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/7/17
 * Desc:   按单退货
 */
public class ReturnedPurchaseByBillActivity extends CommonBaseActivity implements INetWorResult {

    @BindView(R.id.et_billNo)
    EditText mEditBillNo;
    @BindView(R.id.tv_allMoney)
    TextView mTxtAllMoney;//应退金额
    @BindView(R.id.lv_billData)
    ListView mListView;

    private IOtherModel mServerApi;
    private ReturnedPurchaseByBillAdapter mAdapter;
    private List<ReturnedPurchaseResult> mDataList = new ArrayList<>();
    private ReturnedPurchaseResult mSelectMain;
    private int modifyQtyPosition =-1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_returned_purchase_by_bill;
    }


    @Override
    protected void initView() {
        setTopToolBar("按单退货", R.mipmap.ic_left_light, "", 0, "");

        mEditBillNo.setOnKeyListener(onKey);
        mAdapter = new ReturnedPurchaseByBillAdapter(this,mDataList);
        mAdapter.setCallback(new ReturnedPurchaseByBillAdapter.ModifyCallback() {
            @Override
            public void onModifyReQty(int position , String qty) {
                modifyQtyPosition = position;
                Intent intent = new Intent(ReturnedPurchaseByBillActivity.this, ModifyCountDialog.class);
                intent.putExtra("countN",qty);
                startActivityForResult(intent,111);
            }

        });
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setItemClickPosition(position);
                mAdapter.notifyDataSetChanged();
                mSelectMain = mDataList.get(position);
                float retuenMoney = mSelectMain.getSale_price()*mSelectMain.getSale_qnty();
                mTxtAllMoney.setText(String.format("应退金额:￥%s",retuenMoney));
            }
        });

    }

    @Override
    protected void initData() {

        mServerApi = new OtherModelImp(this);

//        float allMoney =0;
//        for(int i=0;i<20;i++){
//            ReturnedPurchaseResult bean = new ReturnedPurchaseResult();
//            bean.setFlow_no("NO_00001");
//            bean.setItem_no("AA"+i);
//            bean.setItem_name("天仙苹果_"+i);
//            bean.setRe_qty(i<<1);
//            bean.setSale_qnty(0);
//            bean.setSale_price((i<<1)+1);
//
//            mDataList.add(bean);
//            allMoney+=bean.getRe_qty()*bean.getSale_price();
//        }
//
//        mAdapter.add(mDataList);
//        mTxtAllMoney.setText(String.format("应退金额:￥%s",allMoney));
    }

    //手动输入条码事件
    View.OnKeyListener onKey= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_UP){
                onClickSearch();
                return true;
            }
            return false;
        }
    };

    //按单号搜索 退货单
    @OnClick(R.id.ib_search)
    public void onClickSearch(){
        if(TextUtils.isEmpty(mEditBillNo.getText().toString().trim())){
            AlertUtil.showToast("请输入单据号!");
            return;
        }
        mServerApi.getSalesRecordDatas(mEditBillNo.getText().toString().trim());
    }

    //全退
    @OnClick(R.id.btn_allReturned)
    public void onClickAllReturned(){

        AlertUtil.showAlert(this, R.string.dialog_title,
                "确定要退掉单据中的所有商品，"+mTxtAllMoney.getText().toString(),
                R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                    }
                },
                R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                    }
                });
    }

    //确定退货
    @OnClick(R.id.btn_sureReturned)
    public void onClickSureReturned(){
        if(mSelectMain==null){
            AlertUtil.showToast("请选择要退货的商品!");
            return;
        }
        AlertUtil.showAlert(this, R.string.dialog_title,
                "确定要退掉（"+mSelectMain.getItem_name()+"）商品，"+mTxtAllMoney.getText().toString(),
                R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                    }
                },
                R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK){
            return;
        }

        //修改数量
        if(requestCode == 111 && data!=null){
            String count = data.getStringExtra("countN");

            for (int i = 0; i < mDataList.size(); i++) {
                if(i==modifyQtyPosition){

                    if(MyUtils.convertToInt(count,0)>mDataList.get(i).getRe_qty()){
                        AlertUtil.showToast(String.format("退货数量%s超过可退数量%s",count,mDataList.get(i).getRe_qty()));
                        return;
                    }
                    mDataList.get(i).setSale_qnty(MyUtils.convertToInt(count,0));
                    //如果修改退货数量刚好也选中的商品，那么重新计算应退金额
                    if(mSelectMain!=null && mSelectMain.getItem_no().equals(mDataList.get(i).getItem_no())){
                        mSelectMain = mDataList.get(i);
                        float retuenMoney = mSelectMain.getSale_price()*mSelectMain.getSale_qnty();
                        mTxtAllMoney.setText(String.format("应退金额:￥%s",retuenMoney));
                    }

                    break;
                }
            }

            mAdapter.add(mDataList);

        }


    }

    //网络返回的数据处理
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
                mDataList = (List<ReturnedPurchaseResult>) o;
                mAdapter.add(mDataList);

                float allMoney =0;
                for(ReturnedPurchaseResult bean:mDataList){
                    allMoney+=bean.getRe_qty()*bean.getSale_price();
                }
                mTxtAllMoney.setText(String.format("应退金额:￥%s",allMoney));

                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(o.toString());
                break;
        }
    }
}