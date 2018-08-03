package com.as1124.ch8test;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.as1124.ch8test.notification.NoticeDetailActivity;

/**
 * 在通知传递参数的时候，关键操作在于{@link PendingIntent#FLAG_UPDATE_CURRENT} 的设置
 *
 * @author as-1124 (mailto: as1124huang@gmail.com)
 */
public class NotificationActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        findViewById(R.id.ch8_but_sendnotice).setOnClickListener(this);
        findViewById(R.id.ch8_but_sendnotice1).setOnClickListener(this);
        findViewById(R.id.ch8_but_sendnotice2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewid = v.getId();
        Bundle extData = new Bundle();
        extData.putString("ext_data", "去你妈的");
        Intent intent = new Intent(this, NoticeDetailActivity.class);
        intent.putExtra("normal_intent", "huangjw");
        intent.putExtras(extData);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        /*****************最后一个Flag参数很重要：关系到Intent跳转时候的activity能不能收到extra数据************************/

        PendingIntent pintent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder noticeBuilder = new Notification.Builder(this)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pintent)
                .setExtras(extData);
        switch (viewid) {
            case R.id.ch8_but_sendnotice:
                noticeBuilder.setContentTitle("Title of Notification")
                        .setContentText("消息正文内容")
                        .setNumber(2)
                        .setSmallIcon(R.drawable.icon_warn_32)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_warn_64))
                        .setVibrate(new long[]{0, 500, 3000, 1000})// 底层是当作二维数组处理, 间隔时长-震动时间
                        .setLights(Color.GREEN, 1000, 2000)
                        .setDefaults(Notification.DEFAULT_SOUND);
                if (Build.VERSION.SDK_INT >= 21) {
                    noticeBuilder.setColor(getResources().getColor(R.color.noticeColor));
                }
                manager.notify(1, noticeBuilder.build());
                break;
            case R.id.ch8_but_sendnotice1:
                noticeBuilder.setStyle(new Notification.BigTextStyle().bigText("通知了很多文字哈哈哈哈哈啊哈哈哈哈哈哈哈.....士大夫看见去你妈的"));
                manager.notify(2, noticeBuilder.build());
                break;
            case R.id.ch8_but_sendnotice2:
                noticeBuilder.setStyle(new Notification.BigPictureStyle().setBigContentTitle("大图片通知的标题---Title")
                        .setSummaryText("大图片通知的正文摘要.....是个什么鬼样式呢！！！")
                        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.big_icon)));
                manager.notify(3, noticeBuilder.build());
                break;
            default:
                break;
        }
    }
}
