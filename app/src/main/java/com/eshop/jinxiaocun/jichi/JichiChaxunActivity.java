package com.eshop.jinxiaocun.jichi;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class JichiChaxunActivity extends CommonBaseActivity implements INetWorResult {


    @BindView(R.id.et_search)
    public EditText mEtSearch;
    @BindView(R.id.tv_cardNumber)
    public TextView mTvCardNumber;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_card_type)
    TextView mTvCardType;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;//余额
    @BindView(R.id.tv_current_integral)
    TextView mTvCurrentIntegral;// 当前积分

    private IMemberList mApi;
    public List<MemberCheckResultItem> data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_jichi_chaxun;
    }

    @Override
    protected void initView() {
        mApi = new MemberImp(this);
        if(Config.mMemberInfo!=null){
            refreshUIByData(Config.mMemberInfo);
        }

        setTopToolBar("计次查询", R.mipmap.ic_left_light, "", 0, "");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                        AlertUtil.showToast("请输入卡号/手机号/姓名");
                        return false;
                    }
                    AlertUtil.showNoButtonProgressDialog(JichiChaxunActivity.this,"正在读取卡信息，请稍后...");
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
    }

    private void refreshUIByData(MemberCheckResultItem data) {
        mEtSearch.setText("");
        mTvCardNumber.setText(data.getCardNo_TelNo());
        mTvName.setText(data.getCardName());
        mTvCardType.setText(data.getCardType());
        mTvStatus.setText(data.getCardState());
        mTvBalance.setText(MyUtils.convertToString(data.getResidual_amt(), "0"));
        mTvCurrentIntegral.setText(MyUtils.convertToString(data.getVip_accnum(), "0"));
    }

    @OnClick(R.id.iv_close)
    public void onClickClose(){
        mEtSearch.setText("");
    }

    //搜索
    @OnClick(R.id.iv_search)
    public void onClickSearch() {
        if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
            AlertUtil.showToast("请输入卡号/手机号/姓名");
            return;
        }
        AlertUtil.showNoButtonProgressDialog(this,"正在读取卡信息，请稍后...");
        mApi.getMemberCheckData(mEtSearch.getText().toString().trim());
        hideSoftInput();
    }



    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {

            case Config.MESSAGE_OK:
                AlertUtil.dismissProgressDialog();
                data = (List<MemberCheckResultItem>) o;
                if (data != null && data.size()>0) {
                    //Config.mMemberInfo=data.get(0);//记录最近一次会员信息  供销售结算时使用
                    refreshUIByData(data.get(0));
                } else {
                    AlertUtil.showToast("没有对应此卡号的信息！");
                }
                break;
            case Config.MESSAGE_ERROR:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast(o.toString());
                break;
        }
    }

    /*隐藏软键盘*/
    private void hideSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(mEtSearch.getApplicationWindowToken(), 0);
        }
    }
}
