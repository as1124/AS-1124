package com.as1124.touch_input.softinput.way2;

import android.content.Context;
import android.content.res.Configuration;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.as1124.touch_input.R;
import com.as1124.touch_input.softinput.As1124InputMethodService;
import com.as1124.touch_input.softinput.As1124Keyboard;
import com.as1124.touch_input.softinput.ExKey;

import java.util.List;

/**
 * 通过扩展{@link ViewGroup}来实现输入法的键盘界面布局管理
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SoftInputKeyboardLayout extends ViewGroup implements KeyboardView.OnKeyboardActionListener {

    private static final String LOG_TAG = "SELF_KEYBOARD_VIEW";

    private As1124Keyboard mKeyboard;

    private KeyboardView.OnKeyboardActionListener mKeyboardListener;

    private InputMethodManager mInputManager;

    private boolean isPopupShow = false;

    private int oldOrientation = -1;

    /**
     * 按键View是否填充完成
     */
    private boolean inflateDone = false;

    public SoftInputKeyboardLayout(Context context) {
        super(context);
    }

    public SoftInputKeyboardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SoftInputKeyboardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        this.mInputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Step1: 针对 Keyboard 进行测绘并且把 inflate 的View添加进来
        // Step2: 测绘每个键（自定义组合View）

        As1124Keyboard keyboard = getKeyboard();
        int suggestedWidth = getMeasuredWidth();
        if (suggestedWidth < 100) {
            suggestedWidth = getKeyboard().getMinWidth();
        }
        setMeasuredDimension(suggestedWidth, getPaddingTop() + getPaddingBottom() + keyboard.getHeight());

        if (keyboard.isNeedResize()) {
            if (keyboard.getKeyboardType().startsWith("26")) {
                As1124Keyboard.adjust26Keys(keyboard, getMeasuredWidth(), getMeasuredHeight());
            } else {
                As1124Keyboard.adjust9Keys(keyboard, getMeasuredWidth(), getMeasuredHeight());
            }
            keyboard.setMinWidth(getMeasuredWidth());
            keyboard.setNeedResize(false);
        }

        if (!inflateDone) {
            initKeys();
            inflateDone = true;
            measureChildren(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void initKeys() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        List<Keyboard.Key> allKeys = getKeyboard().getKeys();
        float scaleBase = getResources().getDisplayMetrics().scaledDensity;
        for (int i = 0; i < allKeys.size(); i++) {
            ExKey oneKey = (ExKey) allKeys.get(i);
            SoftInputKeyLayout keyView = (SoftInputKeyLayout) inflater.inflate(R.layout.view_soft_key2, null);
            keyView.setKeyIndex(i);
            ((TextView) keyView.findViewById(R.id.text_key_major)).setText(oneKey.label);
            if (oneKey.backgroundResID != -1) {
                keyView.setBackground(getResources().getDrawable(oneKey.backgroundResID));
            }
            if (oneKey.icon != null) {
                keyView.showIcon(oneKey.icon);
            }
            if (oneKey.labelSize != -1) {
                // 获得的值是已经经过计算后的值，所以要还原
                float originalSize = getResources().getDimension(oneKey.labelSize);
                keyView.setMajorTextSize(originalSize / scaleBase);
            }
            this.addView(keyView, oneKey.width, oneKey.height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Step3: call layout on every child, which means i need to figure out the child's position
        List<Keyboard.Key> allKeys = getKeyboard().getKeys();
        for (int i = 0; i < getChildCount(); i++) {
            Keyboard.Key key = allKeys.get(i);
            getChildAt(i).layout(key.x, key.y, key.x + key.width, key.y + key.height);
        }
    }


    @Override
    public void onPress(int primaryCode) {
        // 所有按键按下都会触发 onPress
        Log.i(LOG_TAG, "onPress: " + primaryCode);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        // Modify 键按下会触发，普通键不触发
        Log.i(LOG_TAG, "onKey：" + keyCodes[0]);

        As1124InputMethodService inputService = ((As1124InputMethodService) getContext());
        switch (primaryCode) {
            case Keyboard.KEYCODE_SHIFT:
                getKeyboard().setShifted(!getKeyboard().isShifted());
                invalidate();
                break;
            case Keyboard.KEYCODE_DELETE:
                InputConnection connection = ((InputMethodService) getContext()).getCurrentInputConnection();
                if (connection != null) {
                    CharSequence selection = connection.getSelectedText(0);
                    if (selection != null) {
                        connection.deleteSurroundingText(selection.length(), 0);
                    } else {
                        connection.deleteSurroundingText(1, 0);
                    }
                }
                break;
            case Keyboard.KEYCODE_DONE:
                // 直接通过 InputMethodManager 来隐藏键盘不行, 因为windowToken
//            mInputManager.hideSoftInputFromWindow(getWindowToken(), 0);

                // Step1: 如果是 ACTION_DONE 则隐藏输入法
                // Step2: 分发对应的ACTION事件
                EditorInfo ei = ((InputMethodService) getContext()).getCurrentInputEditorInfo();
                InputConnection ic = ((InputMethodService) getContext()).getCurrentInputConnection();
                if (ei != null && ic != null) {
                    int actionMask = ei.imeOptions & EditorInfo.IME_MASK_ACTION;
                    if (actionMask == EditorInfo.IME_ACTION_NONE) {
                        ((InputMethodService) getContext()).requestHideSelf(0);
                    }
                    ic.performEditorAction(ei.actionId == 0 ? actionMask : ei.actionId);
                }
                break;
            case Keyboard.KEYCODE_MODE_CHANGE:
                // 切换键盘类型
                String type = ((As1124Keyboard) getKeyboard()).getKeyboardType();
                if ("26_en".equals(type)) {
                    setKeyboard(inputService.getKeyboard("9_num"));
                } else {
                    setKeyboard(inputService.getKeyboard("26_en"));
                }
                break;
            case 731:
                // 切换输入法
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    inputService.switchToNextInputMethod(false);
                } else {
                    String nextInputId = "";
                    List<InputMethodInfo> softInputs = mInputManager.getInputMethodList();
                    for (InputMethodInfo info : softInputs) {
                        if (!info.getPackageName().equals(getContext().getPackageName())) {
                            nextInputId = info.getId();
                            break;
                        }
                    }
                    ((InputMethodService) getContext()).switchInputMethod(nextInputId);
                }
                break;
            default:
                if (isPopupShow) {
                    onText(String.valueOf((char) primaryCode));
                    isPopupShow = false;
                }
                break;
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        Log.i(LOG_TAG, "onRelease: " + primaryCode);
    }

    @Override
    public void onText(CharSequence text) {
        InputConnection connection = ((InputMethodService) getContext()).getCurrentInputConnection();
        if (connection != null) {
            if (getKeyboard().isShifted()) {
                connection.commitText(text.toString().toUpperCase(), text.length() - 1);
            } else {
                connection.commitText(text, text.length() - 1);
            }
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

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation != oldOrientation && getKeyboard() != null) {
            getKeyboard().setNeedResize(true);
        }
        oldOrientation = newConfig.orientation;
    }


    public void setKeyboard(As1124Keyboard mKeyboard) {
        this.mKeyboard = mKeyboard;
    }

    public As1124Keyboard getKeyboard() {
        return mKeyboard;
    }

    public void setOnKeyboardActionListener(KeyboardView.OnKeyboardActionListener listener) {
        mKeyboardListener = listener;
    }
}
