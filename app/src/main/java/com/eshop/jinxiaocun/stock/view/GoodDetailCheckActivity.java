package com.eshop.jinxiaocun.stock.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.stock.adapter.GoodCheckAdapter;
import com.eshop.jinxiaocun.stock.bean.StockCheckBean;
import com.eshop.jinxiaocun.stock.bean.StockCheckBeanResult;
import com.eshop.jinxiaocun.stock.presenter.IStock;
import com.eshop.jinxiaocun.stock.presenter.IStockImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodDetailCheckActivity extends CommonBaseScanActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;
    @BindView(value = R.id.sp_type)
    Spinner sp_type;
    @BindView(R.id.ib_seach)
    ImageButton ib_seach;

    @BindView(R.id.tv_good_name)
    TextView tv_good_name;
    @BindView(R.id.tv_good_code)
    TextView tv_good_code;
    @BindView(R.id.tv_good_subcode)
    TextView tv_good_subcode;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_unit)
    TextView tv_unit;
    @BindView(R.id.tv_product)
    TextView tv_product;
    @BindView(R.id.tv_main_provider)
    TextView tv_main_provider;
    @BindView(R.id.tv_buy_price)
    TextView tv_buy_price;
    @BindView(R.id.tv_sale_price)
    TextView tv_sale_price;
    @BindView(R.id.tv_vip_price1)
    TextView tv_vip_price1;
    @BindView(R.id.tv_wholesale1)
    TextView tv_pifajia1;
    @BindView(R.id.tv_kuchun)
    TextView tv_kuchun;

    private IStock mServerApi;
    private ILingshouScan mLingShouScanImp;
    private GoodCheckAdapter mAdapter;
    List<String> listType = new ArrayList<>();
    private List<StockCheckBeanResult> mListData = new ArrayList<>();

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_good_detail_check;
    }

    @Override
    protected void initView() {
        super.initView();
//        hasBackDialog = false;
        mLayoutScanBottom.setVisibility(View.GONE);
        mEtBarcode.setOnKeyListener(onKey);
        setTopToolBar("商品信息查询",R.mipmap.ic_left_light,"",0,"");

        listType.add("精确查询");
        listType.add("模糊查询");
        ArrayAdapter<String> adapterBCType = new ArrayAdapter<>(this, R.layout.my_simple_spinner_item, listType);
        adapterBCType.setDropDownViewResource(R.layout.my_drop_down_item);
        sp_type.setAdapter(adapterBCType);

        mAdapter = new GoodCheckAdapter(this,mListData);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        ib_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanResultData(mEtBarcode.getText().toString().trim());
                /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputMethodManager.isActive()){
                    inputMethodManager.hideSoftInputFromWindow(ib_seach.getApplicationWindowToken(), 0);
                }
            }
        });

    }

    //手动输入条码事件
    View.OnKeyListener onKey= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == EditorInfo.IME_ACTION_SEARCH
                    || keyCode == 0
                    || keyCode == EditorInfo.IME_ACTION_GO || keyCode == 6) { /*判断是否是“GO”键*/
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
        mLingShouScanImp = new LingShouScanImp(this);
    }

    //查询
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
        getGood(barcode);
        //getStockCheckData(barcode);
    }

    private void getGood(String barcode){
        if(sp_type.getSelectedItemPosition()==0){
            mLingShouScanImp.getPLUInfo(barcode);
        }else {
            mLingShouScanImp.getPLULikeInfo(barcode);
        }
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
        List<StockCheckBeanResult> mStockCheckBeanResultList = new ArrayList<>();
        List<GetClassPluResult> mGetClassPluResultList = new ArrayList<>();

        switch (flag){
            case Config.MESSAGE_OK:
                mStockCheckBeanResultList.add((StockCheckBeanResult)o);
                mAdapter.setListInfo(mStockCheckBeanResultList);
                break;
            case Config.MESSAGE_ERROR:
            case Config.MESSAGE_GOODS_INFOR_FAIL:
                AlertUtil.showToast(o.toString(), Application.mContext);
                break;
            case Config.MESSAGE_start_query://一条数据
                mGetClassPluResultList = (List<GetClassPluResult>)o;
                mGetClassPluResultList.addAll(mGetClassPluResultList);
                GetClassPluResult mGetClassPluResult = mGetClassPluResultList.get(0);
                tv_good_name.setText(mGetClassPluResult.getItem_name());
                tv_good_code.setText(mGetClassPluResult.getItem_no());
                tv_good_subcode.setText(mGetClassPluResult.getItem_subno());
                tv_type.setText(mGetClassPluResult.getItem_clsno());
                tv_unit.setText(mGetClassPluResult.getUnit_no());
                //tv_product.setText(mGetClassPluResult.getItemName());
                tv_main_provider.setText(mGetClassPluResult.getMain_supcust());
                tv_buy_price.setText(mGetClassPluResult.getPrice());
                tv_sale_price.setText(mGetClassPluResult.getSale_price());
                tv_vip_price1.setText(mGetClassPluResult.getVip_price());
                tv_pifajia1.setText(mGetClassPluResult.getBase_price());
                tv_kuchun.setText(mGetClassPluResult.getStock_qty());
                break;
            case Config.MESSAGE_GOODS_INFOR://多条数据
                break;
        }
    }
}
