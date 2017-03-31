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

        // real context则是真实的Context, 不返回 Application context
        assertNull(presenter.getRealContext());
    }


    @Test
    public void testNullView() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        // 没有attach View 时默认返回动态代理创建的View
        assertNotNull(presenter.getView());

        // real view则为 null
        assertNull(presenter.getRealView());


        // attach null
        presenter.attach(null, null);
        // 没有attach View 时默认返回动态代理创建的View
        assertNotNull(presenter.getView());
        // real view则为 null
        assertNull(presenter.getRealView());
    }


    @Test
    public void testLifeCycle() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        // 获取一个Application的Context进行测试
        presenter.attach(InstrumentationRegistry.getContext(), new TestMockView());
        // attach之后 是 attached 状态
        assertTrue( presenter.isAttached() );
    }

    @Test
    public void testLifeCycleWithNoAttach() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        // 没有 attach 则不是 attached状态
        assertFalse( presenter.isAttached() );
    }


    // =================  start
    @Test
    public void testAfterDetach() throws Exception {
        MockPresenter presenter = new MockPresenter() ;
        MockView view = new TestMockView() ;

        presenter.attach(InstrumentationRegistry.getTargetContext(), view);
        // attach 则不是 attached状态
        assertTrue( presenter.isAttached() );

        assertEquals(InstrumentationRegistry.getTargetContext(), presenter.getContext());
        assertEquals(InstrumentationRegistry.getTargetContext(), presenter.getRealContext());

        assertEquals(view, presenter.getView());
        assertEquals(view, presenter.getRealView());

        // detach
        presenter.detach();

        // 没有 attach 则不是 attached状态
        assertFalse( presenter.isAttached() );

        // detach之后返回的也是 Application context
        assertNotNull( presenter.getContext());
        assertNull(presenter.getRealContext());

        assertNotNull(presenter.getView());
        assertNull(presenter.getRealView());

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
