package com.eshop.jinxiaocun.lingshou.view;

import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.huiyuan.view.MemberCheckActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

public class SaveMemberActivity extends MemberCheckActivity {

    @BindView(R.id.bt_ok)
    Button bt_ok;//

    @OnClick(R.id.bt_ok)
    public void onClickOK(){
        if (TextUtils.isEmpty(mTvCardNumber.getText().toString().trim())) {
            AlertUtil.showToast("请查询会员号");
            return;
        }
        Intent mIntent = new Intent();
        mIntent.putExtra("memberId",mTvCardNumber.getText().toString().trim());
        mIntent.putExtra("data", (Serializable) data);
        setResult(Config.SAVE_MEMBER_ID,mIntent);
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
}
