package com.eshop.jinxiaocun.pifaxiaoshou.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.db.BusinessBLL;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.CustomerInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.OrderDetailBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.OrderGoodsPriceBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBean;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.UploadDanjuDetailBean;
import com.eshop.jinxiaocun.othermodel.bean.UploadDanjuMainBean;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.othermodel.view.SelectCustomerListActivity;
import com.eshop.jinxiaocun.peisong.view.CiteOrderListActivity;
import com.eshop.jinxiaocun.piandian.view.SelectPandianGoodsListActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.adapter.PifaChukuScanAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DrawableTextView;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;
import com.eshop.jinxiaocun.widget.ModifyGoodsPriceDialog;
import com.eshop.jinxiaocun.widget.ModifyPriceDialog;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/27
 * Desc: 批发出库扫描
 */

public class PifaChukuScanActivity extends CommonBaseScanActivity implements INetWorResult {
    @BindView(R.id.et_barcode)
    EditText mEtBarcode;
    @BindView(R.id.tv_pf_user)
    DrawableTextView mTvUser;
    @BindView(R.id.tv_pf_store)
    TextView mTvUserStore;

    private IOtherModel mOtherApi;
    private ILingshouScan mLingshouApi;

    private CustomerInfoBeanResult mCustomerInfo;
    private PifaChukuScanAdapter mAdapter;

    private List<GetClassPluResult> mListDatas=new ArrayList<>();
    private GetClassPluResult mSelectGoodsEntity;
    private String mStr_OrderNo;//批发出库单据号
    private String mCheckflag = "0";//0未审核，1审核
    private String SupCust_No ="";
    private DanJuMainBeanResultItem mSelectMainBean;
    private GetClassPluResult mAddSelectGoods;//添加的商品
    private ArrayList<GetClassPluResult> mOldListDatas=new ArrayList<>();//列表过来的原有商品
    private String mSheetNo;//标记本地数据的单据号
    private GetDBDatas mGetDBDatas;
    private final String mSheetType = "本地_"+Config.YwType.SO.toString();
    private boolean isCiteOrder;//false不是引单  true为引单

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pifa_chuku_scan;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("批发出库生成", R.mipmap.ic_left_light, "", 0, "");
        setTopToolBarRightTitleAndStyle("查找商品",R.drawable.border_bg);
        mEtBarcode.setOnKeyListener(onKey);
        mLayoutScanBottomZslZje.setVisibility(View.VISIBLE);
        mLayoutAllRowNumber.setVisibility(View.VISIBLE);
        mBtnModifyPrice.setVisibility(View.VISIBLE);
        mBtnAdd.setText(R.string.btnSave);
        mTvUserStore.setText(""+Config.branch_no);

        mTvUser.setDrawableRightClick(new DrawableTextView.DrawableRightClickListener() {
            @Override
            public void onDrawableRightClickListener(View view) {
                if(mCheckflag.equals("1")){
                    AlertUtil.showToast("该单据已审核，不能再操作!");
                    return;
                }
                Intent intent = new Intent(PifaChukuScanActivity.this, SelectCustomerListActivity.class);
                startActivityForResult(intent,2);
            }
        });

