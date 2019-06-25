package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.base.view.QreShanpingActivity;
import com.eshop.jinxiaocun.huiyuan.view.MemberCheckActivity;
import com.eshop.jinxiaocun.jichi.JichiActivity;
import com.eshop.jinxiaocun.lingshou.bean.GetOptAuthResult;
import com.eshop.jinxiaocun.lingshou.bean.GetPluPriceBeanResult;
import com.eshop.jinxiaocun.lingshou.bean.PlayFlowBean;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBean;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.NfcUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.DanPinZheKouCreatDialog;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;
import com.eshop.jinxiaocun.widget.PiChiDialog;
import com.zxing.android.CaptureActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LingShouCreatAtivity extends BaseLinShouCreatActivity implements INetWorResult {

    @BindView(R.id.et_barcode)
    EditText et_barcode;
    @BindView(R.id.btn_add)
    ImageView btSell;//销售
    @BindView(R.id.btn_delete)
    ImageView btn_delete;//删除
    @BindView(R.id.btn_modify_count)
    ImageView btn_modify_count;//改数
    @BindView(R.id.tv_check_num)
    TextView tv_check_num;//总数
    @BindView(R.id.ly_buttom1)
    LinearLayout ly_buttom1;
    @BindView(R.id.btn_yijia)
    Button btn_yijia;//议价
    @BindView(R.id.btn_zhekou)
    Button btn_zhekou;//折扣
    @BindView(R.id.tv_total_num)
    TextView tv_total_num;//商品数
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;//记录数
    @BindView(R.id.ib_seach)
    ImageButton ib_seach;
    @BindView(R.id.btn_vip)
    Button btn_vip;
    @BindView(R.id.bt_next)
    Button bt_next;
    @BindView(R.id.iv_scan)
    ImageView iv_scan;

    private List<GetClassPluResult> mListData = new ArrayList<>();
    private ILingshouScan mLingShouScanImp;
    private IOtherModel mIOtherModel;
    protected List<SaleFlowBean> mSaleFlowBeanList;
    protected List<PlayFlowBean> mPlayFlowBeanList;
    private Double total = 0.00;
    private List<GetPluPriceBeanResult> mGetPluPriceBeanResult;
    private List<GetClassPluResult> mGetClassPluResultList;
    private Double change = 0.0;
    private Double payMoney = 0.0;
    private GetOptAuthResult mGetOptAuthResult = null;
    private boolean hasDiscount = false;
    private static boolean is58mm = true;
    private String Pay_way = "";
    private String Pay_way2 = "";
    private boolean isVipPay = false;
    private FinishReceiver recevier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout.setBackgroundColor(getResources().getColor(R.color.item_gray_line));
        setScanBroadCast();
    }

    private void setScanBroadCast() {
        Intent intent = new Intent("com.android.scanner.service_settings");
        intent.putExtra("action_barcode_broadcast", "com.android.server.scannerservice.broadcast");
        intent.putExtra("key_barcode_broadcast", "scannerdata");
        sendBroadcast(intent);
        IntentFilter intentFilter = new IntentFilter("com.android.server.scannerservice.broadcast");
        registerReceiver(scanReceiver, intentFilter);

        recevier = new FinishReceiver();
        IntentFilter intentFilter2 = new IntentFilter("com.example.mymessage");
        //当网络发生变化的时候，系统广播会发出值为android.net.conn.CONNECTIVITY_CHANGE这样的一条广播
        registerReceiver(recevier, intentFilter2);
    }

    private BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String barcode = intent.getStringExtra("scannerdata");
            Log.e("", "--" + barcode);
            et_barcode.setText(barcode);
        }
    };

    @SuppressLint("WrongViewCast")
    @Override
    protected void initView() {
        super.initView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View mView = this.getLayoutInflater().inflate(R.layout.activity_lingshou, null);
        mLinearLayout.addView(mView, 0, params);
        ButterKnife.bind(this);
        //btSell.setText(R.string.bt_sell);

        mMyActionBar.setRightTitleAndStyle("\t\t\t", R.drawable.member_card);
        et_barcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false;
                }
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == 0
                        || actionId == EditorInfo.IME_ACTION_GO || actionId == 6) { /*判断是否是“GO”键*/
                    mLingShouScanImp.getPLUInfo(v.getText().toString().trim());
                    /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager.isActive()) {
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
                }

                return false;
            }
        });
        et_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(LingShouCreatAtivity.this, QreShanpingActivity.class);
                if(mGetClassPluResultList!=null){

                    mGetClassPluResultList.clear();
                    mGetClassPluResultList.addAll(mListData);
                }
                mIntent.putExtra("selectList", (Serializable) mGetClassPluResultList);
                mIntent.putExtra("lingshou",true);
                startActivityForResult(mIntent, 100);
            }
        });

        ib_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLingShouScanImp.getPLUInfo(et_barcode.getText().toString().trim());
                /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) LingShouCreatAtivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(et_barcode.getApplicationWindowToken(), 0);
                }
            }
        });

        if ((ArrayList<GetClassPluResult>) getIntent().getSerializableExtra("mListData") != null) {
            mListData = (ArrayList<GetClassPluResult>) getIntent().getSerializableExtra("mListData");
        }
        mScanAdapter = new LingShouCreatAdapter(mListData);
        reflashList();
        mListview.setAdapter(mScanAdapter);
        mScanAdapter.notifyDataSetChanged();
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemClickPosition = i;
                mScanAdapter.setItemClickPosition(i);
                mScanAdapter.notifyDataSetInvalidated();
                btn_zhekou(mListData.get(i));
            }
        });

        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LingShouCreatAtivity.this, CaptureActivity.class);
                startActivityForResult(intent, Config.REQ_QR_CODE);
            }
        });
    }

    void btn_zhekou(GetClassPluResult mGetClassPluResult) {
        try {
            if (mScanAdapter.getItemClickPosition() == -1) {
                ToastUtils.showShort("请选择商品");
                return;
            }
            GetClassPluResult item = mGetClassPluResult;
//            if( !canModifyPrice(item))return;
            Intent intent = new Intent(this, DanPinZheKouCreatDialog.class);
            intent.putExtra("oldPrice", Double.parseDouble(item.getSale_price()));
            intent.putExtra("count", item.getSale_qnty());
            intent.putExtra("GetClassPluResult",item);
            if (!item.getEnable_discount().equals("1")) {
                ToastUtils.showShort("此商品不允许打折。");
                intent.putExtra("limit", Config.danbiZheKoulimit);
            }
            if(item.getChange_price().equals("1")){
                ToastUtils.showShort("此商品不允许议价。");
                intent.putExtra("yijialimit",Config.danbiYiJialimit);
            }
            startActivityForResult(intent, 100);
        } catch (Exception e) {
            Log.e("", "");

        }

    }

    @OnClick(R.id.btn_modify_count)
    void modifyCount() {
        if (mScanAdapter.getItemClickPosition() == -1) {
            ToastUtils.showShort("请选择商品");
            return;
        }
        GetClassPluResult mGetClassPluResult = mListData.get(itemClickPosition);
        Intent intent = new Intent();
        intent.putExtra("countN", mGetClassPluResult.getSale_qnty());
        intent.setClass(this, ModifyCountDialog.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mSaleFlowBeanList = new ArrayList<>();
        mPlayFlowBeanList = new ArrayList<>();
        mLingShouScanImp = new LingShouScanImp(this);
        mIOtherModel = new OtherModelImp(this);
        mLingShouScanImp.getOptAuth(Config.GRANT_ITEM_JINE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Config.REQ_QR_CODE && data != null) {
            String codedContent = data.getStringExtra("codedContent");
            if(codedContent != null && !codedContent.equals("")){
                et_barcode.setText(codedContent);
                scanData(codedContent);
            }
            return;
        }
        double temprice;
        switch (resultCode) {
            case Config.RESULT_SELECT_GOODS:
                mGetClassPluResultList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");

               /* if(mGetClassPluResultList.size()==1){
                    //一个商品判断批次，返回变成多个商品，暂时不判断批次
                    if (Integer.decode(mGetClassPluResultList.get(0).getEnable_batch()) == 1) {
                        getPiCi(mGetClassPluResultList);
                    } else {
                        mGetClassPluResultList.get(0).setItem_barcode("");
                        addListData();
                        reflashList();
                    }
                }else{*/
                    for(int i=0; i<mGetClassPluResultList.size(); i++){
                        mGetClassPluResultList.get(i).setItem_barcode("");
                    }
                    mListData.clear();
                    addListData();
                    reflashList();
                //}


                break;
            case Config.RESULT_SELECT_GOODS_QUERY:
                mGetClassPluResultList = (List<GetClassPluResult>) data.getSerializableExtra("SelectList");
                for(int i=0; i<mGetClassPluResultList.size(); i++){
                    mGetClassPluResultList.get(i).setItem_barcode("");
                }
                addListData();
                reflashList();
                break;
            case Config.MESSAGE_INTENT_ZHEKOU:
                String newPrice = data.getStringExtra("countN");
                int zhekou = data.getIntExtra("zhekou",100);
                String shuliang = data.getStringExtra("count");
                String price = data.getStringExtra("price");
                GetClassPluResult mClass = mListData.get(itemClickPosition);
//                temprice = Double.valueOf(mClass.getSale_price()) * Double.valueOf(zhekou);
                if (shuliang.equals("0")) {
                    mListData.remove(itemClickPosition);
                    reflashList();
                    return;
                }
                mClass.setZhekou(zhekou);
                mClass.setSale_price(newPrice);
                if (shuliang != null && !shuliang.equals("")) {
                    mClass.setSale_qnty(shuliang);
                }
                mClass.setHasYiJia(true);
                reflashList();
                break;
            case RESULT_OK:
                String mCount = data.getStringExtra("countN");
                GetClassPluResult item = getSelectObject();
                item.setSale_qnty(mCount);
                reflashList();
                break;
            case Config.PICHI_SELECT_DIALOG:
                GoodsPiciInfoBeanResult mGoodsPiciInfoBeanResult = (GoodsPiciInfoBeanResult) data.getSerializableExtra("pichi");
                GetClassPluResult mGetClassPluResult = mListData.get(mListData.size() - 1);
                mGetClassPluResult.setItem_barcode(mGoodsPiciInfoBeanResult.getItem_barcode());
                mGetClassPluResult.setProduce_date(mGoodsPiciInfoBeanResult.getProduce_date());
                mGetClassPluResult.setValid_date(mGoodsPiciInfoBeanResult.getValid_date());
                reflashList();
                break;

        }
    }

    private GetClassPluResult getSelectObject() {
        int itemClickPosition = mScanAdapter.getItemClickPosition();
        GetClassPluResult item = mListData.get(itemClickPosition);
        return item;
    }

    private void reflashList() {
        mScanAdapter.notifyDataSetChanged();
        total = 0.0;
        int goodTotal = 0;
        for (int i = 0; i < mListData.size(); i++) {
            GetClassPluResult mGetClassPluResult = mListData.get(i);
            total += (Double.parseDouble(mGetClassPluResult.getSale_price()) * Double.parseDouble(mGetClassPluResult.getSale_qnty()));
            goodTotal += Integer.decode(mGetClassPluResult.getSale_qnty());
        }
        String totalStr = MyUtils.formatDouble2(total) + "";
        if ((totalStr.length() - totalStr.indexOf(".")) > 3) {
            totalStr = totalStr.substring(0, totalStr.indexOf(".") + 4);
        }
        tv_check_num.setText("总金额：" + totalStr);
        tv_total_num.setText(goodTotal+"");
        tv_order_num.setText("记录数：" + mListData.size());
    }

    private void addListData() {
        for (int i = 0; i < mListData.size(); i++) {
            for (int j = 0; j < mGetClassPluResultList.size(); j++) {
                if (mListData.get(i).getItem_no().trim().equalsIgnoreCase(mGetClassPluResultList.get(j).getItem_no().trim())) {
                    int qnty1 = Integer.decode(mListData.get(i).getSale_qnty());
                    int qnty2 = Integer.decode(mGetClassPluResultList.get(j).getSale_qnty());
                    mListData.get(i).setSale_qnty(qnty1+qnty2 + "");
                    mGetClassPluResultList.remove(j);
                    break;
                }
            }
        }
        for (int i = 0; i < mGetClassPluResultList.size(); i++) {
            mGetClassPluResultList.get(i).setSale_price_beforModify(mGetClassPluResultList.get(i).getSale_price());
        }
        mListData.addAll(mGetClassPluResultList);
    }

    private void getPiCi(List<GetClassPluResult> mGetClassPluResult) {
        GoodsPiciInfoBean mGoodsPiciInfoBean = new GoodsPiciInfoBean();
        mGoodsPiciInfoBean.JsonData.as_branchNo = Config.branch_no;
        mGoodsPiciInfoBean.JsonData.as_posid = Config.posid;
        mGoodsPiciInfoBean.JsonData.as_item_no = mGetClassPluResult.get(0).getItem_no();
        mIOtherModel.getGoodsPiciInfo(mGoodsPiciInfoBean);
    }

    @Override
    public void handleResule(int flag, Object o) {
        Intent intent;
        switch (flag) {
            case Config.MESSAGE_OK:
                break;
            case Config.MESSAGE_GOODS_INFOR_FAIL:
                AlertUtil.showAlert(LingShouCreatAtivity.this, "提示", (String) o);
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.showAlert(LingShouCreatAtivity.this, "提示", "请求失败");
                break;
            case Config.MESSAGE_PICI:
                List<GoodsPiciInfoBeanResult> mGoodsPiciInfoBeanResult = (List<GoodsPiciInfoBeanResult>) o;
                if(mGoodsPiciInfoBeanResult.size()>1){
                    Intent mIntent = new Intent(LingShouCreatAtivity.this, PiChiDialog.class);
                    startActivityForResult(mIntent,0);
                }else{
                    GetClassPluResult mGetClassPluResult = mListData.get(mListData.size() - 1);
                    mGetClassPluResult.setItem_barcode(mGoodsPiciInfoBeanResult.get(0).getItem_barcode());
                    mGetClassPluResult.setProduce_date(mGoodsPiciInfoBeanResult.get(0).getProduce_date());
                    mGetClassPluResult.setValid_date(mGoodsPiciInfoBeanResult.get(0).getValid_date());
                    reflashList();
                }
                break;
            case Config.MESSAGE_GET_OPT_AUTH:
                mGetOptAuthResult = (GetOptAuthResult) o;
                break;
            case Config.MESSAGE_GOODS_INFOR:
                mGetClassPluResultList = (List<GetClassPluResult>) o;
                if (mGetClassPluResultList != null && mGetClassPluResultList.size() == 0) {
                    AlertUtil.showAlert(LingShouCreatAtivity.this, "提示", "没有商品");
                    et_barcode.setText("");
                    return;
                }
                if (mGetClassPluResultList != null && mGetClassPluResultList.size() > 1) {
                    intent = new Intent(this, QreShanpingActivity.class);
                    intent.putExtra("barcode", et_barcode.getText().toString());
                    startActivityForResult(intent, 100);
                } else {
                    if (Integer.decode(mGetClassPluResultList.get(0).getEnable_batch()) == 1) {
                        getPiCi(mGetClassPluResultList);
                    } else {
                        mGetClassPluResultList.get(0).setItem_barcode("");//设置批次空
                        addListData();
                        //reflashList();
                    }
                    // setSaleFlowBean();
                }
                break;
        }
    }

    @Override
    public void onLeftClick() {
        super.onLeftClick();
        AlertUtil.showAlert(this,
                R.string.dialog_title,
                R.string.mess_back,
                R.string.confirm,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertUtil.dismissDialog();
                        finish();
                    }
                },
                R.string.cancel,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertUtil.dismissDialog();
                    }
                }
        );
    }

    @Override
    public void onRightClick() {
//        Intent mIntent = new Intent(this, QreShanpingActivity.class);
//        startActivityForResult(mIntent,100);
        //结算完成时记录Config.mMemberInfo赋null
        startActivity(new Intent(this, SaveMemberActivity.class));
    }

    @OnClick(R.id.bt_next)
    void sell() {
        if (mListData == null || mListData.size() == 0) {
            ToastUtils.showShort("请选择商品");
            return;
        }
        Intent mIntent = new Intent(this, LingShouScanActivity.class);
        mIntent.putExtra("mListData", (Serializable) mListData);
        startActivity(mIntent);
        //startActivityForResult(mIntent,300);
    }

    @OnClick(R.id.btn_delete)
    void delete() {
        try {
            if (mScanAdapter.getItemClickPosition() == -1) {
                ToastUtils.showShort("请选择商品");
                return;
            }
            mListData.remove(itemClickPosition);
            reflashList();
        } catch (Exception e) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Config.mMemberInfo = null;
        unregisterReceiver(recevier);
    }

    public class FinishReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == "com.example.mymessage") {
                finish();
            }
        }
    }

    //接收条码
    @Override
    protected void scanData(String barcode) {
        if(et_barcode!=null ){
            et_barcode.selectAll();
            et_barcode.setText(barcode);
        }
        mLingShouScanImp.getPLUInfo(barcode);
    }

}
