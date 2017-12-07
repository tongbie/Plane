package com.example.plane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aaa on 2017/11/28.
 */

public class Bullet extends View {

    public Bullet(Context context, int Xbullet, int Ybullet) {
        super(context);
        this.Xbullet = Xbullet;
        this.Ybullet = Ybullet;
    }

    public Bullet(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Bullet(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int Xbullet;
    private int Ybullet;
    private Bitmap bitmap;
    private Matrix matrix = new Matrix();
    private Paint paint = new Paint();

    public void setXbullet(int Xbullet) {
        this.Xbullet = Xbullet;
    }

    public void setYbullet(int Ybullet) {
        this.Ybullet = Ybullet;
    }

    public int getXbullet() {
        return Xbullet;
    }

    public int getYbullet() {
        return Ybullet;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.setTranslate(Xbullet-bitmap.getWidth()/2, Ybullet-bitmap.getHeight()/2);
        canvas.drawBitmap(bitmap, matrix, paint);
        Ybullet -= 54;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
    }
}