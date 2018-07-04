package com.eshop.jinxiaocun.netWork.httpDB.message;


import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/28.
 */

public class HandlerMessagePost implements IMessagePost {
    @Override
    public void postError(IOException e, IResponseListener o) {
        o.handleError(e);
    }

    @Override
    public void postResult(Object response, IResponseListener o) {
        o.handleResult(response);
    }

    @Override
    public void postMessage(Object o) {

    }
}
