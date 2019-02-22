package com.as1124.touch_input.inputmethod;

import android.view.View;
import android.view.inputmethod.BaseInputConnection;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124InputConnection extends BaseInputConnection {

    public As1124InputConnection(View targetView, boolean fullEditor) {
        super(targetView, fullEditor);
    }

}
