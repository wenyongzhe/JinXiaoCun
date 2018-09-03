package com.eshop.jinxiaocun.base;

import com.eshop.jinxiaocun.utils.GsonUtil;

import java.util.List;

public class JsonFormatImp implements IJsonFormat {


    @Override
    public String ObjetToString(Object object) {
        return GsonUtil.GsonString(object);
    }

    @Override
    public <T> T JsonToBean(String jsonString, Class<T> cls) {
        return GsonUtil.GsonToBean(jsonString,cls);
    }

    @Override
    public <T> List<T> JsonToList(String gsonString, Class<T> cls) {
        return GsonUtil.jsonToList(gsonString,cls);
    }

    @Override
    public <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        return GsonUtil.GsonToList(gsonString,cls);
    }
}
