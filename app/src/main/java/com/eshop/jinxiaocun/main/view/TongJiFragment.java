package com.eshop.jinxiaocun.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshop.jinxiaocun.R;


public class TongJiFragment extends Fragment {

    public static android.support.v4.app.Fragment newInstance() {
        return new TongJiFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        return view;
    }

}
