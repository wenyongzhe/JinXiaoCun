package com.eshop.jinxiaocun.widget;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.ListBean;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

public class TwoListDetailAdapter extends BaseAdapter {

    private List<GetClassPluResult> listInfo;


    public TwoListDetailAdapter(List<GetClassPluResult> listInfo) {
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
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_twolist_detail,parent,false);
        }
        TextView tvTitle = ViewHolderUtils.get(convertView, R.id.tvTitle);
        TextView tv_message = ViewHolderUtils.get(convertView, R.id.tv_message);
        TextView tv_price = ViewHolderUtils.get(convertView, R.id.tv_price);

        tvTitle.setText(listInfo.get(position).getItem_name());
        tv_message.setText(listInfo.get(position).getItem_no());
        tv_price.setText(listInfo.get(position).getSale_price()+Application.mContext.getString(R.string.yuan));

        return convertView;
    }
}
