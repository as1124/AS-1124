package com.as1124.intents.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.as1124.intents.R

/**
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
class TestActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        var mWebView = findViewById<WebView>(R.id.webview_activity_test);
        mWebView.settings.allowContentAccess = true;
        mWebView.settings.javaScriptEnabled = true;
        var webClient: WebViewClient = WebViewClient();
        mWebView.webViewClient = webClient;
        if (this.intent != null) {
            var urlData = this.intent.data;
            if (urlData != null) {
                mWebView?.loadUrl(urlData.toString())
            } else {
                mWebView.loadUrl("http://www.bing.com");
            }
        }
        findViewById<Button>(R.id.but_finish_result).setOnClickListener { v: View? ->
            finishSelf()
        }

    }

    override fun onStart() {
        super.onStart()
    }

    private fun finishSelf() {
        this.setResult(RESULT_OK)
        this.finish();
    }
}
