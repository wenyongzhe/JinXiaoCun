package com.eshop.jinxiaocun.piandian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.piandian.bean.PandianFanweiBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;


public class SelectPandianFanweiAdapter extends BaseAdapter {

    private List<PandianFanweiBeanResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;

    public SelectPandianFanweiAdapter(Context context, List<PandianFanweiBeanResult> listInfo) {
        super();
        this.listInfo = listInfo;
        inflater = LayoutInflater.from(context);
    }

    public void setIsSelected(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }

    @Override
    public int getCount() {
        return listInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return listInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_pandain_fanwei_lbpp,parent,false);
        }
        TextView tv_typeId = ViewHolderUtils.get(convertView, R.id.tv_typeId);
        TextView tv_typeName = ViewHolderUtils.get(convertView, R.id.tv_typeName);

        tv_typeId.setText(listInfo.get(position).getType_id());
        tv_typeName.setSelected(true);
        tv_typeName.setText(listInfo.get(position).getType_name());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }

        return convertView;
    }
}
