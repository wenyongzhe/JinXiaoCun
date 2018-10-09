package com.eshop.jinxiaocun.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.login.LoginActivity;
import com.eshop.jinxiaocun.login.SystemSettingActivity;


public class jichuFragment extends Fragment {


    public static Fragment newInstance() {
        return new jichuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_jichu, container, false);

        Button btn_setting = view.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SystemSettingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
