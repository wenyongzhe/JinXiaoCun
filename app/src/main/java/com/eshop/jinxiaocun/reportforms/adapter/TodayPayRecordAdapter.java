package com.eshop.jinxiaocun.reportforms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.othermodel.bean.PayRecordResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/6
 * Desc:
 */
public class TodayPayRecordAdapter extends BaseAdapter {

    private List<PayRecordResult> mListInfo;
    private LayoutInflater inflater;
    private int itemClickPosition = -1;

    public TodayPayRecordAdapter(List<PayRecordResult> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_today_pay_record,parent,false);
        }


        TextView sales = ViewHolderUtils.get(convertView, R.id.tv_sales);
        TextView allMoney = ViewHolderUtils.get(convertView, R.id.tv_allMoney);

        PayRecordResult info = mListInfo.get(position);

        sales.setText("销售");
        allMoney.setText("￥"+info.getSale_amount());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void add(List<PayRecordResult> listInfo) {
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