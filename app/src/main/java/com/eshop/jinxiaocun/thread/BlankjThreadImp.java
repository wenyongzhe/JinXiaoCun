package com.eshop.jinxiaocun.thread;

import android.os.AsyncTask;

public class BlankjThreadImp implements ThreadManagerInterface {

    private static TaskInterface taskInterface;

    @Override
    public void executeRunnable(TaskInterface taskInterface) {

        BlankjThreadImp.taskInterface = taskInterface;
        asyncTask.execute();

    }

    AsyncTask asyncTask =  new AsyncTask() {

        @Override
        protected Object doInBackground(Object[] objects) {
            return BlankjThreadImp.taskInterface.doInBackground(objects);
        }

        @Override
        protected void onPostExecute(Object o) {
            BlankjThreadImp.taskInterface.onPostExecute(o);
        }
    };

}
