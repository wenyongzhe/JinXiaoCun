package com.eshop.jinxiaocun.caigou.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.caigou.adapter.CaigouOrderListAdapter;
import com.eshop.jinxiaocun.db.BusinessBLL;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Lu An
 * 创建时间  2018/9/27
 * 描述 采购订单
 */

public class CaigouOrderListActivity extends CommonBaseListActivity implements INetWorResult {

    @BindView(R.id.dt_startDate)
    TextView mTvStartDate;
    @BindView(R.id.dt_endDate)
    TextView mTvEndDate;

    private CaigouOrderListAdapter mAdapter;
    private List<DanJuMainBeanResultItem> mListInfo = new ArrayList<>();
    private IDanJuList mDanJuList;
    private IOtherModel mServerApi;
    private DanJuMainBeanResultItem mSelectMainBean;
    private int mPageIndex = 1;
    private int mPageSize = 20;
    private String mCheckflag = "0";//0未审核，1审核
    private final String mSheetType = "本地_"+Config.YwType.PO.toString();
    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_caigou_order_list;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("采购订单列表", R.mipmap.ic_left_light, "", 0, "");
        setTopToolBarRightTitleAndStyle("审核单",R.drawable.border_bg);

        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");

        setHeaderTitle(R.id.tv_0,R.string.list_item_XuHao,50);//序号
        setHeaderTitle(R.id.tv_1,R.string.list_item_Status,100);//单据状态
        setHeaderTitle(R.id.tv_2,R.string.list_item_FormIndex,150);//单据号
        setHeaderTitle(R.id.tv_3,R.string.list_item_BillType,100);// 单据类型
        setHeaderTitle(R.id.tv_4,R.string.list_item_SupName,150); //供应商名称
        setHeaderTitle(R.id.tv_5,R.string.list_item_AllGoodsCount,100);//总商品数
        setHeaderTitle(R.id.tv_6,R.string.list_item_ValidDate,150);//交货日期

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getCaigouOrderData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex++;
                getCaigouOrderData();
            }
        });

        mAdapter = new CaigouOrderListAdapter(mListInfo);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        mDanJuList = new DanJuListImp(this);
        mServerApi = new OtherModelImp(this);
        getCaigouOrderData();
    }

    private void getCaigouOrderData() {
        DanJuMainBean mDanJuMainBean = new DanJuMainBean();
        mDanJuMainBean.JsonData.pos_id = Config.posid;
        mDanJuMainBean.JsonData.branchNo = Config.branch_no;
        mDanJuMainBean.JsonData.sheettype = Config.YwType.PO.toString();//单据类型
        mDanJuMainBean.JsonData.operid = Config.UserId;//操作员ID
        mDanJuMainBean.JsonData.begintime = mTvStartDate.getText().toString();
        mDanJuMainBean.JsonData.endtime = mTvEndDate.getText().toString();
        mDanJuMainBean.JsonData.checkflag = mCheckflag;//审核标志
        mDanJuMainBean.JsonData.pagenum = mPageSize;
        mDanJuMainBean.JsonData.page = mPageIndex;
        mDanJuList.getDanJuList(mDanJuMainBean);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        mSelectMainBean = mListInfo.get(position-1);
        mAdapter.setItemClickPosition(position-1);
        mAdapter.notifyDataSetInvalidated();
        mTvCurrentPosition.setText("当前选择是第"+position+"条单据");
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
            getCaigouOrderData();
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
            getCaigouOrderData();
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
                    if("0".equals(mCheckflag)){//未审核
                        //取缓存本地的主表信息
                        List<DanJuMainBeanResultItem> datas = BusinessBLL.getInstance().getOrderMainInfos(mSheetType);
                        datas.addAll((List<DanJuMainBeanResultItem>)o);
                        mListInfo=datas;
                    }else{
                        mListInfo = (List<DanJuMainBeanResultItem>)o;
                    }
                    if(mListInfo.size()>0){
                        mLayoutBottomTxt.setVisibility(View.VISIBLE);
                    }else{
                        mLayoutBottomTxt.setVisibility(View.GONE);
                    }
                }else{
                    mListInfo.addAll((List<DanJuMainBeanResultItem>)o);
                }
                if(mListInfo.size()>0){
                    mTvAllCount.setText("总共有"+mListInfo.size()+"条单据");
                }
                mAdapter.setListInfo(mListInfo,mCheckflag);
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(o.toString());
                break;
            case Config.RESULT_SUCCESS://审核成功
                AlertUtil.showToast(o.toString());
                mSelectMainBean = null;
                mPageIndex = 1;
                getCaigouOrderData();
                break;
            case Config.RESULT_FAIL://审核失败
                AlertUtil.showToast(o.toString());
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==2 && resultCode ==22){
            setTopToolBarRightTitle("审核单");
            mCheckflag ="0";
            mPageIndex =1;
            mSelectMainBean =null;
            mAdapter.setItemClickPosition(-1);
            mAdapter.notifyDataSetInvalidated();
            mTvCurrentPosition.setText("当前选择是第0条单据");
            getCaigouOrderData();
        }
    }

    @Override
    protected boolean createOrderBefore() {
        return true;
    }

    @Override
    protected void createOrderAfter() {
        startActivityForResult(new Intent(this,CaigouOrderScanActivity.class),2);
    }

    @Override
    protected boolean deleteOrderBefore() {
        if(mSelectMainBean==null){
            AlertUtil.showToast("请选择单据，再做审核操作!");
            return false;
        }
        return true;
    }
    @Override
    protected void deleteOrderAfter() {
        if(mSheetType.equals(mSelectMainBean.getSheetType())){
            int isSuccess = BusinessBLL.getInstance().deleteMainInfoAndGoodsInfo(mSelectMainBean.getSheet_No());
            if(isSuccess ==0){
                AlertUtil.showToast("删除本地数据失败");
                return;
            }
            for (DanJuMainBeanResultItem item : mListInfo) {
                if(item.getSheet_No().equals(mSelectMainBean.getSheet_No())){
                    mListInfo.remove(item);
                    break;
                }
            }

            mSelectMainBean =null;
            mAdapter.setItemClickPosition(-1);
            mAdapter.notifyDataSetInvalidated();
        }else{
            AlertUtil.showToast("删除还没有接口!");
        }
    }
    @Override
    protected boolean modifyBefore() {
        if(mSelectMainBean==null){
            AlertUtil.showToast("请选择单据，再做审核操作!");
            return false;
        }
        return true;
    }
    @Override
    protected void modifyAfter() {
        Intent intent = new Intent(this,CaigouOrderScanActivity.class);
        intent.putExtra("Checkflag",mCheckflag);
        intent.putExtra("MainBean",mSelectMainBean);
        startActivityForResult(intent,2);
    }
    @Override
    protected boolean checkBefore() {

        if(!CommonUtility.getInstance().havePermission(14)){
            AlertUtil.showToast("没有权限，不能做审核操作，请联系管理员!");
            return false;
        }
        if(mListInfo.size()==0){
            AlertUtil.showToast("没有单据，不能做审核操作!");
            return false;
        }
        if(mSelectMainBean==null){
            AlertUtil.showToast("请选择单据，再做审核操作!");
            return false;
        }
        if(mSheetType.equals(mSelectMainBean.getSheetType())){
            AlertUtil.showToast("单据内容没有保存，请先修改内容并保存内容!");
            return false;
        }
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核过，不能再做审核操作!");
            return false;
        }
        return true;
    }
    @Override
    protected void checkAfter() {
        mServerApi.sheetCheck(mSelectMainBean.getSheetType(),mSelectMainBean.getSheet_No());
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
        mSelectMainBean = null;
        getCaigouOrderData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mListInfo!=null){
            mListInfo.clear();
            mListInfo=null;
        }
    }

}
