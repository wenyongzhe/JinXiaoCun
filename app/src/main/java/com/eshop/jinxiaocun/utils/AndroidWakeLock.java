package com.eshop.jinxiaocun.utils;

import android.os.PowerManager;

public class AndroidWakeLock {
    PowerManager.WakeLock wakelock;
    PowerManager pmr;

    public AndroidWakeLock(PowerManager pm) {
        this.pmr = pm;
    }

    public void WakeLock() {
        if (this.wakelock == null) {
            this.wakelock = this.pmr.newWakeLock(26, this.getClass().getCanonicalName());
        }

        this.wakelock.acquire();
    }

    public void ReleaseWakeLock() {
        if (this.wakelock != null && this.wakelock.isHeld()) {
            this.wakelock.release();
            this.wakelock = null;
        }

    }
}
