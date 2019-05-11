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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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

    private Button bt_ok;
    private LinearLayout lyButton;
    private List<DanJuMainBeanResult> mListData;
    private List<QryClassResult> mQryClassResult;
    private List<QryClassResult> mMainlistTemp = new ArrayList<>();
    private List<GetClassPluResult> mGetClassPluResult;
    private List<GetClassPluResult> mDetaillistTemp = new ArrayList<>();
    private List<GetClassPluResult> selectList;
    private TwoListView mTwoListView;
    ISelectGoods mISelectGoods;
    private String type_no = "";

    public static SelectGoodsFragment getInstance(String type_no) {
        SelectGoodsFragment sf = new SelectGoodsFragment(type_no);
        return sf;
    }

    public SelectGoodsFragment(String type_no) {
        this.type_no = type_no;
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
        selectList = (List<GetClassPluResult>) getActivity().getIntent().getSerializableExtra("selectList");
        if(selectList == null){
            selectList = new ArrayList<GetClassPluResult>();
        }
        mISelectGoods = new SelectGoodsImp(this);
        View v = inflater.inflate(R.layout.selectgoods_fragment, null);
        mTwoListView = v.findViewById(R.id.twlist);
        lyButton = v.findViewById(R.id.ly_buttonContain);
        bt_ok = v.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //selectList.addAll(mTwoListView.getSelectList());
                    Intent mIntent = new Intent();
                    mIntent.putExtra("SelectList", (Serializable) selectList);
                    getActivity().setResult(Config.RESULT_SELECT_GOODS,mIntent);
                    getActivity().finish();
                }catch (Exception e){
                    Log.e("--",""+e.getMessage());
                }
            }
        });
        loadData();
        return v;
    }

    @Override
    public void handleResule(int flag, Object o) {
        Message ms = new Message();
        switch (flag){
            case Config.MESSAGE_QRYCLASSINFO:
                mQryClassResult = (List<QryClassResult>) o;
                if(type_no!=null && !type_no.equals("")){
                    for(int i=0; i<mQryClassResult.size(); i++){
                        if(mQryClassResult.get(i).getType_no().equals(type_no)){
                            QryClassResult qryClassResult = new QryClassResult();
                            qryClassResult = mQryClassResult.get(i);
                            mQryClassResult.clear();
                            mQryClassResult.add(qryClassResult);
                            break;
                        }
                    }
                }
                ms.what = 1;
                mHandler.sendMessage(ms);
                mISelectGoods.getClassPluInfo(mQryClassResult.get(0).getType_no(),1);
                break;
            case Config.MESSAGE_GETCLASSPLUINFO:
                ms.obj = o;
                ms.what = 2;
                mHandler.sendMessage(ms);
                break;
        }

    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    for(int i=0; i<mQryClassResult.size(); i++){
                        if(mQryClassResult.get(i).getType_no().length()==2){
                            mMainlistTemp.add(mQryClassResult.get(i));
                        }
                    }
                    mTwoListView.setMainListBean(mMainlistTemp,new MainListListener());
                    break;
                case 2:
                    mGetClassPluResult = (List<GetClassPluResult>) msg.obj;
                    mDetaillistTemp.clear();
                    if(!currentType_no.equals("")){
                        for(int i=0; i<mGetClassPluResult.size(); i++){
                            if(mGetClassPluResult.get(i).getItem_clsno().equals(currentType_no)){
                                mDetaillistTemp.add(mGetClassPluResult.get(i));
                            }
                        }
                    }else{
                        mDetaillistTemp.addAll(mGetClassPluResult);
                    }
                    mTwoListView.setDetailListBean(mDetaillistTemp,selectList,new DetailListListener());
                    break;

            }

        }
    };

    public class MainListListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mISelectGoods.getClassPluInfo(mMainlistTemp.get(i).getType_no(),1);
            lyButton.removeAllViews();
            currentType_no = "";
            for(int j=0; j<mQryClassResult.size(); j++){
                String type1 = mQryClassResult.get(j).getType_no();
                String type2 = mMainlistTemp.get(i).getType_no();
                if(type1.substring(0,2).equals(type2) && type1.length()>2){
                    addButton(mQryClassResult.get(j));
                }
            }
        }
    }

    String currentType_no = "";
    private void addButton(QryClassResult mQryClassResult) {
        final Button btn1 = new Button(getActivity());
        btn1.setTextSize(12);
        btn1.setHeight(8);
        btn1.setTag(mQryClassResult);
        btn1.setText(mQryClassResult.getType_name());
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QryClassResult mQryClassResult = (QryClassResult) view.getTag();
                currentType_no = mQryClassResult.getType_no();
                mISelectGoods.getClassPluInfo(mQryClassResult.getType_no(),1);
            }
        });
        lyButton.addView(btn1);
    }

    public class DetailListListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            try {
//                selectList.add(mDetaillistTemp.get(i-1));
//                Intent mIntent = new Intent();
//                mIntent.putExtra("SelectList", (Serializable) selectList);
//                getActivity().setResult(Config.RESULT_SELECT_GOODS,mIntent);
//                getActivity().finish();
//            }catch (Exception e){
//                Log.e("--",""+e.getMessage());
//            }

        }
    }

    @Override
    protected void reflashList() {
        mTwoListView.onRefreshComplete();

    }
}
