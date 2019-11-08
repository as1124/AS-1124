package com.as1124.selflib.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * 不自动聚焦的TextView
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class NotAutoFocusedText extends TextView {

    private InputMethodManager imm;

    public NotAutoFocusedText(Context context) {
        super(context);
    }

    public NotAutoFocusedText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        boolean hasFocus = hasWindowFocus();
        Object windowToken = getWindowToken();
        if (hasFocus && (windowToken != null)) {
            // This means the first rendering is finished, and the surface is totally showing up
            return super.requestFocus(direction, previouslyFocusedRect);
        } else {
            return false;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            if (imm == null) {
                imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            // when click KEY_ENTER, Android System will auto look up a control(if null is current) for a next
            // focus, so should make sure the following:
            // 1. close SoftInputMethod if is active
            // 2. notify to close keyboard if step-1 is match
            if (imm.isActive(this)) {
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
