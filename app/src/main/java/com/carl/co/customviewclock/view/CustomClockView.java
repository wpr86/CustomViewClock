package com.carl.co.customviewclock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Host-0 on 2017/3/13.
 */

public class CustomClockView extends View {
    // 定义画笔
    private Paint mPaint;
    // 用于获取文字的宽和高
    private Rect mBounds;

    private Canvas mCanvas;
    /* 时钟半径，不包括padding值 */
    private float mRadius;
    /* 时针角度 */
    private float mHourDegree;
    /* 分针角度 */
    private float mMinuteDegree;
    /* 秒针角度 */
    private float mSecondDegree;

    private String title = "这是个时钟";

    private Path path;

    private RectF mRectF;

    public CustomClockView(Context context) {
        this(context,null);
    }

    public CustomClockView(Context context, @Nullable AttributeSet attrs) {
        this(context,null,0);
    }

    public CustomClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mBounds = new Rect();
        path = new Path();
        mRectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w - getPaddingLeft() - getPaddingRight(),    // 初始化半径
                h - getPaddingTop() - getPaddingBottom()) / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        getTimeDegree();
        drawTimeText();
        drawTitle();
        drawTimeDegree();
        invalidate();
    }

    private void getTimeDegree()
    {
        Calendar calendar = Calendar.getInstance();
        float milliSecond = calendar.get(Calendar.MILLISECOND);
        float second = calendar.get(Calendar.SECOND) + milliSecond / 1000;
        float minute = calendar.get(Calendar.MINUTE) + second / 60;
        float hour = calendar.get(Calendar.HOUR) + minute / 60;
        mSecondDegree = second / 60 * 360;
        mMinuteDegree = minute / 60 * 360;
        mHourDegree = hour / 12 * 360;
    }

    private void drawTimeText()
    {
        mCanvas.save();
        mCanvas.translate(getWidth()/2, getHeight()/2);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(35);
        String time = "12";
        mPaint.getTextBounds(time, 0, time.length(), mBounds);
        mCanvas.drawText(time, -mBounds.width()/2, mBounds.height()/2 - mRadius, mPaint);
        time = "3";
        mPaint.getTextBounds(time, 0, time.length(), mBounds);
        mCanvas.drawText(time, mRadius-mBounds.width()/2, mBounds.height()/2, mPaint);
        time = "6";
        mPaint.getTextBounds(time, 0, time.length(), mBounds);
        mCanvas.drawText(time, -mBounds.width()/2, mRadius + mBounds.height()/2, mPaint);
        time = "9";
        mPaint.getTextBounds(time, 0, time.length(), mBounds);
        mCanvas.drawText(time, -mBounds.width()/2 - mRadius, mBounds.height()/2, mPaint);


        mRectF.set(-mRadius, -mRadius, mRadius, mRadius);
        mCanvas.drawArc(mRectF, 5, 80, false, mPaint);
        mCanvas.drawArc(mRectF, 95, 80, false, mPaint);
        mCanvas.drawArc(mRectF, 185, 80, false, mPaint);
        mCanvas.drawArc(mRectF, 275, 80, false, mPaint);

        mCanvas.restore();
    }

    private void drawTitle()
    {
        mCanvas.save();
        mCanvas.translate(getWidth()/2, getHeight()/2);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(35);
        mRectF.set(-getWidth()/4, -getWidth()/4, getWidth()/4, getWidth()/4);
        path.addArc(mRectF, -180, 180);
        mPaint.setTextAlign(Paint.Align.CENTER);
//		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
//		String title = df.format(new Date(System.currentTimeMillis()));
        mCanvas.drawTextOnPath(title, path, 0, 0, mPaint);
        mCanvas.restore();
    }

    private void drawTimeDegree()
    {
        mCanvas.save();
        mCanvas.translate(getWidth()/2, getHeight()/2);
        //绘制指针
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(4);
        mCanvas.drawCircle(0, 0, 20, mPaint);

        mPaint.setStrokeWidth(12);

        mCanvas.save();
        mPaint.setColor(Color.BLACK);
        mCanvas.rotate(mHourDegree,0f,0f);
        mCanvas.drawLine(0, -25, 0, 60-mRadius, mPaint);
        mCanvas.restore();
        mCanvas.save();
        mPaint.setColor(Color.MAGENTA);
        mCanvas.rotate(mMinuteDegree,0f,0f);
        mCanvas.drawLine(0, -25, 0, 40-mRadius, mPaint);
        mCanvas.restore();
        mCanvas.save();
        mPaint.setColor(Color.GREEN);
        mCanvas.rotate(mSecondDegree,0f,0f);
        mCanvas.drawLine(0, -25, 0, 20-mRadius, mPaint);
        mCanvas.restore();
    }
}
