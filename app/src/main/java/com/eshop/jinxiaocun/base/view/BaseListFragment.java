package com.eshop.jinxiaocun.base.view;

import android.os.Handler;
import android.os.Message;
import android.widget.BaseAdapter;

import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.RefreshListView;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.IDanJuList;

public abstract class BaseListFragment extends BaseFragment{

    protected BaseAdapter mDanJuAdapter;
    protected RefreshListView mListView;
    /** 页数 */
    protected int page = 1;
    /** 每次请求的条数 */
    protected int limit = 10;

    public IDanJuList mDanJuList;

    protected Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Config.MESSAGE_REFLASH:
                    if(mDanJuAdapter==null){
                        return;
                    }
                    mListView.setAdapter(mDanJuAdapter);
                    mDanJuAdapter.notifyDataSetChanged();
                    reflashList();
                    break;
            }
        }
    };

    protected abstract void reflashList();
}
