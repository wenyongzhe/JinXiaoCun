package com.eshop.jinxiaocun.lingshou.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.SaleFlowBean;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.base.view.MyBaseAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

public class LingShouScanAdapter extends MyBaseAdapter {

    private List<GetClassPluResult> listInfo;


    public LingShouScanAdapter(List<GetClassPluResult> listInfo) {
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
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_lingshou,parent,false);
        }
        TextView item_name = ViewHolderUtils.get(convertView, R.id.item_name);
        TextView item_no = ViewHolderUtils.get(convertView, R.id.item_no);
        TextView item_subno = ViewHolderUtils.get(convertView, R.id.item_subno);
        TextView sale_price = ViewHolderUtils.get(convertView, R.id.sale_price);
        TextView sale_qnty = ViewHolderUtils.get(convertView, R.id.sale_qnty);
        TextView sale_beforprice = ViewHolderUtils.get(convertView, R.id.sale_beforprice);
        TextView list_item_Pici_Name = ViewHolderUtils.get(convertView, R.id.list_item_Pici_Name);

        sale_qnty.setText(listInfo.get(position).getSale_qnty());
        sale_price.setText(listInfo.get(position).getSale_price());
        item_no.setText(listInfo.get(position).getItem_no()==null?"":listInfo.get(position).getItem_no());
        item_name.setText(listInfo.get(position).getItem_name());
        sale_beforprice.setText(listInfo.get(position).getSale_price());
        list_item_Pici_Name.setText(listInfo.get(position).getItem_barcode());
        item_subno.setText(listInfo.get(position).getItem_subno());
        return super.getView(position,convertView,parent);
    }


}
