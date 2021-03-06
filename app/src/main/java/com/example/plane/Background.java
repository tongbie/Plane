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

public class Background extends View {

    private Bitmap bitmap;
    private final Matrix matrix = new Matrix();
    private final Paint paint = new Paint();
    private int Time = 0;
    private boolean isFirstMeasured = true;

    public Background(Context context) {
        super(context);
    }

    public Background(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Background(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.setTranslate(0, Time);
        canvas.drawBitmap(bitmap, matrix, paint);
        Time += getMeasuredHeight() / 360;
        if (Time >= 0) {
            Time = -bitmap.getHeight() / 2;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isFirstMeasured) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_night_sky);
            float scale = getScreenWidth(getContext()) / (float) bitmap.getWidth();
            matrix.postScale(scale, scale);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            Time = -bitmap.getHeight() + getMeasuredHeight();
            isFirstMeasured = false;
        }
    }

    private int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
