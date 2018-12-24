package com.as1124.intents.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.as1124.intents.R;

import java.util.List;

/**
 * Intent处理Activity
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class IntentForActivity extends Activity implements View.OnClickListener {

    protected static final int REQUEST_CODE = 124;

    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_for);

        findViewById(R.id.but_start_activity).setOnClickListener(this);
        findViewById(R.id.but_start_resutl).setOnClickListener(this);
        findViewById(R.id.but_implicit_intent).setOnClickListener(this);
        findViewById(R.id.but_background_explicit_activity).setOnClickListener(this);
        findViewById(R.id.but_background_implicit_intent).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Activity result OK!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Activity result Cancel!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int viewid = v.getId();
        switch (viewid) {
            case R.id.but_start_activity:
                // 这些方式都是供显式启动Activity调用的
                Intent intent = new Intent(IntentForActivity.this, TestActivity.class);
//                intent.setClass();
//                intent.setClassName();
//                intent.setComponent(new ComponentName())
                startActivity(intent);
                break;
            case R.id.but_start_resutl:
                startActivityForResult(new Intent(IntentForActivity.this, TestActivity.class), REQUEST_CODE);
                break;
            case R.id.but_implicit_intent:
                Intent intent2 = implicitIntent();
                List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent2, PackageManager.MATCH_ALL);
                if (resolveInfos.size() > 1) {
                    Intent chooserIntent = Intent.createChooser(intent2, "请选择要打开的应用");
                    startActivity(chooserIntent);
                } else if (resolveInfos.size() > 0) {
                    startActivity(intent2);
                } else {
                    Toast.makeText(this, "找不到能处理Intent 的Activity", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.but_background_explicit_activity:
                scheduleExplicitIntent();
                break;
            case R.id.but_background_implicit_intent:
                scheduleImplicitIntent();
                break;
            default:
                break;
        }
    }

    private void scheduleExplicitIntent() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerTime = System.currentTimeMillis() + 1 * 20 * 1000;
        Intent intent = new Intent();
        intent.setClass(this, TestActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 125, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pIntent);
    }

    private void scheduleImplicitIntent() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerTime = System.currentTimeMillis() + 1 * 20 * 1000;
        Intent intent = new Intent();
        intent.setAction("com.as1124.myActivity");
        intent.setData(Uri.parse("http://www.bing.com"));
        PendingIntent pIntent = PendingIntent.getActivity(this, 126, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pIntent);
    }

    /**
     * Intent 匹配策略测试
     *
     * @return
     */
    private Intent implicitIntent() {
        Intent intent2 = new Intent();
        int mod = count % 14;
        switch (mod) {
            case 0:
                // 匹配部分 Category
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                // 失败
                break;
            case 1:
                // 全 Category 匹配
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                // 失败
                break;
            case 2:
                // 超量 Category 匹配
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                intent2.addCategory(Intent.CATEGORY_HOME);
                // 失败
                break;
            case 3:
                // 一个 Action 匹配
                intent2.setAction("com.as1124.myActivity");
                // 失败
                break;
            case 4:
                // 一个 data 匹配
                intent2.setData(Uri.parse("https://www.baidu.com"));
                // 成功, 多个ResolverInfo
                break;
            case 5:
                // 部分 Category 和一个 Action
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.setAction("com.as1124.myActivity");
                // 失败
                break;
            case 6:
                // 全部 Category 和一个 Action
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                intent2.setAction("com.as1124.myActivity");
                // 失败
                break;
            case 7:
                // 一个 data 和一个 Action
                intent2.setAction("com.as1124.myActivity");
                intent2.setData(Uri.parse("http://www.bing.com"));
                // 成功, 一个 ResolverInfo
                break;
            case 8:
                // 一个 data 和部分 Category
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.setData(Uri.parse("http://www.bing.com"));
                // 成功, 多个 ResolverInfo
                break;
            case 9:
                // 一个 data 和全部 Category
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                intent2.setData(Uri.parse("http://www.bing.com"));
                // 成功, 一个 ResolverInfo
                break;
            case 10:
                // 一个 data 和超量 Category
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                intent2.addCategory(Intent.CATEGORY_HOME);
                intent2.setData(Uri.parse("http://www.bing.com"));
                // 失败
                break;
            case 11:
                // 一个 Action, 一个 data, 部分 Category匹配
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.setAction("com.as1124.myActivity");
                intent2.setData(Uri.parse("http://www.bing.com"));
                // 成功, 一个 ResolverInfo
                break;
            case 12:
                // 一个 Action, 一个 data, 全部 Category匹配
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                intent2.setAction("com.as1124.myActivity");
                intent2.setData(Uri.parse("http://www.bing.com"));
                // 成功, 一个 ResolverInfo
                break;
            case 13:
                // 一个 Action, 一个 data, 超量 Category匹配
                intent2.addCategory(Intent.CATEGORY_DEFAULT);
                intent2.addCategory(Intent.CATEGORY_BROWSABLE);
                intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                intent2.addCategory(Intent.CATEGORY_HOME);
                intent2.setAction("com.as1124.myActivity");
                intent2.setData(Uri.parse("http://www.bing.com"));
                // 失败
                break;
            default:
                break;
        }

        count++;
        return intent2;
    }
}
