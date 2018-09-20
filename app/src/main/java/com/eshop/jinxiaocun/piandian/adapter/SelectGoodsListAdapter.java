package com.eshop.jinxiaocun.piandian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/9/20
 * 描述
 */

public class SelectGoodsListAdapter extends BaseAdapter {

    private List<GetClassPluResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public SelectGoodsListAdapter(List<GetClassPluResult> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_list_pandian_goods_info,parent,false);
        }

        TextView tv_product_name = ViewHolderUtils.get(convertView, R.id.tv_product_name);
        TextView tv_product_code = ViewHolderUtils.get(convertView, R.id.tv_product_code);
        TextView tv_pici_code = ViewHolderUtils.get(convertView, R.id.tv_pici_code);
        TextView tv_product_Date = ViewHolderUtils.get(convertView, R.id.tv_product_Date);
        TextView tv_valid_Date = ViewHolderUtils.get(convertView, R.id.tv_valid_Date);

        tv_product_name.setText(listInfo.get(position).getItem_name());
        tv_product_code.setText(listInfo.get(position).getItem_no());
        tv_pici_code.setText(listInfo.get(position).getItem_barcode());
        tv_product_Date.setText(listInfo.get(position).getProduce_date());
        tv_valid_Date.setText(listInfo.get(position).getValid_date());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<GetClassPluResult> listInfo) {
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
