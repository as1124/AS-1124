package com.as1124.ch3.views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import com.as1124.ch3.views.axis.ViewCoordinateActivity;
import com.as1124.ch3.views.scroll.ViewScrollActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_coordinate).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewCoordinateActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//            this.finish();
        });

        findViewById(R.id.but_to_scroll).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewScrollActivity.class)));

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 111);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
        }
    }
}
