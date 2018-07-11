package com.eshop.jinxiaocun.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.widget.ImageTextView;

public class XiaoShouFragment extends Fragment {

    private ImageTextView imagetext1;


    public static Fragment newInstance() {
        return new XiaoShouFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xiaoshou_form, container, false);
        imagetext1 = view.findViewById(R.id.imagetext1);
        imagetext1.setSubjectHead("3334");
        return view;
    }

}
