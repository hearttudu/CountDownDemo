package com.example.countdowndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int MSG_WHAT_UPDATE_COUNTDOWN = 1;
    protected static final long COUNTDOWN_CHECK_PERIOD = 1000;

    private SplashCountDownView mSplashCountDownView;
    protected Handler mUiHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_UPDATE_COUNTDOWN:
                    updateCountdownUI();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSplashCountDownView = findViewById(R.id.splash_count_down_view);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUiHandler.sendEmptyMessageDelayed(MSG_WHAT_UPDATE_COUNTDOWN, COUNTDOWN_CHECK_PERIOD);
            }
        });
    }

    private void updateCountdownUI() {
        mSplashCountDownView.setVisibility(View.VISIBLE);
        mSplashCountDownView.updateCountDownUI(1999220701);
        mUiHandler.sendEmptyMessageDelayed(MSG_WHAT_UPDATE_COUNTDOWN, COUNTDOWN_CHECK_PERIOD);
    }
}