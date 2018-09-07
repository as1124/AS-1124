package com.as1124.ch10test.demo;

import android.os.AsyncTask;
import android.os.Environment;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private IDownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(IDownloadListener dListener) {
        this.listener = dListener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        long downloadedSize = 0;
        String downloadURL = params[0];
        String filename = FilenameUtils.getBaseName(downloadURL);
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

        InputStream inputStream = null;
        RandomAccessFile savedFile = null;
        Response response = null;
        File file = new File(directory, filename);
        if (file.exists()) {
            downloadedSize = file.length();
        }
        try {
            long totalSize = getContentLength(downloadURL);
            if (totalSize == 0) {
                return TYPE_FAILED;
            } else if (totalSize == downloadedSize) {
                return TYPE_SUCCESS;
            }
            OkHttpClient httpClient = new OkHttpClient.Builder().build();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downloadedSize + "-")
                    .url(downloadURL).get().build();
            response = httpClient.newCall(request).execute();
            if (response != null) {
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadedSize);
                byte[] buff = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(buff)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        savedFile.write(buff, 0, length);
                        downloadedSize += length;
                        int progress = (int) (downloadedSize * 100 / totalSize);
                        publishProgress(progress);
                    }
                }
            }
            return TYPE_SUCCESS;
        } catch (IOException e) {
            android.util.Log.i(getClass().getSimpleName(), e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (savedFile != null) {
                try {
                    savedFile.close();
                } catch (IOException e) {
                }
            }
            if (response != null) {
                response.close();
            }
        }
        return TYPE_FAILED;
    }

    public long getContentLength(String fileURL) throws IOException {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(fileURL).build();
        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                return response.body().contentLength();
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            default:
                break;
        }
    }

    public void pauseDownload() {
        this.isPaused = true;
    }

    public void cancelDownload() {
        this.isCanceled = true;
    }
}
