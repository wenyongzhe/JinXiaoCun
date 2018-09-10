package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.lingshou.presenter.IQueryGoods;
import com.eshop.jinxiaocun.lingshou.presenter.QueryGoodsImp;
import com.eshop.jinxiaocun.piandian.adapter.PandianScanAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBean;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/9
 * Desc:
 */

public class PandianScanActivity extends CommonBaseScanActivity implements INetWorResult{

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;

    @BindView(R.id.listview)
    ListView mListView;

    private IPandian mServerApi;
    private IQueryGoods mQueryGoods;
    private String mSheetNo;//盘点批号
    private PandianScanAdapter mAdapter;
    private List<PandianDetailBeanResult> mListPandianDetailData = new ArrayList<>();

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pandian_scan;
    }

    @Override
    protected void scanResultData(String barcode) {
        if(!TextUtils.isEmpty(barcode)){
            //模糊查询 取第一条数据 （到时候要换成精准查询接口的）
            mQueryGoods.getPLULikeInfo(barcode,0);
        }
    }

    @Override
    protected void initView() {
        super.initView();

        CommonUtility.getInstance().closeKeyboard(this,mEtBarcode);
        mEtBarcode.setOnKeyListener(onKey);
        setTopToolBar("盘点明细",R.mipmap.ic_left_light,"",R.mipmap.add,"");

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
        mSheetNo = getIntent().getStringExtra("sheet_no");
        mServerApi = new PandianImp(this);
        mQueryGoods = new QueryGoodsImp(this);
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

    //选择商品时  添加到列表中去
    private void addGoodsData(List<GetClassPluResult> selectGoodsList){
        if(selectGoodsList !=null && selectGoodsList.size()>0){
            for (GetClassPluResult goods : selectGoodsList) {
                if(mListPandianDetailData.size()>0){
                    boolean isSame = false;
                    for (int i = 0; i < mListPandianDetailData.size(); i++) {
                        if(mListPandianDetailData.get(i).getItem_no().equals(goods.getItem_no())){
                            isSame = true;
                            int number = mListPandianDetailData.get(i).getCheck_qty()+1;
                            mListPandianDetailData.get(i).setCheck_qty(number);
                            break;
                        }
                    }

                    if(!isSame){
                        PandianDetailBeanResult obj = new PandianDetailBeanResult();
                        obj.setItem_name(goods.getItem_name());
                        obj.setItem_no(goods.getItem_no());
                        obj.setBranch_no("");
                        obj.setItem_size(goods.getItem_size());
                        obj.setUnit_no(goods.getUnit_no());
                        obj.setIn_price(MyUtils.convertToFloat(goods.getPrice(),0f));
                        obj.setSale_price(MyUtils.convertToFloat(goods.getSale_price(),0f));
                        obj.setStock_qty(MyUtils.convertToInt(goods.getStock_qty(),0));
                        obj.setCheck_qty(1);
                        obj.setBalance_qty(0);
                        mListPandianDetailData.add(obj);
                    }

                }else{
                    PandianDetailBeanResult obj = new PandianDetailBeanResult();
                    obj.setItem_name(goods.getItem_name());
                    obj.setItem_no(goods.getItem_no());
                    obj.setBranch_no("");
                    obj.setItem_size(goods.getItem_size());
                    obj.setUnit_no(goods.getUnit_no());
                    obj.setIn_price(MyUtils.convertToFloat(goods.getPrice(),0f));
                    obj.setSale_price(MyUtils.convertToFloat(goods.getSale_price(),0f));
                    obj.setStock_qty(MyUtils.convertToInt(goods.getStock_qty(),0));
                    obj.setCheck_qty(1);
                    obj.setBalance_qty(0);
                    mListPandianDetailData.add(obj);
                }
            }
            mAdapter.setListInfo(mListPandianDetailData);
        }
    }

    @Override
    protected void onTopBarRightClick() {
        super.onTopBarRightClick();
        Intent mIntent = new Intent(this, QreShanpingActivity.class);
        startActivityForResult(mIntent,1);
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
            case Config.MESSAGE_GETCLASSPLUINFO:
                mEtBarcode.requestFocus();
                mEtBarcode.setFocusable(true);
                mEtBarcode.setText("");
                List<GetClassPluResult> goodsData = (List<GetClassPluResult>) o;
                if(goodsData !=null && goodsData.size()>0){
                    //模糊查询 取第一条数据 （到时候要换成精准查询接口的）
                    List<GetClassPluResult> goodsDataList = new ArrayList<>();
                    goodsDataList.add(goodsData.get(0));
                    addGoodsData(goodsDataList);
                }
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Config.RESULT_SELECT_GOODS){
            List<GetClassPluResult> selectGoodsList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
            addGoodsData(selectGoodsList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListPandianDetailData.clear();
    }
}
