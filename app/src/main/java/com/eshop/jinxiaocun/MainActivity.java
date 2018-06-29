package com.eshop.jinxiaocun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eshop.jinxiaocun.utils.Config;

import supoin.jinxiaocun.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(){
            @Override
            public void run() {
            }
        }.start();

    }


}
