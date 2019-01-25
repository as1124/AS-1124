package com.as1124.ui.material;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 自定义的浮动按钮; 可以随手势移动和自动贴边
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124FloatingButton extends FloatingActionButton {

    private static String TAG = "As1124FloatingActionButton";

    public As1124FloatingButton(Context context) {
        super(context);
    }

    public As1124FloatingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

        Log.i(TAG, "Floating Button focus changed == " + gainFocus);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        Log.i(TAG, "Floating Button enabled state changed == " + enabled);
    }

    @Override
    public void setHovered(boolean hovered) {
        super.setHovered(hovered);

        Log.i(TAG, "Floating Button hovered state == " + hovered);
    }

    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        return super.dispatchHoverEvent(event);
    }

}
