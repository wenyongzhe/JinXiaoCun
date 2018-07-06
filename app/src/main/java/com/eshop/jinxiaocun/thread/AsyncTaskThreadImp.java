package com.eshop.jinxiaocun.thread;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class AsyncTaskThreadImp implements ThreadManagerInterface {

    private static TaskInterface taskInterface;

    @Override
    public void executeRunnable(TaskInterface taskInterface) {
            AsyncTaskThreadImp.taskInterface = taskInterface;
            asyncTask.execute();
    }

    @SuppressLint("StaticFieldLeak")
    AsyncTask asyncTask =  new AsyncTask() {

        @Override
        protected Object doInBackground(Object[] objects) {
            return AsyncTaskThreadImp.taskInterface.doInBackground(objects);
        }

        @Override
        protected void onPostExecute(Object o) {
            AsyncTaskThreadImp.taskInterface.onPostExecute(o);
        }
    };

}
