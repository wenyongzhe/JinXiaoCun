package com.eshop.jinxiaocun.base.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;


import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.widget.LightProgressDialog;


/**
 * Created by gaoqiujie on 2018/3/23.
 */

public class BaseFragment extends Fragment implements View.OnClickListener{

    public ProgressDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        //初始化扫描
        if(isHidden()){
            onHiddenChanged(isHidden());
        }else{
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        try {
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            try {
            }catch (Exception e){
                e.printStackTrace();
            }
       }else{
        }
    }


    public void showLoadingDialog(String msg, boolean isDefaultMsg){
        loadingDialog = LightProgressDialog.show(getActivity(),null,msg);
        if (isDefaultMsg){
            loadingDialog.setMessage(getResources().getString(R.string.loading));
        }else {
            loadingDialog.setMessage(msg);
        }

        if (!loadingDialog.isShowing())
            loadingDialog.show();
    }

    public void closeLoadingDialog() {
        if(loadingDialog!=null && loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(loadingDialog!=null){
            loadingDialog.dismiss();
            loadingDialog=null;
        }
    }

    @Override
    public void onClick(View view) {

    }
}
