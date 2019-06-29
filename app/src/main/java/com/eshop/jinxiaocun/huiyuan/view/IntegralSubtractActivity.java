package com.eshop.jinxiaocun.huiyuan.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralSubtractBean;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.lingshou.bean.GetFlowNoBeanResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.utils.AidlUtil;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc:  积分冲减
 */

public class IntegralSubtractActivity extends CommonBaseActivity implements INetWorResult{

    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.tv_cardNumber)
    TextView mTvCardNumber;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_current_integral)
    TextView mTvCurrentIntegral;//当前积分
    @BindView(R.id.et_subtract_integral)
    EditText mEtSubtractIntegral;//冲减积分
    @BindView(R.id.et_remarks)
    EditText mEtRemarks;


    private IMemberList mApi;
    private ILingshouScan mApi2;
    private String mPassword;
    private float mIntegral;
    private int mAsType;
    private String mAccFlag;
    private boolean isStatusNormal;
    private boolean isSubtractIntegral;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_integral_subtract;
    }

    @Override
    protected void initView() {
        setTopToolBar("积分冲减", R.mipmap.ic_left_light,"",0,"");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                        AlertUtil.showToast("请输入卡号/手机号/姓名");
                        return false;
                    }
                    AlertUtil.showNoButtonProgressDialog(IntegralSubtractActivity.this,"正在读取卡信息，请稍后...");
                    mApi.getMemberCheckData(mEtSearch.getText().toString().trim());
                    hideSoftInput();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        mApi = new MemberImp(this);
        mApi2 = new LingShouScanImp(this);
    }

    private void refreshUIByData(MemberCheckResultItem data) {
        mPassword = data.getPassword();
        mAccFlag = data.getAcc_Flag();

        if(!TextUtils.isEmpty(data.getCardState())){
            //取出字符中的数字 [02]储值卡
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(data.getCardState());
            String cardStatusId = m.replaceAll("").trim();
            if("0".equals(cardStatusId)){
                isStatusNormal = true;
            }else {
                if(data.getCardState().contains("正常")){
                    isStatusNormal = true;
                }
            }
        }

        mEtSearch.setText("");
        mTvCardNumber.setText(data.getCardNo_TelNo());
        mTvName.setText(data.getCardName());
        mTvSex.setText(data.getVip_sex());
        mTvType.setText(data.getCardType());
        mTvStatus.setText(data.getCardState());
        mTvCurrentIntegral.setText(MyUtils.convertToString(data.getVip_accnum(), "0"));
    }

    //全部设置默认值
    private void reSetViewValues(){
        mTvCardNumber.setText("");
        mTvName.setText("");
        mTvSex.setText("");
        mTvType.setText("");
        mTvStatus.setText("");
        mTvCurrentIntegral.setText("");
        mEtSubtractIntegral.setText("");
        mEtRemarks.setText("");
    }

    @OnClick(R.id.iv_close)
    public void onClickClose(){
        mEtSearch.setText("");
    }

    //点击搜索按钮
    @OnClick(R.id.iv_search)
    public void onClickSearch(){
        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
            AlertUtil.showToast("请输入卡号/手机号/姓名");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在读取卡信息，请稍后...");
        mApi.getMemberCheckData(mEtSearch.getText().toString().trim());
        hideSoftInput();
    }


    //点击积分奖励按钮
    @OnClick(R.id.btn_integral_add)
    public void onClickIntegralAdd(){

        if(!isStatusNormal){
            AlertUtil.showToast("非正常状态卡，请检查！");
            return;
        }

        if("1".equals(mAccFlag)){
            AlertUtil.showToast("非积分卡，不能进行积分奖励！");
            return;
        }

        if(TextUtils.isEmpty(mEtSubtractIntegral.getText().toString().trim())){
            AlertUtil.showToast("请先输入冲减积分");
            return;
        }
        mIntegral = MyUtils.convertToFloat(mEtSubtractIntegral.getText().toString().trim(), 0);
        mAsType = 2;
        isSubtractIntegral = false;
        AlertUtil.showNoButtonProgressDialog(this,"奖励积分中，请稍后...");
        mApi2.getFlowNo();
    }

    //点击积分冲减按钮
    @OnClick(R.id.btn_integral_subtract)
    public void onClickIntegralSubtract(){

        if(!isStatusNormal){
            AlertUtil.showToast("非正常状态卡，请检查！");
            return;
        }

        if("1".equals(mAccFlag)){
            AlertUtil.showToast("非积分卡，不能进行积分冲减！");
            return;
        }

        if(TextUtils.isEmpty(mEtSubtractIntegral.getText().toString().trim())){
            AlertUtil.showToast("请先输入冲减积分");
            return;
        }
        if(MyUtils.convertToFloat(mTvCurrentIntegral.getText().toString().trim(), 0)<
                MyUtils.convertToFloat(mEtSubtractIntegral.getText().toString().trim(), 0)){
            AlertUtil.showToast("冲减积分不能大于当前积分");
            return;
        }
        mIntegral = MyUtils.convertToFloat(mEtSubtractIntegral.getText().toString().trim(), 0);
        mAsType = 3;
        isSubtractIntegral = true;
        AlertUtil.showNoButtonProgressDialog(this,"积分冲减中，请稍后...");
        mApi2.getFlowNo();
    }

    //as_type;//业务类型 --1：储值消费，2:积分奖励，3：积分冲减 ,4:储值冲正
    private void integralSubtract(int as_type ,float integral ,String flowNo){

        if(TextUtils.isEmpty(mTvCardNumber.getText().toString().trim())){
            AlertUtil.showToast("请先查询卡信息，再进行积分冲减/积分奖励");
            return;
        }

        IntegralSubtractBean bean = new IntegralSubtractBean();
        bean.JsonData.oper_id = Config.UserId;
        bean.JsonData.branch_no = Config.branch_no;
        bean.JsonData.as_vipNo = mTvCardNumber.getText().toString().trim();
        bean.JsonData.as_type = as_type;
        bean.JsonData.as_flow_no = flowNo;//小票号
        bean.JsonData.adec_consume_num =integral; //本单积分值
//        bean.JsonData.adec_consume_amt ="";  //本单消费金额  不用填写
//        bean.JsonData.adec_sav_amtnumeric ="";  //储值消费金额  不用填写
        bean.JsonData.as_card_pass = mPassword;//卡密码
        bean.JsonData.memo = mEtRemarks.getText().toString().trim();
        mApi.integralSubtract(bean);

    }



    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {

            case Config.MESSAGE_OK:
                AlertUtil.dismissProgressDialog();
                List<MemberCheckResultItem> data = (List<MemberCheckResultItem>) o;
                if (data != null && data.size()>0) {
                    refreshUIByData(data.get(0));
                } else {
                    AlertUtil.showToast("没有对应此卡号的信息！");
                }
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
            //取小票号
            case Config.MESSAGE_FLOW_NO:
                GetFlowNoBeanResult.FlowNoJson result = (GetFlowNoBeanResult.FlowNoJson)o;
                if(result != null ){
                    String flowNo = MyUtils.formatFlowNo(result.getFlowNo());
                    integralSubtract(mAsType,mIntegral,flowNo);
                }else{
                    AlertUtil.showToast("没有小票号信息");
                    AlertUtil.dismissProgressDialog();
                }
                break;
            case Config.RESULT_SUCCESS:
                AlertUtil.dismissProgressDialog();
                if(isSubtractIntegral){
                    AlertUtil.showToast("积分冲减成功！");
                }else{
                    AlertUtil.showToast("积分奖励成功！");
                }


                if(AidlUtil.getInstance().isConnect()){
                    AlertUtil.showAlert(this, R.string.recharge_success, R.string.is_need_print,
                            R.string.yes, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertUtil.dismissDialog();
                                    print();
                                }
                            }, R.string.no, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertUtil.dismissDialog();
                                    reSetViewValues();
                                }
                            });
                }else{
                    AlertDialog dialog = new AlertDialog.Builder(this).create();
                    dialog.setTitle("温馨提示");
                    dialog.setMessage("未连接打印机！");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            reSetViewValues();
                        }
                    });
                    dialog.show();
                }



                break;
            case Config.RESULT_FAIL:
                AlertUtil.dismissProgressDialog();
                if(isSubtractIntegral){
                    AlertUtil.showToast("积分冲减失败！");
                }else{
                    AlertUtil.showToast("积分奖励失败！");
                }
                break;
        }
    }

    //打印
    private void print(){
        int maxLength = 25;
        String title ;
        if(isSubtractIntegral){
            title = "积分冲减";
        }else{
            title = "积分奖励";
        }
        if(MyUtils.length(title)<maxLength){
            int beginLength = (maxLength-MyUtils.length(title))>>1;
            int endLength=beginLength;
            if(beginLength%2!=0){
                endLength+=1;
            }
            title = MyUtils.rpad(beginLength,"")+title+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(title+"\n",30f,false,false);
        }

        String userName = "操作人: "+ Config.UserName;
        if(MyUtils.length(userName)<maxLength){
            int endLength = maxLength-MyUtils.length(userName);
            userName=userName+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(userName+"\n",30f,false,false);
        }else{//如果超过一行  要换行
            AidlUtil.getInstance().printText(userName+"\n",30f,false,false);
            AidlUtil.getInstance().printEmptyLine(1);
        }

        String exchargeData = "充值时间: "+ DateUtility.getCurrentTime();
        if(MyUtils.length(exchargeData)<maxLength){
            int endLength = maxLength-MyUtils.length(exchargeData);
            exchargeData=exchargeData+MyUtils.rpad(endLength,"");
            AidlUtil.getInstance().printText(exchargeData+"\n",30f,false,false);
        }else{//如果超过一行  要换行
            AidlUtil.getInstance().printText(exchargeData+"\n",30f,false,false);
            AidlUtil.getInstance().printEmptyLine(1);
        }
        AidlUtil.getInstance().printText("-------------------------"+"\n",30f,false,false);

        float integral ;
        String subtractName;
        if(isSubtractIntegral){
            integral = MyUtils.convertToFloat(mTvCurrentIntegral.getText().toString().trim(), 0)
                    - MyUtils.convertToFloat(mEtSubtractIntegral.getText().toString().trim(), 0);

            subtractName = "冲减积分: ";
        }else{
            integral = MyUtils.convertToFloat(mTvCurrentIntegral.getText().toString().trim(), 0)
                    + MyUtils.convertToFloat(mEtSubtractIntegral.getText().toString().trim(), 0);

            subtractName = "奖励积分: ";
        }

        AidlUtil.getInstance().printText("卡积分: "+mTvCurrentIntegral.getText().toString().trim()+"\n",30f,false,false);
        AidlUtil.getInstance().printEmptyLine(1);

        String subtractIntegral = subtractName+mEtSubtractIntegral.getText().toString().trim();
        AidlUtil.getInstance().printText(subtractIntegral+"\n",30f,false,false);
        AidlUtil.getInstance().printEmptyLine(1);

        AidlUtil.getInstance().printText("卡当前积分: "+integral+"\n",30f,false,false);
        AidlUtil.getInstance().printEmptyLine(1);



        AidlUtil.getInstance().printText("-------------------------"+"\n",30f,false,false);

        if(!TextUtils.isEmpty(mEtRemarks.getText().toString().trim())){
            AidlUtil.getInstance().printText("备注: "+mEtRemarks.getText().toString().trim()+"\n",30f,false,false);
        }

        AidlUtil.getInstance().printEmptyLine(3);
        AidlUtil.getInstance().print();
        reSetViewValues();
    }


    /*隐藏软键盘*/
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mEtSearch.getApplicationWindowToken(), 0);
        }
    }
}
