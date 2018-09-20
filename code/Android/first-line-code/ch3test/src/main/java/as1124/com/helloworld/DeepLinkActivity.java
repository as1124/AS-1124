package as1124.com.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

/**
 * 阐述android中Link的处理问题
 * <ul>
 * <li><code>Deep Link</code>的处理：想在浏览器里面访问，需要用&lat;a href="">，直接在地址栏访问无法实现跳转</li>
 * <li><code>Android APP Link</code>的处理</li>
 * <li><code>Instant APP Link</code>的处理</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class DeepLinkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);

        findViewById(R.id.but_query_activities).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("huangjw://goods/default?data=index"));
            PackageManager manager = getPackageManager();
            List<ResolveInfo> result = manager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            android.util.Log.i("DeepLink", "Resolver size = " + result.size());
        });
        WebView myWebview = findViewById(R.id.webview_deep_link);
        myWebview.getSettings().setAllowContentAccess(true);
        myWebview.getSettings().setAllowFileAccess(true);
        myWebview.getSettings().setJavaScriptEnabled(true);
        myWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                android.util.Log.i("DeepLinkActivity", "web page load finished!");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                android.util.Log.i("DeepLinkActivity", "web page error occurred: " + error.toString());
            }
        });
        String action = getIntent().getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = getIntent().getData();
            if (uri != null) {
                String schema = uri.getScheme();
                android.util.Log.i(DeepLinkActivity.class.getSimpleName(), "Android Deep Link: " + schema);
            }
        } else {
            myWebview.loadUrl("http://www.bing.com");
        }
    }
}
