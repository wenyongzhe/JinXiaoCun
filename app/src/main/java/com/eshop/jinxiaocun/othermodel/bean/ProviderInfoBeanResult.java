package com.eshop.jinxiaocun.othermodel.bean;

import java.io.Serializable;

/**
 * @Author Lu An
 * 创建时间  2018/9/28 0028
 * 描述 供应商信息
 */

public class ProviderInfoBeanResult implements Serializable {
    private String Id;//
    private String Name;//
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
