package com.eshop.jinxiaocun.piandian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/8/24
 * 描述
 */

public class CheckNoPandianGoodsListAdapter extends BaseAdapter {

    private List<PandianDetailBeanResult> listInfo;
    private int itemClickPosition = -1;
    private CallbackInterface mCallback;
    private Context mContext;

    public CheckNoPandianGoodsListAdapter(Context context,List<PandianDetailBeanResult> listInfo) {
        this.listInfo = listInfo;
        this.mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_check_no_pandian_goods,parent,false);
        }
        TextView tvGoodsName = ViewHolderUtils.get(convertView, R.id.tv_pd_goodsName);
        TextView tvPiciNo = ViewHolderUtils.get(convertView, R.id.tv_pd_piciNo);
        TextView tvSpec = ViewHolderUtils.get(convertView, R.id.tv_pd_spec);
        TextView tvInprice = ViewHolderUtils.get(convertView, R.id.tv_pd_Inprice);
        TextView tvStore_number = ViewHolderUtils.get(convertView, R.id.tv_pd_store_number);
        TextView tvAdd = ViewHolderUtils.get(convertView, R.id.tv_pd_add);

        tvGoodsName.setSelected(true);
        tvPiciNo.setSelected(true);

        tvGoodsName.setText(listInfo.get(position).getItem_name());
        tvPiciNo.setText(listInfo.get(position).getItem_barcode());
        tvSpec.setText(listInfo.get(position).getItem_size());
        tvInprice.setText(listInfo.get(position).getIn_price()+"");
        tvStore_number.setText(listInfo.get(position).getStock_qty()+"/"+listInfo.get(position).getUnit_no());

        final int pos = position;
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback !=null){
                    mCallback.onClickAddPandian(pos);
                }
            }
        });

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<PandianDetailBeanResult> listInfo) {
        this.listInfo = listInfo;
        notifyDataSetChanged();
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

    public void setCallback(CallbackInterface callback){
        mCallback = callback;
    }

    public interface CallbackInterface{
        void onClickAddPandian(int position);
    }


}
