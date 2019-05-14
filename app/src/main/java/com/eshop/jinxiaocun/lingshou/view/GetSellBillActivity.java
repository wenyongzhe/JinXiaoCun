package com.eshop.jinxiaocun.lingshou.view;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.BaseActivity;
import com.eshop.jinxiaocun.base.view.BaseScanActivity;
import com.eshop.jinxiaocun.db.BusinessBLL;
import com.eshop.jinxiaocun.lingshou.bean.GetBillMain;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class GetSellBillActivity extends BaseScanActivity implements View.OnClickListener {

    private GridView mGridView;
    private List<GetBillMain> mListMainData = new ArrayList<>();
    private List<GetClassPluResult> mListDetalData = new ArrayList<>();
    private LingShouGetBillMainAdapter mGetBIllMainAdapter;
    private LingShouScanAdapter mGetBIllDetailAdapter;
    private Button mButton;
    private Button mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void addViewConten() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.activity_get_sell_bill, null);
        mLinearLayout.addView(bottomView,-1,params);
        mListview = bottomView.findViewById(R.id.listview_data);
        mGridView = bottomView.findViewById(R.id.gv_bill_main);
        mButton = bottomView.findViewById(R.id.btn_get_bill);
        mDelete = bottomView.findViewById(R.id.btn_delete);
        mMyActionBar.setData("零售取单",R.mipmap.ic_left_light,"",0,"",this);
        loadData();
    }

    @Override
    protected void loadData() {
        mListMainData =  BusinessBLL.getInstance().getGuaDanMain();
        mGetBIllMainAdapter = new LingShouGetBillMainAdapter(mListMainData);
        mGetBIllDetailAdapter = new LingShouScanAdapter(mListDetalData);
        mGridView.setAdapter(mGetBIllMainAdapter);
        mListview.setAdapter(mGetBIllDetailAdapter);
        mListview.setOnItemClickListener(null);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mGetBIllMainAdapter.setItemClickPosition(i);
                mGetBIllMainAdapter.notifyDataSetChanged();
                mListDetalData = BusinessBLL.getInstance().getGuaDanDetail(mListMainData.get(i).getTimeNo());
                mGetBIllDetailAdapter.setListInfo(mListDetalData);
                mGetBIllDetailAdapter.notifyDataSetChanged();
            }
        });
        mButton.setOnClickListener(this);
        mDelete.setOnClickListener(this);
    }

    private void reflash(){
        mListMainData =  BusinessBLL.getInstance().getGuaDanMain();
        mGetBIllMainAdapter.setListInfo(mListMainData);
        mGetBIllMainAdapter.setItemClickPosition(-1);
        mGetBIllMainAdapter.notifyDataSetChanged();
        mListDetalData.clear();
        mGetBIllDetailAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void scanData(String barcode) {

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
                finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_get_bill){
            if(mGetBIllMainAdapter.getItemClickPosition()==-1){
                AlertUtil.showToast("请选择单据");
                return;
            }
            Intent mIntent = new Intent(this,LingShouCreatAtivity.class);
            mIntent.putExtra("mListData", (Serializable) mListDetalData);
            startActivity(mIntent);
            BusinessBLL.getInstance().deleGuaDanMainDetail(mListMainData.get(mGetBIllMainAdapter.getItemClickPosition()).getTimeNo());
            reflash();
        }
        if(view.getId()==R.id.btn_delete){
            if(mGetBIllMainAdapter.getItemClickPosition()!=-1){
                BusinessBLL.getInstance().deleGuaDanMainDetail(mListMainData.get(mGetBIllMainAdapter.getItemClickPosition()).getTimeNo());
                reflash();
            }
        }
    }
}
