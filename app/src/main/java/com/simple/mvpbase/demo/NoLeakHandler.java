package com.simple.mvpbase.demo;

import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * todo :
 * 如果是内部类覆写了handleMessage函数, 那么子类需要是 static class, 避免内存泄露 !
 * 例如:
 *
 * static class MainHandler extends NoLeakHandler {
 *      @Override
        public void handleMessage(Message msg) {
            // do sth
        }
 * }
 *
 * Created by mrsimple on 31/3/17.
 */
public class NoLeakHandler extends Handler {
    /**
     * 外部引用, 例如Activity, Fragment, Dialog等
     */
    protected WeakReference<Object> mTargetRef ;

    public NoLeakHandler() {
    }

    public NoLeakHandler(Object external) {
        this.mTargetRef = new WeakReference<>(external);
    }

    protected  <T> T getTarget() {
        if ( isTargetAlive() ) {
            return (T) mTargetRef.get() ;
        }
        return null;
    }

    protected boolean isTargetAlive() {
        checkTarget();
        return mTargetRef != null && mTargetRef.get() != null;
    }

    private void checkTarget() {
        if ( mTargetRef != null && mTargetRef.get() == null ) {
            removeCallbacksAndMessages(null);
        }
    }
}
