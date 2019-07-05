package com.eshop.jinxiaocun.caigou.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.DateUtility;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/9/28
 * Desc:
 */

public class CaigouRucangListAdapter extends BaseAdapter {

    private List<DanJuMainBeanResultItem> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;
    private String mCheckflag = "0";//0未审核，1审核

    public CaigouRucangListAdapter(List<DanJuMainBeanResultItem> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_list_caigou_rucang,parent,false);
        }
        TextView tvOrderNo = ViewHolderUtils.get(convertView, R.id.tv_order_no);
        TextView tvValidDate = ViewHolderUtils.get(convertView, R.id.tv_valid_Date);

        TextView tvSupplierName = ViewHolderUtils.get(convertView, R.id.tv_supplier_Name);

        TextView tvAllGoodsCount = ViewHolderUtils.get(convertView, R.id.tv_allgoodscount);
        TextView tvAllMoney = ViewHolderUtils.get(convertView, R.id.tv_all_Money);
        TextView tvOrderStatus = ViewHolderUtils.get(convertView, R.id.tv_order_status);

        tvOrderNo.setText(listInfo.get(position).getSheet_No());
        tvValidDate.setText(DateUtility.getStrDate3(listInfo.get(position).getValid_date()));
        tvSupplierName.setText(listInfo.get(position).getSupplyName());
        tvAllGoodsCount.setText(listInfo.get(position).getGoodsNum());
        tvAllMoney.setText("0.00元");
        if(mCheckflag.equals("1")){
            tvOrderStatus.setText("已审核");
        }else{
            tvOrderStatus.setText("未审核");
        }

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<DanJuMainBeanResultItem> listInfo,String checkflag) {
        this.listInfo = listInfo;
        this.mCheckflag = checkflag;
        notifyDataSetChanged();
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

}
