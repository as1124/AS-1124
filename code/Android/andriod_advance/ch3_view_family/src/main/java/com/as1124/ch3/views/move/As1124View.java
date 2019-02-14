package com.as1124.ch3.views.move;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Scroller;

import com.as1124.ch3.views.R;

/**
 * FIXME: 没有改变布局参数的情况下, 当切换Spinner时界面所有状态重置
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124View extends View {

    // 记录Action发生时手指触摸的坐标
    private float lastX;
    private float lastY;

    private Scroller mScroller;

    public As1124View(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldLeft, int oldTop) {
        super.onScrollChanged(l, t, oldLeft, oldTop);
    }

    public void smoothScrollTo(int destY) {
        int currentY = mScroller.getCurrY();
        mScroller.startScroll(0, currentY, 0, destY, 2000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        // draw()方法会触发当前调用
        if (mScroller.computeScrollOffset()) {
            int offsetX = mScroller.getCurrX();// Scroller沿X轴滑动后的坐标值
            int offsetY = mScroller.getCurrY();// Scroller沿Y轴滑动后的坐标值
            ((View) getParent()).scrollTo(offsetX, offsetY);
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取手指触摸点的坐标, 值相对于当前控件
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
                // ATTENTION 不触发onDraw
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
            case 1:
                // 调用 offsetLeftAndRight
                // ATTENTION 不处罚onDraw
                offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);
                break;
            case 2:
                // 修改 LayoutParams
                // ATTENTION 布局参数发生变化, 触发onDraw
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                layoutParams.leftMargin = getLeft() + offsetX;
                layoutParams.topMargin = getTop() + offsetY;
                setLayoutParams(layoutParams);
                break;
            case 3:
                // ATTENTION 触发onDraw
                // 普通动画 Animation, 执行完成之后并不改变View 的位置状态（布局参数）
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate);
                startAnimation(animation);
                break;
            case 4:
                // 属性动画, 执行完成之后，View的布局信息也改变
                // ATTENTION 不触发onDraw
                ObjectAnimator.ofFloat(this, "translationX", 0, 350)
                        .setDuration(2000)
                        .start();
                break;
            case 5:
                // scrollTo
                int destX = (getScrollX() + offsetX);
                int destY = (getScrollY() + offsetY);

                // FIXME 那个是对的呢?
//                int destX = getLeft() + offsetX;
//                int destY = getTop() + offsetY;

                ((View) getParent()).scrollTo(-destX, -destY);
                break;
            case 6:
                // scrollBy: 滚动偏移量和坐标轴偏移量相反; 浏览网页时想看到下方内容，手势是向上话的，即X周偏移量为负数
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            default:
                // 调用layout 方法来移动到新的位置上
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
