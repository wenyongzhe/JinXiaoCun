package com.eshop.jinxiaocun.NetWork;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface NetRequest {
    void init(Context context);

    void doGet(String url, final Map<String, String> paramsMap, final IResponseListener iResponseListener);

    void doGet(String url, final Map<String, String> paramsMap, NetworkOption networkOption, final IResponseListener iResponseListener);


    void doPost(String url, final Map<String, String> paramsMap, final IResponseListener iResponseListener);

    void doPost(String url, String jsonParams, final IResponseListener iResponseListener);

    void doPost(String url, final Map<String, String> paramsMap, NetworkOption networkOption,
                final IResponseListener iResponseListener);


    void cancel(Object tag);}
