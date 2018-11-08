package com.eshop.jinxiaocun.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.lingshou.bean.GetPayModeResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.lingshou.view.SelectPayListAdapter;
import com.eshop.jinxiaocun.utils.Config;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectPayDialog extends Activity implements INetWorResult {

    @BindView(R.id.lv_select_pay)
    ListView mListView;

    private ILingshouScan mLingShouScanImp;
    List<GetPayModeResult> mGetPayModeResult;
    SelectPayListAdapter mSelectPayListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay);
        mLingShouScanImp = new LingShouScanImp(this);
        mLingShouScanImp.getPayMode();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }

    @Override
    public void handleResule(int flag, Object o) {
        switch (flag){
            case Config.MESSAGE_GET_PAY_MODE:
                mGetPayModeResult = ( List<GetPayModeResult>)o;
                Iterator<GetPayModeResult> iterator = mGetPayModeResult.iterator();
                 while(iterator.hasNext()){
                     GetPayModeResult next = iterator.next();
                     if(!(next.getPay_way().equals("RMB")||next.getPay_way().equals("ZFB")||next.getPay_way().equals("WXZ"))){
                         iterator.remove();
                     }
                 }
                mSelectPayListAdapter = new SelectPayListAdapter(mGetPayModeResult);
                mListView.setAdapter(mSelectPayListAdapter);
                mSelectPayListAdapter.notifyDataSetChanged();
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent mIntent = new Intent();
                        mIntent.putExtra("Pay_way",mGetPayModeResult.get(i).getPay_way());
                        setResult(Config.MESSAGE_SELECT_PAY_RETURN,mIntent);
                        finish();
                    }
                });
                break;
        }
    }
}
