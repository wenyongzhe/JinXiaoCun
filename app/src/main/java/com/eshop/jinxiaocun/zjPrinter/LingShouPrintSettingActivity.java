package com.eshop.jinxiaocun.zjPrinter;

import android.content.Intent;
import android.os.Bundle;

import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.utils.AidlUtil;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class LingShouPrintSettingActivity extends PrinterSettingActivity {

    private double money;
    private String  FlowNo;
    private double  molingMoney;
    private double  youhuiMoney;
    private List<GetClassPluResult> mListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        money = getIntent().getDoubleExtra("money",0.0);
        FlowNo = getIntent().getStringExtra("FlowNo");
        molingMoney = getIntent().getDoubleExtra("molingMoney",0.0);
        youhuiMoney = getIntent().getDoubleExtra("youhuiMoney",0.0);
        mListData =  (ArrayList<GetClassPluResult>) getIntent().getSerializableExtra("mListData");
    }

    @Override
    public void gotoPrintPreview() {
        int shuliang = 0;
        String mes = "";

        if(!Config.mPrintPageHeader.equals("")){mes += Config.mPrintPageHeader+"\n";}
        if(!Config.mPrintOrderName.equals("")){mes += Config.mPrintOrderName+"\n";}
        if(Config.isPrinterCashier){mes += "收银员："+Config.UserName+"\n";}
        if(Config.mMemberInfo != null){
            if(Config.isPrinterCardNo){mes += "会员卡号："+Config.mMemberInfo.getCardNo_TelNo()+"\n";}
            if(Config.isPrinterUserName){mes += "会员姓名："+Config.mMemberInfo.getCardName()+"\n";}
            if(Config.isPrinterUserTel){mes += "客户联系方式："+Config.mMemberInfo.getVip_tel()+" "+Config.mMemberInfo.getMobile()+"\n";}
        }
        mes += "门店号: "+Config.posid+"\n单据  "+FlowNo+"\n";
        mes += "品名      数量    单价    金额\n";
        mes += "-------------------------------\n";

        for(int i=0; i<mListData.size(); i++){
            GetClassPluResult mGetClassPluResult = mListData.get(i);
            Double total1 = Double.parseDouble(mGetClassPluResult.getSale_price())*Double.parseDouble(mGetClassPluResult.getSale_qnty());
            shuliang += Integer.decode(mGetClassPluResult.getSale_qnty());
            mes += mGetClassPluResult.getItem_name()+"\n            "+
                    mGetClassPluResult.getSale_qnty()+"   "+
                    MyUtils.formatDouble2(Double.parseDouble(mGetClassPluResult.getSale_price()))+"     "+
                            total1+"\n";
        }

        mes += "-------------------------------\n";
        mes += "数量：     "+shuliang+"\n总计：     "+money+"\n";
        mes += "抹零：     "+molingMoney+"\n优惠：     "+youhuiMoney+"\n";
        mes += "-------------------------------\n";
        if(!Config.mPrintPageFoot.equals("")){mes += Config.mPrintPageFoot+"\n";}

        mes += "\n";
        mes += "\n";

        Intent mIntent = new Intent(this,PrintPreviewActivity.class);
        mIntent.putExtra("content",mes);
        startActivity(mIntent);
    }
}
