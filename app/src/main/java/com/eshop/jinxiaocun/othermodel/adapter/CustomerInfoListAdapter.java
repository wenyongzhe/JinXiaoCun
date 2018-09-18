package com.eshop.jinxiaocun.othermodel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.othermodel.bean.CustomerInfoBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/9/18
 * 描述
 */

public class CustomerInfoListAdapter extends BaseAdapter {

    private List<CustomerInfoBeanResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public CustomerInfoListAdapter(List<CustomerInfoBeanResult> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_list_customer_info,parent,false);
        }

        TextView tv_customer_name = ViewHolderUtils.get(convertView, R.id.tv_customer_name);
        TextView tv_customer_id = ViewHolderUtils.get(convertView, R.id.tv_customer_id);
        TextView tv_customer_zjm = ViewHolderUtils.get(convertView, R.id.tv_customer_zjm);

        tv_customer_name.setText(listInfo.get(position).getName());
        tv_customer_id.setText(listInfo.get(position).getId());
        tv_customer_zjm.setText(listInfo.get(position).getZjm());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<CustomerInfoBeanResult> listInfo) {
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
