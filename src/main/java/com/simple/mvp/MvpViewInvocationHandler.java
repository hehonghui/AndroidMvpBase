package com.simple.mvp;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * dynamic proxy handler, do nothing.
 * Created by mrsimple on 23/12/16.
 */
public class MvpViewInvocationHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.e("", "### MvpView InvocationHandler do nothing -> " + method.getName()) ;
        return null;
    }
}
