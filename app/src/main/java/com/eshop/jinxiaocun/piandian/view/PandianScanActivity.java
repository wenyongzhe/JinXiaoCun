package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseScanActivity;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.IQueryGoods;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.lingshou.presenter.QueryGoodsImp;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBean;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBean;
import com.eshop.jinxiaocun.othermodel.bean.SheetNoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.piandian.adapter.PandianScanAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBean;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoHuoquBeanResult;
import com.eshop.jinxiaocun.piandian.bean.UploadPandianDetailDataEntity;
import com.eshop.jinxiaocun.piandian.bean.UploadRecordHeadDataEntity;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;
import com.eshop.jinxiaocun.widget.ModifyGoodsPiciDialog;

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

    private IPandian mServerApi;
    private ILingshouScan mQueryGoodsApi;
    private GetClassPluResult mScanOrSelectGoods;
    private IOtherModel mOtherApi;
    private PandianPihaoHuoquBeanResult mPandianPihao;
    private PandianScanAdapter mAdapter;
    private List<PandianDetailBeanResult> mAddPandianGoodsDetailData = new ArrayList<>();
    private List<PandianDetailBeanResult> mPandianDetailData = new ArrayList<>();
    private PandianDetailBeanResult mSelectPandianDetailEntity = null;
    private boolean isDianpin=false;//true为单品盘点
    private boolean isSure = false;//true为继续保存

    @Override
    protected int getLayoutContentId() {
        return R.layout.activity_pandian_scan;
    }

    @Override
    protected void initView() {
        super.initView();

        mPandianPihao = (PandianPihaoHuoquBeanResult) getIntent().getSerializableExtra("PandianPihaoEntity");
        if( mPandianPihao !=null && mPandianPihao.getOper_range_name().equals("单品盘点")){
            isDianpin=true;
        }
        if( mPandianPihao !=null){
            mTvOrderNo.setText("");
            mTvOrderNo.setSelected(true);
            mTvFanwei.setText(mPandianPihao.getOper_range_name());
            mTvPihao.setText(mPandianPihao.getSheet_no());
            mTvPihao.setSelected(true);
            mTvOperId.setText(mPandianPihao.getOper_id());
            mTvStore.setText("["+mPandianPihao.getBranch_no()+"]"+mPandianPihao.getBranch_name());
            mEtBz.setText(mPandianPihao.getMemo());
        }
//        CommonUtility.getInstance().closeKeyboard(this,mEtBarcode);
        CommonUtility.getInstance().closeKeyboard(this,mEtBz);
        mEtBarcode.setOnKeyListener(onKey);
        setTopToolBar("盘点明细",R.mipmap.ic_left_light,"",R.mipmap.add,"");
        mBtnAdd.setText(R.string.btnSave);

        setHeaderTitle(R.id.tv_0,R.string.list_item_ProdName,150);//商品名称
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdCode,150);//商品编码
        setHeaderTitle(R.id.tv_2,R.string.list_item_Spec,100);//规格
        setHeaderTitle(R.id.tv_3,R.string.list_item_Price,100);//价格
        setHeaderTitle(R.id.tv_4,R.string.list_item_XSPrice,100);//销售价格
        setHeaderTitle(R.id.tv_5,R.string.list_item_Unit,80);//单位
//        setHeaderTitle(R.id.tv_6,R.string.list_item_StoreName,150);//仓库名称
        setHeaderTitle(R.id.tv_6,R.string.list_item_StoreNum,100);//库存数量
        setHeaderTitle(R.id.tv_7,R.string.list_item_CountN4,100);//盘点数量
