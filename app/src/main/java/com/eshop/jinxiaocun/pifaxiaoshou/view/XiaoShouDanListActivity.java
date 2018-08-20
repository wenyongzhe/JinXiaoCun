package com.eshop.jinxiaocun.pifaxiaoshou.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseListActivity;
import com.eshop.jinxiaocun.widget.RefreshListView;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;

import java.util.List;

public class XiaoShouDanListActivity extends BaseListActivity implements INetWorResult{

    private XiaoshouDanListAdapter mXiaoshouDanAdapter;
    private List<DanJuMainBeanResult> listInfo;
    private IDanJuList mDanJuList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDanJuList = new DanJuListImp(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(getView(R.layout.activity_xiaoshou_dan),0,params);
        mListView = mLinearLayout.findViewById(R.id.list_view);
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
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        setHeaderTitle(R.id.tv_0, R.string.list_item_Status, 100);
        setHeaderTitle(R.id.tv_1, R.string.list_item_FormIndex, 200);
        setHeaderTitle(R.id.tv_2, R.string.list_item_AllCount, 100);
        setHeaderTitle(R.id.tv_3, R.string.list_item_AllAmount, 100);
        setHeaderTitle(R.id.tv_4, R.string.list_item_SupName, 150);
        setHeaderTitle(R.id.tv_5, R.string.list_item_DHRiQi, 150);
        setHeaderTitle(R.id.tv_6, R.string.list_item_OrderDate, 150);
        setHeaderTitle(R.id.tv_7, R.string.list_item_HTNAME, 150);
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
    public void handleResule(int flag, Object o) {
        DanJuMainBeanResult mDanJuMainBeanResult = (DanJuMainBeanResult) o;
        mXiaoshouDanAdapter = new XiaoshouDanListAdapter(mDanJuMainBeanResult.JsonData);
        mListView.setAdapter(mXiaoshouDanAdapter);
        mXiaoshouDanAdapter.notifyDataSetChanged();
    }
}
