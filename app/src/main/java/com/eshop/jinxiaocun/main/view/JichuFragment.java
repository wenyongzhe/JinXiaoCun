package com.eshop.jinxiaocun.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.BaseFragment;
import com.eshop.jinxiaocun.caigou.view.CaigouManagerActivity;
import com.eshop.jinxiaocun.lingshou.view.LingShouScanActivity;
import com.eshop.jinxiaocun.login.SystemSettingActivity;
import com.eshop.jinxiaocun.main.adapter.MenuAdapter;
import com.eshop.jinxiaocun.peisong.view.PeisongManagerActivity;
import com.eshop.jinxiaocun.piandian.view.PandianManagerActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.view.PifaManagerActivity;
import com.eshop.jinxiaocun.stock.view.StockCheckActivity;
import com.eshop.jinxiaocun.utils.Config;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class JichuFragment extends BaseFragment {
    @BindView(R.id.gridview)
    GridView gridview;
    private Unbinder unbinder;
    private int[] iconIds = {
            R.drawable.setting,
    };
    private int[] nameIds = {
            R.string.setting,
    };

    public static Fragment newInstance() {
        return new JichuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_jichu, container, false);
        unbinder = ButterKnife.bind(this, view);
        MenuAdapter menuAdapter = new MenuAdapter(getActivity(), iconIds, nameIds);
        gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(menuAdapter);
        gridview.setOnItemClickListener(this);

//        Button btn_setting = view.findViewById(R.id.btn_setting);
////        btn_setting.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent();
////                intent.setClass(getActivity(), SystemSettingActivity.class);
////                startActivity(intent);
////            }
////        });
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();

        switch (i){
            case 0:
                intent.setClass(getActivity(), SystemSettingActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}