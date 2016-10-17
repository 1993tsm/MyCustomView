package com.example.administrator.mycustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/8/18.
 */
public class EraserView extends View{

    private int MIN_MOVE_DIS = 5;

    private int screenW,screenH;

    private Paint mPaint; // 橡皮擦的画笔
    private Path mPath;   // 橡皮擦的路径
    private Canvas mCanvas; // 绘制橡皮擦路径的画布

    private Bitmap fgBitmap,bgBitmap; // 前景与 背景

    float preX,preY;      //记录上一个触摸事件的xy坐标
    public EraserView(Context context) {
        super(context);
    }

    public EraserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getScreen();
        initPaint(context);
    }

    private void initPaint(Context context) {
        mPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG); // 设置画笔的抗锯齿和抗抖动
        mPaint.setARGB(125, 255, 0, 0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE); // 画笔风格为描边
        mPaint.setStrokeJoin(Paint.Join.ROUND);  // 设置路径结合处样式
        mPaint.setStrokeWidth(50); // 设置笔触宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        fgBitmap = Bitmap.createBitmap(screenW,screenH, Bitmap.Config.ARGB_8888); //前景图bitmap

        mCanvas = new Canvas(fgBitmap);   // 将其注入画布

        mCanvas.drawColor(0xFF808080);

        bgBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);

        bgBitmap = Bitmap.createScaledBitmap(bgBitmap,screenW,screenH,true);




    }

    private void getScreen() {
        screenW = getResources().getDisplayMetrics().widthPixels;
        screenH = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        canvas.drawBitmap(fgBitmap, 0, 0, null);

        mCanvas.drawPath(mPath,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当前事件 的xy坐标
        float x = event.getX();
        float y =  event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x,y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x-preX);
                float dy = Math.abs(y-preY);
                if(dx > MIN_MOVE_DIS || dy > MIN_MOVE_DIS){
                    mPath.quadTo(preX,preY,(x+preX)/2,(y+preY)/2);
                    preX = x;
                    preY = y;
                }

                break;

        }
        invalidate();
        return true;
    }
}
