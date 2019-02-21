package com.as1124.touch_input.gesture.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * 自定义View演示DRAG操作
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class DragScaleView extends View {

    // The 'active pointer' is the one currently moving our object
    private int mActivePointerID = MotionEvent.INVALID_POINTER_ID;
    private float mLastTouchX;
    private float mLastTouchY;
    private float mPosX;
    private float mPosY;
    private ScaleGestureDetector mScaleDetector;
    private ScaleGestureDetector.OnScaleGestureListener mScaleListener = new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    };

    public DragScaleView(Context context) {
        super(context);
        initView();
    }

    public DragScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the S
        mScaleDetector.onTouchEvent(event);

        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                int pointerIndex = event.getActionIndex();
                // Remember where we started for dragging
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                // Save the ID of this pointer for dragging
                mActivePointerID = event.getPointerId(pointerIndex);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                // Find the index of the active pointer and fetch its position
                int pointerIndex = event.findPointerIndex(mActivePointerID);
                int deltaX = (int) (event.getX(pointerIndex) - mLastTouchX);
                int deltaY = (int) (event.getY(pointerIndex) - mLastTouchY);

//                mPosX += deltaX;
//                mPosY += deltaY;
                layout(getLeft() + deltaX, getTop() + deltaY, getRight() + deltaX, getBottom() + deltaY);

                invalidate();

                // Remember this touch position for the next move event
                mLastTouchX = event.getX();
                mLastTouchY = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActivePointerID = MotionEvent.INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_UP: {
                int pointerIndex = event.getActionIndex();
                int pointerID = event.getPointerId(pointerIndex);
                if (pointerID == mActivePointerID) {
                    // This was our active pointer going up. Choose a new active pointer and adjust accordingly.
                    int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = event.getX();
                    mLastTouchY = event.getY();
                    mActivePointerID = event.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }
}
