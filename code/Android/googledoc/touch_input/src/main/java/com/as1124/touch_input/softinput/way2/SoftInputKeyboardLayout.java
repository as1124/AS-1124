package com.as1124.touch_input.softinput.way2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 通过扩展{@link ViewGroup}来实现输入法的键盘界面布局管理
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SoftInputKeyboardLayout extends ViewGroup {

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
