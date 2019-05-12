package com.eshop.jinxiaocun.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.lingshou.view.PiChiListAdapter;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.DensityUtil;
import com.eshop.jinxiaocun.utils.MyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PiChiDialog extends Activity {

    @BindView(R.id.lv_pichi)
    ListView lv_pichi;
    List<GoodsPiciInfoBeanResult> mGoodsPiciInfoBeanResultList;
    private PiChiListAdapter mPiChiListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pichi_dialog);
        ButterKnife.bind(this);
        mGoodsPiciInfoBeanResultList = (List<GoodsPiciInfoBeanResult>) getIntent().getSerializableExtra("mGoodsPiciInfoBeanResult");
        mPiChiListAdapter = new PiChiListAdapter(mGoodsPiciInfoBeanResultList);
        lv_pichi.setAdapter(mPiChiListAdapter);
        mPiChiListAdapter.notifyDataSetChanged();
        lv_pichi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsPiciInfoBeanResult mGoodsPiciInfoBeanResult = mGoodsPiciInfoBeanResultList.get(i);
                Intent mIntent = new Intent();
                mIntent.putExtra("pichi", mGoodsPiciInfoBeanResult);
                setResult(Config.PICHI_SELECT_DIALOG,mIntent);
                finish();
            }
        });
        int screen_width = DensityUtil.getInstance().getScreenWidth(this);
        int screen_height = DensityUtil.getInstance().getScreenHeight(this);

        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.copyFrom(getWindow().getAttributes());
        if(screen_height>1080)
            localLayoutParams.width = screen_width-160;
        else
            localLayoutParams.width = screen_width-100;
        localLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(localLayoutParams);
    }

    @OnClick(R.id.btn_ok)
    void OnOk()
    {
    }

    public void showSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.btn_cancel)
    void OnCancel()
    {
        finish();
    }
}
