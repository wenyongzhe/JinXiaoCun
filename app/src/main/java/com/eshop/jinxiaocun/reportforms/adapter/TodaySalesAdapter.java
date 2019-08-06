package com.eshop.jinxiaocun.reportforms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.reportforms.bean.SalesGoodsInfo;
import com.eshop.jinxiaocun.reportforms.bean.TodaySalesInfo;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;
import com.eshop.jinxiaocun.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/6
 * Desc:
 */
public class TodaySalesAdapter extends BaseAdapter {

    private List<TodaySalesInfo> mListInfo;
    private LayoutInflater inflater;
    private int itemClickPosition = -1;
    private Context mContext;
    public TodaySalesAdapter(Context context, List<TodaySalesInfo> listInfo) {
        mContext = context;
        this.mListInfo = listInfo;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 5;
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
            convertView = inflater.inflate(R.layout.item_today_sales,parent,false);
        }


        TextView billNo = ViewHolderUtils.get(convertView, R.id.tv_billNo);
        TextView billDate = ViewHolderUtils.get(convertView, R.id.tv_billDate);
        NoScrollListView listView = ViewHolderUtils.get(convertView, R.id.lv_salesGoods);

//        TodaySalesInfo info = mListInfo.get(position);

        billNo.setText("TDS0000"+position);
        billDate.setText(DateUtility.getCurrentTime());

        List<SalesGoodsInfo> goodsInfoList = new ArrayList<>();
        TodaySalesGoodsAdapter adapter = new TodaySalesGoodsAdapter(mContext,goodsInfoList);
        listView.setAdapter(adapter);

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void add(List<TodaySalesInfo> listInfo) {
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