package com.as1124.activities.multiwindow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.as1124.activities.MainActivity;
import com.as1124.activities.R;

/**
 * Android 多窗口和画中画模式的支持
 * <ol>
 * <li>分屏、多窗口：resizeableActivity</li>
 * <li>分屏、多窗口支持拖放: {@link android.view.View#startDragAndDrop(ClipData, View.DragShadowBuilder, Object, int)}</li>
 * <li>画中画模式：supportsPictureInPicture</li>
 * <li>画中画实例应用：微信视频聊天的小窗</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MultiWindowActivity extends Activity {

    private static String TAG = "Multi-Window";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_window);

        Log.i(TAG, "Activity onCreate");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TextView text = findViewById(R.id.text_multi_window);
            text.setText("1-多窗口支持：resizeableActivity\r\n2-画中画：supportsPictureInPicture");
            findViewById(R.id.but_support_resize).setOnClickListener(v -> {
                boolean supportMultiWindow = MultiWindowActivity.this.isInMultiWindowMode();
                boolean supportPiP = MultiWindowActivity.this.isInPictureInPictureMode();
                Toast.makeText(this, "运行在多窗口模式：" + supportMultiWindow, Toast.LENGTH_SHORT).show();
            });
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Activity Window").setMessage("当前设备不支持多窗口模式和画中画模式")
                    .create().show();
        }

        findViewById(R.id.but_start_pip).setOnClickListener(v -> {
            // 只有当处于分屏模式下才能生效
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        findViewById(R.id.but_start_pip2).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                enterPictureInPictureMode();
            }
        });

    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode, Configuration newConfig) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig);

        Log.i(TAG, "多窗口模式放生变化");
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);

        Log.i(TAG, "画中画模式更改");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "Activity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "Activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "Activity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "Activity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(TAG, "Activity onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "Activity onConfigurationChanged[[ " + newConfig.orientation);
    }
}
