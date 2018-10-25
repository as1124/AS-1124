package com.as1124.images.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class CustomerView extends View {

    private ShapeDrawable shapeDrawable;

    public CustomerView(Context context) {
        super(context);
        makeDrawable();
    }

    public CustomerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        makeDrawable();
    }

    private void makeDrawable() {
        shapeDrawable = new ShapeDrawable(new OvalShape());
        // if the color isn't set, the shape uses black as the default
        shapeDrawable.getPaint().setColor(0xff74ac23);
        // if the bounds aren't set, the shape can't be drawn
        shapeDrawable.setBounds(10, 10, 310, 110);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shapeDrawable.draw(canvas);
    }
}
