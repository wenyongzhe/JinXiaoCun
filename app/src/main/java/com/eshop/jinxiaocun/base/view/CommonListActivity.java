package com.eshop.jinxiaocun.base.view;

import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.CommonUtility;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @Author Lu An
 * 创建时间  2019/7/1
 * 描述
 */

public abstract class CommonListActivity extends CommonBaseActivity implements AdapterView.OnItemClickListener {


    @BindView(R.id.rfListview)
    protected RefreshListView mListView;
    @BindView(R.id.ll_bottom_txt)
    protected LinearLayout mLayoutBottomTxt;
    @BindView(R.id.tv_CurrentPosition)
    protected TextView mTvCurrentPosition;//当前选择第几条
    @BindView(R.id.tv_AllCount)
    protected TextView mTvAllCount;//总条数
    @BindView(R.id.ll_bottom)
    protected LinearLayout mLayoutBottom;
    @BindView(R.id.bottom_btn_create)
    protected Button mBtnCreate;
    @BindView(R.id.bottom_btn_modify)
    protected Button mBtnModify;
    @BindView(R.id.bottom_btn_delete_order)
    protected Button mBtnDeleteOrder;
    @BindView(R.id.bottom_btn_check)
    protected Button mBtnCheck;

    //子类去实现
    protected abstract @LayoutRes int getLayoutContentId();
    protected abstract boolean createOrderBefore();//开单前
    protected abstract void createOrderAfter();
    protected abstract boolean deleteOrderBefore();//删单前
    protected abstract void deleteOrderAfter();
    protected abstract boolean modifyBefore();//修改前
    protected abstract void modifyAfter();
    protected abstract boolean checkBefore();//审核前
    protected abstract void checkAfter();

    @Override
    protected int getLayoutId() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.common_list, null);
        mLinearLayout.addView(bottomView,-1,params);
        return getLayoutContentId();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @OnClick(R.id.bottom_btn_create)
    public void onClickCreate(){
        if(!createOrderBefore()){
            return;
        }
        createOrderAfter();
    }

    @OnClick(R.id.bottom_btn_modify)
    public void onClickModify(){
        if(!modifyBefore())return;
        modifyAfter();
    }

    @OnClick(R.id.bottom_btn_delete_order)
    public void onClickDelete(){
        if(!deleteOrderBefore())return;
        AlertUtil.showAlert(this, R.string.dialog_title,
                "确定删除所选的单据？", R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                        deleteOrderAfter();
                    }
                }, R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                    }
                });

    }

    @OnClick(R.id.bottom_btn_check)
    public void onClickUpload(){
        if(!checkBefore())return;
        AlertUtil.showAlert(this, R.string.dialog_title,
                "确定审核所选的单据？", R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                        checkAfter();
                    }
                }, R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertUtil.dismissDialog();
                    }
                });

    }

}
