package com.as1124.touch_input.softinput;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import com.as1124.touch_input.R;
import com.as1124.touch_input.softinput.way1.As1124Keyboard;
import com.as1124.touch_input.softinput.way1.As1124KeyboardView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124InputMethodService extends InputMethodService {

    private static final String LOG_TAG = "AS_INPUT_SERVICE";

    private Map<String, As1124Keyboard> supportedKeyboards = new HashMap<>();

    private As1124Keyboard currentKeyboard;

    /**
     * 输入法的ID
     */
    private InputMethodInfo mInputInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "--onCreate--");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        List<InputMethodInfo> softInputs = imm.getInputMethodList();
        for (InputMethodInfo info : softInputs) {
            if (info.getPackageName().equals(this.getPackageName())
                    && info.getServiceName().equals(this.getClass().getName())) {
                this.mInputInfo = info;
                break;
            }
        }

        As1124Keyboard enTypeKeyboard = new As1124Keyboard(getApplicationContext(), R.xml.keyboard_26_en_port);
        supportedKeyboards.put(enTypeKeyboard.getKeyboardType(), enTypeKeyboard);

        As1124Keyboard numKeyboard = new As1124Keyboard(getApplicationContext(), R.xml.keyboard_9_port);
        numKeyboard.setKeyboardType("9_num");
        supportedKeyboards.put(numKeyboard.getKeyboardType(), numKeyboard);

        currentKeyboard = enTypeKeyboard;
    }

    @Override
    public View onCreateInputView() {
        Log.i(LOG_TAG, "--onCreateInputView--");

        // 提供自定义键盘的输入界面
        return inputViewFromFramework();
    }

    private View inputViewFromFramework() {
        View view = getLayoutInflater().inflate(R.layout.view_soft_keyboard1, null);
        As1124KeyboardView keyboardView = view.findViewById(R.id.keyboard_view);
        keyboardView.setOnKeyboardActionListener(keyboardView);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setKeyboard(currentKeyboard);
        return view;
    }

    private View inputViewSelfDefined() {
        return null;
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

    @Override
    public void showWindow(boolean showInput) {
        Log.i(LOG_TAG, "--showWindow--");
        super.showWindow(showInput);
    }

    @Override
    public boolean onEvaluateInputViewShown() {
        Log.i(LOG_TAG, "--onEvaluateInputViewShown--");
        return super.onEvaluateInputViewShown();
    }

    public As1124Keyboard getKeyboard(String type) {
        return this.supportedKeyboards.get(type);
    }

    public InputMethodInfo getInputMethodInfo() {
        return this.mInputInfo;
    }
}
