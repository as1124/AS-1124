package com.as1124.images.views;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * 自定义图形显示部件
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class CustomerDrawable extends Drawable {

    private Paint mRedPaint;

    public CustomerDrawable() {
        // Set up color and text size
        mRedPaint = new Paint();
        mRedPaint.setARGB(255, 255, 0, 0);
    }

    @Override
    public void draw(Canvas canvas) {
        // Get the drawable's bounds
        int width = getBounds().width();
        int height = getBounds().height();
        float radius = Math.min(width, height) / 2;

        // Draw a red circle in the center
        canvas.drawCircle(width / 2, height / 2, radius, mRedPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        // This method is required
        android.util.Log.i(getClass().getSimpleName(), "透明度==" + alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // This method is required

    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
