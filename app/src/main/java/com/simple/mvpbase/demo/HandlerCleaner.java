package com.simple.mvpbase.demo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by mrsimple on 17/4/17.
 */

public final class HandlerCleaner {

    private HandlerCleaner() {
    }

    public static void removeAllMessages(Object target) {
        try {
            Class<?> clazz = target.getClass();
            while (clazz != null && !clazz.equals(Object.class)) {
                Field[] fields = clazz.getDeclaredFields();
                if (fields == null || fields.length <= 0) {
                    return;
                }
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (!Handler.class.isAssignableFrom(field.getType())) {
                        continue;
                    }
                    Handler handler = (Handler) field.get(target);
                    if (handler != null && handler.getLooper() == Looper.getMainLooper()) {
                        handler.removeCallbacksAndMessages(null);
                        Log.e("", "### removeCallbacksAndMessages ") ;
                    }
                    field.setAccessible(false);
                }
                clazz = clazz.getSuperclass() ;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
