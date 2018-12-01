package com.as1124.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import com.as1124.selflib.WindowUtils;
import com.as1124.ui.layout.CardViewActivity;

import java.util.List;

/**
 * 本模块旨在学习User Interface & Navigation设计
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowUtils.fullScreen(this);

        findViewById(R.id.but_to_recyclerview).setOnClickListener(v -> {
        });
        findViewById(R.id.but_to_cardview).setOnClickListener(
                v -> startActivity(new Intent(MainActivity.this, CardViewActivity.class))
        );

        findViewById(R.id.but_test_activity).setOnClickListener(v -> {
            Intent intent = new Intent();
            // intent-filter形式
            intent.setAction("com.as1124.huangjw.XiaoXianNv");
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
//            List<ResolveInfo> resolvers = getPackageManager().queryBroadcastReceivers(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            if (resolvers != null) {
            sendBroadcast(intent);
//            }
        });
    }
}
