package com.eshop.jinxiaocun.widget;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.lingshou.bean.GetPayModeResult;
import com.eshop.jinxiaocun.lingshou.presenter.ILingshouScan;
import com.eshop.jinxiaocun.lingshou.presenter.LingShouScanImp;
import com.eshop.jinxiaocun.lingshou.view.SelectPayListAdapter;
import com.eshop.jinxiaocun.utils.Config;

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
                mSelectPayListAdapter = new SelectPayListAdapter(mGetPayModeResult);
                mListView.setAdapter(mSelectPayListAdapter);
                break;
        }
    }
}
