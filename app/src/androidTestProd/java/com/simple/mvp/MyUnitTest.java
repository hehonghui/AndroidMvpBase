package com.simple.mvp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MyUnitTest {
    @Test
    public void useAppContext() throws Exception {
        // ContextT of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.simple.mvpbase", appContext.getPackageName());
    }
}
