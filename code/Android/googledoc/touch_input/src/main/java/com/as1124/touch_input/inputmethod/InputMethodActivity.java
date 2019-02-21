package com.as1124.touch_input.inputmethod;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.touch_input.R;

/**
 * 自定义输入法主入口
 * <ul>
 * <li>{@link android.inputmethodservice.InputMethodService}</li>
 * <li>{@link android.inputmethodservice.KeyboardView}</li>
 * <li>{@link android.view.inputmethod.BaseInputConnection}</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class InputMethodActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_method);
    }
}
