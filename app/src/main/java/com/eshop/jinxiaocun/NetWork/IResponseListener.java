package com.eshop.jinxiaocun.NetWork;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface IResponseListener {
    void handleMessage(Object event);
    void handleError(Object event);
    void handleResult(Object event);
}
