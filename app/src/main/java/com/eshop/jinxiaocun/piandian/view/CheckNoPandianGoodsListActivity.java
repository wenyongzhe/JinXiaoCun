package com.eshop.jinxiaocun.piandian.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.view.CommonBaseActivity;
import com.eshop.jinxiaocun.db.BusinessBLL;
import com.eshop.jinxiaocun.piandian.adapter.CheckNoPandianGoodsListAdapter;
import com.eshop.jinxiaocun.piandian.bean.PandianDetailBeanResult;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.widget.AlertUtil;
import com.eshop.jinxiaocun.widget.ModifyCountDialog;
import com.eshop.jinxiaocun.widget.RefreshListView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
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
    private List<PandianDetailBeanResult> mListData = new ArrayList<>();
    private CheckNoPandianGoodsListAdapter mAdapter;
    private String mSheetNo;
    private GetDBDatas mGetDBDatas;
    private PandianDetailBeanResult mSelectItem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nopandian_goods;
    }

    @Override
    protected void initView() {
        super.initView();
        try {
            mSheetNo = getIntent().getStringExtra("SheetNo");
            setTopToolBar("未盘点商品", R.mipmap.ic_left_light,"",0,"");

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

                    //点击盘点 先修改盘点数量
                    mSelectItem = mListData.get(position);
                    Intent intent = new Intent(CheckNoPandianGoodsListActivity.this, ModifyCountDialog.class);
                    intent.putExtra("countN",mSelectItem.getCheck_qty()+"");
                    startActivityForResult(intent,1);

                }
            });

            if(BusinessBLL.getInstance().isHavePandianGoodsEntity("sheet_no='"+mSheetNo+"'")){
                AlertUtil.showNoButtonProgressDialog(this,"正在加载数据");
                mGetDBDatas= new GetDBDatas(this);
                mGetDBDatas.execute();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //点击盘点 先修改盘点数量
            if(requestCode==1 && resultCode==RESULT_OK){
                int checkQty = MyUtils.convertToInt(data.getStringExtra("countN"),1);
                mSelectItem.setCheck_qty(checkQty);//重新修改盘点数量
                EventBus.getDefault().post(mSelectItem);
                Iterator<PandianDetailBeanResult> iter = mListData.iterator();
                while (iter.hasNext()) {
                    PandianDetailBeanResult item = iter.next();
                    if (item.getItem_no().equals(mSelectItem.getItem_no())) {
                        iter.remove();
                        break;
                    }
                }
                setTopToolBar("未盘点商品"+mListData.size()+"种", R.mipmap.ic_left_light,"",0,"");
                mAdapter.setListInfo(mListData);
                mAdapter.notifyDataSetInvalidated();
            }
        }catch (Exception e) {
            e.printStackTrace();
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
                StringBuffer where = new StringBuffer();
                where.append("sheet_no='");
                where.append(mSheetNo);
                where.append("' and has_stocktake=0 and status=0");//has_stocktake 0未盘点 1已盘点
                BusinessBLL.getInstance().getDBStocktakeGoodsDatas(where.toString(), new BusinessBLL.DbCallBack<PandianDetailBeanResult>() {
                    @Override
                    public void progressUpdate(int progress, int maxProgress,PandianDetailBeanResult module) {
                        publishProgress(progress+"/"+maxProgress);
                        mListData.add(module);
                        mAdapter.notifyDataSetChanged();

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
            try {
                CheckNoPandianGoodsListActivity activity = weakActivity.get();
                if (activity == null
                        || activity.isFinishing()
                        || activity.isDestroyed()) {
                    // activity没了,就结束可以了
                    return;
                }
            }catch (Exception e){

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
                setTopToolBar("未盘点商品"+mListData.size()+"种", R.mipmap.ic_left_light,"",0,"");
                mAdapter.setListInfo(mListData);
            }else{
                AlertUtil.showToast("获取本地数据异常："+s);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(mListData!=null){
                mListData.clear();
                mListData=null;
            }
            if(mGetDBDatas !=null){
                mGetDBDatas.cancel(true);
            }
        }catch (Exception e){

        }

    }
}
