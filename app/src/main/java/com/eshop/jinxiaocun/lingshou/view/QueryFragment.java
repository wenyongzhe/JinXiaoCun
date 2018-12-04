package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class QueryFragment extends BaseListFragment implements INetWorResult {
    private List<GetClassPluResult> mListData = new ArrayList<>();
    private List<GetClassPluResult> selectList = new ArrayList<>();
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
        mDanJuAdapter = new QueryGoodsListAdapter(getActivity(),mListData);
        View v = inflater.inflate(R.layout.query_fragment, null);
        mListView = (RefreshListView) v.findViewById(R.id.list_view);
        mListView.setAdapter(mDanJuAdapter);

        et_query = (EditText) v.findViewById(R.id.et_query);
        et_query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == 0
                        || actionId == EditorInfo.IME_ACTION_GO || actionId == 6) { /*判断是否是“GO”键*/
                    mQueryGoods.getPLULikeInfo(v.getText().toString().trim(),0);
                    /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(inputMethodManager.isActive()){
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    return true;
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
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            selectList.add(mListData.get(i-1));
                            Intent mIntent = new Intent();
                            mIntent.putExtra("SelectList", (Serializable) selectList);
                            getActivity().setResult(Config.RESULT_SELECT_GOODS,mIntent);
                            getActivity().finish();
                        }catch (Exception e){

                        }

                    }
                });
                break;
        }


    }

    @Override
    protected void reflashList() {
        mListView.onRefreshComplete();

    }
}
