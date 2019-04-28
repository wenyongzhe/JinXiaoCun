package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.lingshou.bean.GetSystemBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.NetPlayBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.PlayFlowBean;
import com.eshop.jinxiaocun.lingshou.bean.VipPayBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.login.SystemSettingActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DanPinGaiJiaDialog;
import com.eshop.jinxiaocun.widget.DanPinZheKouDialog;
import com.eshop.jinxiaocun.widget.SaleManDialog;
import com.eshop.jinxiaocun.zjPrinter.BluetoothService;
import com.google.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
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
    @BindView(R.id.btn_print)
    Button btn_print;

    Button btn_jiesuan;//

    private static boolean is58mm = true;
    public boolean isOk = false;
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
    protected List<SaleFlowBean> mSaleFlowBeanList = new ArrayList<>();
    protected List<PlayFlowBean> mPlayFlowBeanList = new ArrayList<>();
    private double gaiJiaMoney = 0;
    private String FlowNo = "";
    private String memberId = "";
    private List<MemberCheckResultItem> memberData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.activity_pay, null);
        mLinearLayout.addView(bottomView,-1,params);
        ButterKnife.bind(this);
        mMyActionBar.setData("支付订单",R.mipmap.ic_left_light,"",0,"",this);
        btn_jiesuan = (Button) findViewById(R.id.btn_jiesuan);
        btn_jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_jiesuan();
            }
        });
        money = getIntent().getDoubleExtra("money",0.00);
        FlowNo = getIntent().getStringExtra("FlowNo");
        mListData =  (ArrayList<GetClassPluResult>) getIntent().getSerializableExtra("mListData");

//        money = initDouble(3,money);
//        money = addDouble(money);
        double moneyTemp0 = money*100.000;
        int moneyTemp = (int)moneyTemp0;
        fenRmb = moneyTemp%10;
        moneyTemp = (int)(money*10);
        jiaoRmb = moneyTemp%10;

        money = initDouble(3,money);
        et_price.setText("￥"+money);
        sp_payway = (Spinner) findViewById(R.id.sp_payway);
        mLingShouScanImp = new LingShouScanImp(this);

        //数据源
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add("现金");
        spinners.add("聚合支付");
//        spinners.add("支付宝");
//        spinners.add("微信");
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
                        if(memberId.equals("")){
                            Intent mIntent = new Intent(PayActivity.this, SaveMemberActivity.class);
                            startActivityForResult(mIntent,200);
                        }
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
                if(charSequence.toString().equals("")){
                    tv_pay_return.setText("");
                }else {
                    double tv_money = Double.valueOf(charSequence.toString());
                    tv_money = tv_money + 0.0000000000001;
                    if(tv_money>money){
                        double moneyTem = initDouble(3,tv_money-money);
                        tv_pay_return.setText(moneyTem+"");
                    }else{
                        tv_pay_return.setText("");
                    }
                }
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

    //打印小票
    private void printMs() {
        if( !BluetoothAdapter.getDefaultAdapter().isEnabled()){
            return;
        }
        Print_Ex();
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SystemSettingActivity.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            ToastUtils.showShort("连接成功");
                            printMs();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            ToastUtils.showShort("连接中");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            //ToastUtils.showShort("没有连接打印机");
                            break;
                    }
                    break;
                case SystemSettingActivity.MESSAGE_WRITE:

                    break;
                case SystemSettingActivity.MESSAGE_READ:

                    break;
                case SystemSettingActivity.MESSAGE_DEVICE_NAME:
                    break;
                case SystemSettingActivity.MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    Toast.makeText(getApplicationContext(), "无法连接设备",
                            Toast.LENGTH_SHORT).show();
                    break;
                case SystemSettingActivity.MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    Toast.makeText(getApplicationContext(), "蓝牙已断开连接",
                            Toast.LENGTH_SHORT).show();

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
    }


    VipPayBeanResult mVipPayBeanResult;
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.dismissDialog();
                if(((String)o).equals("")){
                    o = "程序错误";
                }
                AlertUtil.showAlert(PayActivity.this, "提示", (String)o,
                        "确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertUtil.dismissDialog();
                            }
                        });
                //AlertUtil.showAlert(PayActivity.this,"提示",(String)o);
                break;
            case Config.MESSAGE_VIP_PAY_RESULT:
                mVipPayBeanResult = (VipPayBeanResult)o;
                setResult(Config.MESSAGE_VIP_PAY_RESULT);
                finish();
                break;
            case Config.MESSAGE_GET_SYSTEM_INFO_RETURN:
                mSystemJson = (List<GetSystemBeanResult.SystemJson>) o;
                break;
            case Config.MESSAGE_UP_PLAY_FLOW:
                mSystemJson = (List<GetSystemBeanResult.SystemJson>) o;
                switch (sp_payway.getSelectedItemPosition()){
                    case 0:
                        mHan.sendEmptyMessage(0);
                        break;
                    case 1:
                        mHan.sendEmptyMessage(1);
                        break;
                    case 2:
                        mHan.sendEmptyMessage(2);
                        break;

                }

                break;
            case Config.MESSAGE_NET_PAY_RETURN://网络付款返回
                if(query==false){
                    return;
                }
                NetPlayBeanResult mNetPlayBeanResult =  (NetPlayBeanResult)o;
                if(mNetPlayBeanResult==null){
                    ToastUtils.showShort(R.string.message_sell_error);
                    return;
                }
                if( mNetPlayBeanResult.getReturn_code().equals("000000")){
                    mHan.sendEmptyMessage(0);
                    AlertUtil.dismissDialog();
                }else {
                    //ToastUtils.showShort(mNetPlayBeanResult.getReturn_msg());
                    if( mNetPlayBeanResult.getReturn_code().equals("1013")){
                        AlertUtil.showAlert(PayActivity.this, "提示", mNetPlayBeanResult.getReturn_msg(),
                                "整单取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        query = false;
                                        AlertUtil.dismissDialog();
                                        cancle();
                                    }
                                });
                    }else{
                        AlertUtil.showAlert(PayActivity.this, "提示", "结算处理中 "+mNetPlayBeanResult.getReturn_msg(),
                                "取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        query = false;
                                        AlertUtil.dismissDialog();
                                    }
                                });
                        mHan.sendEmptyMessageDelayed(2,2000);
                    }
                }
                break;
            case Config.MESSAGE_SELL_SUB:
                AlertUtil.showAlert(PayActivity.this, "提示", "结算处理完成",
                        "确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertUtil.dismissDialog();
                                cancle();
                            }
                        },R.string.txt_print, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertUtil.dismissDialog();
                                cancle();
                                Print_Ex();
                            }
                });
