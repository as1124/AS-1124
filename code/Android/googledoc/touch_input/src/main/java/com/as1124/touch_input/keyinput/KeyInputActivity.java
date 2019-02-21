package com.as1124.touch_input.keyinput;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.as1124.touch_input.R;

/**
 * 处理键盘输入事件
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class KeyInputActivity extends Activity {

    private As1124EditText editText;
    private AutoCompleteTextView autoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_input);

        editText = findViewById(R.id.text_keyboard_event);
        editText.setOnEditorActionListener((view, actionID, event) -> {
            if (actionID == EditorInfo.IME_ACTION_SEND) {
                return true;
            }
            return false;
        });

        autoText = findViewById(R.id.auto_text_keyboard);
        String[] strs = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strs);
        autoText.setAdapter(adapter);
        findViewById(R.id.but_show_keyboard).setOnClickListener(v -> showKeyboard());
    }

    private void showKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        inputManager.showInputMethodPicker();
    }
}
