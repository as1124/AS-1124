package as1124.com.helloworld.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import as1124.com.helloworld.R;

/**
 * 系统{@link AlertDialog} 和 {@link ProgressBar}的使用
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class NormalActivity extends Activity {

    private LinearLayout viewRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        android.util.Log.i(getClass().getSimpleName(), "Standard + FLAG_NEW_TASK 模式下taskID===" + getTaskId());

        viewRoot = findViewById(R.id.normal_layout_root);
        findViewById(R.id.test_alert_dialog).setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NormalActivity.this);
            dialogBuilder.setTitle("Title").setMessage("提醒内容正文").setIcon(R.mipmap.ic_launcher);
            dialogBuilder.setCancelable(true);
            dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                android.util.Log.i("as-1124", "Dialog OK clicked!");
            });
            dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                android.util.Log.i("as-1124", "Dialog closed!!!");
            });
            dialogBuilder.show();
        });

        findViewById(R.id.test_progress_dialog).setOnClickListener(v -> {
            ProgressBar bar = new ProgressBar(NormalActivity.this, null, android.R.attr.progressBarStyleHorizontal);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            bar.setLayoutParams(layoutParams);
            bar.setMax(100);
            bar.setSecondaryProgress(10);
            bar.incrementProgressBy(3);
            bar.setIndeterminate(true);
            bar.setVisibility(ProgressBar.VISIBLE);
            viewRoot.addView(bar);
        });

    }
}
