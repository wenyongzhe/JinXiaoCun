package com.eshop.jinxiaocun.caigou.view;

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
import com.eshop.jinxiaocun.caigou.adapter.CaigouRucangScanAdapter;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.OrderDetailBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.OrderGoodsPriceBeanResult;
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
import com.eshop.jinxiaocun.piandian.view.SelectPandianGoodsListActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DrawableTextView;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;
import com.eshop.jinxiaocun.widget.ModifyPriceDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/28
 * Desc: 采购入仓扫描
 */

public class CaigouRucangScanActivity extends CommonBaseScanActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;
    @BindView(R.id.tv_provider)
    DrawableTextView mTvProvider;//供应商
    @BindView(R.id.tv_receiving_warehouse)
    TextView mTvReceivingWarehouse;//收货仓库

    private IOtherModel mOtherApi;
    private ILingshouScan mQueryGoodsApi;

    private ProviderInfoBeanResult mProviderInfo;//供应商信息
    private CaigouRucangScanAdapter mAdapter;

    private List<GetClassPluResult> mListDatas=new ArrayList<>();
    private GetClassPluResult mSelectGoodsEntity;
    private String mStr_OrderNo;//采购入仓单据号
    private String mCheckflag = "0";//0未审核，1审核
    private String SupCust_No ="";
    private DanJuMainBeanResultItem mSelectMainBean;
    private String mAddSelectGoodsNo;//添加的商品编码
    private ArrayList<GetClassPluResult> mOldListDatas=new ArrayList<>();//列表过来的原有商品

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_caigou_rucang_scan;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("采购入仓生成", R.mipmap.ic_left_light, "", 0, "");
        setTopToolBarRightTitleAndStyle("查找商品",R.drawable.border_bg);
        mEtBarcode.setOnKeyListener(onKey);
        mLayoutScanBottomZslZje.setVisibility(View.VISIBLE);
        mBtnModifyPrice.setVisibility(View.VISIBLE);
        mBtnAdd.setText(R.string.btnSave);

        mTvProvider.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                if(mCheckflag.equals("1")){
                    AlertUtil.showToast("该单据已审核，不能再操作!");
                    return;
                }
                Intent intent = new Intent(CaigouRucangScanActivity.this, SelectProviderListActivity.class);
                intent.putExtra("SheetType",Config.YwType.PI.toString());
                startActivityForResult(intent,2);
            }
        });

        mTvReceivingWarehouse.setText(Config.branch_no);

        setHeaderTitle(R.id.tv_0,R.string.list_item_XuHao,100);//序号
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdName,150);//商品名称
        setHeaderTitle(R.id.tv_2,R.string.list_item_ProdCode,150);//商品编码
        setHeaderTitle(R.id.tv_3,R.string.list_item_ZiCode,150);//自编码
        setHeaderTitle(R.id.tv_4,R.string.list_item_Amount,100);//金额
        setHeaderTitle(R.id.tv_5,R.string.list_item_Price,100);//价格
        setHeaderTitle(R.id.tv_6,R.string.list_item_CountN5,100);//数量

        mAdapter = new CaigouRucangScanAdapter(mListDatas);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();

        mOtherApi = new OtherModelImp(this);
        mQueryGoodsApi = new LingShouScanImp(this);
        mSelectMainBean = (DanJuMainBeanResultItem) getIntent().getSerializableExtra("MainBean");
        if(mSelectMainBean !=null){
            mStr_OrderNo = mSelectMainBean.getSheet_No();
            SupCust_No =mSelectMainBean.getSupCust_No();
            mTvProvider.setText(mSelectMainBean.getSupplyName());
            mTvReceivingWarehouse.setText(mSelectMainBean.getBranch_No());
            mCheckflag = getIntent().getStringExtra("Checkflag");
            mOtherApi.getOrderDetail(mSelectMainBean.getSheetType(),mSelectMainBean.getSheet_No(),mSelectMainBean.getVoucher_Type());
        }

    }

    //手动输入条码事件
    View.OnKeyListener onKey= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_UP){
                if(TextUtils.isEmpty(mTvProvider.getText().toString().trim())){
                    AlertUtil.showToast("请选择供应商，再添加商品!");
                    return false;
                }
                scanResultData(mEtBarcode.getText().toString().trim());
                return true;
            }
            return false;
        }
    };

    @Override
    protected boolean onTopBarLeftClick() {
        return onBackFinish();
    }

    @Override
    protected void onTopBarRightClick() {
        super.onTopBarRightClick();
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核，不能再添加商品!");
            return;
        }

        if(TextUtils.isEmpty(mTvProvider.getText().toString().trim())){
            AlertUtil.showToast("请选择供应商，再添加商品!");
            return ;
        }
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

    @OnClick(R.id.btn_print)
    public void onClickPront(){
        AlertUtil.showToast("好的，我去打印");
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
            if(!isSame){//不存在则添加 ，已经存则直接刷新
                mListDatas.add(scanOrSelectGoods);
                mAddSelectGoodsNo = scanOrSelectGoods.getItem_no();
                mOtherApi.getOrderGoodsPrice(Config.YwType.PI.toString(),"",scanOrSelectGoods.getItem_no(),SupCust_No);
                return;
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
                    MyUtils.convertToFloat(data.getSale_price(),0);
        }

        mTvZsl.setText(zsl+"");
        mTvZje.setText(String.format(Locale.CANADA, "%.2f",zje)+"元");
    }

    //上传记录头数据（即上传主表信息）
    private void uploadRecordHeadData(){
        UploadDanjuMainBean bean = new UploadDanjuMainBean();
        bean.JsonData.Sheet_No = mStr_OrderNo;//单据号
        bean.JsonData.SheetType = Config.YwType.PI.toString(); //单据类型
        bean.JsonData.Branch_No = Config.branch_no;//当前门店/仓库
        bean.JsonData.SupCust_No = SupCust_No;//供应商 客户 代码
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
            // 单据明细获取
            case Config.MESSAGE_OK:
                List<OrderDetailBeanResult> orderDetailDatas = (List<OrderDetailBeanResult>) o;
                for (OrderDetailBeanResult detailData : orderDetailDatas) {
                    GetClassPluResult obj = new GetClassPluResult();
                    obj.setItem_name(detailData.getName());
                    obj.setItem_no(detailData.getBarCode());
                    obj.setItem_barcode(detailData.getPluBatch());//批次
                    obj.setItem_subno(detailData.getSelfCode());//自编码
                    obj.setUnit_no(detailData.getUnit());
                    obj.setSale_qnty(detailData.getCheckNum()+"");
                    obj.setStock_qty(detailData.getStockNum()+"");
                    obj.setPrice(detailData.getBuyPrice()+"");//进价
                    obj.setSale_price(detailData.getSalePrice()+"");//销价
                    obj.setProduce_date(detailData.getMadeDate());
                    obj.setValid_date(detailData.getVaildDate());
                    obj.setEnable_batch(detailData.getEnable_batch());

                    boolean isSave = false;
                    for ( int i=0; i<mListDatas.size();i++) {
                        GetClassPluResult data = mListDatas.get(i);
                        if(detailData.getBarCode().equals(data.getItem_no())){
                            isSave = true;
                            int number = MyUtils.convertToInt(mListDatas.get(i).getSale_qnty(),0)+detailData.getCheckNum();
                            mListDatas.get(i).setSale_qnty(number+"");
                            break;
                        }
                    }

                    if(!isSave){//不存在则添加
                        mListDatas.add(obj);
                    }
                }

                for (OrderDetailBeanResult detailData : orderDetailDatas) {
                    GetClassPluResult obj = new GetClassPluResult();
                    obj.setItem_name(detailData.getName());
                    obj.setItem_no(detailData.getBarCode());
                    obj.setItem_barcode(detailData.getPluBatch());//批次
                    obj.setItem_subno(detailData.getSelfCode());//自编码
                    obj.setUnit_no(detailData.getUnit());
                    obj.setSale_qnty(detailData.getCheckNum()+"");
                    obj.setStock_qty(detailData.getStockNum()+"");
                    obj.setPrice(detailData.getBuyPrice()+"");//进价
                    obj.setSale_price(detailData.getSalePrice()+"");//销价
                    obj.setProduce_date(detailData.getMadeDate());
                    obj.setValid_date(detailData.getVaildDate());
                    obj.setEnable_batch(detailData.getEnable_batch());

                    boolean isSave = false;
                    for ( int i=0; i<mOldListDatas.size();i++) {
                        GetClassPluResult data = mOldListDatas.get(i);
                        if(detailData.getBarCode().equals(data.getItem_no())){
                            isSave = true;
                            int number = MyUtils.convertToInt(mOldListDatas.get(i).getSale_qnty(),0)+detailData.getCheckNum();
                            mOldListDatas.get(i).setSale_qnty(number+"");
                            break;
                        }
                    }

                    if(!isSave){//不存在则添加
                        mOldListDatas.add(obj);
                    }
                }

                mAdapter.setListInfo(mListDatas);
                upDateUI();
                break;
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
                //精准查询接口的  可能有多条数据
                List<GetClassPluResult> goodsData = (List<GetClassPluResult>) o;
                if(goodsData !=null && goodsData.size()>0){
                    if(goodsData.size()==1){
                        addGoodsData(goodsData.get(0));
                    }else{
                        //多条数据 弹出选择其中一条
                        Intent intent = new Intent(CaigouRucangScanActivity.this,SelectPandianGoodsListActivity.class);
                        intent.putExtra("GoodsInfoList", (Serializable) goodsData);
                        startActivityForResult(intent,44);
                    }
                }else{
                    AlertUtil.showToast("该条码没有对应的商品数据!");
                }
                break;
            //上传单据主表 成功
            case Config.MESSAGE_SUCCESS:
                uploadGoodDetailData();// 上传商品明细
                break;
            // 上传单据明细 成功
            case Config.MESSAGE_RESULT_SUCCESS:
                mOtherApi.sheetSave(Config.YwType.PI.toString(),mStr_OrderNo);//保存业务单据
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
            //单据商品取价
            case Config.MESSAGE_GET_PRICE_SUCCESS:
                OrderGoodsPriceBeanResult obj = (OrderGoodsPriceBeanResult) o;
                for (int i=0 ; i<mListDatas.size();i++) {
                    GetClassPluResult bean = mListDatas.get(i);
                    if(bean.getItem_no().equals(obj.getBarCode())){
                        mListDatas.get(i).setBase_price(obj.getPrice()+"");
                        mListDatas.get(i).setPrice(obj.getBuyPrice()+"");
                        mListDatas.get(i).setSale_price(obj.getSalePrice()+"");
                        mListDatas.get(i).setVip_price(obj.getVip_price()+"");
                        break;
                    }
                }
                mAddSelectGoodsNo = null;
                mAdapter.setListInfo(mListDatas);
                upDateUI();
                break;
            case Config.MESSAGE_GET_PRICE_FAIL:
                for (int i = 0; i < mListDatas.size(); i++) {
                    if(mListDatas.get(i).getItem_no().equals(mAddSelectGoodsNo)){
                        mListDatas.remove(i);
                        break;
                    }
                }
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
            mProviderInfo = (ProviderInfoBeanResult) data.getSerializableExtra("ProviderInfo");
            SupCust_No =mProviderInfo.getId();
            mTvProvider.setText(mProviderInfo.getName());
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

        //修改价格
        if(requestCode == 33 && resultCode == RESULT_OK){
            String price = data.getStringExtra("Price");
            for (int i = 0; i < mListDatas.size(); i++) {
                if(mListDatas.get(i).getItem_no().equals(mSelectGoodsEntity.getItem_no())){
                    mListDatas.get(i).setSale_price(price);
                    mAdapter.setListInfo(mListDatas);
                    upDateUI();
                    break;
                }
            }
        }

        //搜索返回多条数据 ，选择其中一条
        if(requestCode == 44 && resultCode == RESULT_OK){
            GetClassPluResult entity = (GetClassPluResult) data.getSerializableExtra("GoodsInfoEntity");
            addGoodsData(entity);
        }

    }

    @Override
    protected boolean scanBefore() {
        return true;
    }

    @Override
    protected void scanResultData(String barcode) {
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核，不能再添加商品!");
            return;
        }
        if(!TextUtils.isEmpty(barcode)){
            //精准查询接口的
            mQueryGoodsApi.getPLUInfo(barcode);
        }
    }

    //保存前
    @Override
    protected boolean addBefore() {
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核，不能再操作!");
            return false;
        }
        if(mListDatas.size()==0){
            AlertUtil.showToast("请添加商品，再保存!");
            return false;
        }
        if(TextUtils.isEmpty(mTvProvider.getText().toString().trim())){
            AlertUtil.showToast("请选择供应商，再保存!");
            return false;
        }

        if(TextUtils.isEmpty(mTvReceivingWarehouse.getText().toString().trim())){
            AlertUtil.showToast("请选择收货仓库，再保存!");
            return false;
        }

        return true;
    }

    @Override
    protected void addAfter() {
//        在上传保存前，获取采购入仓单据号
        if(TextUtils.isEmpty(mStr_OrderNo)){
            SheetNoBean bean = new SheetNoBean();
            bean.JsonData.trans_no = Config.YwType.PI.toString();
            bean.JsonData.branch_no=Config.branch_no;
            mOtherApi.getSheetNoData(bean);
        }else{
            uploadRecordHeadData();
        }
    }

    @Override
    protected boolean deleteBefore() {
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核，不能再操作!");
            return false;
        }
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
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核，不能再操作!");
            return false;
        }
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
    protected boolean modifyPriceBefore() {
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核，不能再操作!");
            return false;
        }
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
    protected void modifyPriceAfter() {
        Intent intent = new Intent();
        intent.putExtra("Price", mSelectGoodsEntity.getSale_price()+"");
        intent.setClass(this, ModifyPriceDialog.class);
        startActivityForResult(intent, 33);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if(!onBackFinish()){
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean onBackFinish(){
        if(mSelectMainBean !=null){//说明是列表那边过来的数据
            if(mOldListDatas.size()!=0){
                if(mOldListDatas.size()!=mListDatas.size()){
                    //数据有变动
                    showHint();
                }else{
                    //价格 和 数量 有改变都提示
                    if(!MyUtils.isListEqual(mOldListDatas,mListDatas)){
                        //数据有变动
                        showHint();
                    }else{
                        return false;
                    }
                }

            }else{
                return false;
            }
        }else{
            //新开单的
            if(mListDatas.size()>0){
                //数据有变动
                showHint();
            }else{
                return false;
            }
        }
        return true;
    }

    private void showHint(){
        AlertUtil.showAlert(this,
                R.string.dialog_title,R.string.change_msg_back,
                R.string.confirm,new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(addBefore()){
                            addAfter();
                        }
                        AlertUtil.dismissDialog();
                    } },
                R.string.cancel,new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertUtil.dismissDialog();
                    } }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListDatas.clear();
    }

}
