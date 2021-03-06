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

public class Plane extends View {

    public Plane(Context context) {
        super(context);
    }

    public Plane(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Plane(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int Xhero = 0;
    private int Yhero = 0;
    private Bitmap bitmap0;
    private Bitmap bitmap1;
    private Matrix matrix = new Matrix();
    private Paint paint = new Paint();
    private int heroState = 0;
    public int Xlength = 0;
    public int Ylength = 0;
    private boolean isFristMeasure = true;

    public void setXhero(int Xhero) {
        if (Xhero > Xlength && Xhero < (getMeasuredWidth() - Xlength)) {
            this.Xhero = Xhero;
        }
    }

    public void setYhero(int Yhero) {
        if (Yhero > Ylength && Yhero < (getMeasuredHeight() - Ylength)) {
            this.Yhero = Yhero;
        }
    }

    public int getXhero() {
        return Xhero;
    }

    public int getYhero() {
        return Yhero;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.setTranslate(Xhero - bitmap0.getWidth() / 2, Yhero - bitmap0.getHeight() / 2);
        if (heroState >= 0 && heroState < 5) {
            canvas.drawBitmap(bitmap0, matrix, paint);
        } else {
            canvas.drawBitmap(bitmap1, matrix, paint);
        }
        heroState++;
        if (heroState == 10) {
            heroState = 0;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (isFristMeasure) {
            super.onSizeChanged(w, h, oldw, oldh);
            bitmap0 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_plane_state_1);
            bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_plane_state_2);
            Xhero = getMeasuredWidth() / 2;
            Yhero = getMeasuredHeight() / 2;
            Xlength = bitmap0.getWidth() / 2;
            Ylength = bitmap0.getHeight() / 2;
            isFristMeasure = false;
        }
    }
}
