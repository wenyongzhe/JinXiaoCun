package com.eshop.jinxiaocun.piandian.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.piandian.adapter.SelectPandianTypeAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBean;
import com.eshop.jinxiaocun.piandian.bean.PandianLeibieBeanResultItem;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.utils.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectPandianTypeDialog extends Activity implements INetWorResult {

    TextView tv_0;
    TextView tv_1;

    SelectPandianTypeAdapter adapterData;
    private IPandian mServerApi;

    ListView mListView;
    List<PandianLeibieBeanResultItem> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_pandian_type);

        ButterKnife.bind(this);

        tv_0=findViewById(R.id.tv_0);
        tv_1=findViewById(R.id.tv_1);
        mListView=findViewById(R.id.listview_data);
        tv_0.setWidth(CommonUtility.dip2px(this,80));
        tv_1.setWidth(CommonUtility.dip2px(this,140));
        tv_0.setText("编码");
        tv_1.setText("名称");


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lv = (ListView) parent;
                PandianLeibieBeanResultItem obj = (PandianLeibieBeanResultItem) lv.getItemAtPosition(position);
                adapterData.setIsSelected(position);
                adapterData.notifyDataSetInvalidated();
            }
        });

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int width =wm.getDefaultDisplay().getWidth();
        int screen_height = wm.getDefaultDisplay().getHeight();
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.copyFrom(getWindow().getAttributes());
        if(screen_height>1080)
            localLayoutParams.width = width-160;
        else
            localLayoutParams.width = width-100;
        localLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(localLayoutParams);

        mServerApi = new PandianImp(this);
        getPandianLeibieData();
    }

    //取盘点类别数据
    private void getPandianLeibieData(){
        PandianLeibieBean bean = new PandianLeibieBean();
        bean.JsonData.as_branchNo="";//门店号
        bean.JsonData.as_posId="";//pos id
        bean.JsonData.as_type="1";//'1'类别 '0' 品牌
        bean.JsonData.as_clsorbrno="";//指定的类型或者品牌
        mServerApi.getPandianTypeData(bean);
    }



    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //取盘点类别
            case Config.MESSAGE_PANDIANLEIBIE_OK:

                break;
            case Config.MESSAGE_PANDIANLEIBIE_ERROR:
                listData = (List<PandianLeibieBeanResultItem>) o;
                adapterData = new SelectPandianTypeAdapter(this, listData);
                mListView.setAdapter(adapterData);
                break;
        }
    }
}
