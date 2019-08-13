package com.as1124.touch_input.inputmethod;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 提供输入法界面的View
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124KeyboardView extends KeyboardView {

    private static final String TAG = "As1124KeyboardView";

    public As1124KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public As1124KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        // super在这里处理了当一行的总宽度超过屏幕宽度的情况
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void onDraw(Canvas canvas) {
//        TextView v = new TextView(getContext());
//        v.setLayoutParams(new LinearLayout.LayoutParams(120, 60));
//        v.setText("ssssssHHH");
//        v.draw(canvas);
//
//        canvas.translate(-500, -500);
//
//        TextView v2 = new TextView(getContext());
//        v2.setHeight(60);
//        v2.setWidth(120);
//        v2.setLayoutParams(new LinearLayout.LayoutParams(120, 60));
//        v2.setTextColor(Color.WHITE);
//        v2.setText("HHHHSSS");
//        v2.draw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(new Rect(0, 0, 100, 100), paint);
        canvas.translate(150, 150);
        canvas.drawRect(new Rect(0, 0, 100, 100), paint);

//        super.onDraw(canvas);

//        // 好看的话，需要自己绘制键盘的每一个按键
//        Keyboard keyboard = getKeyboard();
//        if (keyboard == null) {
//            return;
//        }
//        List<Keyboard.Key> keys = keyboard.getKeys();
//        int keyCount = keys.size();
//        for (int i = 0; i < keyCount; i++) {
//            keys.get(i);
//        }
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
        android.util.Log.i(TAG, "**getKeyboard**");
        return super.getKeyboard();
    }

    @Override
    public void onClick(View v) {
        android.util.Log.i(TAG, "**onClick**");
        super.onClick(v);
    }
}
