package com.eshop.jinxiaocun.turnedpurchase.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
    private ModifyCallback mModifyCallback;
    private Context mContext;

    public ReturnedPurchaseByBillAdapter(Context context,List<ReturnedPurchaseBean> listInfo) {
        mContext=context;
        this.listInfo = listInfo;
        inflater = LayoutInflater.from(Application.mContext);
    }

    public void setCallback(ModifyCallback modifyCallback){
        mModifyCallback = modifyCallback;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_returned_purchase_by_bill, parent, false);
        }
        TextView goodsName = ViewHolderUtils.get(convertView, R.id.tv_goodsName);//商品名称
        TextView price = ViewHolderUtils.get(convertView, R.id.tv_price);//价格
        TextView reQty = ViewHolderUtils.get(convertView, R.id.tv_reQty);//可退数量
        final TextView qty = ViewHolderUtils.get(convertView, R.id.tv_qty);//退货数量

        final ReturnedPurchaseBean info = listInfo.get(position);
        goodsName.setSelected(true);
        goodsName.setText(info.getItem_name());
        price.setText(String.format("￥%s",info.getSale_price()));
        reQty.setText(MyUtils.convertToString(info.getRe_qty(),"0"));
        qty.setText(MyUtils.convertToString(info.getSale_qnty(),"0"));
        qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mModifyCallback!=null){
                    mModifyCallback.onModifyReQty(position,qty.getText().toString().trim());
                }
            }
        });

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


    public interface ModifyCallback{
        void onModifyReQty(int position ,String qty);
    }


}