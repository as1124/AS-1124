package as1124.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import as1124.com.helloworld.ch3.LayoutTestActivity;
import as1124.com.helloworld.lifecycle.DialogActivity;
import as1124.com.helloworld.lifecycle.NormalActivity;

public class HelloWorldActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);

        android.util.Log.i("as-1124", this.toString() + ":" + getTaskId());

        findViewById(R.id.toFirstBut).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, FirstActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.toSecondBut).setOnClickListener(v -> {
            String action = "as1124.com.helloworld.huangjw";
            Intent intent = new Intent(action);
            startActivity(intent);
        });

        findViewById(R.id.butStartNormal).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, NormalActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.butStartDialog).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, DialogActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button_ch3_main).setOnClickListener(v -> {
            Intent intent = new Intent(HelloWorldActivity.this, LayoutTestActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        android.util.Log.i("as-1124", "HelloActivity is paused!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        android.util.Log.i("as-1124", "HelloActivity stoped!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.util.Log.i("as-1124", "HelloActivity is destoried!!");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // onPause -> onSaveInstanceState -> onStop
        super.onSaveInstanceState(outState);
        android.util.Log.i("as-1124", "HelloActivity onSaveInstanceState called!!");
    }
}
