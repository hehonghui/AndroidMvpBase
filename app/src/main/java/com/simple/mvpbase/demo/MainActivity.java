package com.simple.mvpbase.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter mPresenter = new MainPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mPresenter.attach(this, this);
        mPresenter.fetchNews();
    }

    @Override
    public void showNews() {

    }
}
