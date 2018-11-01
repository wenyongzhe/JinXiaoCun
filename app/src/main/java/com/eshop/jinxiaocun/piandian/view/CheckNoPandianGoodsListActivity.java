package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.base.view.CommonBaseListActivity;
import com.eshop.jinxiaocun.piandian.adapter.CheckNoPandianGoodsListAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/11/1
 * Desc: 查看未盘点的商品  可以进行盘点
 */

public class CheckNoPandianGoodsListActivity extends CommonBaseActivity {


    @BindView(R.id.rfListview)
    protected RefreshListView mListView;


    private List<PandianDetailBeanResult> mAddPandianGoodsDetailData = new ArrayList<>();
    private List<PandianDetailBeanResult> mPandianDetailData = new ArrayList<>();
    private List<PandianDetailBeanResult> mListData = new ArrayList<>();
    private CheckNoPandianGoodsListAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_nopandian_goods;
    }

    @Override
    protected void initView() {
        super.initView();

        mPandianDetailData = (List<PandianDetailBeanResult>) getIntent().getSerializableExtra("AllDetailListData");
        mAddPandianGoodsDetailData = (List<PandianDetailBeanResult>) getIntent().getSerializableExtra("AddDetailListData");
        int count = mPandianDetailData.size()-mAddPandianGoodsDetailData.size();
        setTopToolBar("未盘点商品"+count+"种", R.mipmap.ic_left_light,"",0,"");
        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.onRefreshComplete();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.onRefreshComplete();
            }
        });

        for(int i=0;i<5;i++){
            PandianDetailBeanResult obj = new PandianDetailBeanResult();
            obj.setItem_name("安_"+i);
            mListData.add(obj);
        }

        mAdapter = new CheckNoPandianGoodsListAdapter(this,mListData);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mListView.setAdapter(mAdapter);
        mAdapter.setCallback(new CheckNoPandianGoodsListAdapter.CallbackInterface() {
            @Override
            public void onClickAddPandian(int position) {
                Log.e("lu","position_"+position);
                Intent intent =new Intent();
                intent.putExtra("SelectAddPandianDatas",mListData.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPandianDetailData.clear();
        mAddPandianGoodsDetailData.clear();
        mListData.clear();
    }
}
