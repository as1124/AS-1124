package com.as1124.ui.handler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.as1124.ui.R;

/**
 * Handler, Looper, MessageQueue, Message
 * <ol>
 * <li>Handler是对开发者开放的接口，是{@link Message}执行的默认target</li>
 * <li>Looper是{@link Handler}的成员属性；负责消息分发，{@link Looper#loop()} 本身不阻塞，阻塞是因为{@link android.os.MessageQueue}导致</li>
 * <li>Looper是线程隔离的，由静态ThreadLocal进行管理维护；调用{@link Looper#prepare()}进行线程Looper的初始化，默认是null</li>
 * <li>MessageQueue是{@link Looper}的成员属性，负责消息的管理、排序</li>
 * <li>MessageQueue 的 <code>next()</code> 被调用时，如果没有消息则阻塞</li>
 * <li>Message 是链表结构, 由MessageQueue 进行维护处理</li>
 * <li>Message 是{@link android.os.Parcelable}资源，和IO序列化资源一样属于耗时资源，所以内部处理采用了类似于线程池的模型</li>
 * <li>Message 的管理：sPool-当前可用Message对象，sPoolSize: 可用Message对象数量，MAX_POOL_SIZE：50；都是静态变量，每当一个Message被回收则加入可用消息池；所以消息池是整个应用循环利用</li>
 * <li>Messenger 可用于进程间通信，原理是基于{@link android.os.Binder}的通信; 搭配{@link Handler}, {@link ServiceConnection} 一起使用</li>
 * <li>Messenger 跨进程：双方各持有一个{@link Messenger}, 一方作为另一方的replyTo；同时{@link Message}也是Android序列化对象，也可直接通过target对应的{@link Handler}直接发送消息</li>
 * </ol>
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class AboutHandlerActivity extends Activity implements View.OnClickListener {

    private Handler uiHandler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 99:
                    if (logText != null) {
                        logText.append(msg.obj.toString());
                        logText.append("\r\n");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serverMessengerRef = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger clientMessenger = null;

    private Messenger serverMessengerRef = null;

    private TextView logText;

    private TaskThread ta;

    private CommandThread tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_handler);

        clientMessenger = new Messenger(uiHandler);

        logText = findViewById(R.id.text_handler_log);
        findViewById(R.id.btn_handler_ta).setOnClickListener(this);
        findViewById(R.id.btn_handler_tb).setOnClickListener(this);
        findViewById(R.id.btn_handler_start).setOnClickListener(this);
        findViewById(R.id.btn_handler_stop).setOnClickListener(this);
        findViewById(R.id.btn_handler_messenger).setOnClickListener(this);
        findViewById(R.id.btn_handler_msg2server).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_handler_ta:
                ta = new TaskThread(uiHandler);
                new Thread(ta).start();
                break;
            case R.id.btn_handler_tb:
                tb = new CommandThread(uiHandler, ta.getHandler());
                new Thread(tb).start();
                break;
            case R.id.btn_handler_start:
                Message msg = tb.getHandler().obtainMessage(11, "http://www.i-dadong.com");
                tb.getHandler().sendMessage(msg);
                break;
            case R.id.btn_handler_stop:
                Message msgx = tb.getHandler().obtainMessage(10);
                tb.getHandler().sendMessage(msgx);
                break;
            case R.id.btn_handler_messenger:
                Intent intent = new Intent(this, RemoteService.class);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setAction("com.as1124.messengerTest");
                Message bindMsg = Message.obtain();
                intent.putExtra("client_msg", bindMsg);
                this.bindService(intent, connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_handler_msg2server:
                Message msg2Server = Message.obtain(uiHandler, 99, "你好，这是来自Client的信息");
                msg2Server.replyTo = clientMessenger;
                try {
                    serverMessengerRef.send(msg2Server);
                } catch (RemoteException e) {
                    Log.i("Messenger-Test", "跨进程通讯异常==" + e.getMessage());
                }
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        uiHandler.getLooper().quit();

        if (ta != null && ta.getHandler() != null) {
            ta.getHandler().getLooper().quit();
        }

        if (tb != null && tb.getHandler() != null) {
            tb.getHandler().getLooper().quit();
        }
    }
}
