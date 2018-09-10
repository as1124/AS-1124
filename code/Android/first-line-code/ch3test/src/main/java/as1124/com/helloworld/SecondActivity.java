package as1124.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import as1124.com.helloworld.dialog.NormalActivity;

/**
 * 测试隐式Intent调用
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        android.util.Log.i(getClass().getSimpleName(), "SingleInstance 模式下taskID===" + getTaskId());

        findViewById(R.id.secondActivityText).setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, NormalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}
