package com.as1124.touch_input.inputmethod;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * 提供输入法界面的View
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124KeyboardView extends KeyboardView {

    public As1124KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public As1124KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 好看的话，需要自己绘制键盘的每一个按键
        Keyboard keyboard = getKeyboard();
        if (keyboard == null) {
            return;
        }
        List<Keyboard.Key> keys = keyboard.getKeys();
        int keyCount = keys.size();
        for (int i = 0; i < keyCount; i++) {
            keys.get(i);
        }
//
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setColor(Color.BLACK);
//        canvas.drawRect(0, 0, 50, 100, paint);
//        canvas.translate(60, 0);
//
//        paint.setColor(Color.YELLOW);
//        canvas.drawRect(0, 0, 80, 80, paint);
//        canvas.save();
    }

    @Override
    public Keyboard getKeyboard() {
        android.util.Log.i("KeyboardView", "**getKeyboard**");
        return super.getKeyboard();
    }

    @Override
    public void onClick(View v) {
        android.util.Log.i("KeyboardView", "**onClick**");
        super.onClick(v);
    }
}
