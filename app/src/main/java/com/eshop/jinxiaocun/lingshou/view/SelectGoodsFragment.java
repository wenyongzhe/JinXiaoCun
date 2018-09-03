package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.QryClassResult;
import com.eshop.jinxiaocun.base.view.BaseListFragment;
import com.eshop.jinxiaocun.lingshou.presenter.ISelectGoods;
import com.eshop.jinxiaocun.lingshou.presenter.SelectGoodsImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBean;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResultItem;
import com.eshop.jinxiaocun.pifaxiaoshou.presenter.DanJuListImp;
import com.eshop.jinxiaocun.pifaxiaoshou.view.FinishListAdapter;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.RefreshListView;
import com.eshop.jinxiaocun.widget.TwoListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@SuppressLint("ValidFragment")
public class SelectGoodsFragment extends BaseListFragment implements INetWorResult {
    private List<DanJuMainBeanResult> mListData;
    private List<QryClassResult> mQryClassResult;
    private List<GetClassPluResult> mGetClassPluResult;
    private TwoListView mTwoListView;
    ISelectGoods mISelectGoods;
    public static SelectGoodsFragment getInstance() {
        SelectGoodsFragment sf = new SelectGoodsFragment();
        return sf;
    }

    @Override
    protected void loadData() {
        mISelectGoods.qryClassInfo();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mQryClassResult = new ArrayList<QryClassResult>();
        mGetClassPluResult = new ArrayList<GetClassPluResult>();
        mISelectGoods = new SelectGoodsImp(this);
        View v = inflater.inflate(R.layout.selectgoods_fragment, null);
        mTwoListView = v.findViewById(R.id.twlist);
        mTwoListView.setMainListBean(mQryClassResult);
        mTwoListView.setDetailListBean(mGetClassPluResult);

        loadData();
        return v;
    }

    @Override
    public void handleResule(int flag, Object o) {
        try{
            List<DanJuMainBeanResultItem> list = new ArrayList();
            DanJuMainBeanResultItem test = new DanJuMainBeanResultItem();
            test.Sheet_No = "PI1234567";
            test.Oper_Date = "2017-3-4";
            test.Ord_Amt = "$200";
            test.Oper_Name = "张三";
            test.Branch_No = "三楼仓库";
            list.add(test);
            mDanJuAdapter = new FinishListAdapter(list);
            mHandle.sendEmptyMessage(Config.MESSAGE_REFLASH);
        }catch (Exception e){
            Log.e("Exception",e.getMessage());
        }


//        DanJuMainBeanResult mDanJuMainBeanResult = (DanJuMainBeanResult) o;
//        mXiaoshouDanAdapter = new FinishListAdapter(mDanJuMainBeanResult.JsonData);
//        mListView.setAdapter(mXiaoshouDanAdapter);
//        mXiaoshouDanAdapter.notifyDataSetChanged();
    }

    @Override
    protected void reflashList() {
        mListView.onRefreshComplete();

    }
}
