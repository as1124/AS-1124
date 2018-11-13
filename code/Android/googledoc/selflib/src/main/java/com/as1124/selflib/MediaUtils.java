package com.as1124.selflib;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaDescription;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 多媒体素材处理公共类
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MediaUtils {

    public static String[] IMAGE_PROJECTION = new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DESCRIPTION};

    /**
     * 查询文件存储存储中的图片
     *
     * @param context 上下文
     */
    public static List<String> queryImages(Context context) {
        List<String> imagePaths = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(uri, IMAGE_PROJECTION, null, null, null);
        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                int dataIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                int bucketNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                int size = cursor.getCount();
                for (int i = 0; i < size; i++) {
                    cursor.moveToPosition(i);
                    long id = cursor.getLong(idIndex);
                    String path = cursor.getString(dataIndex);
                    // 文件夹名称
                    String bucketName = cursor.getString(bucketNameIndex);
                    Log.i("IMAGE-INFO", id + " = " + path + ", " + bucketName);
                    if (!imagePaths.contains(path)) {
                        imagePaths.add(path);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return imagePaths;
    }

    /**
     * 搜索本地存储卡上的音频文件
     *
     * @param context 上下文
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static List<MediaBrowser.MediaItem> queryAudioItem(Context context) {
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

}
