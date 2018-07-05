package com.eshop.jinxiaocun.thread;

public interface TaskInterface {

    public Object doInBackground(Object[] objects);
    public void onPostExecute(Object o);
}
