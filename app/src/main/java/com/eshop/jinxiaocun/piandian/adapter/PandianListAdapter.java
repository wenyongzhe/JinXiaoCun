package com.eshop.jinxiaocun.piandian.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/8/24
 * 描述
 */

public class PandianListAdapter extends BaseAdapter {

    private List<DanJuMainBeanResultItem> listInfo;
    private int itemClickPosition = -1;

    public PandianListAdapter(List<DanJuMainBeanResultItem> listInfo) {
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
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_pandian,parent,false);
        }
        TextView tvOrderNo = ViewHolderUtils.get(convertView, R.id.tv_order_no);
        TextView tvShopname = ViewHolderUtils.get(convertView, R.id.tv_shopname);
        TextView tvFanwei = ViewHolderUtils.get(convertView, R.id.tv_pd_fanwei);
        TextView tvCangku = ViewHolderUtils.get(convertView, R.id.tv_Cangku);
        TextView tvAllGoodsCount = ViewHolderUtils.get(convertView, R.id.tv_allgoodscount);
        TextView tvOperName = ViewHolderUtils.get(convertView, R.id.tv_OperName);

        tvOrderNo.setSelected(true);
        tvShopname.setSelected(true);

        tvOrderNo.setText(listInfo.get(position).getSheet_No());
        tvShopname.setText(listInfo.get(position).getShopName());
        tvFanwei.setText(listInfo.get(position).getOper_Range());
        tvCangku.setText(listInfo.get(position).getBranch_No());
        tvAllGoodsCount.setText(listInfo.get(position).getGoodsNum());
        tvOperName.setText(listInfo.get(position).getOper_Name());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<DanJuMainBeanResultItem> listInfo) {
        this.listInfo = listInfo;
        notifyDataSetChanged();
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

}
