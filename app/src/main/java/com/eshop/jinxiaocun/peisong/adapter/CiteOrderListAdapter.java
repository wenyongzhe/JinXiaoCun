package com.eshop.jinxiaocun.peisong.adapter;

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
 * 创建时间  2018/10/12
 * 描述
 */

public class CiteOrderListAdapter extends BaseAdapter {

    private List<DanJuMainBeanResultItem> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;
    private String mCheckflag = "0";//0未审核，1审核
    private String mSheetType="";//单据类型

    public CiteOrderListAdapter(List<DanJuMainBeanResultItem> listInfo) {
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
            convertView = inflater.inflate(R.layout.item_list_cite_order,parent,false);
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
        tvOrderType.setText(mSheetType);
        tvShopname.setText(listInfo.get(position).getYHShopName());
        tvAllGoodsCount.setText(listInfo.get(position).getGoodsNum());
        tvValidDate.setText(listInfo.get(position).getValid_date());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<DanJuMainBeanResultItem> listInfo,String checkflag,String sheetType) {
        this.listInfo = listInfo;
        this.mCheckflag = checkflag;
        this.mSheetType = sheetType;
        notifyDataSetChanged();
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

}
