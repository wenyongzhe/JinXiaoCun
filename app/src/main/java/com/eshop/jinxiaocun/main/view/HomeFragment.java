package com.eshop.jinxiaocun.main.view;

import android.bluetooth.BluetoothAdapter;
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
import com.eshop.jinxiaocun.caigou.view.CaigouManagerActivity;
import com.eshop.jinxiaocun.lingshou.view.LingShouScanActivity;
import com.eshop.jinxiaocun.login.SystemSettingActivity;
import com.eshop.jinxiaocun.main.adapter.MenuAdapter;
import com.eshop.jinxiaocun.peisong.view.PeisongManagerActivity;
import com.eshop.jinxiaocun.peisong.view.YaohuoOrderListActivity;
import com.eshop.jinxiaocun.piandian.view.PandianManagerActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.view.PiFaXiaoshouDanScanActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.view.PifaManagerActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.view.PifaOrderListActivity;
import com.eshop.jinxiaocun.stock.view.GoodDetailCheckActivity;
import com.eshop.jinxiaocun.stock.view.StockCheckActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.zjPrinter.BluetoothService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.gridview)
    GridView gridview;
    private BluetoothAdapter mBluetoothAdapter = null;

    private Unbinder unbinder;
    private int[] iconIds = {
            R.drawable.cgd,
//            R.drawable.xsdj,
            R.drawable.kcgl,
            R.drawable.kssk,
            //R.drawable.tbgl,
            R.drawable.pdgl,
            R.drawable.spgl,
            R.drawable.spgl
    };
    private int[] nameIds = {
            R.string.menu_caigoudan,
//            R.string.menu_xiaoshoudan,
            R.string.menu_shanpingchaxun,
            R.string.item_message_lingshou,
            //R.string.menu_diaobo_manage,
            R.string.menu_pandian_manage,
            R.string.menu_pifa_xiaoshou,
            R.string.menu_peisong_manager
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
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
                intent.setClass(Application.mContext, CaigouManagerActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent.setClass(Application.mContext, GoodDetailCheckActivity.class);
                startActivity(intent);
                break;
            case 2:
                if (mBluetoothAdapter!=null&&!mBluetoothAdapter.isEnabled()) {
                    Intent enableIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, SystemSettingActivity.REQUEST_ENABLE_BT);
                    // Otherwise, setup the session
                } else {
                    intent.setClass(Application.mContext, LingShouScanActivity.class);
                    intent.putExtra(Config.SHEET_NO,"");
                    startActivity(intent);
                }

                break;
            case 3:
                intent.setClass(Application.mContext, PandianManagerActivity.class);
                startActivity(intent);
                break;
            case 4://批发管理
                intent.setClass(Application.mContext, PifaManagerActivity.class);
                startActivity(intent);
                break;
            case 5://配送管理
                intent.setClass(Application.mContext, PeisongManagerActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
