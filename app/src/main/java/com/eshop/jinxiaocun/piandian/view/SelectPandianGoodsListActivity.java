package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.INetWorResult;
import com.eshop.jinxiaocun.base.bean.GetClassPluResult;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.othermodel.adapter.PiciInfoListAdapter;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBean;
import com.eshop.jinxiaocun.othermodel.bean.GoodsPiciInfoBeanResult;
import com.eshop.jinxiaocun.othermodel.presenter.IOtherModel;
import com.eshop.jinxiaocun.othermodel.presenter.OtherModelImp;
import com.eshop.jinxiaocun.piandian.adapter.SelectGoodsListAdapter;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimeListener;
import com.eshop.jinxiaocun.slidedatetimepicker.SlideDateTimePicker;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.widget.AlertUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author Lu An
 * 创建时间  2018/9/20
 * 描述
 */
public class SelectPandianGoodsListActivity extends CommonBaseActivity{


    @BindView(R.id.listview)
    ListView mListView;

    private SelectGoodsListAdapter mAdapter;
    private List<GetClassPluResult> mListData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_pandian_goods_info;
    }

    @Override
    protected void initView() {
        super.initView();

        setTopToolBar("选择批次信息",R.mipmap.ic_left_light,"",0,"");
        mListData =  (List<GetClassPluResult>) getIntent().getSerializableExtra("GoodsInfoList");
        mAdapter = new SelectGoodsListAdapter(mListData);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("GoodsInfoEntity",mListData.get(position));
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }


    @Override
    protected boolean onTopBarLeftClick() {
        showHint();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            showHint();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showHint(){
        AlertUtil.showAlert(this, R.string.dialog_title, "没有选择商品，您确定要退出吗？", R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
                AlertUtil.dismissDialog();
            }
        }, R.string.cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertUtil.dismissDialog();
            }
        });
    }



}
