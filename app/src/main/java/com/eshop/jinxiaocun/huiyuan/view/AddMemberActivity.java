package com.eshop.jinxiaocun.huiyuan.view;

import android.text.TextUtils;
import android.widget.EditText;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.huiyuan.bean.AddMemberBean;
import com.eshop.jinxiaocun.huiyuan.presenter.IMemberList;
import com.eshop.jinxiaocun.huiyuan.presenter.MemberImp;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc:
 */

public class AddMemberActivity extends CommonBaseActivity implements INetWorResult{

    @BindView(R.id.et_card_number)
    EditText mEtCardNumber;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.et_card_type)
    EditText mEtCardType;
    @BindView(R.id.et_sex)
    EditText mEtSex;
    @BindView(R.id.et_birthday)
    EditText mEtBirthday;
    @BindView(R.id.et_remarks)
    EditText mEtRemarks;

    private IMemberList mApi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_member;
    }

    @Override
    protected void initView() {
        super.initView();
        setTopToolBar("新增会员",R.mipmap.ic_left_light,"",0,"");
    }

    @Override
    protected void initData() {
        mApi = new MemberImp(this);
    }

    @OnClick(R.id.btn_save_member_info)
    public void onClickAddMember(){
        if(TextUtils.isEmpty(mEtCardNumber.getText().toString().trim())){
            AlertUtil.showToast("请输入卡号！");
            return;
        }
        if(TextUtils.isEmpty(mEtName.getText().toString().trim())){
            AlertUtil.showToast("请输入姓名！");
            return;
        }

        if(TextUtils.isEmpty(mEtPhoneNumber.getText().toString().trim())){
            AlertUtil.showToast("请输入手机号！");
            return;
        }

        if(TextUtils.isEmpty(mEtCardType.getText().toString().trim())){
            AlertUtil.showToast("请输入卡类型！");
            return;
        }

        if(TextUtils.isEmpty(mEtSex.getText().toString().trim())){
            AlertUtil.showToast("请输入性别！");
            return;
        }

        if(TextUtils.isEmpty(mEtBirthday.getText().toString().trim())){
            AlertUtil.showToast("请输入生日！");
            return;
        }

        AlertUtil.showNoButtonProgressDialog(this,"正在添加新会员，请稍后...");
        AddMemberBean bean = new AddMemberBean();
        bean.JsonData.OperInfo = Config.UserId;//操作人员
        bean.JsonData.Branch_No = Config.branch_no;
        bean.JsonData.CardNo_TelNo = mEtCardNumber.getText().toString().trim();
        bean.JsonData.Name = mEtName.getText().toString().trim();
        bean.JsonData.Mobile = mEtPhoneNumber.getText().toString().trim();
        bean.JsonData.Tel = "";
        bean.JsonData.MemberType = mEtCardType.getText().toString().trim();
        bean.JsonData.Sex = mEtSex.getText().toString().trim();
        bean.JsonData.BirthDay = mEtBirthday.getText().toString().trim();
        bean.JsonData.Memo = mEtRemarks.getText().toString().trim();

        mApi.addMemberData(bean);

    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_OK:
            case Config.MESSAGE_ERROR:
                AlertUtil.showToast(o.toString());
                AlertUtil.dismissProgressDialog();
                break;
        }
    }

}
