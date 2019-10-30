package com.eshop.jinxiaocun.bluetoothprinter;

import android.bluetooth.BluetoothAdapter;

public class SingletonPrinter { //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static SingletonPrinter instance;

    //构造器私有化
    private SingletonPrinter(){}

    //方法同步，调用效率低
    public static synchronized SingletonPrinter getInstance(){
        if(instance==null){
            instance=new SingletonPrinter();
        }
        return instance;
    }

    //打印小票
    private void printMs() {
        if( !BluetoothAdapter.getDefaultAdapter().isEnabled()){
            return;
        }
        //Print_Ex();
    }

}