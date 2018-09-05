package com.eshop.jinxiaocun.lingshou.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.bean.QryClassResult;
import com.eshop.jinxiaocun.base.view.BaseListFragment;
import com.eshop.jinxiaocun.lingshou.presenter.ISelectGoods;
import com.eshop.jinxiaocun.lingshou.presenter.SelectGoodsImp;
import com.eshop.jinxiaocun.pifaxiaoshou.bean.DanJuMainBeanResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.TwoListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SelectGoodsFragment extends BaseListFragment implements INetWorResult {
    private List<DanJuMainBeanResult> mListData;
    private List<QryClassResult> mQryClassResult;
    private List<GetClassPluResult> mGetClassPluResult;
    private List<GetClassPluResult> selectList;
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
        selectList = new ArrayList<GetClassPluResult>();
        mISelectGoods = new SelectGoodsImp(this);
        View v = inflater.inflate(R.layout.selectgoods_fragment, null);
        mTwoListView = v.findViewById(R.id.twlist);

        loadData();
        return v;
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_QRYCLASSINFO:
                mQryClassResult = (List<QryClassResult>) o;
                mTwoListView.setMainListBean(mQryClassResult,new MainListListener());
                mISelectGoods.getClassPluInfo(mQryClassResult.get(0).getType_no(),1);
                break;
            case Config.MESSAGE_GETCLASSPLUINFO:
                Message ms = new Message();
                ms.obj = o;
                mHandler.sendMessage(ms);
                break;
        }

    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mGetClassPluResult = (List<GetClassPluResult>) msg.obj;
            mTwoListView.setDetailListBean(mGetClassPluResult,new DetailListListener());
        }
    };

    public class MainListListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mISelectGoods.getClassPluInfo(mQryClassResult.get(i).getType_no(),1);
        }
    }

    public class DetailListListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            try {
                selectList.add(mGetClassPluResult.get(i-1));
                Intent mIntent = new Intent();
                mIntent.putExtra("SelectList", (Serializable) selectList);
                getActivity().setResult(Config.RESULT_SELECT_GOODS,mIntent);
                getActivity().finish();
            }catch (Exception e){
                Log.e("--",""+e.getMessage());
            }

        }
    }

    @Override
    protected void reflashList() {
        mTwoListView.onRefreshComplete();

    }
}
