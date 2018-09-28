package com.as1124.googledoc.intent;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.widget.Toast;

import com.as1124.googledoc.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * {@link android.content.Intent}、{@link android.content.IntentFilter}部件学习
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class IntentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        findViewById(R.id.but_intent_add_alarm).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission("com.android.alarm.permission.SET_ALARM") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.SET_ALARM, "com.android.alarm.permission.SET_ALARM"}, 111);
            } else {
                addAlarm();
            }
        });

        findViewById(R.id.but_intent_show_alarms).setOnClickListener(v -> {
            showAllAlarms();
        });
        findViewById(R.id.but_intent_add_calendar).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, 112);
            } else {
                addCalendarEvent();
            }
        });
        findViewById(R.id.but_intent_add_photo).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 113);
            } else {
                startCamera();
            }
        });
        findViewById(R.id.but_intent_pick_contact).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    pickContacts();
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 114);
                }
            } else {
                pickContacts();
            }
        });
        findViewById(R.id.but_intent_phone).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 115);
                }
            } else {
                makePhoneCall();
            }
        });
        findViewById(R.id.but_intent_sms).setOnClickListener(v -> {
            sendMessage();
        });
        findViewById(R.id.but_intent_settings).setOnClickListener(v -> {
            openSettingPage();
        });
    }

    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_CALL); // 拨打电话
//            Intent intent = new Intent(Intent.ACTION_DIAL); // 打开拨号面板
        intent.setData(Uri.parse("tel:10086"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }
            } else {
                startActivity(intent);
            }
        }
    }

    private void sendMessage() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.putExtra("sms_body", "给10086发送个中国联通");
        intent.setData(Uri.parse("smsto:10086"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void openSettingPage() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void pickContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 223);
        }
    }

    private void addAlarm() {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "我的闹钟");
        ArrayList<Integer> days = new ArrayList<>();
        days.add(Calendar.MONDAY);
        days.add(Calendar.TUESDAY);
        intent.putExtra(AlarmClock.EXTRA_DAYS, days);
        intent.putExtra(AlarmClock.EXTRA_HOUR, 20);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, 30);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // 可以避免程序崩溃
            try {
                startActivity(intent);
            } catch (Exception e) {
                android.util.Log.e(getClass().getSimpleName(), e.getMessage(), e);
            }
        }
    }

    private void showAllAlarms() {
        Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                startActivity(intent);
            } catch (Exception e) {
                android.util.Log.e(getClass().getSimpleName(), e.getMessage(), e);
            }
        }
    }

    private void addCalendarEvent() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, "日程标题");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "干什么，呵呵呵把");
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, Boolean.TRUE);
        Calendar begin = Calendar.getInstance(Locale.getDefault());
        begin.add(Calendar.HOUR, 1);
        Calendar end = Calendar.getInstance(Locale.getDefault());
        end.add(Calendar.DATE, 1);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                startActivity(intent);
            } catch (Exception e) {
                android.util.Log.e(getClass().getSimpleName(), e.getMessage(), e);
            }
        }
    }

    private void startCamera() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //拍照
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA); //静态模式启动相机，拍照完成后自动保存并不返回调用者APP
//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE); //录像
        File imageFile = new File(Environment.getExternalStorageDirectory(), Environment.DIRECTORY_DOWNLOADS + "/huangjw.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, 222);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addAlarm();
            }
        } else if (requestCode == 112 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addCalendarEvent();
            }
        } else if (requestCode == 113 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            }
        } else if (requestCode == 114 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickContacts();
            }
        } else if (requestCode == 115 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222 && resultCode == RESULT_OK) {
            Toast.makeText(this, "拍照成功", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 223 && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, new String[]{ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY},
                    null, null, null);
            if (cursor != null) {
                try {
                    cursor.moveToNext();
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String primaryName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    android.util.Log.i("Intent_Pick_Contact", id + "==" + name);
                } catch (Exception e) {
                    android.util.Log.e("Intent_Pick_Contact", e.getMessage(), e);
                } finally {
                    cursor.close();
                }
            }
        }
    }
}
