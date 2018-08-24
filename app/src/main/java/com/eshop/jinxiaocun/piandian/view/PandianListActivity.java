package com.eshop.jinxiaocun.piandian.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseListActivity;
/**
 * @Author Lu An
 * 创建时间  2018/8/24
 * 描述
 */
public class PandianListActivity extends BaseListActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayout.addView(getView(R.layout.activity_piandian_list));

        loadData();
        initView();
    }

    @Override
    protected void loadData() {
        super.loadData();

    }

    @Override
    protected void initView() {
        super.initView();
        setHeaderTitle(R.id.tv_0,R.string.list_item_Status,150);
        setHeaderTitle(R.id.tv_1,R.string.list_item_ProdName,150);
        setHeaderTitle(R.id.tv_2,R.string.list_item_ProdCode,150);
        setHeaderTitle(R.id.tv_3,R.string.list_item_OrderDate,150);
    }
}
