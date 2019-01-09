package com.as1124.googledoc.connectivity.background;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * 加载网络图片Task
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class DownloadTask extends AsyncTask<String, Integer, Object> {

    private IDownloadCallback mCallback;
    private ConnectivityManager networkManager;

    public DownloadTask(Context context, IDownloadCallback callback) {
        networkManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mCallback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // 调用发生在主线程

        NetworkInfo info = networkManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() ||
                (info.getType() != ConnectivityManager.TYPE_WIFI && info.getType() != ConnectivityManager.TYPE_MOBILE)) {
            // 无网络可用, 取消
            this.cancel(true);
            if (mCallback != null) {
                mCallback.cancelDownload();
            }
        }
    }

    @Override
    protected Object doInBackground(String... strings) {
        HttpsURLConnection connection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(strings[0]);
            if (url == null) {
                return null;
            }
            connection = (HttpsURLConnection) url.openConnection();
            if (connection == null) {
                return null;
            }
            connection.setReadTimeout(9000);
            connection.setConnectTimeout(5000);

            // For this use case, set HTTP method to GET.
            connection.setRequestMethod("GET");

            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input(response) body.
            connection.setDoInput(true);

            // Open communications link(network traffic occurs here)
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                connection.disconnect();
                return Boolean.FALSE;
            }

            inputStream = connection.getInputStream();
            return Drawable.createFromStream(inputStream, "有坂深雪");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close stream and disconnect HTTPS connection
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object taskResult) {
        super.onPostExecute(taskResult);

        // 调用发生在主线程

        if (mCallback != null) {
            mCallback.onFinished(taskResult);
        }
    }
}
