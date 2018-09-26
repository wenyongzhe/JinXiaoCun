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
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.piandian.adapter.PandianListAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Lu An
 * 创建时间  2018/8/24
 * 描述
 */
public class PandianListActivity extends CommonBaseListActivity implements INetWorResult {

    private PandianListAdapter mAdapter;
    private List<DanJuMainBeanResultItem> mListInfo = new ArrayList<>();
    private IDanJuList mServerApi;

    private int mPageIndex = 1;
    private int mPageSize =20;

    @Override
    protected void initData() {
        super.initData();
        mServerApi = new DanJuListImp(this);
        getPandianListData();
    }

    private void getPandianListData(){
        DanJuMainBean mDanJuMainBean = new DanJuMainBean();
        mDanJuMainBean.JsonData.pos_id = Config.posid;
        mDanJuMainBean.JsonData.branchNo = Config.branch_no;
        mDanJuMainBean.JsonData.sheettype = Config.YwType.PD.toString();//单据类型
        mDanJuMainBean.JsonData.operid = Config.UserId;//操作员ID
        mDanJuMainBean.JsonData.begintime = "";
        mDanJuMainBean.JsonData.endtime = "";
        mDanJuMainBean.JsonData.checkflag = "0";//审核标志
        mDanJuMainBean.JsonData.pagenum = mPageSize;
        mDanJuMainBean.JsonData.page = mPageIndex;
        mServerApi.getDanJuList(mDanJuMainBean);
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("盘点列表",R.mipmap.ic_left_light,"",0,"");

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getPandianListData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex ++;
                getPandianListData();
            }
        });

        setHeaderTitle(R.id.tv_0,R.string.list_item_Status,150);
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdName,150);
        setHeaderTitle(R.id.tv_2,R.string.list_item_ProdCode,150);
        setHeaderTitle(R.id.tv_3,R.string.list_item_OrderDate,150);

        mAdapter = new PandianListAdapter(mListInfo);
        mListView.setAdapter(mAdapter);

    }

    @Override
    public void handleResule(int flag, Object o) {
//        mListView.onRefreshComplete();
//        switch (flag) {
//            case Config.MESSAGE_OK:
//                if(mPageIndex==1){
//                    mListInfo = (List<DanJuMainBeanResultItem>)o;
//                }else{
//                    mListInfo.addAll((List<DanJuMainBeanResultItem>)o);
//                }
//                mAdapter.setListInfo(mListInfo);
//                break;
//            case Config.MESSAGE_ERROR:
//                AlertUtil.showToast(o.toString());
//                break;
//        }
    }


    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_piandian_list;
    }

    @Override
    protected boolean createOrderBefore() {
        return true;
    }

    @Override
    protected void createOrderAfter() {
        startActivity(new Intent(PandianListActivity.this,PandianCreateActivity.class));
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
}
