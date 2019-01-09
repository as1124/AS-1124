package com.as1124.googledoc.connectivity.background;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.googledoc.connectivity.R;

/**
 * 使用HttpsURLConnection，以及在{@link android.os.AsyncTask}在后台异步处理网络任务
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class HttpsConnectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https_connection);

        NetworkFragment.getInstance(getFragmentManager());
    }
}
