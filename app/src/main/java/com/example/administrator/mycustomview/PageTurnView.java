package com.example.administrator.mycustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */
public class PageTurnView extends View{
    boolean isNextPage,isLastPage;

    private TextPaint mTextPaint;  // 文本画笔

    private Context mContext; // 上下文环境引用

    private List<Bitmap> mBitmaps;   //位图数据列表
    private int pageIndex;  // 当前显示 mBitmaps 数据的下标
    private int mViewWidth,mViewHeight; // 控件宽高


    private float mClipX; // 裁剪右端点坐标

    private float mAutoAreaLeft,mAutoAreaRight; // 控件左侧右侧吸附的区域

    private float mCurPointX; // 指尖 触碰时 的X的坐标值
    private float mMoveValid;   //移动事件 的有效距离


    //private Paint mTextPaint;
    public PageTurnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 设置位图数据
     * @param mBitmaps 位图数据列表
     */
    public synchronized void setBitmaps(List<Bitmap> mBitmaps){

        if(null == mBitmaps || mBitmaps.size() == 0)
            throw new IllegalArgumentException("no bitmap diaplay");


        if(mBitmaps.size() < 2)
            throw new IllegalArgumentException("fuck you and fuck to use imageview");

        this.mBitmaps = mBitmaps;
        invalidate();
    }


    /**
     * 默认显示
     *
     * @param
     *
     */
//    private void defaultDisplay(Canvas canvas) {
//        // 绘制底色
//        canvas.drawColor(Color.WHITE);
//
//        // 绘制标题文本
//        mTextPaint.setTextSize(mTextSizeLarger);
//        mTextPaint.setColor(Color.RED);
//        canvas.drawText("FBI WARNING", mViewWidth / 2, mViewHeight / 4, mTextPaint);
//
//        // 绘制提示文本
//        mTextPaint.setTextSize(mTextSizeNormal);
//        mTextPaint.setColor(Color.BLACK);
//        canvas.drawText("Please set data use setBitmaps method", mViewWidth / 2, mViewHeight / 3, mTextPaint);
//    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        //判断当前事件类型
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:             //触摸屏幕时
                //获取当前事件 坐标
                mCurPointX = event.getX();
                // 如果点击事件位于回滚区域

                if(mCurPointX < mAutoAreaLeft){
                    // 翻上一页
                    isNextPage = false;
                    pageIndex--;
                    mClipX = mCurPointX;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float SlideDis = mCurPointX - event.getX();
                if(Math.abs(SlideDis) > mMoveValid){
                    //获取触摸点的X坐标
                    mClipX = event.getX();
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                //判断是否需要滑动
                judgeSlideAuto();
                if(!isLastPage && isNextPage && mClipX <=0){
                    pageIndex++;
                    mClipX = mViewWidth;
                    invalidate();
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    //判断是否需要自动滑动 根据参数的当前值判断自动滑动
    private void judgeSlideAuto() {
        /**
         *  如果裁剪的右端点坐标在控件左端十分之一的区域内，那么我们直接让其自动滑到控件左端
         */
        if(mClipX < mAutoAreaLeft){
            while(mClipX > 0){
                mClipX--;
                invalidate();
            }

        }

        if(mClipX > mAutoAreaRight){
            while(mClipX < mViewWidth){
                mClipX++;
                invalidate();
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 获取控件宽高
        mViewWidth = w;
        mViewHeight = h;

        // 初始化位图数据
        initBitmaps();

        mClipX = mViewWidth;
        // 计算控件左侧和右侧的回滚区域
        mAutoAreaLeft = mViewWidth *1/5F;
        mAutoAreaRight = mViewWidth*4/5F;

        mMoveValid = mViewWidth *1/100F;
    }

    private void initBitmaps(){
        List<Bitmap> temp = new ArrayList<Bitmap>();
        for(int i = mBitmaps.size() -1;i>=0;i--){
            Bitmap bitmap = Bitmap.createScaledBitmap(mBitmaps.get(i),mViewWidth,mViewHeight,true);
            temp.add(bitmap);


        }
        mBitmaps = temp;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBitmaps(canvas);
    }

    private void drawBitmaps(Canvas canvas) {
        isLastPage = false;
        pageIndex = pageIndex < 0 ? 0:pageIndex;
        pageIndex = pageIndex > mBitmaps.size() ? mBitmaps.size():pageIndex;

        int start = mBitmaps.size() -2 -pageIndex;
        int end = mBitmaps.size() - pageIndex;

        if(start < 0){
            isLastPage = true;
            Toast.makeText(mContext,"最好一页",Toast.LENGTH_SHORT).show();
            start = 0;
            end = 1;
        }
        for(int i = start;i < end;i++){
            canvas.save();
            // 仅裁剪 最顶层的 画布区域  如果到了末页 则不进行裁剪
            if(!isLastPage && i == end -1){
                canvas.clipRect(0,0,mClipX,mViewHeight);
            }
            canvas.drawBitmap(mBitmaps.get(i),0,0,null);
            canvas.restore();

        }

    }
}
