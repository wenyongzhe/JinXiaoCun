//package com.eshop.jinxiaocun.netWork.httpDB;
//
//import android.content.Context;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2018/3/27.
// */
//
//public class NetManager implements INetWork{
//    private static INetWork instance;
//    private static Context mContext;
//
//    /*使用方法
//     NetManager.getRequest().doPost(RequestConst.GetReport, param, this);
//     */
//    public static INetWork getRequest(){
//        return instance;
//    }
//
//    static HashMap<String,INetWork> mMap=new HashMap<>();
//
//    public NetManager(Context context) {
//        instance = NetWorkImp.getInstance();
//        mContext = context.getApplicationContext();
//        instance.init(NetManager.mContext);
//    }
//
//    public void  init(Context context){
//        instance = NetWorkImp.getInstance();
//        mContext = context.getApplicationContext();
//        instance.init(NetManager.mContext);
//    }
//
//    @Override
//    public void doGet(String url, Map<String, String> paramsMap, IResponseListener iResponseListener) {
//        instance.doGet( url, paramsMap,  iResponseListener);
//    }
//
//    @Override
//    public void doGet(String url, Map<String, String> paramsMap, NetworkOption networkOption, IResponseListener iResponseListener) {
//        instance.doGet( url, paramsMap, networkOption, iResponseListener);
//    }
//
//    @Override
//    public void doPost(String url, Map<String, String> paramsMap, IResponseListener iResponseListener) {
//        instance.doPost( url, paramsMap,  iResponseListener);
//    }
//
//    @Override
//    public void doPost(String url, String jsonParams, IResponseListener iResponseListener) {
//        instance.doPost( url, jsonParams,  iResponseListener);
//    }
//
//    @Override
//    public void doPost(String url, Map<String, String> paramsMap, NetworkOption networkOption, IResponseListener iResponseListener) {
//        instance.doPost( url, paramsMap,  networkOption,iResponseListener);
//    }
//
//    @Override
//    public void cancel(Object tag) {
//        instance.cancel(tag);
//    }
//
//
//    // 采用反射的形式实现，这样有一个好处是，以后增加新的实现类的话，我们只需要传递相应 的 Class，
////而不需要更改 NetManger 的代码
//    /*public static <T extends INetWork> INetWork getRequest(Class<T> clz){
//        String simpleName = clz.getSimpleName();
//        INetWork request = mMap.get(simpleName);
//        if(request ==null){
//            try {
//                Constructor<T> constructor = clz.getDeclaredConstructor();
//                constructor.setAccessible(true);
//                request = constructor.newInstance();
//                request.init(mContext);
//                mMap.put(simpleName,request);
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//        instance=request;
//        return request;
//    }*/
//}
