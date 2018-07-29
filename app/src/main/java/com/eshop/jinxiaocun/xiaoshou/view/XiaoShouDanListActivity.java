package com.eshop.jinxiaocun.xiaoshou.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.BaseListActivity;
import com.eshop.jinxiaocun.thread.AsyncTaskThreadImp;
import com.eshop.jinxiaocun.thread.ThreadManagerInterface;
import com.eshop.jinxiaocun.xiaoshou.bean.TWmSheetMasterBean;
import com.eshop.jinxiaocun.xiaoshou.dao.TWmSheetMasterDao;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class XiaoShouDanListActivity extends BaseListActivity {

    private ListView lvXiaoshoudan;
    private XiaoshouDanAdapter mXiaoshouDanAdapter;
    private List<TWmSheetMasterBean> listInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        mLinearLayout.addView(getView(R.layout.activity_xiaoshou_dan),-1,params);
        lvXiaoshoudan = findViewById(R.id.lv_xiaoshoudan);

        ThreadManagerInterface mThreadManagerInterface = new AsyncTaskThreadImp();
        mThreadManagerInterface.executeRunnable(this);

    }

    private void loadData(){
        TWmSheetMasterDao mTWmSheetMasterDao = new TWmSheetMasterDao();
        try {
            listInfo = mTWmSheetMasterDao.getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object doInBackground(Object[] objects) {
        loadData();
        return null;
    }

    @Override
    public void onPostExecute(Object o) {
        mXiaoshouDanAdapter = new XiaoshouDanAdapter(listInfo);
        lvXiaoshoudan.setAdapter(mXiaoshouDanAdapter);
        mXiaoshouDanAdapter.notifyDataSetChanged();
    }
}
