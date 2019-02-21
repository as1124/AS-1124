package com.as1124.touch_input.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * 自定义View处理滑动事件；{@link android.widget.Scroller}, {@link OverScroller}
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SelfScrollableView extends LinearLayout {

    private static final String LOG_TAG = "SelfScrollableView";

    private OverScroller mScroller;

    public SelfScrollableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
    }

    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    public void smoothScrollBy(int dx, int dy) {
        // 设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);

        // 这里必须调用invalidate()重绘才能保证computeScroll()会被调用, 否则不一定能看到效果
        invalidate();
    }

    @Override
    public void computeScroll() {
        // 先判断mScroller 滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 这里调用View的scrollTo完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            // 因为是非UI线程，所以调用该方法实现重绘
            postInvalidate();
        }

        super.computeScroll();
    }
}
