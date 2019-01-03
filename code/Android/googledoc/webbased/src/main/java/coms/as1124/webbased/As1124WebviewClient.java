package coms.as1124.webbased;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.ViewGroup;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.lang.ref.WeakReference;

public class As1124WebviewClient extends WebViewClient {

    private Context appContext;

    private WeakReference<WebView> webViewReference;

    public As1124WebviewClient(Context context, WebView webView) {
        appContext = context.getApplicationContext();
        webViewReference = new WeakReference<>(webView);
    }

    /**
     * 当WebView渲染引擎崩溃时（网页存在错误导致、系统强制回收资源导致等）
     *
     * @param view
     * @param detail
     * @return
     */
    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        if (Build.VERSION.SDK_INT >= 26) {
            if (!detail.didCrash()) {
                // Renderer was killed because the system ran out of memory. The app can recover gracefully by
                // creating a new WebView instance in the foreground.
                if (webViewReference != null && webViewReference.get() != null) {
                    WebView webView = webViewReference.get();
                    ((ViewGroup) webView.getParent()).removeView(webView);
                    webView.stopLoading();
                    webView.destroy();
                    webView = null;
                    webViewReference.clear();
                }

                // By this point, the WebView is guaranteed to be null, so it's safe to reinitialize it.
                return true; // The app continues executing
            }
        }

        // Renderer crashed because of an internal error, such as a memory access violation.
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String host = Uri.parse(view.getUrl()).getHost();

        // 这里假设自己的域名主址就是这个
        if (host.indexOf("baidu.com") > 0) {
            // This is my website, so do not override; let my WebView load the page.
            return false;
        } else {
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URL
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(view.getUrl()));
            if (intent.resolveActivity(appContext.getPackageManager()) != null) {
                appContext.startActivity(intent);
            }
            return true;
        }
    }
}
