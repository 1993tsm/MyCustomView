package com.example.administrator.mycustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/10/20.
 */

public class CustomLayout extends ViewGroup{
    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int parentPaddingTop = getPaddingTop();
        int parentPaddingLeft = getPaddingLeft();
        // 如果有子元素 则对子元素遍历 布局
        if(getChildCount() > 0){
            int mutiHeiht = 0; // 申明高度 存储 高度倍增值
            for(int i = 0;i<getChildCount();i++){
                View child = getChildAt(i);
//                child.layout(0,mutiHeiht,child.getMeasuredWidth(),child.getMeasuredHeight() + mutiHeiht);
                CustomLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
                child.layout(parentPaddingLeft+params.leftMargin,mutiHeiht +parentPaddingTop+params.topMargin,parentPaddingLeft + child.getMeasuredWidth()+params.leftMargin,parentPaddingTop+mutiHeiht+child.getMeasuredHeight()+params.topMargin);


                mutiHeiht += child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 申明 父容器的 期望值
        int parentDesireWidth = 0;
        int parentDesireHeight = 0;
        //如果有子元素
        if(getChildCount() > 0){
//            //则对子元素进行测量
//            measureChildren(widthMeasureSpec, heightMeasureSpec);
            for (int i = 0;i<getChildCount();i++){
                View child = getChildAt(i);
                CustomLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
                measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,0);
                //考虑到 子元素的 外边距
                parentDesireWidth += child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                parentDesireHeight += child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            }

            //考虑到父容器的 外边距
            parentDesireWidth += getPaddingLeft()+getPaddingRight();
            parentDesireHeight += getPaddingBottom() + getPaddingTop();

            //  将 父容器的期望值  与建议值比较
            parentDesireWidth = Math.max(parentDesireWidth,getSuggestedMinimumWidth());
            parentDesireHeight = Math.max(parentDesireHeight,getSuggestedMinimumHeight());



        }
        //设置最终测量值
        setMeasuredDimension(resolveSize(parentDesireWidth,widthMeasureSpec),resolveSize(parentDesireHeight,heightMeasureSpec));
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);


    }

    public static class CustomLayoutParams extends MarginLayoutParams{

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }

        public CustomLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(LayoutParams source) {
            super(source);
        }
    }

    /**
     * 生成布局参数
     * @return
     */
    @Override
    protected CustomLayoutParams generateDefaultLayoutParams() {
        return new CustomLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    /**
     * 生成布局参数
     * @param attrs   配置属性  生成布局参数
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }
}
