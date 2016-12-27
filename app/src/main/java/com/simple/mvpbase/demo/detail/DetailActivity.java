package com.simple.mvpbase.demo.detail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.simple.mvpbase.demo.R;

/**
 * Created by mrsimple on 27/12/16.
 */

public class DetailActivity extends Activity implements DetailView {

    DetailPresenter mPresenter = new DetailPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPresenter.attach(this, this);
        mPresenter.fetchNewsDetail();

        findViewById(R.id.fetch_comment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.fetchCommentsNoCheck();
            }
        });
    }

    @Override
    public void showNewsDetail() {
        Toast.makeText(this, "show news detail ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComments() {
        Toast.makeText(this, "show comments ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
