package com.eshop.jinxiaocun.pifaxiaoshou.view;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/9/13 0013
 * 描述
 */

public class PifaXiaoshouListActivity extends CommonBaseListActivity implements INetWorResult{

    private List<DanJuMainBeanResultItem> mListInfo;
    private IDanJuList mDanJuList;

    private int mPageIndex =1;
    private int mPageSize =20;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pifa_xiaoshou_list;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("批发销售订单列表",R.mipmap.ic_left_light,"",0,"");

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex = 1;
                getPifaXiaoshouData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageIndex ++;
                getPifaXiaoshouData();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mDanJuList = new DanJuListImp(this);
    }

    private void getPifaXiaoshouData(){

        DanJuMainBean mDanJuMainBean = new DanJuMainBean();
        mDanJuMainBean.JsonData.POSID = "";
        mDanJuMainBean.JsonData.UserId = "";
        mDanJuMainBean.JsonData.SheetType = "";//单据类型
        mDanJuMainBean.JsonData.Oper_ID = "";//操作员ID
        mDanJuMainBean.JsonData.BeginTime = "";
        mDanJuMainBean.JsonData.EndTime = "";
        mDanJuMainBean.JsonData.CheckFlag = "";//审核标志
        mDanJuMainBean.JsonData.PageNum = mPageSize;
        mDanJuMainBean.JsonData.Page = mPageIndex;
        mDanJuList.getDanJuList(mDanJuMainBean);
    }

    @Override
    protected boolean createOrderBefore() {
        return false;
    }

    @Override
    protected void createOrderAfter() {

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

    @Override
    public void handleResule(int flag, Object o) {

    }

}
