package com.eshop.jinxiaocun.piandian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/9
 * Desc:
 */

public class PandianScanAdapter extends BaseAdapter {
    private List<PandianDetailBeanResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public PandianScanAdapter(Context context, List<PandianDetailBeanResult> listInfo){
        super();
        this.listInfo = listInfo;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return listInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_pandian_scan,parent,false);
        }

        PandianDetailBeanResult obj = listInfo.get(position);
        TextView tv_product_name = ViewHolderUtils.get(convertView, R.id.tv_product_name);
        TextView tv_product_code = ViewHolderUtils.get(convertView, R.id.tv_product_code);
        TextView tv_product_pici = ViewHolderUtils.get(convertView, R.id.tv_product_pici);
        TextView tv_spec = ViewHolderUtils.get(convertView, R.id.tv_spec);
        TextView tv_xsprice = ViewHolderUtils.get(convertView, R.id.tv_xsprice);
        TextView tv_unit = ViewHolderUtils.get(convertView, R.id.tv_unit);
        TextView tv_pd_number = ViewHolderUtils.get(convertView, R.id.tv_pd_number);
        TextView tv_pd_store_number = ViewHolderUtils.get(convertView, R.id.tv_pd_store_number);


        tv_product_name.setSelected(true);
        tv_product_code.setSelected(true);
        tv_product_pici.setSelected(true);
        tv_product_name.setText(obj.getItem_name());
        tv_product_code.setText(obj.getItem_no());
        tv_product_pici.setText(obj.getItem_barcode());
        tv_spec.setText(obj.getItem_size());
        tv_xsprice.setText(obj.getSale_price()+"");
        tv_unit.setText(obj.getUnit_no());
        tv_pd_number.setText(obj.getCheck_qty()+"");
        tv_pd_store_number.setText(obj.getStock_qty()+"");


        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public List<PandianDetailBeanResult> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<PandianDetailBeanResult> listInfo) {
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
