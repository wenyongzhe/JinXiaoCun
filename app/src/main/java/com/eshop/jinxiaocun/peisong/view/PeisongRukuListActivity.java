package com.eshop.jinxiaocun.peisong.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonListActivity;
import com.eshop.jinxiaocun.db.BusinessBLL;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.peisong.adapter.PeisongRukuListAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Lu An
 * 创建时间  2018/9/29
 * 描述 配送入库
 */

public class PeisongRukuListActivity extends CommonListActivity implements INetWorResult {

    @BindView(R.id.dt_startDate)
    TextView mTvStartDate;
    @BindView(R.id.dt_endDate)
    TextView mTvEndDate;

    private DatePickerDialog mStartDate,mEndDate;
    private PeisongRukuListAdapter mAdapter;
    private List<DanJuMainBeanResultItem> mListInfo = new ArrayList<>();
    private IDanJuList mDanJuList;
    private IOtherModel mServerApi;
    private DanJuMainBeanResultItem mSelectMainBean;
    private int mPageIndex = 1;
    private int mPageSize = 20;
    private String mCheckflag = "0";//0未审核，1审核
    private final String mSheetType = "本地_"+Config.YwType.MI.toString();
    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_peisong_ruku_list;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("配送入库列表", R.mipmap.ic_left_light, "", 0, "");
        setTopToolBarRightTitleAndStyle("审核单",R.drawable.border_bg);

        mTvStartDate.setText(DateUtility.getCurrentDate()+" 00:00:00");
        mTvEndDate.setText(DateUtility.getCurrentDate()+" 23:59:59");

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getPeisongRukuData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex++;
                getPeisongRukuData();
            }
        });

        mAdapter = new PeisongRukuListAdapter(mListInfo);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        mDanJuList = new DanJuListImp(this);
        mServerApi= new OtherModelImp(this);
        getPeisongRukuData();
    }
    private void getPeisongRukuData() {
        DanJuMainBean mDanJuMainBean = new DanJuMainBean();
        mDanJuMainBean.JsonData.pos_id = Config.posid;
        mDanJuMainBean.JsonData.branchNo = Config.branch_no;
        mDanJuMainBean.JsonData.sheettype = Config.YwType.MI.toString();//单据类型
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
        mSelectMainBean = mListInfo.get(position);
        mAdapter.setItemClickPosition(position);
        mAdapter.notifyDataSetInvalidated();
        mTvCurrentPosition.setText("当前选择是第"+(position+1)+"条单据");
    }

    @OnClick(R.id.dt_startDate)
    void OnStartDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mStartDate = new DatePickerDialog(this,listenerStart,year,month,day);
        mStartDate.show();
    }

    DatePickerDialog.OnDateSetListener listenerStart = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // 获取月，这里需要需要月份的范围为0~11，因此获取月份的时候需要+1才是当前月份值
            int m = month+1;
            String strMonth = m>9?m+"":"0"+m;
            mTvStartDate.setText(year+"-"+strMonth+"-"+dayOfMonth+" 00:00:00");
            getPeisongRukuData();
        }
    };

    @OnClick(R.id.dt_endDate)
    void OnEndDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mEndDate = new DatePickerDialog(this,listenerEnd,year,month,day);
        mEndDate.show();
    }
    DatePickerDialog.OnDateSetListener listenerEnd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // 获取月，这里需要需要月份的范围为0~11，因此获取月份的时候需要+1才是当前月份值
            int m = month+1;
            String strMonth = m>9?m+"":"0"+m;
            mTvEndDate.setText(year+"-"+strMonth+"-"+dayOfMonth+" 23:59:59");
            getPeisongRukuData();
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
                getPeisongRukuData();
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
            getPeisongRukuData();
        }
    }

    @Override
    protected boolean createOrderBefore() {
        return true;
    }

    @Override
    protected void createOrderAfter() {
        startActivityForResult(new Intent(this,PeisongRukuScanActivity.class),2);
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
        Intent intent = new Intent(this,PeisongRukuScanActivity.class);
        intent.putExtra("Checkflag",mCheckflag);
        intent.putExtra("MainBean",mSelectMainBean);
        startActivityForResult(intent,2);
    }

    @Override
    protected boolean checkBefore() {

        if(!CommonUtility.getInstance().havePermission(4)){
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
        getPeisongRukuData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mListInfo!=null){
            mListInfo.clear();
            mListInfo=null;
        }

        if(mStartDate!=null &&mStartDate.isShowing()){
            mStartDate.dismiss();
            mStartDate=null;
        }

        if(mEndDate!=null &&mEndDate.isShowing()){
            mEndDate.dismiss();
            mEndDate=null;
        }

    }

}
