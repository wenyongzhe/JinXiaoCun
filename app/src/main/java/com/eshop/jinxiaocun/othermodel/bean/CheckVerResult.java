package com.eshop.jinxiaocun.othermodel.bean;

import com.eshop.jinxiaocun.base.bean.BaseResult;
import com.eshop.jinxiaocun.lingshou.bean.GetSystemBeanResult;

import java.io.Serializable;

/**
 */
public class CheckVerResult extends BaseResult {

    private CheckVerResult.CheckVerJson jsonData;

    public CheckVerResult.CheckVerJson getJsonData() {
        return jsonData;
    }

    public void setJsonData(CheckVerResult.CheckVerJson jsonData) {
        jsonData = jsonData;
    }

    public class CheckVerJson{
        public String ServerVer;// :”V2.0” //服务器版本号
        public int bUpdate;//: 1 //是否需要升级 0:不需要 1：需要
        public String AppUrl;//" :”” //新APP下载地址
    }



}
