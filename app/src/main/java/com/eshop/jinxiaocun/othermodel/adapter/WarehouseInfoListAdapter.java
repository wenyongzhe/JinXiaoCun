package com.eshop.jinxiaocun.othermodel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.othermodel.bean.WarehouseInfoBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/9/28
 * 描述
 */

public class WarehouseInfoListAdapter extends BaseAdapter {

    private List<WarehouseInfoBeanResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public WarehouseInfoListAdapter(List<WarehouseInfoBeanResult> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_list_warehouse_info,parent,false);
        }

        TextView tvProviderName = ViewHolderUtils.get(convertView, R.id.tv_warehouse_name);
        TextView tvProviderCode = ViewHolderUtils.get(convertView, R.id.tv_warehouse_code);
        TextView tvContact = ViewHolderUtils.get(convertView, R.id.tv_warehouse_zjm);

        tvProviderName.setSelected(true);
        tvProviderCode.setSelected(true);
        tvProviderName.setText(listInfo.get(position).getName());
        tvProviderCode.setText(listInfo.get(position).getId());
        tvContact.setText(listInfo.get(position).getZjm());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<WarehouseInfoBeanResult> listInfo) {
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
