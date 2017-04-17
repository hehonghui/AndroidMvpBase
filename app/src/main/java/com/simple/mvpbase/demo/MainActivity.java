package com.simple.mvpbase.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.simple.mvpbase.demo.detail.DetailActivity;

public class MainActivity extends AppCompatActivity {


    Handler mHandler = new MainHandler(this);
    Button mJumpBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJumpBtn = (Button) findViewById(R.id.jump_button) ;
        mJumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
            }
        });

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "set text on jump : " + mJumpBtn, Toast.LENGTH_LONG).show();
                mJumpBtn.setText("New Text On Jump");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mJumpBtn = null ;
    }

    private static class MainHandler extends NoLeakHandler {

        public MainHandler() {
        }

        public MainHandler(Object external) {
            super(external);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isTargetAlive()) {
                MainActivity activity = getTarget();
                activity.mJumpBtn.setText("Hello");
            }
        }
    }
}
