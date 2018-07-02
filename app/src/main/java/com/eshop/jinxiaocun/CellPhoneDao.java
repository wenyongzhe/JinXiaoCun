package com.eshop.jinxiaocun;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class CellPhoneDao  {

    Dao<CellPhone, String> accountDao;

    public void performDBOperations(ConnectionSource connectionSource)
            throws SQLException {
        accountDao = DaoManager.createDao(connectionSource, CellPhone.class);
        // create table
//        TableUtils.createTableIfNotExists(connectionSource, CellPhone.class);

        CellPhone cp = new CellPhone();
        cp.setName("N 9522");
        cp.setCompany("Nokia");

        CellPhone cp1 = new CellPhone();
        cp1.setName("N 96");
        cp1.setCompany("android ");

        CellPhone cp3 = new CellPhone();
        cp3.setName("N 96");
        cp3.setCompany("from 中国 ");

        // save objects to DB
        accountDao.create(cp);
        accountDao.create(cp1);
        accountDao.create(cp3);

        // retrieve all objects from DB
        List<CellPhone> list = accountDao.queryForAll();
        System.out.println("*******List of objects saved in DB*******");
        for (CellPhone cellPhone : list) {
            System.out.println(cellPhone);
        }

    }

}
