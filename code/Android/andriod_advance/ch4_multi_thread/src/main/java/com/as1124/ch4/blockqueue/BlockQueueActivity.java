package com.as1124.ch4.blockqueue;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.as1124.ch4.R;

/**
 * 阻塞队列
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class BlockQueueActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_queue);

        findViewById(R.id.but_block_queue).setOnClickListener(v -> {

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12345) {
            Toast.makeText(this, "调用应用锁成功", Toast.LENGTH_SHORT).show();
        }
    }
}
