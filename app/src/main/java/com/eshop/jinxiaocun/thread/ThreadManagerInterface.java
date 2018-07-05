package com.eshop.jinxiaocun.thread;

import com.blankj.utilcode.util.ThreadUtils;

import java.util.logging.Handler;

public interface ThreadManagerInterface {

    public void executeRunnable(TaskInterface taskInterface);
}
