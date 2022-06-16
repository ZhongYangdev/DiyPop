package com.diypop.android;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int BG_ALPHA_TRANSITION = 300;//更多操作弹窗出现时背景过渡时间
    public static final float START_ALPHA = 1f;//渐变透明开始值
    public static final float END_ALPHA = 0.3f;//渐变透明结束值

    private Button mBtnShow;
    private TextView mTvResult;

    private DatePop mDatePop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*初始化控件*/
        initView();
        /*初始化事件*/
        initEvent();
    }

    private void initEvent() {
        /*显示按钮点击事件*/
        mBtnShow.setOnClickListener(v -> {
            //显示日期弹窗
            mDatePop.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            //背景变黑
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(START_ALPHA, END_ALPHA);
            valueAnimator.setDuration(BG_ALPHA_TRANSITION);
            valueAnimator.addUpdateListener(animation -> {
                //获取渐变梯度值，转换成浮点型
                float value = (float) animation.getAnimatedValue();
                //设置背景透明度
                updateBgAlpha(value, this);
            });
            valueAnimator.start();
        });
        /*弹窗关闭监听*/
        mDatePop.setOnDismissListener(() -> {
            //背景恢复
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(END_ALPHA, START_ALPHA);
            valueAnimator.setDuration(BG_ALPHA_TRANSITION);
            valueAnimator.addUpdateListener(animation -> {
                //获取渐变梯度值，转换成浮点型
                float value = (float) animation.getAnimatedValue();
                //设置背景透明度
                updateBgAlpha(value, this);
            });
            valueAnimator.start();
        });
        /*弹窗选中数据监听*/
        mDatePop.setOnDatePickerClickListener((startDate, endDate) -> {
            String result = "开始日期：" + startDate + "\n结束日期：" + endDate;
            mTvResult.setText(result);
        });
    }

    private void initView() {
        /*找控件*/
        mBtnShow = findViewById(R.id.btn_main_show);
        mTvResult = findViewById(R.id.tv_main_result);

        /*初始化日期选择弹窗*/
        mDatePop = new DatePop(this);
    }

    /**
     * 改变背景透明度方法
     */
    private static void updateBgAlpha(float alpha, Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        window.setAttributes(attributes);
    }
}