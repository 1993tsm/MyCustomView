package com.example.administrator.mycustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/8/28.
 */
public class BrickView extends View{
    private Paint mFillPaint,mStrokePaint; // 填充园内图片的画笔以及 描边的画笔
    private BitmapShader bitmapShader;     // 图片着色器

    private float postX,postY;               //触摸点的x,y坐标


    public BrickView(Context context) {
        super(context);
    }

    public BrickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStrokePaint.setColor(0xFF000000);
        mStrokePaint.setStrokeWidth(5);
        mStrokePaint.setStyle(Paint.Style.STROKE);

        mFillPaint = new Paint();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.gou);
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT);
        mFillPaint.setShader(bitmapShader);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                postX = event.getX();
                postY = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.DKGRAY);


        canvas.drawCircle(postX, postY, 300, mFillPaint);
        canvas.drawCircle(postX,postY,300,mStrokePaint);




    }
}
