package com.eshop.jinxiaocun.huiyuan.view;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/16
 * Desc: 会员查询
 */

public class MemberCheckActivity extends CommonBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_member_check;
    }

    @Override
    protected void initView() {
        setTopToolBar("会员查询",R.mipmap.ic_left_light,"",0,"");
    }
}
