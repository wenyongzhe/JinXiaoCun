package com.eshop.jinxiaocun.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.ColorUtils;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.pifaxiaoshou.view.PiFaXiaoshouDanScanActivity;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;

import java.util.List;


public final class MyActionBar extends LinearLayout {

    private View layRoot;
    private View vStatusBar;
    private View layLeft;
    private View layRight;
    private TextView tvTitle;
    private TextView tvLeft;
    private TextView tvRight;
    private View iconLeft;
    private View iconRight;
    private Spinner mSpinner;
    private Context context;

    public MyActionBar(Context context) {
        this(context, null);
    }

    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MyActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        setOrientation(HORIZONTAL);
        View contentView = inflate(getContext(), R.layout.actionbar_dft, this);
        layRoot = contentView.findViewById(R.id.lay_transroot);
        vStatusBar = contentView.findViewById(R.id.v_statusbar);
        tvTitle = (TextView) contentView.findViewById(R.id.tv_actionbar_title);
        tvLeft = (TextView) contentView.findViewById(R.id.tv_actionbar_left);
        tvRight = (TextView) contentView.findViewById(R.id.tv_actionbar_right);
        iconLeft = contentView.findViewById(R.id.iv_actionbar_left);
        iconRight = contentView.findViewById(R.id.v_actionbar_right);
        mSpinner = contentView.findViewById(R.id.sp_actionbar_right);
        tvTitle.setText(Config.soft_name);
    }

    /**
     * 设置状态栏高度
     *
     * @param statusBarHeight
     */
    public void setStatusBarHeight(int statusBarHeight) {
        ViewGroup.LayoutParams params = vStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        vStatusBar.setLayoutParams(params);
    }

    /**
     * 设置是否需要渐变
     *
     * @param translucent
     */
    public void setNeedTranslucent(boolean translucent) {
        if (translucent) {
            layRoot.setBackgroundDrawable(null);
        }
    }

    /**
     * 设置标题
     *
     * @param strTitle
     */
    public void setTitle(String strTitle) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边标题
     *
     * @param strTitle
     */
    public void setRightTitle(String strTitle) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvRight.setText(strTitle);
            tvRight.setVisibility(View.VISIBLE);
        }else {
            tvRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边标题及样式
     *
     * @param strTitle
     */
    public void setRightTitleAndStyle(String strTitle, @DrawableRes int drawableId) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvRight.setText(strTitle);
            tvRight.setBackgroundResource(drawableId);
            tvRight.setPadding(10,5,10,5);
            tvRight.setVisibility(View.VISIBLE);
        }else {
            tvRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置透明度
     *
     * @param transAlpha 0-255 之间
     */
    public void setTranslucent(int transAlpha) {
        layRoot.setBackgroundColor(ColorUtils.setAlphaComponent(getResources().getColor(R.color.primary), transAlpha));
        tvTitle.setAlpha(transAlpha);
        tvLeft.setAlpha(transAlpha);
        tvRight.setAlpha(transAlpha);
        iconLeft.setAlpha(transAlpha);
        iconRight.setAlpha(transAlpha);
    }

    public TextView getTvRight() {
        return tvRight;
    }

    /**
     * 设置数据
     *
     * @param strTitle
     * @param resIdLeft
     * @param strLeft
     * @param resIdRight
     * @param strRight
     * @param listener
     */
    public void setData(String strTitle, int resIdLeft, String strLeft, int resIdRight, String strRight, final ActionBarClickListener listener) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(strLeft)) {
            tvLeft.setText(strLeft);
            tvLeft.setVisibility(View.VISIBLE);
        } else {
            tvLeft.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(strRight)) {
            tvRight.setText(strRight);
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setVisibility(View.GONE);
        }

        if (resIdLeft == 0) {
            iconLeft.setVisibility(View.GONE);
        } else {
            iconLeft.setBackgroundResource(resIdLeft);
            iconLeft.setVisibility(View.VISIBLE);
        }

        if (resIdRight == 0) {
            iconRight.setVisibility(View.GONE);
        } else {
            iconRight.setBackgroundResource(resIdRight);
            iconRight.setVisibility(View.VISIBLE);
        }

        if (listener != null) {
            layLeft = findViewById(R.id.lay_actionbar_left);
            layRight = findViewById(R.id.lay_actionbar_right);
            layLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLeftClick();
                }
            });
            layRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRightClick();
                }
            });
        }
    }

    public void setData(String strTitle, int resIdLeft, String strLeft, int resIdRight, String strRight, List strSpinner, final ActionBarClickListener listener) {
        if (!TextUtils.isEmpty(strTitle)) {
            tvTitle.setText(strTitle);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(strLeft)) {
            tvLeft.setText(strLeft);
            tvLeft.setVisibility(View.VISIBLE);
        } else {
            tvLeft.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(strRight)) {
            tvRight.setText(strRight);
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setVisibility(View.GONE);
        }

        if (strSpinner!=null&&strSpinner.size()>0) {
            ArrayAdapter<String> mTuiHupoAdapter = new ArrayAdapter<>(context, R.layout.my_simple_spinner_item2, strSpinner);
            mTuiHupoAdapter.setDropDownViewResource(R.layout.my_drop_down_item);
            mSpinner.setAdapter(mTuiHupoAdapter);
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            mSpinner.setVisibility(View.VISIBLE);
        } else {
            mSpinner.setVisibility(View.GONE);
        }

        if (resIdLeft == 0) {
            iconLeft.setVisibility(View.GONE);
        } else {
            iconLeft.setBackgroundResource(resIdLeft);
            iconLeft.setVisibility(View.VISIBLE);
        }

        if (resIdRight == 0) {
            iconRight.setVisibility(View.GONE);
        } else {
            iconRight.setBackgroundResource(resIdRight);
            iconRight.setVisibility(View.VISIBLE);
        }

        if (listener != null) {
            layLeft = findViewById(R.id.lay_actionbar_left);
            layRight = findViewById(R.id.lay_actionbar_right);
            layLeft.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onLeftClick();
                }
            });
            layRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRightClick();
                }
            });
        }
    }

}
