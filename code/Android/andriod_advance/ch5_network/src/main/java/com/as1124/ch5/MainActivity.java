package com.as1124.ch5;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.security.NetworkSecurityPolicy;
import android.util.Log;
import android.widget.Toast;

import com.as1124.ch5.okhttp.OkHttpActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Android 网络框架使用
 * <ol>
 * <li>{@link ApplicationInfo#FLAG_USES_CLEARTEXT_TRAFFIC} 用来控制APP是否强制https请求，<code>{@link NetworkSecurityPolicy}</code></li>
 * <li>Apache Http组件：因为Android组件已经移除，使用需要添加<code>useLibrary('org.apache.http.legacy')</code></li>
 * <li>HttpURLConnection/HttpsURLConnection</li>
 * <li>Volley: </li>
 * <li>OKHttp: </li>
 * <li>Retrofit: </li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    public static final String TAG = "CH5_Network";

    private Runnable apacheRunnable = () -> apacheGet("https://www.baidu.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 这个设置可以在AndroidManifest.xml的application节点进行配置
        findViewById(R.id.but_allow_http).setOnClickListener(v -> {
            boolean flag = NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted();
            if (!flag) {
                // ATTENTION 不生效要咋整???
                getApplicationInfo().flags = getApplicationInfo().flags | ApplicationInfo.FLAG_USES_CLEARTEXT_TRAFFIC;
            }
        });
        findViewById(R.id.but_test_http).setOnClickListener(v -> testCleartextRequest());

        findViewById(R.id.but_apache_get).setOnClickListener(
                v -> new Thread(apacheRunnable, "Apache-GET").start());

        findViewById(R.id.but_urlconnection_post).setOnClickListener(v -> urlConnectionPost());

        findViewById(R.id.but_to_okhttp).setOnClickListener(v -> startActivity(new Intent(this, OkHttpActivity.class)));

        findViewById(R.id.but_to_retrofit).setOnClickListener(v -> {
        });
    }

    private void apacheGet(String httpURL) {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
        HttpConnectionParams.setSoTimeout(httpParams, 30000);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);

        HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
        HttpProtocolParams.setUseExpectContinue(httpParams, true); // 持续握手
        HttpClient httpClient = new DefaultHttpClient(httpParams);

        HttpUriRequest getRequest = new HttpGet(httpURL);
        getRequest.addHeader("Connection", "Keep-Alive");
        try {
            HttpResponse response = httpClient.execute(getRequest);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    Log.i(TAG, EntityUtils.toString(entity, "UTF-8"));
                }
                entity.consumeContent();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果不允许http请求时会发生：<br/>
     * <code>java.io.IOException: Cleartext traffic not permitted</code>
     */
    private void testCleartextRequest() {
        if (!NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted()) {
            Toast.makeText(this, "当前应用不允许请求http接口", Toast.LENGTH_SHORT).show();
        }
        new AsyncTask<String, Integer, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                apacheGet(strings[0]);
                return null;
            }
        }.execute("http://images3.ctrip.com/wri/images/200709/LINWEI196813230257734.jpg");
    }

    private void urlConnectionPost() {
        BufferedWriter writer = null;
        try {
            URL url = new URL("https://www.baidu.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法
            connection.setRequestMethod("POST");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

            // 写请求参数
            List<NameValuePair> postParams = new ArrayList<>();
            writer.write(URLEncodedUtils.format(postParams, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }
}
