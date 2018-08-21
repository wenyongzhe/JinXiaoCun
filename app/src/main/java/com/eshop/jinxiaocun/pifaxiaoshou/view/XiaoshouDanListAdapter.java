package com.eshop.jinxiaocun.pifaxiaoshou.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;

import java.util.List;

public class XiaoshouDanListAdapter extends BaseAdapter {

    private List<DanJuMainBeanResultItem> listInfo;


    public XiaoshouDanListAdapter(List<DanJuMainBeanResultItem> listInfo) {
        this.listInfo = listInfo;
    }

    @Override
    public int getCount() {
        return listInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return listInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_xiaoshoudan,parent,false);
        }
        TextView tvTitle = ViewHolderUtils.get(convertView, R.id.tvTitle);
        tvTitle.setText(listInfo.get(position).Branch_No);
        return convertView;
    }
}
