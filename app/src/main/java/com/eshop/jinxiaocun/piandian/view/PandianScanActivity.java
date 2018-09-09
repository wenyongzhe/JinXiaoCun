package com.eshop.jinxiaocun.piandian.view;

import android.text.TextUtils;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.piandian.adapter.PandianScanAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBean;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/9
 * Desc:
 */

public class PandianScanActivity extends CommonBaseScanActivity implements INetWorResult{

    @BindView(R.id.listview)
    ListView mListView;


    private IPandian mServerApi;
    private String mSheetNo;//盘点批号
    private PandianScanAdapter mAdapter;
    private List<PandianDetailBeanResult> mListPandianDetailData = new ArrayList<>();

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pandian_scan;
    }

    @Override
    protected void scanResultData(String barcode) {

    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("盘点明细",R.mipmap.ic_left_light,"",0,"");

        setHeaderTitle(R.id.tv_0,R.string.list_item_ProdName,150);//商品名称
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdCode,150);//商品编码
        setHeaderTitle(R.id.tv_2,R.string.list_item_Spec,100);//规格
        setHeaderTitle(R.id.tv_3,R.string.list_item_Price,100);//价格
        setHeaderTitle(R.id.tv_4,R.string.list_item_XSPrice,100);//销售价格
        setHeaderTitle(R.id.tv_5,R.string.list_item_Unit,80);//单位
        setHeaderTitle(R.id.tv_6,R.string.list_item_StoreName,150);//仓库名称
        setHeaderTitle(R.id.tv_7,R.string.list_item_StoreNum,100);//库存数量
        setHeaderTitle(R.id.tv_8,R.string.list_item_CountN4,100);//盘点数量
        setHeaderTitle(R.id.tv_9,R.string.list_item_DiffCount,100);//差异数量

        mAdapter = new PandianScanAdapter(this,mListPandianDetailData);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        mSheetNo = getIntent().getStringExtra("sheet_no");
        mServerApi = new PandianImp(this);

        if(!TextUtils.isEmpty(mSheetNo)){
            getPandianDetailData(mSheetNo);
        }

    }

    //获取盘点明细
    private void getPandianDetailData(String sheet_no){
        PandianDetailBean bean = new PandianDetailBean();
        bean.JsonData.sheet_no=sheet_no;//盘点批号
        mServerApi.getPandianDetailData(bean);
    }


    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //获取盘点明细
            case Config.MESSAGE_OK:

                mListPandianDetailData = (List<PandianDetailBeanResult>) o;
                mAdapter.setListInfo(mListPandianDetailData);

                break;
            case Config.MESSAGE_ERROR:

                break;


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListPandianDetailData.clear();
    }
}
