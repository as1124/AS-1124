package com.as1124.ch8test.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.as1124.ch8test.R;

import java.util.Iterator;

public class NoticeDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        TextView textView = findViewById(R.id.ch8_text_notice);
        String data = "";
        Intent passedIntent = getIntent();
        if (passedIntent != null) {
            Iterator<String> it = passedIntent.getExtras().keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = passedIntent.getExtras().get(key).toString();
                data = data.concat(key).concat("=").concat(value).concat("\r\n");
            }
        }

        textView.setText(data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
    }
}
