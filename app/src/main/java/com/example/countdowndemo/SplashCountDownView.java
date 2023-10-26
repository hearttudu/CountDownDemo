package com.example.countdowndemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SplashCountDownView extends ConstraintLayout {
    /** 倒计时-天 tv */
    private TextView mDayCountTv;
    /** 倒计时-时 tv */
    private TextView mHourCountTv;
    /** 倒计时-分 tv */
    private TextView mMinuteCountTv;
    /** 倒计时-秒 tv */
    private TextView mSecondCountTv;
    /** 一天多少秒 */
    private static final int SEC_OF_ONE_DAY = 86400;
    /** 一小时多少秒 */
    private static final int SEC_OF_ONE_HOUR = 3600;
    /** 一分钟多少秒 */
    private static final int SEC_OF_ONE_MINUTE = 60;
    /** 一秒多少豪秒 */
    private static final int MILLS_OF_ONE_SECOND = 1000;
    /** 小时 mode */
    private static final int HOUR_MODE = 24;
    /** 分钟 mode */
    private static final int MINUTE_MODE = 60;
    /** 秒 mode */
    private static final int SECOND_MODE = 60;
    /** 翻页动画时长 */
    private static final int FLIP_ANIM_DURATION = 100;
    /** 兜底最大剩余天数 防止展现异常 */
    private static final int MAX_REMAIN_DAY = 99;

    public SplashCountDownView(Context context) {
        this(context, null);
    }

    public SplashCountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashCountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate(context);
    }

    /**
     * 初始化布局、控件
     *
     * @param context 上下文
     */
    private void initInflate(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.splash_count_down_view, this);
        mDayCountTv = rootView.findViewById(R.id.count_down_tv_day_count);
        mHourCountTv = rootView.findViewById(R.id.count_down_tv_hour_count);
        mMinuteCountTv = rootView.findViewById(R.id.count_down_tv_minute_count);
        mSecondCountTv = rootView.findViewById(R.id.count_down_tv_second_count);
    }

    /**
     * 更新倒计时UI
     *
     * @param endTime 剩余时间（单位：秒）
     */
    public void updateCountDownUI(long endTime) {

        long currentTime = System.currentTimeMillis() / MILLS_OF_ONE_SECOND;
        long remainTime = endTime - currentTime;
        if (remainTime <= 0) {
            // 异常兜底
            mDayCountTv.setText("0");
            mHourCountTv.setText("0");
            mMinuteCountTv.setText("0");
            mSecondCountTv.setText("0");
        } else {
            // 分别计算剩余的天、时、分、秒
            long remainDay = Math.min(remainTime / SEC_OF_ONE_DAY, MAX_REMAIN_DAY);
            long remainHour = remainTime / SEC_OF_ONE_HOUR % HOUR_MODE;
            long remainMinute = remainTime / SEC_OF_ONE_MINUTE % MINUTE_MODE;
            long remainSecond = remainTime % SECOND_MODE;
            // 设置倒计时
            mDayCountTv.setText(formatNumber(remainDay));
            mHourCountTv.setText(formatNumber(remainHour));
            mMinuteCountTv.setText(formatNumber(remainMinute));
            mSecondCountTv.setText(formatNumber(remainSecond));
            // 对秒做翻页动画
            doFlipAnim(mSecondCountTv);
        }
    }


    /**
     * 一位数字前面补0
     *
     * @param number 数字
     *
     * @return 补0后的字符串
     */
    private String formatNumber(long number) {
        return number >= 10 ? String.valueOf(number) : "0" + number;
    }

    /**
     * 做翻页动画
     *
     * @param view 控件
     */
    private void doFlipAnim(@Nullable View view) {
        if (view == null) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        int transY = view.getHeight() / 2;
        ObjectAnimator translateDisAppear = ObjectAnimator.ofFloat(view, "translationY", 0, transY);
        ObjectAnimator translateAppear = ObjectAnimator.ofFloat(view, "translationY", -transY, 0);
        animatorSet.setDuration(FLIP_ANIM_DURATION);
        animatorSet.play(translateAppear).after(translateDisAppear);
        animatorSet.start();
    }
}