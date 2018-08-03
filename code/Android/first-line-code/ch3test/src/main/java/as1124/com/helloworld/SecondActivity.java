package as1124.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import as1124.com.helloworld.lifecycle.NormalActivity;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        android.util.Log.i("as-1124", this.toString() + ":" + getTaskId());

        findViewById(R.id.secondActivityText).setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, NormalActivity.class);
            startActivity(intent);
        });
    }
}
