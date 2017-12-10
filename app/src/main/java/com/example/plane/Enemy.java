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

/**
 * Created by aaa on 2017/12/3.
 */

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
    private boolean isDestoryed = false;
    private Bitmap bitmap0;
    private Bitmap bitmap1;
    private Matrix matrix = new Matrix();
    private Paint paint = new Paint();
    private int time;
    public int XLength = 100;
    public int YLength = 100;
    private int MODE = 0;
    private int TIME;
    final private int DESTORY = 0x111;
    final private int LEFT = -1;
    final private int STRAIGHT = 0;
    final private int RIGHT = 1;
    final private int SIN = 2;
    final private int SIN1 = 3;
    final private int SIN2=4;
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

    public void setMode(int MODE) {
        this.MODE = MODE;
    }

    public void addXEnemy(int add){
        XEnemy+=add;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.setTranslate(XEnemy - XLength, YEnemy - YLength);
        canvas.drawBitmap(bitmap0, matrix, paint);
        YEnemy += 16;
        /*if (XEnemy > getMeasuredWidth()/2) {
            XEnemy -= 20;
        } else {
            XEnemy += 20;
        }*/
        if (XEnemy <= XLength) {
            MODE = RIGHT;
        } else if (XEnemy >= getMeasuredWidth() - XLength) {
            MODE = LEFT;
        }
        switch (MODE) {
            case STRAIGHT:
                YLength+=20;
                break;
            case LEFT:
                if (XEnemy <= XLength) {
                    MODE = RIGHT;
                }
                if(time<=400) {
                    XEnemy -= Math.abs(Math.sin(time))*getMeasuredWidth() / 100;
                }
                break;
            case RIGHT:
                if (XEnemy >= getMeasuredWidth() - XLength) {
                    MODE = LEFT;
                }
                if(time<=400) {
                    XEnemy += Math.abs(Math.sin(time))*getMeasuredWidth() / 100;
                }
                break;
            case SIN://cosx-2sin2x+sin4x
                if (XEnemy > XLength && XEnemy < getMeasuredWidth() - XLength) {
                    XEnemy += (Math.cos(time) -2*Math.cos(2*time)+Math.sin(4*time))* getMeasuredWidth() / 100;
                }
                break;
            case SIN1://sinx-2cos2x+sin4x
                if (XEnemy > XLength && XEnemy < getMeasuredWidth() - XLength) {
                    XEnemy +=( Math.sin(time)
                            - 2 * Math.cos(time*2)
                            + Math.sin(time*4))* getMeasuredWidth() / 100;
                }
                break;
            case SIN2://x*sinx
                if (XEnemy > XLength && XEnemy < getMeasuredWidth() - XLength) {
                    XEnemy += time*Math.sin(time) * getMeasuredWidth() / 100;
                }
                break;
            default:
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(isFirstMeasure) {
            bitmap0 = BitmapFactory.decodeResource(getResources(), R.drawable.enemy0);
            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bomb_1);
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
