package com.eshop.jinxiaocun.piandian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.piandian.bean.PandianPihaoHuoquBeanResult;
import com.eshop.jinxiaocun.utils.ViewHolderUtils;

import java.util.List;

/**
 * @Author Lu An
 * 创建时间  2018/8/31 0031
 * 描述
 */

public class PandianPihaoListAdapter extends BaseAdapter {
    private List<PandianPihaoHuoquBeanResult> listInfo;
    private LayoutInflater inflater = null;
    private int itemClickPosition = -1;
    private Context mContext;

    public PandianPihaoListAdapter(Context context, List<PandianPihaoHuoquBeanResult> listInfo){
        super();
        this.listInfo = listInfo;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.item_list_pandianpihao,parent,false);
        }

        PandianPihaoHuoquBeanResult obj = listInfo.get(position);
        TextView tv_Status = ViewHolderUtils.get(convertView, R.id.tv_Status);
        TextView tv_PandianPihao = ViewHolderUtils.get(convertView, R.id.tv_PandianPihao);
        TextView tv_Fanwei = ViewHolderUtils.get(convertView, R.id.tv_Fanwei);
        TextView tv_Type = ViewHolderUtils.get(convertView, R.id.tv_Type);
        TextView tv_Cangku = ViewHolderUtils.get(convertView, R.id.tv_Cangku);
        TextView tv_Beizhu = ViewHolderUtils.get(convertView, R.id.tv_Beizhu);


        tv_Status.setText("已审核");
        tv_PandianPihao.setText(obj.getSheet_no());//盘点批号
        tv_Fanwei.setText(obj.getOper_range_name());//范围
        tv_Type.setText(obj.getCheck_cls());//类别
        tv_Cangku.setText("");//仓库
        tv_Beizhu.setText(obj.getMemo());//备注

        if (itemClickPosition == position) {
            convertView.setBackgroundResource(R.color.list_background);
        } else {
            convertView.setBackgroundResource(R.color.transparent1);
        }
        return convertView;
    }

    public List<PandianPihaoHuoquBeanResult> getListInfo() {
        return listInfo;
    }

    public void setListInfo(List<PandianPihaoHuoquBeanResult> listInfo) {
        this.listInfo = listInfo;
    }

    public int getItemClickPosition() {
        return itemClickPosition;
    }

    public void setItemClickPosition(int itemClickPosition) {
        this.itemClickPosition = itemClickPosition;
    }
}
