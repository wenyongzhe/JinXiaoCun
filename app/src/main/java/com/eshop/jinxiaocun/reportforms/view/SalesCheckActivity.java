package com.eshop.jinxiaocun.reportforms.view;

import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.othermodel.bean.SalesCheckResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/4
 * Desc:  销售查询
 */
public class SalesCheckActivity extends CommonBaseActivity implements INetWorResult {

    @BindView(R.id.tv_startDate)
    TextView mTvStartDate;
    @BindView(R.id.tv_endDate)
    TextView mTvEndDate;

    @BindView(R.id.tv_todaySales)
    TextView mTvTodaySales;//今日销售
    @BindView(R.id.tv_todayGathering)
    TextView mTvTodayGathering;//今日收款

    @BindView(R.id.tv_bankCard)
    TextView mTvBankCard;//银行卡
    @BindView(R.id.tv_rmbCash)
    TextView mTvRMBCash;//人民币现金


    private IOtherModel mApi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sales_check;
    }

    //初始化控件
    @Override
    protected void initView() {
        setTopToolBar("我的销售", R.mipmap.ic_left_light, "", 0, "");
        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");

    }

    //初始化数据和网络接口
    @Override
    protected void initData() {
        mApi = new OtherModelImp(this);
        getSalesCheckData();
    }

    //获取销售查询数据
    private void getSalesCheckData(){
        AlertUtil.showNoButtonProgressDialog(this,"正在获取销售数据，请稍等...");
        if(!CommonUtility.getInstance().isConnectingToInternet(this)){
            AlertUtil.showToast("没有网络，请检查网络！");
            AlertUtil.dismissProgressDialog();
           return;
        }
        mApi.getSalesCheckDatas(mTvStartDate.getText().toString(),mTvEndDate.getText().toString());
    }

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
            getSalesCheckData();//获取网络数据
        }
        @Override
        public void onDateTimeCancel() {}
    };

    @OnClick(R.id.tv_end_date)
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
            getSalesCheckData();//获取网络数据
        }
        @Override
        public void onDateTimeCancel() {}
    };

    //点击今日销售
    @OnClick(R.id.tv_todaySales)
    public void onClickTodaySales(){

    }

    //点击今日收款
    @OnClick(R.id.tv_todayGathering)
    public void onClickTodayGathering(){

    }

    //网络返回数据处理
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK:
                AlertUtil.dismissProgressDialog();
                SalesCheckResult data = (SalesCheckResult) o;
                if(data!=null){

                }else{
                    AlertUtil.showToast("没有销售数据!");
                }
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
        }
    }
}
