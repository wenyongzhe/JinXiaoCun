package com.eshop.jinxiaocun.base.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.ViewFindUtils;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.eshop.jinxiaocun.pifaxiaoshou.view.CaoGaoDanJuFragment;
import com.eshop.jinxiaocun.pifaxiaoshou.view.FinishDanJuFragment;
import com.flyco.tablayout.SegmentTabLayout;

import java.util.ArrayList;


public abstract class BaseTabListActivity extends BaseActivity implements AdapterView.OnItemClickListener,ActionBarClickListener {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"已提交", "草稿"};
    private View mDecorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(getView(R.layout.activity_base_tab_list),0,params);

        mDecorView = getWindow().getDecorView();
        SegmentTabLayout tabLayout_4 = ViewFindUtils.find(mDecorView, R.id.tl_1);

        mFragments.add(FinishDanJuFragment.getInstance());
        mFragments.add(CaoGaoDanJuFragment.getInstance());
        tabLayout_4.setTabData(mTitles, this, R.id.fl_change, mFragments);

        mMyActionBar.setData("单据列表",R.mipmap.ic_left_light,"",R.mipmap.add,"",this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
