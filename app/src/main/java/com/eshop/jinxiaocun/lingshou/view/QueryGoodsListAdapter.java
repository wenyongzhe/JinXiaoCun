package com.eshop.jinxiaocun.lingshou.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

public class QueryGoodsListAdapter extends BaseAdapter {

    private List<GetClassPluResult> listInfo;
    Context mContext;

    public QueryGoodsListAdapter(Context mContext,List<GetClassPluResult> listInfo) {
        this.listInfo = listInfo;
        this.mContext = mContext;
    }

    public void setListInfo(List<GetClassPluResult> listInfo) {
        this.listInfo.clear();
        this.listInfo = listInfo;
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
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_query_detail,parent,false);
        }
        TextView tvTitle = ViewHolderUtils.get(convertView, R.id.tvTitle);
        TextView item_no = ViewHolderUtils.get(convertView, R.id.item_no);
        TextView tv_price = ViewHolderUtils.get(convertView, R.id.tv_price);
        TextView item_subno = ViewHolderUtils.get(convertView, R.id.item_subno);
        TextView item_rem = ViewHolderUtils.get(convertView, R.id.item_rem);


        tvTitle.setText(listInfo.get(position).getItem_name());
        item_no.setText(mContext.getString(R.string.item_no)+listInfo.get(position).getItem_no());
        item_subno.setText(mContext.getString(R.string.item_subno)+listInfo.get(position).getItem_subno());
        item_rem.setText(mContext.getString(R.string.item_rem)+listInfo.get(position).getItem_rem());

        tv_price.setText(listInfo.get(position).getSale_price()+Application.mContext.getString(R.string.yuan));
        return convertView;
    }
}
