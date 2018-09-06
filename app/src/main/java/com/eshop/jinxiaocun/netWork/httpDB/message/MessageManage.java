package com.eshop.jinxiaocun.netWork.httpDB.message;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;


import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;
import com.eshop.jinxiaocun.netWork.httpDB.NetWorkImp;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MessageManage {

    private static MessageManage myMessageManage = null;
    private IMessagePost messagePost;
    private Handler mHandler;

    public static void  init(Context context){
    }

    private MessageManage() {
        messagePost = new HandlerMessagePost();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static MessageManage getInstance(){
        if (myMessageManage == null) {
            synchronized (NetWorkImp.class) {
                if (myMessageManage == null) {
                    myMessageManage = new MessageManage();
                }
            }
        }
        return myMessageManage;
    }

    public void postMessage(final Object obj){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                messagePost.postMessage(obj);
            }
        });
    }

    public void postError(final IOException e,final IResponseListener obj){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                messagePost.postError(e,obj);
            }
        });
    }

    public void postResult(final Response response , final IResponseListener obj){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                messagePost.postResult(response,obj);
            }
        });
    }
}
