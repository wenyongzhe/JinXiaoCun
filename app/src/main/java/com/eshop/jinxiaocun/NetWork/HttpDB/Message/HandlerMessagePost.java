package com.eshop.jinxiaocun.NetWork.HttpDB.Message;


import com.eshop.jinxiaocun.NetWork.HttpDB.IResponseListener;

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
