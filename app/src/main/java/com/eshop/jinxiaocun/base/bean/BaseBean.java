package com.eshop.jinxiaocun.base.bean;

import com.eshop.jinxiaocun.utils.Config;

public class BaseBean {

    private String DevID = Config.DeviceID;
    private int SoftVer = Config.VersionCode;
    private String strCmd;

    public String getDevID() {
        return DevID;
    }

    public void setDevID(String devID) {
        DevID = devID;
    }

    public int getSoftVer() {
        return SoftVer;
    }

    public void setSoftVer(int softVer) {
        SoftVer = softVer;
    }

    public String getStrCmd() {
        return strCmd;
    }

    public void setStrCmd(String strCmd) {
        this.strCmd = strCmd;
    }

    public  String getTableName(){return "";};
}
