package com.as1124.media;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class MediaUtils {

    public static void queryAudioList(Context context) {
        String[] projection = new String[]{};
        String selection = "";
        String[] selectArgs = new String[]{};
        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, selectArgs, null);
        if (cursor != null) {
            try {

            } finally {
                cursor.close();
            }
        }
    }

    public static void queryImages() {

    }

    public static void queryVideos() {

    }

}
