package com.eshop.jinxiaocun.xiaoshou.bean;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "pbcatcol")
public class Pbcatcol {


    @DatabaseField(columnName = "pbc_tnam")
    private String pbc_tnam;

    @DatabaseField(columnName = "pbc_ownr")
    private String pbc_ownr;


    public Pbcatcol() {
    }
}
