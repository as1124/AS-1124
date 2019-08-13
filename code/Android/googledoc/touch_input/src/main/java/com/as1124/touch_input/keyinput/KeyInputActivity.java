package com.as1124.touch_input.keyinput;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.as1124.touch_input.R;

/**
 * 焦点时间、触摸事件、软键盘显示
 * <pre>
 *     So this is the point:
 *     Do remember when you make a touch on the EditText, then you saw the {@link android.view.inputmethod.InputMethod}
 *     was shown, this event is not triggered by the focus-change-event but triggered by the
 *     <Strong>{@link #dispatchTouchEvent(MotionEvent)}</Strong>; that means if you consume the {@link MotionEvent} in
 *     the EditText#onTouchEvent, then the InputMethod will never shown!!
 *
 * </pre>
 * <Strong>
 * 按键事件处理：
 * <li>硬输入一定会触发{@link #dispatchKeyEvent(KeyEvent)}, {@link #onKeyUp(int, KeyEvent)},</li>
 * <li>Soft input may or may not triggered KeyEvent, it was depends on how the InputMethod
 * perform and dispatch the KeyEvent.</li>
 * </Strong>
 * <pre></pre>
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
        // Step 1: EditText must be focused if want to show SoftInput
        editText.requestFocus();

        // This way does not work, so means when you make a touch on EditText then you saw the SoftInput shown,
        // it was triggered by dispatchTouchEvent() not the dispatchWindowFocusChanged !!!
        // So this is the point!!
//        editText.setShowSoftInputOnFocus(true);
//        editText.getRootView().dispatchWindowFocusChanged(true);

        // Step 2: Another way is force to show SoftInput using InputMethodManager
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
//        inputManager.showInputMethodPicker();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        Log.i("FOCUS_TEST", "Activity#onWindowFocusChanged");
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("FOCUS_TEST", "Activity#dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // hard key input will triggered this method and be guaranteed, like
        // System-Back Button, Volume-Control button and the virtual navigation pan.
        // Soft input may or may not triggered this , it was depends on how the InputMethod
        // perform and dispatch the KeyEvent.

        Log.i("KeyEvent", "Activity#dispatchKey, KeyCode == " + event.getKeyCode());
        return super.dispatchKeyEvent(event);
    }
}
