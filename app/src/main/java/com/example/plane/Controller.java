package com.example.plane;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Controller extends View {

    @SuppressLint("ClickableViewAccessibility")
    public Controller(Context context) {
        super(context);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int motionEventAction = motionEvent.getAction();
                switch (motionEventAction) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        /*if (isOnTouch == true)
                            if ((Math.pow((Xcontroler - Xposition), 2) + Math.pow((Ycontroler - Yposition), 2)) >= 2*Math.pow(radio, 2)) {
//                                Xcontroler = Xposition;//Xposition+Xcontroler/((Xcontroler - Xposition) * (Ycontroler - Yposition))*radio;
//                                Ycontroler = Yposition;//Yposition+Ycontroler/((Xcontroler - Xposition) * (Ycontroler - Yposition))*radio;
                                Xcontroler=radio*(int)(Xcontroler-Xposition)/(int)Math.sqrt((Math.pow((Xcontroler - Xposition), 2) + Math.pow((Ycontroler - Yposition), 2)))+Xposition;
                                Ycontroler=radio*(int)(Ycontroler-Yposition)/(int)Math.sqrt((Math.pow((Xcontroler - Xposition), 2) + Math.pow((Ycontroler - Yposition), 2)))+Yposition;
//                                isOnTouch = false;
                                return true;
                            } else {*/
                        Xcontroler = (int) motionEvent.getX();
                        Ycontroler = (int) motionEvent.getY();
//                            }
                        break;
                    case MotionEvent.ACTION_UP:
                        Xcontroler = Xposition;
                        Ycontroler = Yposition;
                        break;
                    default:
                }
                return true;
            }
        });
    }

    public Controller(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Controller(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int Xcontroler;
    private int Ycontroler;
    private int Xposition = 0;
    private int Yposition = 0;
    private final Paint paint = new Paint();
    private final Paint paint1 = new Paint();
    private int radio;
    private boolean isFirstMeasure = true;

    public int getXspeed() {
        return Xcontroler - Xposition;
    }

    public int getYspeed() {
        return Ycontroler - Yposition;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /* 将控制器越界判断移至onDraw() */
        if ((Math.pow((Xcontroler - Xposition), 2) + Math.pow((Ycontroler - Yposition), 2)) > Math.pow(radio, 2)) {
//                                Xcontroler = Xposition;//Xposition+Xcontroler/((Xcontroler - Xposition) * (Ycontroler - Yposition))*radio;
//                                Ycontroler = Yposition;//Yposition+Ycontroler/((Xcontroler - Xposition) * (Ycontroler - Yposition))*radio;
            Xcontroler = radio * (Xcontroler - Xposition) / (int) Math.sqrt((Math.pow((Xcontroler - Xposition), 2) + Math.pow((Ycontroler - Yposition), 2))) + Xposition;
            Ycontroler = radio * (Ycontroler - Yposition) / (int) Math.sqrt((Math.pow((Xcontroler - Xposition), 2) + Math.pow((Ycontroler - Yposition), 2))) + Yposition;
//                                isOnTouch = false;

        }

        canvas.drawCircle(Xposition, Yposition, radio, paint);
        canvas.drawCircle(Xcontroler, Ycontroler, getMeasuredWidth() / 12, paint1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isFirstMeasure) {
            Xposition = getMeasuredWidth() / 4;
            Yposition = getMeasuredHeight() / 7 * 6;
            Xcontroler = Xposition;
            Ycontroler = Yposition;
            radio = getMeasuredWidth() / 5;
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setColor(Color.GRAY);
            paint.setAlpha(100);
            paint1.setColor(Color.parseColor("#ec6d71"));
            paint1.setAntiAlias(true);
        }
    }
}
