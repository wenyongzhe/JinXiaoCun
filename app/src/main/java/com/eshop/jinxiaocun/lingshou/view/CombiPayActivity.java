package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DanPinGaiJiaDialog;
import com.eshop.jinxiaocun.widget.DanPinZheKouDialog;
import com.eshop.jinxiaocun.widget.SaleManDialog;
import com.eshop.jinxiaocun.zjPrinter.LingShouPrintSettingActivity;
import com.landicorp.android.eptapi.device.Printer;
import com.landicorp.android.eptapi.exception.RequestException;
import com.zxing.android.CaptureActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CombiPayActivity extends BaseActivity implements ActionBarClickListener, INetWorResult {

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
    @BindView(R.id.btn_print)
    Button btn_print;
    @BindView(R.id.ly_xianjing)
    LinearLayout ly_xianjing;
    @BindView(R.id.ly_juhezhifu)
    LinearLayout ly_juhezhifu;
    @BindView(R.id.ly_chuxukazhifu)
    LinearLayout ly_chuxukazhifu;
    @BindView(R.id.tv_xianjing)
    TextView tv_xianjing;
    @BindView(R.id.tv_zhuhezhifu)
    TextView tv_zhuhezhifu;
    @BindView(R.id.tv_chuxukazhifu)
    TextView tv_chuxukazhifu;
    @BindView(R.id.tv_last_tips)
    TextView tv_last_tips;
    @BindView(R.id.tv_last_pay)
    TextView tv_last_pay;
    double last_pay_money = 0;
    Button btn_jiesuan;//

    private boolean hasMoling = false;
    private boolean hasGaiJia = false;
    private ILingshouScan mLingShouScanImp;
    private double money = 0.00;
    List<GetSystemBeanResult.SystemJson> mSystemJson;
    private int jiaoRmb = 0;
    private int fenRmb = 0;
    private double molingMoney = 0;
    private double youhuiMoney = 0;
    private List<GetClassPluResult> mListData = new ArrayList<>();
    protected List<SaleFlowBean> mSaleFlowBeanList = new ArrayList<>();
    protected List<PlayFlowBean> mPlayFlowBeanList = new ArrayList<>();
    private double gaiJiaMoney = 0;
    private String FlowNo = "";
    private String memberId = "";
    List<HashMap<String,String>> hashMapList = new ArrayList<>();
    private static int XIAN_JING_PAY = 101;
    private double xianjing_money = 0;
    private double zhuhezhifu_money = 0;
    private double chuxukazhifu_money = 0;
    private int whaPayWay = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.activity_combi_pay, null);
        mLinearLayout.addView(bottomView,-1,params);
        ButterKnife.bind(this);

        mMyActionBar.setData("支付订单", R.mipmap.ic_left_light, "", R.drawable.icon_printer,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mIntent = new Intent(CombiPayActivity.this, LingShouPrintSettingActivity.class);
                        mIntent.putExtra("mListData", (Serializable) mListData);
                        mIntent.putExtra("money",money);
                        mIntent.putExtra("FlowNo",FlowNo);
                        mIntent.putExtra("molingMoney",molingMoney);
                        mIntent.putExtra("youhuiMoney",youhuiMoney);
                        startActivity(mIntent);
                    }
                },"整单取消", this);

        btn_jiesuan = (Button) findViewById(R.id.btn_jiesuan);
        btn_jiesuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_jiesuan();
            }
        });
        money = getIntent().getDoubleExtra("money",0.0);

        FlowNo = getIntent().getStringExtra("FlowNo");
        molingMoney = getIntent().getDoubleExtra("molingMoney",0.0);
        youhuiMoney = getIntent().getDoubleExtra("youhuiMoney",0.0);

        mListData =  (ArrayList<GetClassPluResult>) getIntent().getSerializableExtra("mListData");

        double moneyTemp0 = money*100.000;
        int moneyTemp = (int)moneyTemp0;
        fenRmb = moneyTemp%10;
        moneyTemp = (int)(money*10);
        jiaoRmb = moneyTemp%10;

        money = initDouble(3,money);
        et_price.setText("￥"+money);

        last_pay_money = money;

        mLingShouScanImp = new LingShouScanImp(this);
        mLingShouScanImp.getSystemInfo();
        ly_xianjing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whaPayWay = 1;
                Intent intent = new Intent(CombiPayActivity.this,XianJingPayActivity.class);
                intent.putExtra("money",money);
                startActivityForResult(intent,XIAN_JING_PAY);
            }
        });
        ly_juhezhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(last_pay_money==0){
                    AlertUtil.showToast("不需要再支付。");
                    return;
                }
                whaPayWay = 2;
                Intent intent = new Intent(CombiPayActivity.this,CaptureActivity.class);
                startActivityForResult(intent,Config.REQ_QR_CODE);
            }
        });
        ly_chuxukazhifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(last_pay_money==0){
                    AlertUtil.showToast("不需要再支付。");
                    return;
                }
                if(Config.mMemberInfo!=null && !Config.mMemberInfo.getCardNo_TelNo().equals("") ){
                    if(Config.mMemberInfo.getResidual_amt()<last_pay_money){
                        AlertUtil.showToast("余额不足！");
                        return;
                    }
                    mHan.sendEmptyMessage(2);
                }else{
                    Intent mIntent = new Intent(CombiPayActivity.this, VipCardPayActivity.class);
                    mIntent.putExtra("last_pay_money",last_pay_money);
                    startActivityForResult(mIntent,300);
                }
                whaPayWay = 3;
            }
        });
    }

    //打印小票
    private void printMs() {
        if( !BluetoothAdapter.getDefaultAdapter().isEnabled()){
            return;
        }
        Print_Ex();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                hashMapList.clear();
                if(whaPayWay==1){
                    last_pay_money = money;
                }
                AlertUtil.dismissDialog();
                if(o==null || ((String)o).equals("")){
                    o = "接口错误";
                }
                AlertUtil.showAlert(CombiPayActivity.this, "提示", (String)o,
                        "确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertUtil.dismissDialog();
                            }
                        });
                break;
            case Config.MESSAGE_GET_SYSTEM_INFO_RETURN:
                mSystemJson = (List<GetSystemBeanResult.SystemJson>) o;
                break;
            case Config.MESSAGE_UP_PLAY_FLOW:
                mSystemJson = (List<GetSystemBeanResult.SystemJson>) o;
                mHan.sendEmptyMessage(0);

                switch (whaPayWay){
//                    case 0:
//                        mHan.sendEmptyMessage(0);
//                        break;
//                    case 1:
//                        mHan.sendEmptyMessage(1);
//                        break;
                    case 3:
                        if(Config.mMemberInfo!=null){
                            MemberCheckResultItem mMemberCheckResultItem =  Config.mMemberInfo;
                            mLingShouScanImp.sellVipPay(FlowNo,"1",
                                    mMemberCheckResultItem.getCardNo_TelNo(),
                                    mMemberCheckResultItem.getPassword(),
                                    last_pay_money);
                        }
                        break;

                }

                break;
            case Config.MESSAGE_NET_PAY_RETURN://网络付款返回
                if(query==false){
                    return;
                }
                NetPlayBeanResult mNetPlayBeanResult =  (NetPlayBeanResult)o;
                if(mNetPlayBeanResult==null){
                    hashMapList.clear();
                    ToastUtils.showShort(R.string.message_sell_error);
                    return;
                }
                if( mNetPlayBeanResult.getReturn_code().equals("000000")){
                    tv_zhuhezhifu.setText(last_pay_money+"");
                    mHan.sendEmptyMessage(4);
                    AlertUtil.dismissDialog();
                }else {
                    if( mNetPlayBeanResult.getReturn_code().equals("1013")){
                        AlertUtil.showAlert(CombiPayActivity.this, "提示", mNetPlayBeanResult.getReturn_msg(),
                                "整单取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        query = false;
                                        AlertUtil.dismissDialog();
                                        cancle();
                                    }
                                });
                    }else{
                        AlertUtil.showAlert(CombiPayActivity.this, "提示", "结算失败 "+mNetPlayBeanResult.getReturn_msg(),
                                "确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //query = false;
                                        AlertUtil.dismissDialog();
                                    }
                                });
                    }
                }
                break;
            case Config.MESSAGE_SELL_SUB:
                sellOk();
                break;
        }
    }

    private void sellOk(){
        Config.mMemberInfo = null;
        AlertUtil.showAlert(CombiPayActivity.this, "提示", "结算处理完成",
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
    }

    private void cancle(){
        setResult(Config.RESULT_PAY_CANCLE);
        Config.mMemberInfo = null;
        finish();
    }

    boolean query = true;
    private void rtWzfQry(){
        if(query){
            mLingShouScanImp.RtWzfQry(tempayway, code, FlowNo, temMoney, temMoney);
        }
    }

    private Handler mHan = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mLingShouScanImp.sellSub(FlowNo);
                    break;
                case 1:

                  //sunmi的摄像头扫码
                   /* Intent intent = new Intent("com.summi.scan");
                    intent.setPackage("com.sunmi.sunmiqrcodescanner");
                    startActivityForResult(intent, Config.REQ_QR_CODE);*/

                    Intent intent = new Intent(CombiPayActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, Config.REQ_QR_CODE);
                    //rtWzfQry();
                    break;
                case 2:
                    if(Config.mMemberInfo==null || Config.mMemberInfo.getCardNo_TelNo().equals("") ){
                        AlertUtil.showAlert(CombiPayActivity.this,"提示","请点击会员验证会员信息！");
                        return;
                    }
                    hashMapList.clear();
                    HashMap<String,String> hashMapVIP = new HashMap<>();
                    hashMapVIP.put("payAmount",last_pay_money+"");
                    hashMapVIP.put("pay_type","SAV");
                    hashMapList.add(hashMapVIP);
                    if( hasMoling ){
                        HashMap<String,String> hashMap2 = new HashMap<>();
                        hashMap2.put("payAmount",molingMoney+"");
                        hashMap2.put("pay_type","CHG");
                        hashMapList.add(hashMap2);
                    }
                    tv_chuxukazhifu.setText(last_pay_money+"");
                    setPlayFlowBean(hashMapList,Config.mMemberInfo.getCardNo_TelNo());
                    break;
                case 3:
                    rtWzfQry();
                    break;
                case 4:
                    if(Config.mMemberInfo!=null){
                        setPlayFlowBean(hashMapList,Config.mMemberInfo.getCardNo_TelNo());
                    }else{
                        setPlayFlowBean(hashMapList,"");
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
                        molingMoney = MyUtils.convertToDouble("0.0"+fenRmb,0d);
                        money -= molingMoney;
                        hasMoling = true;
                    }
                    break;
                case "2":
                    if(jiaoRmb != 0){
                        molingMoney = MyUtils.convertToDouble("0."+jiaoRmb,0d);
                        hasMoling = true;
                    }
                    if(fenRmb != 0){
                        molingMoney += MyUtils.convertToDouble("0.0"+fenRmb,0d);
                        hasMoling = true;
                    }
                    money -= molingMoney;
                    break;
                case "3":
                    if(jiaoRmb != 0 || fenRmb != 0){
                        molingMoney = MyUtils.convertToDouble("0."+jiaoRmb+fenRmb,0d);
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
                        molingMoney = MyUtils.convertToDouble("0.0"+fenRmb,0d);
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
            moneyTem = MyUtils.convertToDouble(totalStr,0d);
        }
        return moneyTem;
    }

    private double addDouble(double moneyTem){
        String totalStr = moneyTem+"";
        int dd= totalStr.length()-totalStr.indexOf(".");
        if((totalStr.length()-totalStr.indexOf("."))==2){
            totalStr = totalStr+"0001";
            moneyTem =  MyUtils.convertToDouble(totalStr,0d);
        }
        if((totalStr.length()-totalStr.indexOf("."))==3){
            totalStr = totalStr+"0001";
            moneyTem =  MyUtils.convertToDouble(totalStr,0d);
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
            if(last_pay_money>0){
                AlertUtil.showToast("还需要支付："+last_pay_money+"元");
                return;
            }
            query = true;
            if( hasMoling ){
                HashMap<String,String> hashMap2 = new HashMap<>();
                hashMap2.put("payAmount",molingMoney+"");
                hashMap2.put("pay_type","CHG");
                hashMapList.add(hashMap2);
            }
            setPlayFlowBean(hashMapList,"");
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

    private boolean zxingScan(int requestCode, int resultCode, Intent data){
        if (requestCode == Config.REQ_QR_CODE && data != null) {
            String codedContent = data.getStringExtra("codedContent");

            tempayway = "";
            if(codedContent.startsWith("10") ||
                    codedContent.startsWith("11") ||
                    codedContent.startsWith("12") ||
                    codedContent.startsWith("13") ||
                    codedContent.startsWith("14") ||
                    codedContent.startsWith("15")){
                tempayway = "WXZ";
            }else{
                tempayway = "ZFB";
            }

            temMoney = money+"";

            HashMap<String,String> hashMapZFB = new HashMap<>();
            hashMapZFB.put("payAmount",last_pay_money+"");
            hashMapZFB.put("pay_type",tempayway);
            hashMapList.add(hashMapZFB);

            mLingShouScanImp.RtWzfPay(tempayway,codedContent,FlowNo,temMoney,temMoney);
            return true;
        }
        return false;
    }

    String code;
    String tempayway;
    String temMoney;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //sunmi摄像头扫码
        if(zxingScan(requestCode,resultCode,data)){
            return;
        }
        if(requestCode == 300){
            mHan.sendEmptyMessage(2);
            return;
        }
        switch (resultCode){
            case Config.XIANJING_RETURN://现金支付
                xianjing_money = data.getDoubleExtra("money",0);
                double money_return = data.getDoubleExtra("money_return",0);
                tv_xianjing.setText(xianjing_money+"");

                for(int i=0; i<hashMapList.size(); i++){
                    if(hashMapList.get(i).get("pay_type").equals("RMB")){
                        hashMapList.remove(i);
                    }
                }
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("payAmount",xianjing_money+"");
                hashMap.put("pay_type","RMB");
                hashMapList.add(hashMap);
                if( money_return != 0){
                    HashMap<String,String> hashMap1 = new HashMap<>();
                    hashMap1.put("payAmount",money_return+"");
                    hashMap1.put("pay_type","RMB");
                    hashMap1.put("pay_way","D");
                    hashMapList.add(hashMap1);
                }

                reflashUi();
                break;
            case Config.JUHEZHIFU_RETURN://聚合支付
                break;
            case Config.CHUXUKAZHIFU_RETURN://储蓄卡支付
                break;

            case Config.MESSAGE_PAY_MAN:
                Config.saleMan = data.getStringExtra("PayMan");
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
                ArrayList<MemberCheckResultItem> menber = (ArrayList<MemberCheckResultItem>) data.getSerializableExtra("data");
                Config.mMemberInfo =  menber.get(0);
                break;
        }
    }

    private void reflashUi(){
        last_pay_money = money-xianjing_money- zhuhezhifu_money -chuxukazhifu_money;
        if(last_pay_money<=0){
            tv_last_tips.setText("找零");
            tv_last_pay.setText(MyUtils.formatDouble2(-last_pay_money));
            if(last_pay_money==0){
                tv_last_pay.setText(MyUtils.formatDouble2(last_pay_money));
            }
            btn_jiesuan();
        }else{
            tv_last_tips.setText("还需要支付");
            tv_last_pay.setText(MyUtils.formatDouble2(last_pay_money));
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
        if(Config.saleMan == null ){
            Config.saleMan = "";
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

            String money = MyUtils.convertToFloat(mGetClassPluResult.getSale_qnty(),0f)*
                    MyUtils.convertToFloat(mGetClassPluResult.getSale_price(),0f)+"";
            mSaleFlowBean.setSale_money(money);
            mSaleFlowBean.setSell_way("A");
            mSaleFlowBean.setSale_man(Config.saleMan);
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

    private void setPlayFlowBean(List<HashMap<String,String>> hashMap,String mCard_no){
        if(Config.saleMan == null){
            Config.saleMan = "";
        }
        mPlayFlowBeanList.clear();
        for(int i=0; i<hashMap.size(); i++){
            PlayFlowBean mPlayFlowBean = new PlayFlowBean();
            mPlayFlowBean.setBranch_no(Config.branch_no);
            mPlayFlowBean.setFlow_no(FlowNo);
            mPlayFlowBean.setFlow_id(i+1);
            mPlayFlowBean.setSale_amount(money);
            mPlayFlowBean.setPay_way(hashMap.get(i).get("pay_type"));
            if(hashMap.get(i).get("Sell_way")==null || hashMap.get(i).get("Sell_way").equals("")){
                mPlayFlowBean.setSell_way("A");
            }else{
                mPlayFlowBean.setSell_way(hashMap.get(i).get("Sell_way"));
            }

            if(mPlayFlowBean.getPay_way().equals("SAV")){
                if(Config.mMemberInfo==null){
                    mPlayFlowBean.setVip_no("");
                }else{
                    mPlayFlowBean.setCard_no(Config.mMemberInfo.getCardNo_TelNo());
                    mPlayFlowBean.setVip_no(Config.mMemberInfo.getCardNo_TelNo());
                }
            }
            mPlayFlowBean.setCoin_no("");
            mPlayFlowBean.setCoin_rate(1);
            mPlayFlowBean.setPay_amount(MyUtils.convertToFloat(hashMap.get(i).get("payAmount"),0f));//付款金额
            mPlayFlowBean.setVoucher_no("");
            mPlayFlowBean.setPosid(Config.posid);
            mPlayFlowBean.setCounter_no("9999");
            mPlayFlowBean.setOper_id(Config.UserName);
            mPlayFlowBean.setSale_man(Config.saleMan);
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

        int maxLength = 25;
        String title = "销售详细小票";
        if(MyUtils.length(title)<maxLength){
            int beginLength = (maxLength-MyUtils.length(title))>>1;
            int endLength=beginLength;
            if(beginLength%2!=0){
                endLength+=1;
            }
            title = MyUtils.rpad(beginLength,"")+title+MyUtils.rpad(endLength,"");
        }

        for(int j=0; j<MyUtils.convertToInt(Config.mPrintNumber,0); j++){
            float shuliang = 0;
            String mes = title+"\n\n\n";

            if(!Config.mPrintPageHeader.equals("")){mes += Config.mPrintPageHeader+"\n";}
            if(!Config.mPrintOrderName.equals("")){mes += Config.mPrintOrderName+"\n";}
            if(Config.isPrinterCashier){mes += "收银员："+Config.UserName+"\n";}
            if(Config.mMemberInfo != null){
                if(Config.isPrinterCardNo){mes += "会员卡号："+Config.mMemberInfo.getCardNo_TelNo()+"\n";}
                if(Config.isPrinterUserName){mes += "会员姓名："+Config.mMemberInfo.getCardName()+"\n";}
                if(Config.isPrinterUserTel){mes += "客户联系方式："+Config.mMemberInfo.getVip_tel()+" "+Config.mMemberInfo.getMobile()+"\n";}
            }
            mes += "门店号: "+Config.posid+"\n单据："+FlowNo+"\n";
            mes += "品名    数量     单价     金额\n";
            mes += "-------------------------------\n";

            for(int i=0; i<mListData.size(); i++){
                GetClassPluResult mGetClassPluResult = mListData.get(i);
                Double total1 = MyUtils.convertToDouble(mGetClassPluResult.getSale_price(),0d)
                        *MyUtils.convertToDouble(mGetClassPluResult.getSale_qnty(),0d);
                shuliang += MyUtils.convertToFloat(mGetClassPluResult.getSale_qnty(),0f);

                mes += mGetClassPluResult.getItem_name()+"\n"+
                        "        "+mGetClassPluResult.getSale_qnty()+"/"+mGetClassPluResult.getItem_size()+"     "+
                        MyUtils.formatDouble2(MyUtils.convertToDouble(mGetClassPluResult.getSale_price(),0d))+"元    "+
                        MyUtils.formatDouble2(total1)+"元\n";
            }
            mes += "-------------------------------\n";
            mes += "数量：         "+shuliang+"\n总计：        "+money+"\n";
            mes += "抹零：         "+molingMoney+"\n优惠：         "+youhuiMoney+"\n";
            if(!tv_xianjing.getText().toString().equals("")){
                mes += "现金支付："+tv_xianjing.getText().toString()+"\n";
            }
            if(!tv_zhuhezhifu.getText().toString().equals("")){
                mes += "聚合支付："+tv_zhuhezhifu.getText().toString()+"\n";
            }
            if(!tv_chuxukazhifu.getText().toString().equals("")){
                mes += "储蓄卡支付："+tv_chuxukazhifu.getText().toString()+"\n";
            }
            mes += "-------------------------------\n";
            mes += "打印时间："+DateUtility.getCurrentTime()+"\n";

            if(!Config.mPrintPageFoot.equals("")){mes += "    "+Config.mPrintPageFoot+"\n";}

            mes += "\n";
            mes += "\n";
            //AidlUtil.getInstance().printText(mes, 24, false, false);
            print(mes);
        }

    }

    @Override
    public void onRightClick() {
        btn_zhengdanquxiao();
    }

//    @Override
//    protected void onTopBarRightClick() {
//        startActivity(new Intent(this, PrinterSettingActivity.class));
//    }

    /**
     * POS 签购单打印
     */
    public void print(final String mes){
        /** 1、创建 Printer.Progress 实例 */
        Printer.Progress progress = new Printer.Progress() {
            /** 2、在 Printer.Progress 的 doPrint 方法中设置签购单的打印样式 */
            @Override
            public void doPrint(Printer printer) throws Exception {
                /** 设置打印格式 */
                Printer.Format format = new Printer.Format();
                /** 中文字符打印,此处使用 16x16 点,1 倍宽&&1 倍高
                 */
                format.setHzScale(Printer.Format.HZ_SC1x1);
                format.setHzSize(Printer.Format.HZ_DOT24x24);
                printer.setFormat(format);
                printer.printText(mes);
                /** 进纸 2 行
                 */
                printer.feedLine(2);
            }
            @Override
            public void onFinish(int code) {
                /** Printer.ERROR_NONE 即打印成功 */
                if (code == Printer.ERROR_NONE) {
                    AlertUtil.showToast("打印成功!");
                }
                else {
                    AlertUtil.showToast("[打印失败]"+getErrorDescription(code));
                }
            }
            /** 根据错误码获取相应错误提示
             * @param code 错误码
             * @return 错误提示
             */
            public String getErrorDescription(int code) {
                switch(code) {
                    case Printer.ERROR_PAPERENDED: return "Paper-out, the operation is invalid this time";
                    case Printer.ERROR_HARDERR: return "Hardware fault, can not find HP signal";
                    case Printer.ERROR_OVERHEAT: return "Overheat";
                    case Printer.ERROR_BUFOVERFLOW: return "The operation buffer mode position is out of range";
                    case Printer.ERROR_LOWVOL: return "Low voltage protect";
                    case Printer.ERROR_PAPERENDING: return "Paper-out, permit the latter operation";
                    case Printer.ERROR_MOTORERR: return "The printer core fault (too fast or too slow)";
                    case Printer.ERROR_PENOFOUND: return "Automatic positioning did not find the alignment position, the paper back to its original position";
                    case Printer.ERROR_PAPERJAM: return "paper got jammed";
                    case Printer.ERROR_NOBM: return "Black mark not found";
                    case Printer.ERROR_BUSY: return "The printer is busy";
                    case Printer.ERROR_BMBLACK: return "Black label detection to black signal";
                    case Printer.ERROR_WORKON: return "The printer power is open";
                    case Printer.ERROR_LIFTHEAD: return "Printer head lift";
                    case Printer.ERROR_LOWTEMP: return "Low temperature protect";
                }
                return "unknown error ("+code+")";
            }
            @Override
            public void onCrash() {
            }
        };
        /** 3、启动打印 */
        try {
            progress.start();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }


}
