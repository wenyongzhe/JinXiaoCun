package com.eshop.jinxiaocun.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;

public class ImageTextView extends LinearLayout {

    private View tv_subject_heading;
    private View tv_message;

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setOrientation(HORIZONTAL);
        View contentView = inflate(getContext(), R.layout.widget_image_text, this);
        tv_subject_heading = (TextView) contentView.findViewById(R.id.tv_subject_heading);
        tv_message = (TextView) contentView.findViewById(R.id.tv_message);
    }


}
