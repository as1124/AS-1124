package com.as1124.selflib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.as1124.selflib.R;

/**
 * 自定义支持圆形的图像控件
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class RoundImageView extends ImageView {

    /**
     * 是否作为圆形显示
     */
    private boolean mIsRound = false;

    /**
     * 实现方法
     */
    private String mRoundType = "clipCanvas";

    private int mEffect;

    public RoundImageView(Context context) {
        super(context);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray styleAttrs = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mIsRound = styleAttrs.getBoolean(R.styleable.RoundImageView_isRound, false);
        mRoundType = styleAttrs.getString(R.styleable.RoundImageView_roundMethod);

        styleAttrs.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null && mIsRound) {
            int centerX = getMeasuredWidth() / 2;
            int centerY = getMeasuredHeight() / 2;
            int radius = Math.max(centerX, centerY);
            switch (mRoundType) {
                case "clipCanvas":
                    Path path = new Path();
                    path.addCircle(centerX, centerY, radius, Path.Direction.CCW);
                    canvas.clipPath(path);
                    super.onDraw(canvas);
                    break;
                case "clipImage":
                    clipImage(null);
                    break;
                case "Xfermode":
                    break;
            }

        } else {
            super.onDraw(canvas);
        }

    }

    private Drawable clipImage(BitmapDrawable img) {
        int width = 0;
        int height = 0;
        Bitmap.Config originalConfig = img.getBitmap().getConfig();
        if (originalConfig == null) {
            originalConfig = Bitmap.Config.ARGB_8888;
        }
        Bitmap outputBitmap = Bitmap.createBitmap(width, height, originalConfig);
        Canvas canvas = new Canvas(outputBitmap);
        return null;
    }

}
