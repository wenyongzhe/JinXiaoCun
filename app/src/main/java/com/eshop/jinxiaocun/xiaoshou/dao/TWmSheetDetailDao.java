package com.eshop.jinxiaocun.xiaoshou.dao;

import com.eshop.jinxiaocun.base.view.BaseBean;
import com.eshop.jinxiaocun.netWork.jdbc.OrmLiteManager;
import com.eshop.jinxiaocun.xiaoshou.bean.TWmSheetDetailBean;
import com.j256.ormlite.dao.Dao;

public class TWmSheetDetailDao extends BaseBean {

    private Dao<TWmSheetDetailBean,Integer> dao;

    public Dao<TWmSheetDetailBean, Integer> getDao() {
        return dao;
    }


    public TWmSheetDetailDao() {
        OrmLiteManager mOrmLiteManager = OrmLiteManager.getOrmLiteManager();
        dao = mOrmLiteManager.creatDao(mOrmLiteManager.getConnectionSource(), TWmSheetDetailBean.class);

    }

}
