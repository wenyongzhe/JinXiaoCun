package com.eshop.jinxiaocun.jichi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.MemberCheckResultItem;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.lingshou.view.SaveMemberActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.NfcUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.zxing.android.CaptureActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class JichiActivity extends CommonBaseActivity implements INetWorResult {


    @BindView(R.id.et_search)
    public EditText mEtSearch;

    @BindView(R.id.tv_sheet_no)
    public TextView tv_sheet_no;

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_server_name)
    TextView tv_server_name;

    @BindView(R.id.tv_tot_num)
    TextView tv_tot_num;

    @BindView(R.id.tv_ret_num)
    TextView tv_ret_num;//

    @BindView(R.id.tv_tot_money)
    TextView tv_tot_money;//

    @BindView(R.id.tv_real_money)
    TextView tv_real_money;//

    @BindView(R.id.bt_ok)
    Button bt_ok;//

    @BindView(R.id.iv_scan)
    ImageView iv_scan;

    private IMemberList mApi;
    public List<JichiChaxunResult> mJichiChaxunResult;
    public List<JichiSaveResult> mJichiSaveResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.bt_ok)
    public void onClickOK(){
        if (TextUtils.isEmpty(tv_sheet_no.getText().toString().trim())) {
            AlertUtil.showToast("请查询会员号");
            return;
        }
        if (mJichiSaveResult != null && mJichiSaveResult.size()>0) {

        }
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_jichi;
    }

    @Override
    protected void initView() {
        mApi = new MemberImp(this);

        setTopToolBar("计次消费", R.mipmap.ic_left_light, "", 0, "");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //点击键盘搜索按钮
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                        AlertUtil.showToast("请输入卡号/手机号/姓名");
                        return false;
                    }
                    AlertUtil.showNoButtonProgressDialog(JichiActivity.this,"正在读取卡信息，请稍后...");
                    mApi.qryCountInfo(mEtSearch.getText().toString().trim());
                    hideSoftInput();
                    return true;
                }
                return false;
            }
        });
        iv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JichiActivity.this, CaptureActivity.class);
                startActivityForResult(intent, Config.REQ_QR_CODE);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            String str = NfcUtils.resolveIntent(intent);
            mEtSearch.setText(str);
            onClickSearch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        //nfc初始化设置
        NfcUtils nfcUtils = new NfcUtils(this);
    }

    private void refreshUIByData(JichiSaveResult data) {
        mEtSearch.setText("");
        tv_sheet_no.setText(data.getSheet_no());
        mTvName.setText(data.getCust_name());
        tv_server_name.setText(data.getServer_name());
        tv_tot_num.setText(data.getTot_num());
        tv_ret_num.setText(data.getRet_num());
        tv_tot_money.setText(data.getTot_money());
        tv_real_money.setText(data.getReal_money());
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
        mApi.qryCountInfo(mEtSearch.getText().toString().trim());
        hideSoftInput();
    }



    @Override
    public void handleResule(int flag, Object o) {
        switch (flag) {
            case Config.MESSAGE_JICI_CHAXUN_OK:
                mJichiChaxunResult = (List<JichiChaxunResult>) o;
                if (mJichiChaxunResult != null && mJichiChaxunResult.size()>0) {
                    mApi.SaveCountSale(mJichiChaxunResult.get(0).getSheet_no());

                } else {
                    AlertUtil.dismissProgressDialog();
                    AlertUtil.showToast("没有对应此卡号的信息！");
                }
                break;
            case Config.MESSAGE_JICI_SAVE_OK:
                AlertUtil.dismissProgressDialog();
                AlertUtil.showToast("消费成功");
                mJichiSaveResult = (List<JichiSaveResult>) o;
                if (mJichiSaveResult != null && mJichiSaveResult.size()>0) {
                    refreshUIByData(mJichiSaveResult.get(0));
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

    @Override
    public void onResume() {
        super.onResume();
        //开启前台调度系统
        if(NfcUtils.mNfcAdapter!=null){
            NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //关闭前台调度系统
        if(NfcUtils.mNfcAdapter!=null){
            NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.REQ_QR_CODE && data != null) {
            String codedContent = data.getStringExtra("codedContent");
            if(codedContent != null && !codedContent.equals("")){
                mEtSearch.setText(codedContent);
                onClickSearch();
            }
        }

    }
}