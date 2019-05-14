package com.eshop.jinxiaocun.zjPrinter;

import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.utils.AidlUtil;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.ConfigureParamSP;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PrinterSettingActivity extends CommonBaseActivity {

    @BindView(R.id.iv_immediately_printer)
    ImageView mIvImmediatelyPrinter;

    @BindView(R.id.sp_print_size)
    Spinner mSpPrintSize;
    @BindView(R.id.tv_print_number)
    TextView mTvPrintNumber;

    @BindView(R.id.tv_print_order_name)
    TextView mTvPrintOrderName;
    @BindView(R.id.tv_print_page_header)
    TextView mTvPrintPageHeader;
    @BindView(R.id.tv_print_page_foot)
    TextView mTvPrintPageFoot;

    @BindView(R.id.cb_printer_card_no)
    CheckBox mCbPrinterCardNo;
    @BindView(R.id.cb_printer_user_name)
    CheckBox mCbPrinterUserName;
    @BindView(R.id.cb_printer_user_tel)
    CheckBox mCbPrinterUserTel;
    @BindView(R.id.cb_printer_cashier)
    CheckBox mCbPrinterCashier;


    private List<String> mPrintSizeDatas=new ArrayList<>();

    private boolean isImmediatelyPrinter;//是否保存后打印
    private boolean isPrinterCardNo;//是否打印卡号
    private boolean isPrinterUserName;//是否打印客户姓名
    private boolean isPrinterUserTel;//是否打印客户联系方式
    private boolean isPrinterCashier;//是否打印收银员

    @Override
    protected int getLayoutId() {
        return R.layout.activity_printer_setting;
    }


    @Override
    protected void initView() {
        setTopToolBar("打印设置",R.mipmap.ic_left_light,"",0,"");
        setTopToolBarRightTitleAndStyle("保存",R.drawable.border_bg_primary);


        isImmediatelyPrinter = ConfigureParamSP.getInstance().getValue(this,
                ConfigureParamSP.KEY_IMMEDIATELY_PRINTER,false);
        if(isImmediatelyPrinter){
            mIvImmediatelyPrinter.setImageResource(R.drawable.icon_check_printer);
        }else{
            mIvImmediatelyPrinter.setImageResource(R.drawable.icon_not_check_printer);
        }

        mPrintSizeDatas.add("58");
        ArrayAdapter<String> adapterBCType = new ArrayAdapter<>(this, R.layout.my_simple_spinner_item, mPrintSizeDatas);
        adapterBCType.setDropDownViewResource(R.layout.my_drop_down_item);
        mSpPrintSize.setAdapter(adapterBCType);

        if(!TextUtils.isEmpty(Config.mPrintSize)){
            for (int i = 0; i < mPrintSizeDatas.size(); i++) {
                if(Config.mPrintSize.equals(mPrintSizeDatas.get(i))){
                    mSpPrintSize.setSelection(i);
                    break;
                }
            }
        }

        mTvPrintNumber.setText(Config.mPrintNumber);
        mTvPrintOrderName.setText(Config.mPrintOrderName);
        mTvPrintPageHeader.setText(Config.mPrintPageHeader);
        mTvPrintPageFoot.setText(Config.mPrintPageFoot);

        isPrinterCardNo = Config.isPrinterCardNo;
        isPrinterUserName = Config.isPrinterUserName;
        isPrinterUserTel = Config.isPrinterUserTel;
        isPrinterCashier = Config.isPrinterCashier;
        mCbPrinterCardNo.setChecked(isPrinterCardNo);
        mCbPrinterUserName.setChecked(isPrinterUserName);
        mCbPrinterUserTel.setChecked(isPrinterUserTel);
        mCbPrinterCashier.setChecked(isPrinterCashier);

        mCbPrinterCardNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPrinterCardNo = isChecked;
            }
        });
        mCbPrinterUserName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPrinterUserName = isChecked;
            }
        });
        mCbPrinterUserTel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPrinterUserTel = isChecked;
            }
        });
        mCbPrinterCashier.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPrinterCashier = isChecked;
            }
        });

    }


    @Override
    protected void initData() {
    }

    // 保存
    @Override
    protected void onTopBarRightClick() {

        ConfigureParamSP.getInstance().saveValue(this,ConfigureParamSP.KEY_IMMEDIATELY_PRINTER,
                isImmediatelyPrinter);

        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINT_SIZE,mPrintSizeDatas.get(mSpPrintSize.getSelectedItemPosition()));
        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINT_NUMBER,
                TextUtils.isEmpty(mTvPrintNumber.getText().toString().trim())?""
                        :mTvPrintNumber.getText().toString().trim());
        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINT_ORDER_NAME,
                TextUtils.isEmpty(mTvPrintOrderName.getText().toString().trim())?""
                        :mTvPrintOrderName.getText().toString().trim());
        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINT_PAGE_HEADER,
                TextUtils.isEmpty(mTvPrintPageHeader.getText().toString().trim())?""
                        :mTvPrintPageHeader.getText().toString().trim());
        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINT_PAGE_FOOT,
                TextUtils.isEmpty(mTvPrintPageFoot.getText().toString().trim())?""
                        :mTvPrintPageFoot.getText().toString().trim());

        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINTER_CARD_NO,isPrinterCardNo);
        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINTER_USER_NAME,isPrinterUserName);
        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINTER_USER_TEL,isPrinterUserTel);
        ConfigureParamSP.getInstance().saveValue(PrinterSettingActivity.this,
                ConfigureParamSP.KEY_PRINTER_CASHIER,isPrinterCashier);

        Config.mPrintSize = mPrintSizeDatas.get(mSpPrintSize.getSelectedItemPosition());
        Config.mPrintNumber = TextUtils.isEmpty(mTvPrintNumber.getText().toString().trim())?""
                :mTvPrintNumber.getText().toString().trim();
        Config.mPrintOrderName = TextUtils.isEmpty(mTvPrintOrderName.getText().toString().trim())?""
                :mTvPrintOrderName.getText().toString().trim();
        Config.mPrintPageHeader = TextUtils.isEmpty(mTvPrintPageHeader.getText().toString().trim())?""
                :mTvPrintPageHeader.getText().toString().trim();
        Config.mPrintPageFoot = TextUtils.isEmpty(mTvPrintPageFoot.getText().toString().trim())?""
                :mTvPrintPageFoot.getText().toString().trim();
        Config.isPrinterCardNo = isPrinterCardNo ;
        Config.isPrinterUserName = isPrinterUserName;
        Config.isPrinterUserTel = isPrinterUserTel;
        Config.isPrinterCashier = isPrinterCashier;

        AlertUtil.showToast("保存成功!");
        //如果勾选了保存后打印就调用打印
        if(isImmediatelyPrinter){
            gotoPrint();
        }

    }


    @OnClick(R.id.iv_immediately_printer)
    public void onClickImmediatelyPrinter(){

        if(isImmediatelyPrinter){
            isImmediatelyPrinter = false;
            mIvImmediatelyPrinter.setImageResource(R.drawable.icon_not_check_printer);
        }else{
            isImmediatelyPrinter = true;
            mIvImmediatelyPrinter.setImageResource(R.drawable.icon_check_printer);
        }

    }


    @OnClick(R.id.btn_print_preview)
    public void onClickPrintPreview(){
        gotoPrint();
    }

    private void gotoPrint(){

        if(!AidlUtil.getInstance().isConnect()){
            AlertUtil.showToast("打印机没有连接，不能打印!");
            return;
        }


        int maxLength = 25;
        String title = "打印预览";
        if(MyUtils.length(title)<maxLength){
            int beginLength = (maxLength-MyUtils.length(title))>>1;
            int endLength=beginLength;
            if(beginLength%2!=0){
                endLength+=1;
            }
            title = MyUtils.rpad(beginLength,"")+title+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(title,30f,false,false);
        }

        if(isPrinterCashier){
            String cashier = "收银员: "+ Config.UserName;
            if(MyUtils.length(cashier)<maxLength){
                int endLength = maxLength-MyUtils.length(cashier);
                cashier=cashier+MyUtils.rpad(endLength,"");
                AidlUtil.getInstance().printText(cashier,30f,false,false);
            }else{//如果超过一行  要换行
                AidlUtil.getInstance().printText(cashier,30f,false,false);
                AidlUtil.getInstance().printEmptyLine(1);
            }
        }

        String dateTime = "操作时间: "+ DateUtility.getCurrentTime();
        if(MyUtils.length(dateTime)<maxLength){
            int endLength = maxLength-MyUtils.length(dateTime);
            dateTime=dateTime+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(dateTime,30f,false,false);
        }else{//如果超过一行  要换行
            AidlUtil.getInstance().printText(dateTime,30f,false,false);
            AidlUtil.getInstance().printEmptyLine(1);
        }
        AidlUtil.getInstance().printText("-------------------------",30f,false,false);


        if(isPrinterCardNo){
            String cardNo = "卡号: 88888888";
            if(MyUtils.length(cardNo)<maxLength){
                int endLength = maxLength-MyUtils.length(cardNo);
                cardNo=cardNo+MyUtils.rpad(endLength,"");
                AidlUtil.getInstance().printText(cardNo,30f,false,false);
            }else{//如果超过一行  要换行
                AidlUtil.getInstance().printText(cardNo,30f,false,false);
                AidlUtil.getInstance().printEmptyLine(1);
            }
        }

        if(isPrinterUserName){
            String userName = "客户姓名: 张三";
            if(MyUtils.length(userName)<maxLength){
                int endLength = maxLength-MyUtils.length(userName);
                userName=userName+MyUtils.rpad(endLength,"");
                AidlUtil.getInstance().printText(userName,30f,false,false);
            }else{//如果超过一行  要换行
                AidlUtil.getInstance().printText(userName,30f,false,false);
                AidlUtil.getInstance().printEmptyLine(1);
            }
        }

        if(isPrinterUserTel){
            String userTel = "客户联系方式: 13899996666";
            if(MyUtils.length(userTel)<maxLength){
                int endLength = maxLength-MyUtils.length(userTel);
                userTel=userTel+MyUtils.rpad(endLength,"");
                AidlUtil.getInstance().printText(userTel,30f,false,false);
            }else{//如果超过一行  要换行
                AidlUtil.getInstance().printText(userTel,30f,false,false);
                AidlUtil.getInstance().printEmptyLine(1);
            }
        }


        AidlUtil.getInstance().printText("-------------------------",30f,false,false);
        String pageFoot = mTvPrintPageFoot.getText().toString().trim();
        if(MyUtils.length(pageFoot)<maxLength){
            int beginLength = (maxLength-MyUtils.length(pageFoot))>>1;
            int endLength=beginLength;
            if(beginLength%2!=0){
                endLength+=1;
            }
            pageFoot = MyUtils.rpad(beginLength,"")+pageFoot+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(pageFoot,30f,false,false);
        }

        AidlUtil.getInstance().printEmptyLine(3);

    }



}