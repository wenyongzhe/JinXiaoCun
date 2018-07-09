package com.eshop.jinxiaocun.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import supoin.jinxiaocun.R;

public class HomeFragment extends Fragment {

    @BindView(R.id.gridview)
    GridView gridview;

    private HomeListAdapter mReportListAdapter;
    private Unbinder unbinder;

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_form, container, false);
        unbinder = ButterKnife.bind(this, view);
//        tv_title.setText("报表");
        loadData();
        return view;
    }

    private void loadData() {
    }

}
