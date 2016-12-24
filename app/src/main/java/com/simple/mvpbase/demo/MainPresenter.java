package com.simple.mvpbase.demo;

import android.os.Handler;
import android.os.Looper;

import com.simple.mvp.Presenter;

/**
 * Created by mrsimple on 24/12/16.
 */

public class MainPresenter extends Presenter<MainView> {

    private Handler mHandler = new Handler(Looper.getMainLooper()) ;

    public void fetchNews() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMvpView().showNews();
            }
        }, 2000);
    }
}
