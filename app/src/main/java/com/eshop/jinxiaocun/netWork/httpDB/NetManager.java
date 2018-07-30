package com.eshop.jinxiaocun.netWork.httpDB;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/3/27.
 */

public class NetManager {
    private static NetRequest instance;
    private static Context mContext;

    /*使用方法
     NetManager.getRequest().doPost(RequestConst.GetReport, param, this);
     */
    public static NetRequest getRequest(){
        return instance;
    }

    static HashMap<String,NetRequest> mMap=new HashMap<>();

    public static void  init(Context context){
        instance = OKHttpRequest.getInstance();
        mContext = context.getApplicationContext();
        instance.init(NetManager.mContext);
    }


    // 采用反射的形式实现，这样有一个好处是，以后增加新的实现类的话，我们只需要传递相应 的 Class，
//而不需要更改 NetManger 的代码
    /*public static <T extends NetRequest> NetRequest getRequest(Class<T> clz){
        String simpleName = clz.getSimpleName();
        NetRequest request = mMap.get(simpleName);
        if(request ==null){
            try {
                Constructor<T> constructor = clz.getDeclaredConstructor();
                constructor.setAccessible(true);
                request = constructor.newInstance();
                request.init(mContext);
                mMap.put(simpleName,request);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        instance=request;
        return request;
    }*/
}
