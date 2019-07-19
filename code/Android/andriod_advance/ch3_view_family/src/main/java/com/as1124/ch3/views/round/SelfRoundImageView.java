package com.as1124.ch3.views.round;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 实现圆形ImageView
 * <ol>
 * 相交模式
 * <li><code>PorterDuff.Mode.**_OVER: 覆盖模式，即设置相交部分谁cover谁</li>
 * <li><code>PorterDuff.Mode.**_IN: 重叠模式，即设置只显示相交部分</li>
 * <li><code>PorterDuff.Mode.XOR: 镂空模式，即显示除相交外的所有部分</li>
 * <li><code>PorterDuff.Mode.**_OUT: 相交切割模式，即显示单元切割掉相交部分或后剩余的</li>
 * <li><code>PorterDuff.Mode.ADD/LIGHTEN/DARKER: 着色遮罩模式，即着色透明遮罩层</li>
 * </ol>
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class SelfRoundImageView extends ImageView {

    private int drawMode = -1;

    public SelfRoundImageView(Context context) {
        super(context);
    }

    public SelfRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF srcRectF = new RectF(0, 0, getWidth(), getHeight());
        // 使用离屏缓存，新建一个srcRectF区域大小的图层
        canvas.saveLayer(srcRectF, null, Canvas.ALL_SAVE_FLAG);

        switch (drawMode) {
            case -1:
                way1ToRoundIcon(canvas);
                super.onDraw(canvas);
                break;
            case 0:
                Drawable drawable = getDrawable();
                if (drawable != null && (drawable instanceof BitmapDrawable)) {
                    Bitmap circleBitmap = clipBitmap2Circle(((BitmapDrawable) drawable).getBitmap());
                    Rect srcRect = new Rect(0, 0, circleBitmap.getWidth(), circleBitmap.getHeight());
                    Rect dstRect = new Rect(0, 0, getWidth(), getHeight());
                    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    canvas.drawBitmap(circleBitmap, srcRect, dstRect, paint);
                } else {
                    super.onDraw(canvas);
                }
                break;
            case 1:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.ADD);
                break;
            case 2:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.CLEAR);
                break;
            case 3:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.DARKEN);
                break;
            case 4:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.DST_IN);
                break;
            case 5:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.DST_OUT);
                break;
            case 6:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.DST_OVER);
                break;
            case 7:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.SRC_IN);
                break;
            case 8:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.SRC_OUT);
                break;
            case 9:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.SRC_OVER);
                break;
            case 10:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.XOR);
                break;
            case 11:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.OVERLAY);
                break;
            case 12:
                super.onDraw(canvas);
                way3ToRoundIcon(canvas, PorterDuff.Mode.MULTIPLY);
                break;
            default:
                super.onDraw(canvas);
                break;
        }
        canvas.restore();
    }

    /**
     * 有说法说{@link Canvas#clipPath(Path)}画出来的图形不抗锯齿，导致边缘毛糙....暂时未发现这样的情况
     *
     * @param canvas
     */
    private void way1ToRoundIcon(Canvas canvas) {
        Path path = new Path();
        int px = getWidth() / 2;
        int py = getHeight() / 2;
        int radius = Math.min(px, py) - 10;
        path.addCircle(px, py, radius, Path.Direction.CCW);

//        RectF rectF = new RectF(0, 0, getWidth(), getHeight());
//        path.addOval(rectF, Path.Direction.CCW);

        // addCircle 和 addOval 再裁剪后的效果是一样的

        canvas.clipPath(path);
    }


    /**
     * 将图片裁剪成圆形图片
     *
     * @param bitmap
     * @return
     */
    private Bitmap clipBitmap2Circle(Bitmap bitmap) {
        // 创建一个和bitmap等大且色彩模式为 ARGB_8888 的画布
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        int px = bitmap.getWidth() / 2;
        int py = bitmap.getHeight() / 2;
        canvas.drawCircle(px, py, Math.min(px, py) - 5, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Bitmap 和上面画的圆做图形相交操作
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }

    /**
     * {@link android.graphics.PorterDuff}来进行图像的合成
     *
     * @param canvas
     */
    private void way3ToRoundIcon(Canvas canvas, PorterDuff.Mode mode) {
        // 在相交模式下：SRC代表输入图形图像, DST代表原来画布上有的内容
        // 所以这里采用的是保留画布原有图片内容：显示相交重叠的DST内容

        Path path = new Path();
        int px = getWidth() / 2;
        int py = getHeight() / 2;
        int radius = Math.min(px, py) - 5;
        path.addCircle(px, py, radius, Path.Direction.CCW);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        paint.setXfermode(new PorterDuffXfermode(mode));


        if (mode == PorterDuff.Mode.DST_IN || mode == PorterDuff.Mode.SRC_IN) {
            // SDK版本差异
//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
//                canvas.drawPath(path, paint);
//            } else {
            Path srcPath = new Path(); // 控件原始区域的path
            srcPath.addRect(new RectF(0, 0, getWidth(), getHeight()), Path.Direction.CCW);

            // 计算 srcPath 和圆形 path 的差集
            srcPath.op(path, Path.Op.DIFFERENCE);
            if (mode == PorterDuff.Mode.DST_IN) {
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            } else {
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            }
            canvas.drawPath(srcPath, paint);
//            }
        } else {
            canvas.drawPath(path, paint);
        }

        // 清除Xfermode
        paint.setXfermode(null);
    }

    public int getDrawMode() {
        return drawMode;
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }
}
