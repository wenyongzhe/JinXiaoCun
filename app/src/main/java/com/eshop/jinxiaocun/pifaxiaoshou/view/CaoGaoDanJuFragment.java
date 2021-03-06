package com.eshop.jinxiaocun.pifaxiaoshou.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseListFragment;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.RefreshListView;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class CaoGaoDanJuFragment extends BaseListFragment implements INetWorResult {
    private List<DanJuMainBeanResult> mListData;
    private CaoGaoListAdapter mXiaoshouDanAdapter;

    public static CaoGaoDanJuFragment getInstance() {
        CaoGaoDanJuFragment sf = new CaoGaoDanJuFragment();
        return sf;
    }

    @Override
    protected void loadData() {
        DanJuMainBean mDanJuMainBean = new DanJuMainBean();
        mDanJuMainBean.JsonData.pos_id = Config.posid;
        mDanJuMainBean.JsonData.branchNo = Config.branch_no;
        mDanJuMainBean.JsonData.sheettype = "";//单据类型
        mDanJuMainBean.JsonData.operid = Config.UserId;//操作员ID
        mDanJuMainBean.JsonData.begintime = "";
        mDanJuMainBean.JsonData.endtime = "";
        mDanJuMainBean.JsonData.checkflag = "0";//审核标志
        mDanJuMainBean.JsonData.pagenum = limit;
        mDanJuMainBean.JsonData.page = page;
        mDanJuList.getDanJuList(mDanJuMainBean);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDanJuList = new DanJuListImp(this);
        View v = inflater.inflate(R.layout.caogao_fragment, null);
        mListView = (RefreshListView) v.findViewById(R.id.list_view);

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                page ++;
                loadData();
            }
        });
        loadData();
        return v;
    }

    @Override
    public void handleResule(int flag, Object o) {
        try{
            List<DanJuMainBeanResultItem> list = new ArrayList();
            DanJuMainBeanResultItem test = new DanJuMainBeanResultItem();
            test.Sheet_No = "PI1234567";
            test.Oper_Date = "2017-3-4";
            test.Ord_Amt = "$23400";
            test.Oper_Name = "张三";
            test.Branch_No = "三楼仓库";
            list.add(test);
            mDanJuAdapter = new CaoGaoListAdapter(list);
            mHandle.sendEmptyMessage(Config.MESSAGE_REFLASH);
        }catch (Exception e){
            Log.e("Exception",e.getMessage());
        }

//        DanJuMainBeanResult mDanJuMainBeanResult = (DanJuMainBeanResult) o;
//        mXiaoshouDanAdapter = new CaoGaoListAdapter(mDanJuMainBeanResult.jsonData);
//        mListView.setAdapter(mXiaoshouDanAdapter);
//        mXiaoshouDanAdapter.notifyDataSetChanged();
    }

    @Override
    protected void reflashList() {
        mListView.onRefreshComplete();
    }
}