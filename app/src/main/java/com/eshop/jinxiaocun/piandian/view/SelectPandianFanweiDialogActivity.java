package com.eshop.jinxiaocun.piandian.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.piandian.adapter.SelectPandianFanweiAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianFanweiBean;
import com.eshop.jinxiaocun.piandian.bean.PandianFanweiBeanResult;
import com.eshop.jinxiaocun.piandian.presenter.IPandian;
import com.eshop.jinxiaocun.piandian.presenter.PandianImp;
import com.eshop.jinxiaocun.utils.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class SelectPandianFanweiDialogActivity extends Activity implements INetWorResult {

    private TextView title;
    private TextView tv_0;
    private TextView tv_1;

    private SelectPandianFanweiAdapter mAdapter;
    private IPandian mServerApi;

    private ListView mListView;
    private List<PandianFanweiBeanResult> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_pandian_type);

        ButterKnife.bind(this);

        title=findViewById(R.id.title);
        tv_0=findViewById(R.id.tv_0);
        tv_1=findViewById(R.id.tv_1);
        mListView=findViewById(R.id.listview_data);
        title.setText("请选择盘点范围");
        tv_0.setText("编号");
        tv_1.setText("名称");

        mAdapter = new SelectPandianFanweiAdapter(this, listData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lv = (ListView) parent;
                PandianFanweiBeanResult obj = (PandianFanweiBeanResult) lv.getItemAtPosition(position);
                mAdapter.setIsSelected(position);
                mAdapter.notifyDataSetInvalidated();
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
        mServerApi.getPandianFanweiData(new PandianFanweiBean());
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            //取盘点类别
            case Config.MESSAGE_OK:
                listData = (List<PandianFanweiBeanResult>) o;
                mAdapter.setData(listData);
                break;
            case Config.MESSAGE_ERROR:

                break;
        }
    }
}
