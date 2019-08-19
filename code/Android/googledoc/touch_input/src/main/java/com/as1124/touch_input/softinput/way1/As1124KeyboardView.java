package com.as1124.touch_input.softinput.way1;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import com.as1124.touch_input.softinput.As1124InputMethodService;

import java.util.List;

/**
 * 提供输入法界面的View
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124KeyboardView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {

    private static final String LOG_TAG = "As1124KeyboardView";

    public As1124KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public As1124KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getKeyboard() == null) {
            setMeasuredDimension(getPaddingLeft() + getPaddingRight(), getPaddingTop() + getPaddingBottom());
        } else {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getKeyboard().getHeight() + getPaddingTop() + getPaddingBottom());
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        // super在这里处理了当一行的总宽度超过屏幕宽度的情况
        // 不采用框架的调整大小策略
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        As1124Keyboard keyboard = (As1124Keyboard) getKeyboard();
        if (keyboard.isNeedResize()) {
            if (keyboard.getKeyboardType().startsWith("26")) {
                adjust26Keys(keyboard, getMeasuredWidth(), getMeasuredHeight());
            } else {
                adjust9Keys(keyboard, getMeasuredWidth(), getMeasuredHeight());
            }
            keyboard.setMinWidth(getMeasuredWidth());
            keyboard.setNeedResize(false);
        }
    }

    private void adjust26Keys(As1124Keyboard keyboard, int w, int h) {
        List<ExRow> allRows = keyboard.keyRows;
        int rowNum = allRows.size();
        int normalKeyW = -1;
        for (int i = 0; i < rowNum; i++) {
            ExRow row = allRows.get(i);
            int keyNum = row.myKeys.size();
            int normalKeyNum = 0;

            int normalKeyTotal = 0;
            int modifyKeyTotal = 0;
            int gapTotal = 0;


            // Step1：先统计各类型按键数量及对应的宽度和
            for (int j = 0; j < keyNum; j++) {
                ExKey key = row.myKeys.get(j);
                if (j > 0) {
                    gapTotal += key.gap;
                }
                if (key.modifier) {
                    modifyKeyTotal += key.width;
                } else {
                    normalKeyTotal += key.width;
                    normalKeyNum++;
                }
            }

            // Step2：现有View宽度不够, 保留修饰键的宽度, 确定普通键的宽度
            if ((modifyKeyTotal + normalKeyTotal + gapTotal) > w) {
                if (i == 0) {
                    normalKeyW = (w - gapTotal - modifyKeyTotal) / normalKeyNum;
                }
            }

            // Step3：确定修饰键需要调整的宽度
            int differ = (normalKeyNum * normalKeyW + modifyKeyTotal + gapTotal - w);
            int cutoutPixel = 0;
            if (keyNum != normalKeyNum) {
                cutoutPixel = differ / (keyNum - normalKeyNum);
            }

            // Step4：调整居中显示时的起始offset
            int leftOffset = 0;
            if ((keyNum == normalKeyNum) && (normalKeyNum * normalKeyW + gapTotal) < w) {
                leftOffset = (w - normalKeyNum * normalKeyW - gapTotal) / 2;
            }

            // Step5：确定每个键值的坐标
            int x = leftOffset;
            for (int k = 0; k < keyNum; k++) {
                ExKey key = row.myKeys.get(k);
                if (key.modifier) {
                    key.width = key.width - cutoutPixel;
                } else {
                    key.width = normalKeyW;
                }
                key.x = x;
                x = x + key.width + key.gap;
            }
        }
    }

    private void adjust9Keys(As1124Keyboard keyboard, int w, int h) {
        // ATTENTION 后面再来调整
        List<ExRow> allRows = keyboard.keyRows;
        int rowNum = allRows.size();
        for (int i = 0; i < rowNum; i++) {
            ExRow row = allRows.get(i);
            int keyNum = row.myKeys.size();
            int gapTotal = row.myKeys.get(0).gap * (keyNum + 1);

            int keyW = (w - gapTotal) / keyNum;
            int leftOffset = row.myKeys.get(0).gap;
            for (int j = 0; j < keyNum; j++) {
                ExKey key = row.myKeys.get(j);
                key.x = leftOffset;
                key.width = keyW;
                leftOffset = leftOffset + key.width + key.gap;
            }
        }
    }


    @Override
    public void onClick(View v) {
        android.util.Log.i(LOG_TAG, "**onClick**");
        super.onClick(v);
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

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        As1124InputMethodService inputService = ((As1124InputMethodService) getContext());
        switch (primaryCode) {
            case Keyboard.KEYCODE_SHIFT:
                getKeyboard().setShifted(!getKeyboard().isShifted());
                invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DELETE:
                InputConnection connection = getCurrentInputConnection();
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
//            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getWindowToken(), 0);

                ((InputMethodService) getContext()).requestHideSelf(0);
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
//                    ((InputMethodService) getContext()).switchToPreviousInputMethod();
                    inputService.switchToNextInputMethod(false);
                } else {
                    String nextInputId = "";
                    List<InputMethodInfo> softInputs = imm.getInputMethodList();
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
                break;
        }
    }

    @Override
    public void onRelease(int primaryCode) {
        Log.i(LOG_TAG, "onRelease: " + primaryCode);
    }

    @Override
    protected boolean onLongPress(Keyboard.Key popupKey) {
        return super.onLongPress(popupKey);
    }

    @Override
    public void onText(CharSequence text) {
        InputConnection connection = getCurrentInputConnection();
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

    public InputConnection getCurrentInputConnection() {
        if (getContext() != null) {
            InputMethodService context = (InputMethodService) getContext();
            return context.getCurrentInputConnection();
        } else {
            return null;
        }
    }
}
