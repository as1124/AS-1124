package com.as1124.ch3.views.selfdefine;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 继承自原始ViewGroup，实现自定义布局容器
 * <ol>
 * <li>前置条件：当前布局器中所有控件均横向放置</li>
 * <li>处理WRAP_CONTENT</li>
 * <li>计算子View布局</li>
 * <li>处理滑动冲突：这里做的是横向布局，所以当前ViewGroup只支持横向滑动</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SelfViewGroup extends ViewGroup {

    private float offsetX = 0;
    private float offsetY = 0;

    public SelfViewGroup(Context context) {
        super(context);
    }

    public SelfViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // ATTENTION 极其重要, 不进行measure, 子View没有宽高值
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 所有控件均横向布局
        int childrenCount = getChildCount();
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            int width = 0;
            int maxHeight = 0;
            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                int childW = child.getMeasuredWidth();
                int childH = child.getMeasuredHeight();
                width += childW;
                if (childH > maxHeight) {
                    maxHeight = childH;
                }
            }
            setMeasuredDimension(width, maxHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            int width = 0;
            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                int childW = child.getMeasuredWidth();
                width += childW;
            }
            setMeasuredDimension(width, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            int maxHeight = 0;
            for (int i = 0; i < childrenCount; i++) {
                View child = getChildAt(i);
                int childH = child.getMeasuredHeight();
                if (childH > maxHeight) {
                    maxHeight = childH;
                }
            }
            setMeasuredDimension(widthSize, maxHeight);
        }

        if (getChildCount() <= 0) {
            // 如果没有子元素
            int w = widthSize;
            int h = heightSize;
            if (widthMode == MeasureSpec.AT_MOST) {
                w = 0;
            }
            if (heightMode == MeasureSpec.AT_MOST) {
                h = 0;
            }
            setMeasuredDimension(w, h);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int offsetX = 0;

        // 计算布局每一个子控件View, 而不是自身的两个锚点
        int childrenCount = getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            View view = getChildAt(i);
            // 不考虑处理 padding、margin
            if (view.getVisibility() == View.GONE) {
                view.layout(offsetX, 0, offsetX + 0, 0);
            } else {
                int childH = view.getMeasuredHeight();
                int childW = view.getMeasuredWidth();
                view.layout(offsetX, 0, offsetX + childW, childH);
                offsetX += childW;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // S1: 在Bounds范围内touch-event必然调用
        Log.i("SelfViewGroup", "dispatchTouchEvent called!");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // S2: 在Bounds范围内touch-event必然调用
        Log.i("SelfViewGroup", "onInterceptTouchEvent called!");

        // 拦截：如果横向滑动则不向子View进行分发
        boolean intercept = false;
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                // 只处理滑动
                float deltaX = x - offsetX;
                float deltaY = y - offsetY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    // 分发到自身的 onTouch() 中进行处理
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            default:
                break;
        }
        offsetX = x;
        offsetY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // S3: 如果event没有被子View消费（出发点在ViewGroup上而非子View上）是会调用
        Log.i("SelfViewGroup", "onTouchEvent called!");
        return super.onTouchEvent(event);
    }
}
