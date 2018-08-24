package com.eshop.jinxiaocun.netWork.httpDB.message;


import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/28.
 */

public class HandlerMessagePost implements IMessagePost {
    @Override
    public void postError(IOException e, IResponseListener o) {
        o.handleError(e);
    }

    @Override
    public void postResult(Response response, IResponseListener o) {
        byte[] b = new byte[0]; //获取数据的bytes
        try {
            b = response.body().bytes();
            String info = new String(b, "GB2312"); //然后将其转为gb2312
            o.handleResult(response,info );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postMessage(Object o) {

    }
}
