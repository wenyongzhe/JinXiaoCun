package com.eshop.jinxiaocun.turnedpurchase.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.lingshou.bean.PlayFlowBean;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.PayRecordResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.turnedpurchase.adapter.ReturnedPurchaseByBillAdapter;
import com.eshop.jinxiaocun.othermodel.bean.ReturnedPurchaseResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;
import com.j256.ormlite.field.types.BooleanObjectType;

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
    private ILingshouScan mSalesServerApi;
    private ReturnedPurchaseByBillAdapter mAdapter;
    private List<ReturnedPurchaseResult> mSalesRecordDatas = new ArrayList<>();//销售记录
    private List<PayRecordResult> mPayRecordDatas = new ArrayList<>();//付款记录
    private int modifyQtyPosition =-1;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_returned_purchase_by_bill;
    }


    @Override
    protected void initView() {
        setTopToolBar("按单退货", R.mipmap.ic_left_light, "", 0, "");

        mEditBillNo.setOnKeyListener(onKey);
        mAdapter = new ReturnedPurchaseByBillAdapter(this,mSalesRecordDatas);
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


    }

    //初始化数据 或 接口
    @Override
    protected void initData() {
        mServerApi = new OtherModelImp(this);
        mSalesServerApi = new LingShouScanImp(this);
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

    /**
     * 重新计算应退金额
     * @param allReturn true为全退  false退部分
     */
    private void reCountReturnMoneys(boolean allReturn){
        float allMoney =0;
        for(ReturnedPurchaseResult bean:mSalesRecordDatas){
            if(allReturn){
                allMoney+=bean.getRe_qty()*bean.getSale_price();
            }else{
                allMoney+=bean.getRp_Qty()*bean.getSale_price();
            }

        }
        mTxtAllMoney.setText(String.format("应退金额:￥%s",allMoney));
    }

    //按单号搜索 退货单
    @OnClick(R.id.ib_search)
    public void onClickSearch(){
        if(TextUtils.isEmpty(mEditBillNo.getText().toString().trim())){
            AlertUtil.showToast("请输入单据号!");
            return;
        }
        if(!MyUtils.isOpenNetwork()){
            AlertUtil.showToast("网络不可用，请检查网络!");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在获取单据数据，请稍后...");
        mServerApi.getSalesRecordDatas(mEditBillNo.getText().toString().trim());
    }

    //全退
    @OnClick(R.id.btn_allReturned)
    public void onClickAllReturned(){

        if(mSalesRecordDatas==null || mSalesRecordDatas.size()==0){
            AlertUtil.showToast("没有单据销售记录数据，不能退货！");
        }

        if(mPayRecordDatas ==null || mPayRecordDatas.size()==0){
            AlertUtil.showToast("没有单据付款记录数据，不能退货！");
        }

        AlertUtil.showAlert(this, R.string.dialog_title,
                "确定要退掉单据中的所有商品，"+mTxtAllMoney.getText().toString(),
                R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                        AlertUtil.showNoButtonProgressDialog(ReturnedPurchaseByBillActivity.this,
                                "正在退货，请稍等...");
                        uploadSalesFlow(true);
                    }
                },
                R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                    }
                });
    }

    //确定退货（部分商品，即输入了退货数量的商品）
    @OnClick(R.id.btn_sureReturned)
    public void onClickSureReturned(){

        if(mSalesRecordDatas==null || mSalesRecordDatas.size()==0){
            AlertUtil.showToast("没有单据销售记录数据，不能退货！");
        }

        if(mPayRecordDatas ==null || mPayRecordDatas.size()==0){
            AlertUtil.showToast("没有单据付款记录数据，不能退货！");
        }

        boolean isReturn = false;
        for (ReturnedPurchaseResult item : mSalesRecordDatas) {
            if(item.getRp_Qty()>0){//退货数量大于0都退
                isReturn = true;
            }
        }
        if(!isReturn){
            AlertUtil.showToast("请录入商品的退货数量！");
            return;
        }

        AlertUtil.showAlert(this, R.string.dialog_title,
                "确定要退掉已输入退货数量的商品，"+mTxtAllMoney.getText().toString(),
                R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                        AlertUtil.showNoButtonProgressDialog(ReturnedPurchaseByBillActivity.this,
                                "正在退货，请稍等...");
                        uploadSalesFlow(false);
                    }
                },
                R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                    }
                });
    }

    /**
     * 上传销售流水
     * @param allReturn true全退 false退部分
     */
    private void uploadSalesFlow(boolean allReturn){
        List<SaleFlowBean> salesFlowDatas = new ArrayList<>();
        for(int i=0; i<mSalesRecordDatas.size(); i++){
            SaleFlowBean mSaleFlowBean = new SaleFlowBean();
            ReturnedPurchaseResult goodsInfo = mSalesRecordDatas.get(i);

            mSaleFlowBean.setBranch_no(Config.branch_no);
            mSaleFlowBean.setFlow_no(goodsInfo.getFlow_no());
            mSaleFlowBean.setFlow_id((i+1)+"");
            mSaleFlowBean.setItem_no(goodsInfo.getItem_no());
            mSaleFlowBean.setSource_price(MyUtils.convertToString(goodsInfo.getSource_price(),"0"));
            mSaleFlowBean.setSale_price(MyUtils.convertToString(goodsInfo.getSale_price(),"0"));

            if(allReturn){//全退
                mSaleFlowBean.setSale_qnty("-"+MyUtils.convertToString(goodsInfo.getRe_qty(),"0"));
                mSaleFlowBean.setSale_money(String.format("-%s",goodsInfo.getRe_qty()*goodsInfo.getSale_price()));
            }else{
                //退部分
                if(goodsInfo.getRp_Qty()==0){//只退 退货数量大于0的
                    continue;
                }
                mSaleFlowBean.setSale_qnty("-"+MyUtils.convertToString(goodsInfo.getRp_Qty(),"0"));
                mSaleFlowBean.setSale_money(String.format("-%s",goodsInfo.getRp_Qty()*goodsInfo.getSale_price()));
            }

            mSaleFlowBean.setSell_way("B");//销售方式: A销售 B退货 D赠送
            mSaleFlowBean.setSale_man(Config.saleMan);
            mSaleFlowBean.setSpec_flag("");
            mSaleFlowBean.setSpec_sheet_no("");
            mSaleFlowBean.setPosid(Config.posid);
            mSaleFlowBean.setVoucher_no("");
            mSaleFlowBean.setCounter_no("");
            mSaleFlowBean.setOper_id(Config.UserName);
            mSaleFlowBean.setOper_date(DateUtility.getCurrentTime());
            mSaleFlowBean.setIsfreshcodefrag("");
            mSaleFlowBean.setBatch_code("");//批次号  销售记录没有返回？
            mSaleFlowBean.setBatch_made_date("");//批次生产日期
            mSaleFlowBean.setBatch_valid_date("");//批次有效期
            if(i == (mSalesRecordDatas.size()-1)){////表示该单结束标识  1：结束
                mSaleFlowBean.setbDealFlag("1");
            }else{
                mSaleFlowBean.setbDealFlag("0");
            }

            salesFlowDatas.add(mSaleFlowBean);
        }
        mSalesServerApi.upSallFlow(salesFlowDatas);
    }

    //上传付款流水
    private void uploadPayFlow(){
        List<PlayFlowBean> playFlowDatas = new ArrayList<>();
        for(int i=0; i<mPayRecordDatas.size(); i++){
            PayRecordResult payInfo = mPayRecordDatas.get(i);
            PlayFlowBean mPlayFlowBean = new PlayFlowBean();
            mPlayFlowBean.setBranch_no(Config.branch_no);
            mPlayFlowBean.setFlow_no(payInfo.getFlow_no());
            mPlayFlowBean.setFlow_id(1);
            mPlayFlowBean.setSale_amount(-payInfo.getSale_amount());//销售金额 这里的金额与应退金额有关联？
            mPlayFlowBean.setPay_way(payInfo.getPay_way());//支付方式
            mPlayFlowBean.setSell_way("B");//-销售方式: A销售 B退货 D赠送

            mPlayFlowBean.setCard_no(payInfo.getCard_no());//卡号
            mPlayFlowBean.setVip_no(payInfo.getVip_no());//会员卡号

            mPlayFlowBean.setCoin_no("");
            mPlayFlowBean.setCoin_rate(1);
            mPlayFlowBean.setPay_amount(-payInfo.getPay_amount());//付款金额 这里的金额与应退金额有关联？
            mPlayFlowBean.setVoucher_no("");
            mPlayFlowBean.setPosid(Config.posid);
            mPlayFlowBean.setCounter_no("9999");
            mPlayFlowBean.setOper_id(Config.UserName);
            mPlayFlowBean.setSale_man(Config.saleMan);
            mPlayFlowBean.setShift_no("");
            mPlayFlowBean.setOper_date(payInfo.getOper_date());
            mPlayFlowBean.setMemo("");
            mPlayFlowBean.setWorderno("");
            if((i+1) == mPayRecordDatas.size()){
                mPlayFlowBean.setbDealFlag("1");
            }else {
                mPlayFlowBean.setbDealFlag("0");
            }
            playFlowDatas.add(mPlayFlowBean);
        }
        mSalesServerApi.upPlayFlow(playFlowDatas);
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

            for (int i = 0; i < mSalesRecordDatas.size(); i++) {
                if(i==modifyQtyPosition){
                    if(MyUtils.convertToInt(count,0)>mSalesRecordDatas.get(i).getRe_qty()){
                        AlertUtil.showToast(String.format("退货数量%s超过可退数量%s",count,mSalesRecordDatas.get(i).getRe_qty()));
                        return;
                    }
                    //修改退货数量
                    mSalesRecordDatas.get(i).setRp_Qty(MyUtils.convertToInt(count,0));
                    break;
                }
            }
            reCountReturnMoneys(false);//重新计算应退金额
            mAdapter.add(mSalesRecordDatas);

        }


    }

    //网络返回的数据处理
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_OK://获取单据的销售记录
                mSalesRecordDatas = (List<ReturnedPurchaseResult>) o;
                mAdapter.add(mSalesRecordDatas);
                reCountReturnMoneys(true);//重新计算应退金额
                if(mSalesRecordDatas!=null && mSalesRecordDatas.size()>0){
                    //根据单号获取付款记录数据
                    mServerApi.getPayRecordDatas(mSalesRecordDatas.get(0).getFlow_no());
                }else{
                    AlertUtil.showToast("该单据下没有销售记录数据,请确认单据号是否正确!");
                    AlertUtil.dismissProgressDialog();
                }
                break;
            case Config.MESSAGE_ERROR:
            case Config.RESULT_FAIL:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
            case Config.RESULT_SUCCESS:
                //获取付款记录数据
                AlertUtil.dismissProgressDialog();
                mPayRecordDatas = (List<PayRecordResult>) o;
                break;
                
                
        }
    }


    @Override
    protected void onDestroy() {
        if(mSalesRecordDatas!=null){
            mSalesRecordDatas.clear();
            mSalesRecordDatas = null;
        }
        if(mPayRecordDatas!=null){
            mPayRecordDatas.clear();
            mPayRecordDatas = null;
        }
        super.onDestroy();
    }
}
