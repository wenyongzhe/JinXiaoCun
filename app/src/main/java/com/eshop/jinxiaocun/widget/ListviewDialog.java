package com.eshop.jinxiaocun.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.lingshou.bean.GetPayModeResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.lingshou.view.SelectListAdapter;
import com.eshop.jinxiaocun.lingshou.view.SelectPayListAdapter;
import com.eshop.jinxiaocun.utils.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListviewDialog extends Activity{

    ListView mListView;

    List<String> mString;
    SelectListAdapter mSelectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_listview);
        mListView = (ListView) findViewById(R.id.lv_select_list);
        mString = getIntent().getStringArrayListExtra("String");


        initView();
    }


    private void initView() {
        if(mString==null){
            return;
        }
        mSelectListAdapter = new SelectListAdapter(mString);
        mListView.setAdapter(mSelectListAdapter);
        mSelectListAdapter.notifyDataSetChanged();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent();
                mIntent.putExtra("String",mString.get(i));
                setResult(Config.MESSAGE_SELECT_RETURN,mIntent);
                finish();
            }
        });
    }

}
