package com.eshop.jinxiaocun.login.Bean;

public class LoginBeanResult {

    private String intValue; //收银权限 1:允许
    private String strgrant;//”100001” //操作权限 1字节表示一种类型
    private String result;
    private String message;

    public String getIntValue() {
        return intValue;
    }

    public void setIntValue(String intValue) {
        this.intValue = intValue;
    }

    public String getStrgrant() {
        return strgrant;
    }

    public void setStrgrant(String strgrant) {
        this.strgrant = strgrant;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
