package com.eshop.jinxiaocun.stock.view;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.stock.adapter.StockCheckAdapter;
import com.eshop.jinxiaocun.stock.bean.StockCheckBean;
import com.eshop.jinxiaocun.stock.bean.StockCheckBeanResult;
import com.eshop.jinxiaocun.stock.presenter.IStock;
import com.eshop.jinxiaocun.stock.presenter.IStockImp;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StockCheckActivity extends CommonBaseScanActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;

    private IStock mServerApi;
    private StockCheckAdapter mAdapter;

    private List<StockCheckBeanResult> mListData = new ArrayList<>();

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_stock_check;
    }

    @Override
    protected void initView() {
        super.initView();

        mLayoutScanBottom.setVisibility(View.GONE);
        mEtBarcode.setOnKeyListener(onKey);
        setTopToolBar("库存查询",R.mipmap.ic_left_light,"",0,"");

        setHeaderTitle(R.id.tv_0,R.string.list_item_ProdName,150);//商品名称
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdCode,150);//商品编码
        setHeaderTitle(R.id.tv_2,R.string.list_item_Shopinfo,150);//门店信息
        setHeaderTitle(R.id.tv_3,R.string.list_item_Spec,100);//规格
        setHeaderTitle(R.id.tv_4,R.string.list_item_Unit,80);//单位
        setHeaderTitle(R.id.tv_5,R.string.item_kucun,100);//库存
        setHeaderTitle(R.id.tv_6,R.string.list_item_Pihao,150);//批号
        setHeaderTitle(R.id.tv_7,R.string.list_item_BeginDate,100);//起始时间
        setHeaderTitle(R.id.tv_8,R.string.list_item_EndDate,100);//结束时间


        mAdapter = new StockCheckAdapter(this,mListData);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    //手动输入条码事件
    View.OnKeyListener onKey= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_UP){
                scanResultData(mEtBarcode.getText().toString().trim());
                return true;
            }
            return false;
        }
    };

    @Override
    protected void initData() {
        super.initData();
        mServerApi = new IStockImp(this);
        getStockCheckData("000008");
    }

    //库存查询
    private void getStockCheckData(String barCode){

        //userIDt先填posid  userid根据注册返回取值
        StockCheckBean bean = new StockCheckBean();
        bean.JsonData.UserId = Config.posid;//操作员ID
        bean.JsonData.BranchNo =Config.branch_no;//机构号
        bean.JsonData.BarCode = barCode;//编码
        mServerApi.seachGoodsStockData(bean);

    }

    @Override
    protected boolean scanBefore() {
        return true;
    }

    @Override
    protected void scanResultData(String barcode) {
        if(TextUtils.isEmpty(barcode)){
            AlertUtil.showToast("条码不能为空！",Application.mContext);
            return;
        }
        getStockCheckData(barcode);
    }

    @Override
    protected boolean addBefore() {
        return false;
    }

    @Override
    protected void addAfter() {

    }

    @Override
    protected boolean deleteBefore() {
        return false;
    }

    @Override
    protected void deleteAfter() {

    }

    @Override
    protected boolean modifyCountBefore() {
        return false;
    }

    @Override
    protected void modifyCountAfter() {

    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
                mAdapter.setListInfo((List<StockCheckBeanResult>) o);
                break;
            case Config.MESSAGE_ERROR:
                mListData.clear();
                mAdapter.setListInfo(mListData);
                AlertUtil.showToast(o.toString(), Application.mContext);
                break;
        }
    }
}
