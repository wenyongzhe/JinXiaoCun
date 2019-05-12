package com.eshop.jinxiaocun.lingshou.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

public class PiChiListAdapter extends BaseAdapter {

    private List<GoodsPiciInfoBeanResult> listInfo;
    List<GoodsPiciInfoBeanResult> mGoodsPiciInfoBeanResult;

    public PiChiListAdapter(List<GoodsPiciInfoBeanResult> listInfo) {
        this.listInfo = listInfo;
    }

    public void setListInfo(List<GoodsPiciInfoBeanResult> listInfo) {
        this.listInfo.clear();
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
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_select,parent,false);
        }
        TextView tv_name = ViewHolderUtils.get(convertView, R.id.tv_name);

        tv_name.setText(listInfo.get(position).getItem_barcode());
        return convertView;
    }
}
