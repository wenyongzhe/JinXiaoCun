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
import com.eshop.jinxiaocun.huiyuan.bean.MemberRechargeBean;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.PlayFlowBean;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.lingshou.view.LingShouScanActivity;
import com.eshop.jinxiaocun.lingshou.view.PayActivity;
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
import com.zxing.android.CaptureActivity;

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
    private IMemberList mMemberApi;
    private ILingshouScan mSalesServerApi;
    private ReturnedPurchaseByBillAdapter mAdapter;
    private List<ReturnedPurchaseResult> mSalesRecordDatas = new ArrayList<>();//销售记录
    private List<PayRecordResult> mPayRecordDatas = new ArrayList<>();//付款记录
    private int modifyQtyPosition =-1;
    private boolean mAllReturn;//true为全退，false为退部分
    private GetFlowNoBeanResult.FlowNoJson mFlowNoJson;
    private float mRpAllMoney=0;//退货的总金额

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
        mMemberApi = new MemberImp(this);
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
        mServerApi.getRetSalesRecordDatas(mEditBillNo.getText().toString().trim());
    }

    //全退
    @OnClick(R.id.btn_allReturned)
    public void onClickAllReturned(){

        if(!MyUtils.isOpenNetwork()){
            AlertUtil.showToast("网络不可用，请检查网络!");
            return;
        }

        if(mSalesRecordDatas==null || mSalesRecordDatas.size()==0){
            AlertUtil.showToast("没有单据销售记录数据，不能退货！");
            return;
        }

        if(mPayRecordDatas ==null || mPayRecordDatas.size()==0){
            AlertUtil.showToast("没有单据付款记录数据，不能退货！");
            return;
        }

        if(mPayRecordDatas.size()>1){
            AlertUtil.showAlert(this, "温馨提示", "该单据为组合付款，请到pc端或前台退货！",
                    "确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertUtil.dismissDialog();
                        }
                    });
            return;
        }

        AlertUtil.showAlert(this, R.string.dialog_title,
                "确定要退掉单据中的所有商品，"+mTxtAllMoney.getText().toString(),
                R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                        AlertUtil.showNoButtonProgressDialog(ReturnedPurchaseByBillActivity.this,
                                "正在退货，请稍等...");
                        mAllReturn = true;
                        mSalesServerApi.getFlowNo();//取流水号
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

        if(!MyUtils.isOpenNetwork()){
            AlertUtil.showToast("网络不可用，请检查网络!");
            return;
        }

        if(mSalesRecordDatas==null || mSalesRecordDatas.size()==0){
            AlertUtil.showToast("没有单据销售记录数据，不能退货！");
            return;
        }

        if(mPayRecordDatas ==null || mPayRecordDatas.size()==0){
            AlertUtil.showToast("没有单据付款记录数据，不能退货！");
            return;
        }

        if(mPayRecordDatas.size()>1){
            AlertUtil.showAlert(this, "温馨提示", "该单据为组合付款，请到pc端或前台退货！",
                    "确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertUtil.dismissDialog();
                        }
                    });
            return;
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
                        mAllReturn = false;
                        mSalesServerApi.getFlowNo();//取流水号
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
     */
    private void uploadSalesFlow(){
        List<SaleFlowBean> salesFlowDatas = new ArrayList<>();
        for(int i=0; i<mSalesRecordDatas.size(); i++){
            SaleFlowBean mSaleFlowBean = new SaleFlowBean();
            ReturnedPurchaseResult goodsInfo = mSalesRecordDatas.get(i);

            mSaleFlowBean.setBranch_no(Config.branch_no);
            mSaleFlowBean.setFlow_no(MyUtils.formatFlowNo(mFlowNoJson.getFlowNo()));
            mSaleFlowBean.setFlow_id((i+1)+"");
            mSaleFlowBean.setItem_no(goodsInfo.getItem_no());
            mSaleFlowBean.setSource_price(MyUtils.convertToString(goodsInfo.getSource_price(),"0"));
            mSaleFlowBean.setSale_price(MyUtils.convertToString(goodsInfo.getSale_price(),"0"));

            if(mAllReturn){//全退
                mSaleFlowBean.setSale_qnty("-"+MyUtils.convertToString(goodsInfo.getRe_qty(),"0"));
                mSaleFlowBean.setSale_money(String.format("-%s",goodsInfo.getRe_qty()*goodsInfo.getSale_price()));
            }else{
                //退部分
                if(goodsInfo.getRp_Qty()==0){//只退退货数量大于0的
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
            mSaleFlowBean.setVoucher_no(goodsInfo.getFlow_no());//引单号 （原单据号）
            mSaleFlowBean.setCounter_no("");
            mSaleFlowBean.setOper_id(Config.UserName);
            mSaleFlowBean.setOper_date(DateUtility.getCurrentTime());
            mSaleFlowBean.setIsfreshcodefrag("");
            mSaleFlowBean.setBatch_code(goodsInfo.getItem_barcode());//批次号
            mSaleFlowBean.setBatch_made_date(goodsInfo.getProduce_date());//批次生产日期
            mSaleFlowBean.setBatch_valid_date(goodsInfo.getValid_date());//批次有效期
            if(i == (mSalesRecordDatas.size()-1)){//表示该单结束标识  1：结束
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

        float rpAllMoney=0;//计算部分应退货的总金额
        for(int i=0; i<mSalesRecordDatas.size(); i++){
            ReturnedPurchaseResult goodsInfo = mSalesRecordDatas.get(i);
            if(!mAllReturn){
                //退部分
                if(goodsInfo.getRp_Qty()==0){//只退退货数量大于0的
                    continue;
                }
                rpAllMoney+=goodsInfo.getRp_Qty()*goodsInfo.getSale_price();
            }
        }

        List<PlayFlowBean> playFlowDatas = new ArrayList<>();
        for(int i=0; i<mPayRecordDatas.size(); i++){
            PayRecordResult payInfo = mPayRecordDatas.get(i);
            PlayFlowBean mPlayFlowBean = new PlayFlowBean();
            mPlayFlowBean.setBranch_no(Config.branch_no);
            mPlayFlowBean.setFlow_no(MyUtils.formatFlowNo(mFlowNoJson.getFlowNo()));
            mPlayFlowBean.setFlow_id(1);
            if(mAllReturn) {//全退
                mRpAllMoney = -payInfo.getSale_amount();
                mPlayFlowBean.setSale_amount(-payInfo.getSale_amount());//销售金额
            }else{//退部分
                mRpAllMoney = -rpAllMoney;
                mPlayFlowBean.setSale_amount(-rpAllMoney);//销售金额
            }
            mPlayFlowBean.setPay_way(payInfo.getPay_way());//支付方式
            mPlayFlowBean.setSell_way("B");//-销售方式: A销售 B退货 D赠送

            mPlayFlowBean.setCard_no(payInfo.getCard_no());//卡号
            mPlayFlowBean.setVip_no(payInfo.getVip_no());//会员卡号

            mPlayFlowBean.setCoin_no("");
            mPlayFlowBean.setCoin_rate(1);
            if(mAllReturn) {//全退
                mPlayFlowBean.setPay_amount(-payInfo.getPay_amount());//付款金额
            }else{//退部分
                mPlayFlowBean.setPay_amount(-rpAllMoney);//付款金额
            }
            mPlayFlowBean.setVoucher_no(payInfo.getFlow_no());//引单号（原单据号）
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

    //退款
    private void returnMoney(){
        if(mPayRecordDatas==null || mPayRecordDatas.size()==0){
            return;
        }
        //get(0)目前只能存在一种付款记录，多种付款记录则是组合付款目前还没有方案处理
        PayRecordResult payInfo = mPayRecordDatas.get(0);
        if("RMB".equals(payInfo.getPay_way())){//现金支付直接结算
            mSalesServerApi.sellSub(MyUtils.formatFlowNo(mFlowNoJson.getFlowNo()));//结算
            return;
        }else if("SAV".equals(payInfo.getPay_way())){//会员支付
            rechargeData(payInfo.getCard_no(),-mRpAllMoney);
            return;
        }
        //auth_code就是付款记录的里的备注信息
        mSalesServerApi.RtWzfPay(payInfo.getPay_way(),payInfo.getMemo(),MyUtils.formatFlowNo(mFlowNoJson.getFlowNo())
                ,mRpAllMoney+"",mRpAllMoney+"");

    }

    private void rechargeData(String cardNo,float money){
        MemberRechargeBean bean = new MemberRechargeBean();
        bean.JsonData.BranchNo = Config.branch_no;
        bean.JsonData.UserId = Config.UserId;
        bean.JsonData.CardNo = cardNo;
        //充值金额(有可能包含赠送金额)
        bean.JsonData.AddMoney = money;
        //实付金额
        bean.JsonData.PayMoney = money;
        bean.JsonData.FlowNo = MyUtils.formatFlowNo(mFlowNoJson.getFlowNo());//"流水号"
        bean.JsonData.PayWay = "RMB";//支付方式  后续需要改成多种支付方式，跟销售一样
        bean.JsonData.Memo = "商品退款";

        mMemberApi.setMemberRechargeData(bean);
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
                    String flowNo = mSalesRecordDatas.get(0).getFlow_no();
                    mServerApi.getPayRecordDatas(flowNo);
                }else{
                    AlertUtil.showToast("该单据下没有销售记录数据,请确认单据号是否正确!");
                    AlertUtil.dismissProgressDialog();
                }
                break;
            case Config.MESSAGE_FAIL:
                AlertUtil.showToast((String) o);
                AlertUtil.dismissProgressDialog();
                break;
            case Config.MESSAGE_ERROR:
            case Config.RESULT_FAIL:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
            case Config.PAY_RECORD_SUCCESS:
                //获取付款记录数据
                AlertUtil.dismissProgressDialog();
                mPayRecordDatas = (List<PayRecordResult>) o;
                break;
            case Config.MESSAGE_FLOW_NO://获取流水号
                mFlowNoJson = (GetFlowNoBeanResult.FlowNoJson)o;
                if(mFlowNoJson!=null && !TextUtils.isEmpty(mFlowNoJson.getFlowNo())){
                    uploadSalesFlow();
                }else{
                    AlertUtil.dismissProgressDialog();
                    AlertUtil.showToast("流水号为空!");
                }
                break;
            case LingShouScanActivity.SELL://上传销售流水成功
                uploadPayFlow();//上传付款流水
                break;
            case Config.MESSAGE_UP_PLAY_FLOW://上传付款流水成功
                returnMoney();//发起退款
//                mSalesServerApi.sellSub(MyUtils.formatFlowNo(mFlowNoJson.getFlowNo()));//结算
//                //调用扫描二维码获取授权码
//                Intent intent = new Intent(this, CaptureActivity.class);
//                startActivityForResult(intent, Config.REQ_QR_CODE);
                break;
            case Config.MESSAGE_NET_PAY_RETURN://聚合支付成功
            case Config.RESULT_SUCCESS://会员充值（退款退到会员卡）
                mSalesServerApi.sellSub(MyUtils.formatFlowNo(mFlowNoJson.getFlowNo()));//结算
                break;
            case Config.MESSAGE_SELL_SUB:
                //结算完成  即退货完成
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast("退货成功！");
                mSalesRecordDatas.clear();
                mPayRecordDatas.clear();
                mAdapter.add(mSalesRecordDatas);
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
