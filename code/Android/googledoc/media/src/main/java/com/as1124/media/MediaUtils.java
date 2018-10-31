package com.as1124.media;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaUtils {


    /**
     * 搜索本地外部存储卡上的音频文件
     *
     * @param context 上下文
     */
    public static List<MediaBrowser.MediaItem> queryLocalAudioItem(Context context) {
        List<MediaBrowser.MediaItem> items = new ArrayList<>();
        MediaDescription.Builder builder = new MediaDescription.Builder();

        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            try {
                int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int audioTypeColumn = cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
                int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                int size = cursor.getCount();
                for (int i = 0; i < size; i++) {
                    cursor.moveToPosition(i);
                    long itemID = cursor.getLong(idColumn);
                    Uri itemURI = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, itemID);
                    String itemTitle = cursor.getString(titleColumn);
                    String artist = cursor.getString(artistColumn);
                    String album = cursor.getString(albumColumn);
                    int isMusic = cursor.getInt(audioTypeColumn);
                    if (isMusic == 1) {
                        builder.setTitle(itemTitle)
                                .setDescription(artist + "-" + album)
                                .setMediaId(Long.toString(itemID));
//                            .setMediaUri(itemURI)
//                            .setIconUri();
                        MediaBrowser.MediaItem item = new MediaBrowser.MediaItem(builder.build(), MediaBrowser.MediaItem.FLAG_PLAYABLE);
                        items.add(item);
                    }
                }
            } finally {
                cursor.close();
            }
        } else {
            // query failed, handle error
        }
        return items;
    }

    public static void queryImages() {

    }

    public static void queryVideos() {

    }

}
