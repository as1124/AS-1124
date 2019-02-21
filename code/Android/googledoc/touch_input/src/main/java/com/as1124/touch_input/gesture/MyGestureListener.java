package com.as1124.touch_input.gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 自定义手势检测器，这里处理单击、长按、滑动、双击、快速移动(onFling)
 * <ol>
 * <li>single tap</li>
 * <li>double tap</li>
 * <li>long press</li>
 * <li>scroll</li>
 * <li>fling</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MyGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String DEBUG_TAG = "MyGestureListener";

    @Override
    public boolean onDown(MotionEvent event) {
        // ATTENTION ACTION_DOWN 时触发，用于决定是否处理后续Motion事件
        Log.i(DEBUG_TAG, "onDown: ");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        // ACTION_DOWN 时触发, 不适合用来进行手势判断, 一般空处理
        Log.i(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.i(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        // event1：触发ACTION_DOWN时即起始的MotionEvent信息
        // event2：达到触发onFling事件要求的最后一个ACTION_MOVE标识的MotionEvent信息
        // velocityX：X轴上的移动速度，像素/秒
        // velocityY：Y轴上的移动速度，像素/秒

        float deltaX = event2.getX() - event1.getX();
        Log.i(DEBUG_TAG, "deltaX = " + deltaX + ", velocityX === " + velocityX);
        return true;
    }


    @Override
    public void onLongPress(MotionEvent event) {
        Log.i(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
        Log.i(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.i(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.i(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
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

}
