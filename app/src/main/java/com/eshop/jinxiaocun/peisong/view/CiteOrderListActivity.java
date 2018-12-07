package com.eshop.jinxiaocun.peisong.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.BarcodeScan;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.peisong.adapter.CiteOrderListAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
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
 * 创建时间  2018/10/12
 * 描述 引用单据查询
 */

public class CiteOrderListActivity extends CommonBaseListActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtStartDate;
    @BindView(R.id.dt_startDate)
    TextView mTvStartDate;
    @BindView(R.id.dt_endDate)
    TextView mTvEndDate;

    protected BarcodeScan mBarcodeScan;//扫描控制
    private CiteOrderListAdapter mAdapter;
    private List<DanJuMainBeanResultItem> mListInfo = new ArrayList<>();
    private IOtherModel mServerApi;
    private int mPageIndex = 1;
    private int mPageSize = 20;
    private String mCheckflag = "1";//需要时审核过的//0未审核，1审核
    //采购入库引采购订单，采购退货引采购入库；
    // 批发出库引批发订单，批发退货引批发退货；
    // 配送出库引要货单，配送入库引配送出库单
    private String mSheetType = "";
    private String mOperIdOrOrderNo = Config.UserId;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_cite_order_list;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("引用单据列表", R.mipmap.ic_left_light, "", 0, "");
//        setTopToolBarRightTitleAndStyle("审核单",R.drawable.border_bg);

        mLayoutBottom.setVisibility(View.GONE);

        mEtStartDate.setOnKeyListener(onKey);
        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");

        setHeaderTitle(R.id.tv_0,R.string.list_item_Status,100);//单据状态
        setHeaderTitle(R.id.tv_1,R.string.list_item_FormIndex,150);//单据号
        setHeaderTitle(R.id.tv_2,R.string.list_item_BillType,100);// 单据类型
        setHeaderTitle(R.id.tv_3,R.string.list_item_ShopName,150); //门店名称
        setHeaderTitle(R.id.tv_4,R.string.list_item_AllGoodsCount,100);//总商品数
        setHeaderTitle(R.id.tv_5,R.string.list_item_ValidDate,150);//交货日期

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getCiteOrderData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex++;
                getCiteOrderData();
            }
        });

        mAdapter = new CiteOrderListAdapter(mListInfo);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();

        mSheetType = getIntent().getStringExtra("SheetType");
        mServerApi= new OtherModelImp(this);
        getCiteOrderData();

        IntentFilter scanDataIntentFilter = new IntentFilter();
        scanDataIntentFilter.addAction("ACTION_BAR_SCAN");
        registerReceiver(mScanDataReceiver, scanDataIntentFilter);
        try {
            mBarcodeScan = new BarcodeScan(this);
            mBarcodeScan.open();
        }catch (Exception e){

        }
    }

    private void getCiteOrderData() {
        mServerApi.getCiteOrderDatas(mSheetType,mOperIdOrOrderNo,mCheckflag,
                mPageIndex,mPageSize,mTvStartDate.getText().toString(),mTvEndDate.getText().toString());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        Intent intent = new Intent();
//        intent.putExtra("Checkflag",mCheckflag);
        intent.putExtra("SelectOrder",mListInfo.get(position-1));
        setResult(RESULT_OK,intent);
        finish();

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
            getCiteOrderData();
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
            getCiteOrderData();
        }
        @Override
        public void onDateTimeCancel() {

        }
    };

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
                mAdapter.setListInfo(mListInfo,mCheckflag,mSheetType);
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(o.toString());
                break;

        }

    }

    //手动输入条码事件
    View.OnKeyListener onKey= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_UP){
                mPageIndex = 1;
                mOperIdOrOrderNo = mEtStartDate.getText().toString().trim();
                getCiteOrderData();
                return true;
            }
            return false;
        }
    };

    /**
     * 扫描返回数据
    */
    private BroadcastReceiver mScanDataReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals("ACTION_BAR_SCAN")) {
                String str_OperIdOrOrderNo = intent.getStringExtra("EXTRA_SCAN_DATA");
                mEtStartDate.setText(str_OperIdOrOrderNo);
                mPageIndex = 1;
                mOperIdOrOrderNo = str_OperIdOrOrderNo;
                getCiteOrderData();
            }
        }
    };

    @Override
    protected boolean createOrderBefore() {
        return false;
    }

    @Override
    protected void createOrderAfter() {

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

    @Override
    protected void onTopBarRightClick() {
        super.onTopBarRightClick();

        if(mCheckflag.equals("0")){
            setTopToolBarRightTitle("未审核单");
            mCheckflag ="1";
        }else if(mCheckflag.equals("1")){
            setTopToolBarRightTitle("审核单");
            mCheckflag ="0";
        }
        mPageIndex =1;
        mAdapter.setItemClickPosition(-1);
        getCiteOrderData();
    }

}
