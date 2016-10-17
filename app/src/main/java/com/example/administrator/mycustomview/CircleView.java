package com.example.administrator.mycustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/8/17.
 */
public class CircleView extends View implements Runnable {
    Paint paint;
    private float radiu;
    private Context mContext;
    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getResources().getDisplayMetrics().widthPixels/2,getResources().getDisplayMetrics().heightPixels/2,radiu,paint);

    }

    @Override
    public void run() {
        while (true){
            try {
                if(radiu <= 200){
                    radiu += 20;
                    postInvalidate();
                }else{
                    radiu =0;
                }
                Thread.sleep(2000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
