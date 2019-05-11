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
    private List<GetClassPluResult> selectList;

    public TwoListDetailAdapter(List<GetClassPluResult> listInfo,List<GetClassPluResult> selectList) {
        this.listInfo = listInfo;
        this.selectList = selectList;
        clearData();
        initData();

    }

    private void initData(){
        for(int j=0; j<listInfo.size(); j++){
            GetClassPluResult mGetClassPluResult = listInfo.get(j);
            for(int k=0; k<selectList.size(); k++){
                GetClassPluResult mGetClassPluResult2 = selectList.get(k);
                if (mGetClassPluResult.getItem_no().trim().equalsIgnoreCase(mGetClassPluResult2.getItem_no().trim())) {
                    mGetClassPluResult.setSale_qnty(mGetClassPluResult2.getSale_qnty());
                    break;
                }
            }
        }
    }

    private void clearData(){
        for(int k=0; k<listInfo.size(); k++){
            GetClassPluResult mGetClassPluResult2 = listInfo.get(k);
            mGetClassPluResult2.setSale_qnty("0");
        }
    }

    public List<GetClassPluResult> getSelectList() {
        return selectList;
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
        if(listInfo.get(position).getSale_qnty().equals("0")){
            mViewHolder.tv_num.setVisibility(View.INVISIBLE);
            mViewHolder.iv_plus.setVisibility(View.INVISIBLE);
            mViewHolder.iv_minus.setVisibility(View.INVISIBLE);
        }else {
            mViewHolder.tv_num.setVisibility(View.VISIBLE);
            mViewHolder.iv_plus.setVisibility(View.VISIBLE);
            mViewHolder.iv_minus.setVisibility(View.VISIBLE);
        }

        mViewHolder.tvTitle.setSelected(true);
        mViewHolder.tv_message.setSelected(true);
        mViewHolder.tvTitle.setText(listInfo.get(position).getItem_name());
        mViewHolder.tv_message.setText(listInfo.get(position).getItem_no());
        mViewHolder.tv_price.setText(listInfo.get(position).getSale_price()+Application.mContext.getString(R.string.yuan));
        for(int j=0; j<listInfo.size(); j++){
            GetClassPluResult mGetClassPluResult = listInfo.get(j);
            for(int k=0; k<selectList.size(); k++){
                GetClassPluResult mGetClassPluResult2 = selectList.get(k);
                if (mGetClassPluResult.getItem_no().trim().equalsIgnoreCase(mGetClassPluResult2.getItem_no().trim())) {
                    mGetClassPluResult.setSale_qnty(mGetClassPluResult2.getSale_qnty());
                    break;
                }
            }
        }
        mViewHolder.tv_num.setText(listInfo.get(position).getSale_qnty());

        mViewHolder.iv_plus.setOnClickListener(new View.OnClickListener() {
            int id = position;
            @Override
            public void onClick(View view) {
                GetClassPluResult mGetClassPluResult2 = listInfo.get(id);
                int mun = Integer.decode(mGetClassPluResult2.getSale_qnty())+1;
                if(mun==1){
                    mGetClassPluResult2.setSale_qnty(mun+"");
                    selectList.add(mGetClassPluResult2);
                }else if(mun>1){
                    for(int j=0; j<selectList.size(); j++) {
                        GetClassPluResult mGetClassPluResult = selectList.get(j);
                        if (mGetClassPluResult.getItem_no().trim().equalsIgnoreCase(mGetClassPluResult2.getItem_no().trim())) {
                            int mun2 = Integer.decode(mGetClassPluResult.getSale_qnty())+1;
                            mGetClassPluResult.setSale_qnty(mun2+"");
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
        mViewHolder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetClassPluResult mGetClassPluResult2 = listInfo.get(position);
                int mun = Integer.decode(mGetClassPluResult2.getSale_qnty())-1;
                if(mun<1){
                    mGetClassPluResult2.setSale_qnty("0");
                    for(int i=0; i<selectList.size(); i++){
                        if(mGetClassPluResult2.getItem_no().equals(selectList.get(i).getItem_no())){
                            selectList.remove(i);
                            break;
                        }
                    }
                }else if(mun>0){
                    for(int j=0; j<selectList.size(); j++) {
                        GetClassPluResult mGetClassPluResult = selectList.get(j);
                        if (mGetClassPluResult.getItem_no().trim().equalsIgnoreCase(mGetClassPluResult2.getItem_no().trim())) {
                            int mun2 = Integer.decode(mGetClassPluResult.getSale_qnty())-1;
                            mGetClassPluResult.setSale_qnty(mun2+"");
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder mViewHolder = (ViewHolder) view.getTag();
                mViewHolder.iv_plus.performClick();
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
