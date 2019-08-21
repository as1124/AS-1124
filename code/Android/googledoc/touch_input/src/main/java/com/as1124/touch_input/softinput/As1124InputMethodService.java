package com.as1124.touch_input.softinput;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;

import com.as1124.touch_input.R;
import com.as1124.touch_input.softinput.way1.As1124Keyboard;
import com.as1124.touch_input.softinput.way1.As1124KeyboardView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义输入法服务
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124InputMethodService extends InputMethodService {

    private static final String LOG_TAG = "AS_INPUT_SERVICE";

    private Map<String, As1124Keyboard> supportedKeyboards = new HashMap<>();

    private WeakReference<KeyboardView> inputViewRef;
    private WeakReference<View> candidateViewRef;

    /**
     * 输入法的ID
     */
    private InputMethodInfo mInputInfo;

    @Override
    public void onCreate() {
        super.onCreate();

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
    }

    @Override
    public View onCreateInputView() {
        Log.i(LOG_TAG, "--onCreateInputView--");

        // 提供自定义键盘的输入界面
        View inputRoot = inputViewFromFramework();
        return inputRoot;
    }

    /**
     * 利用系统输入法框架实现的键盘
     *
     * @return
     */
    private View inputViewFromFramework() {
        View view = getLayoutInflater().inflate(R.layout.view_soft_keyboard1, null);
        As1124KeyboardView keyboardView = view.findViewById(R.id.keyboard_view);
        inputViewRef = new WeakReference<>(keyboardView);
        keyboardView.setOnKeyboardActionListener(keyboardView);
        keyboardView.setPreviewEnabled(true);
        return view;
    }

    /**
     * 自定义View实现键盘布局和绘制
     *
     * @return
     */
    private View inputViewSelfDefined() {
        return null;
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        Log.i(LOG_TAG, "--onStartInputView--");
        int typeFlag = info.inputType & InputType.TYPE_MASK_CLASS;
        As1124Keyboard keyboard;
        switch (typeFlag) {
            case InputType.TYPE_CLASS_NUMBER:
            case InputType.TYPE_CLASS_PHONE:
                keyboard = getKeyboard("9_num");
                break;
            default:
                keyboard = getKeyboard("26_en");
        }
        inputViewRef.get().setKeyboard(keyboard);
        keyboard.getKeys().get(keyboard.getKeys().size() - 1).label = getTextForImeAction(info.imeOptions);
    }

    @Override
    public CharSequence getTextForImeAction(int imeOptions) {
        return super.getTextForImeAction(imeOptions);
    }


    @Override
    public View onCreateCandidatesView() {
        Log.i(LOG_TAG, "--onCreateCandidatesView--");
//        if (candidateViewRef != null) {
//            return candidateViewRef.get();
//        } else {
//            return super.onCreateCandidatesView();
//        }
        return getLayoutInflater().inflate(R.layout.view_input_candidate, null);
    }

    @Override
    public void onStartCandidatesView(EditorInfo info, boolean restarting) {
        Log.i(LOG_TAG, "--onStartCandidatesView--");
        super.onStartCandidatesView(info, restarting);
    }

    @Override
    public void setCandidatesViewShown(boolean shown) {
        Log.i(LOG_TAG, "--setCandidatesViewShown--");
        super.setCandidatesViewShown(shown);
    }


    @Override
    protected void onCurrentInputMethodSubtypeChanged(InputMethodSubtype newSubtype) {
        Log.i(LOG_TAG, "--onCurrentInputMethodSubtypeChanged--");
        super.onCurrentInputMethodSubtypeChanged(newSubtype);
    }


    public As1124Keyboard getKeyboard(String type) {
        return this.supportedKeyboards.get(type);
    }

    public InputMethodInfo getInputMethodInfo() {
        return this.mInputInfo;
    }
}
