package com.as1124.googledoc.connectivity.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 数据同步器. Handle the transfer of data between a server and an app, using the Android sync
 * adapter framework.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124SyncAdapter extends AbstractThreadedSyncAdapter {

    // Define a variable to contain a content resolver instance
    private ContentResolver mContentResolver;

    public As1124SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        // If your app uses a content resolver, get an instance of it from the incoming Context.
        mContentResolver = context.getContentResolver();
    }

    /**
     * Specify the code you want to run in the sync adapter. The entire sync adapter runs is a
     * background thread, so you don't have to set up your own background processing.
     *
     * @param account
     * @param extras
     * @param authority
     * @param provider
     * @param syncResult
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Toast.makeText(getContext(), "AS1124 账号信息开始同步", Toast.LENGTH_SHORT).show();
        android.util.Log.i("[AccountSyncAdapter]", "Android系统开始同步 【AS1124】 账号信息!!");
    }
}
