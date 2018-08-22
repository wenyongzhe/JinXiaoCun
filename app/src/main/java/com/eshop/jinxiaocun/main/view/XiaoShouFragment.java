package com.eshop.jinxiaocun.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseFragment;
import com.eshop.jinxiaocun.lingshou.view.LingShouScanActivity;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.ImageTextView;
import com.eshop.jinxiaocun.pifaxiaoshou.view.XiaoShouDanTabListActivity;
import com.eshop.jinxiaocun.pifaxiaoshou.view.XiaoShouTuiHuoListActivity;

public class XiaoShouFragment extends BaseFragment {

    private ImageTextView imagetext1;
    private ImageTextView myView_xiaoshou;
    private ImageTextView myView_xiaoshoutui;
    private ImageTextView myView_lingshou;

    public static Fragment newInstance() {
        return new XiaoShouFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xiaoshou_form, container, false);
        imagetext1 = view.findViewById(R.id.imagetext1);

        myView_xiaoshou = view.findViewById(R.id.myView_xiaoshou);
        myView_xiaoshoutui = view.findViewById(R.id.myView_xiaoshoutui);
        myView_lingshou = view.findViewById(R.id.myView_lingshou);
        myView_xiaoshou.setOnClickListener(this);
        myView_xiaoshoutui.setOnClickListener(this);
        myView_lingshou.setOnClickListener(this);

        return view;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        Intent mIntent;
        switch (view.getId()){
            case R.id.myView_xiaoshou:
                mIntent = new Intent(getActivity(), XiaoShouDanTabListActivity.class);
                startActivity(mIntent);
                break;
            case R.id.myView_xiaoshoutui:
                mIntent = new Intent(getActivity(), XiaoShouTuiHuoListActivity.class);
                startActivity(mIntent);
                break;
            case R.id.myView_lingshou:
                mIntent = new Intent(getActivity(), LingShouScanActivity.class);
                mIntent.putExtra(Config.SHEET_NO,"");
                startActivity(mIntent);
                break;
        }
    }
}
