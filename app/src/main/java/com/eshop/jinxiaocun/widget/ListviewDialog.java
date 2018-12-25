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
import com.eshop.jinxiaocun.lingshou.view.SelectListAdapter;
import com.eshop.jinxiaocun.lingshou.view.SelectPayListAdapter;
import com.eshop.jinxiaocun.utils.Config;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListviewDialog extends Activity implements INetWorResult {

    @BindView(R.id.lv_select_list)
    ListView mListView;

    List<GetPayModeResult> mGetPayModeResult;
    SelectListAdapter mSelectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_listview);
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
                GetPayModeResult mGetPayModeResultBean = new GetPayModeResult();
                mGetPayModeResultBean.setPay_way("VIP");
                mGetPayModeResultBean.setPay_name("会员卡");
                mGetPayModeResult.add(mGetPayModeResultBean);
                mSelectListAdapter = new SelectListAdapter(mGetPayModeResult);
                mListView.setAdapter(mSelectListAdapter);
                mSelectListAdapter.notifyDataSetChanged();
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
