package com.as1124.googledoc.connectivity.background;

/**
 * 下载结果回调接口，可用于通知UI进行变更
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public interface IDownloadCallback {

    void startDownload(String downloadURL);

    void onFinished(Object result);

    void cancelDownload();
}
