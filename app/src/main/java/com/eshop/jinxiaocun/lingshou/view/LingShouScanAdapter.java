package com.eshop.jinxiaocun.lingshou.view;


import android.graphics.Paint;
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
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

public class LingShouScanAdapter extends MyBaseAdapter {

    private List<GetClassPluResult> listInfo;

    public void setListInfo(List<GetClassPluResult> listInfo) {
        this.listInfo = listInfo;
    }

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
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_lingshou_scan,parent,false);
        }
        TextView item_name = ViewHolderUtils.get(convertView, R.id.item_name);
        TextView item_no = ViewHolderUtils.get(convertView, R.id.item_no);
        TextView sale_price = ViewHolderUtils.get(convertView, R.id.sale_price);
        TextView sale_price_old = ViewHolderUtils.get(convertView, R.id.sale_price_old);
        TextView sale_qnty = ViewHolderUtils.get(convertView, R.id.sale_qnty);
        TextView sale_total = ViewHolderUtils.get(convertView, R.id.sale_total);

        sale_qnty.setText(listInfo.get(position).getSale_qnty()+listInfo.get(position).getUnit_no());
        sale_price.setText("售价￥"+MyUtils.formatFloat2(Float.parseFloat(listInfo.get(position).getSale_price())));
        if( listInfo.get(position).getSale_price_beforModify().equals(listInfo.get(position).getSale_price())){
            sale_price_old.setVisibility(View.INVISIBLE);
        }else{
            sale_price_old.setVisibility(View.VISIBLE);
        }
        sale_price_old.setText("原价￥"+MyUtils.formatFloat2(Float.parseFloat(listInfo.get(position).getSale_price_beforModify())));
        sale_price_old.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        item_no.setText(listInfo.get(position).getItem_no()==null?"":listInfo.get(position).getItem_no());
        item_name.setText(listInfo.get(position).getItem_name());
        sale_total.setText("合计￥"+ MyUtils.formatFloat2(Float.parseFloat(listInfo.get(position).getSale_qnty())*
                Float.parseFloat(listInfo.get(position).getSale_price()))+"");
        return super.getView(position,convertView,parent);
    }


}
