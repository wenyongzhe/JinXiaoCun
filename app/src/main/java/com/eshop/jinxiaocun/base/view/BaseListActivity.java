package com.eshop.jinxiaocun.base.view;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.utils.CommonUtility;

public class BaseListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    protected ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        View bottomView = this.getLayoutInflater().inflate(R.layout.common_list_bottom, null);
        mLinearLayout.addView(bottomView,-1,params);
    }

    @Override
    protected void loadData() {
    }


    public void setHeaderTitle(int tv_id, int title, int width)
    {
        TextView textView=(TextView)this.findViewById(tv_id);
        textView.setWidth(CommonUtility.dip2px(this,width));
        textView.setText(getResources().getString(title));
        textView.setGravity(Gravity.CENTER);
        textView.setVisibility(View.VISIBLE);

        switch (tv_id){

            case R.id.tv_0:
                View view_0 = this.findViewById(R.id.view_0);
                view_0.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_1:
                View view_1 = this.findViewById(R.id.view_1);
                view_1.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_2:
                View view_2 = this.findViewById(R.id.view_2);
                view_2.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_3:
                View view_3 = this.findViewById(R.id.view_3);
                view_3.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_4:
                View view_4 = this.findViewById(R.id.view_4);
                view_4.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_5:
                View view_5 = this.findViewById(R.id.view_5);
                view_5.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_6:
                View view_6= this.findViewById(R.id.view_6);
                view_6.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_7:
                View view_7 = this.findViewById(R.id.view_7);
                view_7.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_8:
                View view_8 = this.findViewById(R.id.view_8);
                view_8.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_9:
                View view_9 = this.findViewById(R.id.view_9);
                view_9.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
