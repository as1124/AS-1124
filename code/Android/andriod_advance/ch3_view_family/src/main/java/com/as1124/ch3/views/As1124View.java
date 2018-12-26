package com.as1124.ch3.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class As1124View extends View {

    // 记录Action发生时手指触摸的坐标
    float lastX;
    float lastY;

    public As1124View(Context context, AttributeSet attrs) {
        super(context, attrs);
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

                // 调用layout 方法来移动到新的位置上
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return true;
    }
}
