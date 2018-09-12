package com.eshop.jinxiaocun.stock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.stock.bean.StockCheckBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/12
 * Desc:
 */

public class StockCheckAdapter extends BaseAdapter {

    private List<StockCheckBeanResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public StockCheckAdapter(Context context, List<StockCheckBeanResult> listInfo){
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
            convertView = inflater.inflate(R.layout.item_list_stock_check,parent,false);
        }

        StockCheckBeanResult obj = listInfo.get(position);
        TextView tv_product_name = ViewHolderUtils.get(convertView, R.id.tv_product_name);
        TextView tv_product_code = ViewHolderUtils.get(convertView, R.id.tv_product_code);
        TextView tv_shopinfo = ViewHolderUtils.get(convertView, R.id.tv_shopinfo);
        TextView tv_spec = ViewHolderUtils.get(convertView, R.id.tv_spec);
        TextView tv_unit = ViewHolderUtils.get(convertView, R.id.tv_unit);
        TextView tv_store = ViewHolderUtils.get(convertView, R.id.tv_store);
        TextView tv_pihao = ViewHolderUtils.get(convertView, R.id.tv_pihao);
        TextView tv_begin_date = ViewHolderUtils.get(convertView, R.id.tv_begin_date);
        TextView tv_end_date = ViewHolderUtils.get(convertView, R.id.tv_end_date);

        tv_product_name.setSelected(true);
        tv_shopinfo.setSelected(true);


        tv_product_name.setText(obj.getItemName());
        tv_product_code.setText(obj.getItemCode());
        tv_shopinfo.setText(obj.getShopInfo());
        tv_spec.setText(obj.getItemSize());
        tv_unit.setText(obj.getItemUnitNo());
        tv_store.setText(obj.getItemStock());
        tv_pihao.setText(obj.getItemBatchNo());
        tv_begin_date.setText(obj.getBeginDate());
        tv_end_date.setText(obj.getEndDate());


        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public List<StockCheckBeanResult> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<StockCheckBeanResult> listInfo) {
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
