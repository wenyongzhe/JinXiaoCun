package com.eshop.jinxiaocun.base.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.TabEntity;
import com.eshop.jinxiaocun.lingshou.view.QueryFragment;
import com.eshop.jinxiaocun.lingshou.view.SelectGoodsFragment;
import com.eshop.jinxiaocun.pifaxiaoshou.view.CaoGaoDanJuFragment;
import com.eshop.jinxiaocun.pifaxiaoshou.view.FinishDanJuFragment;
import com.eshop.jinxiaocun.utils.ViewFindUtils;
import com.eshop.jinxiaocun.widget.ActionBarClickListener;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

public class QreShanpingActivity extends BaseTabListActivity  implements AdapterView.OnItemClickListener,ActionBarClickListener {

    ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"选择商品", "搜索商品"};
//    private int[] mIconUnselectIds = { R.mipmap.img_diaobo_geli, R.mipmap.img_diaobo_geli};
//    private int[] mIconSelectIds = { R.mipmap.img_diaobo_geli, R.mipmap.img_diaobo_geli};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(getView(R.layout.activity_chaxun_shanping),0,params);

        mDecorView = getWindow().getDecorView();
        SegmentTabLayout tabLayout_4 = ViewFindUtils.find(mDecorView, R.id.tl_1);

//        for (int i = 0; i < mTitles.length; i++) {
//            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
//        }

        mFragments.add(SelectGoodsFragment.getInstance());
        mFragments.add(QueryFragment.getInstance());
        tabLayout_4.setTabData(mTitles, this, R.id.fl_change, mFragments);

        mMyActionBar.setData("单据列表",R.mipmap.ic_left_light,"",R.mipmap.add,"",this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }
}
