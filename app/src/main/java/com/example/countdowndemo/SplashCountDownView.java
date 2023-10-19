package com.example.countdowndemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashCountDownView extends ConstraintLayout {

    private final Context mContext;
    private TextView mDayCountTv;
    private TextView mHourCountTv;
    private TextView mMinuteCountTv;
    private TextView mSecondCountTv;

    private static int SEC_OF_ONE_DAY = 86400;
    private static int SEC_OF_ONE_HOUR = 3600;
    private static int SEC_OF_ONE_MINUTE = 60;

    public SplashCountDownView(Context context) {
        this(context, null);
    }

    public SplashCountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initInflate(mContext);
    }

    private void initInflate(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.splash_count_down_view, this);
        mDayCountTv = rootView.findViewById(R.id.count_down_tv_day_count);
        mHourCountTv = rootView.findViewById(R.id.count_down_tv_hour_count);
        mMinuteCountTv = rootView.findViewById(R.id.count_down_tv_minute_count);
        mSecondCountTv = rootView.findViewById(R.id.count_down_tv_second_count);
    }

    public void updateCountDownUI(long endTime) {

        long currentTime = System.currentTimeMillis() / 1000;
        long remainTime = endTime - currentTime;
        if (remainTime <= 0) {
            mDayCountTv.setText(0);
            mHourCountTv.setText(0);
            mMinuteCountTv.setText(0);
            mSecondCountTv.setText(0);
        } else {
            long remainDay = remainTime / SEC_OF_ONE_DAY;
            long remainHour = remainTime / SEC_OF_ONE_HOUR % 24;
            long remainMinute = remainTime / SEC_OF_ONE_MINUTE % 60;
            long remainSecond = remainTime % 60;
            mDayCountTv.setText(String.valueOf(remainDay));
            mHourCountTv.setText(String.valueOf(remainHour));
            mMinuteCountTv.setText(String.valueOf(remainMinute));
            mSecondCountTv.setText(String.valueOf(remainSecond));
        }
    }
}