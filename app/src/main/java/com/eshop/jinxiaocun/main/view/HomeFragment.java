package com.eshop.jinxiaocun.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.BaseFragment;
import com.eshop.jinxiaocun.main.adapter.MenuAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.view.XiaoshouDanScanActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.gridview)
    GridView gridview;

    private Unbinder unbinder;
    private int[] iconIds = { R.mipmap.img_shangpin_chaxun,
            R.mipmap.img_jieqian_dayin, R.mipmap.img_mendian_qinghuo,
            R.mipmap.img_receipt_geli,
            R.mipmap.img_peisong_geli,
            R.mipmap.img_diaobo_geli,R.mipmap.img_pandian_geli
    };
    private int[] nameIds = { R.string.menu_goods_manager,
            R.string.menu_caigoudan, R.string.menu_xiaoshoudan,
            R.string.menu_kucunchaxun,
            R.string.menu_kuaishushoukuang,
            R.string.menu_diaobo_manage,R.string.menu_pandian_manage
    };

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_form, container, false);
        unbinder = ButterKnife.bind(this, view);
        MenuAdapter menuAdapter = new MenuAdapter(getActivity(), iconIds, nameIds);
        gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(menuAdapter);
        gridview.setOnItemClickListener(this);
        loadData();
        return view;
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();

        switch (i){
            case 0:
                break;

            case 1:
                break;
            case 2:
                intent.setClass(Application.mContext, XiaoshouDanScanActivity.class);
                startActivity(intent);
                break;
            case 3:
                break;

        }
    }
}
