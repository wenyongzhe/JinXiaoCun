package com.eshop.jinxiaocun.turnedpurchase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.turnedpurchase.bean.ReturnedPurchaseBean;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/7/17
 * Desc:
 */
public class ReturnedPurchaseByBillAdapter extends BaseAdapter {

    private List<ReturnedPurchaseBean> listInfo;
    private LayoutInflater inflater;
    private int itemClickPosition = -1;

    public ReturnedPurchaseByBillAdapter(List<ReturnedPurchaseBean> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_returned_purchase_by_bill, parent, false);
        }
        TextView billType = ViewHolderUtils.get(convertView, R.id.tv_billType);//单据类型
        TextView price = ViewHolderUtils.get(convertView, R.id.tv_price);//价格
        TextView reQty = ViewHolderUtils.get(convertView, R.id.tv_reQty);//可退数量
        TextView qty = ViewHolderUtils.get(convertView, R.id.tv_qty);//退货数量

        ReturnedPurchaseBean info = listInfo.get(position);

        billType.setText(info.getBillType());
        price.setText(String.format("￥%s",info.getPrice()));
        reQty.setText(MyUtils.convertToString(info.getReQty(),"0"));
        qty.setText(MyUtils.convertToString(info.getQty(),"0"));

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void add(List<ReturnedPurchaseBean> listInfo) {
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