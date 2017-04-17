package com.simple.mvpbase.demo;

import android.os.Handler;
import android.os.Looper;

import com.simple.mvp.MvpView;
import com.simple.mvp.Presenter;

/**
 * Created by mrsimple on 5/4/17.
 */

public class BasePresenter<V extends MvpView> extends Presenter<V> {

    private Handler mHandler2 = new Handler(Looper.getMainLooper()) ;

    @Override
    public void detach() {
        super.detach();
        HandlerCleaner.removeAllMessages(this);
    }
}
