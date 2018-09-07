package helloworld.com.as1124.ch5test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.but_test).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission("com.as1124.googledoc.KILL_PERMISSION") != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{"com.as1124.googledoc.KILL_PERMISSION"}, 112);
                } else {
                    gotoActivity();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 112) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gotoActivity();
            }
        }
    }

    private void gotoActivity() {
        Intent intent = new Intent();
        intent.setClassName("com.as1124.googledoc", "com.as1124.googledoc.permission.CustomPermissionActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Log.e("ssss", e.getMessage(), e);
        }
    }
}
