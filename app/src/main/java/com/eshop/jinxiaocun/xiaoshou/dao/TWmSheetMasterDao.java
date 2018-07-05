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

    public int add(Object mObject){
        try {
            return dao.create((TWmSheetMasterBean) mObject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int create(Object data) throws SQLException {
        return dao.create((TWmSheetMasterBean) data);
    }

    @Override
    public Object queryForId(Object o) throws SQLException {
        try {
            return dao.queryForId((Integer) o);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List queryForAll() throws SQLException {
        return null;
    }

}
