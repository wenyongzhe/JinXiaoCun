package com.eshop.jinxiaocun.NetWork.HttpDB.Message;


import com.eshop.jinxiaocun.NetWork.HttpDB.IResponseListener;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/28.
 */

public interface IMessagePost {
    void postError(IOException e, IResponseListener o);
    void postResult(Object response, IResponseListener o);
    void postMessage(Object o);
}
