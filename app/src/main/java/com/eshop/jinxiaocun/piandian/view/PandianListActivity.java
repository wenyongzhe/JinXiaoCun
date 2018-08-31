package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseListActivity;
import com.eshop.jinxiaocun.piandian.adapter.PandianListAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Lu An
 * 创建时间  2018/8/24
 * 描述
 */
public class PandianListActivity extends BaseListActivity implements INetWorResult,ActionBarClickListener {

    private PandianListAdapter mAdapter;
    private List<DanJuMainBeanResultItem> mListInfo;
    private IDanJuList mDanJuList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    @Override
    protected void loadData() {
        super.loadData();

        mDanJuList = new DanJuListImp(this);
        DanJuMainBean mDanJuMainBean = new DanJuMainBean();
        mDanJuMainBean.JsonData.BeginTime = "";
        mDanJuMainBean.JsonData.EndTime = "";
        mDanJuMainBean.JsonData.CheckFlag = "";//审核标志
        mDanJuMainBean.JsonData.PageNum = limit+"";
        mDanJuMainBean.JsonData.Page = page+"";
        mDanJuList.getDanJuList(mDanJuMainBean);

    }

    @Override
    protected void initView() {
        super.initView();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(getView(R.layout.activity_piandian_list),0,params);
        mListView = mLinearLayout.findViewById(R.id.listview_pandian);
        mMyActionBar.setData("盘点列表",R.mipmap.ic_left_light,"",R.mipmap.add,"",this);

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

        setHeaderTitle(R.id.tv_0,R.string.list_item_Status,150);
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdName,150);
        setHeaderTitle(R.id.tv_2,R.string.list_item_ProdCode,150);
        setHeaderTitle(R.id.tv_3,R.string.list_item_OrderDate,150);


        Button btnCreate = mLinearLayout.findViewById(R.id.bottom_btn_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PandianListActivity.this,PandianPihaoListActivity.class));
            }
        });

    }

    @Override
    public void handleResule(int flag, Object o) {
        DanJuMainBeanResult mDanJuMainBeanResult = (DanJuMainBeanResult) o;
        mListInfo = mDanJuMainBeanResult.JsonData;
        if(mListInfo == null)return;
//        mAdapter = new PandianListAdapter(mListInfo);
//        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
