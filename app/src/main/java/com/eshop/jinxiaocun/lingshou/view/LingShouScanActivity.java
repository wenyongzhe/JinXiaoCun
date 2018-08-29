package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.BillType;
import com.eshop.jinxiaocun.base.bean.UpDetailBean;
import com.eshop.jinxiaocun.base.view.BaseScanActivity;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuDetailBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IXiaoShouScan;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.PiFaXiaoShouScanImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LingShouScanActivity extends BaseScanActivity implements INetWorResult {

    @BindView(R.id.ly1_sp)
    private Spinner mSpinner1;
    @BindView(R.id.ly2_sp)
    private Spinner mSpinner2;
    @BindView(R.id.ly3_sp)
    private Spinner mSpinner3;

    private LinearLayout ly_kaidan;
    private ILingshouScan mLingShouScanImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        loadData();
        initView();
    }

    //接收条码
    @Override
    protected void scanData(String barcode) {
        mLingShouScanImp.getPLUInfo(barcode);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mLingShouScanImp = new LingShouScanImp(this);
        if(!newSheet){
            mLingShouScanImp.getSheetDetail(sheet_no);
        }else
            initMainBean();
        mLingShouScanImp.getFlowNo();
    }

    private void initMainBean(){
        mUpMainBean.setFlow_ID(MyUtils.getCurrentTime());//时间流水ID
        mUpMainBean.setSheetType(BillType.SO+"");//单据类型-批发销售
        mUpMainBean.setSheet_No("");//单号
        mUpMainBean.setBranch_No(Config.branch_no);//当前仓库
        mUpMainBean.setOrd_Man_No(Config.UserName);//业务员
        mUpMainBean.setPDA_No(Config.DeviceID);//PDA_No”:””	//PDA机号
        mUpMainBean.setUSER_ID(Config.DeviceID);//USER_ID”:””       	//用户ID
        mUpMainBean.setOper_Date(MyUtils.getCurrentTime());///Oper_Date”:”” 	//操作日期

        mUpMainBean.setPayment("");//Payment”:”” //付款方式
        mUpMainBean.setOrd_Amt("");//Ord_Amt”:””             //定金
    }

    private void setDetailBean(UpDetailBean mUpDetailBean){
        mUpDetailBean.setFlow_ID(MyUtils.getCurrentTime());//时间流水ID
        mUpDetailBean.setPOSID(Config.DeviceID);
        mUpDetailBean.setBillNo("");//BillNo”:”” //单据号
        mUpDetailBean.setSupplyCode("");//SupplyCode

        mUpDetailBeanList.add(mUpDetailBean);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View mView = this.getLayoutInflater().inflate(R.layout.activity_add_xiaoshou_dan, null);
        mLinearLayout.addView(mView,0,params);
        mSpinner1 = findViewById(R.id.ly1_sp);
        mSpinner2 = findViewById(R.id.ly2_sp);
        mSpinner3 = findViewById(R.id.ly3_sp);


        setHeaderTitle(R.id.tv_0, R.string.list_item_ProdName, 150);
        setHeaderTitle(R.id.tv_1, R.string.list_item_CountN5, 100);
        setHeaderTitle(R.id.tv_2, R.string.list_item_BarCode, 180);


        List<String> list = new ArrayList<>();
        list.add("正品");
        list.add("赠品");
        list.add("促销品");
        list.add("不良品");
        ArrayAdapter<String> mTuiHupoAdapter = new ArrayAdapter<>(LingShouScanActivity.this, R.layout.my_simple_spinner_item, list);
        mTuiHupoAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
        mSpinner1.setAdapter(mTuiHupoAdapter);
        mSpinner2.setAdapter(mTuiHupoAdapter);
        mSpinner3.setAdapter(mTuiHupoAdapter);
    }

    @Override
    public void handleResule(int flag,Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_ERROR:
                break;
            case Config.MESSAGE_GOODS_INFOR:
                GoodGetBeanResult mGoodGetBeanResult = (GoodGetBeanResult)o;
                UpDetailBean mUpDetailBean = new UpDetailBean();
                mUpDetailBean.setBarCode(mGoodGetBeanResult.JsonData.get(0).item_no);//条码
                mUpDetailBean.setBuyPrice(mGoodGetBeanResult.JsonData.get(0).Price);//进价
                mUpDetailBean.setSalePrice(mGoodGetBeanResult.JsonData.get(0).sale_price);//售价
                setDetailBean(mUpDetailBean);
                setViewData(mGoodGetBeanResult);
                break;
            case Config.MESSAGE_FLOW_NO:
                GetFlowNoBeanResult mGetFlowNoBeanResult = (GetFlowNoBeanResult)o;
                if(mGetFlowNoBeanResult.getJsonData() != null && mGetFlowNoBeanResult.getJsonData().size()>0){
                    GetFlowNoBeanResult.FlowNoJson json = mGetFlowNoBeanResult.getJsonData().get(0);
                }

                break;
        }
    }

    /*
    更新界面数据
     */
    private void setViewData(GoodGetBeanResult mGoodGetBeanResult) {

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}