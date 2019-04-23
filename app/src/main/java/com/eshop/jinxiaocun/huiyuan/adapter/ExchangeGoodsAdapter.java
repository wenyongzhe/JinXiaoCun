package com.eshop.jinxiaocun.huiyuan.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.huiyuan.bean.IntegralExchangeGoodsResultItem;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/23
 * Desc:
 */

public class ExchangeGoodsAdapter extends BaseAdapter {

    private List<IntegralExchangeGoodsResultItem> mListInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public ExchangeGoodsAdapter(List<IntegralExchangeGoodsResultItem> listInfo) {
        this.mListInfo = listInfo;
        inflater = LayoutInflater.from(Application.mContext);
    }

    @Override
    public int getCount() {
        return mListInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return mListInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_exchange_goods,parent,false);
        }
        TextView tvGoodsName = ViewHolderUtils.get(convertView, R.id.tv_goods_name);
        TextView tvPrice = ViewHolderUtils.get(convertView, R.id.tv_price);
        TextView tvNumber = ViewHolderUtils.get(convertView, R.id.tv_number);
        TextView tvIntegral = ViewHolderUtils.get(convertView, R.id.tv_integral);

        IntegralExchangeGoodsResultItem info = mListInfo.get(position);
        tvGoodsName.setText(info.getName());
        tvPrice.setText("￥"+0);
        tvNumber.setText(info.getNum()+"");
        tvIntegral.setText(info.getJiFen()+"");

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<IntegralExchangeGoodsResultItem> listInfo) {
        this.mListInfo = listInfo;
        notifyDataSetChanged();
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }
}
