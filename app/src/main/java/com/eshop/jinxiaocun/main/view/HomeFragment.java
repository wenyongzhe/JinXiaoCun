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
import com.eshop.jinxiaocun.huiyuan.view.HuiyuanManagerActivity;
import com.eshop.jinxiaocun.lingshou.view.GetSellBillActivity;
import com.eshop.jinxiaocun.lingshou.view.LingShouCreatAtivity;
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

    private Unbinder unbinder;
    private int[] iconIds = {
            R.drawable.kssk,
            R.drawable.xsqd,
            R.drawable.menber,
            R.drawable.pandian,
            R.drawable.caigou,
            R.drawable.kcgl,
//            R.drawable.tbgl,
            R.drawable.xiaoshoupifa,
            R.drawable.peisong
//            R.drawable.peisong
    };
    private int[] nameIds = {
            R.string.item_message_lingshou,
            R.string.menu_qudan,
            R.string.menu_member_manager,
            R.string.menu_pandian_manage,
            R.string.menu_caigoudan,
            R.string.menu_shanpingchaxun,
            R.string.menu_pifa_xiaoshou,
            R.string.menu_peisong_manager
//            R.string.menu_diaobo_manage,
//            R.string.menu_peisong_manager,
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
            case 0://零售
                intent.setClass(Application.mContext, LingShouCreatAtivity.class);
                intent.putExtra(Config.SHEET_NO,"");
                startActivity(intent);
                break;
            case 1://销售取单
                intent.setClass(Application.mContext, GetSellBillActivity.class);
                startActivity(intent);
                break;
            case 2://会员管理
                intent.setClass(Application.mContext, HuiyuanManagerActivity.class);
                startActivity(intent);
                break;
            case 3://盘点
                intent.setClass(Application.mContext, PandianManagerActivity.class);
                startActivity(intent);
                break;
            case 4://采购
                intent.setClass(Application.mContext, CaigouManagerActivity.class);
                startActivity(intent);
                break;
            case 5://商品查询
                intent.setClass(Application.mContext, GoodDetailCheckActivity.class);
                startActivity(intent);
                break;
            case 6://批发管理
                intent.setClass(Application.mContext, PifaManagerActivity.class);
                startActivity(intent);
                break;
            case 7://配送管理
                intent.setClass(Application.mContext, PeisongManagerActivity.class);
                startActivity(intent);
                break;
//            case 8://批发销售单
//                intent.setClass(Application.mContext, PiFaXiaoshouDanScanActivity.class);
//                startActivity(intent);
//                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
