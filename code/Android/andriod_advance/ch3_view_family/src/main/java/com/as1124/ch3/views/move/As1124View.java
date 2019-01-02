package com.as1124.ch3.views.move;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Scroller;

import com.as1124.ch3.views.R;

public class As1124View extends View {

    // 记录Action发生时手指触摸的坐标
    float lastX;
    float lastY;

    private Scroller mScroller;

    public As1124View(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldLeft, int oldTop) {
        super.onScrollChanged(l, t, oldLeft, oldTop);
    }

    public void smoothScrollTo(int destX) {
        int scrollX = mScroller.getCurrX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取手指触摸点的坐标
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算移动偏移量
                int offsetX = (int) (x - lastX);
                int offsetY = (int) (y - lastY);

                moveSelf(offsetX, offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    private void moveSelf(int offsetX, int offsetY) {
        switch (ViewMovementActivity.getScrollType()) {
            case 0:
                // 调用layout 方法来移动到新的位置上
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
            case 1:
                // 调用 offsetLeftAndRight
                offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);
                break;
            case 2:
                // 修改 LayoutParams
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                layoutParams.leftMargin = getLeft() + offsetX;
                layoutParams.topMargin = getTop() + offsetY;
                setLayoutParams(layoutParams);
                break;
            case 3:
                // 普通动画 Animation, 执行完成之后并不改变View 的位置状态（布局参数）
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate);
                startAnimation(animation);

                // 属性动画, 执行完成之后，View的布局信息也改变
//                ObjectAnimator.ofFloat(this, "translationX", 0, 300)
//                        .setDuration(2000)
//                        .start();

                // 第一种View点击事件出发点停留在原位置; 属性动画后View点击事件出发点在当前位置
                break;
            case 4:
                // scrollTo
                ((View) getParent()).scrollTo(-(getScrollX() + offsetX), -(getScrollY() + offsetY));
                break;
            case 5:
                // scrollBy: 滚动偏移量和坐标轴偏移量相反; 浏览网页时想看到下方内容，手势是向上话的，即X周偏移量为负数
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            default:
                // 调用layout 方法来移动到新的位置上
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
        }
    }
}
