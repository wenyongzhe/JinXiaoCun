package com.eshop.jinxiaocun.base;

import java.util.List;

public interface IJsonFormat {
    String ObjetToString(Object object);
    <T> T JsonToBean(String jsonString, Class<T> cls);
    <T> List<T> JsonToList(String gsonString, Class<T> cls);
    <T> List<T> GsonToList(String gsonString, Class<T> cls);

}
