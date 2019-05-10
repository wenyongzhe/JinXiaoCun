package com.eshop.jinxiaocun.widget;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.ListBean;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.GoodGetBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

public class TwoListDetailAdapter extends BaseAdapter {

    private List<GetClassPluResult> listInfo;


    public TwoListDetailAdapter(List<GetClassPluResult> listInfo) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(Application.mContext).inflate(R.layout.item_list_twolist_detail,parent,false);
            mViewHolder = new ViewHolder();
            mViewHolder.id = position;
            mViewHolder.mViewHolderImageView = new ViewHolderImageView();
            mViewHolder.mViewHolderImageView.id = position;
            mViewHolder.mViewHolderImageView.count = 0;

            mViewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            mViewHolder.tv_message = convertView.findViewById(R.id.tv_message);
            mViewHolder.tv_price = convertView.findViewById(R.id.tv_price);
            mViewHolder.iv_plus = convertView.findViewById(R.id.iv_plus);
            mViewHolder.tv_num = convertView.findViewById(R.id.tv_num);
            mViewHolder.iv_minus = convertView.findViewById(R.id.iv_minus);

            mViewHolder.mViewHolderImageView.tv_num = mViewHolder.tv_num;
            mViewHolder.iv_plus.setTag(mViewHolder.mViewHolderImageView);

            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.tvTitle.setSelected(true);
        mViewHolder.tv_message.setSelected(true);
        mViewHolder.tvTitle.setText(listInfo.get(position).getItem_name());
        mViewHolder.tv_message.setText(listInfo.get(position).getItem_no());
        mViewHolder.tv_price.setText(listInfo.get(position).getSale_price()+Application.mContext.getString(R.string.yuan));
        mViewHolder.tv_num.setText(listInfo.get(position).getSale_qnty());

        mViewHolder.iv_plus.setOnClickListener(new View.OnClickListener() {
            int id = position;
            @Override
            public void onClick(View view) {
                int mun = Integer.decode(listInfo.get(id).getSale_qnty())+1;
                listInfo.get(id).setSale_qnty(mun+"");
                notifyDataSetChanged();
            }
        });
        mViewHolder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolderImageView mViewHolderImageView = (ViewHolderImageView) view.getTag();
                mViewHolderImageView.tv_num.setText("3");
            }
        });

        return convertView;
    }

    public class ViewHolder {
        public int id;
        public ViewHolderImageView mViewHolderImageView;
        public TextView tvTitle;
        public TextView tv_message;
        public TextView tv_price;
        public ImageView iv_plus;
        public TextView tv_num;
        public ImageView iv_minus;
    }

    public class ViewHolderImageView {
        public int id;
        public TextView tv_num;
        public int count = 0;


    }

}
