package com.eshop.jinxiaocun.huiyuan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.huiyuan.bean.ExpenseCheckResultItem;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Author: 安仔夏天勤奋
 * Date: 2019/4/24
 * Desc:
 */

public class ExpenseCheckAdapter extends BaseAdapter {

    private List<ExpenseCheckResultItem> mListInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public ExpenseCheckAdapter(List<ExpenseCheckResultItem> listInfo) {
        this.mListInfo = listInfo;
        inflater = LayoutInflater.from(Application.mContext);
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
            convertView = inflater.inflate(R.layout.item_expense_check_list,parent,false);
        }
        TextView tvFlowNo = ViewHolderUtils.get(convertView, R.id.tv_flow_no);
        TextView tvBranchName = ViewHolderUtils.get(convertView, R.id.tv_branch_name);
        TextView tvCashierName = ViewHolderUtils.get(convertView, R.id.tv_cashier_name);
        TextView tvPayTime = ViewHolderUtils.get(convertView, R.id.tv_pay_time);
        TextView tvAmount = ViewHolderUtils.get(convertView, R.id.tv_amount);
        TextView tvRemarks = ViewHolderUtils.get(convertView, R.id.tv_remarks);

        ExpenseCheckResultItem info = mListInfo.get(position);
        tvFlowNo.setText(""+info.getFlow_no());
        tvBranchName.setText(""+info.getBranch_name());
        tvCashierName.setText(""+info.getCashier_id());
        tvPayTime.setText(""+info.getPay_time());
        tvAmount.setText(""+info.getAmount());
        tvRemarks.setText(""+info.getMemo());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public void setListInfo(List<ExpenseCheckResultItem> listInfo) {
        this.mListInfo = listInfo;
        notifyDataSetChanged();
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

}
