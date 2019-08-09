package com.as1124.touch_input.inputmethod;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodSubtype;

import com.as1124.touch_input.R;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124InputMethodService extends InputMethodService {

    private static final String LOG_TAG = "AS_INPUT_SERVICE";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "--onCreate--");
    }

    @Override
    public View onCreateInputView() {
        Log.i(LOG_TAG, "--onCreateInputView--");

        // 提供自定义键盘的输入界面
        View view = getLayoutInflater().inflate(R.layout.view_keyboard, null);
        As1124KeyboardView keyboardView = view.findViewById(R.id.keyboard_view);
        keyboardView.setOnKeyboardActionListener(new As1124KeyboardActionListener());

        // ATTENTION 需要提供一个键盘
//        Keyboard myKeyboard = new As1124Keyboard(getApplicationContext(), R.xml.keyboard_26_port);
        // 能更好的控制键盘上每个键的样式, 可以解析自定义属性
        Keyboard keyboard = new As1124Keyboard(getApplicationContext(), R.xml.keyboard_9_port);
        keyboardView.setKeyboard(keyboard);
        return view;
    }

    @Override
    public boolean onEvaluateInputViewShown() {
        Log.i(LOG_TAG, "--onEvaluateInputViewShown--");

        // 用来决定当前状态下是否应该显示输入界面

        return super.onEvaluateInputViewShown();
    }

    @Override
    public void updateInputViewShown() {
        Log.i(LOG_TAG, "--updateInputViewShown--");

        super.updateInputViewShown();
    }

    @Override
    public View onCreateCandidatesView() {
        Log.i(LOG_TAG, "--onCreateCandidatesView--");

        // 提供输入时候选字、词的界面
        return super.onCreateCandidatesView();
    }

    @Override
    public View onCreateExtractTextView() {
        Log.i(LOG_TAG, "--onCreateExtractTextView--");

        // 只在全屏模式下才会出现
        return super.onCreateExtractTextView();
    }


    @Override
    public boolean onEvaluateFullscreenMode() {
        Log.i(LOG_TAG, "--onEvaluateFullscreenMode--");
        return super.onEvaluateFullscreenMode();
    }

    @Override
    public void updateFullscreenMode() {
        Log.i(LOG_TAG, "--updateFullscreenMode--");
        super.updateFullscreenMode();
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        Log.i(LOG_TAG, "--onStartInputView--");
        super.onStartInputView(info, restarting);
    }

    @Override
    protected void onCurrentInputMethodSubtypeChanged(InputMethodSubtype newSubtype) {
        Log.i(LOG_TAG, "--onCurrentInputMethodSubtypeChanged--");
        super.onCurrentInputMethodSubtypeChanged(newSubtype);
    }

    @Override
    public void onFinishInput() {
        Log.i(LOG_TAG, "--onFinishInput--");
        super.onFinishInput();
    }
}
