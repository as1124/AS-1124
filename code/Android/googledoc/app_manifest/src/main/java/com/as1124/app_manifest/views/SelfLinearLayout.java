package com.as1124.app_manifest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import com.as1124.app_manifest.R;

/**
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class SelfLinearLayout extends LinearLayout {

    /**
     * 可下拉的最大移动距离
     */
    private static int MAX_PULLDOWN_OFFSET = 0;

    /**
     * 记录起始位置
     */
    private float startX = 0, startY = 0;

    private float lastX = 0, lastY = 0;

    private OverScroller mScroller;

    private TextView mInfoText;


    public SelfLinearLayout(Context context) {
        super(context);
    }

    public SelfLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mScroller = new OverScroller(context);
    }

    public SelfLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new OverScroller(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        MAX_PULLDOWN_OFFSET = (int) (getResources().getDisplayMetrics().density * 140);
        mInfoText = findViewById(R.id.text_pulldown_info);
        mScroller = new OverScroller(getContext(), new LinearInterpolator());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 隐藏第一个布局块
        setTop(t - MAX_PULLDOWN_OFFSET);
        super.onLayout(changed, l, t - MAX_PULLDOWN_OFFSET, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (super.onTouchEvent(event)) {
            // 说明事件被子View消费了
            return true;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                startY = event.getRawY();
                lastX = startX;
                lastY = startY;
            case MotionEvent.ACTION_MOVE:
                int direction = (int) (event.getRawY() - lastY);
                if (direction >= 0) {
                    // 向下滑动
                    onPullDown(event);
                } else {
                    // 向上滑动
                    onPullUp(event);
                }
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startX = 0;
                startY = 0;

                // 重置View的位置
                // Way1: 认定滑动最大距离后释放及View移动的距离就是设定的可移动最大距离, 所以重置只需反向移动最大距离即可
                // 造成问题：发现存在误差，重复多次之后View隐藏的更多了
//                this.scrollBy(0, MAX_PULLDOWN_OFFSET);

                // Way2: 通过获取View中记录的移动参数来还原对应的值，结果正确
                // 发现：getScrollY为实际移动值 并不等于设定的最大移动值，因为 onTouchEvent 调用时并不是没一个像素发生变化就调用
                // 因此也就带来误差，最后一次的距离差可能并没有移动
//                this.scrollBy(0, -getScrollY());

                mScroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY(), 800);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void onPullDown(MotionEvent event) {
        int yDelta = (int) (event.getRawY() - lastY);
        int scrolled = Math.abs(getScrollY());
        if ((scrolled + yDelta) > (MAX_PULLDOWN_OFFSET / 2)) {
            updateInfoText("释放刷新...");
        } else {
            updateInfoText("下拉刷新");
        }
        if ((scrolled + yDelta) < MAX_PULLDOWN_OFFSET) {
            this.scrollBy(0, -yDelta);
        } else {
            this.scrollBy(0, -(MAX_PULLDOWN_OFFSET - scrolled));
        }
    }

    protected void onPullUp(MotionEvent event) {

    }


    public void updateInfoText(String text) {
        if (mInfoText != null) {
            mInfoText.setText(text);
        }
    }
}
