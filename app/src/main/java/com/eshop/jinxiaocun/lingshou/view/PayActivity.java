package com.eshop.jinxiaocun.lingshou.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.lingshou.bean.GetSystemBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.VipPayBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBeanResultItem;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DanPinGaiJiaDialog;
import com.eshop.jinxiaocun.widget.DanPinZheKouDialog;
import com.eshop.jinxiaocun.widget.SaleManDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayActivity extends BaseActivity implements ActionBarClickListener, INetWorResult {

    @BindView(R.id.btn_moling)
    Button btn_moling;//抹零
    @BindView(R.id.btn_zhengdanzhekou)
    Button btn_zhengdanzhekou;//整单折扣
    @BindView(R.id.btn_zhengdanyijia)
    Button btn_zhengdanyijia;//整单议价
    @BindView(R.id.btn_zhengdanquxiao)
    Button btn_zhengdanquxiao;//整单取消
    @BindView(R.id.btn_yingyeyuan)
    Button btn_yingyeyuan;//营业员
    @BindView(R.id.et_price)
    TextView et_price;
    @BindView(R.id.et_pay_money)
    EditText et_pay_money;
    @BindView(R.id.tv_pay_return)
    TextView tv_pay_return;
    @BindView(R.id.ly_fukuan_jinge)
    LinearLayout ly_fukuan_jinge;
    @BindView(R.id.ly_fukuan_zhaoling)
    LinearLayout ly_fukuan_zhaoling;

    private boolean hasMoling = false;
    private boolean hasGaiJia = false;
    private ILingshouScan mLingShouScanImp;
    private Button btn_ok;
    private Spinner sp_payway;
    private double money = 0.00;
    List<GetSystemBeanResult.SystemJson> mSystemJson;
    private int jiaoRmb = 0;
    private int fenRmb = 0;
    private double molingMoney = 0;
    private static  String saleMan = "";
    private List<GetClassPluResult> mListData = new ArrayList<>();
    protected List<SaleFlowBean> mSaleFlowBeanList;
    private double gaiJiaMoney = 0;
    private String FlowNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.activity_pay, null);
        mLinearLayout.addView(bottomView,-1,params);
        ButterKnife.bind(this);
        mMyActionBar.setData("支付订单",R.mipmap.ic_left_light,"",0,"",this);

        money = getIntent().getDoubleExtra("money",0.00);
        FlowNo = getIntent().getStringExtra("FlowNo");
        mListData =  (ArrayList<GetClassPluResult>) getIntent().getSerializableExtra("mListData");

        money = initDouble(3,money);
        money = addDouble(money);
        double moneyTemp0 = money*100.000;
        int moneyTemp = (int)moneyTemp0;
        fenRmb = moneyTemp%10;
        moneyTemp = (int)(money*10);
        jiaoRmb = moneyTemp%10;

        money = initDouble(3,money);
        et_price.setText("￥"+money);
        btn_ok = findViewById(R.id.btn_ok);
        sp_payway = findViewById(R.id.sp_payway);
        mLingShouScanImp = new LingShouScanImp(this);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mLingShouScanImp.sellVipPay(vip_name.getText().toString(),vip_password.getText().toString(),money);
            }
        });

        //数据源
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add("现金");
        spinners.add("支付宝");
        spinners.add("微信");
        spinners.add("会员卡");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinners);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_payway.setAdapter(adapter);
        sp_payway.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        displayLayout(true);
                        break;
                    case 1:
                        displayLayout(false);
                        break;
                    case 2:
                        displayLayout(false);
                        break;
                    case 3:
                        displayLayout(false);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        et_pay_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv_pay_return.setText((money-Double.valueOf(charSequence.toString()))+"");
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mLingShouScanImp.getSystemInfo();
    }

    private void displayLayout(boolean display){
        if(display){
            ly_fukuan_jinge.setVisibility(View.VISIBLE);
            ly_fukuan_zhaoling.setVisibility(View.VISIBLE);
        }else {
            ly_fukuan_jinge.setVisibility(View.GONE);
            ly_fukuan_zhaoling.setVisibility(View.GONE);
        }
    }

    VipPayBeanResult mVipPayBeanResult;
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showAlert(PayActivity.this,"提示",(String)o);
                break;
            case Config.MESSAGE_VIP_PAY_RESULT:
                mVipPayBeanResult = (VipPayBeanResult)o;
                setResult(Config.MESSAGE_VIP_PAY_RESULT);
                finish();
                break;
            case Config.MESSAGE_GET_SYSTEM_INFO_RETURN:
                mSystemJson = (List<GetSystemBeanResult.SystemJson>) o;
                break;
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

    @OnClick(R.id.btn_moling)
    void btn_moling() {
        try {
            if(mSystemJson==null){
                return;
            }
            if(hasMoling==true){
                AlertUtil.showAlert(this,"提示","已抹零！");
                return;
            }

            //0-实收，1- 抹去分 2- 抹去角分，3-元以后的四舍五入，4角以下四舍五入  PosAmtEndDec
            String value = "0";
            for(int i=0; i<mSystemJson.size(); i++){
                if(mSystemJson.get(i).getName().equals("PosAmtEndDec")){
                    value = mSystemJson.get(i).getValue();
                }
            }
            switch (value){
                case "0":
                    break;
                case "1":
                    if(fenRmb != 0){
                        molingMoney = Double.parseDouble("0.0"+fenRmb);
                        money -= molingMoney;
                        hasMoling = true;
                    }
                    break;
                case "2":
                    if(jiaoRmb != 0){
                        molingMoney = Double.parseDouble("0."+jiaoRmb);
                        hasMoling = true;
                    }
                    if(fenRmb != 0){
                        molingMoney += Double.parseDouble("0.0"+fenRmb);
                        hasMoling = true;
                    }
                    money -= molingMoney;
                    break;
                case "3":
                    if(jiaoRmb != 0 || fenRmb != 0){
                        molingMoney = Double.parseDouble("0."+jiaoRmb+fenRmb);
                        if(jiaoRmb>=5){
                            money = money-molingMoney+1;
                        }else {
                            money -= molingMoney;
                            molingMoney = -molingMoney;
                        }
                        hasMoling = true;
                    }
                    break;
                case "4":
                    if(fenRmb != 0){
                        molingMoney = Double.parseDouble("0.0"+fenRmb);
                        if(fenRmb>=5){
                            money = money-molingMoney+0.100;
                        }else {
                            money -= molingMoney;
                            molingMoney = -molingMoney;
                        }
                        hasMoling = true;
                    }
                    break;
            }
            money = initDouble(3,money);
            et_price.setText("￥"+money);

            if(hasMoling==false){
                AlertUtil.showAlert(this,"提示","不需要抹零！");
                return;
            }
        }catch (Exception e){
        }
    }

    private double initDouble(int leng, double moneyTem){
        String totalStr = moneyTem+"";
        if((totalStr.length()-totalStr.indexOf("."))>leng){
            totalStr = totalStr.substring(0,totalStr.indexOf(".")+(leng));
            moneyTem = Double.parseDouble(totalStr);
        }
        return moneyTem;
    }

    private double addDouble(double moneyTem){
        String totalStr = moneyTem+"";
        int dd= totalStr.length()-totalStr.indexOf(".");
        if((totalStr.length()-totalStr.indexOf("."))==2){
            totalStr = totalStr+"0001";
            moneyTem = Double.parseDouble(totalStr);
        }
        if((totalStr.length()-totalStr.indexOf("."))==3){
            totalStr = totalStr+"0001";
            moneyTem = Double.parseDouble(totalStr);
        }
        return moneyTem;
    }

    @OnClick(R.id.btn_zhengdanzhekou)
    void btn_zhengdanzhekou() {
        try {
            if(hasGaiJia == true || hasDanPingGaiJia()){
                AlertUtil.showAlert(this, "提示",
                        "已经修改过价格。", "确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertUtil.dismissDialog();
                            }
                        });
                return;
            }
            Intent intent = new Intent(this, DanPinZheKouDialog.class);
            intent.putExtra("oldPrice",money);
            intent.putExtra("limit",Config.zhendanZheKoulimit);
            startActivityForResult(intent,100);
        }catch (Exception e){
            Log.e("","");

        }
    }


    @OnClick(R.id.btn_zhengdanyijia)
    void btn_zhengdanyijia() {
        try {
            if(hasGaiJia == true || hasDanPingGaiJia()){
                AlertUtil.showAlert(this, "提示",
                        "已经修改过价格。", "确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertUtil.dismissDialog();
                            }
                        });
                return;
            }
            Intent intent = new Intent(this, DanPinGaiJiaDialog.class);
            intent.putExtra("oldPrice",money);
            intent.putExtra("limit",Config.zhendanYiJialimit);
            startActivityForResult(intent,200);
        }catch (Exception e){

        }
    }


    @OnClick(R.id.btn_zhengdanquxiao)
    void btn_zhengdanquxiao() {
        try {
            AlertUtil.showAlert(this, R.string.dialog_title,
                    R.string.pay_cancle, R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertUtil.dismissDialog();
                    setResult(Config.RESULT_PAY_CANCLE);
                    finish();
                }
            },R.string.cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertUtil.dismissDialog();
                        }
                    }
                    );

        }catch (Exception e){
        }
    }

    @OnClick(R.id.btn_yingyeyuan)
    void btn_yingyeyuan() {
        try {
            Intent intent = new Intent(this, SaleManDialog.class);
            startActivityForResult(intent,200);
        }catch (Exception e){
        }
    }

    @OnClick(R.id.btn_ok)
    void btn_ok() {
        try {
            Intent intent = new Intent(this, SaleManDialog.class);
            startActivityForResult(intent,200);
        }catch (Exception e){
        }
    }

    private boolean hasDanPingGaiJia(){
        for(int i=0; i<mListData.size(); i++){
            if(mListData.get(i).isHasYiJia()){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        double temprice;
        switch (resultCode){
            case Config.MESSAGE_PAY_MAN:
                saleMan = data.getStringExtra("PayMan");
                break;
            case Config.MESSAGE_INTENT_ZHEKOU:
                String zhekou =  data.getStringExtra("countN");
                gaiJiaMoney = (1-Double.valueOf(zhekou))*money;
                hasGaiJia = true;
                reflashGaiJiaItemPrice();
                break;
            case Config.MESSAGE_MONEY:
                String gaijia =  data.getStringExtra("countN");
                gaiJiaMoney = Double.valueOf(gaijia);
                hasGaiJia = true;
                reflashGaiJiaItemPrice();
                break;
        }
    }

    private void reflashGaiJiaItemPrice(){
        double tempmoney = 0;
        for(int i=0; i<mListData.size(); i++){
            GetClassPluResult mGetClassPluResult = mListData.get(i);
            double itemPrice = Double.valueOf(mGetClassPluResult.getSale_price());
            itemPrice = itemPrice-((itemPrice/money) * gaiJiaMoney);
            itemPrice =  initDouble(4,itemPrice);
            mGetClassPluResult.setSale_price(itemPrice+"");
            tempmoney += itemPrice;
        }
        setSaleFlowBean();//销售流水
        et_price.setText("￥"+tempmoney);
    }

    private void setSaleFlowBean(){
        mSaleFlowBeanList.clear();
        for(int i=0; i<mListData.size(); i++){
            SaleFlowBean mSaleFlowBean = new SaleFlowBean();
            GetClassPluResult mGetClassPluResult = mListData.get(i);

            mSaleFlowBean.setBranch_no(Config.branch_no);
            mSaleFlowBean.setFlow_no(FlowNo);
            mSaleFlowBean.setFlow_id((i+1)+"");
            mSaleFlowBean.setItem_no(mGetClassPluResult.getItem_no());
            mSaleFlowBean.setSource_price(mGetClassPluResult.getSource_price());
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
        mLingShouScanImp.upSallFlow(mSaleFlowBeanList);

    }
}
