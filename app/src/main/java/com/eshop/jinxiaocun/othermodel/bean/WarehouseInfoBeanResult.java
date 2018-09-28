package com.eshop.jinxiaocun.othermodel.bean;

import java.io.Serializable;

/**
 * @Author Lu An
 * 创建时间  2018/9/28 0028
 * 描述
 */

public class WarehouseInfoBeanResult implements Serializable {

    private String Id;//仓库号
    private String Name;//仓库名
    private String Zjm;//助记码

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getZjm() {
        return Zjm;
    }

    public void setZjm(String zjm) {
        Zjm = zjm;
    }
}
