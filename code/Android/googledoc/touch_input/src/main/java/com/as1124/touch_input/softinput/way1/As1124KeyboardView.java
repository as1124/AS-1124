package com.as1124.touch_input.softinput.way1;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

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
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        // super在这里处理了当一行的总宽度超过屏幕宽度的情况
        // 不采用框架的调整大小策略
        Keyboard keyboard = getKeyboard();
        if (keyboard != null && (keyboard instanceof As1124Keyboard)) {
            if (((As1124Keyboard) keyboard).getKeyboardType().startsWith("26")) {
                adjust26Keys((As1124Keyboard) keyboard, w, h);
            } else {
                adjust9Keys((As1124Keyboard) keyboard, w, h);
            }
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
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public Keyboard getKeyboard() {
        android.util.Log.i(LOG_TAG, "**getKeyboard**");
        return super.getKeyboard();
    }

    @Override
    public void onClick(View v) {
        android.util.Log.i(LOG_TAG, "**onClick**");
        super.onClick(v);
    }

    @Override
    public void onPress(int primaryCode) {
        Log.i(LOG_TAG, "onPress: " + primaryCode);
    }

    @Override
    public void onRelease(int primaryCode) {
        Log.i(LOG_TAG, "onRelease: " + primaryCode);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.i(LOG_TAG, "onKey");
    }

    @Override
    public void onText(CharSequence text) {
        Log.i(LOG_TAG, "onText: " + text);
        if (this.mConnection != null) {
            this.mConnection.commitText(text, text.length() - 1);
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
}
