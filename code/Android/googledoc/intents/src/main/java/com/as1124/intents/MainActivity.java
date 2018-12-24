package com.as1124.intents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.intents.activities.IntentForActivity;
import com.as1124.intents.broadcast.Intent4BroadcastActivity;
import com.as1124.intents.service.Intent4ServiceActivity;
import com.as1124.intents.system.SystemIntentActivity;

/**
 * 本Module介绍{@link Intent} 和 {@link android.content.IntentFilter}的使用
 * <ol>
 * <li>显示、隐式Intent启动Activity</li>
 * <li>显示、隐式Intent启动Broadcast</li>
 * <li>显示、隐式Intent启动Service</li>
 * <li>IntentFilter匹配规则：action、data、category</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_activity).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, IntentForActivity.class)));
        findViewById(R.id.but_to_broadcast).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Intent4BroadcastActivity.class)));
        findViewById(R.id.but_to_service).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Intent4ServiceActivity.class)));
        findViewById(R.id.but_to_system_intent).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SystemIntentActivity.class)));
    }
}
