package com.simple.mvpbase.demo;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
 * todo : 该类可以用于测试由于 postDelay 等异步的任务引发的空指针问题, 因为在 Activity的onDestroy 之后View会被置空, 如果异步处理不到位容易引发空指针.
 * see : http://www.ctolib.com/topics-114235.html
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


    // ============================== runnable intercept START =============================

    public static void config(Application context, boolean delayTestMode) {
        isTestCrashMode = delayTestMode ;
        if ( delayTestMode && BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 14) {
            context.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                Handler mHandler = new Handler(Looper.getMainLooper()) ;
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            NoLeakHandler.executePendingRunnables();
                        }
                    }, 200);
                }
            });
        }
    }


    private static final List<WeakReference<Runnable>> PENDING_RUNNABLES = new ArrayList<>() ;
    public static boolean isTestCrashMode = false ;

    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        if ( isRunnableMsg(msg) && isTestCrashMode && BuildConfig.DEBUG) {
            Toast.makeText(MvpApplication.sContext, "intercept post runnable", Toast.LENGTH_SHORT).show();
            PENDING_RUNNABLES.add(new WeakReference<>(msg.getCallback())) ;
            return true;
        }
        return super.sendMessageAtTime(msg, uptimeMillis);
    }

    public static void executePendingRunnables() {
        for (WeakReference<Runnable> runnable : PENDING_RUNNABLES) {
            runnable.get().run();
        }
        PENDING_RUNNABLES.clear();
    }

    private boolean isRunnableMsg(Message msg) {
        return msg.what == 0 && msg.getCallback() != null ;
    }

    // ============================== END =============================
}
