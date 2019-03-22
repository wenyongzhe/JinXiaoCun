package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.db.BusinessBLL;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBean;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.othermodel.view.SelectPiciInfoActivity;
import com.eshop.jinxiaocun.piandian.adapter.PandianScanAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBean;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailResult;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoHuoquBeanResult;
import com.eshop.jinxiaocun.piandian.bean.UploadPandianDetailDataEntity;
import com.eshop.jinxiaocun.piandian.bean.UploadRecordHeadDataEntity;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.AndroidWakeLock;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/9
 * Desc:
 */

public class PandianScanActivity extends CommonBaseScanActivity implements INetWorResult{

    @BindView(R.id.et_barcode)
    EditText mEtBarcode;
    @BindView(R.id.ll_content1)
    LinearLayout mLayoutContent1;
    @BindView(R.id.ll_content2)
    LinearLayout mLayoutContent2;
    @BindView(R.id.tv_pd_orderNo)
    TextView mTvOrderNo;
    @BindView(R.id.tv_pd_fanwei)
    TextView mTvFanwei;
    @BindView(R.id.tv_pd_pihao)
    TextView mTvPihao;
    @BindView(R.id.tv_pd_operId)
    TextView mTvOperId;
    @BindView(R.id.tv_pd_store)
    TextView mTvStore;
    @BindView(R.id.et_pd_bz)
    EditText mEtBz;
    @BindView(R.id.iv_hint)
    ImageView mIvHint;
    @BindView(R.id.tv_pd_nopandian)
    TextView mTvNoPandian;


    private IPandian mServerApi;
    private ILingshouScan mQueryGoodsApi;
    private GetClassPluResult mScanOrSelectGoods;
    private IOtherModel mOtherApi;
    private PandianPihaoHuoquBeanResult mPandianPihao;
    private PandianScanAdapter mAdapter;
    private List<PandianDetailBeanResult> mAddPandianGoodsDetailData = new ArrayList<>();
    private List<PandianDetailBeanResult> mPandianDetailData = new ArrayList<>();
    private PandianDetailBeanResult mSelectPandianDetailEntity = null;
    private GetDBDatas mGetDBDatas;

    private boolean isDianpin=false;//true为单品盘点
    private int mPageSize =2000;
    private int mPageIndex =1;
    private int mNowCount;//现在取到的行数
    private String mSheetNo;//盘点批次号
    private int lastClickedPosition = -1;//标记最后点击的位置

