package com.as1124.ch3.views.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.as1124.ch3.views.R;

/**
 * View的滑动
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ViewScrollActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scroll);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
