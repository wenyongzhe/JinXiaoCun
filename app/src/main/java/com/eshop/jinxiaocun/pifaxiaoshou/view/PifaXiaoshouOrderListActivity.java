package com.eshop.jinxiaocun.pifaxiaoshou.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.adapter.PifaXiaoshouOrderListAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
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
 * 创建时间  2018/9/13 0013
 * 描述
 */

public class PifaXiaoshouOrderListActivity extends CommonBaseListActivity implements INetWorResult {

    @BindView(R.id.dt_startDate)
    TextView mTvStartDate;
    @BindView(R.id.dt_endDate)
    TextView mTvEndDate;

    private PifaXiaoshouOrderListAdapter mAdapter;
    private List<DanJuMainBeanResultItem> mListInfo = new ArrayList<>();
    private IDanJuList mDanJuList;

    private int mPageIndex = 1;
    private int mPageSize = 20;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pifa_xiaoshou_order_list;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("批发销售订单列表", R.mipmap.ic_left_light, "", 0, "");

        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");


        setHeaderTitle(R.id.tv_0,R.string.list_item_Status,80);// 单据状态
        setHeaderTitle(R.id.tv_1,R.string.list_item_FormIndex,150);//单据号
        setHeaderTitle(R.id.tv_2,R.string.list_item_ProdCode,150);
        setHeaderTitle(R.id.tv_3,R.string.list_item_OrderDate,150);

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getPifaXiaoshouData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex++;
                getPifaXiaoshouData();
            }
        });

        mAdapter = new PifaXiaoshouOrderListAdapter(mListInfo);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        mDanJuList = new DanJuListImp(this);
        getPifaXiaoshouData();
    }

    private void getPifaXiaoshouData() {

        DanJuMainBean mDanJuMainBean = new DanJuMainBean();
        mDanJuMainBean.JsonData.POSID = Config.posid;
        mDanJuMainBean.JsonData.UserId = "1001";
        mDanJuMainBean.JsonData.SheetType = Config.YwType.SS.toString();//单据类型
        mDanJuMainBean.JsonData.Oper_ID = Config.posid;//操作员ID
        mDanJuMainBean.JsonData.BeginTime = "2018/09/03";
        mDanJuMainBean.JsonData.EndTime = "2018/09/13";
        mDanJuMainBean.JsonData.CheckFlag = "1";//审核标志
        mDanJuMainBean.JsonData.PageNum = mPageSize;
        mDanJuMainBean.JsonData.Page = mPageIndex;
        mDanJuList.getDanJuList(mDanJuMainBean);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        AlertUtil.showToast("position " + position);
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
        }
        @Override
        public void onDateTimeCancel() {

        }
    };

    @Override
    protected boolean createOrderBefore() {
        return true;
    }

    @Override
    protected void createOrderAfter() {
        startActivity(new Intent(this,PifaXiaoshouOrderScanActivity.class));
    }

    @Override
    protected boolean deleteBefore() {
        return false;
    }

    @Override
    protected void deleteAfter() {

    }

    @Override
    protected boolean modifyBefore() {
        return false;
    }

    @Override
    protected void modifyAfter() {

    }

    @Override
    protected boolean uploadBefore() {
        return false;
    }

    @Override
    protected void uploadAfter() {

    }

    @Override
    public void handleResule(int flag, Object o) {
        mListView.onRefreshComplete();
        switch (flag) {
            case Config.MESSAGE_OK:
                if(mPageIndex==1){
                    mListInfo = (List<DanJuMainBeanResultItem>)o;
                }else{
                    mListInfo.addAll((List<DanJuMainBeanResultItem>)o);
                }
                mAdapter.setListInfo(mListInfo);
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(o.toString());
                break;
        }

    }

}
