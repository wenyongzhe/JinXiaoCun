package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.QryClassResult;
import com.eshop.jinxiaocun.base.view.BaseListFragment;
import com.eshop.jinxiaocun.lingshou.presenter.IQueryGoods;
import com.eshop.jinxiaocun.lingshou.presenter.QueryGoodsImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.view.FinishListAdapter;
import com.eshop.jinxiaocun.pifaxiaoshou.view.XiaoshouDanListAdapter;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.RefreshListView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class QueryFragment extends BaseListFragment implements INetWorResult {
    private List<GetClassPluResult> mListData = new ArrayList<>();
    private EditText et_query;
    private IQueryGoods mQueryGoods;

    public static QueryFragment getInstance() {
        QueryFragment sf = new QueryFragment();
        return sf;
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mQueryGoods = new QueryGoodsImp(this);
        mDanJuList = new DanJuListImp(this);
        mDanJuAdapter = new QueryGoodsListAdapter(mListData);
        View v = inflater.inflate(R.layout.query_fragment, null);
        mListView = (RefreshListView) v.findViewById(R.id.list_view);
        mListView.setAdapter(mDanJuAdapter);

        et_query = (EditText) v.findViewById(R.id.et_query);
        et_query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == 0) {
                    mQueryGoods.getPLULikeInfo(v.getText().toString().trim(),0);
                }
                return false;
            }
        });
        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                page ++;
                loadData();
            }
        });
        loadData();
        return v;
    }

    @Override
    public void handleResule(int flag, Object o) {
        Message ms = new Message();
        switch (flag){
            case Config.MESSAGE_GETCLASSPLUINFO:
                mListData.clear();
                mListData = (List<GetClassPluResult>) o;
                ((QueryGoodsListAdapter)mDanJuAdapter).setListInfo(mListData);
                mHandle.sendEmptyMessage(Config.MESSAGE_REFLASH);
                break;
        }


    }

    @Override
    protected void reflashList() {
        mListView.onRefreshComplete();

    }
}
