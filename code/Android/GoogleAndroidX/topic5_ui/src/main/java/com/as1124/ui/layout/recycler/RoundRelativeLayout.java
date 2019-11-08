package com.as1124.ui.layout.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.as1124.ui.R;

/**
 * 支持圆角的布局容器
 *
 * @author
 */
public class RoundRelativeLayout extends RelativeLayout {

    private int[] radius = new int[]{0, 0, 0, 0};

    public RoundRelativeLayout(Context context) {
        super(context);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundRelativeLayout);
        radius[0] = typedArray.getDimensionPixelSize(R.styleable.RoundRelativeLayout_leftTopRadius, 0);
        radius[1] = typedArray.getDimensionPixelSize(R.styleable.RoundRelativeLayout_rightTopRadius, 0);
        radius[2] = typedArray.getDimensionPixelSize(R.styleable.RoundRelativeLayout_rightBottomRadius, 0);
        radius[3] = typedArray.getDimensionPixelSize(R.styleable.RoundRelativeLayout_leftBottomRadius, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();

        // 4个角的X,Y坐标
        float[] radiusArray = new float[]{radius[0], radius[0], radius[1], radius[1], radius[2], radius[2], radius[3], radius[3]};
        path.addRoundRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), radiusArray, Path.Direction.CW);
        canvas.clipPath(path);

        super.onDraw(canvas);
    }
}
