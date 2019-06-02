package com.as1124.app_manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_another).setOnClickListener(v -> startActivity(new Intent(this, AnotherActivity.class)));
        findViewById(R.id.but_to_third).setOnClickListener(v -> startActivity(new Intent(this, ThirdActivity.class)));
        findViewById(R.id.but_aaa).setOnClickListener(v -> unlockByFingerprint());

        findViewById(R.id.but_disable_copy).setOnClickListener(v -> {
            disableClipboard();
        });

    }


    private void disableClipboard() {

    }


    private void unlockByFingerprint() {
        if (Build.VERSION.SDK_INT >= 28) {
            Executor aaaa = Executors.newSingleThreadExecutor();
            BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this);
            builder.setDescription("生物认证描述信息");
            builder.setTitle("生物认证标题").setSubtitle("请进行认证");
            builder.setNegativeButton("取消", aaaa, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("ssssssssss", "验证取消......");
                }
            });
            BiometricPrompt prompt = builder.build();
            CancellationSignal cancelSignal = new CancellationSignal();
            cancelSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
                @Override
                public void onCancel() {
                    new AlertDialog.Builder(getBaseContext()).setTitle("取消了").create().show();
                }
            });
            Executor eee = Executors.newSingleThreadExecutor();
            BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    Log.i("ssssssssss", "验证失败");
                }

                @Override
                public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                    Log.i("ssssssssss", "验证成功");
                }
            };
            prompt.authenticate(cancelSignal, eee, callback);
        }
    }

}
