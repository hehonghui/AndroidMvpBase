package com.simple.mvpbase.demo.detail;

import android.app.AlertDialog;
import android.os.Handler;
import android.os.Looper;

import com.simple.mvp.Presenter;
import com.simple.mvpbase.demo.R;

/**
 * Created by mrsimple on 27/12/16.
 */
public class DetailPresenter extends Presenter<DetailView> {

    private Handler mHandler = new Handler(Looper.getMainLooper()) ;


    public void fetchNewsDetail() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onFetchDetail();
            }
        }, 3000);
    }


    private void onFetchDetail() {
        // todo: 判断Activity是否已经销毁, 避免crash.
        if ( isActivityAlive() ) {
            showInfoDialog(R.string.fetched_news_detail);
        }
        // 操作View则不需要 isActivityAlive 判断, 动态代理会构建一个空实现.
        getMvpView().showNewsDetail();
    }

    public void fetchCommentsNoCheck() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // todo : 弹出dialog, 如果页面退出后才执行则会crash
                showInfoDialog(R.string.fetched_news_comments);
                // 操作View
                getMvpView().showComments();
            }
        }, 2000);
    }


    private void showInfoDialog(int msg) {
        new AlertDialog.Builder(getContext()).setMessage(msg).show();
    }
}
