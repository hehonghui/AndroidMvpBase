package com.simple.mvpbase.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by mrsimple on 17/4/17.
 */

public class MvpApplication extends Application {

    public static Context sContext ;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this ;
        // debug mode
        NoLeakHandler.config(this, true);
    }
}
