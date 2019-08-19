package com.as1124.touch_input.softinput;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import com.as1124.touch_input.R;

/**
 * 自定义输入法主入口
 * <ul>
 * <li>{@link android.inputmethodservice.InputMethodService}</li>
 * <li>{@link android.inputmethodservice.KeyboardView}</li>
 * <li>{@link android.view.inputmethod.BaseInputConnection}</li>
 * </ul>
 * <strong>输入法键盘的实现有两种方式：</strong>
 * <ol>
 * <li>利用系统提供的框架{@link android.inputmethodservice.KeyboardView}, 将所有键通过Canvas绘图画上去；
 * 涉及到{@link android.inputmethodservice.Keyboard.Row}、{@link android.inputmethodservice.Keyboard.Key}</li>
 * <li>自定义<code>ViewGroup</code>, 将每个键作为单独View布局到上面</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class InputMethodActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_method);

        findViewById(R.id.but_setting_keyboard).setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            startActivity(intent);
        });
        findViewById(R.id.but_switch_input).setOnClickListener(v -> {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showInputMethodPicker();
        });
        findViewById(R.id.but_switch_subtype).setOnClickListener(v->{
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showInputMethodAndSubtypeEnabler("");
        });
    }
}
