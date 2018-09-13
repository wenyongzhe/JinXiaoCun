package com.eshop.jinxiaocun.pifaxiaoshou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/9/13
 * 描述
 */

public class PifaXiaoshouListAdapter extends BaseAdapter {

    private List<DanJuMainBeanResultItem> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public PifaXiaoshouListAdapter(List<DanJuMainBeanResultItem> listInfo) {
        this.listInfo = listInfo;
        inflater = LayoutInflater.from(Application.mContext);
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
            convertView = inflater.inflate(R.layout.item_list_pifa_xiaoshou,parent,false);
        }
        TextView tvTitle = ViewHolderUtils.get(convertView, R.id.tvTitle);
        tvTitle.setText(listInfo.get(position).Branch_No);

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<DanJuMainBeanResultItem> listInfo) {
        this.listInfo = listInfo;
        notifyDataSetChanged();
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

}