        setHeaderTitle(R.id.tv_0,R.string.list_item_XuHao,100);//序号
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdName,150);//商品名称
        setHeaderTitle(R.id.tv_2,R.string.list_item_ProdCode,150);//商品编码
        setHeaderTitle(R.id.tv_3,R.string.list_item_ZiCode,150);//自编码
        setHeaderTitle(R.id.tv_4,R.string.list_item_Amount,100);//金额
        setHeaderTitle(R.id.tv_5,R.string.list_item_Price,100);//价格
        setHeaderTitle(R.id.tv_6,R.string.list_item_need_Qty,100);//数量
        setHeaderTitle(R.id.tv_7,R.string.list_item_order_Qty,100);//订单数量

        mAdapter = new PifaChukuScanAdapter(mListDatas);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
        mOtherApi = new OtherModelImp(this);
        mLingshouApi = new LingShouScanImp(this);
        mSelectMainBean = (DanJuMainBeanResultItem) getIntent().getSerializableExtra("MainBean");
        if(mSelectMainBean !=null){
            mCheckflag = getIntent().getStringExtra("Checkflag");
        }
        getOrderDetail(mSelectMainBean);
    }
    //从单据列表进入 取此单据明细
    private void getOrderDetail(DanJuMainBeanResultItem selectMainBean) {
        if (selectMainBean != null) {
            SupCust_No =selectMainBean.getSupCust_No();
            mTvUser.setText(TextUtils.isEmpty(selectMainBean.getSupplyName())?""+selectMainBean.getSupCust_No():selectMainBean.getSupplyName());
            mTvUserStore.setText(""+selectMainBean.getBranch_No());
            if(mSheetType.equals(selectMainBean.getSheetType())){
                mSheetNo=selectMainBean.getSheet_No();
                mGetDBDatas= new GetDBDatas(this);
                mGetDBDatas.execute();
            }else{
                mStr_OrderNo = mSelectMainBean.getSheet_No();
                mOtherApi.getOrderDetail(selectMainBean.getSheetType(),selectMainBean.getSheet_No(),selectMainBean.getVoucher_Type());
            }
        }else{
            String newOrderNo = BusinessBLL.getInstance().
                    getNewBillNO("PifaChuku",3,true);
            mSheetNo = mSheetType+newOrderNo;
        }
    }
    //手动输入条码事件
    View.OnKeyListener onKey= new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_UP){
                if(TextUtils.isEmpty(mTvUser.getText().toString().trim())){
                    AlertUtil.showToast("请选择客户，再添加商品!");
                    return false;
                }
                scanResultData(mEtBarcode.getText().toString().trim());
                return true;
            }
            return false;
        }
    };

    @OnClick(R.id.ib_seach)
    public void onClickSearch(){
        if(TextUtils.isEmpty(mTvUser.getText().toString().trim())){
            AlertUtil.showToast("请选择客户，再添加商品!");
            return ;
        }
        scanResultData(mEtBarcode.getText().toString().trim());
    }

    @OnClick(R.id.btn_citeOrder)
    public void onClickCiteOrder(){
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核，不能再添加商品!");
            return;
        }
        Intent intent = new Intent(PifaChukuScanActivity.this,CiteOrderListActivity.class);
        intent.putExtra("SheetType",Config.YwType.SO.toString());
        startActivityForResult(intent,4);
    }

    @Override
    protected boolean onTopBarLeftClick() {
        if(!onBackFinish()){
            if(mListDatas.size()==0){
                int isSuccessDelete = BusinessBLL.getInstance().deleteMainInfoAndGoodsInfo(mSheetNo);
                if(isSuccessDelete ==0){
                    AlertUtil.showToast("删除本地数据失败");
                }
            }
            setResult(22);
            finish();
        }
        return true;
    }

    @Override
    protected void onTopBarRightClick() {
        super.onTopBarRightClick();
        if(mCheckflag.equals("1")){
            AlertUtil.showToast("该单据已审核，不能再添加商品!");
            return;
        }
        if(TextUtils.isEmpty(mTvUser.getText().toString().trim())){
            AlertUtil.showToast("请选择客户，再添加商品!");
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
                    //如果是新开单或之前保存本地的单据 更新数量
                    if(mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())) {
                        int zsl = 0;
                        for (GetClassPluResult data : mListDatas) {
                            zsl += MyUtils.convertToInt(data.getSale_qnty(),0);
                        }
                        int isSuccess = BusinessBLL.getInstance().upDateGoodsQtyAndOrderQty(mSheetNo,
                                scanOrSelectGoods.getItem_no(),pdNumber+"",mListDatas.get(i).getOrder_qnty()
                                        +"",zsl+"");
                        if(isSuccess==0){
                            AlertUtil.showToast("本地商品更改数量失败！");
                        }
                    }
                    break;
                }
            }
            if(!isSame){//已经存在直接刷新
                mAddSelectGoods = scanOrSelectGoods;
                mOtherApi.getOrderGoodsPrice(Config.YwType.SO.toString(),"",scanOrSelectGoods.getItem_no(),SupCust_No);
                return;
            }
            if(mListDatas.size()>0){//默认选中最后一条
                mSelectGoodsEntity = mListDatas.get(mListDatas.size()-1);
                mAdapter.setItemClickPosition(mListDatas.size()-1);
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
        mTvAllRowNumber.setText(""+mListDatas.size());
        mTvZsl.setText(zsl+"");
        mTvZje.setText(String.format(Locale.CANADA, "%.2f",zje)+"元");
    }
    //保存临时主表信息
    private void saveMainInfo(String sheet_no){
        DanJuMainBeanResultItem mainInfo = new DanJuMainBeanResultItem();
        mainInfo.setSheet_No(sheet_no);//单据号
        mainInfo.setSheetType(mSheetType);//单据类型
        mainInfo.setBranch_No(Config.branch_no);//当前门店/仓库
        mainInfo.setSupCust_No(SupCust_No);//供应商/客户
        mainInfo.setSupplyName(mTvUser.getText().toString());
        mainInfo.setUSER_ID(Config.UserId);//用户ID
        mainInfo.setOper_Date(DateUtility.getCurrentDate());//操作日期
        //是否保存过 没有保存过则保存
        if(!BusinessBLL.getInstance().isSaveOrderMainInfo(sheet_no)){
            mainInfo.setSheet_No(sheet_no);//单据号
            if(!BusinessBLL.getInstance().insertOrderMianInfo(mainInfo)){
                AlertUtil.showToast("保存本地数据失败！");
            }
        }
    }
    //删除临时主表和商品保存本地的数据
    private void deleteMainInfoAndGoodsInfo(){
        //如果是新开单或之前保存本地的单据 删除
        if(mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())) {
            int isSuccessDelete = BusinessBLL.getInstance().deleteMainInfoAndGoodsInfo(mSheetNo);
            if(isSuccessDelete ==0){
                AlertUtil.showToast("删除本地数据失败");
            }
        }
    }
    //上传记录头数据（即上传主表信息）
    private void uploadRecordHeadData(){
        UploadDanjuMainBean bean = new UploadDanjuMainBean();
        bean.JsonData.Sheet_No = mStr_OrderNo;//单据号
        bean.JsonData.SheetType = Config.YwType.SO.toString(); //单据类型
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
            obj.SalePrice = MyUtils.convertToFloat(data.getBase_price(),0f);//销价 = 批发价
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
                    if(isCiteOrder){
                        obj.setSale_qnty("0");
                        obj.setOrder_qnty(detailData.getCheckNum());
                    }else{
                        obj.setSale_qnty(detailData.getCheckNum()+"");
                        obj.setOrder_qnty(0);
                    }
                    obj.setStock_qty(detailData.getStockNum()+"");
                    obj.setPrice(detailData.getSalePrice()+"");
                    obj.setSale_price(detailData.getSalePrice()+"");//销价
                    obj.setBase_price(detailData.getSalePrice()+"");//批发价
                    obj.setProduce_date(detailData.getMadeDate());
                    obj.setValid_date(detailData.getVaildDate());
                    obj.setEnable_batch(detailData.getEnable_batch());

                    boolean isSave = false;
                    for ( int i=0; i<mListDatas.size();i++) {
                        GetClassPluResult data = mListDatas.get(i);
                        if(detailData.getBarCode().equals(data.getItem_no())){
                            isSave = true;
                            int number = MyUtils.convertToInt(mListDatas.get(i).getSale_qnty(),0)
                                    + MyUtils.convertToInt(obj.getSale_qnty(),0);
                            int orderNumber = mListDatas.get(i).getOrder_qnty()+obj.getOrder_qnty();
                            mListDatas.get(i).setSale_qnty(number+"");
                            mListDatas.get(i).setOrder_qnty(orderNumber);
                            //如果是引单进入 新开单或之前保存本地的单据 商品保存本地
                            if(isCiteOrder && mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())){
                                int zsl = 0;
                                for (GetClassPluResult info : mListDatas) {
                                    zsl += MyUtils.convertToInt(info.getSale_qnty(),0);
                                }
                                int isSuccess = BusinessBLL.getInstance().upDateGoodsQtyAndOrderQty(mSheetNo,
                                        data.getItem_no(),number+"",orderNumber+"",zsl+"");
                                if(isSuccess==0){
                                    AlertUtil.showToast("本地商品更改数量失败！");
                                }
                            }
                            break;
                        }
                    }

                    if(!isSave){//不存在则添加
                        mListDatas.add(obj);
                        //如果是引单进入 新开单或之前保存本地的单据 商品保存本地
                        if(isCiteOrder && mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())){
                            int zsl = 0;
                            for (GetClassPluResult info : mListDatas) {
                                zsl += MyUtils.convertToInt(info.getSale_qnty(),0);
                            }
                            obj.setSheet_No(mSheetNo);
                            int isSuccess = BusinessBLL.getInstance().insertGoodsInfoAndUpdateOrderQty(obj,mSheetNo,zsl+"");
                            if(isSuccess == 0){
                                AlertUtil.showToast("商品信息保存本地失败!");
                            }
                        }
                    }

                    if(!isCiteOrder) {//不是引单时  从列表进入的网上取到的主表取其明细
                        GetClassPluResult old_obj = new GetClassPluResult();
                        old_obj.setItem_name(detailData.getName());
                        old_obj.setItem_no(detailData.getBarCode());
                        old_obj.setItem_barcode(detailData.getPluBatch());//批次
                        old_obj.setItem_subno(detailData.getSelfCode());//自编码
                        old_obj.setUnit_no(detailData.getUnit());
                        old_obj.setSale_qnty(detailData.getCheckNum()+"");
                        old_obj.setStock_qty(detailData.getStockNum()+"");
                        old_obj.setPrice(detailData.getBuyPrice()+"");//进价
                        old_obj.setSale_price(detailData.getSalePrice()+"");//销价
                        old_obj.setBase_price(detailData.getSalePrice()+"");//批发价
                        old_obj.setProduce_date(detailData.getMadeDate());
                        old_obj.setValid_date(detailData.getVaildDate());
                        old_obj.setEnable_batch(detailData.getEnable_batch());
                        boolean isOldSave = false;
                        for ( int i=0; i<mOldListDatas.size();i++) {
                            GetClassPluResult data = mOldListDatas.get(i);
                            if(detailData.getBarCode().equals(data.getItem_no())){
                                isOldSave = true;
                                int number = MyUtils.convertToInt(mOldListDatas.get(i).getSale_qnty(),0)+detailData.getCheckNum();
                                mOldListDatas.get(i).setSale_qnty(number+"");
                                break;
                            }
                        }
                        if(!isOldSave){//不存在则添加
                            mOldListDatas.add(old_obj);
                        }
                    }
                }

                if(mListDatas.size()>0){//默认选中和第一条
                    mSelectGoodsEntity = mListDatas.get(0);
                    mAdapter.setItemClickPosition(0);
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
            //上传单据主表 成功
            case Config.MESSAGE_SUCCESS:
                uploadGoodDetailData();// 上传批发商品明细
                break;
            // 上传单据明细 成功
            case Config.MESSAGE_RESULT_SUCCESS:
                mOtherApi.sheetSave(Config.YwType.SO.toString(),mStr_OrderNo);//保存业务单据
                break;
            //保存业务单据 成功
            case Config.RESULT_SUCCESS:
                AlertUtil.showToast(o.toString());
                deleteMainInfoAndGoodsInfo();
                setResult(22);
                finish();
                break;
            //单据明细 上传单据主表或上传单据明细 保存业务单据 单据明细获取 失败
            case Config.MESSAGE_FAIL:
                AlertUtil.showToast(o.toString());
                if(isCiteOrder)isCiteOrder=false;
                break;
            //单据商品取价
            case Config.MESSAGE_GET_PRICE_SUCCESS:
                OrderGoodsPriceBeanResult obj = (OrderGoodsPriceBeanResult) o;
                if(obj==null){
                    AlertUtil.showToast("商品"+mAddSelectGoods.getItem_no()+"取价失败，没有此商品价格!");
                    return;
                }
                mListDatas.add(mAddSelectGoods);
                GetClassPluResult addGoodsInfo = null;
                for (int i=0 ; i<mListDatas.size();i++) {
                    GetClassPluResult bean = mListDatas.get(i);
                    if(bean.getItem_no().equals(obj.getBarCode())){
                        mListDatas.get(i).setBase_price(obj.getPrice()+"");
                        mListDatas.get(i).setPrice(obj.getBuyPrice()+"");
                        mListDatas.get(i).setSale_price(obj.getSalePrice()+"");
                        mListDatas.get(i).setVip_price(obj.getVip_price()+"");
                        addGoodsInfo=mListDatas.get(i);
                        break;
                    }
                }
                if(mListDatas.size()>0){//默认选中最后一条
                    mSelectGoodsEntity = mListDatas.get(mListDatas.size()-1);
                    mAdapter.setItemClickPosition(mListDatas.size()-1);
                }
                //如果是新开单或之前保存本地的单据 更新数量
                if(mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())) {
                    int zsl = 0;
                    for (GetClassPluResult info : mListDatas) {
                        zsl += MyUtils.convertToInt(info.getSale_qnty(),0);
                    }
                    addGoodsInfo.setSheet_No(mSheetNo);
                    int isSuccess = BusinessBLL.getInstance().insertGoodsInfoAndUpdateOrderQty(addGoodsInfo,mSheetNo,zsl+"");
                    if(isSuccess == 0){
                        AlertUtil.showToast("商品信息保存本地失败!");
                    }
                }
                mAddSelectGoods = null;
                mAdapter.setListInfo(mListDatas);
                upDateUI();
                break;
            case Config.MESSAGE_GET_PRICE_FAIL:
                mAddSelectGoods = null;
                AlertUtil.showToast(o.toString());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == Config.RESULT_SELECT_GOODS){
            mEtBarcode.requestFocus();
            mEtBarcode.setFocusable(true);
            mEtBarcode.setText("");
            List<GetClassPluResult> selectGoodsList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
            if(selectGoodsList !=null && selectGoodsList.size()>0){
                GetClassPluResult goods= selectGoodsList.get(0);
                goods.setSale_qnty(TextUtils.isEmpty(goods.getSale_qnty())?"1":goods.getSale_qnty());
                if(isCiteOrder){
                    //如果是引单 判断商品是否在引单商品中 如果在则添加 反之不添加
                    boolean isSave = false;
                    for (GetClassPluResult info : mListDatas) {
                        if(info.getItem_no().equals(goods.getItem_no())){
                            isSave = true;
                            break;
                        }
                    }
                    if(isSave){
                        addGoodsData(goods);
                    }else{
                        AlertUtil.showToast("商品("+goods.getItem_name()+")不在引单商品中");
                    }
                }else{
                    addGoodsData(goods);
                }
            }
        }

        //选择的客户
        if(requestCode == 2 && resultCode == 22){
            mCustomerInfo = (CustomerInfoBeanResult) data.getSerializableExtra("CustomerInfo");
            SupCust_No =mCustomerInfo.getId();
            mTvUser.setText(mCustomerInfo.getName());
            //如果是新开单或之前保存本地的单据  添加商品时也选择供应商 所以这时创建一个临时主表信息
            if(mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())){
                saveMainInfo(mSheetNo);
            }
        }

        //修改数量
        if(requestCode == 22 && resultCode == RESULT_OK){
            String countN = data.getStringExtra("countN");
            for (int i = 0; i < mListDatas.size(); i++) {
                if(mListDatas.get(i).getItem_no().equals(mSelectGoodsEntity.getItem_no())){
                    mListDatas.get(i).setSale_qnty(countN);
                    mAdapter.setListInfo(mListDatas);
                    upDateUI();
                    //如果是新开单或之前保存本地的单据 更新数量
                    if(mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())) {
                        int zsl = 0;
                        for (GetClassPluResult info : mListDatas) {
                            zsl += MyUtils.convertToInt(info.getSale_qnty(),0);
                        }
                        int isSuccess = BusinessBLL.getInstance().upDateGoodsQtyAndOrderQty(mSheetNo,
                                mSelectGoodsEntity.getItem_no(),countN,mListDatas.get(i).getOrder_qnty()+"",zsl+"");
                        if(isSuccess==0){
                            AlertUtil.showToast("本地商品更改数量失败！");
                        }
                    }
                    break;
                }
            }
        }

        //引用单据
        if(requestCode == 4 && resultCode == RESULT_OK){
            if(mListDatas.size()>0){
                AlertUtil.showAlert(this, R.string.dialog_title,
                        "引单将会删除不在引单中的商品，确定引单？", R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertUtil.dismissDialog();
                                doCiteOrder(data);
                            }
                        }, R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertUtil.dismissDialog();
                            }
                        });
            }else{
                doCiteOrder(data);
            }
        }

        //修改价格
        if(requestCode ==200 && resultCode == RESULT_OK){
            String modifyPrice =  data.getStringExtra("Price");
            for (int i = 0; i < mListDatas.size(); i++) {
                if(mListDatas.get(i).getItem_no().equals(mSelectGoodsEntity.getItem_no())){
                    mListDatas.get(i).setBase_price(modifyPrice);
                    //如果是新开单或之前保存本地的单据 更新价格
                    if(mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())) {
                        int isSuccess = BusinessBLL.getInstance().updateGoodsInfoBasePrice(mSheetNo,modifyPrice,mSelectGoodsEntity.getItem_no());
                        if(isSuccess==1){
                            mListDatas.get(i).setHasModifyPrice(1);
                        }else{
                            AlertUtil.showToast("本地商品更改价格失败！");
                        }
                    }
                    mAdapter.setListInfo(mListDatas);
                    upDateUI();
                    break;
                }
            }
        }

    }

    //处理引单过来的业务
    private void doCiteOrder(Intent data) {

        int isSuccess = BusinessBLL.getInstance().deleteMainInfoAndGoodsInfo(mSheetNo);
        if (isSuccess == 0) {
            AlertUtil.showToast("删除本地数据失败");
            return;
        }
        mListDatas.clear();

        DanJuMainBeanResultItem selectMainBean = (DanJuMainBeanResultItem) data.getSerializableExtra("SelectOrder");
        SupCust_No =selectMainBean.getSupCust_No();
        mTvUser.setText(selectMainBean.getSupplyName());
        //如果是新开单或之前保存本地的单据  添加商品时也选择供应商 所以这时创建一个临时主表信息
        if(mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())){
            saveMainInfo(mSheetNo);
        }
        // 单据类型，入库方法需要自己填 入库方式，加库存的填“+”，减库存的填"-" 订单不产生库存变化的可以填空
        selectMainBean.setSheetType(Config.YwType.SO.toString());
        selectMainBean.setVoucher_Type("-");
        //引单  根据主表取引单明细
        isCiteOrder = true;
        mOtherApi.getOrderDetail(selectMainBean.getSheetType(),selectMainBean.getSheet_No(),selectMainBean.getVoucher_Type());
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
        Intent intent = new Intent(this, QreShanpingActivity.class);
        intent.putExtra("barcode",barcode);
        startActivityForResult(intent,1);

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
        if(TextUtils.isEmpty(mTvUser.getText().toString().trim())){
            AlertUtil.showToast("请选择客户，再保存!");
            return false;
        }
        return true;
    }

    @Override
    protected void addAfter() {
//        在上传保存前，获取批发订单单据号
        if(TextUtils.isEmpty(mStr_OrderNo)){
            SheetNoBean bean = new SheetNoBean();
            bean.JsonData.trans_no = Config.YwType.SO.toString();
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
        String itemNo = mSelectGoodsEntity.getItem_no();
        for (int i = 0; i < mListDatas.size(); i++) {
            if(mListDatas.get(i).getItem_no().equals(itemNo)){
                mSelectGoodsEntity = null;
                mListDatas.remove(i);
                mAdapter.setItemClickPosition(-1);
                mAdapter.setListInfo(mListDatas);
                upDateUI();
                break;
            }
        }
        //如果是新开单或之前保存本地的单据 删除商品并更新数量
        if(mSelectMainBean==null || mSheetType.equals(mSelectMainBean.getSheetType())) {
            int zsl = 0;
            for (GetClassPluResult info : mListDatas) {
                zsl += MyUtils.convertToInt(info.getSale_qnty(),0);
            }
            int isSuccess = BusinessBLL.getInstance().deleteGoodsInfoAndUpdateOrderQty(mSheetNo,
                    itemNo,zsl+"");
            if(isSuccess==0){
                AlertUtil.showToast("本地商品更改数量失败！");
            }
        }

        if(mListDatas.size()==0){//如果删除完数据，isCiteOrder为true则是引单数据，则把isCiteOrder设置回false
            if(isCiteOrder)isCiteOrder=false;
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

        //有权限
        if(!CommonUtility.getInstance().havePermission(26)){
            AlertUtil.showToast("当前登录用户没有改价权限!");
            return false;
        }

        return true;
    }

    @Override
    protected void modifyPriceAfter() {
        Intent intent = new Intent(this, ModifyPriceDialog.class);
        intent.putExtra("Price",mSelectGoodsEntity.getSale_price());
        startActivityForResult(intent,200);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if(!onBackFinish()){
                if(mListDatas.size()==0){
                    int isSuccessDelete = BusinessBLL.getInstance().deleteMainInfoAndGoodsInfo(mSheetNo);
                    if(isSuccessDelete ==0){
                        AlertUtil.showToast("删除本地数据失败");
                    }
                }
                setResult(22);
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
                R.string.btnSave,new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(addBefore()){
                            addAfter();
                        }
                        AlertUtil.dismissDialog();
                    } },
                R.string.menu_signout,new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertUtil.dismissDialog();
                        if(mListDatas.size()==0){
                            BusinessBLL.getInstance().deleteMainInfoAndGoodsInfo(mSheetNo);
                        }
                        setResult(22);
                        finish();
                    } }
        );
    }

    private class GetDBDatas extends AsyncTask<String,String,String> {
        // 弱引用是允许被gc回收的;
        private final WeakReference<PifaChukuScanActivity> weakActivity;
        GetDBDatas(PifaChukuScanActivity activity) {
            this.weakActivity = new WeakReference<>(activity);
        }
        @Override
        protected String doInBackground(String... strings) {
            try{
                List<GetClassPluResult> datas= BusinessBLL.getInstance().getAllGoodsInfo(mSheetNo);
                List<GetClassPluResult> oldDatas=BusinessBLL.getInstance().getAllGoodsInfo(mSheetNo);
                mListDatas.addAll(datas);
                mOldListDatas.addAll(oldDatas);
                return "ok";
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            PifaChukuScanActivity activity = weakActivity.get();
            if (activity == null
                    || activity.isFinishing()
                    || activity.isDestroyed()) {
                // activity没了,就结束可以了
                return;
            }
            AlertUtil.setNoButtonMessage("正在加载数据 "+values[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            PifaChukuScanActivity activity = weakActivity.get();
            if (activity == null
                    || activity.isFinishing()
                    || activity.isDestroyed()) {
                // activity没了,就结束可以了
                return;
            }
            AlertUtil.dismissProgressDialog();
            if(s.equals("ok")){
                if(mListDatas.size()>0){//默认选中和第一条
                    mSelectGoodsEntity = mListDatas.get(0);
                    mAdapter.setItemClickPosition(0);
                }
                mAdapter.setListInfo(mListDatas);
                upDateUI();
            }else{
                AlertUtil.showToast("获取本地数据异常："+s);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mListDatas!=null){
            mListDatas.clear();
            mListDatas=null;
        }
        if(mOldListDatas!=null){
            mOldListDatas.clear();
            mOldListDatas=null;
        }
        if(mGetDBDatas !=null){
            mGetDBDatas.cancel(true);
        }
    }

}
