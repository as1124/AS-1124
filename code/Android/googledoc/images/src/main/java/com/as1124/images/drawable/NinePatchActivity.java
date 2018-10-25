package com.as1124.images.drawable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.as1124.images.R;

public class NinePatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ninepatch);

        WebView webView = findViewById(R.id.webview_ninepatch);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                android.util.Log.e("NinePatchActivity", error.toString());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("NinePatchActivity", "webview加载完成");
            }
        });
        webView.loadUrl("https://www.cnblogs.com/woider/p/5121700.html");
    }
}
