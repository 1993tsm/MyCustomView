package com.example.administrator.mycustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/10/19.
 */

public class IconView extends View {

    private Bitmap mBitmap;// 位图
    private TextPaint mPaint;// 绘制文本的画笔
    private String mStr;// 绘制的文本

    private float mTextSize;// 画笔的文本尺寸



    private enum Ratio{
        WIDTH,HEIGHT
    }

    public IconView(Context context) {
        super(context);
    }

    public IconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        calArgs(context);
        init(context);
    }

    private void init(Context context) {
        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        mStr = "标题";

        //初始化文字画笔
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG );
        mPaint.setColor(Color.LTGRAY);
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void calArgs(Context context) {
        //计算参数
        int screenW = getResources().getDisplayMetrics().widthPixels;
        mTextSize  = screenW *1/10F;
     }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getMeasuredSize(widthMeasureSpec,Ratio.WIDTH),getMeasuredSize(heightMeasureSpec,Ratio.HEIGHT));
    }

    /**
     * 获取测量后的尺寸
     * @param measureSpec    测量规格
     * @param ratio           宽高标识
     * @return    宽高测量值
     */
    private int getMeasuredSize(int measureSpec, Ratio ratio) {
        //临时变量
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);

        int size = MeasureSpec.getSize(measureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:                 // EXACTLY时直接复赋值
                result = size;

                break;
            default:
                if(ratio == Ratio.WIDTH){
                    float textSize = mPaint.measureText(mStr); // 计算 图标下面标题的 宽度  若 textSize >= bitmap自身宽度 则取 textSize为最后结果
                    result = ((int) (textSize >= mBitmap.getWidth() ? textSize : mBitmap.getWidth()))  + getPaddingLeft() + getPaddingRight();
                }else if(ratio == Ratio.HEIGHT){
                    //result = (int) ((mPaint.descent() - mPaint.ascent())*2 + mBitmap.getHeight() + getPaddingTop() + getPaddingBottom());
                    result = ((int) ((mPaint.descent() - mPaint.ascent()) * 2 + mBitmap.getHeight())) + getPaddingTop() + getPaddingBottom();
                }
                if(mode == MeasureSpec.AT_MOST){
                    result = Math.min(result,size);
                }

                break;
        }

        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(mBitmap,getWidth() - mBitmap.getWidth(),getHeight() - mBitmap.getHeight(),null);
//        canvas.drawText(mStr,getWidth()/2,getHeight() + mBitmap.getHeight()/2 - mPaint.ascent(),mPaint);

        canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmap.getWidth() / 2, getHeight() / 2 - mBitmap.getHeight() / 2, null);
        canvas.drawText(mStr, getWidth() / 2, mBitmap.getHeight() + getHeight() / 2 - mBitmap.getHeight() / 2 - mPaint.ascent(), mPaint);
    }
}
