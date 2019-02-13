package com.as1124.ch3.views.selfdefine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 继承已有控件
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SelfTextView extends TextView {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public SelfTextView(Context context) {
        super(context);
        initDraw();
    }

    public SelfTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDraw();
    }

    public SelfTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    private void initDraw() {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(1.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.drawLine(0, height / 2.0f, width, height / 2.0f, mPaint);
    }
}
