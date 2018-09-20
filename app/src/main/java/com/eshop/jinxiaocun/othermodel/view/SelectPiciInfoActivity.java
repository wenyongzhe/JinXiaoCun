package com.eshop.jinxiaocun.othermodel.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.othermodel.adapter.PiciInfoListAdapter;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBean;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author Lu An
 * 创建时间  2018/9/20
 * 描述
 */
public class SelectPiciInfoActivity extends CommonBaseActivity implements INetWorResult{


    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.et_pici)
    EditText mEtPici;
    @BindView(R.id.dt_startDate)
    TextView mTvStartDate;//生产日期
    @BindView(R.id.dt_endDate)
    TextView mTvEndDate;//有效日期

    private IOtherModel mOtherApi;
    private String mProduct_code;
    private PiciInfoListAdapter mAdapter;
    private List<GoodsPiciInfoBeanResult> mListData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_pici_info;
    }

    @Override
    protected void initView() {
        super.initView();

        mProduct_code = getIntent().getStringExtra("ProductCode");
        setTopToolBar("选择批次信息",R.mipmap.ic_left_light,"",0,"");
        mAdapter = new PiciInfoListAdapter(mListData);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resultPiciInfo(mListData.get(position));
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        mOtherApi = new OtherModelImp(this);
        getGoodsPiciInfo(mProduct_code);
    }

    //获取商品批次信息
    private void getGoodsPiciInfo(String product_code){
        GoodsPiciInfoBean bean = new GoodsPiciInfoBean();
        bean.JsonData.as_branchNo= Config.branch_no;//门店号
        bean.JsonData.as_posid=Config.posid;
        bean.JsonData.as_item_no=product_code;//商品编码
        mOtherApi.getGoodsPiciInfo(bean);
    }

    @OnClick(R.id.btn_sure)
    void oncLICKSure(){
        if(TextUtils.isEmpty(mEtPici.getText().toString())){
            AlertUtil.showToast("请输入批次号!");
            return;
        }

        GoodsPiciInfoBeanResult pici = new GoodsPiciInfoBeanResult();
        pici.setItem_barcode(mEtPici.getText().toString());
        pici.setProduce_date(TextUtils.isEmpty(mTvStartDate.getText().toString())?"":mTvStartDate.getText().toString());
        pici.setValid_date(TextUtils.isEmpty(mTvEndDate.getText().toString())?"":mTvEndDate.getText().toString());
        pici.setStock_qty(0);

        resultPiciInfo(pici);

    }

    private void resultPiciInfo(GoodsPiciInfoBeanResult piciInfo){
        Intent intent = new Intent();
        intent.putExtra("PiciEntity",piciInfo);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected boolean onTopBarLeftClick() {
        showHint();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            showHint();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showHint(){
        AlertUtil.showAlert(this, R.string.dialog_title, "没有选择/输入批次信息，您确定要退出吗？", R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                AlertUtil.dismissDialog();
            }
        }, R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertUtil.dismissDialog();
            }
        });
    }


    @OnClick(R.id.dt_startDate)
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
        public void onDateTimeCancel() {

        }
    };

    @OnClick(R.id.dt_endDate)
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
        public void onDateTimeCancel() {

        }
    };

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //获取商品批次信息
            case Config.RESULT_SUCCESS:
                mAdapter.setListInfo((List<GoodsPiciInfoBeanResult>)o);
                break;
            case Config.RESULT_FAIL:
                break;
        }
    }
}
