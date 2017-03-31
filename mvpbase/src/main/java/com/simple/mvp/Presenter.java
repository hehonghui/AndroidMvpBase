package com.simple.mvp;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

import static com.simple.mvp.MvpViewHelper.createEmptyView;

/**
 * Mvp Presenter 抽象类. 通过 弱引用持有 Context 和 View对象, 避免产生内存泄露。
 *
 * todo: 注意, 如果Presenter有多个泛型类,那么 MvpView类型的泛型类要放在第一位.
 *
 * 用法:
 *
 * public class MainActivity extends Activity implements XxxView {
 *
 *      XxxPresenter mPresenter ;
 *
 *     onCreate() {
 *          mPresenter = new XxxPresenter();
 *          mPresenter.attach(this, this);
 *     }
 *
 *
 *     onDestroy() {
 *          mPresenter.detach();
 *     }
 * }
 *
 *
 * <p>
 * 当 Context (通常是指Activity)被销毁时如果客户端程序
 * 再调用Context, 那么直接返回 Application 的Context. 因此如果用户需要调用与Activity相关的UI操作(例如弹出Dialog)时,
 * 应该先调用 {@link #isAttached()} ()} 来判断Activity是否还存活.
 * </p>
 *
 * 当 View 对象销毁时如果用户再调用 View对象, 那么则会
 * 通过动态代理创建一个View对象 {@link #mNullView}, 这样保证 view对象不会为空.
 *
 * see : https://github.com/sockeqwe/mosby/blob/master/mvp-nullobject-presenter/src/main/java/com/hannesdorfmann/mosby3/mvp/MvpNullObjectBasePresenter.java
 *
 * @param <T> Presenter 对应的 MvpView类型
 */
public abstract class Presenter<T extends MvpView> {
    /**
     * application context
     */
    private static Context sAppContext;
    /**
     * context weak reference
     */
    private WeakReference<Context> mContextRef;
    /**
     * mvp view weak reference
     */
    private WeakReference<T> mViewRef;
    /**
     * Mvp View created by dynamic Proxy
     */
    private T mNullView;
    /**
     * init application context with reflection.
     */
    static {
        try {
            // 先通过 ActivityThread 来获取 Application Context
            Application application = (Application) Class.forName("android.app.ActivityThread").getMethod
                    ("currentApplication").invoke(null, (Object[]) null);
            if (application != null) {
                sAppContext = application;
            }
            if (sAppContext == null) {
                // 第二种方式初始化
                application = (Application) Class.forName("android.app.AppGlobals").getMethod
                        ("getInitialApplication").invoke(null, (Object[]) null);
                if (application != null) {
                    sAppContext = application;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * attach context & mvp view
     *
     * @param context
     * @param view
     */
    public void attach(Context context, T view) {
        mContextRef = new WeakReference<>(context);
        mViewRef = new WeakReference<>(view);
        if (sAppContext == null && context != null) {
            sAppContext = context.getApplicationContext();
        }
    }


    /**
     * release resource
     */
    public void detach() {
        if (mContextRef != null) {
            mContextRef.clear();
        }
        if (mViewRef != null) {
            mViewRef.clear();
        }
    }


    /**
     * 返回 Context. 如果 Activity被销毁, 那么返回应用的Context.
     * <p>
     * 注意:
     * 通过过Context进行UI方面的操作时应该调用 {@link #isAttached()}
     * 判断Activity是否还已经被销毁, 在Activity未销毁的状态下才能操作. 否则会引发crash.
     * 而获取资源等操作则可以使用应用的Context.
     *
     * @return
     */
    protected Context getContext() {
        Context context = mContextRef != null ? mContextRef.get() : null;
        if (context == null || !isAttached()) {
            context = sAppContext;
        }
        return context;
    }


    protected String getString(int rid) {
        Context context = getContext();
        if (context != null) {
            return context.getString(rid);
        }
        return "";
    }

    /**
     * UI展示相关的操作需要判断一下 Activity 是否已经 finish.
     * <p>
     * todo : 只有当 isActivityAlive 返回true时才可以执行与Activity相关的操作,
     * 比如 弹出Dialog、Window、跳转Activity等操作.
     *
     * @return
     */
    protected boolean isAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }


    /**
     * 返回 Mvp View对象. 如果真实的 View对象已经被销毁, 那么会通过动态代理构建一个View,
     * 确保调用 View对象执行操作时不会crash.
     *
     * @return Mvp View
     */
    protected T getView() {
        T view = mViewRef != null ? mViewRef.get() : null;
        if (view == null ) {
            // create null mvp view
            if (mNullView == null) {
                mNullView = createEmptyView(this);
            }
            view = mNullView;
        }
        return view;
    }
}
