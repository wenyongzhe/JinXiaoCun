package com.eshop.jinxiaocun.netWork.httpDB.json;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public interface IJsonFormat {
    public <T> List<T> jsonToList(String gsonString, Class<T> cls);
    public <T> List<T> jsonToList(String gsonString);
    public <T> T jsonToBean(String gsonString, Class<T> cls);
    public <T> T getData(String list);
    public String GsonString(Object object);
}
