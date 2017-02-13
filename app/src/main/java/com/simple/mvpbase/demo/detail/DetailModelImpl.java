package com.simple.mvpbase.demo.detail;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * Created by mrsimple on 13/2/17.
 */

public class DetailModelImpl implements DetailModel {


    private Handler mHandler = new Handler(Looper.getMainLooper()) ;

    @Override
    public void fetchDetail(final DetailListener listener) {
        Log.e("", "### DetailModelImpl ==> fetchDetail");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ( listener != null ) {
                    listener.onFetched();
                }
            }
        }, 2000);
    }
}
