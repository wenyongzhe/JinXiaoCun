package com.eshop.jinxiaocun.xiaoshou.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseListFragment;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.RefreshListView;
import com.eshop.jinxiaocun.xiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.xiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.xiaoshou.bean.DanJuMainBeanResultJson;
import com.eshop.jinxiaocun.xiaoshou.presenter.DanJuListImp;

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
        mDanJuMainBean.JsonData.BeginTime = "";
        mDanJuMainBean.JsonData.EndTime = "";
        mDanJuMainBean.JsonData.CheckFlag = "";//审核标志
        mDanJuMainBean.JsonData.PageNum = limit+"";
        mDanJuMainBean.JsonData.Page = page+"";
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
            List<DanJuMainBeanResultJson> list = new ArrayList();
            DanJuMainBeanResultJson test = new DanJuMainBeanResultJson();
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
//        mXiaoshouDanAdapter = new CaoGaoListAdapter(mDanJuMainBeanResult.JsonData);
//        mListView.setAdapter(mXiaoshouDanAdapter);
//        mXiaoshouDanAdapter.notifyDataSetChanged();
    }

    @Override
    protected void reflashList() {
        mListView.onRefreshComplete();
    }
}