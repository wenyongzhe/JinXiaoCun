package com.eshop.jinxiaocun.pifaxiaoshou.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;
import java.util.Locale;

/**
 * @Author Lu An
 * 创建时间  2018/9/27
 * 描述
 */

public class PifaTuihuoScanAdapter extends BaseAdapter {

    private List<GetClassPluResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public PifaTuihuoScanAdapter(List<GetClassPluResult> listInfo) {
        this.listInfo = listInfo;
        inflater = LayoutInflater.from(Application.mContext);
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
            convertView = inflater.inflate(R.layout.item_list_pifa_tuihuo_scan,parent,false);
        }
        TextView tv_xuhao = ViewHolderUtils.get(convertView, R.id.tv_xuhao);
        TextView tv_product_name = ViewHolderUtils.get(convertView, R.id.tv_product_name);
        TextView tv_product_code = ViewHolderUtils.get(convertView, R.id.tv_product_code);
        TextView tv_zi_code = ViewHolderUtils.get(convertView, R.id.tv_zi_code);
        TextView tv_je = ViewHolderUtils.get(convertView, R.id.tv_je);
        TextView tv_price = ViewHolderUtils.get(convertView, R.id.tv_price);
        TextView tv_number = ViewHolderUtils.get(convertView, R.id.tv_number);

        tv_product_name.setSelected(true);
        tv_product_code.setSelected(true);
        tv_zi_code.setSelected(true);
        tv_xuhao.setText((position+1)+"");
        tv_zi_code.setText(listInfo.get(position).getItem_subno());
        tv_product_name.setText(listInfo.get(position).getItem_name());
        tv_product_code.setText(listInfo.get(position).getItem_no());
        float zje = MyUtils.convertToFloat(listInfo.get(position).getBase_price(),0)
                *MyUtils.convertToFloat(listInfo.get(position).getSale_qnty(),1);
        tv_je.setText(String.format(Locale.CANADA, "%.2f",zje));
        tv_price.setText(listInfo.get(position).getBase_price());
        tv_number.setText(listInfo.get(position).getSale_qnty()+listInfo.get(position).getUnit_no());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<GetClassPluResult> listInfo) {
        this.listInfo = listInfo;
        notifyDataSetChanged();
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

}