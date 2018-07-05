package com.eshop.jinxiaocun.xiaoshou.dao;

import com.eshop.jinxiaocun.base.BaseBean;
import com.eshop.jinxiaocun.netWork.jdbc.OrmLiteManager;
import com.eshop.jinxiaocun.xiaoshou.bean.TVipNormalCertBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class TVipNormalDao extends BaseBean {

    private Dao<TVipNormalCertBean,Integer> dao;

    public Dao<TVipNormalCertBean, Integer> getDao() {
        return dao;
    }

    public TVipNormalDao() {
        OrmLiteManager mOrmLiteManager = OrmLiteManager.getOrmLiteManager();
        dao = mOrmLiteManager.creatDao(mOrmLiteManager.getConnectionSource(), TVipNormalCertBean.class);
    }

}
