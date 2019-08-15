package com.as1124.touch_input.softinput.way1;

import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.inputmethod.InputConnection;

public class As1124KeyboardActionListener implements KeyboardView.OnKeyboardActionListener {

    private static final String LOG_TAG = "Key_Action_Listener";

    private InputConnection mConnection;

    public As1124KeyboardActionListener(InputConnection connection) {
        this.mConnection = connection;
    }

    @Override
    public void onPress(int primaryCode) {
        Log.i(LOG_TAG, "onPress: " + primaryCode);
    }

    @Override
    public void onRelease(int primaryCode) {
        Log.i(LOG_TAG, "onRelease: " + primaryCode);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.i(LOG_TAG, "onKey");
    }

    @Override
    public void onText(CharSequence text) {
        Log.i(LOG_TAG, "onText: " + text);
        if (this.mConnection != null) {
            this.mConnection.commitText(text, text.length() - 1);
        }

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
