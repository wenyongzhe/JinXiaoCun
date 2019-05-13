package com.eshop.jinxiaocun.lingshou.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.huiyuan.view.MemberCheckActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.NfcUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import java.io.UnsupportedEncodingException;
import butterknife.BindView;
import butterknife.OnClick;

public class SaveMemberActivity extends MemberCheckActivity {

    @BindView(R.id.bt_ok)
    Button bt_ok;//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @OnClick(R.id.bt_ok)
    public void onClickOK(){
        if (TextUtils.isEmpty(mTvCardNumber.getText().toString().trim())) {
            AlertUtil.showToast("请查询会员号");
            return;
        }
       /* Intent mIntent = new Intent();
        mIntent.putExtra("memberId",mTvCardNumber.getText().toString().trim());
        mIntent.putExtra("data", (Serializable) data);
        setResult(Config.SAVE_MEMBER_ID,mIntent);*/
        if (data != null && data.size()>0) {
            Config.mMemberInfo=data.get(0);//记录最近一次会员信息  供销售结算时使用
        }
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_get;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        //当该Activity接收到NFC标签时，运行该方法
        //调用工具方法，读取NFC数据
        try {
            NfcUtils.resolveIntent(intent);
            String str = NfcUtils.readNFCFromTag(intent);
            mEtSearch.setText(str);
            onClickSearch();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    public void initData() {
        //nfc初始化设置
        NfcUtils nfcUtils = new NfcUtils(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //开启前台调度系统
        NfcUtils.mNfcAdapter.enableForegroundDispatch(this, NfcUtils.mPendingIntent, NfcUtils.mIntentFilter, NfcUtils.mTechList);
    }

    @Override
    public void onPause() {
        super.onPause();
        //关闭前台调度系统
        NfcUtils.mNfcAdapter.disableForegroundDispatch(this);
    }
}
