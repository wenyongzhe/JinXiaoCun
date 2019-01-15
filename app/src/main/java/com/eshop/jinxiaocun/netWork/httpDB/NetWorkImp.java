package com.eshop.jinxiaocun.netWork.httpDB;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.eshop.jinxiaocun.netWork.httpDB.message.MessageManage;
import com.eshop.jinxiaocun.utils.MD5Util;
import com.eshop.jinxiaocun.utils.MyUtils;
import com.eshop.jinxiaocun.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2018/3/27.
 */

public class NetWorkImp implements INetWork {

    private static OkHttpClient myOkHttpClient = null;

//    private volatile static NetWorkImp mOKHttpRequest;
    private MessageManage messageManage;

    public NetWorkImp(Context context){
        init(context);
    }

//    public static NetWorkImp getInstance(){
//        if (mOKHttpRequest == null) {
//            synchronized (NetWorkImp.class) {
//                if (mOKHttpRequest == null) {
//                    mOKHttpRequest = new NetWorkImp();
//                }
//            }
//        }
//        return mOKHttpRequest;
//    }

    @Override
    public void init(Context context) {
        myOkHttpClient = getOkHttpClient();
        messageManage = MessageManage.getInstance();
    }

    public synchronized static OkHttpClient getOkHttpClient() {
        if (myOkHttpClient == null) {
            //判空 为空创建实例
            // okHttpClient = new OkHttpClient();
            /**
             * 和OkHttp2.x有区别的是不能通过OkHttpClient直接设置超时时间和缓存了，而是通过OkHttpClient.Builder来设置，
             * 通过builder配置好OkHttpClient后用builder.build()来返回OkHttpClient，
             * 所以我们通常不会调用new OkHttpClient()来得到OkHttpClient，而是通过builder.build()：
             */
            //  File sdcache = getExternalCacheDir();
            //缓存目录
            File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
            int cacheSize = 10 * 1024 * 1024;
            //OkHttp3拦截器
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("xxx", message.toString());
                }
            });
            //Okhttp3的拦截器日志分类 4种
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


            myOkHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES)
                    //添加OkHttp3的拦截器
                    .addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(new CacheInterceptor())
                    .writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
                    //.cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))
                    .build();
        }
        return myOkHttpClient;
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (MyUtils.isOpenNetwork()) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
               /* Looper.prepare();
                Toast.makeText(MyApp.getInstance(), "走拦截器缓存", Toast.LENGTH_SHORT).show();
                Looper.loop();*/
            }
            Response response = chain.proceed(request);
            if (MyUtils.isOpenNetwork()) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    @Override
    public void doGet(String url, Map<String, String> paramsMap, final IResponseListener iResponseListener) {
        String md5Tem = paramsMap.get("JsonData")+"C41Ore7aL5n8E";
        String md5 = MD5Util.string2MD5(md5Tem);
        paramsMap.put("Sign",md5);
        paramsMap.put("sign",md5);
        doGet(url,paramsMap,null,iResponseListener);
    }

    @Override
    public void doGet(String url, Map<String, String> paramsMap, NetworkOption networkOption,
                      final   IResponseListener iResponseListener) {
        //url= NetWorkUtil.checkUrl(url);
        url= NetWorkUtil.appendUrl(url,paramsMap);
//        final NetworkOption option=NetUtils.checkNetworkOption(networkOption,url);
        Request.Builder builder;
        if(networkOption==null){
            builder = new Request.Builder().url(url);
        }else{
            builder = new Request.Builder().url(url).tag(networkOption.mTag);
            builder=configHeaders(builder,networkOption);
        }

        Request build = builder.build();

        myOkHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                messageManage.postError(e, iResponseListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                messageManage.postResult(response, iResponseListener);
            }
        });
    }



    private Request.Builder configHeaders(Request.Builder builder, NetworkOption option) {
        Map<String, String> headers = option.mHeaders;
        if(headers==null || headers.size()==0){
            return builder;
        }
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for(Map.Entry<String, String> entry: entries){
            String key = entry.getKey();
            String value = entry.getValue();
            // 添加请求头
            builder.addHeader(key,value);
        }
        return builder;

    }

    @Override
    public void doPost(String url, Map<String, String> paramsMap, final IResponseListener iResponseListener) {
        String md5 = MD5Util.string2MD5(paramsMap.get("JsonData")==null?paramsMap.get("jsonData"):paramsMap.get("JsonData"));
        paramsMap.put(paramsMap.get("Sign")==null?"sign":"Sign",md5.toUpperCase());
        doPost(url,paramsMap,null,iResponseListener);

    }

    @Override
    public void doPost(String url, String jsonParams, final IResponseListener iResponseListener) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        myOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                messageManage.postError(e, iResponseListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                messageManage.postResult(response, iResponseListener);
            }
        });
    }

    private FormBody.Builder configPushParam(FormBody.Builder builder, Map<String, String> paramsMap) {
        if(paramsMap!=null){
            Set<Map.Entry<String, String>> entries = paramsMap.entrySet();
            for(Map.Entry<String,String> entry:entries ){
                String key = entry.getKey();
                String value = entry.getValue();
                builder.add(key,value);
            }
        }
        return builder;
    }

    @Override
    public void doPost(String url, Map<String, String> paramsMap, NetworkOption networkOption,
                       final IResponseListener iResponseListener) {
//        url= NetUtils.checkUrl(url);
//        final NetworkOption option=NetUtils.checkNetworkOption(networkOption,url);
        // 以表单的形式提交
        FormBody.Builder builder = new FormBody.Builder();
        builder=configPushParam(builder,paramsMap);
        FormBody formBody = builder.build();

        Request.Builder requestBuilder;
        if(networkOption != null){
            requestBuilder = new Request.Builder().url(url).post(formBody).tag(networkOption.mTag);
            requestBuilder=configHeaders(requestBuilder,networkOption);
        }else{
            requestBuilder = new Request.Builder().url(url).post(formBody);
        }

        Request request = requestBuilder.build();
        myOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                messageManage.postError(e, iResponseListener);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                messageManage.postResult(response, iResponseListener);
            }
        });
    }

    @Override
    public void cancel(Object tag) {
        if(myOkHttpClient!=null){
            if(myOkHttpClient != null) {
                // 在等待队列中查找是否有相应的请求
                for(Call call : myOkHttpClient.dispatcher().queuedCalls()) {
                    if(call.request().tag().equals(tag))
                        call.cancel();
                }
                // 在正在请求的请求队列中查找是否有相应的请求
                for(Call call : myOkHttpClient.dispatcher().runningCalls()) {
                    if(call.request().tag().equals(tag))
                        call.cancel();
                }
            }
        }
    }
}
