package com.eshop.jinxiaocun.huiyuan.view;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc:
 */

public class AddMemberActivity extends CommonBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_member;
    }

    @Override
    protected void initView() {
        super.initView();
        setTopToolBar("新增会员",R.mipmap.ic_left_light,"",0,"");
    }

}
