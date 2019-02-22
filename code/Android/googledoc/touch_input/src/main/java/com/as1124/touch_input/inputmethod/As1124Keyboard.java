package com.as1124.touch_input.inputmethod;

import android.content.Context;
import android.inputmethodservice.Keyboard;

public class As1124Keyboard extends Keyboard {

    public As1124Keyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public As1124Keyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public As1124Keyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }
}
