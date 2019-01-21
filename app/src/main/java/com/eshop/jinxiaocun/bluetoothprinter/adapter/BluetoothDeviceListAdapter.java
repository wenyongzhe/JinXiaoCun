package com.eshop.jinxiaocun.bluetoothprinter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.bluetoothprinter.entity.BluetoothDeviceInfo;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * Created by zhangfan on 2018/1/15 0015.
 *
 */

public class BluetoothDeviceListAdapter extends BaseAdapter {

    private List<BluetoothDeviceInfo> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;
    private Context mConext;

   public BluetoothDeviceListAdapter(Context context, List<BluetoothDeviceInfo> listInfo){
       super();
       this.listInfo = listInfo;
       inflater = LayoutInflater.from(context);
       mConext = context;
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
            convertView = inflater.inflate(R.layout.item_list_bluetooth,parent,false);
        }

        TextView tv_BluetoothName = ViewHolderUtils.get(convertView, R.id.tv_BluetoothName);
        TextView tv_PairedStatus = ViewHolderUtils.get(convertView, R.id.tv_PairedStatus);

        tv_BluetoothName.setText(listInfo.get(position).getBluetoothName());
        tv_PairedStatus.setText(listInfo.get(position).getPairedStatus());

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }

        return convertView;
    }

    public List<BluetoothDeviceInfo> getListInfo() {
        return listInfo;
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }
}
