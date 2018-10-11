package com.eshop.jinxiaocun.peisong.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.caigou.adapter.CaigouOrderScanAdapter;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.ProviderInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBean;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.UploadDanjuDetailBean;
import com.eshop.jinxiaocun.othermodel.bean.UploadDanjuMainBean;
import com.eshop.jinxiaocun.othermodel.bean.WarehouseInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.othermodel.view.SelectProviderListActivity;
import com.eshop.jinxiaocun.othermodel.view.SelectWarehouseListActivity;
import com.eshop.jinxiaocun.peisong.adapter.PeisongRukuScanAdapter;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DrawableTextView;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/29
 * Desc: 配送入库扫描
 */

public class PeisongRukuScanActivity extends CommonBaseScanActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;
    @BindView(R.id.tv_tiaochu)
    DrawableTextView mTvTiaoChu;//调出
    @BindView(R.id.tv_tiaoru)
    TextView mTvTiaoRu;//调入

    private IOtherModel mOtherApi;
    private ILingshouScan mQueryGoodsApi;

    private WarehouseInfoBeanResult mStoreInfo;//门店、仓库、机构、分部
    private PeisongRukuScanAdapter mAdapter;

    private List<GetClassPluResult> mListDatas=new ArrayList<>();
    private GetClassPluResult mSelectGoodsEntity;
    private String mStr_OrderNo;//配送入库单据号

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_peisong_ruku_scan;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("配送入库生成", R.mipmap.ic_left_light, "", 0, "添加商品");
        mEtBarcode.setOnKeyListener(onKey);
        mLayoutScanBottomZslZje.setVisibility(View.VISIBLE);
        mBtnAdd.setText(R.string.btnSave);
        mTvTiaoRu.setText("["+Config.branch_no+"]");

        mTvTiaoChu.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                Intent intent = new Intent(PeisongRukuScanActivity.this, SelectWarehouseListActivity.class);
                intent.putExtra("SheetType",Config.YwType.MI.toString());
                intent.putExtra("ShowType",2);
                startActivityForResult(intent,2);
            }
        });

        setHeaderTitle(R.id.tv_0,R.string.list_item_ProdName,150);//商品名称
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdCode,150);//商品编码
        setHeaderTitle(R.id.tv_2,R.string.list_item_Amount,100);//金额
        setHeaderTitle(R.id.tv_3,R.string.list_item_Price,100);//价格
        setHeaderTitle(R.id.tv_4,R.string.list_item_CountN5,100);//数量

        mAdapter = new PeisongRukuScanAdapter(mListDatas);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();

        mOtherApi = new OtherModelImp(this);
        mQueryGoodsApi = new LingShouScanImp(this);

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
    protected void onTopBarRightClick() {
        super.onTopBarRightClick();
        Intent mIntent = new Intent(this, QreShanpingActivity.class);
        startActivityForResult(mIntent,1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        mSelectGoodsEntity = mListDatas.get(position);
        mAdapter.setItemClickPosition(position);
        mAdapter.notifyDataSetInvalidated();
    }

    private void addGoodsData(GetClassPluResult scanOrSelectGoods){
        if(scanOrSelectGoods !=null){

            boolean isSame = false;
            for (int i = 0; i < mListDatas.size(); i++) {
                if(mListDatas.get(i).getItem_no().equals(scanOrSelectGoods.getItem_no())){
                    //已经存在自动+1
                    int pdNumber = MyUtils.convertToInt(mListDatas.get(i).getSale_qnty(),1)+1;
                    mListDatas.get(i).setSale_qnty(pdNumber+"");
                    isSame = true;
                    break;
                }
            }
            if(!isSame){//已经存在直接刷新
                mListDatas.add(scanOrSelectGoods);
            }
            mAdapter.setListInfo(mListDatas);
            upDateUI();
        }
    }

    private void upDateUI(){
        int zsl = 0;
        float zje = 0f;
        for (GetClassPluResult data : mListDatas) {
            zsl += MyUtils.convertToInt(data.getSale_qnty(),0);
            zje += MyUtils.convertToFloat(data.getSale_qnty(),0)*
                    MyUtils.convertToFloat(data.getBase_price(),0);
        }

        mTvZsl.setText(zsl+"");
        mTvZje.setText(String.format(Locale.CANADA, "%.2f",zje)+"元");
    }

    //上传记录头数据（即上传主表信息）
    private void uploadRecordHeadData(){
        UploadDanjuMainBean bean = new UploadDanjuMainBean();
        bean.JsonData.Sheet_No = mStr_OrderNo;//单据号
        bean.JsonData.SheetType = Config.YwType.MI.toString(); //单据类型
        bean.JsonData.Branch_No = Config.branch_no;//当前门店/仓库
        bean.JsonData.T_Branch_No = mStoreInfo.getId();//对方门店/仓库
        bean.JsonData.SupCust_No = "";//供应商客户代码
        bean.JsonData.USER_ID = Config.UserId;//用户ID
        bean.JsonData.Oper_Date = DateUtility.getCurrentTime();//操作日期
        mOtherApi.uploadDanjuMainInfo(bean);
    }

    //上传商品明细数据
    private void uploadGoodDetailData(){
        List<UploadDanjuDetailBean.UploadDanjuDetail> jsonData = new ArrayList<>();

        for (int i=0;i<mListDatas.size();i++) {
            GetClassPluResult data = mListDatas.get(i);
            UploadDanjuDetailBean.UploadDanjuDetail obj = new UploadDanjuDetailBean.UploadDanjuDetail();
            obj.FLow_ID = ""+(i+1) ;//序号从1开始
            obj.POSID = Config.posid;
            obj.BillNo = mStr_OrderNo;//单据号
            obj.BarCode = data.getItem_no();//编码
            obj.Name = data.getItem_name();//名称
            obj.Unit = data.getUnit_no();//单位
            obj.CheckNum = MyUtils.convertToInt(data.getSale_qnty(),0);//数量
            obj.StockNum = MyUtils.convertToInt(data.getStock_qty(),0);//库存数量
            obj.BuyPrice = MyUtils.convertToFloat(data.getPrice(),0f);//进价
            obj.SalePrice = MyUtils.convertToFloat(data.getSale_price(),0f);//销价
            obj.sub_amt = obj.CheckNum*obj.SalePrice;//金额
            obj.MadeDate = data.getProduce_date();//生产日期
            obj.VaildDate = data.getValid_date();//有效日期
            obj.Enable_batch = data.getEnable_batch();
            obj.EndFlag = "y";
            jsonData.add(obj);
        }

        UploadDanjuDetailBean bean = new UploadDanjuDetailBean();
        bean.JsonData = jsonData;
        mOtherApi.uploadDanjuDetailInfo(bean);

    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //业务单据号
            case Config.MESSAGE_SHEETNO_OK:
                SheetNoBeanResult sheetNoBeanResult = (SheetNoBeanResult) o;
                mStr_OrderNo =sheetNoBeanResult.getSheetno();
                if(mStr_OrderNo !=null && !TextUtils.isEmpty(mStr_OrderNo) ){
                    addAfter();//去上传主表信息
                }
                break;
            case Config.MESSAGE_SHEETNO_ERROR:
                AlertUtil.showToast("获取业务单据号失败："+o.toString());
                break;
            //扫描时返回搜索的数据
            case Config.MESSAGE_GOODS_INFOR:
                mEtBarcode.requestFocus();
                mEtBarcode.setFocusable(true);
                mEtBarcode.setText("");
                List<GetClassPluResult> goodsData = (List<GetClassPluResult>) o;
                if(goodsData !=null && goodsData.size()>0){
                    //第一条数据 （精准查询接口的）
                    addGoodsData(goodsData.get(0));
                }
                break;
            //上传单据主表 成功
            case Config.MESSAGE_SUCCESS:
//                AlertUtil.showToast(o.toString());
                uploadGoodDetailData();// 上传商品明细
                break;
            // 上传单据明细 成功
            case Config.MESSAGE_RESULT_SUCCESS:
//                AlertUtil.showToast(o.toString());
                mOtherApi.sheetSave(Config.YwType.MI.toString(),mStr_OrderNo);//保存业务单据
                break;
            //保存业务单据 成功
            case Config.RESULT_SUCCESS:
                AlertUtil.showToast(o.toString());
                setResult(22);
                finish();
                break;
            //上传单据主表或上传单据明细 保存业务单据 失败
            case Config.MESSAGE_FAIL:
                AlertUtil.showToast(o.toString());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Config.RESULT_SELECT_GOODS){
            List<GetClassPluResult> selectGoodsList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
            if(selectGoodsList !=null && selectGoodsList.size()>0){
                addGoodsData(selectGoodsList.get(0));
            }
        }

        //选择的供应商
        if(requestCode == 2 && resultCode == 22){
            mStoreInfo = (WarehouseInfoBeanResult) data.getSerializableExtra("WarehouseInfo");
            mTvTiaoChu.setText("["+mStoreInfo.getId()+"]"+mStoreInfo.getName());
        }

        //修改数量
        if(requestCode == 22 && resultCode == RESULT_OK){
            String countN = data.getStringExtra("countN");
            for (int i = 0; i < mListDatas.size(); i++) {
                if(mListDatas.get(i).getItem_no().equals(mSelectGoodsEntity.getItem_no())){
                    mListDatas.get(i).setSale_qnty(countN);
                    mAdapter.setListInfo(mListDatas);
                    upDateUI();
                    break;
                }
            }
        }

    }

    @Override
    protected boolean scanBefore() {
        return true;
    }

    @Override
    protected void scanResultData(String barcode) {
        if(!TextUtils.isEmpty(barcode)){
            //精准查询接口的
            mQueryGoodsApi.getPLUInfo(barcode);
        }
    }

    //保存前
    @Override
    protected boolean addBefore() {
        if(mListDatas.size()==0){
            AlertUtil.showToast("请添加商品，再保存!");
            return false;
        }
        if(TextUtils.isEmpty(mTvTiaoChu.getText().toString().trim())){
            AlertUtil.showToast("请选择调出，再保存!");
            return false;
        }

        return true;
    }

    @Override
    protected void addAfter() {
//        在上传保存前，获取单据号
        if(TextUtils.isEmpty(mStr_OrderNo)){
            SheetNoBean bean = new SheetNoBean();
            bean.JsonData.trans_no = Config.YwType.MI.toString();
            bean.JsonData.branch_no=Config.branch_no;
            mOtherApi.getSheetNoData(bean);
        }else{
            uploadRecordHeadData();
        }
    }

    @Override
    protected boolean deleteBefore() {
        if(mListDatas ==null || mListDatas.size()==0){
            AlertUtil.showToast("没有商品，不能做删除操作!");
            return false;
        }
        if(mSelectGoodsEntity ==null){
            AlertUtil.showToast("请选择要删除的商品!");
            return false;
        }
        return true;
    }

    @Override
    protected void deleteAfter() {
        for (int i = 0; i < mListDatas.size(); i++) {
            if(mListDatas.get(i).getItem_no().equals(mSelectGoodsEntity.getItem_no())){
                mSelectGoodsEntity = null;
                mListDatas.remove(i);
                mAdapter.setItemClickPosition(-1);
                mAdapter.setListInfo(mListDatas);
                upDateUI();
                break;
            }
        }
    }

    @Override
    protected boolean modifyCountBefore() {
        if(mListDatas ==null || mListDatas.size()==0){
            AlertUtil.showToast("没有商品，不能做改数操作!");
            return false;
        }
        if(mSelectGoodsEntity ==null){
            AlertUtil.showToast("请选择要改数的商品!");
            return false;
        }
        return true;
    }

    @Override
    protected void modifyCountAfter() {
        Intent intent = new Intent();
        intent.putExtra("countN", mSelectGoodsEntity.getSale_qnty()+"");
        intent.setClass(this, ModifyCountDialog.class);
        startActivityForResult(intent, 22);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListDatas.clear();
    }

}
