package com.eshop.jinxiaocun.huiyuan.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
 * Date: 2019/4/21
 * Desc:
 */

public class IntegralExchangeGoodsAdapter extends BaseAdapter {

    private List<IntegralExchangeGoodsResultItem> mListInfo;
    private LayoutInflater inflater;
    private int itemClickPosition = -1;
    private SelectGoodsCallback mSelectGoodsCallback;

    public IntegralExchangeGoodsAdapter(List<IntegralExchangeGoodsResultItem> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_integral_exchange_goods,parent,false);
        }
        TextView tvGoodsName = ViewHolderUtils.get(convertView, R.id.tv_goods_name);
        TextView tvPrice = ViewHolderUtils.get(convertView, R.id.tv_price);
        TextView tvNumber = ViewHolderUtils.get(convertView, R.id.tv_number);
        TextView tvIntegral = ViewHolderUtils.get(convertView, R.id.tv_integral);
        TextView tvStartDate = ViewHolderUtils.get(convertView, R.id.tv_start_date);
        TextView tvEndDate = ViewHolderUtils.get(convertView, R.id.tv_end_date);
        ImageView ivSelect = ViewHolderUtils.get(convertView, R.id.iv_select);

        final IntegralExchangeGoodsResultItem info = mListInfo.get(position);
        tvGoodsName.setText(info.getName());
        tvPrice.setText("￥"+0);
        tvNumber.setText(info.getSelectNum()+"/"+info.getNum());
        tvIntegral.setText(info.getJiFen()+"");

        if(!TextUtils.isEmpty(info.getBeginTime())){
            tvStartDate.setText(info.getBeginTime());
        }else{//如果没有起始时间 就显示当天
            tvStartDate.setText(DateUtility.getCurrentDate());
        }

        if(!TextUtils.isEmpty(info.getEndTime())){
            tvEndDate.setText(info.getEndTime());
        }else{//如果没有结束时间 就显示当天
            tvEndDate.setText(DateUtility.getCurrentDate());
        }

        if(info.isSelect()){
            ivSelect.setImageResource(R.drawable.checkbox_checked);
        }else{
            ivSelect.setImageResource(R.drawable.checkbox_unchecked);
        }

        ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectGoodsCallback!=null){
                    mSelectGoodsCallback.onSelectGoods(info);
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

    public void setListInfo(List<IntegralExchangeGoodsResultItem> listInfo) {
        this.mListInfo = listInfo;
        notifyDataSetChanged();
    }

    public void setSelectGoodsCallbck(SelectGoodsCallback selectGoodsCallback){
        this.mSelectGoodsCallback = selectGoodsCallback;
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

    public interface SelectGoodsCallback{
        void onSelectGoods(IntegralExchangeGoodsResultItem info);
    }


}
