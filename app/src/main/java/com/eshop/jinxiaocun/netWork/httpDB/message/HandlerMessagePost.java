package com.eshop.jinxiaocun.netWork.httpDB.message;


import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.eshop.jinxiaocun.R;
import com.eshop.jinxiaocun.base.IJsonFormat;
import com.eshop.jinxiaocun.base.JsonFormatImp;
import com.eshop.jinxiaocun.base.bean.Result;
import com.eshop.jinxiaocun.base.view.Application;
import com.eshop.jinxiaocun.netWork.httpDB.IResponseListener;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.Response;

/**
 * Created by Administrator on 2018/3/28.
 */

public class HandlerMessagePost implements IMessagePost {
    IJsonFormat mJsonFormatImp = new JsonFormatImp();

    @Override
    public void postError(IOException e, IResponseListener o) {
        o.handleError(e);
        Toast.makeText(Application.mContext, R.string.message_error,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postResult(Response response, IResponseListener o) {
        byte[] b = new byte[0]; //获取数据的bytes
        try {
            if(response.code() !=200){
                o.handleResult(response,response.message() );
                o.handleResultJson("-1",response.message(),"");
                return;
            }
            b = response.body().bytes();
            String info = new String(b, "GB2312"); //然后将其转为gb2312
            Result mResult = mJsonFormatImp.JsonToBean(info, Result.class);
            o.handleResult(response,info );
            o.handleResultJson(mResult.status,mResult.msg,mResult.jsonData);
            if(Integer.decode(mResult.status)!=0){
                ToastUtils.showShort(mResult.msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            o.handleResult(response,e.getMessage() );
            o.handleResultJson("-1",e.getMessage(),"");
        }
    }

    @Override
    public void postMessage(Object o) {

    }
}
