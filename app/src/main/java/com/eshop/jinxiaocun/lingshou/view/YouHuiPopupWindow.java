package com.eshop.jinxiaocun.lingshou.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;

public class YouHuiPopupWindow extends PopupWindow {

    public TextView id_1;
    public TextView id_2;
    public TextView id_3;
    public TextView id_4;
    public TextView id_5;

    public interface OnPopupWindowClick {
        public void OnClick(int id);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public YouHuiPopupWindow(Context context, final OnPopupWindowClick mOnPopupWindowClick) {
        super(context);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View contentView = LayoutInflater.from(context).inflate(R.layout.popu_window_youhui,
                null, false);

        id_1 = contentView.findViewById(R.id.id_1);
        id_2 = contentView.findViewById(R.id.id_2);
        id_3 = contentView.findViewById(R.id.id_3);
        id_4 = contentView.findViewById(R.id.id_4);
        id_5 = contentView.findViewById(R.id.id_5);

        id_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPopupWindowClick.OnClick(view.getId());
            }
        });
        id_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPopupWindowClick.OnClick(view.getId());
            }
        });
        id_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPopupWindowClick.OnClick(view.getId());
            }
        });
        id_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPopupWindowClick.OnClick(view.getId());
            }
        });
        id_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnPopupWindowClick.OnClick(view.getId());
            }
        });
        setContentView(contentView);
    }
}