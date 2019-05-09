package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.PopupWindowCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.db.BusinessBLL;
import com.eshop.jinxiaocun.lingshou.bean.GetBillMain;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.GetOptAuthResult;
import com.eshop.jinxiaocun.lingshou.bean.GetPluPriceBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.GetSystemBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.NetPlayBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.PlayFlowBean;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.login.SystemSettingActivity;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBean;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import com.eshop.jinxiaocun.utils.AidlUtil;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DanPinGaiJiaDialog;
import com.eshop.jinxiaocun.widget.DanPinZheKouDialog;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;
import com.eshop.jinxiaocun.widget.MoneyDialog;
import com.eshop.jinxiaocun.widget.SaleManDialog;
import com.eshop.jinxiaocun.widget.ZheKouDialog;
import com.eshop.jinxiaocun.zjPrinter.BluetoothService;
import com.eshop.jinxiaocun.zjPrinter.Command;
import com.eshop.jinxiaocun.zjPrinter.PrinterCommand;
import com.google.zxing.activity.CaptureActivity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LingShouScanActivity extends BaseLinShouScanActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText et_barcode;
    @BindView(R.id.btn_add)
    Button btSell;//销售
//    @BindView(R.id.btn_delete)
//    Button btn_delete;//删除
//    @BindView(R.id.btn_modify_count)
//    Button btn_modify_count;//改数
    @BindView(R.id.tv_check_num)
    TextView tv_check_num;//总数
    @BindView(R.id.tv_yingyeyuan)
    TextView tv_yingyeyuan;
