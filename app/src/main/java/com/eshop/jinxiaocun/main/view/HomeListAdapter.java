package com.eshop.jinxiaocun.main.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class HomeListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

//    List<GetReport> getReportData = new ArrayList<>();
//
//    @Override
//    public int getCount() {
//        return getReportData.size();
//    }
//
//    public HomeListAdapter(List<GetReport> getReportData) {
//        this.getReportData = getReportData;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//        //将布局文件转换成View
//        View gridview_item = View.inflate(myApplication.mContext, R.layout.item_gridview_report, null);
//
//        ImageView iv_icon = (ImageView) gridview_item.findViewById(R.id.iv_icon);
//        TextView tv_title = (TextView) gridview_item.findViewById(R.id.tv_title);
//
//        tv_title.setText(getReportData.get(position).Name);
//
//        return gridview_item;
//    }


}
