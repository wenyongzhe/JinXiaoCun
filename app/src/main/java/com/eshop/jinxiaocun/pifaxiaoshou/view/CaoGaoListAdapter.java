package com.eshop.jinxiaocun.pifaxiaoshou.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;

import java.util.HashMap;
import java.util.List;

public class CaoGaoListAdapter extends BaseAdapter {

    private List<DanJuMainBeanResultItem> listInfo;


    public CaoGaoListAdapter(List<DanJuMainBeanResultItem> listInfo) {
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
        if(listInfo==null){
            listInfo = (List<DanJuMainBeanResultItem>) new HashMap<>();
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_xiaoshoudan_finish,parent,false);
        }
        TextView tv_OperName = ViewHolderUtils.get(convertView, R.id.tv_OperName);
        tv_OperName.setText(listInfo.get(position).getOper_Name());
        TextView tv_GoodsNum = ViewHolderUtils.get(convertView, R.id.tv_GoodsNum);
        tv_GoodsNum.setText(listInfo.get(position).getGoodsNum());

        TextView tv_SheetNo = ViewHolderUtils.get(convertView, R.id.tv_SheetNo);
        tv_SheetNo.setText(listInfo.get(position).getSheet_No());
        TextView tv_OrdAmt = ViewHolderUtils.get(convertView, R.id.tv_OrdAmt);
        tv_OrdAmt.setText(listInfo.get(position).getOrd_Amt());

        TextView tv_OperDate = ViewHolderUtils.get(convertView, R.id.tv_OperDate);
        tv_OperDate.setText(listInfo.get(position).getOper_Date());
        TextView tv_BranchNo = ViewHolderUtils.get(convertView, R.id.tv_BranchNo);
        tv_BranchNo.setText(listInfo.get(position).getBranch_No());
        return convertView;
    }
}
