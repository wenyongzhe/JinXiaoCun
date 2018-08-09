package com.eshop.jinxiaocun.base;

import java.util.List;

public interface IJsonFormat {
    public String ObjetToString(Object object);
    public <T> T JsonToBean(String jsonString, Class<T> cls);
    public <T> List<T> JsonToList(String gsonString, Class<T> cls);


}
