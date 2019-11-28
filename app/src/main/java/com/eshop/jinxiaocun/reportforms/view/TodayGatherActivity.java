package com.eshop.jinxiaocun.reportforms.view;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.othermodel.bean.PayQueryBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.PayRecordResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.reportforms.adapter.TodayGatheringAdapter;
import com.eshop.jinxiaocun.reportforms.bean.TodayGatheringInfo;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;
import com.landicorp.android.eptapi.device.Printer;
import com.landicorp.android.eptapi.exception.RequestException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/12
 * Desc: 今日收款
 */
public class TodayGatherActivity extends CommonBaseActivity implements INetWorResult {

    @BindView(R.id.et_billNo)
    EditText mEtBillNo;

    @BindView(R.id.lv_cashierData)
    RefreshListView mListView;

    private IOtherModel mServerApi;
    private TodayGatheringAdapter mAdapter;
    private List<TodayGatheringInfo> mDataList = new ArrayList<>();
    private int mTotPage=0;//总页数
    private int mPageNum=1;
    private int mPerNum = MyUtils.convertToInt(Config.PerNum,50);
    private final String mStartDate = DateUtility.getCurrentDate()+" 00:00";
    private final String mEndDate = DateUtility.getCurrentDate()+" 23:59";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_today_gathering;
    }


    //初始化控件
    @Override
    protected void initView() {
        setTopToolBar("今日收款", R.mipmap.ic_left_light, "", 0, "");

        mEtBillNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideSoftInput();
                    onClickSearch();
                    return true;
                }
                return false;
            }
        });

        mAdapter = new TodayGatheringAdapter(this,mDataList);
        mAdapter.setCallbck(new TodayGatheringAdapter.PrintCallback() {
            @Override
            public void onClickBillNo(TodayGatheringInfo item) {
                onPrintData(item);
            }
        });
        mListView.setAdapter(mAdapter);

        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNum = 1;
                onClickSearch();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mPageNum ++;
                if(mPageNum>mTotPage){
                    mListView.onRefreshComplete();
                    ToastUtils.showShort("已是最后一页");
                    return;
                }
                onClickSearch();
            }
        });

    }

    //初始化数据和网络接口
    @Override
    protected void initData() {
        mServerApi = new OtherModelImp(this);
    }

    //搜索
    @OnClick(R.id.ib_search)
    public void onClickSearch(){
        if(TextUtils.isEmpty(mEtBillNo.getText().toString().trim())){
            AlertUtil.showToast("请输入单据！");
            return;
        }

        hideSoftInput();
        loadData(mEtBillNo.getText().toString().trim());

    }


    //加载数据
    private void loadData(String billNo){
        if(!MyUtils.isOpenNetwork()){
            AlertUtil.showToast("网络不可用，请检查网络!");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在获取收款数据，请稍后...");
        mServerApi.getPayQuery(mStartDate,mEndDate,billNo,mPerNum,mPageNum);
    }

    //根据返回的销售记录，单据相同算一组，并刷新UI
    private void refreshData(List<PayQueryBeanResult> dataList){
        mListView.onRefreshComplete();

        if(dataList==null){
            mDataList.clear();
            mAdapter.add(mDataList);
            return;
        }

        if(mPageNum==1 && dataList.size()==0){
            ToastUtils.showShort("今日暂时没有付款记录数据!");
            return;
        }

        //每次刷新都清除原来的数据
        if(mPageNum==1){
            mDataList.clear();
        }

        if(dataList.size()>0){//获取总页数
            mTotPage = dataList.get(0).getTotpage();
        }

        //缓存不相同的单据号
        Set<String> billNoList = new HashSet<>();
        for (PayQueryBeanResult item : dataList) {
            billNoList.add(item.getFlow_no());
        }

        //合并相同的单据商品
        for (String billNo : billNoList) {
            List<PayQueryBeanResult> sameGoodsList = new ArrayList<>();
            for (PayQueryBeanResult item : dataList) {
                if(billNo.equals(item.getFlow_no())){
                    sameGoodsList.add(item);
                }
            }
            if(sameGoodsList.size()>0){
                TodayGatheringInfo info = new TodayGatheringInfo();
                info.setBillNo(sameGoodsList.get(0).getFlow_no());
                info.setBillDate(sameGoodsList.get(0).getOper_date());
                info.setPayRecordInfos(sameGoodsList);
                mDataList.add(info);
            }
        }
        //刷新UI
        mAdapter.add(mDataList);

    }

    /*隐藏软键盘*/
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mEtBillNo.getApplicationWindowToken(), 0);
        }
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_OK://获取单据的付款记录
                List<PayQueryBeanResult> resultList = (List<PayQueryBeanResult>) o;
                refreshData(resultList);
                AlertUtil.dismissProgressDialog();
                break;
            case Config.RESULT_FAIL:
                AlertUtil.showToast((String) o);
                AlertUtil.dismissProgressDialog();
                break;

        }
    }

    //打印小票
    private void onPrintData(TodayGatheringInfo data){
        Print_Ex( data);
    }

    /**
     * 打印自定义小票
     */
    @SuppressLint("SimpleDateFormat")
    private void Print_Ex(TodayGatheringInfo data) {
        if( !BluetoothAdapter.getDefaultAdapter().isEnabled()){
            return;
        }

        int maxLength = 25;
        String title = "收款重打";
        if(MyUtils.length(title)<maxLength){
            int beginLength = (maxLength-MyUtils.length(title))>>1;
            int endLength=beginLength;
            if(beginLength%2!=0){
                endLength+=1;
            }
            title = MyUtils.rpad(beginLength,"")+title+MyUtils.rpad(endLength,"");
        }


        for(int j=0; j<Integer.decode(Config.mPrintNumber); j++){
            int shuliang = 0;
            String mes = title+"\n\n\n";

            if(!Config.mPrintPageHeader.equals("")){mes += Config.mPrintPageHeader+"\n";}
            if(!Config.mPrintOrderName.equals("")){mes += Config.mPrintOrderName+"\n";}
            if(Config.isPrinterCashier){mes += "收银员："+Config.UserName+"\n";}
            if(Config.mMemberInfo != null){
                if(Config.isPrinterCardNo){mes += "会员卡号："+Config.mMemberInfo.getCardNo_TelNo()+"\n";}
                if(Config.isPrinterUserName){mes += "会员姓名："+Config.mMemberInfo.getCardName()+"\n";}
                if(Config.isPrinterUserTel){mes += "客户联系方式："+Config.mMemberInfo.getVip_tel()+" "+Config.mMemberInfo.getMobile()+"\n";}
            }
            mes += "门店号: "+Config.posid+"\n单据："+data.getBillNo()+"\n";
            mes += "小票号    付款方式     付款金额     日期时间\n";
            mes += "-------------------------------\n";

            for(int i=0; i<data.getPayRecordInfos().size(); i++){
                PayQueryBeanResult mPayQueryBeanResult = data.getPayRecordInfos().get(i);
                mes += mPayQueryBeanResult.getFlow_no()+"\n"+
                        "        "+mPayQueryBeanResult.getPay_way_name()+"     "+
                        MyUtils.formatDouble2(mPayQueryBeanResult.getPay_amount())+"元    "+
                        mPayQueryBeanResult.getOper_date()+"\n";
            }
            mes += "-------------------------------\n";
            mes += "打印时间："+DateUtility.getCurrentTime()+"\n";

            if(!Config.mPrintPageFoot.equals("")){mes += "    "+Config.mPrintPageFoot+"\n";}

            mes += "\n";
            mes += "\n";
            //AidlUtil.getInstance().printText(mes, 24, false, false);
            print(mes);
        }

    }

    /**
     * POS 签购单打印
     */
    public void print(final String mes){
        /** 1、创建 Printer.Progress 实例 */
        Printer.Progress progress = new Printer.Progress() {
            /** 2、在 Printer.Progress 的 doPrint 方法中设置签购单的打印样式 */
            @Override
            public void doPrint(Printer printer) throws Exception {
                /** 设置打印格式 */
                Printer.Format format = new Printer.Format();
                /** 中文字符打印,此处使用 16x16 点,1 倍宽&&1 倍高
                 */
                format.setHzScale(Printer.Format.HZ_SC1x1);
                format.setHzSize(Printer.Format.HZ_DOT24x24);
                printer.setFormat(format);
                printer.printText(mes);
                /** 进纸 2 行
                 */
                printer.feedLine(2);
            }
            @Override
            public void onFinish(int code) {
                /** Printer.ERROR_NONE 即打印成功 */
                if (code == Printer.ERROR_NONE) {
                    AlertUtil.showToast("打印成功!");
                }
                else {
                    AlertUtil.showToast("[打印失败]"+getErrorDescription(code));
                }
            }
            /** 根据错误码获取相应错误提示
             * @param code 错误码
             * @return 错误提示
             */
            public String getErrorDescription(int code) {
                switch(code) {
                    case Printer.ERROR_PAPERENDED: return "Paper-out, the operation is invalid this time";
                    case Printer.ERROR_HARDERR: return "Hardware fault, can not find HP signal";
                    case Printer.ERROR_OVERHEAT: return "Overheat";
                    case Printer.ERROR_BUFOVERFLOW: return "The operation buffer mode position is out of range";
                    case Printer.ERROR_LOWVOL: return "Low voltage protect";
                    case Printer.ERROR_PAPERENDING: return "Paper-out, permit the latter operation";
                    case Printer.ERROR_MOTORERR: return "The printer core fault (too fast or too slow)";
                    case Printer.ERROR_PENOFOUND: return "Automatic positioning did not find the alignment position, the paper back to its original position";
                    case Printer.ERROR_PAPERJAM: return "paper got jammed";
                    case Printer.ERROR_NOBM: return "Black mark not found";
                    case Printer.ERROR_BUSY: return "The printer is busy";
                    case Printer.ERROR_BMBLACK: return "Black label detection to black signal";
                    case Printer.ERROR_WORKON: return "The printer power is open";
                    case Printer.ERROR_LIFTHEAD: return "Printer head lift";
                    case Printer.ERROR_LOWTEMP: return "Low temperature protect";
                }
                return "unknown error ("+code+")";
            }
            @Override
            public void onCrash() {
            }
        };
        /** 3、启动打印 */
        try {
            progress.start();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }


}