//                printMs();
                break;
        }
    }

    private void cancle(){
        setResult(Config.RESULT_PAY_CANCLE);
        finish();
    }

    boolean query = true;
    private void rtWzfQry(){
        if(query){
            mLingShouScanImp.RtWzfQry(tempayway, code, FlowNo, temMoney, temMoney);
        }
    }

    Handler mHan = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mLingShouScanImp.sellSub(FlowNo);
                    break;
                case 1:
                    /*Intent intent = new Intent(PayActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, Config.REQ_QR_CODE);*/
                    rtWzfQry();
                    break;
                case 2:
                    if(memberData!=null&&memberData.size()>0){
                        MemberCheckResultItem mMemberCheckResultItem =  memberData.get(0);
                        mLingShouScanImp.sellVipPay(FlowNo,"-1",
                                mMemberCheckResultItem.getCardNo_TelNo(),
                                mMemberCheckResultItem.getPassword(),
                                money);
                    }else{
                        AlertUtil.showToast("没有会员信息");
                    }
                    break;
            }
        }
    };

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
                    cancle();
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

    @OnClick(R.id.btn_print)
    void btn_print() {
        printMs();
    }

    @OnClick(R.id.btn_yingyeyuan)
    void btn_yingyeyuan() {
        try {
            Intent intent = new Intent(this, SaleManDialog.class);
            startActivityForResult(intent,200);
        }catch (Exception e){
        }
    }

    void btn_jiesuan() {
        try {
            query = true;
            List<HashMap<String,String>> hashMapList = new ArrayList<>();
            switch (sp_payway.getSelectedItemPosition()){
                case 0:
                    if(et_pay_money.getText().toString().equals("") || Double.parseDouble(et_pay_money.getText().toString())<money){
                        ToastUtils.showShort("填写付款金额有误。");
                        return;
                    }
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("payAmount",et_pay_money.getText().toString());
                    hashMap.put("pay_type","RMB");
                    hashMapList.add(hashMap);
                    if( !tv_pay_return.getText().toString().equals("") ){
                        HashMap<String,String> hashMap1 = new HashMap<>();
                        hashMap1.put("payAmount",tv_pay_return.getText().toString());
                        hashMap1.put("pay_type","RMB");
                        hashMap1.put("pay_way","D");
                        hashMapList.add(hashMap1);
                    }
                    break;
                case 1:
                    HashMap<String,String> hashMapZFB = new HashMap<>();
                    hashMapZFB.put("payAmount",money+"");
                    hashMapZFB.put("pay_type","ZFB");
                    hashMapList.add(hashMapZFB);
                    break;
               /* case 2:
                    HashMap<String,String> hashMapWX = new HashMap<>();
                    hashMapWX.put("payAmount",money+"");
                    hashMapWX.put("pay_type","WXZ");
                    hashMapList.add(hashMapWX);
                    break;*/
                case 2:
                    if(memberId==null || btn_jiesuan.equals("")){
                        AlertUtil.showAlert(this,"提示","请点击会员验证会员信息！");
                        return;
                    }
                    HashMap<String,String> hashMapVIP = new HashMap<>();
                    hashMapVIP.put("payAmount",money+"");
                    hashMapVIP.put("pay_type","VIP");
                    hashMapList.add(hashMapVIP);
                    break;
            }
            if( hasMoling ){
                HashMap<String,String> hashMap2 = new HashMap<>();
                hashMap2.put("payAmount",molingMoney+"");
                hashMap2.put("pay_type","CHG");
                hashMapList.add(hashMap2);
            }
            setPlayFlowBean(hashMapList);
        }catch (Exception e){
            Log.e("","");
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

    String code;
    String tempayway;
    String temMoney;
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
            case Config.MESSAGE_CAPTURE_RETURN:
                /*微信付款码是18位纯数字，以10、11、12、13、14、15为主。微信的背景色是绿色。
                支付宝条码规则：28开头的18位数字。支付宝背景色是黄色*/
                code = data.getStringExtra(Config.INTENT_EXTRA_KEY_QR_SCAN );
                tempayway = "";
                /*if(sp_payway.getSelectedItemPosition()==1){
                    tempayway = "ZFB";
                }else if(sp_payway.getSelectedItemPosition()==2){
                    tempayway = "WXZ";
                }*/
                if(code.startsWith("28")){
                    tempayway = "ZFB";
                }else{
                    tempayway = "WXZ";
                }

                temMoney = money+"";
                mLingShouScanImp.RtWzfPay(tempayway,code,FlowNo,temMoney,temMoney);
                Log.e("",code);
                break;
            case Config.SAVE_MEMBER_ID:
                memberId = data.getStringExtra("memberId");
                memberData =  (ArrayList<MemberCheckResultItem>) data.getSerializableExtra("data");
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
        et_price.setText("￥"+(money-gaiJiaMoney));
    }

    private void setSaleFlowBean(){
        if(saleMan == null || saleMan.equals("")){
            saleMan = "9999";
        }
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
            mSaleFlowBean.setSale_man(saleMan);
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

    private void setPlayFlowBean(List<HashMap<String,String>> hashMap){
        if(saleMan == null || saleMan.equals("")){
            saleMan = "9999";
        }
        for(int i=0; i<hashMap.size(); i++){
            PlayFlowBean mPlayFlowBean = new PlayFlowBean();
            mPlayFlowBean.setBranch_no(Config.branch_no);
            mPlayFlowBean.setFlow_no(FlowNo);
            mPlayFlowBean.setFlow_id(1);
            mPlayFlowBean.setSale_amount(Float.parseFloat(hashMap.get(i).get("payAmount")));
            mPlayFlowBean.setPay_way(hashMap.get(i).get("pay_type"));
            if(hashMap.get(i).get("Sell_way")==null || hashMap.get(i).get("Sell_way").equals("")){
                mPlayFlowBean.setSell_way("A");
            }else{
                mPlayFlowBean.setSell_way(hashMap.get(i).get("Sell_way"));
            }
            mPlayFlowBean.setCard_no(1);
            mPlayFlowBean.setVip_no(1);
            mPlayFlowBean.setCoin_no("RMB");
            mPlayFlowBean.setCoin_rate(1);
            mPlayFlowBean.setPay_amount(Float.parseFloat(hashMap.get(i).get("payAmount")));//付款金额
            mPlayFlowBean.setVoucher_no("");
            mPlayFlowBean.setPosid(Config.posid);
            mPlayFlowBean.setCounter_no("");
            mPlayFlowBean.setOper_id(Config.UserName);
            mPlayFlowBean.setSale_man(saleMan);
            mPlayFlowBean.setShift_no("");
            mPlayFlowBean.setOper_date(DateUtility.getCurrentTime());
            mPlayFlowBean.setMemo("");
            mPlayFlowBean.setWorderno("");
            if((i+1) == hashMap.size()){
                mPlayFlowBean.setbDealFlag("1");
            }else {
                mPlayFlowBean.setbDealFlag("0");
            }
            mPlayFlowBeanList.add(mPlayFlowBean);
        }
        mLingShouScanImp.upPlayFlow(mPlayFlowBeanList);
    }

    /**
     * 打印自定义小票
     */
    @SuppressLint("SimpleDateFormat")
    private void Print_Ex() {

    }



}