    //RFID
    private int inventoryFlag = 1;
    private boolean loopFlag = false;
    private boolean getInforFlag = false;
    private boolean starting = false;
    public static AndroidWakeLock Awl;

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pandian_scan;
    }

    @Override
    protected boolean onTopBarLeftClick() {
        return finishActivity();
    }

    @Override
    protected void initView() {
        super.initView();

        EventBus.getDefault().register(this);
        mPandianPihao = (PandianPihaoHuoquBeanResult) getIntent().getSerializableExtra("PandianPihaoEntity");
        if( mPandianPihao !=null && mPandianPihao.getOper_range_name().equals("单品盘点")){
            isDianpin=true;
        }
        if( mPandianPihao !=null){
            mSheetNo = mPandianPihao.getSheet_no();
            mTvOrderNo.setText("");
            mTvOrderNo.setSelected(true);
            mTvFanwei.setText(mPandianPihao.getOper_range_name());
            mTvPihao.setText(mPandianPihao.getSheet_no());
            mTvPihao.setSelected(true);
            mTvOperId.setText(mPandianPihao.getOper_id());
            mTvStore.setText("["+mPandianPihao.getBranch_no()+"]"+mPandianPihao.getBranch_name());
            mEtBz.setText(mPandianPihao.getMemo());
        }
        mLayoutScanBottomZslZje.setVisibility(View.VISIBLE);
        mLayoutAllRowNumber.setVisibility(View.VISIBLE);
        mEtBarcode.setOnKeyListener(onKey);
        setTopToolBar("盘点明细",R.mipmap.ic_left_light,"",0,"");
        setTopToolBarRightTitleAndStyle("查找商品",R.drawable.border_bg);
        mBtnAdd.setText(R.string.btnSave);

        setHeaderTitle(R.id.tv_0,R.string.list_item_ProdName,150);//商品名称
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdCode,150);//商品编码
        setHeaderTitle(R.id.tv_2,R.string.list_item_CountN4,100);//盘点数量
        setHeaderTitle(R.id.tv_3,R.string.list_item_XSPrice,100);//销售价格
        setHeaderTitle(R.id.tv_4,R.string.list_item_Unit,60);//单位
        setHeaderTitle(R.id.tv_5,R.string.list_item_Pici_Name,150);//批次
        setHeaderTitle(R.id.tv_6,R.string.list_item_Spec,100);//规格
        setHeaderTitle(R.id.tv_7,R.string.list_item_StoreNum,100);//库存数量

        mAdapter = new PandianScanAdapter(this,mAddPandianGoodsDetailData);
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

        mServerApi = new PandianImp(this);
        mQueryGoodsApi = new LingShouScanImp(this);
        mOtherApi = new OtherModelImp(this);
        //如果是单品盘点则不取盘点明细 否则取盘点明细
        if(!isDianpin){
            AlertUtil.showNoButtonProgressDialog(this,"正在加载数据");
            if(BusinessBLL.getInstance().isHavePandianGoodsEntity("sheet_no='"+mSheetNo+"'")){
                mGetDBDatas = new GetDBDatas(this);
                mGetDBDatas.execute();
            }else{
                getPandianDetailData(mSheetNo);
            }
        }else {
            mGetDBDatas = new GetDBDatas(this);
            mGetDBDatas.execute();
        }

    }

    //获取盘点明细
    private void getPandianDetailData(String sheet_no){
        PandianDetailBean bean = new PandianDetailBean();
        bean.JsonData.sheet_no=sheet_no;//盘点批号
        bean.JsonData.pageNum = mPageIndex;
        bean.JsonData.perNum = mPageSize;
        mServerApi.getPandianDetailData(bean);
    }

    //获取商品批次信息
    private void getGoodsPiciInfo(String product_code){
        Intent intent = new Intent(this, SelectPiciInfoActivity.class);
        intent.putExtra("ProductCode",product_code);
        startActivityForResult(intent,33);
    }

    //上传记录头
    private void uploadRecordHeadData(){
        AlertUtil.showNoButtonProgressDialog(this,"正在上传数据");
        UploadRecordHeadDataEntity bean = new UploadRecordHeadDataEntity();
        bean.JsonData.sheet_no = mTvOrderNo.getText().toString().trim(); //盘点单号 通过getsheetno获取
        bean.JsonData.check_no = mTvPihao.getText().toString().trim();//盘点批次号
        bean.JsonData.trans_no = Config.YwType.CR.toString();//单据类型
        bean.JsonData.branch_no = mPandianPihao.getBranch_no();//盘点门店
        bean.JsonData.oper_range = MyUtils.convertToInt(mPandianPihao.getOper_range(),0);//10 //盘点类型
        bean.JsonData.oper_id = mTvOperId.getText().toString().trim();//操作员
        bean.JsonData.oper_date = DateUtility.getCurrentTime(); //操作时间
        bean.JsonData.memo = mEtBz.getText().toString().trim();//备注
        mServerApi.uploadPandianRecordHeadData(bean);
    }
    //上传明细
    private void uploadPandianDetailData(){

        UploadPandianDetailDataEntity bean = new UploadPandianDetailDataEntity();
        List<UploadPandianDetailDataEntity.UploadPandianDetail> uploadData = new ArrayList<>();

        for (PandianDetailBeanResult obj : mAddPandianGoodsDetailData) {
            UploadPandianDetailDataEntity.UploadPandianDetail data = new UploadPandianDetailDataEntity.UploadPandianDetail();
            data.item_no = obj.getItem_no();//编码
            data.sheet_no = mTvOrderNo.getText().toString().trim(); //盘点单号
            data.item_barcode = obj.getItem_barcode();//批次号
            data.in_price = obj.getIn_price();//进价
            data.sale_price = obj.getSale_price();//销价
            data.check_qty = obj.getCheck_qty();//盘点数量
            data.produce_date = obj.getProduce_date();//生产日期
            data.valid_date = obj.getValid_date() ;//有效日期
            data.as_dealflag = 0;//1：表示该单结束
            uploadData.add(data);
        }

        bean.JsonData = uploadData;
        mServerApi.uploadPandianDetailData(bean);

    }

    // 单品盘点 添加到列表中去
    private void addDianpinGoodsData(GetClassPluResult scanOrSelectGoods){
        if(scanOrSelectGoods !=null){
            if(!scanOrSelectGoods.getEnable_batch().equals("1")){//1为有批次商品
               //非批次商品
                PandianDetailBeanResult obj = new PandianDetailBeanResult();
                obj.setItem_name(scanOrSelectGoods.getItem_name());
                obj.setItem_no(scanOrSelectGoods.getItem_no());
                obj.setBranch_no(Config.branch_no);
                obj.setItem_size(scanOrSelectGoods.getItem_size());
                obj.setUnit_no(scanOrSelectGoods.getUnit_no());
                obj.setIn_price(MyUtils.convertToFloat(scanOrSelectGoods.getPrice(),0f));
                obj.setSale_price(MyUtils.convertToFloat(scanOrSelectGoods.getSale_price(),0f));
                obj.setStock_qty(MyUtils.convertToInt(scanOrSelectGoods.getStock_qty(),0));
                obj.setCheck_qty(1);
                obj.setBalance_qty(0);
                obj.setProduce_date("");
                obj.setValid_date("");
                obj.setItem_barcode("");//批次号
                obj.setHas_stocktake(1);//已盘点
                obj.setStatus(0);//未上传

                boolean isSame = false;
                for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                    if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(scanOrSelectGoods.getItem_no())
                            && TextUtils.isEmpty(mAddPandianGoodsDetailData.get(i).getItem_barcode())
                            ){
                        //已经存在自动+1
                        int pdNumber = mAddPandianGoodsDetailData.get(i).getCheck_qty()+1;
                        mAddPandianGoodsDetailData.get(i).setCheck_qty(pdNumber);
                        //同时更新表中单品盘点盘点数量
                        int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsCheckQty(pdNumber,obj.getItem_no(),mSheetNo);
                        if(isSuccess==0){
                            AlertUtil.showToast("更新本地数据失败！");
                        }
                        isSame = true;
                        break;
                    }
                }
                if(!isSame){//不存在列表中 添加到列表里
                    mAddPandianGoodsDetailData.add(obj);
                    boolean isSuccess = BusinessBLL.getInstance().insertPandianGoodsEntity(mSheetNo,obj);
                    if(!isSuccess){
                        AlertUtil.showToast("保存本地单品盘点数据失败！");
                    }
                }
                mAdapter.setListInfo(mAddPandianGoodsDetailData);
                upDateShowView();
            }else{//是批次商品
                mScanOrSelectGoods = scanOrSelectGoods;
                //去取商品批次信息
                getGoodsPiciInfo(scanOrSelectGoods.getItem_no());
            }
        }else{
            AlertUtil.showToast("不在盘点范围!");
        }


    }
    // 单品盘点 获取商品批次信息 并在商品中赋值
    private void addDianpinGoodsPiciData(GoodsPiciInfoBeanResult piciInfo ){
        if(piciInfo !=null ){
            PandianDetailBeanResult obj = new PandianDetailBeanResult();
            obj.setItem_name(mScanOrSelectGoods.getItem_name());
            obj.setItem_no(mScanOrSelectGoods.getItem_no());
            obj.setBranch_no(Config.branch_no);
            obj.setItem_size(mScanOrSelectGoods.getItem_size());
            obj.setUnit_no(mScanOrSelectGoods.getUnit_no());
            obj.setIn_price(MyUtils.convertToFloat(mScanOrSelectGoods.getPrice(),0f));
            obj.setSale_price(MyUtils.convertToFloat(mScanOrSelectGoods.getSale_price(),0f));
            obj.setStock_qty(MyUtils.convertToInt(mScanOrSelectGoods.getStock_qty(),0));
            obj.setCheck_qty(1);
            obj.setBalance_qty(0);
            obj.setProduce_date(piciInfo.getProduce_date());
            obj.setValid_date(piciInfo.getValid_date());
            obj.setItem_barcode(piciInfo.getItem_barcode());//批次号
            obj.setHas_stocktake(1);//已盘点
            obj.setStatus(0);//未上传

            boolean isSame = false;
            for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(obj.getItem_no())
                        && mAddPandianGoodsDetailData.get(i).getItem_barcode().equals(obj.getItem_barcode())
                        ){
                    //已经存在自动+1
                    int pdNumber = mAddPandianGoodsDetailData.get(i).getCheck_qty()+1;
                    mAddPandianGoodsDetailData.get(i).setCheck_qty(pdNumber);
                    //同时更新表中单品盘点盘点数量
                    int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsCheckQty(pdNumber,obj.getItem_no(),mSheetNo);
                    if(isSuccess==0){
                        AlertUtil.showToast("更新本地数据失败！");
                    }
                    isSame = true;
                    break;
                }
            }
            if(!isSame){//不存在列表中 添加到列表里
                mAddPandianGoodsDetailData.add(obj);
                boolean isSuccess = BusinessBLL.getInstance().insertPandianGoodsEntity(mSheetNo,obj);
                if(!isSuccess){
                    AlertUtil.showToast("保存本地单品盘点数据失败！");
                }
            }
            mAdapter.setListInfo(mAddPandianGoodsDetailData);
            upDateShowView();
        }else{
            AlertUtil.showToast("不在盘点范围!");
        }
    }

    // 非单品盘点 添加到列表中去
    private void addNoDianpinGoodsData(GetClassPluResult scanOrSelectGoods){
        if(mPandianDetailData !=null && mPandianDetailData.size()>0){

            //是否在盘点范围
            boolean isBelongToPandian = false;
            for (PandianDetailBeanResult obj : mPandianDetailData) {
                if(obj.getItem_no().equals(scanOrSelectGoods.getItem_no())){
                    isBelongToPandian = true;
                    break;
                }
            }

            if(isBelongToPandian){
                if(scanOrSelectGoods.getEnable_batch().equals("1")) {//1为有批次商品
                    //批次商品
                    mScanOrSelectGoods = scanOrSelectGoods;
                    //去取商品批次信息
                    getGoodsPiciInfo(scanOrSelectGoods.getItem_no());
                }else{
                    //非批次商品
                    PandianDetailBeanResult obj = new PandianDetailBeanResult();
                    obj.setItem_name(scanOrSelectGoods.getItem_name());
                    obj.setItem_no(scanOrSelectGoods.getItem_no());
                    obj.setBranch_no(Config.branch_no);
                    obj.setItem_size(scanOrSelectGoods.getItem_size());
                    obj.setUnit_no(scanOrSelectGoods.getUnit_no());
                    obj.setIn_price(MyUtils.convertToFloat(scanOrSelectGoods.getPrice(), 0f));
                    obj.setSale_price(MyUtils.convertToFloat(scanOrSelectGoods.getSale_price(), 0f));
                    obj.setStock_qty(MyUtils.convertToInt(scanOrSelectGoods.getStock_qty(), 0));
                    obj.setCheck_qty(1);
                    obj.setBalance_qty(0);
                    obj.setProduce_date("");
                    obj.setValid_date("");
                    obj.setItem_barcode("");//批次号
                    obj.setHas_stocktake(1);//已盘点
                    obj.setStatus(0);//未上传

                    boolean isSame = false;
                    for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                        if (mAddPandianGoodsDetailData.get(i).getItem_no().equals(scanOrSelectGoods.getItem_no())
                                && TextUtils.isEmpty(mAddPandianGoodsDetailData.get(i).getItem_barcode())
                                ) {
                            //已经存在自动+1
                            int pdNumber = mAddPandianGoodsDetailData.get(i).getCheck_qty() + 1;
                            mAddPandianGoodsDetailData.get(i).setCheck_qty(pdNumber);
                            //同时更新表中盘点状态和更改盘点数量 标记为已盘点
                            int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsStatusAndCheckQty(pdNumber,"1",obj.getItem_no(),mSheetNo);
                            if(isSuccess==0){
                                AlertUtil.showToast("更新本地数据失败！");
                            }
                            isSame = true;
                            break;
                        }
                    }
                    if (!isSame) {//不存在列表中 添加到列表里
                        mAddPandianGoodsDetailData.add(obj);
                        //同时更新表中盘点状态和更改盘点数量 标记为已盘点
                        int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsStatusAndCheckQty(1,"1",obj.getItem_no(),mSheetNo);
                        if(isSuccess==0){
                            AlertUtil.showToast("更新本地数据失败！");
                        }
                    }
                    mAdapter.setListInfo(mAddPandianGoodsDetailData);
                    upDateShowView();
                }

            }else{
                AlertUtil.showToast("不在盘点范围!");
            }

        }else{
            AlertUtil.showToast("不在盘点范围!");
        }
    }
    // 非单品盘点 获取商品批次信息 并在商品中赋值
    private void addNoDianpinGoodsPiciData(GoodsPiciInfoBeanResult piciInfo ){
        if(piciInfo !=null){

            PandianDetailBeanResult obj = new PandianDetailBeanResult();
            obj.setItem_name(mScanOrSelectGoods.getItem_name());
            obj.setItem_no(mScanOrSelectGoods.getItem_no());
            obj.setBranch_no(Config.branch_no);
            obj.setItem_size(mScanOrSelectGoods.getItem_size());
            obj.setUnit_no(mScanOrSelectGoods.getUnit_no());
            obj.setIn_price(MyUtils.convertToFloat(mScanOrSelectGoods.getPrice(), 0f));
            obj.setSale_price(MyUtils.convertToFloat(mScanOrSelectGoods.getSale_price(), 0f));
            obj.setStock_qty(MyUtils.convertToInt(mScanOrSelectGoods.getStock_qty(), 0));
            obj.setCheck_qty(1);
            obj.setBalance_qty(0);
            obj.setProduce_date(piciInfo.getProduce_date());
            obj.setValid_date(piciInfo.getValid_date());
            obj.setItem_barcode(piciInfo.getItem_barcode());//批次号
            obj.setHas_stocktake(1);//已盘点
            obj.setStatus(0);//未上传

            boolean isSame = false;
            for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(obj.getItem_no())
                        && mAddPandianGoodsDetailData.get(i).getItem_barcode().equals(obj.getItem_barcode())
                        ){
                    //已经存在自动+1
                    int pdNumber = mAddPandianGoodsDetailData.get(i).getCheck_qty()+1;
                    mAddPandianGoodsDetailData.get(i).setCheck_qty(pdNumber);
                    //同时更新表中盘点状态和更改盘点数量 标记为已盘点
                    int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsStatusAndCheckQty(pdNumber,"1",obj.getItem_no(),mSheetNo);
                    if(isSuccess==0){
                        AlertUtil.showToast("更新本地数据失败！");
                    }
                    isSame = true;
                    break;
                }
            }
            if(!isSame){//不存在列表中 添加到列表里
                mAddPandianGoodsDetailData.add(obj);
                //同时更新表中盘点状态和更改盘点数量 标记为已盘点
                int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsStatusAndCheckQty(1,"1",obj.getItem_no(),mSheetNo);
                if(isSuccess==0){
                    AlertUtil.showToast("更新本地数据失败！");
                }
            }
            mAdapter.setListInfo(mAddPandianGoodsDetailData);
            upDateShowView();
        }else{
            AlertUtil.showToast("不在盘点范围!");
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
            mEtBarcode.setText(barcode);
            mQueryGoodsApi.getPLUInfo(barcode);
        }
    }

    @Override
    protected boolean addBefore() {

        if(mAddPandianGoodsDetailData ==null || mAddPandianGoodsDetailData.size()==0){
            AlertUtil.showToast("请扫描/添加盘点商品，再保存!");
            return false;
        }

        if(!isDianpin){
            int difNumder = mPandianDetailData.size() - mAddPandianGoodsDetailData.size();
            if(difNumder != 0){//没盘完不要上传
                AlertUtil.showAlert(this, R.string.dialog_title,
                        "还有"+difNumder+"种商品没有盘完，请检查!", R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertUtil.dismissDialog();
                                addAfter();
                            }
                        }, R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertUtil.dismissDialog();
                            }
                        });
                return false;
            }else{//说明盘点完了 直接保存
                return true;
            }
        }

        return true;
    }

    @Override
    protected void addAfter() {
        //在上传盘点单到后台保存前，获取盘点单号
        if(TextUtils.isEmpty(mTvOrderNo.getText().toString().trim())){
            SheetNoBean bean = new SheetNoBean();
            bean.JsonData.trans_no = Config.YwType.CR.toString();
            bean.JsonData.branch_no=Config.branch_no;
            mOtherApi.getSheetNoData(bean);
        }else{
            uploadRecordHeadData();
        }
    }

    @Override
    protected boolean deleteBefore() {
        if(mAddPandianGoodsDetailData ==null || mAddPandianGoodsDetailData.size()==0){
            AlertUtil.showToast("没有盘点商品，不能做删除操作!");
            return false;
        }

        if(mSelectPandianDetailEntity ==null){
            AlertUtil.showToast("请选择要删除的商品!");
            return false;
        }
        return true;
    }

    @Override
    protected void deleteAfter() {
        for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
            if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(mSelectPandianDetailEntity.getItem_no())){
                if(isDianpin){
                    //删除单品盘点的记录
                    int isSuccess = BusinessBLL.getInstance().deleteStocktakeGoods(mSelectPandianDetailEntity.getItem_no(),mSheetNo);
                    if(isSuccess==0){
                        AlertUtil.showToast("删除本地单品盘点数据失败！");
                    }
                }else{
                    //同时更新表中盘点状态和修改盘点数量 标记为未盘点
                    int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsStatusAndCheckQty(0,"0",mSelectPandianDetailEntity.getItem_no(),mSheetNo);
                    if(isSuccess==0){
                        AlertUtil.showToast("更新本地数据失败！");
                    }
                }
                mSelectPandianDetailEntity = null;
                mAddPandianGoodsDetailData.remove(i);
                mAdapter.setItemClickPosition(-1);
                mAdapter.setListInfo(mAddPandianGoodsDetailData);
                break;
            }
        }
        upDateShowView();
    }

    @Override
    protected boolean modifyCountBefore() {
        if(mAddPandianGoodsDetailData ==null || mAddPandianGoodsDetailData.size()==0){
            AlertUtil.showToast("没有盘点商品，不能做改数操作!");
            return false;
        }

        if(mSelectPandianDetailEntity ==null){
            AlertUtil.showToast("请选择要改数的商品!");
            return false;
        }
        return true;
    }

    @Override
    protected void modifyCountAfter() {
        Intent intent = new Intent();
        intent.putExtra("countN", mSelectPandianDetailEntity.getCheck_qty()+"");
        intent.setClass(this, ModifyCountDialog.class);
        startActivityForResult(intent, 22);
    }

    @OnClick(R.id.iv_hint)
    public void onClickShowHint(){
        if(mLayoutContent2.getVisibility()==View.VISIBLE){
            mIvHint.setImageResource(R.mipmap.arrow_up);
            mLayoutContent1.setVisibility(View.GONE);
            mLayoutContent2.setVisibility(View.GONE);
        }else{
            mIvHint.setImageResource(R.mipmap.arrow_down);
            mLayoutContent1.setVisibility(View.VISIBLE);
            mLayoutContent2.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.tv_pd_nopandian)
    public void onClickNoPandian(){
        Intent intent = new Intent(this,CheckNoPandianGoodsListActivity.class);
        intent.putExtra("SheetNo", mSheetNo);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        mSelectPandianDetailEntity = mAddPandianGoodsDetailData.get(position);
        mAdapter.setItemClickPosition(position);
        mAdapter.notifyDataSetChanged();

        if (MyUtils.isFastDoubleClick() && position == lastClickedPosition) {
            //快速双击修改数量
            Intent intent = new Intent();
            intent.putExtra("countN", mSelectPandianDetailEntity.getCheck_qty()+"");
            intent.setClass(this, ModifyCountDialog.class);
            startActivityForResult(intent, 22);
        }
        lastClickedPosition = position;

    }

    @Override
    protected void onTopBarRightClick() {
        super.onTopBarRightClick();
        Intent mIntent = new Intent(this, QreShanpingActivity.class);
        //如果是类别盘点，查询商品只显示这个类别
        if( mPandianPihao !=null && mPandianPihao.getOper_range_name().equals("类别盘点")){
            mIntent.putExtra("TYPE_NO",mPandianPihao.getCheck_cls());
        }
        startActivityForResult(mIntent,1);
    }

    private long time;
    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //获取盘点明细
            case Config.MESSAGE_OK:
                PandianDetailResult result = (PandianDetailResult) o;
                if(result==null || result.getDetailData()==null){
                    AlertUtil.showToast("获取盘点明细数据为空!");
                    return;
                }
                mPandianDetailData.addAll(result.getDetailData());
                mNowCount+=result.getNowCount();
                try {
                    boolean isSuccess = BusinessBLL.getInstance().insertPandianGoodsEntitys(mSheetNo,result.getDetailData());
                    if(!isSuccess){
                        AlertUtil.showToast("保存本地盘点数据失败!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("lu","SQLite Exception is "+e.getMessage());
                }

                long lastTime = System.currentTimeMillis();
                float diffTime = (lastTime-time)/1000f;
                time = System.currentTimeMillis();
                Log.e("lu","run time is "+diffTime);
                Log.e("lu","mNowCount = "+mNowCount);
                //总条数相等不再取盘点明细
                if(result.getTotalCount()!=0 && result.getTotalCount()!=mNowCount){
                    AlertUtil.setNoButtonMessage("正在加载数据 "+mNowCount+"/"+result.getTotalCount());
                    mPageIndex++;
                    getPandianDetailData(mSheetNo);
                }else{
                    mTvNoPandian.setVisibility(View.VISIBLE);
                    AlertUtil.dismissProgressDialog();
                }
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast("获取盘点明细失败！原因："+o.toString());
                break;
            //扫描时返回搜索的数据
            case Config.MESSAGE_GOODS_INFOR:
                mEtBarcode.requestFocus();
                mEtBarcode.setFocusable(true);
                mEtBarcode.setText("");
                List<GetClassPluResult> goodsData = (List<GetClassPluResult>) o;
                if(goodsData !=null && goodsData.size()>0){
                    if(goodsData.size()==1){
                        //精准查询接口的  可能有多条数据
                        if(isDianpin){//单品盘点
                            addDianpinGoodsData(goodsData.get(0));
                        }else{
                            addNoDianpinGoodsData(goodsData.get(0));
                        }
                    }else{
                        Intent intent = new Intent(PandianScanActivity.this,SelectPandianGoodsListActivity.class);
                        intent.putExtra("GoodsInfoList", (Serializable) goodsData);
                        startActivityForResult(intent,44);
                    }
                }else{
                    AlertUtil.showToast("该条码没有对应的商品数据!");
                }
                getInforFlag = true;
                break;
            case Config.MESSAGE_GOODS_INFOR_FAIL:
                AlertUtil.showToast("不在盘点范围！原因："+o.toString());
                break;
            //业务单据号
            case Config.MESSAGE_SHEETNO_OK:
                SheetNoBeanResult sheetNoBeanResult = (SheetNoBeanResult) o;
                mTvOrderNo.setText(sheetNoBeanResult==null?"":sheetNoBeanResult.getSheetno());
                if(sheetNoBeanResult !=null && !TextUtils.isEmpty(sheetNoBeanResult.getSheetno()) ){
                    addAfter();
                }
                break;
            case Config.MESSAGE_SHEETNO_ERROR:
                AlertUtil.showToast("获取业务单据号失败："+o.toString());
                break;
            //上传记录头 成功
            case Config.MESSAGE_SUCCESS:
                uploadPandianDetailData();// 上传盘点明细
                break;
            // 上传盘点明细 成功
            case Config.MESSAGE_RESULT_SUCCESS:
                mOtherApi.sheetSave(Config.YwType.CR.toString(),mTvOrderNo.getText().toString());//保存业务单据
                break;
            //保存业务单据 成功
            case Config.RESULT_SUCCESS:
                if(isDianpin){
                    //上传完了单品盘点 删除本地记录的单品盘点记录
                    int isSuccess = BusinessBLL.getInstance().deleteStocktakeGoods(mSheetNo);
                    if(isSuccess==0){
                        AlertUtil.showToast("删除本地数据单品盘点失败！");
                    }
                }else {
                    //同时更新表中盘点状态 标记为已盘点
                    int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsStatus(mAddPandianGoodsDetailData,mSheetNo);
                    if(isSuccess==0){
                        AlertUtil.showToast("更新本地数据失败！");
                    }
                }
                AlertUtil.showToast(o.toString());
                AlertUtil.dismissProgressDialog();
                setResult(22);
                finish();
                break;
            //上传记录头或上传盘点明细  保存业务单据 失败
            case Config.MESSAGE_FAIL:
                AlertUtil.showToast(o.toString());
                AlertUtil.dismissProgressDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Config.RESULT_SELECT_GOODS){
            List<GetClassPluResult> selectGoodsList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
            if(selectGoodsList !=null && selectGoodsList.size()>0){
                if(selectGoodsList.size()==1){
                    if(isDianpin){//单品盘点
                        addDianpinGoodsData(selectGoodsList.get(0));
                    }else{
                        addNoDianpinGoodsData(selectGoodsList.get(0));
                    }
                }else{
                    Intent intent = new Intent(PandianScanActivity.this,SelectPandianGoodsListActivity.class);
                    intent.putExtra("GoodsInfoList", (Serializable) selectGoodsList);
                    startActivityForResult(intent,44);
                }
            }else{
                AlertUtil.showToast("该条码没有对应的商品数据!");
            }
        }

        //选择批次信息
        if(requestCode == 33 && resultCode == RESULT_OK){
            GoodsPiciInfoBeanResult piciInfo = (GoodsPiciInfoBeanResult) data.getSerializableExtra("PiciEntity");
            if(isDianpin) {
                //单品盘点
                addDianpinGoodsPiciData(piciInfo);
            }else{
                addNoDianpinGoodsPiciData(piciInfo);
            }
        }
        //没有选择批次信息
        if(requestCode == 33 && resultCode == RESULT_CANCELED){
            AlertUtil.showToast("放弃盘点该商品!");
            mScanOrSelectGoods = null;
        }

        //修改数量
        if(requestCode == 22 && resultCode == RESULT_OK){
            String countN = data.getStringExtra("countN");
            for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(mSelectPandianDetailEntity.getItem_no())){
                    mAddPandianGoodsDetailData.get(i).setCheck_qty(MyUtils.convertToInt(countN,0));
                    mAdapter.setListInfo(mAddPandianGoodsDetailData);
                    if(!isDianpin){
                        //同时更新表中盘点状态和更改盘点数量 标记为已盘点
                        int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsStatusAndCheckQty(MyUtils.convertToInt(countN,0),
                                "1",mSelectPandianDetailEntity.getItem_no(),mSheetNo);
                        if(isSuccess==0){
                            AlertUtil.showToast("更新本地数据失败！");
                        }
                    }else{
                        int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsCheckQty(MyUtils.convertToInt(countN,0),
                                mSelectPandianDetailEntity.getItem_no(),mSheetNo);
                        if(isSuccess==0){
                            AlertUtil.showToast("更新本地数据失败！");
                        }
                    }
                    break;
                }
            }
            upDateShowView();
        }

        //搜索返回多条数据 ，选择其中一条
        if(requestCode == 44 && resultCode == RESULT_OK){
            GetClassPluResult entity = (GetClassPluResult) data.getSerializableExtra("GoodsInfoEntity");
            if(isDianpin){//单品盘点
                addDianpinGoodsData(entity);
            }else{
                addNoDianpinGoodsData(entity);
            }
        }

        if(requestCode == 44 && resultCode == RESULT_CANCELED){
            AlertUtil.showToast("放弃盘点该商品!");
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            return finishActivity();
        }
        //RFID
        if (keyCode == 139 ||keyCode == 280) {
            if (event.getRepeatCount() == 0) {
                readTag();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //非单品   从未盘点的商品中选择
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNoPandianDatas(PandianDetailBeanResult eventResult){
        PandianDetailBeanResult result = eventResult;
        if(result!=null){
            mAddPandianGoodsDetailData.add(result);
            mAdapter.setListInfo(mAddPandianGoodsDetailData);
            upDateShowView();
            //同时更新表中盘点状态 标记为已盘点
            int isSuccess = BusinessBLL.getInstance().updateStocktakeGoodsStatusAndCheckQty(result.getCheck_qty(),"1",result.getItem_no(),mSheetNo);
            if(isSuccess==0){
                AlertUtil.showToast("更新本地数据失败！");
            }
        }
    }

    private class GetDBDatas extends AsyncTask<String,String,String>{

        // 弱引用是允许被gc回收的;
        private final WeakReference<PandianScanActivity> weakActivity;
        GetDBDatas(PandianScanActivity activity) {
            this.weakActivity = new WeakReference<>(activity);
        }
        @Override
        protected String doInBackground(String... strings) {
            try{
                long time = System.currentTimeMillis();
                StringBuffer where = new StringBuffer();
                where.append("sheet_no='");
                where.append(mSheetNo);
                where.append("' and status=0");//0未上传
                //取未上传的所有本地数据
                BusinessBLL.getInstance().getDBStocktakeGoodsDatas(where.toString(), new BusinessBLL.DbCallBack<PandianDetailBeanResult>() {
                    @Override
                    public void progressUpdate(int progress, int maxProgress,PandianDetailBeanResult module) {
                        publishProgress(progress+"/"+maxProgress);
                        if(!isDianpin){
                            mPandianDetailData.add(module);
                        }
                        //0未盘点 1已盘点
                        if(module.getHas_stocktake()==1){
                            mAddPandianGoodsDetailData.add(module);
                        }
                    }
                });
                Log.e("lu","get db data time is "+(System.currentTimeMillis()-time)/1000);
                return "ok";
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            PandianScanActivity activity = weakActivity.get();
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
            PandianScanActivity activity = weakActivity.get();
            if (activity == null
                    || activity.isFinishing()
                    || activity.isDestroyed()) {
                // activity没了,就结束可以了
                return;
            }
            AlertUtil.dismissProgressDialog();
            if(s.equals("ok")){

                if(!isDianpin){
                    //本地数据库里有盘点单就显示未盘点商品按钮
//                    if(mPandianDetailData.size()>0){
                        mTvNoPandian.setVisibility(View.VISIBLE);
//                    }else{
//                        mTvNoPandian.setVisibility(View.GONE);
//                    }
                }

                if(mAddPandianGoodsDetailData.size()>0){
                    mAdapter.setListInfo(mAddPandianGoodsDetailData);
                    upDateShowView();
                }
            }else{
                AlertUtil.showToast("获取本地数据异常："+s);
            }
        }
    }
    //分部内容显示与隐藏
    private void upDateShowView(){
        if(mAddPandianGoodsDetailData.size()>0){
            mLayoutContent1.setVisibility(View.GONE);
            mLayoutContent2.setVisibility(View.GONE);
            mIvHint.setImageResource(R.mipmap.arrow_up);
        }else{
            mLayoutContent1.setVisibility(View.VISIBLE);
            mLayoutContent2.setVisibility(View.VISIBLE);
            mIvHint.setImageResource(R.mipmap.arrow_down);
        }

        float allCount=0;
        float allMoney=0;
        for (PandianDetailBeanResult info : mAddPandianGoodsDetailData) {
            allCount+=info.getCheck_qty();
            allMoney+=info.getSale_price()*info.getCheck_qty();
        }

        mTvAllRowNumber.setText(mAddPandianGoodsDetailData.size()+"");
        mTvZsl.setText(allCount+"");
        mTvZje.setText(allMoney+"");
    }
    //退出界面时判断是否保存数据
    private boolean finishActivity(){
        if(mAddPandianGoodsDetailData.size()>0){
            AlertUtil.showAlert(this, R.string.dialog_title,
                    "商品没有上传保存，是否退出？", R.string.btnSave, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertUtil.dismissDialog();
                            if(addBefore()){
                                addAfter();
                            }
                        }
                    }, R.string.menu_signout, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertUtil.dismissDialog();
                            setResult(22);
                            finish();
                        }
                    });
            return true;
        }
        setResult(22);
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAddPandianGoodsDetailData!=null){
            mAddPandianGoodsDetailData.clear();
            mAddPandianGoodsDetailData=null;
        }
        if(mPandianDetailData!=null){
            mPandianDetailData.clear();
            mPandianDetailData=null;
        }
        EventBus.getDefault().unregister(this);
        if(mGetDBDatas !=null){
            mGetDBDatas.cancel(true);
        }
    }

    public class BtInventoryClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            readTag();
        }
    }

    private void readTag() {
        if ( !starting )// 识别标签
        {
            Awl.WakeLock();
            switch (inventoryFlag) {
                case 0:// 单步
                {
                    String strUII = mReader.inventorySingleTag();
                    if (!TextUtils.isEmpty(strUII)) {
                        String strEPC = mReader.convertUiiToEPC(strUII);
//                        addEPCToList(strEPC, "N/A");
                    } else {
                        ToastUtils.showLong( R.string.uhf_msg_inventory_fail);
//					mContext.playSound(2);
                    }
                }
                break;
                case 1:// 单标签循环  .startInventoryTag((byte) 0, (byte) 0))
                {
                    //  mContext.mReader.setEPCTIDMode(true);
                    if (mReader.startInventoryTag(0,0)) {
                        starting = true;
                        loopFlag = true;
                        new TagThread().start();
                    } else {
                        mReader.stopInventory();
                        ToastUtils.showLong( R.string.uhf_msg_inventory_open_fail);
//					mContext.playSound(2);
                    }
                }
                break;
                default:
                    break;
            }
        } else {// 停止识别
            Awl.ReleaseWakeLock();
            stopInventory();
        }
    }

    /**
     * 停止识别
     */
    private void stopInventory() {
        if (loopFlag) {
            loopFlag = false;
            if (mReader.stopInventory()) {
                starting = false;
            } else {
                ToastUtils.showLong( R.string.uhf_msg_inventory_stop_fail);
            }
        }
    }

