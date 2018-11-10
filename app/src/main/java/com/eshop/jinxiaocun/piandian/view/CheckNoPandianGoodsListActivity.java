package com.eshop.jinxiaocun.piandian.view;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.db.BusinessBLL;
import com.eshop.jinxiaocun.piandian.adapter.CheckNoPandianGoodsListAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.utils.Config;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.RefreshListView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author: 安仔夏天勤奋
 * Date: 2018/11/1
 * Desc: 查看未盘点的商品  可以进行盘点
 */

public class CheckNoPandianGoodsListActivity extends CommonBaseActivity {


    @BindView(R.id.rfListview)
    protected RefreshListView mListView;


    private List<PandianDetailBeanResult> mAddPandianGoodsDetailData = new ArrayList<>();
    private List<PandianDetailBeanResult> mPandianDetailData = new ArrayList<>();
    private List<PandianDetailBeanResult> mListData = new ArrayList<>();
    private CheckNoPandianGoodsListAdapter mAdapter;
    private String mSheetNo;
    private GetDBDatas mGetDBDatas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nopandian_goods;
    }

    @Override
    protected void initView() {
        super.initView();

        mSheetNo = getIntent().getStringExtra("SheetNo");
        mAddPandianGoodsDetailData = (List<PandianDetailBeanResult>) getIntent().getSerializableExtra("AddDetailListData");
        setTopToolBar("未盘点商品", R.mipmap.ic_left_light,"",0,"");
        mListView.setonTopRefreshListener(new RefreshListView.OnTopRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.onRefreshComplete();
            }
        });

        mListView.setonBottomRefreshListener(new RefreshListView.OnBottomRefreshListener() {
            @Override
            public void onRefresh() {
                mListView.onRefreshComplete();
            }
        });

        mAdapter = new CheckNoPandianGoodsListAdapter(this,mListData);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mListView.setAdapter(mAdapter);
        mAdapter.setCallback(new CheckNoPandianGoodsListAdapter.CallbackInterface() {
            @Override
            public void onClickAddPandian(int position) {
                EventBus.getDefault().post(mListData.get(position));
                mListData.remove(position);
                mAdapter.setListInfo(mListData);
            }
        });

        AlertUtil.showNoButtonProgressDialog(this,"正在加载数据");
        if(BusinessBLL.getInstance().isHavePandianGoodsEntity("sheet_no='"+mSheetNo+"'")){
            mGetDBDatas= new GetDBDatas(this);
            mGetDBDatas.execute();
        }


    }


    private class GetDBDatas extends AsyncTask<String,String,String> {

        // 弱引用是允许被gc回收的;
        private final WeakReference<CheckNoPandianGoodsListActivity> weakActivity;
        GetDBDatas(CheckNoPandianGoodsListActivity activity) {
            this.weakActivity = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                long time = System.currentTimeMillis();
                BusinessBLL.getInstance().getDBPandianGoodsDatas("sheet_no='" + mSheetNo + "'", new BusinessBLL.DbCallBack() {
                    @Override
                    public void progressUpdate(int progress, int maxProgress,PandianDetailBeanResult module) {
                        publishProgress(progress+"/"+maxProgress);
                        boolean isSame = false;
                        for (PandianDetailBeanResult beanResult : mAddPandianGoodsDetailData) {
                            if(module.getItem_no().equals(beanResult.getItem_no())){
                                isSame = true;
                                break;
                            }
                        }
                        if(!isSame){
                            mPandianDetailData.add(module);
                        }

                    }
                });
                Log.e("lu","get db data time is "+(System.currentTimeMillis()-time)/1000);
                return "ok";
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            CheckNoPandianGoodsListActivity activity = weakActivity.get();
            if (activity == null
                    || activity.isFinishing()
                    || activity.isDestroyed()) {
                // activity没了,就结束可以了
                return;
            }

            AlertUtil.setNoButtonMessage("正在加载数据 "+values[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            CheckNoPandianGoodsListActivity activity = weakActivity.get();
            if (activity == null
                    || activity.isFinishing()
                    || activity.isDestroyed()) {
                // activity没了,就结束可以了
                return;
            }

            AlertUtil.dismissProgressDialog();
            if(s.equals("ok")){
                setTopToolBar("未盘点商品"+mPandianDetailData.size()+"种", R.mipmap.ic_left_light,"",0,"");
                mAdapter.setListInfo(mPandianDetailData);
            }else{
                AlertUtil.showToast("获取本地数据异常："+s);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPandianDetailData.clear();
        mAddPandianGoodsDetailData.clear();
        mListData.clear();
        if(mGetDBDatas !=null){
            mGetDBDatas.cancel(true);
        }
    }
}
