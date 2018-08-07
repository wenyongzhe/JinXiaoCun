package com.eshop.jinxiaocun.base.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.AdapterView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.ViewFindUtils;
import com.flyco.tablayout.SegmentTabLayout;

import java.util.ArrayList;


public class BaseTabListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "消息", "联系人"};
    private View mDecorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_tab_list);

        mDecorView = getWindow().getDecorView();
        SegmentTabLayout tabLayout_4 = ViewFindUtils.find(mDecorView, R.id.tl_1);

        for (String title : mTitles) {
            mFragments.add(DemoTestFragment.getInstance("Switch Fragment " + title));
        }
        tabLayout_4.setTabData(mTitles, this, R.id.fl_change, mFragments);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
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
