package com.as1124.touch_input;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.as1124.selflib.WindowUtils;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowUtils.fullScreen(this);

        findViewById(R.id.text_test_gesture).setOnTouchListener((v, event) -> {
            int action = event.getActionMasked();
            // return true代表事件已被处理不应当继续分发
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 用来标识手势操作开始, 如果返回false则不会接收后续的event
                    Log.i("MotionEvent", "View Action was DOWN");
                    return false;
                case MotionEvent.ACTION_MOVE:
                    Log.i("MotionEvent", "View Action was MOVE");
                    return true;
                case MotionEvent.ACTION_UP:
                    // 用来标识手势操作结束
                    Log.i("MotionEvent", "View Action was UP");
                    return true;
                case MotionEvent.ACTION_CANCEL:
                    // 一般长按就取消了
                    Log.i("MotionEvent", "View Action was CANCEL");
                    return true;
                case MotionEvent.ACTION_OUTSIDE:
                    Log.i("MotionEvent", "View Movement occurred outside bounds of current screen");
                    return true;
                default:
                    return super.onTouchEvent(event);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // This example shows an Activity, but you would use the same approach
        // if you were subclassing a View

        int action = event.getActionMasked();
        // return true代表事件已被处理不应当继续分发
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 用来标识手势操作开始
                Log.i("MotionEvent", "Action was DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.i("MotionEvent", "Action was MOVE");
                return true;
            case MotionEvent.ACTION_UP:
                // 用来标识手势操作结束
                Log.i("MotionEvent", "Action was UP");
                return true;
            case MotionEvent.ACTION_CANCEL:
                // 一般长按就取消了
                Log.i("MotionEvent", "Action was CANCEL");
                return true;
            case MotionEvent.ACTION_OUTSIDE:
                Log.i("MotionEvent", "Movement occurred outside bounds of current screen");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
