package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.base.bean.UpDetailBean;
import com.eshop.jinxiaocun.base.view.BaseScanActivity;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.GetPluPriceBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.PlayFlowBean;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBean;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.LightProgressDialog;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;
import com.eshop.jinxiaocun.widget.MoneyDialog;
import com.eshop.jinxiaocun.widget.ZheKouDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LingShouScanActivity extends BaseScanActivity implements INetWorResult {

    /*@BindView(R.id.ly1_sp)
    Spinner mSpinner1;
    @BindView(R.id.ly2_sp)
    Spinner mSpinner2;
    @BindView(R.id.ly3_sp)
    public Spinner mSpinner3;*/
    @BindView(R.id.et_barcode)
    EditText et_barcode;
    @BindView(R.id.btn_add)
    Button btSell;//销售
    @BindView(R.id.btn_delete)
    Button btn_delete;//删除
    @BindView(R.id.btn_modify_count)
    Button btn_modify_count;//改数
    @BindView(R.id.tv_check_num)
    TextView tv_check_num;//总数
    @BindView(R.id.ly_buttom1)
    LinearLayout ly_buttom1;
    @BindView(R.id.btn_yijia)
    Button btn_yijia;//议价
    @BindView(R.id.btn_zhekou)
    Button btn_zhekou;//折扣

    private final static int SELL = 110;
    private final static int SELL_DANPING_YIJIA = 111;
    private final static int SELL_ZHENDAN_YIJIA = 112;
    private LinearLayout ly_kaidan;
    private ILingshouScan mLingShouScanImp;
    private IOtherModel mIOtherModel;
    protected List<SaleFlowBean> mSaleFlowBeanList;
    protected List<PlayFlowBean> mPlayFlowBeanList;
    private String FlowNo = "";
    private Double total = 0.00;
    private List<GetPluPriceBeanResult> mGetPluPriceBeanResult;
    private List<GetClassPluResult> mGetClassPluResultList;
    private Double zhaoling = 0.0;
    private List<GetClassPluResult> mListData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //接收条码
    @Override
    protected void scanData(String barcode) {
        mLingShouScanImp.getPLUInfo(barcode);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mSaleFlowBeanList = new ArrayList<>();
        mPlayFlowBeanList = new ArrayList<>();
        mLingShouScanImp = new LingShouScanImp(this);
        mIOtherModel = new OtherModelImp(this);
        if(!newSheet){
            mLingShouScanImp.getSheetDetail(sheet_no);
        }else
            initMainBean();
        mLingShouScanImp.getFlowNo();
        mLingShouScanImp.getPayMode();
    }

    private void initMainBean(){
        mUpMainBean.setFlow_ID(MyUtils.getCurrentTime());//时间流水ID
        mUpMainBean.setSheetType(BillType.SO+"");//单据类型-批发销售
        mUpMainBean.setSheet_No("");//单号
        mUpMainBean.setBranch_No(Config.branch_no);//当前仓库
        mUpMainBean.setOrd_Man_No(Config.UserName);//业务员
        mUpMainBean.setPDA_No(Config.DeviceID);//PDA_No”:””	//PDA机号
        mUpMainBean.setUSER_ID(Config.DeviceID);//USER_ID”:””       	//用户ID
        mUpMainBean.setOper_Date(MyUtils.getCurrentTime());///Oper_Date”:”” 	//操作日期

        mUpMainBean.setPayment("");//Payment”:”” //付款方式
        mUpMainBean.setOrd_Amt("");//Ord_Amt”:””             //定金
    }

    //设置要结算流水的数据
    private void setDetailBean(UpDetailBean mUpDetailBean){
        mUpDetailBean.setFlow_ID(MyUtils.getCurrentTime());//时间流水ID
        mUpDetailBean.setPOSID(Config.DeviceID);
        mUpDetailBean.setBillNo("");//BillNo”:”” //单据号
        mUpDetailBean.setSupplyCode("");//SupplyCode

        mUpDetailBeanList.add(mUpDetailBean);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initView() {
        super.initView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View mView = this.getLayoutInflater().inflate(R.layout.activity_lingshou, null);
        mLinearLayout.addView(mView,0,params);
        ButterKnife.bind(this);
        btSell.setText(R.string.bt_sell);
        tv_check_num.setText("总价：");
        ly_buttom1.setVisibility(View.VISIBLE);

        et_barcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == 0) {
//                    mLingShouScanImp.getPLUInfo(v.getText().toString().trim());
                    mLingShouScanImp.getPLULikeInfo(v.getText().toString().trim());
                }
                return false;
            }
        });

        setHeaderTitle(R.id.tv_0, R.string.list_item_ProdName, 180);
        setHeaderTitle(R.id.tv_1, R.string.list_item_BarCode, 180);
        setHeaderTitle(R.id.tv_3, R.string.list_item_CountN5, 100);
        setHeaderTitle(R.id.tv_4, R.string.list_item_Price, 100);

        mScanAdapter = new LingShouScanAdapter(mListData);
        mListview.setAdapter(mScanAdapter);
        mScanAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleResule(int flag,Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                break;
            case Config.MESSAGE_PICI:
                List<GoodsPiciInfoBeanResult> mGoodsPiciInfoBeanResult = (List<GoodsPiciInfoBeanResult>)o;
                GetClassPluResult mGetClassPluResult = mListData.get(mListData.size()-1);
                mGetClassPluResult.setItem_barcode(mGoodsPiciInfoBeanResult.get(0).getItem_barcode());
                mGetClassPluResult.setProduce_date(mGoodsPiciInfoBeanResult.get(0).getProduce_date());
                mGetClassPluResult.setValid_date(mGoodsPiciInfoBeanResult.get(0).getValid_date());
                break;
            case Config.MESSAGE_GOODS_INFOR:
                mGetClassPluResultList = (List<GetClassPluResult>)o;
                reflashList(mGetClassPluResultList,true);
                //getPiCi(mGetClassPluResultList);
                break;
            case Config.MESSAGE_FLOW_NO:
                GetFlowNoBeanResult.FlowNoJson mGetFlowNoBeanResult = (GetFlowNoBeanResult.FlowNoJson)o;
                if(mGetFlowNoBeanResult != null ){
                    FlowNo = mGetFlowNoBeanResult.getFlowNo();
                    FlowNo = MyUtils.formatFlowNo(FlowNo);
                }
                break;
            case Config.MESSAGE_UP_SALL_FLOW:
                mLingShouScanImp.getPluPrice(FlowNo);
                break;
            case Config.MESSAGE_GETPLU_PRICE:
                mGetPluPriceBeanResult = (List<GetPluPriceBeanResult>)o;
                if(mListData!=null){
                    for(int i=0; i<mListData.size(); i++){
                        GetClassPluResult mplu = mListData.get(i);
                        for(int j=0; j<mGetPluPriceBeanResult.size(); j++){
                            GetPluPriceBeanResult mpluSrc = mGetPluPriceBeanResult.get(j);
                            if(mpluSrc.getItem_no().equalsIgnoreCase(mplu.getItem_no())){
                                mplu.setSale_price(mpluSrc.getSale_price());
                            }
                        }
                    }
                    reflashList(mGetClassPluResultList,false);//更新取价后的价格显示
                }
                Intent intent = new Intent(this, MoneyDialog.class);
                intent.putExtra("total",total);
                startActivityForResult(intent,100);
                break;
            case Config.MESSAGE_UP_PLAY_FLOW:
                mLingShouScanImp.sellSub(FlowNo);
                break;
            case Config.MESSAGE_SELL_SUB:
                ToastUtils.showShort(R.string.message_sell_ok);
                AlertUtil.showAlert(LingShouScanActivity.this, "找零", zhaoling+"", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                break;
            case Config.MESSAGE_GET_PAY_MODE:

                break;

        }
    }


    /*
    更新界面数据
     */
    private void setViewData(List<GoodGetBeanResult.GoodGetBeanJson> mGoodGetBeanResult) {
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {
        Intent mIntent = new Intent(this, QreShanpingActivity.class);
        startActivityForResult(mIntent,100);
    }

    private void getPiCi(List<GetClassPluResult> mGetClassPluResult){
        GoodsPiciInfoBean mGoodsPiciInfoBean = new GoodsPiciInfoBean();
        mGoodsPiciInfoBean.JsonData.as_branchNo = Config.branch_no;
        mGoodsPiciInfoBean.JsonData.as_posid = Config.posid;
        mGoodsPiciInfoBean.JsonData.as_item_no = mGetClassPluResult.get(0).getItem_no();
        mIOtherModel.getGoodsPiciInfo(mGoodsPiciInfoBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Config.RESULT_SELECT_GOODS:
                mGetClassPluResultList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
                reflashList(mGetClassPluResultList,true);
                //getPiCi(mGetClassPluResult);
                break;
            case RESULT_OK:
                String mCount =  data.getStringExtra("countN");
                int itemClickPosition = mScanAdapter.getItemClickPosition();
                GetClassPluResult item = mListData.get(itemClickPosition);
                item.setSale_qnty(mCount);
                mScanAdapter.notifyDataSetChanged();
                break;
            case Config.MESSAGE_MONEY:
                if(requestCode == 100){
                    String mMoney =  data.getStringExtra("countN");
                    zhaoling = Double.parseDouble(mMoney) - total;
                    setPlayFlowBean(total+"");
                }else if(requestCode == 200){
                    String zhekou =  data.getStringExtra("countN");
                    total = Double.parseDouble(zhekou);
                    tv_check_num.setText("总价："+total);
                    getBillDiscount(total);
                }
                break;
            case Config.MESSAGE_INTENT_ZHEKOU:
                String zhekou =  data.getStringExtra("countN");
                int int_zhekou = Integer.decode(zhekou);
                total = int_zhekou*total;
                tv_check_num.setText("总价："+total);
                getBillDiscount(total);
                break;

        }
    }

    //整单议价、折扣
    private void getBillDiscount(Double total){
        mLingShouScanImp.getBillDiscount(total,FlowNo);
    }

    private void reflashList(List<GetClassPluResult> mGetClassPluResultlist,boolean flag){
        if(flag){
            mListData.addAll(mGetClassPluResultlist);
        }
        mScanAdapter.notifyDataSetChanged();
        total = 0.0;
        for(int i=0; i<mListData.size(); i++){
            GetClassPluResult mGetClassPluResult = mListData.get(i);
            total += Double.parseDouble(mGetClassPluResult.getSale_price());
        }
        tv_check_num.setText("总价："+total);
    }

    private void setSaleFlowBean(int flag){
        for(int i=0; i<mListData.size(); i++){
            SaleFlowBean mSaleFlowBean = new SaleFlowBean();
            GetClassPluResult mGetClassPluResult = mListData.get(i);

            mSaleFlowBean.setBranch_no(Config.branch_no);
            mSaleFlowBean.setFlow_no(FlowNo);
            mSaleFlowBean.setFlow_id((i+1)+"");
            mSaleFlowBean.setItem_no(mGetClassPluResult.getItem_no());
            mSaleFlowBean.setSource_price(mGetClassPluResult.getSale_price());
            mSaleFlowBean.setSale_price(mGetClassPluResult.getSale_price());
            mSaleFlowBean.setSale_qnty(mGetClassPluResult.getSale_qnty());

            String money = Float.parseFloat(mGetClassPluResult.getSale_qnty())*Float.parseFloat(mGetClassPluResult.getSale_price())+"";
            mSaleFlowBean.setSale_money(money);
            mSaleFlowBean.setSell_way("A");
            mSaleFlowBean.setSale_man(Config.UserName);
            mSaleFlowBean.setSpec_flag("");
            mSaleFlowBean.setSpec_sheet_no("");
            mSaleFlowBean.setPosid(Config.posid);
            mSaleFlowBean.setVoucher_no("");
            mSaleFlowBean.setCounter_no("");
            mSaleFlowBean.setOper_id(Config.UserName);
            mSaleFlowBean.setOper_date(DateUtility.getCurrentTime());
            mSaleFlowBean.setIsfreshcodefrag("");
            mSaleFlowBean.setBatch_code(mGetClassPluResult.getItem_barcode());
            mSaleFlowBean.setBatch_made_date(mGetClassPluResult.getProduce_date()==null?DateUtility.getCurrentTime():mGetClassPluResult.getProduce_date());
            mSaleFlowBean.setBatch_valid_date(mGetClassPluResult.getValid_date()==null?DateUtility.getCurrentTime():mGetClassPluResult.getValid_date());
            if(i == (mListData.size()-1)){
                mSaleFlowBean.setbDealFlag("1");
            }else{
                mSaleFlowBean.setbDealFlag("0");
            }

            mSaleFlowBeanList.add(mSaleFlowBean);
        }
        switch (flag){
            case SELL:
                mLingShouScanImp.upSallFlow(mSaleFlowBeanList);
                break;
            case SELL_ZHENDAN_YIJIA:
                btn_yijia();
                break;
        }

    }

    private void setPlayFlowBean(String payAmount){
        PlayFlowBean mPlayFlowBean = new PlayFlowBean();
        mPlayFlowBean.setBranch_no(Config.branch_no);
        mPlayFlowBean.setFlow_no(FlowNo);
        mPlayFlowBean.setFlow_id(1);
        mPlayFlowBean.setSale_amount(Float.parseFloat(payAmount));
        mPlayFlowBean.setPay_way("RMB");
        mPlayFlowBean.setSell_way("A");
        mPlayFlowBean.setCard_no(1);
        mPlayFlowBean.setVip_no(1);
        mPlayFlowBean.setCoin_no("RMB");
        mPlayFlowBean.setCoin_rate(1);
        mPlayFlowBean.setPay_amount(Float.parseFloat(payAmount));//付款金额
        mPlayFlowBean.setVoucher_no("");
        mPlayFlowBean.setPosid(Config.posid);
        mPlayFlowBean.setCounter_no("");
        mPlayFlowBean.setOper_id(Config.UserName);
        mPlayFlowBean.setSale_man(Config.UserName);
        mPlayFlowBean.setShift_no("");
        mPlayFlowBean.setOper_date(DateUtility.getCurrentTime());
        mPlayFlowBean.setMemo("");
        mPlayFlowBean.setWorderno("");
        mPlayFlowBean.setbDealFlag("1");
        mPlayFlowBeanList.add(mPlayFlowBean);
        mLingShouScanImp.upPlayFlow(mPlayFlowBeanList);
    }

    @OnClick(R.id.btn_add)
    void sell() {
        setSaleFlowBean(SELL);
    }

    @OnClick(R.id.btn_delete)
    void delete() {
        try {
            mListData.remove(itemClickPosition);
            mScanAdapter.notifyDataSetChanged();
        }catch (Exception e){

        }

    }

    @OnClick(R.id.btn_zhekou)
    void btn_zhekou() {
        try {
            if(total==null ||total == 0){
                return;
            }
            Intent intent = new Intent(this, ZheKouDialog.class);
            startActivityForResult(intent,100);
        }catch (Exception e){

        }

    }

    @OnClick(R.id.btn_yijia)
    void btn_yijia() {
        try {
            if(total==null ||total == 0){
                return;
            }
            Intent intent = new Intent(this, MoneyDialog.class);
            startActivityForResult(intent,200);
        }catch (Exception e){

        }

    }

    @OnClick(R.id.btn_modify_count)
    void modifyCount() {
        GetClassPluResult mGetClassPluResult = mListData.get(itemClickPosition);
        Intent intent = new Intent();
        intent.putExtra("countN", mGetClassPluResult.getSale_qnty());
        intent.setClass(this, ModifyCountDialog.class);
        startActivityForResult(intent, 1);
    }
}
