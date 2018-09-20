package com.eshop.jinxiaocun.othermodel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.othermodel.bean.CustomerInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/9/20
 * 描述
 */

public class PiciInfoListAdapter extends BaseAdapter {

    private List<GoodsPiciInfoBeanResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public PiciInfoListAdapter(List<GoodsPiciInfoBeanResult> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_list_pici_info,parent,false);
        }

        TextView tv_pici_code = ViewHolderUtils.get(convertView, R.id.tv_pici_code);
        TextView tv_sc_Date = ViewHolderUtils.get(convertView, R.id.tv_sc_Date);
        TextView tv_yx_Date = ViewHolderUtils.get(convertView, R.id.tv_yx_Date);
        TextView tv_product_number = ViewHolderUtils.get(convertView, R.id.tv_product_number);

        tv_pici_code.setText(listInfo.get(position).getItem_barcode());
        tv_sc_Date.setText(listInfo.get(position).getProduce_date());
        tv_yx_Date.setText(listInfo.get(position).getValid_date());
        tv_product_number.setText(listInfo.get(position).getStock_qty());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<GoodsPiciInfoBeanResult> listInfo) {
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
