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
import android.widget.Toast;

import java.util.Random;

public class Enemy extends View {

    public Enemy(Context context) {
        super(context);
    }

    public Enemy(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Enemy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int XEnemy;
    private int YEnemy;
    private Bitmap bitmap0;
    private Matrix matrix = new Matrix();
    private Paint paint = new Paint();
    private int time;
    public int XLength = 100;
    public int YLength = 100;
    private int MODE = 0;
    private boolean isFirstMeasure=true;

    public int getXEnemy() {
        return XEnemy;
    }

    public int getYEnemy() {
        return YEnemy;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.setTranslate(XEnemy - XLength, YEnemy - YLength);
        canvas.drawBitmap(bitmap0, matrix, paint);
        YEnemy += 16;
        if (XEnemy <= XLength) {
            MODE = 1;
        } else if (XEnemy >= getMeasuredWidth() - XLength) {
            MODE = -1;
        }
        switch (MODE) {
            case 0:
                YEnemy+=20;
                break;
            case -1:
                if (XEnemy <= XLength) {
                    MODE = 1;
                }
                if(time<=400) {
                    XEnemy -= Math.abs(Math.sin(time))*getMeasuredWidth() / 100;
                }
                break;
            case 1:
                if (XEnemy >= getMeasuredWidth() - XLength) {
                    MODE = -1;
                }
                if(time<=400) {
                    XEnemy += Math.abs(Math.sin(time))*getMeasuredWidth() / 100;
                }
                break;
            case 2://cosx-2sin2x+sin4x
                if (XEnemy > XLength && XEnemy < getMeasuredWidth() - XLength) {
                    XEnemy += (Math.cos(time) -2*Math.cos(2*time)+Math.sin(4*time))* getMeasuredWidth() / 100;
                }
                break;
            case 3://sinx-2cos2x+sin4x
                if (XEnemy > XLength && XEnemy < getMeasuredWidth() - XLength) {
                    XEnemy +=( Math.sin(time)
                            - 2 * Math.cos(time*2)
                            + Math.sin(time*4))* getMeasuredWidth() / 100;
                }
                break;
            case 4://x*sinx
                if (XEnemy > XLength && XEnemy < getMeasuredWidth() - XLength) {
                    XEnemy += time*Math.sin(time) * getMeasuredWidth() / 100;
                }
                break;
            default:
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(isFirstMeasure) {
            bitmap0 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_enemy);
            XLength = bitmap0.getWidth() / 2;
            YLength = bitmap0.getHeight() / 2;
            Random random = new Random();
            XEnemy = random.nextInt(getMeasuredWidth() - 2 * XLength) + XLength;
            YEnemy = -YLength;
            MODE = random.nextInt(5) - 1;
            isFirstMeasure=false;
        }
    }
}
