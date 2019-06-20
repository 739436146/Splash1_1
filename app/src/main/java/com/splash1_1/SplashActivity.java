package com.splash1_1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.imageView.MyImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    final String path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=15610" +
            "34434459&di=da7745154900e3532f170c0264ef3c32&imgtype=0&src=http%3A%2F%2Fhbimg.b0.u" +
            "paiyun.com%2Fdd0f2a1486e73d6fc7d10ce91816c8e43730714a50dd0-qgN5GZ_fw658";

//    final String path = "http://10.134.141.194:8080/img/bg2.jpg";

    private int skipTime = 6;

    private Runnable runnable = null;

    private Button skip = null;

    private Timer timer = new Timer(true);

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:{
                    skip.setText("点击跳过  "+skipTime);
                    break;
                }
                case 1:{
                    MainActivity.actionStart(SplashActivity.this,null);
                    SplashActivity.this.finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        skip = findViewById(R.id.skip);

        MyImageView myImageView = findViewById(R.id.splash);

        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Glide.with(this)
                .load(url)
                .into(myImageView);

        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                MainActivity.actionStart(SplashActivity.this,null);
                SplashActivity.this.finish();
            }
        },5000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                skipTime -- ;
                handler.sendEmptyMessage(0);
            }
        },1000,1000);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.actionStart(SplashActivity.this,null);
                SplashActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        handler.removeCallbacksAndMessages(null);
        timer.cancel();
        super.onDestroy();
    }
}
