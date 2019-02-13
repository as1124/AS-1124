package com.as1124.ch3.views.selfdefine;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.as1124.ch3.views.R;

/**
 * 继承自原始{@link View}
 * <ol>
 * <li>绘制矩形</li>
 * <li>处理padding</li>
 * <li>处理WRAP_CONTENT</li>
 * <li>自定义属性</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SelfRectView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mColor = Color.YELLOW;

    public SelfRectView(Context context) {
        super(context);
        initDraw(null);
    }

    public SelfRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDraw(attrs);
    }

    public SelfRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw(attrs);
    }

    private void initDraw(AttributeSet attrs) {
        if (attrs != null) {
            // 从attrs中提取自定义属性的值
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SelfRectView);
            mColor = typedArray.getColor(R.styleable.SelfRectView_as1124, Color.YELLOW);
            typedArray.recycle();
        }

        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(1.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // remember to call setMeasuredDimension() to save measure result.

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // 方式1：MeasureSpec 处理
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(500, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 300);
        }

        // 方式2：直接修改布局参数; 效果一致
//        ViewGroup.LayoutParams params = this.getLayoutParams();
//        if (params.width == ViewGroup.LayoutParams.WRAP_CONTENT && params.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            params.width = 300;
//            params.height = 300;
//            setLayoutParams(params);
//        } else if (params.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            params.width = 500;
//            setLayoutParams(params);
//        } else if (params.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            params.height = 500;
//            setLayoutParams(params);
//        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // 处理 padding
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        canvas.drawRect(0 + paddingLeft, 0 + paddingTop, width - paddingRight, height - paddingBottom, mPaint);
    }
}
