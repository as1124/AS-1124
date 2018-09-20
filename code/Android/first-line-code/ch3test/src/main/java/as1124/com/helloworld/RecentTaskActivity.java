package as1124.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * 演示APP中Activity在android任务中心中展示的情况，
 * 多Task栈启动Activity，单Task栈启动Activity
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class RecentTaskActivity extends Activity {

    static int count = 1;

    private TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_task);

        infoText = findViewById(R.id.text_info);
        infoText.setText("Recent Screens count=: " + count);
        count++;
        findViewById(R.id.but_test1).setOnClickListener(v -> {
            Intent intent = new Intent(RecentTaskActivity.this, RecentTaskActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        findViewById(R.id.but_test2).setOnClickListener(v -> {
            Intent intent = new Intent(RecentTaskActivity.this, RecentTaskActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
            startActivity(intent);
        });
        findViewById(R.id.but_test3).setOnClickListener(v -> {
            Intent intent = new Intent(RecentTaskActivity.this, RecentTaskActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
        findViewById(R.id.but_test4).setOnClickListener(v -> {
            Intent intent = new Intent(RecentTaskActivity.this, RecentTaskActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(intent);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        infoText.setText("Recent Screens count=: " + count);
        count++;
    }
}