//    /**
//     * 判断EPC是否在列表中
//     *
//     * @param strEPC 索引
//     * @return
//     */
    /*
    public int checkIsExist(String strEPC) {
        int existFlag = -1;
        if (TextUtils.isEmpty(strEPC)) {
            return existFlag;
        }
        String tempStr = "";
        for (int i = 0; i < tagList.size(); i++) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp = tagList.get(i);
            tempStr = temp.get("tagUii");
            if (strEPC.equals(tempStr)) {
                existFlag = i;
                break;
            }
        }
        return existFlag;
    }*/

    class TagThread extends Thread {
        public void run() {
            String strTid;
            String strResult;
            String[] res = null;
            getInforFlag = true;
            while (loopFlag) {
                while (getInforFlag){
                    res = mReader.readTagFromBuffer();
                    if (res != null) {
                        strTid = res[0];
                        if (strTid.length() != 0 && !strTid.equals("0000000" +"000000000") && !strTid.equals("000000000000000000000000")) {
                            strResult = strTid ;
                        } else {
                            strResult = "";
                        }
                        //Log.i("data","EPC:"+res[1]+"|"+strResult);
                        Message msg = handler.obtainMessage();
                        msg.obj = strResult + "@" + mReader.convertUiiToEPC(res[1]) + "@" + res[2];
                        getInforFlag = false;
                        handler.sendMessage(msg);
                    }
                }
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = msg.obj + "";
            String[] strs = result.split("@");
            addEPCToList(strs[0], strs[1]);
            playSound(1);
        }
    };

    private void addEPCToList(String str, String str1) {
        if(!TextUtils.isEmpty(str1)){
            //精准查询接口的
            mQueryGoodsApi.getPLUInfo(str1);
        }
    }

}
