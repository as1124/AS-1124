package com.as1124.touch_input.gesture;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.selflib.WindowUtils;
import com.as1124.touch_input.R;

/**
 * 自定义实现可滑动动效的界面，并非通过在布局中嵌入{@link android.widget.ScrollView}实现
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SelfScrollableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_scrollable);
        WindowUtils.fullScreen(this);
    }
}
