package com.as1124.ch10test.demo;

public interface IDownloadListener {

    public void onProgress(int progress);

    public void onSuccess();

    public void onFailed();

    public void onCanceled();

    public void onPaused();
}
