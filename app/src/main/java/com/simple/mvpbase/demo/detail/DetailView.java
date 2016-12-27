package com.simple.mvpbase.demo.detail;

import com.simple.mvp.MvpView;

/**
 * Created by mrsimple on 27/12/16.
 */
public interface DetailView extends MvpView {
    void showNewsDetail();
    void showComments();
}
