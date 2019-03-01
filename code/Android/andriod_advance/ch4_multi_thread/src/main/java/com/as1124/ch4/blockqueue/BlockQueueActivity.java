package com.as1124.ch4.blockqueue;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import com.as1124.ch4.R;

/**
 * 阻塞队列
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class BlockQueueActivity extends Activity {

    private Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    });
    private Messenger mService;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // ATTENTION 获取Binder对象建立跨进程间信道
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            clientMessenger = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_queue);

        findViewById(R.id.but_block_queue).setOnClickListener(v -> {
            Message msg = Message.obtain();
            msg.what = 123;
            msg.arg2 = 404;
            Bundle postData = new Bundle();
            postData.putString("name", "哈哈哈");
            postData.putInt("age", 24);
            msg.setData(postData);
            msg.replyTo = clientMessenger;
            if (mService != null) {
                try {
                    mService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                initMessenger();
            }
        });
    }

    private void initMessenger() {
        Intent bindIntent = new Intent("com.as1124.service");
        bindIntent.setClassName("com.as1124.ch3.views", "com.as1124.ch3.views.AcrossProcessService");
        try {
//            startService(bindIntent);
            bindService(bindIntent, connection, Service.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initMessenger();
    }

    @Override
    protected void onStop() {
        super.onStop();

        unbindService(connection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12345) {
            Toast.makeText(this, "调用应用锁成功", Toast.LENGTH_SHORT).show();
        }
    }
}
