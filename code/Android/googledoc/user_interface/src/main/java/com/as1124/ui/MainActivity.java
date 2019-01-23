package com.as1124.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.selflib.WindowUtils;
import com.as1124.ui.layout.LayoutTopicActivity;
import com.as1124.ui.material.MaterialTopicActivity;
import com.as1124.ui.nc.NotificationTopicActivity;

/**
 * 本模块旨在学习User Interface & Navigation设计
 * <ol>
 * <li>布局容器的使用</li>
 * <li>认识Material-Design</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        WindowUtils.fullScreen(this);

        findViewById(R.id.but_to_layout).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, LayoutTopicActivity.class)));
        findViewById(R.id.but_to_material).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MaterialTopicActivity.class)));
        findViewById(R.id.but_to_notification).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, NotificationTopicActivity.class)));
    }
}
