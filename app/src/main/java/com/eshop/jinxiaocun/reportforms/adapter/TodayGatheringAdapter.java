package com.eshop.jinxiaocun.reportforms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.reportforms.bean.TodayGatheringInfo;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;
import com.eshop.jinxiaocun.widget.NoScrollListView;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/8/12
 * Desc:
 */
public class TodayGatheringAdapter extends BaseAdapter {

    private List<TodayGatheringInfo> mListInfo;
    private LayoutInflater inflater;
    private int itemClickPosition = -1;
    private Context mContext;
    private PrintCallback mPrintCallback;
    public TodayGatheringAdapter(Context context, List<TodayGatheringInfo> listInfo) {
        mContext = context;
        this.mListInfo = listInfo;
        inflater = LayoutInflater.from(mContext);
    }
    public void setCallbck(PrintCallback printCallback){
        mPrintCallback = printCallback;
    }
    @Override
    public int getCount() {
        return mListInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return mListInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_today_gathering,parent,false);
        }
        TextView billNo = ViewHolderUtils.get(convertView, R.id.tv_billNo);
        TextView billDate = ViewHolderUtils.get(convertView, R.id.tv_billDate);
        NoScrollListView listView = ViewHolderUtils.get(convertView, R.id.lv_salesGoods);

        final TodayGatheringInfo info = mListInfo.get(position);

        billNo.setText(info.getBillNo());
        billDate.setText(info.getBillDate());

        TodayPayRecordAdapter adapter = new TodayPayRecordAdapter(info.getPayRecordInfos());
        listView.setAdapter(adapter);

        billNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPrintCallback!=null){
                    mPrintCallback.onClickBillNo(info);
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

    public void add(List<TodayGatheringInfo> listInfo) {
        this.mListInfo = listInfo;
        notifyDataSetChanged();
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }
    public  interface PrintCallback {
        void onClickBillNo(TodayGatheringInfo item);
    }
}