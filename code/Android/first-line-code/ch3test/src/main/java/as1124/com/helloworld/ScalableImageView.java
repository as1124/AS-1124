package as1124.com.helloworld;

        import android.content.Context;
        import android.graphics.Matrix;
        import android.support.annotation.Nullable;
        import android.util.AttributeSet;
        import android.view.GestureDetector;
        import android.view.MotionEvent;
        import android.view.ScaleGestureDetector;
        import android.widget.ImageView;

/**
 * 实现ImageView控件的手势支持功能，如缩放，双击等
 *
 * @author as-1124 (mailto:as1124huang@gmail.com)
 */
public class ScalableImageView extends ImageView {

    private ScaleGestureDetector scaleDetector = null;
    private GestureDetector gestureDetector = null;
    private ImageView.ScaleType scaleType = null;

    public ScalableImageView(Context context) {
        super(context);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void sharedConstructing(Context context, ImageView imageView) {
        super.setClickable(true);
        scaleDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });

        Matrix matrix = new Matrix();
        Matrix preMatrix = new Matrix();
        float[] m = new float[9];
        int normalizedScale = 1;
        if (scaleType == null) {
            scaleType = ImageView.ScaleType.FIT_CENTER;
        }
        int minScale = 1;
        int maxScale = 3;
    }
}