//        setHeaderTitle(R.id.tv_9,R.string.list_item_DiffCount,100);//差异数量

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
            getPandianDetailData(mPandianPihao.getSheet_no());
        }

    }

    //获取盘点明细
    private void getPandianDetailData(String sheet_no){
        PandianDetailBean bean = new PandianDetailBean();
        bean.JsonData.sheet_no=sheet_no;//盘点批号
        mServerApi.getPandianDetailData(bean);
    }

    //获取商品批次信息
    private void getGoodsPiciInfo(String product_code){
        GoodsPiciInfoBean bean = new GoodsPiciInfoBean();
        bean.JsonData.as_branchNo=Config.branch_no;//门店号
        bean.JsonData.as_posid=Config.posid;
        bean.JsonData.as_item_no=product_code;//商品编码
        mOtherApi.getGoodsPiciInfo(bean);
    }

    //上传记录头
    private void uploadRecordHeadData(){
        UploadRecordHeadDataEntity bean = new UploadRecordHeadDataEntity();

        bean.JsonData.sheet_no = mTvOrderNo.getText().toString().trim(); //盘点单号 通过getsheetno获取
        bean.JsonData.check_no = mTvPihao.getText().toString().trim();//盘点批次号
        bean.JsonData.trans_no = Config.YwType.CY.toString();//"CY"  单据类型
        bean.JsonData.branch_no = mPandianPihao.getBranch_no();//盘点门店
        bean.JsonData.oper_range = MyUtils.convertToInt(mPandianPihao.getOper_range(),0);//10 //盘点类型
        bean.JsonData.oper_id = mTvOperId.getText().toString().trim();//操作员
        bean.JsonData.oper_date = DateUtility.getCurrentTime(); //操作时间
        bean.JsonData.memo = mEtBz.getText().toString().trim();//备注
        mServerApi.uploadPandianRecordHeadData(bean);
    }

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

    //选择商品时  添加到列表中去
    private void addGoodsData(GetClassPluResult scanOrSelectGoods){
        if(scanOrSelectGoods !=null){
            mScanOrSelectGoods = scanOrSelectGoods;

            boolean isSame = false;
            for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(scanOrSelectGoods.getItem_no())){
                    //已经存在自动+1
                    int pdNumber = mAddPandianGoodsDetailData.get(i).getCheck_qty()+1;
                    mAddPandianGoodsDetailData.get(i).setCheck_qty(pdNumber);
                    isSame = true;
                    break;
                }
            }
            if(isSame){//已经存在直接刷新
                mAdapter.setListInfo(mAddPandianGoodsDetailData);
                return;
            }


            //单品盘点  选择的商品或扫描的商品没有批次可以填空 有批次就去取商品批次信息
            if(isDianpin){
                if(!scanOrSelectGoods.getEnable_batch().equals("1")){//1为有批次商品
                    PandianDetailBeanResult obj = new PandianDetailBeanResult();
                    obj.setItem_name(scanOrSelectGoods.getItem_name());
                    obj.setItem_no(scanOrSelectGoods.getItem_no());
                    obj.setBranch_no(mScanOrSelectGoods.getItem_no());
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

                    mAddPandianGoodsDetailData.add(obj);
                    mAdapter.setListInfo(mAddPandianGoodsDetailData);

                    return;
                }
            }

            //其他盘点  选择的商品或扫描的商品没有批次不作处理 有批次就去取商品批次信息，
            // 如果返回的商品批次信息没有批次，则手动输入批次
            if(scanOrSelectGoods.getEnable_batch().equals("1")){//1为有批次商品
                //去取商品批次信息
                getGoodsPiciInfo(scanOrSelectGoods.getItem_no());
            }else{
                AlertUtil.showToast("不在盘点范围!");
            }

        }else{
            AlertUtil.showToast("不在盘点范围!");
        }
    }

    //获取商品批次信息 并在商品中赋值
    private void addGoodsPiciData(List<GoodsPiciInfoBeanResult> goodsPiciDataList){


        if(goodsPiciDataList ==null || goodsPiciDataList.size()==0){
            AlertUtil.showToast("不在盘点范围!");
            return;
        }

        GoodsPiciInfoBeanResult piciInfo = null;
        for (GoodsPiciInfoBeanResult pici : goodsPiciDataList) {
            if(!TextUtils.isEmpty(pici.getItem_barcode())){
                piciInfo = pici;//存在批次信息
                break;
            }
        }

        PandianDetailBeanResult obj = new PandianDetailBeanResult();
        obj.setItem_name(mScanOrSelectGoods.getItem_name());
        obj.setItem_no(mScanOrSelectGoods.getItem_no());
        obj.setBranch_no(mScanOrSelectGoods.getItem_no());
        obj.setItem_size(mScanOrSelectGoods.getItem_size());
        obj.setUnit_no(mScanOrSelectGoods.getUnit_no());
        obj.setIn_price(MyUtils.convertToFloat(mScanOrSelectGoods.getPrice(),0f));
        obj.setSale_price(MyUtils.convertToFloat(mScanOrSelectGoods.getSale_price(),0f));
        obj.setStock_qty(MyUtils.convertToInt(mScanOrSelectGoods.getStock_qty(),0));
        obj.setCheck_qty(1);
        obj.setBalance_qty(0);

        //单品盘点  选择的商品或扫描的商品没有批次可以填空 有批次就去取商品批次信息
        if(isDianpin){

            if(piciInfo == null){//有数据却批次都没有值
                AlertUtil.showToast("该商品不在盘点范围!");
                return;
            }

            obj.setProduce_date(piciInfo.getProduce_date());
            obj.setValid_date(piciInfo.getValid_date());
            obj.setItem_barcode(piciInfo.getItem_barcode());//批次号

            mAddPandianGoodsDetailData.add(obj);
            mAdapter.setListInfo(mAddPandianGoodsDetailData);
        }else{
            //其他盘点  选择的商品或扫描的商品没有批次不作处理 有批次就去取商品批次信息，
            // 如果返回的商品批次信息没有批次，则允许手动输入批次
            if(piciInfo != null){
                obj.setProduce_date(piciInfo.getProduce_date());
                obj.setValid_date(piciInfo.getValid_date());
                obj.setItem_barcode(piciInfo.getItem_barcode());//批次号
                boolean isSave = false;
                for (PandianDetailBeanResult entity : mPandianDetailData) {
                    if(mScanOrSelectGoods.getItem_no().equals(entity.getItem_no())){
                        isSave = true;
                        break;
                    }
                }

                if(isSave){
                    for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                        //如果之前存在 则自动加1
                        if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(mScanOrSelectGoods.getItem_no())){
                            int pdNumber = mAddPandianGoodsDetailData.get(i).getCheck_qty()+1;
                            mAddPandianGoodsDetailData.get(i).setCheck_qty(pdNumber);
                            mAdapter.setListInfo(mAddPandianGoodsDetailData);
                            return;
                        }
                    }
                    //不存时 添加到列表中
                    mAddPandianGoodsDetailData.add(obj);
                    mAdapter.setListInfo(mAddPandianGoodsDetailData);
                }else{
                    AlertUtil.showToast("该商品不在盘点范围!");
                }

            }else {
                //手动添加批次号
                Intent intent = new Intent(PandianScanActivity.this, ModifyGoodsPiciDialog.class);
                intent.putExtra("GoodsPici","");
                startActivityForResult(intent,11);
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
            //到时候要换成精准查询接口的
            mQueryGoodsApi.getPLUInfo(barcode);
        }
    }

    @Override
    protected boolean addBefore() {

        if(mAddPandianGoodsDetailData ==null || mAddPandianGoodsDetailData.size()==0){
            Toast.makeText(PandianScanActivity.this,"请扫描/添加盘点商品，再保存!",Toast.LENGTH_SHORT).show();
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
                            }
                        }, R.string.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertUtil.dismissDialog();
                            }
                        });
                return false;
            }
        }

        return true;
    }

    @Override
    protected void addAfter() {
//        在上传盘点单到后台保存前，获取盘点单号
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
            Toast.makeText(PandianScanActivity.this,"没有盘点商品，不能做删除操作!",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mSelectPandianDetailEntity ==null){
            Toast.makeText(PandianScanActivity.this,"请选择要删除的商品!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void deleteAfter() {
        for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
            if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(mSelectPandianDetailEntity.getItem_no())){
                mSelectPandianDetailEntity = null;
                mAddPandianGoodsDetailData.remove(i);
                mAdapter.setItemClickPosition(-1);
                mAdapter.setListInfo(mAddPandianGoodsDetailData);
                break;
            }
        }

    }

    @Override
    protected boolean modifyCountBefore() {
        if(mAddPandianGoodsDetailData ==null || mAddPandianGoodsDetailData.size()==0){
            Toast.makeText(PandianScanActivity.this,"没有盘点商品，不能做改数操作!",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mSelectPandianDetailEntity ==null){
            Toast.makeText(PandianScanActivity.this,"请选择要改数的商品!",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        mSelectPandianDetailEntity = mAddPandianGoodsDetailData.get(position);
        mAdapter.setItemClickPosition(position);
        mAdapter.notifyDataSetChanged();
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
                mPandianDetailData = (List<PandianDetailBeanResult>) o;
                break;
            case Config.MESSAGE_ERROR:
                break;
            //扫描时返回搜索的数据
            case Config.MESSAGE_GOODS_INFOR:
                mEtBarcode.requestFocus();
                mEtBarcode.setFocusable(true);
                mEtBarcode.setText("");
                List<GetClassPluResult> goodsData = (List<GetClassPluResult>) o;
                if(goodsData !=null && goodsData.size()>0){
                    //模糊查询 取第一条数据 （到时候要换成精准查询接口的）
                    addGoodsData(goodsData.get(0));
                }
                break;
            case Config.MESSAGE_GOODS_INFOR_FAIL:
                mEtBarcode.requestFocus();
                mEtBarcode.setFocusable(true);
                mEtBarcode.setText("");
                AlertUtil.showToast("不在盘点范围!");
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
            //上传记录头 成功 上传盘点明细
            case Config.MESSAGE_SUCCESS:
                AlertUtil.showToast(o.toString());
                uploadPandianDetailData();
                break;
            //上传记录头或上传盘点明细  失败
            case Config.MESSAGE_FAIL:
                AlertUtil.showToast(o.toString());
                break;
            //获取商品批次信息
            case Config.RESULT_SUCCESS:
                addGoodsPiciData((List<GoodsPiciInfoBeanResult>)o);
                break;
            case Config.RESULT_FAIL:

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Config.RESULT_SELECT_GOODS){
            List<GetClassPluResult> selectGoodsList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
            addGoodsData(selectGoodsList.get(0));
        }

        //手动输入批次（只要不是单品盘点 获取到的商品批次信息中没有批次的都进入到这里）
        if(requestCode == 11 && resultCode == RESULT_OK){
            String pici = data.getStringExtra("GoodsPici");

            PandianDetailBeanResult obj = new PandianDetailBeanResult();
            obj.setItem_name(mScanOrSelectGoods.getItem_name());
            obj.setItem_no(mScanOrSelectGoods.getItem_no());
            obj.setBranch_no(mPandianPihao.getBranch_name());
            obj.setItem_size(mScanOrSelectGoods.getItem_size());
            obj.setUnit_no(mScanOrSelectGoods.getUnit_no());
            obj.setIn_price(MyUtils.convertToFloat(mScanOrSelectGoods.getPrice(),0f));
            obj.setSale_price(MyUtils.convertToFloat(mScanOrSelectGoods.getSale_price(),0f));
            obj.setStock_qty(MyUtils.convertToInt(mScanOrSelectGoods.getStock_qty(),0));
            obj.setCheck_qty(1);
            obj.setBalance_qty(0);
            obj.setProduce_date("");
            obj.setValid_date("");
            obj.setItem_barcode(pici);//批次号

            //判断是否存在盘点明细中
            boolean isSave = false;
            for (PandianDetailBeanResult entity : mPandianDetailData) {
                if(mScanOrSelectGoods.getItem_no().equals(entity.getItem_no())){
                    isSave = true;
                    break;
                }
            }

            if(isSave){

                for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                    //如果之前存在 则自动加1
                    if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(mScanOrSelectGoods.getItem_no())){
                        int pdNumber = mAddPandianGoodsDetailData.get(i).getCheck_qty()+1;
                        mAddPandianGoodsDetailData.get(i).setCheck_qty(pdNumber);
                        mAdapter.setListInfo(mAddPandianGoodsDetailData);
                        return;
                    }
                }
                //不存时 添加到列表中
                mAddPandianGoodsDetailData.add(obj);
                mAdapter.setListInfo(mAddPandianGoodsDetailData);
            }else{
                AlertUtil.showToast("该商品不在盘点范围!");
            }


        }

        //修改数量
        if(requestCode == 22 && resultCode == RESULT_OK){
            String countN = data.getStringExtra("countN");
            for (int i = 0; i < mAddPandianGoodsDetailData.size(); i++) {
                if(mAddPandianGoodsDetailData.get(i).getItem_no().equals(mSelectPandianDetailEntity.getItem_no())){
                    mAddPandianGoodsDetailData.get(i).setCheck_qty(MyUtils.convertToInt(countN,0));
                    mAdapter.setListInfo(mAddPandianGoodsDetailData);
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddPandianGoodsDetailData.clear();
    }
}
