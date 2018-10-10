package com.eshop.jinxiaocun.pifaxiaoshou.adapter;

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
 * 创建时间  2018/9/27
 * 描述
 */

public class PifaTuihuoListAdapter extends BaseAdapter {

    private List<DanJuMainBeanResultItem> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;
    private String mCheckflag = "0";//0未审核，1审核

    public PifaTuihuoListAdapter(List<DanJuMainBeanResultItem> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_list_pifa_tuihuo,parent,false);
        }
        TextView tvOrderStatus = ViewHolderUtils.get(convertView, R.id.tv_order_status);
        TextView tvOrderNo = ViewHolderUtils.get(convertView, R.id.tv_order_no);
        TextView tvOrderType = ViewHolderUtils.get(convertView, R.id.tv_order_type);
        TextView tvShopname = ViewHolderUtils.get(convertView, R.id.tv_shopname);
        TextView tvAllGoodsCount = ViewHolderUtils.get(convertView, R.id.tv_allgoodscount);
        TextView tvValidDate = ViewHolderUtils.get(convertView, R.id.tv_valid_Date);

        if(mCheckflag.equals("1")){
            tvOrderStatus.setText("已审核");
        }else{
            tvOrderStatus.setText("未审核");
        }
        tvOrderNo.setSelected(true);
        tvShopname.setSelected(true);
        tvOrderNo.setText(listInfo.get(position).getSheet_No());
        tvOrderType.setText(listInfo.get(position).getSheetType());
        tvShopname.setText(listInfo.get(position).getShopName());
        tvAllGoodsCount.setText(listInfo.get(position).getGoodsNum());
        tvValidDate.setText(listInfo.get(position).getValid_date());

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
