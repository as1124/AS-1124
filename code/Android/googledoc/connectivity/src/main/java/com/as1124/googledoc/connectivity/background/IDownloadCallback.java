package com.as1124.googledoc.connectivity.background;

public interface IDownloadCallback {

    public void startDownload(String downloadURL);

    public void onFinished(Object result);

    public void cancelDownload();
}
