package com.eshop.jinxiaocun.reportforms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.othermodel.bean.SaleFlowRecordResult;
import com.eshop.jinxiaocun.othermodel.bean.SaleQueryBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/6
 * Desc:
 */
public class TodaySalesGoodsAdapter extends BaseAdapter {

    private List<SaleQueryBeanResult> mListInfo;
    private LayoutInflater inflater;
    private int itemClickPosition = -1;
    private Context mContext;

    public TodaySalesGoodsAdapter(Context context, List<SaleQueryBeanResult> listInfo) {
        this.mListInfo = listInfo;
        mContext = context;
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
            convertView = inflater.inflate(R.layout.item_sales_goods,parent,false);
        }


        TextView goodsName = ViewHolderUtils.get(convertView, R.id.tv_goodsName);
        TextView qty = ViewHolderUtils.get(convertView, R.id.tv_qty);
        TextView price = ViewHolderUtils.get(convertView, R.id.tv_price);
        TextView allMoney = ViewHolderUtils.get(convertView, R.id.tv_allMoney);

        SaleQueryBeanResult info = mListInfo.get(position);

        goodsName.setText(info.getItem_name());
        int account = info.getSale_qnty();
        qty.setText(account+"/件");
        price.setText("￥"+info.getSale_price());
        allMoney.setText("￥"+account*info.getSale_price());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void add(List<SaleQueryBeanResult> listInfo) {
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