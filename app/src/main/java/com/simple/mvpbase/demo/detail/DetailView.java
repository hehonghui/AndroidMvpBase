package com.simple.mvpbase.demo.detail;

/**
 * Created by mrsimple on 27/12/16.
 */
public interface DetailView extends DetailView1, DetailView2 {
    void showNewsDetail();
    void showComments();
}
