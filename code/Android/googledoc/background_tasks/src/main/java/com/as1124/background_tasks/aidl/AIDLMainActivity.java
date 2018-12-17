package com.as1124.background_tasks.aidl;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.background_tasks.R;

/**
 * AIDL远程调用服务使用学习,
 * <ul>
 * <li>在src/main/aidl目录下创建后缀为aidl的文件并定义服务接口</li>
 * <li>服务端实现接口</li>
 * <li>向客户端公开接口</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AIDLMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidlmain);

    }
}
