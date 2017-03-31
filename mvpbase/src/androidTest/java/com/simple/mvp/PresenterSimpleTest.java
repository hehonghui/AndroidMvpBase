package com.simple.mvp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.simple.mvp.mock.MockPresenter;
import com.simple.mvp.mock.MockView;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PresenterSimpleTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertNotNull(appContext);
    }


    @Test
    public void testNullView() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        // 没有attach View 时默认返回动态代理创建的View
        assertNotNull(presenter.getView());
    }


    @Test
    public void testAppContext() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        // 没有attach context时默认返回Application的Context.
        assertNotNull(presenter.getContext());
    }


    @Test
    public void testNullAndAppContext() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        presenter.attach(null, new TestMockView());
        // context 为空时也时默认返回Application的Context.
        assertNotNull(presenter.getContext());
    }


    @Test
    public void testLifeCycle() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        // 获取一个Application的Context进行测试
        presenter.attach(InstrumentationRegistry.getContext(), new TestMockView());

        // application context , so it's not alive
        assertFalse( presenter.isActivityAlive() );
    }

    @Test
    public void testLifeCycleWithNoAttach() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        // application context , so it's not alive
        assertFalse( presenter.isActivityAlive() );
    }

    /**
     * TestMockView
     */
    public static class TestMockView implements MockView {
        @Override
        public void noOp() {

        }
    }
}