//    @BindView(R.id.ly_buttom1)
//    LinearLayout ly_buttom1;
//    @BindView(R.id.btn_yijia)
//    Button btn_yijia;//议价
//    @BindView(R.id.btn_zhekou)
//    Button btn_zhekou;//折扣
    @BindView(R.id.tv_total_num)
    TextView tv_total_num;//商品数
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;//记录数
    @BindView(R.id.tv_moling_money)
    TextView tv_moling_money;
    @BindView(R.id.tv_youhui_money)
    TextView tv_youhui_money;
    @BindView(R.id.ib_seach)
    ImageButton ib_seach;
    @BindView(R.id.btn_vip)
    Button btn_vip;
    @BindView(R.id.ly_shanpingBarcode)
    LinearLayout ly_shanpingBarcode;

    private double gaiJiaMoney = 0;
    private double youhuiMoney = 0;
    private boolean hasGaiJia = false;
    private boolean hasMoling = false;
    public final static int SELL = 110;
    public final static int SELL_DANPING_YIJIA = 111;
    public final static int SELL_ZHENDAN_YIJIA = 112;
    public final static int SELL_ZHENDAN_ZHEKOU = 113;
    public boolean isOk = false;
    private ILingshouScan mLingShouScanImp;
    private IOtherModel mIOtherModel;
    protected List<SaleFlowBean> mSaleFlowBeanList;
    protected List<PlayFlowBean> mPlayFlowBeanList;
    private String FlowNo = "";
    private Double total = 0.00;
    private Double molingMoney = 0.00;
    private List<GetPluPriceBeanResult> mGetPluPriceBeanResult;
    private List<GetClassPluResult> mGetClassPluResultList;
    private Double change = 0.0;
    private Double payMoney = 0.0;
    private List<GetClassPluResult> mListData = new ArrayList<>();
    private GetOptAuthResult mGetOptAuthResult = null;
    private static boolean is58mm = true;
    private String Pay_way = "";
    private String Pay_way2 = "";
    private boolean isVipPay = false;
    YouHuiPopupWindow mWindow;
    private int goodTotal = 0;
    List<GetSystemBeanResult.SystemJson> mSystemJson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout.setBackgroundColor(getResources().getColor(R.color.item_gray_line));
        setScanBroadCast();
        mLingShouScanImp.getSystemInfo();
    }


    //接收条码
    @Override
    protected void scanData(String barcode) {
        if(et_barcode!=null && et_barcode.getText().toString().trim().equals("")){
            et_barcode.setText(barcode);
        }
        mLingShouScanImp.getPLUInfo(barcode);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mSaleFlowBeanList = new ArrayList<>();
        mPlayFlowBeanList = new ArrayList<>();
        mLingShouScanImp = new LingShouScanImp(this);
        mIOtherModel = new OtherModelImp(this);
        mLingShouScanImp.getFlowNo();
        mLingShouScanImp.getOptAuth(Config.GRANT_ITEM_JINE);
    }

    @SuppressLint("WrongViewCast")
    ArrayList<String> spinners2 = new ArrayList<>();
    @Override
    protected void initView() {
        super.initView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View mView = this.getLayoutInflater().inflate(R.layout.activity_lingshou, null);
        mMyActionBar.setData("结算",R.mipmap.ic_left_light,"",0,"更多优惠",this);
        mLinearLayout.addView(mView,0,params);
        ButterKnife.bind(this);
        ly_shanpingBarcode.setVisibility(View.GONE);
        btSell.setText(R.string.bt_sell_infor);
        tv_check_num.setText("应收：");
        tv_total_num.setText("0");
        tv_order_num.setText("记录数：");
        tv_yingyeyuan.setText("营业员："+Config.saleMan);
        tv_moling_money.setText("抹零：￥0");
        //tv_youhui_money.setText("已优惠：￥0");
        et_barcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event !=null  && event.getAction() != KeyEvent.ACTION_DOWN){
                    return false;
                }
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == 0
                || actionId == EditorInfo.IME_ACTION_GO || actionId == 6) { /*判断是否是“GO”键*/
                    mLingShouScanImp.getPLUInfo(v.getText().toString().trim());
                    /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(inputMethodManager.isActive()){
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }

                return false;
            }
        });
        ib_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLingShouScanImp.getPLUInfo(et_barcode.getText().toString().trim());
                /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) LingShouScanActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputMethodManager.isActive()){
                    inputMethodManager.hideSoftInputFromWindow(et_barcode.getApplicationWindowToken(), 0);
                }
            }
        });

        spinners2.add("现金");
        spinners2.add("支付宝");
        spinners2.add("微信");
        //数据源
        ArrayList<String> spinners = new ArrayList<>();
        spinners.add("现金");
        spinners.add("支付宝");
        spinners.add("微信");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinners);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinners2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btn_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total!=null && total!=0.00 & total!=0.0){
                     isVipPay = true;
                    btSell.performClick();
                }
            }
        });

        mListData =  (ArrayList<GetClassPluResult>) getIntent().getSerializableExtra("mListData");
        reflashVipPrice();
        mScanAdapter = new LingShouScanAdapter(mListData);
        mListview.setAdapter(mScanAdapter);
        reflashList();
    }

    private void reflashVipPrice(){
        boolean has = false;
        if(Config.mMemberInfo!=null){
            for(int i=0; i<mListData.size(); i++){
                GetClassPluResult mGetClassPluResult = mListData.get(i);
                if(Float.parseFloat(mGetClassPluResult.getSale_price())>Float.parseFloat(mGetClassPluResult.getVip_price())){
                    mGetClassPluResult.setSale_price(mGetClassPluResult.getVip_price());
                    if(!has){
                        AlertUtil.showToast("已更新部分商品为会员价。");
                        has = true;
                    }
                }
            }
        }
    }

    @Override
    public void handleResule(int flag,Object o) {
        Intent intent;
        switch (flag){
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_GOODS_INFOR_FAIL:
                AlertUtil.showAlert(LingShouScanActivity.this,"提示", (String) o);
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showAlert(LingShouScanActivity.this,"提示","请求失败");
                break;
            case Config.MESSAGE_PICI:
                List<GoodsPiciInfoBeanResult> mGoodsPiciInfoBeanResult = (List<GoodsPiciInfoBeanResult>)o;
                GetClassPluResult mGetClassPluResult = mListData.get(mListData.size()-1);
                mGetClassPluResult.setItem_barcode(mGoodsPiciInfoBeanResult.get(0).getItem_barcode());
                mGetClassPluResult.setProduce_date(mGoodsPiciInfoBeanResult.get(0).getProduce_date());
                mGetClassPluResult.setValid_date(mGoodsPiciInfoBeanResult.get(0).getValid_date());
                reflashList();
                break;
            case Config.MESSAGE_GOODS_INFOR:
                mGetClassPluResultList = (List<GetClassPluResult>)o;
                if(mGetClassPluResultList!=null && mGetClassPluResultList.size()==0){
                    AlertUtil.showAlert(LingShouScanActivity.this,"提示", "没有商品");
                    return;
                }
                if(mGetClassPluResultList!=null && mGetClassPluResultList.size()>1){
                    intent = new Intent(this, QreShanpingActivity.class);
                    intent.putExtra("barcode",et_barcode.getText().toString());
                    startActivityForResult(intent,100);
                }else{
                    if(Integer.decode(mGetClassPluResultList.get(0).getEnable_batch())==1){
                        getPiCi(mGetClassPluResultList);
                    }else{
                        mGetClassPluResultList.get(0).setItem_barcode("");//设置批次空
                        addListData();
//                    reflashList();
                    }
                    setSaleFlowBean();
                }
                break;
            case Config.MESSAGE_FLOW_NO:
                GetFlowNoBeanResult.FlowNoJson mGetFlowNoBeanResult = (GetFlowNoBeanResult.FlowNoJson)o;
                if(mGetFlowNoBeanResult != null ){
                    FlowNo = mGetFlowNoBeanResult.getFlowNo();
                    FlowNo = MyUtils.formatFlowNo(FlowNo);
                }
                break;
            case Config.MESSAGE_BILL_DISCOUNT_RETURN:
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
                }
                reflashList();//更新取价后的价格显示
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
                    reflashList();//更新取价后的价格显示
                }
                if(isVipPay){
                    Intent mIntent = new Intent(LingShouScanActivity.this,VipPayActivity.class);
                    mIntent.putExtra("money",total);
                    startActivityForResult(mIntent,100);
                }else{

                    Double temTotal = 0.0;
                    if(Pay_way.equals("RMB") || Pay_way2.equals("RMB")){
                        change = temTotal - total;
                        if(!Pay_way2.equals("RMB")&&!Pay_way2.equals("") || !Pay_way.equals("RMB")&& !Pay_way.equals("")){
                            isOk = false;
                        }else{
                            isOk = true;
                        }
                        setPlayFlowBean(temTotal+"","RMB");
                    }else{
                        if(!Pay_way2.equals("RMB")&&Pay_way2.equals("") || !Pay_way.equals("RMB")&& !Pay_way.equals("")){
                            intent = new Intent(LingShouScanActivity.this, CaptureActivity.class);
                            startActivityForResult(intent, Config.REQ_QR_CODE);
                        }
                    }
//                    intent = new Intent(this, SelectPayDialog.class);
//                    intent.putExtra("total",total);
//                    startActivityForResult(intent,400);
                }
             /*   Intent intent = new Intent(this, MoneyDialog.class);
                intent.putExtra("total",total);
                startActivityForResult(intent,100);*/
                break;
            case Config.MESSAGE_UP_PLAY_FLOW:
                if(!Pay_way2.equals("")&&!Pay_way2.equals("RMB") || !Pay_way.equals("")&&!Pay_way.equals("RMB")){
                    intent = new Intent(LingShouScanActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, Config.REQ_QR_CODE);
                }else{
                    mLingShouScanImp.sellSub(FlowNo);
                }
                break;
            case Config.MESSAGE_SELL_SUB:
                if(isOk){
                    ToastUtils.showShort(R.string.message_sell_ok);
                    change = 0.0;
                    Double temTemPay  = 0.0;
                    change = temTemPay - total;

                    AlertUtil.showAlert(LingShouScanActivity.this, "找零", "找零"+ change,
                            "确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertUtil.dismissDialog();
                        }
                    },R.string.txt_print, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    if (mService!=null && mService.getState() != BluetoothService.STATE_CONNECTED){
//                                        Intent serverIntent = new Intent(LingShouScanActivity.this, DeviceListActivity.class);
//                                        startActivityForResult(serverIntent, SystemSettingActivity.REQUEST_CONNECT_DEVICE);
//                                    }
                                    printMs();
                                    AlertUtil.dismissDialog();
                                }
                            });
                }
                break;
            case Config.MESSAGE_GET_PAY_MODE:

                break;
            case Config.MESSAGE_GET_OPT_AUTH:
                mGetOptAuthResult = (GetOptAuthResult)o;
                break;
            case SELL:
                mLingShouScanImp.getPluPrice(FlowNo);
                break;
            case SELL_ZHENDAN_YIJIA:
                intent = new Intent(this, MoneyDialog.class);
                startActivityForResult(intent,200);
                break;
            case SELL_ZHENDAN_ZHEKOU:
                if(mGetOptAuthResult!=null && mGetOptAuthResult.getIsgrant() == 1){
                    if(mGetOptAuthResult.getSavediscount() <= mGetOptAuthResult.getLimitdiscount()){
                        ToastUtils.showShort("请先在服务器设置折扣！");
                        return;
                    }
                }else {
                    ToastUtils.showShort("没有权限！");
                    return;
                }
                intent = new Intent(this, ZheKouDialog.class);
                intent.putExtra("Savediscount",mGetOptAuthResult.getSavediscount());
                intent.putExtra("Limitdiscount",mGetOptAuthResult.getLimitdiscount());
                startActivityForResult(intent,100);
                break;
            case Config.MESSAGE_BILL_DISCOUNT:
                mLingShouScanImp.getPluPrice(FlowNo);
                break;
            case Config.MESSAGE_NET_PAY_RETURN://网络付款返回
                NetPlayBeanResult mNetPlayBeanResult =  (NetPlayBeanResult)o;
                if(mNetPlayBeanResult==null){
                    ToastUtils.showShort(R.string.message_sell_error);
                    return;
                }
                if( mNetPlayBeanResult.getReturn_code().equals("000000")){
                    Double temTotal = 0.0;
                    setPlayFlowBean(temTotal+"",mNetPlayBeanResult.getPayType());
                    isOk = true;
                }else {
                    ToastUtils.showShort(mNetPlayBeanResult.getReturn_msg());
                }
                break;
            case Config.MESSAGE_VIP_PAY_RESULT:
                //setPlayFlowBean(total+"","VIP");
                finish();
                break;
            case Config.JIE_ZHUANG:
                ToastUtils.showShort(R.string.jiezhuang_ok);
                finish();
                sendBroad();
                break;
            case Config.MESSAGE_GET_SYSTEM_INFO_RETURN:
                mSystemJson = (List<GetSystemBeanResult.SystemJson>) o;
                break;
        }
    }

    private void addListData(){
        for (int i=0; i<mListData.size(); i++){
            for(int j=0; j<mGetClassPluResultList.size(); j++){
                if(mListData.get(i).getItem_no().trim().equalsIgnoreCase(mGetClassPluResultList.get(j).getItem_no().trim())){
                    mGetClassPluResultList.remove(j);
                    break;
                }
            }
        }
        for(int i=0; i<mGetClassPluResultList.size(); i++){
            mGetClassPluResultList.get(i).setSale_price_beforModify(mGetClassPluResultList.get(i).getSale_price());
        }
        mListData.addAll(mGetClassPluResultList);
    }

    //打印小票
    private void printMs() {
//        if( !BluetoothAdapter.getDefaultAdapter().isEnabled()){
//            return;
//        }
        Print_Ex();
        finish();
    }

    /**
     * 打印自定义小票
     */
    @SuppressLint("SimpleDateFormat")
    private void Print_Ex() {

        //AidlUtil.getInstance().printText("123456打印测试", 24, false, false);

        String lang = getString(R.string.strLang);
        if ((lang.compareTo("cn")) == 0) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            String date = str + "\n\n\n";
            if (is58mm) {

                try {
//                    byte[] qrcode = PrinterCommand.getBarCommand("资江电子热敏票据打印机!", 0, 3, 6);//
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
//                    SendDataByte(qrcode);

                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("专卖店\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    String mes = "";
                    int shuliang = 0;
                    mes = "门店号: "+Config.posid+"\n单据  "+FlowNo+"\n收银员："+Config.UserName+"\n";
                    SendDataByte(mes.getBytes("GBK"));
                    mes = "品名       数量    单价    金额\n";
                    for(int i=0; i<mListData.size(); i++){
                        GetClassPluResult mGetClassPluResult = mListData.get(i);
                        Double total1 = Double.parseDouble(mGetClassPluResult.getSale_price())*Double.parseDouble(mGetClassPluResult.getSale_qnty());
                        shuliang += Integer.decode(mGetClassPluResult.getSale_qnty());
                        mes += mGetClassPluResult.getItem_name()+"   "+mGetClassPluResult.getSale_qnty()+"   "+mGetClassPluResult.getSale_price()+"     "+total1+"\n";
                    }
                    SendDataByte(mes.getBytes("GBK"));
                    mes = "数量：                "+shuliang+"\n总计：                "+total+"\n付款：                "+payMoney+"\n找零：                "+change+"\n";
                    SendDataByte(mes.getBytes("GBK"));
                    mes = "公司名称：XXXXX\n公司网址：www.xxx.xxx\n地址：深圳市xx区xx号\n电话：0755-XXXXXXXX\n服务专线：400-xxx-xxxx\n================================\n";
                    SendDataByte(mes.getBytes("GBK"));
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("谢谢惠顾,欢迎再次光临!\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);

//                    SendDataByte("(以上信息为测试模板,如有苟同，纯属巧合!)\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x02;
                    SendDataByte(Command.ESC_Align);
                    SendDataString(date);
                    SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(1));
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                try {
                    byte[] qrcode = PrinterCommand.getBarCommand("资江电子热敏票据打印机!", 0, 3, 8);
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    SendDataByte(qrcode);

                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("NIKE专卖店\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("门店号: 888888\n单据  S00003333\n收银员：1001\n单据日期：xxxx-xx-xx\n打印时间：xxxx-xx-xx  xx:xx:xx\n".getBytes("GBK"));
                    SendDataByte("品名            数量    单价    金额\nNIKE跑鞋        10.00   899     8990\nNIKE篮球鞋      10.00   1599    15990\n".getBytes("GBK"));
                    SendDataByte("数量：                20.00\n总计：                16889.00\n付款：                17000.00\n找零：                111.00\n".getBytes("GBK"));
                    SendDataByte("公司名称：NIKE\n公司网址：www.xxx.xxx\n地址：深圳市xx区xx号\n电话：0755-11111111\n服务专线：400-xxx-xxxx\n===========================================\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x01;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x11;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("谢谢惠顾,欢迎再次光临!\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x00;
                    SendDataByte(Command.ESC_Align);
                    Command.GS_ExclamationMark[2] = 0x00;
                    SendDataByte(Command.GS_ExclamationMark);
                    SendDataByte("(以上信息为测试模板,如有苟同，纯属巧合!)\n".getBytes("GBK"));
                    Command.ESC_Align[2] = 0x02;
                    SendDataByte(Command.ESC_Align);
                    SendDataString(date);
                    SendDataByte(PrinterCommand.POS_Set_PrtAndFeedPaper(1));
                    SendDataByte(Command.GS_V_m_n);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     *SendDataByte
     */
    private void SendDataByte(byte[] data) {

//        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
//            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
//                    .show();
//            return;
//        }
//        mService.write(data);
    }

    /*
     * SendDataString
     */
    private void SendDataString(String data) {

//        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
//            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT)
//                    .show();
//            return;
//        }
//        if (data.length() > 0) {
//            try {
//                mService.write(data.getBytes("GBK"));
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
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
        mWindow = new YouHuiPopupWindow(this, new YouHuiPopupWindow.OnPopupWindowClick() {
            @Override
            public void OnClick(int id) {
                if(mWindow.isShowing()){
                    switch (id){
                        case R.id.id_1:
                            setSaleTemData();
                            mLingShouScanImp.saletemp(mSaleFlowBeanList);
                            break;
                        case R.id.id_2:
                            btn_zhengdanzhekou();
                            break;
                        case R.id.id_3:
                            String time = System.currentTimeMillis()+"";
                            GetBillMain mGetBillMain = new GetBillMain();
                            mGetBillMain.setTimeNo(time);
                            mGetBillMain.setTotal(total+"");
                            mGetBillMain.setCount(goodTotal+"");
                            BusinessBLL.getInstance().insertGuaDanMain(mGetBillMain);
                            boolean ok = BusinessBLL.getInstance().insertGuaDanGoodsInfo(time,mListData);
                            if(ok){
                                ToastUtils.showShort(R.string.guadan_ok);
                                finish();
                                sendBroad();
                            }
                            break;
                        case R.id.id_4://抹零
                            btn_moling();
                            break;
                        case R.id.id_5://营业员
                            btn_yingyeyuan();
                            break;
                    }
                    mWindow.dismiss();
                }
            }
        });
        //根据指定View定位
        PopupWindowCompat.showAsDropDown(mWindow,mMyActionBar.getTvRight(), 0, 0, Gravity.START);

    }

    void btn_yingyeyuan() {
        try {
            Intent intent = new Intent(this, SaleManDialog.class);
            startActivityForResult(intent,200);
        }catch (Exception e){
        }
    }

    void btn_moling() {
        try {
            molingMoney = total;
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
                    total = Double.parseDouble(MyUtils.formatDouble(1,total, RoundingMode.DOWN));
                    molingMoney -= total;
                    hasMoling = true;
                    break;
                case "2":
                    total = Double.parseDouble(MyUtils.formatDouble(0,total, RoundingMode.DOWN));
                    molingMoney -= total;
                    break;
                case "3":
                    total = Double.parseDouble(MyUtils.formatDouble(0,total, RoundingMode.HALF_UP));
                    molingMoney -= total;
                    hasMoling = true;
                    break;
                case "4":
                    total = Double.parseDouble(MyUtils.formatDouble(1,total, RoundingMode.HALF_UP));
                    molingMoney -= total;
                    hasMoling = true;
                    break;
            }

            if(hasMoling==false){
                AlertUtil.showAlert(this,"提示","不需要抹零！");
                return;
            }else{
                tv_check_num.setText("应收："+total);
                tv_moling_money.setText("抹零：￥"+molingMoney);
            }
        }catch (Exception e){
        }
    }

    private void sendBroad(){
        Intent intent = new Intent();
        intent.setAction("com.example.mymessage");
        sendBroadcast(intent);//发送标准广播
        sendOrderedBroadcast(intent,null);//发送有序广播
    }

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
            intent.putExtra("oldPrice",total);
            intent.putExtra("limit",Config.zhendanZheKoulimit);
            startActivityForResult(intent,200);
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
        double temprice;
        switch (resultCode){
            case SystemSettingActivity.REQUEST_CONNECT_DEVICE:
//                String address = data.getExtras().getString(
//                        DeviceListActivity.EXTRA_DEVICE_ADDRESS);
//                if (BluetoothAdapter.checkBluetoothAddress(address)) {
//                    BluetoothDevice device = mBluetoothAdapter
//                            .getRemoteDevice(address);
//                    mService.connect(device);
//                }
                break;
            case Config.RESULT_SELECT_GOODS:
                mGetClassPluResultList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
                if(Integer.decode(mGetClassPluResultList.get(0).getEnable_batch())==1){
                    getPiCi(mGetClassPluResultList);
                }else{
                    mGetClassPluResultList.get(0).setItem_barcode("");
                    addListData();
                    reflashList();
                }
                break;
            case RESULT_OK:
                String mCount =  data.getStringExtra("countN");
                GetClassPluResult item = getSelectObject();
                item.setSale_qnty(mCount);
                reflashList();
                break;
            case Config.MESSAGE_MONEY:
                String gaijia =  data.getStringExtra("countN");
                GetClassPluResult mGetClassPluResult = getSelectObject();
                temprice = Double.valueOf(gaijia);
                mGetClassPluResult.setSale_price(temprice+"");
                mGetClassPluResult.setHasYiJia(true);
                reflashList();
                break;
            case Config.MESSAGE_INTENT_ZHEKOU:
                hasGaiJia = true;
                if(requestCode==200){
                    String zhekou =  data.getStringExtra("countN");
                    gaiJiaMoney = total-Double.valueOf(zhekou);
                    hasGaiJia = true;
                    reflashGaiJiaItemPrice();
                }else{
                    String zhekou =  data.getStringExtra("countN");
                    GetClassPluResult mClass = getSelectObject();
                    temprice = Double.valueOf(mClass.getSale_price()) * Double.valueOf(zhekou);
                    mClass.setSale_price(temprice+"");
                    mClass.setHasYiJia(true);
                    reflashList();
                }
                break;
//            case Config.MESSAGE_MONEY:
//                if(requestCode == 100){
//                    String mMoney =  data.getStringExtra("countN");
//                    payMoney = Double.parseDouble(mMoney);
//                    change = payMoney - total;
//                    setPlayFlowBean(total+"","RMB");
//                }else if(requestCode == 200){ //整单议价、折扣
//                    String zhekou =  data.getStringExtra("countN");
//                    getBillDiscount(Double.parseDouble(zhekou));
//                }
//                break;
//            case Config.MESSAGE_INTENT_ZHEKOU:
//                String zhekou =  data.getStringExtra("countN");
//                Double int_zhekou = Double.parseDouble(zhekou);
//                if(mGetOptAuthResult!=null && mGetOptAuthResult.getIsgrant() == 1){
//                    if((int_zhekou/100)>=mGetOptAuthResult.getSavediscount() && (int_zhekou/100)<=mGetOptAuthResult.getLimitdiscount()){
//                    }else{
//                        ToastUtils.showShort("折扣必须在"+mGetOptAuthResult.getSavediscount()*100+"-"+mGetOptAuthResult.getLimitdiscount()*100);
//                        return;
//                    }
//                }
////                total = int_zhekou*total;
////                tv_check_num.setText("总价："+total);
////                setSaleFlowBean(SELL_ZHENDAN_YIJIA);
//                getBillDiscount(int_zhekou*total);
//                break;
            case Config.MESSAGE_SELECT_PAY_RETURN:
                String payway =  data.getStringExtra("Pay_way");
                Pay_way = payway;
                Intent intent;
                switch (payway){
                    case "RMB"://人民币现金
                        intent = new Intent(this, MoneyDialog.class);
                        intent.putExtra("total",total);
                        startActivityForResult(intent,100);
                        break;
                    case "ZFB"://支付宝
                        intent = new Intent(LingShouScanActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, Config.REQ_QR_CODE);
                        break;
                    case "WXZ"://微信支付
                        intent = new Intent(LingShouScanActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, Config.REQ_QR_CODE);
                        break;
                    case "SAV"://储值卡
                        break;
                    case "CRD"://人民币信用卡
                        break;
                    case "SWX"://思迅Pay_微信
                        intent = new Intent(LingShouScanActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, Config.REQ_QR_CODE);
                        break;
                    case "SZF"://思迅Pay_支付宝
                        intent = new Intent(LingShouScanActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, Config.REQ_QR_CODE);
                        break;
                    default:
                        Pay_way = "RMB";
                        break;
                }
                break;
            case Config.MESSAGE_CAPTURE_RETURN:
                String code = data.getStringExtra(Config.INTENT_EXTRA_KEY_QR_SCAN );
                String tempayway = "";
                String temMoney = "0";
                mLingShouScanImp.RtWzfPay(tempayway,code,FlowNo,temMoney,temMoney);
                Log.e("",code);
                break;
            case Config.RESULT_PAY_CANCLE:
                finish();
                sendBroad();
                break;
            case Config.MESSAGE_PAY_MAN:
                Config.saleMan = data.getStringExtra("PayMan");
                break;


        }
    }

    private void reflashGaiJiaItemPrice(){
        double tempmoney = 0;
        for(int i=0; i<mListData.size(); i++){
            GetClassPluResult mGetClassPluResult = mListData.get(i);
            double itemPrice = Double.valueOf(mGetClassPluResult.getSale_price());
            itemPrice = itemPrice-((itemPrice/total) * gaiJiaMoney);
            itemPrice =  initDouble(4,itemPrice);
            mGetClassPluResult.setSale_price(itemPrice+"");
            tempmoney += itemPrice;
        }
        setSaleFlowBean();//销售流水
        total = total-gaiJiaMoney;
        reflashList();
    }

    private double initDouble(int leng, double moneyTem){
        String totalStr = moneyTem+"";
        if((totalStr.length()-totalStr.indexOf("."))>leng){
            totalStr = totalStr.substring(0,totalStr.indexOf(".")+(leng));
            moneyTem = Double.parseDouble(totalStr);
        }
        return moneyTem;
    }

    //整单议价、折扣
    private void getBillDiscount(Double total){
        mLingShouScanImp.getBillDiscount(total,FlowNo);
    }

    private GetClassPluResult getSelectObject(){
        int itemClickPosition = mScanAdapter.getItemClickPosition();
        GetClassPluResult item = mListData.get(itemClickPosition);
        return item;
    }

    private void reflashList(){
        goodTotal = 0;
        mScanAdapter.notifyDataSetChanged();
        total = 0.0;
        double befor_youhui_money = 0.0;
        for(int i=0; i<mListData.size(); i++){
            GetClassPluResult mGetClassPluResult = mListData.get(i);
            total += (Double.parseDouble(mGetClassPluResult.getSale_price()) * Double.parseDouble(mGetClassPluResult.getSale_qnty()));
            goodTotal += Integer.decode(mGetClassPluResult.getSale_qnty());
        }
        for(int i=0; i<mListData.size(); i++){
            GetClassPluResult mGetClassPluResult = mListData.get(i);
            befor_youhui_money += (Double.parseDouble(mGetClassPluResult.getSale_price_beforModify()) * Double.parseDouble(mGetClassPluResult.getSale_qnty()));
        }
        if(befor_youhui_money>total){
            youhuiMoney = befor_youhui_money-total;
        }

        tv_check_num.setText("应收："+MyUtils.formatDouble2(total));
        tv_total_num.setText(""+goodTotal);
        tv_order_num.setText("记录数："+mListData.size());
        tv_youhui_money.setText("已优惠："+MyUtils.formatDouble2(youhuiMoney));
    }

    private void setSaleTemData(){
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
    }

    private void setSaleFlowBean(){
        mSaleFlowBeanList.clear();
        setSaleTemData();
        mLingShouScanImp.upSallFlow(mSaleFlowBeanList);

    }

    private void setPlayFlowBean(String payAmount,String pay_type){
        PlayFlowBean mPlayFlowBean = new PlayFlowBean();
        mPlayFlowBean.setBranch_no(Config.branch_no);
        mPlayFlowBean.setFlow_no(FlowNo);
        mPlayFlowBean.setFlow_id(1);
        mPlayFlowBean.setSale_amount(Float.parseFloat(payAmount));
        mPlayFlowBean.setPay_way(pay_type);
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
        if(mListData==null ||mListData.size()==0){
            ToastUtils.showShort("请选择商品");
            return;
        }
        isOk = true;
        setSaleFlowBean();
        Intent mIntent = new Intent(this,PayActivity.class);
        Bundle bundle = new Bundle();
        mIntent.putExtra("mListData", (Serializable) mListData);
        mIntent.putExtra("money",total);
        mIntent.putExtra("FlowNo",FlowNo);
        mIntent.putExtra("molingMoney",molingMoney);
        mIntent.putExtra("youhuiMoney",youhuiMoney);
        startActivityForResult(mIntent,300);

    }

    //@OnClick(R.id.btn_delete)
    void delete() {
        try {
            if(mScanAdapter.getItemClickPosition() == -1){
                ToastUtils.showShort("请选择商品");
                return;
            }
            mListData.remove(itemClickPosition);
            reflashList();
        }catch (Exception e){

        }

    }

    private boolean canModifyPrice(GetClassPluResult mGetClassPluResult){
        if(mGetClassPluResult.isHasYiJia()){
            ToastUtils.showShort("已做议价或折扣处理。");
            return false;
        }
        return true;
    }

    //@OnClick(R.id.btn_zhekou)
    void btn_zhekou() {
        try {
            if(mScanAdapter.getItemClickPosition() == -1){
                ToastUtils.showShort("请选择商品");
                return;
            }
            GetClassPluResult item = getSelectObject();
//            if( !canModifyPrice(item))return;
            if(!item.getEnable_discount().equals("1")){
                ToastUtils.showShort("此商品不允许打折。");
            }
            Intent intent = new Intent(this, DanPinZheKouDialog.class);
            intent.putExtra("oldPrice",Double.parseDouble(item.getSale_price()));
            intent.putExtra("limit",Config.danbiZheKoulimit);
            startActivityForResult(intent,100);
        }catch (Exception e){
            Log.e("","");

        }

    }

    //@OnClick(R.id.btn_yijia)
    void btn_yijia() {
        try {
            if(mScanAdapter.getItemClickPosition() == -1){
                ToastUtils.showShort("请选择商品");
                return;
            }
            GetClassPluResult item = getSelectObject();
//            if( !canModifyPrice(item))return;
            if(!item.getChange_price().equals("1")){
                ToastUtils.showShort("此商品不允许议价。");
            }
            Intent intent = new Intent(this, DanPinGaiJiaDialog.class);
            intent.putExtra("oldPrice",Double.parseDouble(item.getSale_price_beforModify()));
            intent.putExtra("limit",Config.danbiYiJialimit);
            startActivityForResult(intent,200);
        }catch (Exception e){

        }

    }

    //@OnClick(R.id.btn_modify_count)
    void modifyCount() {
        if(mScanAdapter.getItemClickPosition() == -1){
            ToastUtils.showShort("请选择商品");
            return;
        }
        GetClassPluResult mGetClassPluResult = mListData.get(itemClickPosition);
        Intent intent = new Intent();
        intent.putExtra("countN", mGetClassPluResult.getSale_qnty());
        intent.setClass(this, ModifyCountDialog.class);
        startActivityForResult(intent, 1);
    }

    private void setScanBroadCast(){
        Intent intent = new Intent("com.android.scanner.service_settings");
        intent.putExtra("action_barcode_broadcast","com.android.server.scannerservice.broadcast");
        intent.putExtra("key_barcode_broadcast", "scannerdata");
        sendBroadcast(intent);
        IntentFilter intentFilter = new IntentFilter("com.android.server.scannerservice.broadcast");
        registerReceiver(scanReceiver,intentFilter);
    }

    private BroadcastReceiver scanReceiver  = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String barcode = intent.getStringExtra("scannerdata");
            Log.e("","--"+barcode);
            et_barcode.setText(barcode);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
