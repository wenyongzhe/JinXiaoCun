package com.eshop.jinxiaocun.piandian.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;

import java.io.Serializable;

/**
 * @Author Lu An
 * 创建时间  2018/8/27 0027
 * 描述
 */

public class PandianFanweiBeanResult extends BaseResult implements Serializable {

    /** type_id
     * 0	全场盘点
     * 1	单品盘点
     * 2	类别盘点
     * 3	品牌盘点
     */
    private String type_id;
    private String type_name;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
