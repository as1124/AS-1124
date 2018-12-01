package com.as1124.touch_input.gesture;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.EditText;
import android.widget.Toast;

import com.as1124.selflib.WindowUtils;
import com.as1124.touch_input.R;

/**
 * 手势输入处理
 * <ul>
 * <li>{@link Activity#onTouchEvent(MotionEvent)} 在ContentView中所有View发生Gesture输入时都会触发. Called when
 * a touch screen event was not handled by any of the view under it. This is most useful to process touch
 * events that happen outside of your window bounds, where there is no view to receive it.
 * <Strong>Return true if you have consumed the event, false if you have't, the default
 * implementation always returns false.</Strong>
 * </li>
 * <li>如果给某个View指定了OnTouchListener，则View上的Gesture输入不会触发所在Activity的onTouchEvent方法</li>
 * <li>{@link GestureDetector}</li>
 * <li>手势速率处理类：{@link android.view.VelocityTracker}</li>
 * </ul>
 * 手势的计算一般可以基于四个维度
 * <ol>
 * <li>两点间的距离</li>
 * <li>移动的方向，X轴、Y轴</li>
 * <li>历史轨迹，{@link MotionEvent#getHistorical}获取历史轨迹中的大小、压力、位置等信息；比如实现画'B'执行返回操作</li>
 * <li>移动的速率</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class GestureActivity extends Activity {

    private MyGestureListener gestureListener = new MyGestureListener();

    private GestureDetector mDetector;

    private VelocityTracker mVelocityTracker;

    private EditText myInputText;

    private boolean useSelfDetector = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        WindowUtils.fullScreen(this);
        mDetector = new GestureDetector(this, gestureListener);
        // set the detector as the double tap listener
        mDetector.setOnDoubleTapListener(gestureListener);

        myInputText = findViewById(R.id.text_test_pointer);
        findViewById(R.id.text_test_gesture).setOnTouchListener((v, event) -> {
            // 如果在 ACTION_DOWN 时返回false则不会接收后续的event
            Log.i("MotionEvent", "Handle MotionEvent on View");
            return analyseMotionEvent(event);
        });

        findViewById(R.id.but_self_gesture_detector).setOnClickListener(v -> useSelfDetector = !useSelfDetector);

        findViewById(R.id.but_pointer_capture).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                myInputText.setOnCapturedPointerListener((view, event) -> {
                    return true;
                });
                myInputText.requestPointerCapture();
            } else {
                Toast.makeText(this, "当前系统版本不支持Pointer Capture", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.but_scroll_gesture).setOnClickListener(
                v -> startActivity(new Intent(GestureActivity.this, SelfScrollableActivity.class))
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 如果为ContentView中的某个子View单独设置了OnTouchListener, 则该View上发生的Gesture输入不会触发该方法
        if (useSelfDetector) {
            return mDetector.onTouchEvent(event);
        } else {
            return analyseMotionEvent(event);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        // ATTENTION 为什么true之后立马又回调了一个 false
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pointer Capture").setMessage("捕获指针请求成功==" + hasCapture).show();
    }

    private boolean analyseMotionEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:// 用来标识手势操作开始
                Log.i("MotionEvent", "Action was DOWN");
                if (mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    // Reset the velocity back to it's initial state.
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.i("MotionEvent", "Action was MOVE");
                mVelocityTracker.addMovement(event);

                // Whe you want to determine the velocity, call computeCurrentVelocity(). Then call
                // getXVelocity() and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);

                // Log velocity of pixels per second. Best practice to use VelocityTrackerCompat when possible
                Log.i("GestureActivity", "X velocity == " + mVelocityTracker.getXVelocity());
                Log.i("GestureActivity", "Y velocity == " + mVelocityTracker.getYVelocity());
                return true;
            case MotionEvent.ACTION_UP:// 用来标识手势操作结束
                Log.i("MotionEvent", "Action was UP");
                mVelocityTracker.recycle();
                return true;
            case MotionEvent.ACTION_CANCEL:
                Log.i("MotionEvent", "Action was CANCEL");
                // Return a VelocityTracker object back to be re-used by others.
                mVelocityTracker.recycle();
                return true;
            case MotionEvent.ACTION_OUTSIDE:
                Log.i("MotionEvent", "Movement occurred outside bounds of current screen");
                return true;
            default:
                return false;
        }
    }
}
