package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.piandian.adapter.PandianPihaoListAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoHuoquBean;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoHuoquBeanResult;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PandianPihaoListActivity extends CommonBaseListActivity implements INetWorResult{

    /** 页数 */
    protected int pageIndex = 1;
    /** 每次请求的条数 */
    protected int pageSize = 20;
    private PandianPihaoListAdapter mAdapter;
    private IPandian mServerApi;
    private List<PandianPihaoHuoquBeanResult> mListDatas = new ArrayList<>();

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pandian_pihao_list;
    }

    @Override
    protected void initView() {
        super.initView();
        mLayoutBottom.setVisibility(View.GONE);
        setTopToolBar("盘点批号列表",R.mipmap.ic_left_light,"",R.mipmap.add,"");
        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex = 1;
                getPandianPihaoHuoqu();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex++;
                getPandianPihaoHuoqu();
            }
        });

        setHeaderTitle(R.id.tv_0,R.string.list_item_Status,100);
        setHeaderTitle(R.id.tv_1,R.string.list_item_PandianPihao,200);
        setHeaderTitle(R.id.tv_2,R.string.list_item_Fanwei,150);
        setHeaderTitle(R.id.tv_3,R.string.list_item_Type,150);
        setHeaderTitle(R.id.tv_4,R.string.list_item_Cangku,150);
        setHeaderTitle(R.id.tv_5,R.string.list_item_Beizhu,150);

        mAdapter = new PandianPihaoListAdapter(this,mListDatas);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        mServerApi = new PandianImp(this);
        getPandianPihaoHuoqu();
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

    //盘点批号获取
    private void getPandianPihaoHuoqu(){
        PandianPihaoHuoquBean bean = new PandianPihaoHuoquBean();
        bean.JsonData.sheet_no="%";//获取所有可填%
        bean.JsonData.trans_no="PD";//单据标识
        bean.JsonData.PerNum=pageSize;//每页显示数量
        bean.JsonData.PageNum=pageIndex;//页码
        bean.JsonData.approveflag="0"; //审核标识  只取已审核的数据 (1代表已审核,0代表未审核)
        bean.JsonData.branch_no="0001"; //机构号(保留)
        mServerApi.getPandianPihaoHuoqu(bean);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        Intent intent = new Intent();
        intent.putExtra("PandianPihao",mListDatas.get(position-1));
        setResult(11,intent);
        finish();
    }

    @Override
    public void handleResule(int flag, Object o) {

        mListView.onRefreshComplete();

        switch (flag){
            //盘点批号获取
            case Config.MESSAGE_PANDIANPIHAOHUOQU_OK:
                if(pageIndex==1){
                    mListDatas = (List<PandianPihaoHuoquBeanResult>) o;
                }else{
                    mListDatas.addAll((List<PandianPihaoHuoquBeanResult>) o);
                }
                mAdapter.setListInfo(mListDatas);
                mAdapter.notifyDataSetChanged();
                break;
            case Config.MESSAGE_PANDIANPIHAOHUOQU_ERROR:
                Toast.makeText(PandianPihaoListActivity.this,"获取盘点批号数据出错:"+o.toString(),Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onTopBarRightClick() {
        super.onTopBarRightClick();
        startActivityForResult(new Intent(PandianPihaoListActivity.this,PandianPihaoCreateActivity.class),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode ==11){
            pageIndex = 1;
            getPandianPihaoHuoqu();
        }
    }
}
