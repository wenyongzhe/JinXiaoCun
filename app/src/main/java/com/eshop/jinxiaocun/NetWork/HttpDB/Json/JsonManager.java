package com.eshop.jinxiaocun.NetWork.HttpDB.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/28.
 */

public class JsonManager {
    private static JsonManager mJsonManager = null;
    private IJsonFormat mJsonFormat;

    private JsonManager() {
        mJsonFormat = new GsonFormat();
    }

    public static JsonManager getInstance(){
        if (mJsonManager == null) {
            synchronized (JsonManager.class) {
                if (mJsonManager == null) {
                    mJsonManager = new JsonManager();
                }
            }
        }
        return mJsonManager;
    }

//    public ResultItemEntity getReportItemData(String json, String flag){
//        json = jsonToString(json, flag);
//        ResultItemEntity mReportEntity = (ResultItemEntity) jsonToBean(json, ResultItemEntity.class);
//        if("".equals(mReportEntity.Json)){
//            return null;
//        }
//        mReportEntity.mJsonEntity = (List<ResultItemEntity.JsonEntity>) jsonToList(mReportEntity.Json, ResultItemEntity.JsonEntity.class);
//        return mReportEntity;
//    }
//
//    public LoginLoadBgImp.LoginPicEntity getLoginPic(String json){
//        LoginLoadBgImp.LoginPicEntity mReportEntity = (LoginLoadBgImp.LoginPicEntity) jsonToBean(json, LoginLoadBgImp.LoginPicEntity.class);
//        mReportEntity.mGetLoginPicResult = jsonToList(mReportEntity.GetLoginPicResult, LoginLoadBgImp.LoginPicItem.class);
//        return mReportEntity;
//    }
//
//    public ReportEntity getGridData(String json, String flag){
//        json = jsonToString(json, flag);
//        ReportEntity mReportEntity = (ReportEntity) jsonToBean(json, ReportEntity.class);
//
//        mReportEntity.mJsonEntity = (ReportEntity.JsonEntity) jsonToBean(mReportEntity.Json, ReportEntity.JsonEntity.class);
//        mReportEntity.mJsonEntity.mDataList = getDataBeanList(mReportEntity.Json);
//        mReportEntity.mJsonEntity.mFooterList = getFootBean(mReportEntity.Json);
//        return mReportEntity;
//    }
//
//    //款报表
//    public StyleReportEntity getStyleReportData(String json, String flag){
//        json = jsonToString(json, flag);
//        StyleReportEntity mReportEntity = (StyleReportEntity) jsonToBean(json, StyleReportEntity.class);
//        mReportEntity.mJsonEntityList = (List<StyleReportEntity.JsonEntity>) jsonToList(mReportEntity.Json, StyleReportEntity.JsonEntity.class);
//        return mReportEntity;
//    }
//
//    public ResultEntity getSaveMixGrid(String json, String flag){
//        json = jsonToString(json, flag);
//        ResultEntity mResultEntity = (ResultEntity) jsonToBean(json, ResultEntity.class);
//        return mResultEntity;
//    }
//
//    public MixOrderEntity getMixOrderData(String json, String flag){
//        json = jsonToString(json, flag);
//        MixOrderEntity mReportEntity = (MixOrderEntity) jsonToBean(json, MixOrderEntity.class);
//
//        mReportEntity.mJsonEntity = (MixOrderEntity.JsonEntity) jsonToBean(mReportEntity.Json, MixOrderEntity.JsonEntity.class);
//        mReportEntity.mJsonEntity.mDataList = getOrderDataBeanList(mReportEntity.Json);
//        mReportEntity.mJsonEntity.mFooterList = getOrderFootBean(mReportEntity.Json);
//        return mReportEntity;
//    }
//
//    public ReportEntity getChartData(String json,String flag){
//        try {
//            json = jsonToString(json, flag);
//            ReportEntity mReportEntity = (ReportEntity) jsonToBean(json, ReportEntity.class);
//
//            JSONObject jsonObject = new JSONObject(mReportEntity.Json);
//            mReportEntity.Json = jsonObject.get("Grid").toString();
//            mReportEntity.mJsonEntity = (ReportEntity.JsonEntity) jsonToBean(mReportEntity.Json, ReportEntity.JsonEntity.class);
//            mReportEntity.mJsonEntity.mDataList = getDataBeanList(mReportEntity.Json);
//            mReportEntity.mJsonEntity.mFooterList = getFootBean(mReportEntity.Json);
//            return mReportEntity;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public <T> Object jsonToBean(String json, Class<T> cls){
        return mJsonFormat.jsonToBean(json, cls);
    }

    public String beanToJson(List obj){
        return mJsonFormat.GsonString(obj);
    }

    public String jsonToString(String json,String flag){
        try {
            JSONObject jsonObject = new JSONObject(json);
            json = jsonObject.getString(flag);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public <T> List<T> jsonToList(String json, Class<T> cls) {

        return mJsonFormat.jsonToList(json,cls);
    }

//    private Map<String, MixOrderEntity.JsonData> getOrederBodyBean(JSONObject jsondata) {
//        try {
//            Map<String, MixOrderEntity.JsonData> map = new HashMap<>();
//            Iterator<String> keys = jsondata.keys();
//            while (keys.hasNext()){
//                String key = keys.next();
//                MixOrderEntity.JsonData jsonObj = (MixOrderEntity.JsonData) jsonToBean(jsondata.getJSONObject(key).toString(),MixOrderEntity.JsonData.class);
//                map.put(key,jsonObj);
//            }
//            return map;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    private Map<String, String> getBodyBean(JSONObject jsondata) {
        try {
            Map<String, String> map = new HashMap<>();
            Iterator<String> keys = jsondata.keys();
            while (keys.hasNext()){
                String key = keys.next();
                map.put(key,jsondata.getString(key));
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

//    public List<Map<String,MixOrderEntity.JsonData>> getOrderDataBeanList(String json) {
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray mJSONArray = jsonObject.getJSONArray("Data");
//            List list =new ArrayList();
//            for (int i=0; i<mJSONArray.length(); i++){
//                list.add(getOrederBodyBean((JSONObject) mJSONArray.get(i)));
//            }
//            return list;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public List<Map<String,String>> getDataBeanList(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray mJSONArray = jsonObject.getJSONArray("Data");
            List list =new ArrayList();
            for (int i=0; i<mJSONArray.length(); i++){
                list.add(getBodyBean((JSONObject) mJSONArray.get(i)));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


//    public Map<String, MixOrderEntity.JsonData> getOrderFootBean(String json) {
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            jsonObject = jsonObject.getJSONObject("Footer");
//
//            Map<String, MixOrderEntity.JsonData> map = new HashMap<>();
//            Iterator<String> keys = jsonObject.keys();
//            while (keys.hasNext()){
//                String key = keys.next();
//                MixOrderEntity.JsonData jsonObj = (MixOrderEntity.JsonData) jsonToBean(jsonObject.getJSONObject(key).toString(),MixOrderEntity.JsonData.class);
//                map.put(key,jsonObj);
//            }
//            return map;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public Map<String, String> getFootBean(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject = jsonObject.getJSONObject("Footer");

            Map<String, String> map = new HashMap<>();
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = keys.next();
                map.put(key,jsonObject.getString(key));
            }
            return map;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
