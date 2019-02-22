package com.as1124.touch_input.inputmethod;

import android.inputmethodservice.KeyboardView;
import android.util.Log;

public class As1124KeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

    private static final String LOG_TAG = "Key_Action_listener";

    @Override
    public void onPress(int primaryCode) {
        Log.i(LOG_TAG, "onPress");
    }

    @Override
    public void onRelease(int primaryCode) {
        Log.i(LOG_TAG, "onRelease");
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.i(LOG_TAG, "onKey");
    }

    @Override
    public void onText(CharSequence text) {
        Log.i(LOG_TAG, "onText");
    }

    @Override
    public void swipeLeft() {
        Log.i(LOG_TAG, "swipeLeft");
    }

    @Override
    public void swipeRight() {
        Log.i(LOG_TAG, "swipeRight");
    }

    @Override
    public void swipeDown() {
        Log.i(LOG_TAG, "swipeDown");
    }

    @Override
    public void swipeUp() {
        Log.i(LOG_TAG, "swipeUp");
    }
}
