package com.as1124.googledoc.permission;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.googledoc.R;

/**
 * 测试自定义权限
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class CustomPermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_permission);
    }
}
