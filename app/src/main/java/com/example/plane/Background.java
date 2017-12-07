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
 * Created by aaa on 2017/11/26.
 */

public class Background extends View {

    private Bitmap bitmap;
    private Matrix matrix=new Matrix();
    private Paint paint=new Paint();
    private int Time=0;

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
        canvas.drawBitmap(bitmap,matrix,paint);
        Time+=getMeasuredHeight()/360;
        if(Time>=0){
            Time=-bitmap.getHeight()/2;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        Time=-bitmap.getHeight()+getMeasuredHeight();
    }
}
