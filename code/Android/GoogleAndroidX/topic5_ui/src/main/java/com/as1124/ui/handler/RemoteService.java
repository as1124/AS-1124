package com.as1124.ui.handler;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * 模拟当作是远程APP的Service 进行通信
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class RemoteService extends Service {

    private Messenger remoteMessenger = new Messenger(new Handler() {

        @Override
        public void handleMessage(@NonNull Message msgFromClient) {
            Toast.makeText(getApplicationContext(), "RemoteHandler: " + msgFromClient.obj.toString(), Toast.LENGTH_SHORT).show();
            if (msgFromClient.replyTo != null) {
                Message msgReply = Message.obtain();
                msgReply.what = msgFromClient.what;
                msgReply.obj = "Remote Handler 已经收到客户端消息";
                try {
                    msgFromClient.replyTo.send(msgReply);
                } catch (RemoteException e) {
                    //
                }
            }
        }
    });

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Message msg = intent.getParcelableExtra("client_msg");
        if (msg != null) {
            Toast.makeText(this, "Remote-Service：调用Service消息", Toast.LENGTH_SHORT).show();
        }
        return remoteMessenger.getBinder();
    }
}
