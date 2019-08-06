package com.eshop.jinxiaocun.reportforms.view;

import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.DateUtility;

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
public class CashierCheckActivity extends CommonBaseActivity {

    @BindView(R.id.tv_startDate)
    TextView mTvStartDate;
    @BindView(R.id.tv_endDate)
    TextView mTvEndDate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cashier_check;
    }

    @Override
    protected void initView() {
        setTopToolBar("收银对账", R.mipmap.ic_left_light, "", 0, "");
        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");
    }

    @Override
    protected void initData() {

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

    }

}
