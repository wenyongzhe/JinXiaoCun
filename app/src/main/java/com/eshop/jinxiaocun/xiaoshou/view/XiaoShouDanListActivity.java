package com.eshop.jinxiaocun.xiaoshou.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseListActivity;
import com.eshop.jinxiaocun.xiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.xiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.xiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.xiaoshou.presenter.IDanJuList;

import java.util.List;

public class XiaoShouDanListActivity extends BaseListActivity implements INetWorResult{

    private ListView lvXiaoshoudan;
    private XiaoshouDanListAdapter mXiaoshouDanAdapter;
    private List<DanJuMainBeanResult> listInfo;
    private IDanJuList mDanJuList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDanJuList = new DanJuListImp(this);
        loadData();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(getView(R.layout.activity_xiaoshou_dan),0,params);

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
        mDanJuMainBean.JsonData.PageNum = "100";
        mDanJuMainBean.JsonData.Page = "1";
        mDanJuList.getDanJuList(mDanJuMainBean);
    }

    @Override
    public void handleResule(int flag, Object o) {
        DanJuMainBeanResult mDanJuMainBeanResult = (DanJuMainBeanResult) o;
        mXiaoshouDanAdapter = new XiaoshouDanListAdapter(mDanJuMainBeanResult.JsonData);
        lvXiaoshoudan.setAdapter(mXiaoshouDanAdapter);
        mXiaoshouDanAdapter.notifyDataSetChanged();
    }
}
