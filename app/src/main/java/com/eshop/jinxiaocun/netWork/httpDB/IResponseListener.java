package com.eshop.jinxiaocun.netWork.httpDB;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface IResponseListener {
    void handleError(Object event);
    void handleResult(Response event, String result);
    void handleResultJson(String status, String msg, String jsonData);
}
