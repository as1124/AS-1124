package coms.as1124.webbased;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

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
            Toast.makeText(this, "当前WebView内核信息：" + packageInfo.packageName + ", V-" + packageInfo.versionName, Toast.LENGTH_LONG).show();
        }

        String defaultAgent = WebSettings.getDefaultUserAgent(this);
        Log.i("Web-based Content", "Default UserAgent == " + defaultAgent);
        WebSettings webviewSettings = mWebview.getSettings();
        webviewSettings.setJavaScriptEnabled(true);
        webviewSettings.setDomStorageEnabled(true);
        webviewSettings.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= 26) {
            webviewSettings.setSafeBrowsingEnabled(false);
        }
        mWebview.addJavascriptInterface(new WebviewScriptBridge(this), "as1124");
        mWebview.setWebViewClient(new As1124WebviewClient(this, mWebview));
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                super.onPermissionRequest(request);
                Toast.makeText(MainActivity.this, "【网页】onPermissionRequest", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
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
        });
//        mWebview.loadUrl("http://192.168.8.145:8080/hetong.html");
        mWebview.loadUrl("https://www.baidu.com");
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
}
