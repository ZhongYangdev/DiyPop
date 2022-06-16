package com.diypop.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.gzuliyujiang.wheelpicker.contract.OnDateSelectedListener;
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @创建时间 2022/6/16 9:03
 * @描述 自定义日期选择弹窗
 */
public class DatePop extends PopupWindow {

    private static final String TAG = "DatePop";

    private TextView mTvCancel;
    private TextView mTvDetermine;
    private TextView mTvStart;
    private TextView mTvEnd;
    private DateWheelLayout mViewPicker;

    private OnDatePickerClickListener mOnDatePickerClickListener;

    public DatePop(Context context) {
        /*设置宽高*/
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /*设置布局内容*/
        View view = LayoutInflater.from(context).inflate(R.layout.pop_date, null);
        setContentView(view);
        /*设置透明背景*/
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        /*设置点击外部消失，在此之前要先设置透明背景*/
        setOutsideTouchable(false);
        /*可获得焦点*/
        setFocusable(true);

        /*初始化控件*/
        initView(view);
        /*初始化事件*/
        initEvent();
    }

    private void initEvent() {
        /*取消点击事件（关闭弹窗）*/
        mTvCancel.setOnClickListener(v -> dismiss());
        /*开始时间点击事件*/
        mTvStart.setOnClickListener(v -> updTextViewState(true));
        /*结束日期点击事件*/
        mTvEnd.setOnClickListener(v -> updTextViewState(false));
        /*日期滚轮监听*/
        mViewPicker.setOnDateSelectedListener((year, month, day) -> {
            //对选择日期格式进行处理
            String fMonth = month < 10 ? "0" + month : String.valueOf(month);
            String fDay = day < 10 ? "0" + day : String.valueOf(day);
            //拼接结果
            String result = year + "-" + fMonth + "-" + fDay;
            Log.d(TAG, "选择日期 ==> " + result);
            //判断当前获得焦点的控件，将结果设置上去
            if (mTvStart.isSelected()) {
                mTvStart.setText(result);
            } else if (mTvEnd.isSelected()) {
                mTvEnd.setText(result);
            }
        });
        /*确定按钮点击事件*/
        mTvDetermine.setOnClickListener(v -> {
            //获取开始日期、结束日期内容 将结果通过接口通知出去
            if (mOnDatePickerClickListener != null) {
                mOnDatePickerClickListener.onSelected(mTvStart.getText().toString(), mTvEnd.getText().toString());
            }
            //调用弹窗关闭方法
            dismiss();
        });
    }

    private void initView(View view) {
        /*找控件*/
        mTvCancel = view.findViewById(R.id.tv_date_cancel);
        mTvDetermine = view.findViewById(R.id.tv_date_determine);
        mTvStart = view.findViewById(R.id.tv_date_start);
        mTvEnd = view.findViewById(R.id.tv_date_end);
        mViewPicker = view.findViewById(R.id.view_date_picker);
        /*设置开始日期初始状态*/
        initStartDate();
    }

    private void initStartDate() {
        //设置开始日期为选中状态
        updTextViewState(true);
        //滚轮控件默认显示今天日期，所以直接获取当前日期，设置到开始日期上
        String curDate = getCurrentDate();
        mTvStart.setText(curDate);
    }

    /**
     * 获取当前日期
     */
    private String getCurrentDate() {
        //构建日期
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        //格式化日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return df.format(date);
    }

    /**
     * 更新文本控件选中状态
     *
     * @param isStart 是否为开始日期
     */
    private void updTextViewState(boolean isStart) {
        if (isStart) {
            mTvStart.setSelected(true);//开始日期选中
            mTvEnd.setSelected(false);//结束日期不选中
        } else {
            mTvEnd.setSelected(true);//结束日期选中
            mTvStart.setSelected(false);//开始日期不选中
        }
    }

    /**
     * 定义日期弹窗选中数据监听接口
     */
    public interface OnDatePickerClickListener {

        void onSelected(String startDate, String endDate);
    }

    /**
     * 设置日期弹窗选中数据监听方法
     */
    public void setOnDatePickerClickListener(OnDatePickerClickListener listener) {
        mOnDatePickerClickListener = listener;
    }
}
