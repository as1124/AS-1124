package com.as1124.ch3.views.round;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.as1124.ch3.views.R;

/**
 * 自定义圆形图片
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class SelfRoundView extends View {

    private int actionID = 0;

    private int controlW;

    private int controlH;

    public SelfRoundView(Context context) {
        super(context);
    }

    public SelfRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        controlW = w;
        controlH = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);

        switch (actionID) {
            case 0:
                // 填充整个控件的区域
                canvas.drawColor(Color.BLUE, PorterDuff.Mode.SRC);
                break;
            case 1:
                // 绘制相对坐标点
                canvas.drawPoints(new float[]{20, 20, 20, 160, 150, 270}, paint);
                break;
            case 2:
                // 线都是实心的
                canvas.drawLine(50, 50, 250, 400, paint);
                break;
            case 3:
                // 空心圆
                canvas.drawCircle(200, 200, 60, paint);
                break;
            case 4:
                drawArc(paint, canvas);
                break;
            case 5:
                // Canvas移动相当于移动画笔
                // 先操作画布再绘制，否则没有效果
                canvas.translate(110, 110);
                drawArc(paint, canvas);
                break;
            case 6:
                canvas.rotate(30, getWidth() / 2, getHeight() / 2);
                drawArc(paint, canvas);
                break;
            case 7:
                canvas.scale(0.8f, 0.8f);
                drawArc(paint, canvas);
                break;
            case 8:
                // 裁剪作为留下内容
//                canvas.clipRect(150, 150, 300, 300);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // 裁剪掉内容
                    canvas.clipOutRect(new Rect(150, 150, 300, 300));
                }
                drawArc(paint, canvas);
            default:
                break;
        }
    }

    private void drawArc(Paint paint, Canvas canvas) {
        RectF rectF = new RectF(100, 100, 800, 400);
        canvas.drawRect(rectF, paint);
        paint.setColor(Color.BLUE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawArc(rectF, 0, 90, true, paint);
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }

    public int getActionID() {
        return this.actionID;
    }
}
