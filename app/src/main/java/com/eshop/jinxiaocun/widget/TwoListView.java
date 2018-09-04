package com.eshop.jinxiaocun.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.ListBean;
import com.eshop.jinxiaocun.base.bean.QryClassResult;
import com.eshop.jinxiaocun.pifaxiaoshou.view.XiaoshouDanListAdapter;

import java.util.List;

public class TwoListView extends LinearLayout {

    protected ListView mListViewMain;
    protected RefreshListView mListViewDetail;
    protected TwoListMainAdapter mTwoListMainAdapter;
    protected TwoListDetailAdapter mTwoListDetailAdapter;
    protected List<QryClassResult> mainListBean;
    protected List<GetClassPluResult> detailListBean;
    int page = 1;

    public TwoListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttrs(context, attrs);
    }

    public TwoListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                    break;
                case R.styleable.ImageTextView_subTitle:
                    resourceId = typedArray.getResourceId(R.styleable.ImageTextView_subTitle, 0);
                    break;
                case R.styleable.ImageTextView_subMessage:
                    resourceId = typedArray.getResourceId(R.styleable.ImageTextView_subMessage, 0);
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
        View contentView = inflate(getContext(), R.layout.widget_two_listview, this);
        mListViewMain = (ListView) contentView.findViewById(R.id.lv_main);
        mListViewDetail = (RefreshListView) contentView.findViewById(R.id.lv_detail);
        mListViewDetail.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
            }
        });

        mListViewDetail.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                page ++;
                loadData();
            }
        });




    }

    private void loadData() {
    }

    public void setDetailListBean(List<GetClassPluResult> detailListBean) {
        this.detailListBean = detailListBean;
        mTwoListDetailAdapter = new TwoListDetailAdapter(detailListBean);
        mListViewDetail.setAdapter(mTwoListDetailAdapter);
        mTwoListDetailAdapter.notifyDataSetChanged();
    }

    public void setMainListBean(List<QryClassResult> mainListBean, AdapterView.OnItemClickListener listener) {
        this.mainListBean = mainListBean;
        mTwoListMainAdapter = new TwoListMainAdapter(mainListBean);
        mListViewMain.setAdapter(mTwoListMainAdapter);
        mListViewMain.setOnItemClickListener(listener);
        mTwoListMainAdapter.notifyDataSetChanged();
    }

    public void onRefreshComplete(){
        mListViewDetail.onRefreshComplete();
    }
}
