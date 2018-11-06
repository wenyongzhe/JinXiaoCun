package com.eshop.jinxiaocun.lingshou.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.lingshou.bean.GetPayModeResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

public class SelectPayListAdapter extends BaseAdapter {

    private List<GetPayModeResult> listInfo;


    public SelectPayListAdapter(List<GetPayModeResult> listInfo) {
        this.listInfo = listInfo;
    }

    public void setListInfo(List<GetPayModeResult> listInfo) {
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
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_select_pay,parent,false);
        }
        TextView tv_pay_name = ViewHolderUtils.get(convertView, R.id.tv_pay_name);

        tv_pay_name.setText(listInfo.get(position).getPay_name());
        return convertView;
    }
}
