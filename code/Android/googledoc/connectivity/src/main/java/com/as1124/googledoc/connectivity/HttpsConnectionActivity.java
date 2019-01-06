package com.as1124.googledoc.connectivity;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.googledoc.connectivity.background.NetworkFragment;

public class HttpsConnectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_https_connection);

        NetworkFragment fragment = NetworkFragment.getInstance(getFragmentManager());
    }
}
