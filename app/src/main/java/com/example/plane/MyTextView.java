package com.example.plane;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by aaa on 2017/11/17.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    Matrix mGradientMatrix = new Matrix();//矩阵
    int mViewWidth = 100;
    LinearGradient mLinearGradient;//着色器
    int mTranslate = 0;
    Paint mPaint;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /* 绘制边框 */
        Paint rectPaint = new Paint();
        rectPaint.setColor(getCurrentTextColor());
        rectPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, 5, getMeasuredHeight(), mPaint);
        canvas.drawRect(0, 0, getMeasuredWidth(), 5, mPaint);
        canvas.drawRect(getMeasuredWidth() - 5, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        canvas.drawRect(0, getMeasuredHeight() - 5, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        /* 绘制渐变 */
        mTranslate += mViewWidth / 25;
        if (mTranslate >= 2 * mViewWidth) {
            mTranslate = -mViewWidth;
        }
        mGradientMatrix.setTranslate(mTranslate, 0);//矩阵平移
        mLinearGradient.setLocalMatrix(mGradientMatrix);
        postInvalidateDelayed(20);//延时刷新屏幕
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {//在此进行初始化操作
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();//获取绘制当前控件的画笔
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,//Shader渲染器
                new int[]{getCurrentTextColor(), 0xffffffff, getCurrentTextColor()},
                null, Shader.TileMode.CLAMP);
        /* 起始渐变坐标(x0,y0)，终止坐标(x1,y1)，颜色数组[]{起始色color0，渐变色(十六进制颜色代码带透明度)，终止色}，
         * positions[]与渐变的颜色相对应，取值是0-1的float类型，表示在每一个颜色在整条渐变线中的百分比位置
         * 与BitmapShader一样，用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法 */
        mPaint.setShader(mLinearGradient);
    }
}
