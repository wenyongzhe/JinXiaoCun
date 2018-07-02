package com.eshop.jinxiaocun.NetWork.HttpDB.Message;

import android.content.Context;


import com.eshop.jinxiaocun.NetWork.HttpDB.IResponseListener;
import com.eshop.jinxiaocun.NetWork.HttpDB.OKHttpRequest;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MessageManage {

    private static MessageManage myMessageManage = null;
    private IMessagePost messagePost;

    public static void  init(Context context){
    }

    public MessageManage() {
        messagePost = new HandlerMessagePost();
    }

    public static MessageManage getInstance(){
        if (myMessageManage == null) {
            synchronized (OKHttpRequest.class) {
                if (myMessageManage == null) {
                    myMessageManage = new MessageManage();
                }
            }
        }
        return myMessageManage;
    }

    public void postMessage(Object obj){
        messagePost.postMessage(obj);
    }

    public void postError(IOException e,IResponseListener obj){
        messagePost.postError(e,obj);
    }

    public void postResult(Object response ,IResponseListener obj){
        messagePost.postResult(response,obj);
    }
}
