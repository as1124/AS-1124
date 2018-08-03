package as1124.com.helloworld.lifecycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ProgressBar;

import as1124.com.helloworld.R;

public class NormalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        android.util.Log.i("as-1124", this.toString() + ":" + getTaskId());

        findViewById(R.id.test_alert_dialog).setOnClickListener(v -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NormalActivity.this);
            dialogBuilder.setTitle("提醒对话框").setMessage("提醒内容正文").setIcon(R.mipmap.ic_launcher);
            dialogBuilder.setCancelable(true);
            dialogBuilder.setPositiveButton("OK", (dialog, which) -> {
                android.util.Log.i("as-1124", "Dialog OK clicked!");
            });
            dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.dismiss();
                android.util.Log.i("as-1124", "Dialog closed!!!");
            });
            dialogBuilder.show();
        });

        findViewById(R.id.test_progress_dialog).setOnClickListener(v -> {
            ProgressBar bar = new ProgressBar(NormalActivity.this);
            bar.setMax(100);
            bar.setSecondaryProgress(10);
            bar.incrementProgressBy(3);
            bar.setIndeterminate(true);
        });
    }
}
