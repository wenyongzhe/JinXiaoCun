package com.eshop.jinxiaocun.netWork.httpDB;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface IResponseListener {
//    void handleMessage(Object event);
    void handleError(Object event);
    void handleResult(Response event);
}
