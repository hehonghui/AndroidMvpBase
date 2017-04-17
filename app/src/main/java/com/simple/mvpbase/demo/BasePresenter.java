package com.simple.mvpbase.demo;

import android.os.Handler;
import android.os.Looper;

import com.simple.mvp.MvpView;
import com.simple.mvp.Presenter;

import java.lang.reflect.Field;

/**
 * Created by mrsimple on 5/4/17.
 */

public class BasePresenter<V extends MvpView> extends Presenter<V> {

    @Override
    public void detach() {
        super.detach();
        releaseHandlers();
    }

    public void releaseHandlers() {
        try {
            Class<?> clazz = getClass();
            Field[] fields = clazz.getDeclaredFields();
            if (fields == null || fields.length <= 0) {
                return;
            }
            for (Field field : fields) {
                field.setAccessible(true);
                if (!Handler.class.isAssignableFrom(field.getType())) {
                    continue;
                }
                Handler handler = (Handler) field.get(this);
                if (handler != null && handler.getLooper() == Looper.getMainLooper()) {
                    handler.removeCallbacksAndMessages(null);
                }
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
