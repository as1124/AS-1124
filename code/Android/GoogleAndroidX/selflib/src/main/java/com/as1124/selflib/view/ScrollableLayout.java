package com.as1124.selflib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 可滑动的布局器，支持固定一个Child在指定位置
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class ScrollableLayout extends LinearLayout {

    public ScrollableLayout(Context context) {
        super(context);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
