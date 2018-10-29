package com.as1124.media.video;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.service.media.MediaBrowserService;

import java.util.List;

public class VideoService extends MediaBrowserService {

    public VideoService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(String parentId, Result<List<MediaBrowser.MediaItem>> result) {

    }


}
