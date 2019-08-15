package com.eshop.jinxiaocun.reportforms.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.othermodel.bean.CashierCheckResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.AidlUtil;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/6
 * Desc: 收银对账
 */
public class CashierCheckActivity extends CommonBaseActivity implements INetWorResult {

    @BindView(R.id.tv_startDate)
    TextView mTvStartDate;
    @BindView(R.id.tv_endDate)
    TextView mTvEndDate;
    @BindView(R.id.tv_cashierCheckContent)
    TextView mTvContent;

    private IOtherModel mApi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cashier_check;
    }

    @Override
    protected void initView() {
        setTopToolBar("收银对账", R.mipmap.ic_left_light, "", 0, "");
        setTopToolBarRightTitleAndStyle("打印",R.drawable.border_bg);
        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");
    }

    @Override
    protected void initData() {
        mApi = new OtherModelImp(this);
    }

    //获取收银对账数据
    private void getCashierCheckData(){
        AlertUtil.showNoButtonProgressDialog(this,"正在获取收银对账数据，请稍等...");
        if(!CommonUtility.getInstance().isConnectingToInternet(this)){
            AlertUtil.showToast("没有网络，请检查网络！");
            AlertUtil.dismissProgressDialog();
            return;
        }
        mApi.getSydzDatas(mTvStartDate.getText().toString(),mTvEndDate.getText().toString());
    }

    //开始时间
    @OnClick(R.id.tv_startDate)
    void OnStartDate(){
        new SlideDateTimePicker.Builder(this.getSupportFragmentManager())
                .setListener(listenerStart)
                .setInitialDate(new Date())
                .build()
                .show();
    }
    private SlideDateTimeListener listenerStart = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = format.format(calendar.getTime());
            mTvStartDate.setText(strDate);
        }
        @Override
        public void onDateTimeCancel() {}
    };

    //结束时间
    @OnClick(R.id.tv_endDate)
    void OnEndDate(){
        new SlideDateTimePicker.Builder(this.getSupportFragmentManager())
                .setListener(listenerEnd)
                .setInitialDate(new Date())
                .build()
                .show();
    }
    private SlideDateTimeListener listenerEnd = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = format.format(calendar.getTime());
            mTvEndDate.setText(strDate);
        }
        @Override
        public void onDateTimeCancel() {}
    };

    //对账
    @OnClick(R.id.btn_accountChecking)
    public void onClickAccountChecking(){
        getCashierCheckData();
    }

    @Override
    protected void onTopBarRightClick() {
        if(TextUtils.isEmpty(mTvContent.getText().toString().trim())){
            AlertUtil.showToast("没有对账内容，不能打印!");
            return ;
        }

        if(AidlUtil.getInstance().isConnect()){
            AlertUtil.showAlert(this, R.string.dialog_title, R.string.is_need_print,
                    R.string.yes, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertUtil.dismissDialog();
                            onPrintln();
                        }
                    }, R.string.no, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertUtil.dismissDialog();
                        }
                    });
        }else{
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("温馨提示");
            dialog.setMessage("未连接打印机！");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }

    //打印
    private void onPrintln(){
        int maxLength = 25;
        String title = "收银对账详情";
        if(MyUtils.length(title)<maxLength){
            int beginLength = (maxLength-MyUtils.length(title))>>1;
            int endLength=beginLength;
            if(beginLength%2!=0){
                endLength+=1;
            }
            title = MyUtils.rpad(beginLength,"")+title+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(title+"\n",30f,false,false);
        }

        String userName = "操作人: "+ Config.UserName;
        if(MyUtils.length(userName)<maxLength){
            int endLength = maxLength-MyUtils.length(userName);
            userName=userName+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(userName+"\n",30f,false,false);
        }else{//如果超过一行  要换行
            AidlUtil.getInstance().printText(userName+"\n",30f,false,false);
            AidlUtil.getInstance().printEmptyLine(1);
        }

        String exchargeData = "操作时间: "+ DateUtility.getCurrentTime();
        if(MyUtils.length(exchargeData)<maxLength){
            int endLength = maxLength-MyUtils.length(exchargeData);
            exchargeData=exchargeData+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(exchargeData+"\n",30f,false,false);
        }else{//如果超过一行  要换行
            AidlUtil.getInstance().printText(exchargeData+"\n",30f,false,false);
            AidlUtil.getInstance().printEmptyLine(1);
        }
        AidlUtil.getInstance().printText("========================="+"\n",30f,false,false);

        AidlUtil.getInstance().printText(mTvContent.getText().toString(),30f,false,false);

        AidlUtil.getInstance().printText("========================="+"\n",30f,false,false);

        AidlUtil.getInstance().printEmptyLine(3);
        AidlUtil.getInstance().print();
    }

    //网络返回数据处理
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                AlertUtil.dismissProgressDialog();
                CashierCheckResult data = (CashierCheckResult) o;
                if(data!=null){
                    mTvContent.setText(data.getRepDetail());
                }else{
                    AlertUtil.showToast("没有收银对账数据!");
                }
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
        }
    }
}
