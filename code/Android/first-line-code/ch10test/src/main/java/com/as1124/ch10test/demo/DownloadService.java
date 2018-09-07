package com.as1124.ch10test.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class DownloadService extends Service implements IDownloadListener {

    private DownloadTask downloadTask;

    private String downloadURL;

    public DownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onProgress(int progress) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this).build();
        manager.notify(1, notification);
    }

    @Override
    public void onSuccess() {
        downloadTask = null;
        stopForeground(true);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this).build();
        manager.notify(1, notification);
        Toast.makeText(this, "Download finished!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed() {
        downloadTask = null;
        stopForeground(true);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this).build();
        manager.notify(1, notification);
        Toast.makeText(this, "Download Failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCanceled() {
        downloadTask = null;
        stopForeground(true);
        Toast.makeText(this, "Download has been canceled!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaused() {
        downloadTask = null;
        Toast.makeText(this, "Download has been canceled!", Toast.LENGTH_SHORT).show();
    }

    class DownloadBinder extends Binder {
        public void startDownload(String url) {
            if (downloadTask == null) {
                downloadURL = url;
                downloadTask = new DownloadTask(DownloadService.this);
                startForeground(1, null);
                downloadTask.execute(downloadURL);
                Toast.makeText(DownloadService.this, "下载任务已开始", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
