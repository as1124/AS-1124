package as1124.com.helloworld.widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import as1124.com.helloworld.R;
import as1124.com.helloworld.ch3.list.TestWechatActivity;

/**
 * Android中Widget和Shortcut的使用,
 * Shortcut的使用要求API 7.0
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WidgetShortcutActivity extends Activity {

    public static final String ACTION_OLD_CREATE_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    public static final String ACTION_OLD_REMOVE_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    private static final String SHORTCUT_NAME = "HuangjwShortcut";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_shortcut);

        findViewById(R.id.but_change_app_logo).setOnClickListener(v -> {

        });

        findViewById(R.id.but_add_desktop_shortcut).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT > 23) {
                requestPermissions(new String[]{Intent.ACTION_CREATE_SHORTCUT}, 111);
            }
            addShortcut();
        });

        findViewById(R.id.but_remove_desktop_shortcut).setOnClickListener(v -> {
            removeShortcut();
        });

    }

    private void removeShortcut() {
        if (Build.VERSION.SDK_INT < 26) {
            Intent intent = new Intent(ACTION_OLD_REMOVE_SHORTCUT);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, SHORTCUT_NAME);
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.shortcut_eclipse));
        } else {

        }
    }

    private void addShortcut() {
        Intent shortcutIntent = new Intent(Intent.ACTION_VIEW);
        shortcutIntent.addCategory(Intent.CATEGORY_DEFAULT);
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcutIntent.setClassName(getApplicationContext().getPackageName(), TestWechatActivity.class.getName());
        if (Build.VERSION.SDK_INT < 26) {
            Intent intent = new Intent(ACTION_OLD_CREATE_SHORTCUT);
            // 不允许重复创建，不是根据快捷方式的名字判断重复的
            intent.putExtra("duplicate", false);
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, SHORTCUT_NAME);
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.shortcut_eclipse));
            try {
                sendBroadcast(intent);
                createLongTouchShortcut(shortcutIntent);
            } catch (Exception e) {
                android.util.Log.e("CreateShortcut", e.getMessage(), e);
            }
        } else {
            createLongTouchShortcut(shortcutIntent);
            createPinShortcut(shortcutIntent);
        }
    }

    /**
     * 添加图标长按快捷方式，{@link ShortcutManager} 是API之后添加的功能
     *
     * @param shortcutIntent 长按点击后触发的Intent
     */
    private void createLongTouchShortcut(Intent shortcutIntent) {
        Intent intent = new Intent(Intent.ACTION_CREATE_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, SHORTCUT_NAME);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.shortcut_eclipse));
        try {
            sendBroadcast(intent);
        } catch (Exception e) {
            android.util.Log.e("CreateShortcut", e.getMessage(), e);
        }
    }

    @RequiresApi(26)
    private void createPinShortcut(Intent shortcutIntent) {
        ShortcutManager shortcutManager = (ShortcutManager) getSystemService(Context.SHORTCUT_SERVICE);
        if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
            ShortcutInfo info = new ShortcutInfo.Builder(this, "Shortcut_id")
                    .setIcon(Icon.createWithResource(getApplicationContext(), R.drawable.shortcut_bing))
                    .setShortLabel("Short Label")
                    .setIntent(shortcutIntent)
                    .build();


            //当添加快捷方式的确认弹框弹出来时，将被回调
            PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(this, 123,
                    new Intent(this, WidgetShortcutReceiver.class), PendingIntent.FLAG_UPDATE_CURRENT);

            shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111) {
            addShortcut();
        }
    }
}
