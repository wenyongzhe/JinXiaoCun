package com.eshop.jinxiaocun.base.view;

import com.eshop.jinxiaocun.widget.RefreshListView;
import com.eshop.jinxiaocun.xiaoshou.presenter.IDanJuList;

public abstract class BaseListFragment extends BaseFragment{

    protected RefreshListView mListView;
    /** 页数 */
    protected int page = 1;
    /** 每次请求的条数 */
    protected int limit = 10;

    public IDanJuList mDanJuList;
}
