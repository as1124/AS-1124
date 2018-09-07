package com.as1124.ch10test.async.message;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.as1124.ch10test.R;

/**
 * Android 异步消息通信，异步更新UI内容
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AsynMessageActivity extends Activity implements View.OnClickListener {

    private static final int UPDATE_TEXT = 111;

    private TextView textView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (UPDATE_TEXT == msg.what) {
                textView.setText(msg.obj.toString());
            }
        }
    };

    // 运行在非主线程中
    private AsyncTask<Void, Integer, Boolean> task = new AsyncTask<Void, Integer, Boolean>() {

        @Override
        protected void onPreExecute() {
            // 在main线程中执行
            android.util.Log.i("ssss", "onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            // 在子线程中执行
            android.util.Log.i("ssss", "去你妈的酷酷酷");
            publishProgress(10);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // 在main线程中执行
            android.util.Log.i("ssss", "onProgressUpdate");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            // 在main线程中执行
            android.util.Log.i("ssss", "onPostExecute");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyn_message);

        textView = findViewById(R.id.asyn_text);
        findViewById(R.id.but_thread).setOnClickListener(this);
        findViewById(R.id.but_runOnUI).setOnClickListener(this);
        findViewById(R.id.but_handler).setOnClickListener(this);
        findViewById(R.id.but_ayncTask).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.but_thread:
                new Thread(() -> {
                    textView.setText("通过异步线程修改Text内容");
                }).start();
                break;
            case R.id.but_runOnUI:
                new Thread(() -> {
                    runOnUiThread(() -> {
                        textView.setText("异步线程中通过runOnUiThread回到主线程修改Text内容");
                    });
                }).start();
                break;
            case R.id.but_handler:
                new Thread(() -> {
                    Message msg = new Message();
                    msg.what = UPDATE_TEXT;
                    msg.obj = "异步线程中通过Handler传递消息更新UI内容";
                    handler.sendMessage(msg);
                });
                break;
            case R.id.but_ayncTask:
                task.execute();
                break;
            default:
                break;
        }
    }
}
