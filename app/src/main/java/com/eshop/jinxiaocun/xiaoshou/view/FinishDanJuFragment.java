package com.eshop.jinxiaocun.xiaoshou.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseListFragment;
import com.eshop.jinxiaocun.widget.RefreshListView;
import com.eshop.jinxiaocun.xiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.xiaoshou.bean.DanJuMainBeanResult;

import java.util.List;

@SuppressLint("ValidFragment")
public class FinishDanJuFragment extends BaseListFragment implements INetWorResult {
    private List<DanJuMainBeanResult> mListData;
    private XiaoshouDanListAdapter mXiaoshouDanAdapter;

    public static FinishDanJuFragment getInstance() {
        FinishDanJuFragment sf = new FinishDanJuFragment();
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
        View v = inflater.inflate(R.layout.finish_fragment, null);
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
        return v;
    }

    @Override
    public void handleResule(int flag, Object o) {
        DanJuMainBeanResult mDanJuMainBeanResult = (DanJuMainBeanResult) o;
        mXiaoshouDanAdapter = new XiaoshouDanListAdapter(mDanJuMainBeanResult.JsonData);
        mListView.setAdapter(mXiaoshouDanAdapter);
        mXiaoshouDanAdapter.notifyDataSetChanged();
    }
}
