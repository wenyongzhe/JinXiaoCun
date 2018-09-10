package com.eshop.jinxiaocun.base.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eshop.jinxiaocun.R;

public class MyBaseAdapter extends BaseAdapter{

    private int itemClickPosition = -1;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }
}
