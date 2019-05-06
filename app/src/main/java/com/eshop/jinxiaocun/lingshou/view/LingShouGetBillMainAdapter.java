package com.eshop.jinxiaocun.lingshou.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.MyBaseAdapter;
import com.eshop.jinxiaocun.lingshou.bean.GetBillMain;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

public class LingShouGetBillMainAdapter extends MyBaseAdapter {

    private List<GetBillMain> listInfo;


    public LingShouGetBillMainAdapter(List<GetBillMain> listInfo) {
        this.listInfo = listInfo;
    }

    public void setListInfo(List<GetBillMain> listInfo) {
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
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_get_bill,parent,false);
        }
        TextView item_name = ViewHolderUtils.get(convertView, R.id.item_name);
        TextView item_no = ViewHolderUtils.get(convertView, R.id.item_no);
        TextView sale_price = ViewHolderUtils.get(convertView, R.id.sale_price);
        TextView sale_qnty = ViewHolderUtils.get(convertView, R.id.sale_qnty);
        TextView sale_total = ViewHolderUtils.get(convertView, R.id.sale_total);

        item_name.setText(DateUtility.stampToDate(listInfo.get(position).getTimeNo()));
        sale_price.setText("￥"+listInfo.get(position).getTotal());
        sale_qnty.setText("数量："+listInfo.get(position).getCount());
        return super.getView(position,convertView,parent);
    }


}
