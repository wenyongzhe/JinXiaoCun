package com.eshop.jinxiaocun.xiaoshou.dao;

import com.eshop.jinxiaocun.base.BaseBean;
import com.eshop.jinxiaocun.netWork.jdbc.OrmLiteManager;
import com.eshop.jinxiaocun.xiaoshou.bean.TWmSheetMasterBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class TWmSheetMasterDao extends BaseBean {

    private Dao<TWmSheetMasterBean,Integer> dao;

    public Dao<TWmSheetMasterBean, Integer> getDao() {
        return dao;
    }

    public TWmSheetMasterDao() {
        OrmLiteManager mOrmLiteManager = OrmLiteManager.getOrmLiteManager();
        dao = mOrmLiteManager.creatDao(mOrmLiteManager.getConnectionSource(), TWmSheetMasterBean.class);

    }

}
