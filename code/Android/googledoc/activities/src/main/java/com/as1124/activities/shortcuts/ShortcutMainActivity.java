package com.as1124.activities.shortcuts;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.as1124.activities.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Android Shortcut管理（快捷方式）
 * <ul>
 * 如果指定了Activity, 则Activity必须是 main activity
 * <li>Static Shortcut: App-Driven; 配置xml文件并在主Activity下添加 meta-data 信息; 不在桌面显示</li>
 * <li>Dynamic Shortcut: App-Driven; {@link ShortcutManager}创建; 桌面没有</li>
 * <li>Pinned Shortcut: User-Driven; {@link ShortcutManager#requestPinShortcut(ShortcutInfo, IntentSender)}; 桌面上</li>
 * <li>类似于微信，调用Launcher中Action实现快捷方式的创建; 在桌面上</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ShortcutMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut_main);

        findViewById(R.id.but_create_shortcut).setOnClickListener(v -> {
            createDynamicShortcut();
            createPinnedShortcut();
            createShortcutByLaunchAction();
        });
        findViewById(R.id.but_to_support).setOnClickListener(v -> createShortcutUsingSupport());

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.INSTALL_SHORTCUT,
                        Manifest.permission.UNINSTALL_SHORTCUT}, 924);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 924 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送Launcher项目对应Action实现创建快捷方式(Pinned), 桌面可见, 类似于微信
     */
    private void createShortcutByLaunchAction() {
        // 失败
        Intent shortcutIntent = new Intent(Intent.ACTION_CREATE_SHORTCUT);

        // 快捷方式的名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Desktop Shortcut");
        Bitmap shortcutIcon = BitmapFactory.decodeResource(getResources(), R.drawable.shortcut_e);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, shortcutIcon);
        Intent intent = new Intent("com.as1124.shortcuts.DesktopShortcut");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        try {
            if (shortcutIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(shortcutIntent);
            }
        } catch (Exception e) {
            Log.e("Shortcut", "创建桌面快捷方式失败", e);
        }
    }

    private void createShortcutUsingSupport() {
        // 失败
        Intent createIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 快捷方式的名称
        createIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Desktop Shortcut");
        createIntent.putExtra("duplicate", Boolean.TRUE);
        Bitmap shortcutIcon = BitmapFactory.decodeResource(getResources(), R.drawable.shortcut_e);
        createIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, shortcutIcon);
        Intent intent = new Intent("com.as1124.shortcuts.DesktopShortcut");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        createIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

        sendBroadcast(createIntent);
    }

    /**
     * 创建动态Shortcut, 必须指定 Intent, 如果要指定Activity的话，则对应的Activity必须是main 类型。
     * 清单文件中需要加上 DEFAULT_CATEGORY, 否则无法调起Activity
     */
    private void createDynamicShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
            List<ShortcutInfo> dynamicShortcuts = new ArrayList<>();
            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, "dynamicShortcut_b")
//                    .setActivity(new ComponentName(getPackageName(), DynamicShortcutActivity.class.getName()))
                    .setIntent(new Intent("com.as1124.shortcuts.DynamicShortcut"))
                    .setIcon(Icon.createWithResource(this, R.drawable.shortcut_b))
                    .setShortLabel("动态快捷方式A")
                    .setLongLabel("As1124动态快捷方式A").build();
            ShortcutInfo shortcutInfo2 = new ShortcutInfo.Builder(this, "dynamicShortcut_c")
//                    .setActivity(new ComponentName(getPackageName(), DynamicShortcutActivity.class.getName()))
                    .setIntent(new Intent("com.as1124.shortcuts.DynamicShortcut"))
                    .setIcon(Icon.createWithResource(this, R.drawable.shortcut_c))
                    .setShortLabel("动态快捷方式B")
                    .setLongLabel("As1124动态快捷方式B").build();
            dynamicShortcuts.add(shortcutInfo);
            dynamicShortcuts.add(shortcutInfo2);
            shortcutManager.setDynamicShortcuts(dynamicShortcuts);
        } else {
            Toast.makeText(this, "不支持动态 Shortcut 功能", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建Pin 形式的快捷方式, Android原生系统桌面有, 国内OEM厂商系统没有
     */
    private void createPinnedShortcut() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
            if (shortcutManager.isRequestPinShortcutSupported()) {
                ShortcutInfo pinnedShortcut = new ShortcutInfo.Builder(this, "pinnedShortcut")
                        .setIntent(new Intent("com.as1124.shortcuts.PinnedShortcut"))
                        .setIcon(Icon.createWithResource(this, R.drawable.shortcut_d))
                        .setShortLabel("Pinned快捷方式")
                        .setLongLabel("As1124 Pinned快捷方式").build();

                // Create the PendingIntent object only if your app need to be notified that the user
                // allowed the shortcut to be pinned. Note that, if the pinning operation fails, your app
                // isn't notified.
                Intent pinIntent = shortcutManager.createShortcutResultIntent(pinnedShortcut);
                pinIntent.setClassName(getPackageName(), ShortcutReceiver.class.getName());

                // Configure the intent so that your app's broadcast receiver gets the callback successfully.
                PendingIntent successCallback = PendingIntent.getBroadcast(this, 124, pinIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                shortcutManager.requestPinShortcut(pinnedShortcut, successCallback.getIntentSender());
            } else {
                Toast.makeText(this, "系统不支持创建 Pinned Shortcut！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
