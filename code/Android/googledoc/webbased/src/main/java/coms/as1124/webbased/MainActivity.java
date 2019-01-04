package coms.as1124.webbased;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * WebView功能使用
 * <ul>
 * <li>网页JavaScript同原生系统交互接口：{@link android.webkit.JavascriptInterface}</li>
 * <li>WebViewClient处理</li>
 * <li>动态权限处理：Android 7.0之后非https网站默认不允许申请定位, 没有提醒框</li>
 * <li>回收：当WebView崩溃或者Android强制回收内存时、{@link android.webkit.WebViewClient#onRenderProcessGone(WebView, RenderProcessGoneDetail)}</li>
 * <li>API 19之后提供了基于Chromium内核的WebView</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmai.com)
 */
public class MainActivity extends Activity {

    private WebView mWebview;
    private GeolocationPermissions.Callback locationCallback;
    private String previewOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebview = findViewById(R.id.webview);
        if (Build.VERSION.SDK_INT >= 26) {
            PackageInfo packageInfo = WebView.getCurrentWebViewPackage();
            if (packageInfo != null) {
                Toast.makeText(this, "当前WebView内核信息：" + packageInfo.packageName + ", V-" + packageInfo.versionName,
                        Toast.LENGTH_LONG).show();
            }
        }

        String defaultAgent = WebSettings.getDefaultUserAgent(this);
        Log.i("Web-based Content", "Default UserAgent == " + defaultAgent);
        WebSettings webviewSettings = mWebview.getSettings();
        webviewSettings.setJavaScriptEnabled(true);
        webviewSettings.setDomStorageEnabled(true);
        webviewSettings.setAllowContentAccess(true);
        webviewSettings.setGeolocationEnabled(true);
        webviewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= 26) {
            webviewSettings.setSafeBrowsingEnabled(false);
        }
        mWebview.addJavascriptInterface(new WebviewScriptBridge(this), "as1124");
        mWebview.setWebViewClient(new As1124WebviewClient(this, mWebview));
        mWebview.setWebChromeClient(new As1124WebChromeClient(this));
//        mWebview.loadUrl("https://www.baidu.com");
        mWebview.loadUrl("file:///android_asset/4webview.html");

        // 从原生端调用网页 javascript 的两种方式
        findViewById(R.id.but_load_url).setOnClickListener(v ->
                mWebview.loadUrl("javascript:callFromClient('黄先生')"));
        findViewById(R.id.but_eval_js).setOnClickListener(v ->
                // API 19及以上版本可以调用
                mWebview.evaluateJavascript("callFromClientWithReturn(89)",
                        retValue -> Toast.makeText(MainActivity.this, retValue, Toast.LENGTH_SHORT).show())
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1234) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationCallback.invoke(previewOrigin, true, false);
                Toast.makeText(MainActivity.this, "【网页】定位申请", Toast.LENGTH_SHORT).show();
            } else {
                locationCallback.invoke(previewOrigin, false, false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebview != null) {
            mWebview.destroy();
        }
    }

    class As1124WebChromeClient extends WebChromeClient {

        private WeakReference<MainActivity> activityWeakReference;

        As1124WebChromeClient(MainActivity context) {
            activityWeakReference = new WeakReference<>(context);
        }

        @Override
        public void onPermissionRequest(PermissionRequest request) {
            String[] resource = new String[0];
            if (Build.VERSION.SDK_INT >= 21) {
                resource = request.getResources();
            }
            Toast.makeText(activityWeakReference.get(), "【网页】onPermissionRequest", Toast.LENGTH_SHORT).show();
            Log.i("[As1124WebChromeClient]", "请求资源： " + resource);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // 网址url, 是否允许, 是否保存权限申请状态
                    callback.invoke(origin, true, false);
                } else {
                    locationCallback = callback;
                    previewOrigin = origin;
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1234);
                }
            }
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            if (message.startsWith("as1124://")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activityWeakReference.get());
                builder.setMessage("拦截了网页的Alert弹窗").show();
                return false;
            } else {
                return false;
            }
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

    } // end of class
}
