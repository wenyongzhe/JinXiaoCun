package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseListActivity;
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.piandian.adapter.PandianListAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Lu An
 * 创建时间  2018/8/24
 * 描述
 */
public class PandianListActivity extends CommonBaseListActivity implements INetWorResult {

    @BindView(R.id.dt_startDate)
    TextView mTvStartDate;
    @BindView(R.id.dt_endDate)
    TextView mTvEndDate;

    private PandianListAdapter mAdapter;
    private List<DanJuMainBeanResultItem> mListInfo = new ArrayList<>();
    private IDanJuList mServerApi;

    private int mPageIndex = 1;
    private int mPageSize =20;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_piandian_list;
    }

    @Override
    protected void initData() {
        super.initData();
        mServerApi = new DanJuListImp(this);
        getPandianListData();
    }

    private void getPandianListData(){
        DanJuMainBean mDanJuMainBean = new DanJuMainBean();
        mDanJuMainBean.JsonData.pos_id = Config.posid;
        mDanJuMainBean.JsonData.branchNo = Config.branch_no;
        mDanJuMainBean.JsonData.sheettype = Config.YwType.CR.toString();//单据类型
        mDanJuMainBean.JsonData.operid = Config.UserId;//操作员ID
        mDanJuMainBean.JsonData.begintime = mTvStartDate.getText().toString();
        mDanJuMainBean.JsonData.endtime = mTvEndDate.getText().toString();
        mDanJuMainBean.JsonData.checkflag = "0";//1代表审核 0代表没有审核
        mDanJuMainBean.JsonData.pagenum = mPageSize;
        mDanJuMainBean.JsonData.page = mPageIndex;
        mServerApi.getDanJuList(mDanJuMainBean);
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("盘点列表",R.mipmap.ic_left_light,"",0,"");
        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getPandianListData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex ++;
                getPandianListData();
            }
        });

        setHeaderTitle(R.id.tv_0,R.string.list_item_FormIndex,150);//单据号
        setHeaderTitle(R.id.tv_1,R.string.list_item_ShopName,150); //门店名称
        setHeaderTitle(R.id.tv_2,R.string.list_item_Fanwei,100);//范围
        setHeaderTitle(R.id.tv_3,R.string.list_item_Cangku,150);//仓库
        setHeaderTitle(R.id.tv_4,R.string.list_item_AllGoodsCount,100);//总商品数
        setHeaderTitle(R.id.tv_5,R.string.list_item_Oper_Name,100);//总商品数

        mAdapter = new PandianListAdapter(mListInfo);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    public void handleResule(int flag, Object o) {
        mListView.onRefreshComplete();
//        switch (flag) {
//            case Config.MESSAGE_OK:
//                if(mPageIndex==1){
//                    mListInfo = (List<DanJuMainBeanResultItem>)o;
//                }else{
//                    mListInfo.addAll((List<DanJuMainBeanResultItem>)o);
//                }
//                mAdapter.setListInfo(mListInfo);
//                break;
//            case Config.MESSAGE_ERROR:
//                AlertUtil.showToast(o.toString());
//                break;
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        mAdapter.setItemClickPosition(position);
        mAdapter.notifyDataSetInvalidated();
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
            getPandianListData();
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
            getPandianListData();
        }
        @Override
        public void onDateTimeCancel() {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==2 && resultCode ==22){
            getPandianListData();
        }
    }

    @Override
    protected boolean createOrderBefore() {
        return true;
    }

    @Override
    protected void createOrderAfter() {
        startActivityForResult(new Intent(PandianListActivity.this,PandianCreateActivity.class),2);
    }

    @Override
    protected boolean deleteOrderBefore() {
        return false;
    }

    @Override
    protected void deleteOrderAfter() {

    }

    @Override
    protected boolean modifyBefore() {
        return false;
    }

    @Override
    protected void modifyAfter() {

    }

    @Override
    protected boolean checkBefore() {
        return false;
    }

    @Override
    protected void checkAfter() {

    }
}
