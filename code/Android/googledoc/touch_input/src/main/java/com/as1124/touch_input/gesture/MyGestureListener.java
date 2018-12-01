package com.as1124.touch_input.gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 自定义手势检测器，这里处理单击、长按、滑动、双击、快速移动(onFling)
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MyGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "MyGestureListener";

    @Override
    public boolean onDown(MotionEvent event) {
        Log.i(DEBUG_TAG, "onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.i(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        // event1：触发ACTION_DOWN时即起始的MotionEvent信息
        // event2：达到触发onFling事件要求的最后一个ACTION_MOVE标识的MotionEvent信息
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒

        float deltaX = event1.getX() - event2.getX();
        if (deltaX > 0 && velocityX < 0) {
            // 向左快速滑动：结束位置X坐标小于触发位置X坐标，且X轴移动速率为负数
        }
        if (deltaX < 0 && velocityX > 0) {
            // 手指向右快速移动: 结束位置X轴坐标大于触发位置X轴坐标，沿X轴方向移动速率为正数
        }
        // 沿Y轴的移动判断同X轴
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.i(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        Log.i(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.i(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.i(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.i(DEBUG_TAG, "onDoubleTap: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.i(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.i(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        return true;
    }
}
