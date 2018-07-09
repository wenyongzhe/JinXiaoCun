package com.eshop.jinxiaocun.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.main.adapter.MenuAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    @BindView(R.id.gridview)
    GridView gridview;

    private Unbinder unbinder;
    private int[] iconIds = { R.mipmap.img_shangpin_chaxun,
            R.mipmap.img_jieqian_dayin, R.mipmap.img_mendian_qinghuo,
            R.mipmap.img_receipt_geli,
            R.mipmap.img_peisong_geli,
            R.mipmap.img_diaobo_geli,R.mipmap.img_pandian_geli,R.mipmap.img_lixian_geli
    };
    private int[] nameIds = { R.string.menu_goods_query,
            R.string.menu_jiaqian_print, R.string.menu_mendian_qinghuo,
            R.string.menu_zhigong_manage,
            R.string.menu_deliver_manage,
            R.string.menu_diaobo_manage,R.string.menu_pandian_manage,R.string.menu_offline_manage
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
//        tv_title.setText("报表");
        loadData();
        return view;
    }

    private void loadData() {
    }

}
