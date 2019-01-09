package com.as1124.googledoc.connectivity.sync;

import android.content.Context;
import android.os.IBinder;

public class Authenticator {

    private Context mContext;

    public Authenticator(Context context) {
        this.mContext = context;
    }

    public IBinder getIBinder() {
        return null;
    }
}
