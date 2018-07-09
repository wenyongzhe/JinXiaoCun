package com.eshop.jinxiaocun.main.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;


public class MenuAdapter extends BaseAdapter {
	private LayoutInflater inflater = null;
	private int[] iconIds;
	private int[] nameIds;

	public MenuAdapter(Context context, int[] iconIds, int[] nameIds) {
		this.iconIds = iconIds;
		this.nameIds = nameIds;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return iconIds == null ? 0 : iconIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return iconIds[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.layout_grid_item_menu,parent,false);
		}

		ImageView iv_icon = ViewHolderUtils.get(convertView, R.id.iv_icon);
		TextView tv_title = ViewHolderUtils.get(convertView, R.id.tv_title);

		iv_icon.setBackgroundResource(iconIds[position]);
		tv_title.setText(nameIds[position]);

		return convertView;
	}

}
