package com.eshop.jinxiaocun.xiaoshou.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;
import com.eshop.jinxiaocun.xiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.xiaoshou.bean.GoodGetBeanResult;

import java.util.List;

public class XiaoshouDanListAdapter extends BaseAdapter {

    private List<DanJuMainBeanResult.DanJuMainBeanJson> listInfo;


    public XiaoshouDanListAdapter(List<DanJuMainBeanResult.DanJuMainBeanJson> listInfo) {
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
