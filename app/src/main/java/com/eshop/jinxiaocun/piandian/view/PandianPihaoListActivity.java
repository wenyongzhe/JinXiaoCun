package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.BaseListActivity;
import com.eshop.jinxiaocun.piandian.adapter.PandianPihaoListAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoHuoquBean;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoHuoquBeanResult;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PandianPihaoListActivity extends BaseListActivity implements INetWorResult,ActionBarClickListener {

    private PandianPihaoListAdapter mAdapter;
    private IPandian mServerApi;

    private List<PandianPihaoHuoquBeanResult> mListDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServerApi = new PandianImp(this);
        initView();
        loadData();
    }


    protected void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(getView(R.layout.activity_pandian_pihao_list),0,params);
        mListView = mLinearLayout.findViewById(R.id.listview_pandianpihao);
        mMyActionBar.setData("盘点号列表",R.mipmap.ic_left_light,"",R.mipmap.add,"",this);

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

        setHeaderTitle(R.id.tv_0,R.string.list_item_Status,100);
        setHeaderTitle(R.id.tv_1,R.string.list_item_PandianPihao,200);
        setHeaderTitle(R.id.tv_2,R.string.list_item_Fanwei,150);
        setHeaderTitle(R.id.tv_3,R.string.list_item_Type,150);
        setHeaderTitle(R.id.tv_4,R.string.list_item_Cangku,150);
        setHeaderTitle(R.id.tv_5,R.string.list_item_Beizhu,150);

        limit = 20;//每页20条 数据
        mAdapter = new PandianPihaoListAdapter(this,mListDatas);
        mListView.setAdapter(mAdapter);

        Button btnCreate = mLinearLayout.findViewById(R.id.bottom_btn_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PandianPihaoListActivity.this,SelectPandianFanweiDialogActivity.class));
            }
        });
    }

    protected void loadData() {
        getPandianPihaoHuoqu();
    }

    //盘点批号获取
    private void getPandianPihaoHuoqu(){
        PandianPihaoHuoquBean bean = new PandianPihaoHuoquBean();
        bean.JsonData.sheet_no="%";//获取所有可填%
        bean.JsonData.trans_no="PD";//单据标识
        bean.JsonData.PerNum=limit;//每页显示数量
        bean.JsonData.PageNum=page;//页码
        bean.JsonData.approveflag="1"; //审核标识  只取已审核的数据 (1代表已审核)
        bean.JsonData.branch_no=Config.jigou_no; //机构号(保留)
        mServerApi.getPandianPihaoHuoqu(bean);

    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //盘点批号获取
            case Config.MESSAGE_PANDIANPIHAOHUOQU_OK:
                Toast.makeText(PandianPihaoListActivity.this,"获取盘点批号数据出错:"+o.toString(),Toast.LENGTH_SHORT).show();
                break;
            case Config.MESSAGE_PANDIANPIHAOHUOQU_ERROR:
                if(page==0){
                    mListDatas = (List<PandianPihaoHuoquBeanResult>) o;
                }
                mListDatas.addAll((Collection<? extends PandianPihaoHuoquBeanResult>) o);
                mAdapter.setListInfo(mListDatas);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
