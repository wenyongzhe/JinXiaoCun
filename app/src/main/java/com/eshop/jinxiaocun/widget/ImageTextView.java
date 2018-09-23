package com.eshop.jinxiaocun.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;

public class ImageTextView extends LinearLayout {

    private TextView tv_subject_heading;
    private TextView tv_message;
    private ImageView iv_photo;

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(context, attrs);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs){
        TypedArray typedArray  = context.obtainStyledAttributes(attrs,R.styleable.ImageTextView, 0, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            int resourceId;
            switch (attr) {
                case R.styleable.ImageTextView_leftImage:
                    resourceId = typedArray.getResourceId( R.styleable.ImageTextView_leftImage, 0);
                    iv_photo.setImageResource(resourceId > 0 ?resourceId:R.drawable.xs);
                    break;
                case R.styleable.ImageTextView_subTitle:
                    resourceId = typedArray.getResourceId(
                            R.styleable.ImageTextView_subTitle, 0);
                    tv_subject_heading.setText(resourceId > 0 ? typedArray.getResources().getText(
                            resourceId) : typedArray
                            .getString(R.styleable.ImageTextView_subTitle));
                    break;
                case R.styleable.ImageTextView_subMessage:
                    resourceId = typedArray.getResourceId(
                            R.styleable.ImageTextView_subMessage, 0);
                    tv_message.setText(resourceId > 0 ? typedArray.getResources().getText(
                            resourceId) : typedArray
                            .getString(R.styleable.ImageTextView_subTitle));
                    break;
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        View contentView = inflate(getContext(), R.layout.widget_image_text, this);
        tv_subject_heading = (TextView) contentView.findViewById(R.id.tv_subject_heading);
        tv_message = (TextView) contentView.findViewById(R.id.tv_message);
        iv_photo = (ImageView) contentView.findViewById(R.id.iv_photo);
    }

    public void setSubjectHead(String str){
        tv_subject_heading.setText(str);
    }

    public void setSubMessage(String str){
        tv_message.setText(str);
    }



}
